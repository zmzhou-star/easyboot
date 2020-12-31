/*
 * Created by zmzhou on 2020/12/29 17:12
 */
import request from '@/utils/request'

/**
 * 获取邮箱验证码
 * @param params 用户名和邮箱
 * @returns {AxiosPromise}
 */
export function getEmailCode(params) {
  return request({
    url: '/nonAuth/getEmailCode',
    method: 'get',
    params: params
  })
}

/**
 * 验证用户邮箱验证码
 * @param params 用户邮箱验证码信息
 * @returns {AxiosPromise}
 */
export function checkEmailCode(params) {
  return request({
    url: '/nonAuth/checkEmailCode',
    method: 'post',
    data: params
  })
}

/**
 * 重置密码
 * @param uuid 重置密码会话id
 * @param password 新密码
 * @returns {AxiosPromise}
 */
export function resetPwd(uuid, password) {
  const data = {
    uuid,
    password
  }
  return request({
    url: '/nonAuth/resetPwd',
    method: 'put',
    params: data
  })
}
