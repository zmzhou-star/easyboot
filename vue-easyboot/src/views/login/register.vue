<template>
  <div class="register-container">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="top title">注册账号</h3>
      </div>
      <el-form-item prop="username" label="用户名" label-width="82px">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="registerForm.username"
          name="username"
          type="text"
          minlength="4"
          tabindex="1"
          placeholder="不可修改，长度4~20个字符"
        />
      </el-form-item>
      <el-form-item prop="password" label="密码" label-width="82px">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="registerForm.password"
          :type="passwordType"
          name="password"
          minlength="6"
          maxlength="20"
          tabindex="2"
          placeholder="字母、数字和特殊字符，长度6~20个字符"
        />
        <span class="show-pwd" @mousedown="showPwd" @mouseup="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item prop="confirmPassword" label="确认密码" label-width="82px">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="confirmPassword"
          v-model="registerForm.confirmPassword"
          :type="passwordType"
          name="confirmPassword"
          minlength="6"
          maxlength="20"
          tabindex="3"
        />
        <span class="show-pwd" @mousedown="showPwd" @mouseup="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item prop="tel" label="手机号码" label-width="82px">
        <span class="svg-container">
          <svg-icon icon-class="tel" />
        </span>
        <el-input
          ref="tel"
          v-model="registerForm.tel"
          name="tel"
          type="text"
          tabindex="4"
          minlength="11"
          maxlength="11"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="email" label="邮 箱" label-width="82px">
        <span class="svg-container">
          <svg-icon icon-class="email" />
        </span>
        <el-input
          ref="email"
          v-model="registerForm.email"
          name="email"
          type="text"
          tabindex="5"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="remark" label="邮箱验证码" label-width="82px">
        <span class="svg-container">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </span>
        <el-input
          v-model="registerForm.remark"
          auto-complete="off"
          maxlength="6"
          type="text"
          tabindex="6"
          class="captcha"
          @keyup.enter.native="register"
        />
        <el-button v-if="countdown !== 60" type="info" class="get-code">{{ countdown }}s</el-button>
        <el-button v-if="countdown === 60" :loading="loadingEmailCode" type="primary" class="get-code" @click.native.prevent="getRegisterEmailCode">点击获取验证码</el-button>
      </el-form-item>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="register">注册</el-button>
      <div>
        <router-link to="/login" class="link-type2" style="font-size: 14px;">
          <span>已有账号，返回登录</span>
        </router-link>
      </div>
    </el-form>
  </div>
</template>

<script>
import { getRegisterEmailCode, register } from '@/api/nonAuth'

export default {
  name: 'Register',
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      countdown: 60,
      registerForm: {
        username: undefined,
        nickName: undefined,
        password: undefined,
        tel: undefined,
        email: undefined,
        sex: '0',
        remark: ''
      },
      registerRules: {
        username: [{ required: true, trigger: 'blur', message: '用户名不能为空，长度在4~20个字符', min: 4, max: 20 }],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' },
          { pattern: /^.*(?=.{6,16})(?=.*[A-Za-z]{2,})(?=.*\d)(?=.*[!@#$%^&*?\.\(\)]).*$/,
            min: 6, max: 20, message: '密码必需由字母、数字和特殊字符组成，长度在6~20个字符', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: '确认密码不能为空', trigger: 'blur' },
          { required: true, validator: equalToPassword, trigger: 'blur' }
        ],
        tel: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' },
          { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }],
        email: [{ required: true, message: '邮箱地址不能为空', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }],
        remark: [{ required: false, trigger: 'change', message: '邮箱验证码不能为空' }]
      },
      passwordType: 'password',
      loadingEmailCode: false,
      loading: false
    }
  },
  mounted() {
    this.$refs.username.focus()
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    /** 获取邮箱验证码 */
    getRegisterEmailCode() {
      const that = this
      this.registerRules.remark[0].required = false
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          that.loadingEmailCode = true
          // 密码加密
          const param = that.deepClone(that.registerForm)
          param.password = that.sha256(that.registerForm.password)
          param.confirmPassword = undefined
          getRegisterEmailCode(param).then(res => {
            that.registerForm.nickName = res
            that.loadingEmailCode = false
            this.msgSuccess('验证码发送成功，请前往邮箱查看')
            // 已经成功发送了邮件，开始倒计时60秒内不允许再点获取邮箱验证码按钮
            that.countdown -= 1
            const myInterval = setInterval(function() {
              that.countdown -= 1
              if (that.countdown === 0) {
                that.countdown = 60
                clearInterval(myInterval)
              }
            }, 1000)
          }).catch(() => {
            that.loadingEmailCode = false
            that.$nextTick(() => {
              that.$refs.username.focus()
            })
          })
        } else {
          console.error('validate error !!!')
          return false
        }
      })
    },
    /** 注册账号 */
    register() {
      this.registerRules.remark[0].required = true
      const that = this
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          that.loading = true
          // 密码加密
          const param = that.deepClone(that.registerForm)
          param.password = that.sha256(that.registerForm.password)
          param.confirmPassword = undefined
          register(param).then(res => {
            that.loading = false
            if (res) {
              that.msgSuccess('注册账号成功')
              that.$router.push(`/login`)
            } else {
              that.msgError('注册账号失败')
            }
          }).catch(() => {
            that.loading = false
          })
        } else {
          console.error('validate error !!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .register-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.register-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  background-image: url('~@/assets/bg.svg');
  background-repeat: no-repeat;
  background-position: 50%;
  background-size: 100%;

  label {
    padding: 0;
    line-height: 45px;
  }
  .el-input {
    display: inline-block;
    height: 40px;
    width: 85%;

    input {
      background: transparent;
      border: 0;
      -webkit-appearance: none;
      border-radius: 0;
      padding: 0;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
  .captcha {
    width: calc(100% - 165px);
    input{
      text-transform: uppercase;
    }
  }
  .login-form {
    position: relative;
    width: 460px;
    max-width: 100%;
    padding: 130px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .svg-container {
    padding: 0;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
    line-height: 43px;
  }

  .title-container {
    position: relative;

    .top {
      text-align: center;
      margin: 0 auto 20px auto;
      color: $light_gray;
    }
    .title {
      font-size: 26px;
      font-weight: bold;
    }
    .desc {
      font-size: 14px;
      opacity: 0.8;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
  .get-code {
    width: 130px;
    height: 50px;
    float: right;
    text-align: center;
    padding: 10px;
  }
}
</style>
