import request from '@/utils/request'

// 查询字典类型列表
export function listType(query) {
  return request({
    url: '/system/dict/type/list',
    method: 'post',
    data: query
  })
}

// 查询字典类型详细
export function getType(dictId) {
  return request({
    url: '/system/dict/' + dictId,
    method: 'get'
  })
}

// 新增字典类型
export function addType(data) {
  return request({
    url: '/system/dict',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function updateType(data) {
  return request({
    url: '/system/dict',
    method: 'put',
    data: data
  })
}

// 删除字典类型
export function delType(dictId) {
  return request({
    url: '/system/dict/' + dictId,
    method: 'delete'
  })
}

// 导出字典类型
export function exportType(query) {
  return request({
    url: '/system/dict/type/export',
    method: 'post',
    data: query
  })
}

// 获取字典选择框列表
export function optionSelect() {
  return request({
    url: '/system/dict/type/optionSelect',
    method: 'get'
  })
}
