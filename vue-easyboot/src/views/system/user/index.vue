<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="用户名称" prop="username">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入用户名称"
          clearable
          size="small"
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="tel">
        <el-input
          v-model="queryParams.tel"
          placeholder="请输入手机号码"
          clearable
          maxlength="11"
          size="small"
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="用户状态"
          clearable
          size="small"
          style="width: 150px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:user:add']" type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:user:edit']" type="success" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:user:remove']" type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:user:import']" type="info" icon="el-icon-upload2" size="mini" @click="handleImport">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:user:export']" type="warning" icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="listLoading"
      :data="data.rows"
      element-loading-text="Loading"
      stripe
      height="465"
      fit
      highlight-current-row
      @sort-change="sortChange"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="45" align="center" />
      <el-table-column align="center" label="序号" width="60">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="用户名称" align="center" prop="username" :show-overflow-tooltip="true" width="100" sortable="custom" />
      <el-table-column label="用户昵称" align="center" prop="nickName" :show-overflow-tooltip="true" />
      <el-table-column label="手机号码" align="center" prop="tel" :show-overflow-tooltip="true" width="110" />
      <el-table-column label="邮箱" align="center" prop="email" :show-overflow-tooltip="true" width="120" />
      <el-table-column label="性别" align="center" prop="sex" :show-overflow-tooltip="true" width="70" sortable="custom">
        <template slot-scope="scope">
          <div>{{ scope.row.sex == 1 ? '男' : scope.row.sex == 0 ? '女' : '未知' }}</div>
        </template>
      </el-table-column>
      <!-- <el-table-column class-name="status-col" label="Status" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column label="状态" align="center" prop="status" sortable="custom">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" inactive-value="0" active-value="1" @change="handleStatusChange(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="最后登录IP" align="center" prop="loginIp" :show-overflow-tooltip="true" width="110" />
      <el-table-column align="center" prop="loginDate" label="最后登录时间" width="165" sortable="custom">
        <template slot-scope="scope">
          <em class="el-icon-time" />
          <span>{{ scope.row.loginDate }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="createTime" label="创建时间" width="155" sortable="custom" />
      <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-hasPermi="['system:user:edit']" size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button v-hasPermi="['system:user:remove']" v-if="scope.row.id !== 1" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button v-hasPermi="['system:user:resetPwd']" size="mini" type="text" icon="el-icon-key" @click="handleResetPwd(scope.row)">重置</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="data.total>0"
      :total="data.total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchData"
    />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.id === undefined" label="用户名称" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.id === undefined" label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.id !== 1" label="角色">
              <el-select v-model="form.roles" multiple placeholder="请选择">
                <el-option
                  v-for="r in roleOptions"
                  :key="r.value"
                  :value="r.value"
                  :label="r.label"
                  :disabled="r.value === '1'"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="500px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?isUpdate=' + upload.isUpdate"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <em class="el-icon-upload" />
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div slot="tip" class="el-upload__tip">
          <el-checkbox v-model="upload.isUpdate" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px;font-weight: bold" @click="excelTemplate">下载模板</el-link>
        </div>
        <div slot="tip" class="el-upload__tip" style="color:red">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList, getOne, del, add, update, exportUser, resetPwd, changeUserStatus, excelTemplate } from '@/api/system/user'
import { getToken } from '@/utils/auth'

export default {
  name: 'User',
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 数据,总条数
      data: { rows: null, total: 0 },
      listLoading: true,
      // 日期范围
      dateRange: [],
      roleOptions: [],
      // 用户状态字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: undefined,
        tel: undefined,
        status: undefined
      },
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {},
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        isUpdate: 0,
        // 设置上传的请求头部
        headers: { Authorization: getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + 'system/user/importExcel'
      },
      // 表单校验
      rules: {
        username: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' },
          { pattern: /^[a-zA-Z\d]{4,20}$/,
            min: 4, max: 20, message: '必需由字母和数字组成，长度在4~20个字符', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' },
          { pattern: /^.*(?=.{6,16})(?=.*[A-Za-z]{2,})(?=.*\d)(?=.*[!@#$%^&*?\.\(\)]).*$/,
            min: 6, max: 20, message: '密码必需由字母、数字和特殊字符组成，长度在6~20个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
          {
            type: 'email',
            message: "'请输入正确的邮箱地址",
            trigger: ['blur', 'change']
          }
        ],
        tel: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created() {
    this.fetchData()
    this.getDicts('sys_normal_disable').then(response => {
      this.statusOptions = response
    })
    this.getDicts('sys_user_sex').then(response => {
      this.sexOptions = response
    })
    this.getConfigKey('sys.user.initPassword').then(response => {
      this.initPassword = response
    })
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        // 用户表格数据
        this.data = response
        this.listLoading = false
      })
    },
    // 用户状态修改
    handleStatusChange(row) {
      const text = row.status === '1' ? '启用' : '停用'
      this.$confirm('确认要"' + text + '""' + row.username + '"用户吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return changeUserStatus(row.id, row.status)
      }).then(() => {
        this.msgSuccess(text + '成功')
        this.fetchData()
      }).catch(function() {
        row.status = row.status === '0' ? '1' : '0'
      })
    },
    sortChange(sort) {
      this.queryParams.prop = sort.prop
      this.queryParams.order = sort.order
      this.fetchData()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.fetchData()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        username: undefined,
        nickName: undefined,
        tel: undefined,
        email: undefined,
        password: undefined,
        sex: undefined,
        roles: undefined,
        status: '1',
        remark: undefined
      }
      this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      getOne().then(response => {
        this.open = true
        this.title = '添加用户'
        this.form.password = this.initPassword
        this.roleOptions = response.roleList
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const rowId = row.id || this.ids
      getOne(rowId).then(response => {
        this.form = response
        this.open = true
        this.title = '修改用户'
        this.form.password = ''
        this.roleOptions = response.roleList
      })
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.username + '"的新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        resetPwd(row.id, this.sha256(value)).then(response => {
          if (response) {
            this.msgSuccess('修改成功，新密码是：' + value)
          } else {
            this.msgError('修改密码失败')
          }
        })
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== undefined) {
            update(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.fetchData()
            }).catch(err => {
              console.error(err)
            })
          } else {
            const params = this.deepClone(this.form)
            params.password = this.sha256(this.form.password)
            add(params).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.fetchData()
            }).catch(err => {
              console.error(err)
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除用户编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return del(ids)
      }).then(() => {
        this.fetchData()
        this.msgSuccess('删除成功')
      }).catch(function() {})
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      queryParams.excelName = '用户管理'
      this.$confirm('是否确认导出所有用户数据项?', '操作提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportUser(queryParams)
      }).then(response => {
        this.download(response)
      }).catch(function(e) {
        console.log(e)
      })
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '用户导入'
      this.upload.open = true
    },

    /** 下载模板操作 */
    excelTemplate() {
      excelTemplate().then(response => {
        this.download(response)
      })
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert(response.data || response.msg, '导入结果', { dangerouslyUseHTMLString: true })
      this.fetchData()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    }
  }
}
</script>
