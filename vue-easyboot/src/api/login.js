import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    method: 'post',
    data
  })
}
// 获取用户详细信息
export function getInfo(token) {
  return request({
    url: '/getUserInfo',
    method: 'get'
    // params: { token }
  })
}
// 退出
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}
// 获取验证码
export function getCaptcha() {
  return request({
    url: '/common/captcha',
    method: 'get'
  })
}
