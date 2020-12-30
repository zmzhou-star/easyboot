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
