import request from '@/utils/request'

/**
 * 查询通知公告信息列表
 * @param {Object} query
 * @returns {Object}
 */
export function listNotice(query) {
  return request({
    url: '/system/notice/list',
    method: 'post',
    data: query
  })
}
/**
 * 查询通知公告信息详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getNotice(id) {
  return request({
    url: '/system/notice/' + id,
    method: 'get'
  })
}
/**
 * 新增通知公告信息
 * @param {Object} data
 * @returns {Object}
 */
export function addNotice(data) {
  return request({
    url: '/system/notice',
    method: 'post',
    data: data
  })
}
/**
 * 修改通知公告信息
 * @param {Object} data
 * @returns {Object}
 */
export function updateNotice(data) {
  return request({
    url: '/system/notice',
    method: 'put',
    data: data
  })
}
/**
 * 删除通知公告信息
 * @param {number} id
 * @returns {Object}
 */
export function delNotice(id) {
  return request({
    url: '/system/notice/' + id,
    method: 'delete'
  })
}
