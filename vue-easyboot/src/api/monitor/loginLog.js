import request from '@/utils/request'

// 查询登录日志列表
export function list(params) {
  return request({
    url: '/monitor/loginLog/list',
    method: 'post',
    data: params
  })
}

// 删除登录日志
export function delLoginLog(ids) {
  return request({
    url: '/monitor/loginLog/' + ids,
    method: 'delete'
  })
}

// 清空登录日志
export function cleanLoginLog() {
  return request({
    url: '/monitor/loginLog/clean',
    method: 'delete'
  })
}

// 导出登录日志
export function exportLoginLog(query) {
  return request({
    url: '/monitor/loginLog/export',
    method: 'post',
    data: query
  })
}
