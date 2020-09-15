import request from '@/utils/request'

// 查询操作日志列表
export function list(params) {
  return request({
    url: '/monitor/operLog/list',
    method: 'post',
    data: params
  })
}

// 删除操作日志
export function delOperLog(operId) {
  return request({
    url: '/monitor/operLog/' + operId,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanOperLog() {
  return request({
    url: '/monitor/operLog/clean',
    method: 'delete'
  })
}

// 导出操作日志
export function exportOperLog(query) {
  return request({
    url: '/monitor/operLog/export',
    method: 'post',
    data: query
  })
}
