import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    nickName: '',
    avatar: '',
    roles: [],
    permissions: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_NICKNAME: (state, nickName) => {
    state.nickName = nickName
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },
  SET_AVATAR: (state, avatar) => {
    // state.avatar = avatar || 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'
    state.avatar = avatar || '/avatar.gif'
  }
}
// 用户头像访问路径前缀
const avatarPrefix = process.env.VUE_APP_BASE_API + 'common/avatar?url='
const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, code, uuid } = userInfo
    return new Promise((resolve, reject) => {
      login(username.trim(), password, code, uuid).then(response => {
        commit('SET_TOKEN', response.token)
        setToken(response.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(res => {
        if (!res) {
          return reject('Verification failed, please Login again.')
        }
        const user = res.user
        // 设置用户头像访问路径
        const avatar = !user.avatar ? '' : avatarPrefix + user.avatar
        if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
          commit('SET_ROLES', res.roles)
          commit('SET_PERMISSIONS', res.permissions)
        } else {
          commit('SET_ROLES', ['ROLE_DEFAULT'])
        }
        commit('SET_NAME', user.username)
        commit('SET_NICKNAME', user.nickName)
        commit('SET_AVATAR', avatar)
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        commit('SET_PERMISSIONS', [])
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },
  // 前端 登出
  fedLogOut({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  },
  /**
   * 更新用户头像
   * @param commit
   * @param avatar
   */
  setAvatar({ commit }, avatar) {
    commit('SET_AVATAR', avatarPrefix + avatar)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

