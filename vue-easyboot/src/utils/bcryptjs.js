// 引入bcryptjs库
import bcrypt from 'bcryptjs'

/**
 * 加密
 * @param password
 * @returns {?string|string}
 */
export function hashSync(password) {
  // 定义密码加密的计算强度,默认10
  var salt = bcrypt.genSaltSync(12)
  // 把自己的密码(password)带进去,返回的就是加密后的密码
  return bcrypt.hashSync(password, salt)
}

/**
 * 比较
 * @param password
 * @param hash
 * @returns {boolean}
 */
export function compareSync(password, hash) {
  // password为用户输入的密码,hash为后台返回的密码，若是密码相同则返回true
  return bcrypt.compareSync(password, hash)
}
