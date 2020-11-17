import request from '@/utils/request'

/**
 * 查询参数配置列表
 * @param {Object} params
 * @returns {Object}
 */
export function listConfig(params) {
  return request({
    url: '/system/config/list',
    method: 'post',
    data: params
  })
}
/**
 * 查询参数配置详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getConfig(id) {
  return request({
    url: '/system/config/' + id,
    method: 'get'
  })
}

// 根据参数键名查询参数值
export function getConfigKey(configKey) {
  return request({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  })
}
/**
 * 新增参数配置
 * @param {Object} data
 * @returns {Object}
 */
export function addConfig(data) {
  return request({
    url: '/system/config',
    method: 'post',
    data: data
  })
}
/**
 * 修改参数配置
 * @param {Object} data
 * @returns {Object}
 */
export function updateConfig(data) {
  return request({
    url: '/system/config',
    method: 'put',
    data: data
  })
}
/**
 * 删除参数配置
 * @param {number} id
 * @returns {Object}
 */
export function delConfig(id) {
  return request({
    url: '/system/config/' + id,
    method: 'delete'
  })
}
/**
 * 导出参数配置
 * @param {Object} params
 * @returns {Object}
 */
export function exportConfig(params) {
  return request({
    url: '/system/config/export',
    method: 'post',
    data: params
  })
}

