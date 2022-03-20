import request from '@/utils/request'

// 获取redis缓存信息
export function getCache() {
  return request({
    url: '/monitor/cache',
    method: 'get'
  })
}
