<template>
  <div class="login-container">
    <el-form v-if="step === 1" ref="emailCodeForm" :model="emailCodeForm" :rules="emailCodeRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="top title">忘记密码</h3>
      </div>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="emailCodeForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="email">
        <span class="svg-container">
          <svg-icon icon-class="email" />
        </span>
        <el-input
          ref="email"
          v-model="emailCodeForm.email"
          placeholder="邮箱"
          name="email"
          type="text"
          tabindex="2"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="code">
        <span class="svg-container">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </span>
        <el-input
          v-model="emailCodeForm.code"
          auto-complete="off"
          maxlength="6"
          type="text"
          tabindex="3"
          placeholder="邮箱验证码"
          class="captcha"
          @keyup.enter.native="checkEmailCode"
        />
        <el-button v-if="countdown !== 60" type="info" class="get-code">{{ countdown }}s</el-button>
        <el-button v-if="countdown === 60" :loading="loadingEmailCode" type="primary" class="get-code" @click.native.prevent="getEmailCode">点击获取验证码</el-button>
      </el-form-item>
      <div style="margin:0 0 25px 0;">
        <router-link to="/login" class="link-type2" style="font-size: 14px;">
          <span>返回登录</span>
        </router-link>
      </div>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="checkEmailCode">验证</el-button>
    </el-form>
    <!-- 重置密码 -->
    <el-form v-if="step === 2" ref="resetForm" :model="resetForm" :rules="resetFormRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="top title">重置密码</h3>
      </div>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="resetForm.password"
          :type="passwordType"
          placeholder="新密码"
          name="password"
          tabindex="1"
          minlength="6"
          maxlength="20"
          auto-complete="on"
        />
        <span class="show-pwd" @mousedown="showPwd" @mouseup="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="confirmPassword"
          v-model="resetForm.confirmPassword"
          :type="passwordType"
          placeholder="确认密码"
          name="confirmPassword"
          tabindex="2"
          minlength="6"
          maxlength="20"
          auto-complete="on"
        />
        <span class="show-pwd" @mousedown="showPwd" @mouseup="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <div style="margin:0 0 25px 0;">
        <router-link to="/login" class="link-type2" style="font-size: 14px;">
          <span>返回登录</span>
        </router-link>
      </div>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="resetPwd">重置密码</el-button>
    </el-form>
  </div>
</template>

<script>
import { getEmailCode, checkEmailCode, resetPwd } from '@/api/nonAuth'

export default {
  name: 'ForgetPassword',
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.resetForm.password !== value) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      step: 1,
      countdown: 60,
      emailCodeForm: {
        username: '',
        email: '',
        code: '',
        uuid: ''
      },
      resetForm: {
        password: '',
        confirmPassword: ''
      },
      emailCodeRules: {
        username: [{ required: true, trigger: 'blur', message: '用户名不能为空' }],
        email: [{ required: true, message: '邮箱地址不能为空', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }],
        code: [{ required: false, trigger: 'change', message: '邮箱验证码不能为空' }]
      },
      resetFormRules: {
        password: [
          { required: true, message: '新密码不能为空', trigger: 'blur' },
          { pattern: /^.*(?=.{6,16})(?=.*[A-Za-z]{2,})(?=.*\d)(?=.*[!@#$%^&*?\.\(\)]).*$/,
            min: 6, max: 20, message: '密码必需由字母、数字和特殊字符组成，长度在6~20个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '确认密码不能为空', trigger: 'blur' },
          { required: true, validator: equalToPassword, trigger: 'blur' }
        ]
      },
      passwordType: 'password',
      loading: false,
      loadingEmailCode: false
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
    getEmailCode() {
      const that = this
      this.emailCodeRules.code[0].required = false
      this.$refs.emailCodeForm.validate(valid => {
        if (valid) {
          that.loadingEmailCode = true
          getEmailCode(that.emailCodeForm).then(res => {
            if (!res) {
              this.msgError('验证码发送失败')
              return
            }
            that.emailCodeForm.uuid = res
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
          })
        } else {
          console.error('validate error !!!')
          return false
        }
      })
    },
    /** 验证用户名邮箱验证码 */
    checkEmailCode() {
      this.emailCodeRules.code[0].required = true
      const that = this
      this.$refs.emailCodeForm.validate(valid => {
        if (valid) {
          that.loading = true
          checkEmailCode(that.emailCodeForm).then(res => {
            that.loading = false
            // 验证通过
            if (res) {
              that.step = 2
            }
          }).catch(() => {
            that.loading = false
          })
        } else {
          console.error('validate error !!!')
          return false
        }
      })
    },
    /** 重置密码 */
    resetPwd() {
      const that = this
      this.$refs.resetForm.validate(valid => {
        if (valid) {
          that.loading = true
          resetPwd(that.emailCodeForm.uuid, that.sha256(that.resetForm.password)).then(res => {
            that.loading = false
            if (res) {
              this.msgSuccess('重置密码成功')
              this.$router.push(`/login`)
            } else {
              this.msgError('重置密码失败')
            }
          }).catch(() => {
            that.loading = false
            this.msgError('重置密码失败')
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
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  background-image: url('~@/assets/bg.svg');
  background-repeat: no-repeat;
  background-position: 50%;
  background-size: 100%;

  .el-input {
    display: inline-block;
    height: 40px;
    width: 85%;

    input {
      background: transparent;
      border: 0;
      -webkit-appearance: none;
      border-radius: 0;
      padding: 12px 5px 12px 15px;
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
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
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
