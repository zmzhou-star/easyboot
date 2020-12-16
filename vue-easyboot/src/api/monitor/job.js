import request from '@/utils/request'

/**
 * 查询定时任务列表
 * @param {Object} query
 * @returns {Object}
 */
export function listJob(query) {
  return request({
    url: '/monitor/job/list',
    method: 'post',
    data: query
  })
}
/**
 * 查询定时任务详细信息
 * @param {number} id
 * @returns {Object}
 */
export function getJob(id) {
  return request({
    url: '/monitor/job/' + id,
    method: 'get'
  })
}
/**
 * 新增定时任务
 * @param {Object} data
 * @returns {Object}
 */
export function addJob(data) {
  return request({
    url: '/monitor/job',
    method: 'post',
    data: data
  })
}
/**
 * 修改定时任务
 * @param {Object} data
 * @returns {Object}
 */
export function updateJob(data) {
  return request({
    url: '/monitor/job',
    method: 'put',
    data: data
  })
}
/**
 * 删除定时任务
 * @param {number} id
 * @returns {Object}
 */
export function delJob(id) {
  return request({
    url: '/monitor/job/' + id,
    method: 'delete'
  })
}

/**
 * 任务状态修改
 * @param {Number} id id
 * @param {String} status 任务状态
 * @returns {AxiosPromise}
 */
export function changeJobStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/monitor/job/changeStatus',
    method: 'put',
    data: data
  })
}

/**
 * 定时任务立即执行一次
 * @param {Number} id id
 * @returns {AxiosPromise}
 */
export function runJob(id) {
  const data = { id }
  return request({
    url: '/monitor/job/run',
    method: 'put',
    data: data
  })
}
