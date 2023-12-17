import request from '@/utils/request'

/**
 * 查询药方列表
 * @param {Object} query
 * @returns {Object}
 */
export function listPrescript(query) {
  return request({
    url: '/medicalrecord/prescript/list',
    method: 'post',
    data: query
  })
}
/**
 * 查询药方详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getPrescript(id) {
  return request({
    url: '/medicalrecord/prescript/' + id,
    method: 'get'
  })
}
/**
 * 新增药方
 * @param {Object} data
 * @returns {Object}
 */
export function addPrescript(data) {
  return request({
    url: '/medicalrecord/prescript',
    method: 'post',
    data: data
  })
}
/**
 * 修改药方
 * @param {Object} data
 * @returns {Object}
 */
export function updatePrescript(data) {
  return request({
    url: '/medicalrecord/prescript',
    method: 'put',
    data: data
  })
}
/**
 * 删除药方
 * @param {number} id
 * @returns {Object}
 */
export function delPrescript(id) {
  return request({
    url: '/medicalrecord/prescript/' + id,
    method: 'delete'
  })
}
/**
 * 导出药方
 * @param {Object} params
 * @returns {Object}
 */
export function exportPrescript(params) {
  return request({
    url: '/medicalrecord/prescript/export',
    method: 'post',
    data: params
  })
}
/**
 * 查询所有药方
 * @returns {Object}
 */
export function findAllPrescript() {
  return request({
    url: '/medicalrecord/prescript/findAllPrescript',
    method: 'get'
  })
}
