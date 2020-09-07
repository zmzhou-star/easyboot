import request from '@/utils/request'
import { praseStrEmpty } from '@/utils/easyboot'

// 获取用户列表
export function getList(params) {
  return request({
    url: '/system/user/list',
    method: 'post',
    data: params
  })
}
// 用户状态修改
export function changeUserStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/system/user/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询用户详细
export function getOne(userId) {
  return request({
    url: '/system/user/getOne/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
export function add(data) {
  return request({
    url: '/system/user/save',
    method: 'post',
    data: data
  })
}

// 修改用户
export function update(data) {
  return request({
    url: '/system/user/update',
    method: 'put',
    data: data
  })
}

// 删除用户
export function del(userId) {
  return request({
    url: '/system/user/delete/' + userId,
    method: 'delete'
  })
}

// 导出用户
export function exportUser(params) {
  return request({
    url: '/system/user/export',
    method: 'post',
    data: params
  })
}

// 用户密码重置
export function resetPwd(id, password) {
  const data = {
    id,
    password
  }
  return request({
    url: '/system/user/resetPwd',
    method: 'put',
    data: data
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/system/user/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: '/system/user/profile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/system/user/profile/updatePwd',
    method: 'put',
    params: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  })
}

// 下载用户导入模板
export function excelTemplate() {
  return request({
    url: '/system/user/excelTemplate',
    method: 'get'
  })
}
