import request from '@/utils/request'

/**
 * 查询彩票历史数据列表
 * @param {Object} query
 * @returns {Object}
 */
export function listHistory(query) {
  return request({
    url: '/lottery/history/list',
    method: 'post',
    data: query
  })
}

/**
 * 查询彩票历史数据详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getHistory(id) {
  return request({
    url: '/lottery/history/' + id,
    method: 'get'
  })
}

/**
 * 导出彩票历史数据
 * @param {Object} params
 * @returns {Object}
 */
export function exportHistory(params) {
  return request({
    url: '/lottery/history/export',
    method: 'post',
    data: params
  })
}
