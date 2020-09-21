import request from '@/utils/request'

/**
 * 查询生成表数据
 * @param {Object} query
 * @returns {Object}
 */
export function listTable(query) {
  return request({
    url: '/tool/code/gen/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询db数据库列表
 * @param {Object} query
 * @returns {Object}
 */
export function listDbTable(query) {
  return request({
    url: '/tool/code/gen/dbTable/list',
    method: 'get',
    params: query
  })
}
/**
 * 查询表详细信息
 * @param {number} tableId
 * @returns {Object}
 */
export function getGenTable(tableId) {
  return request({
    url: '/tool/code/gen/' + tableId,
    method: 'get'
  })
}
/**
 * 修改代码生成信息
 * @param {Object} data
 * @returns {Object}
 */
export function updateGenTable(data) {
  return request({
    url: '/tool/code/gen',
    method: 'put',
    data: data
  })
}
/**
 * 导入表
 * @param {Object} data
 * @returns {Object}
 */
export function importTable(data) {
  return request({
    url: '/tool/code/gen/importTable',
    method: 'post',
    params: data
  })
}
/**
 * 预览生成代码
 * @param {number} tableId
 * @returns {Object}
 */
export function previewTable(tableId) {
  return request({
    url: '/tool/code/gen/preview/' + tableId,
    method: 'get'
  })
}
/**
 * 删除表数据
 * @param {number} tableId
 * @returns {Object}
 */
export function delTable(tableId) {
  return request({
    url: '/tool/code/gen/' + tableId,
    method: 'delete'
  })
}

