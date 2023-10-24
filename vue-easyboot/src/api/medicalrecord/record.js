import request from '@/utils/request'

/**
 * 查询看诊记录列表
 * @param {Object} query
 * @returns {Object}
 */
export function listRecord(query) {
  return request({
    url: '/medicalrecord/record/list',
    method: 'post',
    data: query
  })
}
/**
 * 查询看诊记录详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getRecord(id) {
  return request({
    url: '/medicalrecord/record/' + id,
    method: 'get'
  })
}
/**
 * 新增看诊记录
 * @param {Object} data
 * @returns {Object}
 */
export function addRecord(data) {
  return request({
    url: '/medicalrecord/record',
    method: 'post',
    data: data
  })
}
/**
 * 修改看诊记录
 * @param {Object} data
 * @returns {Object}
 */
export function updateRecord(data) {
  return request({
    url: '/medicalrecord/record',
    method: 'put',
    data: data
  })
}
/**
 * 删除看诊记录
 * @param {number} id
 * @returns {Object}
 */
export function delRecord(id) {
  return request({
    url: '/medicalrecord/record/' + id,
    method: 'delete'
  })
}
/**
 * 导出看诊记录
 * @param {Object} params
 * @returns {Object}
 */
export function exportRecord(params) {
  return request({
    url: '/medicalrecord/record/export',
    method: 'post',
    data: params
  })
}
