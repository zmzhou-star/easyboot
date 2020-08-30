import request from '@/utils/request'

// 登录方法
export function login(data) {
  return request({
    url: '/system/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/system/user/getUserInfo',
    method: 'get'
    // params: { token }
  })
}

export function logout() {
  return request({
    url: '/system/user/logout',
    method: 'post'
  })
}
