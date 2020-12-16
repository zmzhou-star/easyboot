<template>
  <div>
    <div class="user-info-head" @click="editCropper()">
      <img :src="options.img" title="点击上传头像" alt="头像找不到了" class="img-circle img-lg">
    </div>
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :auto-crop="options.autoCrop"
            :auto-crop-width="options.autoCropWidth"
            :auto-crop-height="options.autoCropHeight"
            :fixed-box="options.fixedBox"
            @realTime="realTime"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" alt="用户头像">
          </div>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              上传
              <em class="el-icon-upload el-icon--right" />
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)" />
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)" />
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()" />
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()" />
        </el-col>
        <el-col :lg="{span: 2, offset: 5}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 1}" :md="2">
          <el-button type="danger" size="small" @click="cancel">取 消</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import store from '@/store'
import { VueCropper } from 'vue-cropper'
import { uploadAvatar } from '@/api/system/user'

export default {
  components: { VueCropper },
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '修改头像',
      options: {
        img: store.getters.avatar, // 裁剪图片的地址
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 200, // 默认生成截图框宽度
        autoCropHeight: 200, // 默认生成截图框高度
        fixedBox: true // 固定截图框大小 不允许改变
      },
      previews: {}
    }
  },
  methods: {
    // 编辑头像
    editCropper() {
      this.open = true
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 覆盖默认的上传行为
    requestUpload() {
    },
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft()
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight()
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1
      this.$refs.cropper.changeScale(num)
    },
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf('image/') === -1) {
        this.msgError('文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。')
      } else {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => {
          this.options.img = reader.result
        }
      }
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        const formData = new FormData()
        formData.append('avatar', data)
        uploadAvatar(formData).then(response => {
          if (response) {
            this.open = false
            this.$store.dispatch('user/setAvatar', response.avatar).then(() => {
              this.options.img = store.getters.avatar
            })
            this.msgSuccess('修改成功')
          }
          this.$refs.cropper.clearCrop()
        })
      })
    },
    // 实时预览
    realTime(data) {
      this.previews = data
    }
  }
}
</script>
<style scoped lang="scss">
.user-info-head {
  position: relative;
  display: inline-block;
  min-width: 130px;
  min-height: 130px;
}

.user-info-head:hover:after {
  content: '+';
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 26px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 120px;
  border-radius: 50%;
}
</style>
