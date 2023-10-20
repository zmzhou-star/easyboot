<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入姓名"
          clearable
          size="small"
          style="width: 250px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="tel">
        <el-input
          v-model="queryParams.tel"
          placeholder="请输入手机号码"
          clearable
          size="small"
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['medicalrecord:patient:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['medicalrecord:patient:edit']"
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['medicalrecord:patient:remove']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['medicalrecord:patient:export']"
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table
      v-loading="loading"
      :data="patientList"
      element-loading-text="Loading"
      stripe
      height="465"
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="病人ID" align="center" prop="id" />
      <el-table-column label="姓名" align="center" prop="userName" />
      <el-table-column label="手机号码" align="center" prop="tel" />
      <el-table-column label="性别" align="center" prop="sex" :formatter="sexFormat" />
      <el-table-column label="出生日期" align="center" prop="birthDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出生地" align="center" prop="birthplace" />
      <el-table-column label="常住地址" align="center" prop="permanentAddress" />
      <el-table-column label="职业" align="center" prop="career" />
      <el-table-column label="婚育史" align="center" prop="marriage" />
      <el-table-column label="主诉" align="center" prop="chiefComplaint" />
      <el-table-column label="治法方药" align="center" prop="medicines" />
      <el-table-column label="治法方药" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['medicalrecord:patient:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['medicalrecord:patient:remove']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >复诊</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改病人信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="手机号码" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入手机号码" maxlength="11"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in sexOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker
            v-model="form.birthDate"
            clearable
            size="small"
            style="width: 200px"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期"
          />
        </el-form-item>
        <el-form-item label="出生地" prop="birthplace">
          <el-input v-model="form.birthplace" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="常住地址" prop="permanentAddress">
          <el-input v-model="form.permanentAddress" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="职业" prop="career">
          <el-input v-model="form.career" placeholder="请输入职业" />
        </el-form-item>
        <el-form-item label="婚育史" prop="marriage">
          <el-input v-model="form.marriage" placeholder="请输入婚育史" />
        </el-form-item>
        <el-form-item label="主诉" prop="chiefComplaint">
          <el-input v-model="form.chiefComplaint" type="textarea" placeholder="请输入内容" rows="5" :autosize="{ minRows: 5, maxRows: 10}"/>
        </el-form-item>
        <el-form-item label="治法方药" prop="medicines">
          <el-input v-model="form.medicines" type="textarea" placeholder="请输入内容" :autosize="{ minRows: 5, maxRows: 10}"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPatient, getPatient, delPatient, addPatient, updatePatient, exportPatient } from '@/api/medicalrecord/patient'

export default {
  name: 'Patient',
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        0: 'danger'
      }
      return statusMap[status]
    }
  },
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 病人信息表格数据
      patientList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 性别字典
      sexOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        tel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }
        ],
        permanentAddress: [
          { required: true, message: '常住地址不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getDicts('sys_user_sex').then(response => {
      this.sexOptions = response
    })
  },
  methods: {
    /** 查询病人信息列表 */
    getList() {
      this.loading = true
      listPatient(this.queryParams).then(response => {
        this.patientList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 性别字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userName: null,
        tel: null,
        sex: null,
        birthDate: null,
        birthplace: null,
        permanentAddress: null,
        career: null,
        marriage: null,
        chiefComplaint: null,
        medicines: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加病人信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPatient(id).then(response => {
        this.form = response
        this.open = true
        this.title = '修改病人信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePatient(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPatient(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除病人信息编号为"' + ids + '"的数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delPatient(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      queryParams.excelName = '病人信息'
      this.$confirm('是否确认导出所有病人信息数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportPatient(queryParams)
      }).then(response => {
        this.download(response)
      })
    }
  }
}
</script>
