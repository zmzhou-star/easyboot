import request from '@/utils/request'

/**
 * 查询系统邮件记录列表
 * @param {Object} query
 * @returns {Object}
 */
export function listMail(query) {
  return request({
    url: '/monitor/mail/list',
    method: 'post',
    data: query
  })
}
/**
 * 查询系统邮件记录详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getMail(id) {
  return request({
    url: '/monitor/mail/' + id,
    method: 'get'
  })
}

/**
 * 删除系统邮件记录
 * @param {number} id
 * @returns {Object}
 */
export function delMail(id) {
  return request({
    url: '/monitor/mail/' + id,
    method: 'delete'
  })
}
/**
 * 导出系统邮件记录
 * @param {Object} params
 * @returns {Object}
 */
export function exportMail(params) {
  return request({
    url: '/monitor/mail/export',
    method: 'post',
    data: params
  })
}
