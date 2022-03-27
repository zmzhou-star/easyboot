import request from '@/utils/request'

// 获取redis缓存信息
export function getCacheInfo() {
  return request({
    url: '/monitor/cache',
    method: 'get'
  })
}

/**
 * 获取redis缓存信息列表
 *
 * @returns {AxiosPromise}
 */
export function getCacheData(query) {
  return request({
    url: '/monitor/cache/getCacheData',
    method: 'get',
    params: query
  })
}
