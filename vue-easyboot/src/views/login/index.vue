<template>
  <div class="login-container">
    <github-corner class="github-corner" />
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="top title">Easy Boot</h3>
        <div class="top desc">企业级前端解决方案</div>
      </div>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @mousedown="showPwd" @mouseup="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item prop="code">
        <span class="svg-container">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </span>
        <el-input
          ref="code"
          v-model="loginForm.code"
          auto-complete="off"
          maxlength="4"
          placeholder="验证码"
          class="captcha"
          @keyup.enter.native="handleLogin"
        />
        <div class="login-code">
          <img :src="codeUrl" alt="验证码" @click="getCode">
        </div>
      </el-form-item>
      <div>
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0 0 25px 0;">记住密码</el-checkbox>
        <router-link to="/forgetPassword" class="link-type2" style="font-size: 14px;float: right">
          <span>忘记密码</span>
        </router-link>
      </div>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">Login</el-button>
      <div style="line-height: 26px;height: 26px;">
        <div class="third-party" @click="thirdTips">
          <span>第三方账号登录</span>
          <svg-icon icon-class="wechat1" />
          <svg-icon icon-class="qq1" />
        </div>
        <router-link to="/register" class="link-type2" style="font-size: 14px;float: right">
          <span>注册账号</span>
        </router-link>
      </div>
      <div class="tips">
        <span style="margin-right:20px;">username: guest</span>
        <span> password: Guest.132</span>
      </div>
    </el-form>
    <div class="beian-wrap">
      <a class="beian-link" href="https://beian.miit.gov.cn/#/Integrated/index?unitName=湘ICP备2021013044号" target="_blank">
        <img data-src="//img.alicdn.com/tfs/TB1..50QpXXXXX7XpXXXXXXXXXX-40-40.png" class="beian-img" src="//img.alicdn.com/tfs/TB1..50QpXXXXX7XpXXXXXXXXXX-40-40.png" alt="">
        <span class="beian-link-text">湘ICP备2021013044号</span>
      </a>
      <a class="beian-link" href="https://gitee.com/zmzhou-star/easyboot" target="_blank">
        <span class="beian-Copyright-text">Copyright © 2020-present zmzhou-star. All Rights Reserved.</span>
      </a>
    </div>
  </div>
</template>

<script>
import { getCaptcha } from '@/api/login'
import Cookies from 'js-cookie'
import GithubCorner from '@/components/GithubCorner'

export default {
  name: 'Login',
  components: {
    GithubCorner
  },
  data() {
    return {
      codeUrl: '',
      loginForm: {
        username: 'guest',
        password: 'Guest.132',
        rememberMe: true,
        code: '',
        uuid: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: '用户名不能为空' }],
        password: [{ required: true, trigger: 'blur', message: '密码不能为空' }],
        code: [{ required: true, trigger: 'change', message: '验证码不能为空' }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  mounted() {
    this.$refs.code.focus()
  },
  created() {
    this.getCookie()
    this.getCode()
  },
  methods: {
    getCode() {
      getCaptcha().then(res => {
        // 验证码和uuid
        this.codeUrl = res.code
        this.loginForm.uuid = res.uuid
      })
    },
    getCookie() {
      const username = Cookies.get('username')
      const password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : password,
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
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
    thirdTips() {
      this.msgInfo('程序猿正在加班开发中……')
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set('username', this.loginForm.username, { expires: 30 })
            Cookies.set('password', this.loginForm.password, { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove('username')
            Cookies.remove('password')
            Cookies.remove('rememberMe')
          }
          const params = this.deepClone(this.loginForm)
          params.password = this.sha256(this.loginForm.password)
          this.$store.dispatch('user/login', params).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
            this.loginForm.code = ''
            this.getCode()
          })
        } else {
          this.getCode()
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
.beian-wrap img, .beian-wrap span {
  vertical-align: middle;
  display: inline-block;
}
.beian-wrap {
  bottom: 10px;
  position: absolute;
  width: 100%;
  text-align: center;
}
.beian-img {
  width: 20px;
  height: 20px;
}
.beian-link-text {
  color: white;
  margin: 0 15px;
}
.beian-Copyright-text {
  color: white;
}
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
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
    width: calc(100% - 160px);
    input{
      text-transform: uppercase;
    }
  }
  .login-code {
      width: 120px;
      height: 50px;
      float: right;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  background-image: url('~@/assets/bg.svg');
  background-repeat: no-repeat;
  background-position: 50%;
  background-size: 100%;

  .login-form {
    position: relative;
    width: 460px;
    max-width: 100%;
    padding: 130px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .third-party {
    float: left;
    font-size: 14px;
    span {
      color: $light_gray;
    }
    svg {
      margin-left: 15px;
      font-size: 26px;
      cursor: pointer;
    }
  }
  .tips {
    font-size: 14px;
    color: $dark_gray;
    margin: 15px 0;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
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
}
</style>
