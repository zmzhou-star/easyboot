import request from '@/utils/request'

/**
 * 查询定时任务日志列表
 * @param {Object} query
 * @returns {Object}
 */
export function listJobLog(query) {
  return request({
    url: '/monitor/jobLog/list',
    method: 'post',
    data: query
  })
}
/**
 * 清空定时任务日志
 * @returns {Object}
 */
export function cleanJobLog() {
  return request({
    url: '/monitor/jobLog/clean',
    method: 'delete'
  })
}
/**
 * 删除定时任务日志
 * @param {number} id
 * @returns {Object}
 */
export function delJobLog(id) {
  return request({
    url: '/monitor/jobLog/' + id,
    method: 'delete'
  })
}
/**
 * 导出定时任务日志
 * @param {Object} params
 * @returns {Object}
 */
export function exportJobLog(params) {
  return request({
    url: '/monitor/jobLog/export',
    method: 'post',
    data: params
  })
}
