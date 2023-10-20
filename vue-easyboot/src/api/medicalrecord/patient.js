import request from '@/utils/request'

/**
 * 查询病人信息列表
 * @param {Object} query
 * @returns {Object}
 */
export function listPatient(query) {
  return request({
    url: '/medicalrecord/patient/list',
    method: 'post',
    data: query
  })
}
/**
 * 查询病人信息详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getPatient(id) {
  return request({
    url: '/medicalrecord/patient/' + id,
    method: 'get'
  })
}
/**
 * 新增病人信息
 * @param {Object} data
 * @returns {Object}
 */
export function addPatient(data) {
  return request({
    url: '/medicalrecord/patient',
    method: 'post',
    data: data
  })
}
/**
 * 修改病人信息
 * @param {Object} data
 * @returns {Object}
 */
export function updatePatient(data) {
  return request({
    url: '/medicalrecord/patient',
    method: 'put',
    data: data
  })
}
/**
 * 删除病人信息
 * @param {number} id
 * @returns {Object}
 */
export function delPatient(id) {
  return request({
    url: '/medicalrecord/patient/' + id,
    method: 'delete'
  })
}
/**
 * 导出病人信息
 * @param {Object} params
 * @returns {Object}
 */
export function exportPatient(params) {
  return request({
    url: '/medicalrecord/patient/export',
    method: 'post',
    data: params
  })
}
