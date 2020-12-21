/*
 * Created by zmzhou on 2020/12/21 11:16
 */
import request from '@/utils/request'

/**
 * 首页面板卡片统计
 */
export function cardStat() {
  return request({
    url: '/dashboard/cardStat',
    method: 'post'
  })
}
/**
 * 用户登录日志记录统计
 * @param {object} params 查询日期范围
 */
export function userLoginStat(params) {
  return request({
    url: '/dashboard/userLoginStat',
    method: 'post',
    data: params
  })
}
