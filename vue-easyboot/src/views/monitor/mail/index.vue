<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="邮件主题" prop="subject">
        <el-input
          v-model="queryParams.subject"
          placeholder="请输入邮件主题"
          clearable
          size="small"
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small" style="width: 150px">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发送时间" prop="sendDate">
        <el-date-picker
          v-model="queryParams.sendDate"
          clearable
          size="small"
          style="width: 200px"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择发送时间"
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
          v-hasPermi="['monitor:mail:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:mail:edit']"
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:mail:remove']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:mail:export']"
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
      :data="mailList"
      element-loading-text="Loading"
      stripe
      height="465"
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" label="序号" width="60">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="邮件发送人" align="center" prop="from" :show-overflow-tooltip="true" />
      <el-table-column label="邮件接收人" align="center" prop="to" :show-overflow-tooltip="true" />
      <el-table-column label="邮件主题" align="center" prop="subject" :show-overflow-tooltip="true" />
      <el-table-column label="邮件内容" align="center" prop="text" :show-overflow-tooltip="true" />
      <el-table-column label="抄送" align="center" prop="cc" :show-overflow-tooltip="true" />
      <el-table-column label="密送" align="center" prop="bcc" :show-overflow-tooltip="true" />
      <el-table-column class-name="status-col" label="状态" width="110" prop="status" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ statusFormat(scope.row) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="报错信息" align="center" prop="error" :show-overflow-tooltip="true" />
      <el-table-column label="发送时间" align="center" prop="sendDate" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['monitor:mail:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['monitor:mail:remove']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加或修改系统邮件记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="邮件发送人" prop="from">
          <el-input v-model="form.from" placeholder="请输入邮件发送人" />
        </el-form-item>
        <el-form-item label="邮件接收人" prop="to">
          <el-input v-model="form.to" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="邮件主题" prop="subject">
          <el-input v-model="form.subject" placeholder="请输入邮件主题" />
        </el-form-item>
        <el-form-item label="邮件内容" prop="text">
          <el-input v-model="form.text" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="抄送" prop="cc">
          <el-input v-model="form.cc" placeholder="请输入抄送" />
        </el-form-item>
        <el-form-item label="密送" prop="bcc">
          <el-input v-model="form.bcc" placeholder="请输入密送" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="报错信息" prop="error">
          <el-input v-model="form.error" placeholder="请输入报错信息" />
        </el-form-item>
        <el-form-item label="发送时间" prop="sendDate">
          <el-date-picker
            v-model="form.sendDate"
            clearable
            size="small"
            style="width: 200px"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择发送时间"
          />
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
import { listMail, getMail, delMail, exportMail } from '@/api/monitor/mail'

export default {
  name: 'Mail',
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
      // 系统邮件记录表格数据
      mailList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        to: null,
        subject: null,
        status: null,
        sendDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
    this.getDicts('sys_common_status').then(response => {
      this.statusOptions = response
    })
  },
  methods: {
    /** 查询系统邮件记录列表 */
    getList() {
      this.loading = true
      listMail(this.queryParams).then(response => {
        this.mailList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status)
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
        from: null,
        to: null,
        subject: null,
        text: null,
        cc: null,
        bcc: null,
        status: null,
        error: null,
        sendDate: null
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
      this.title = '添加系统邮件记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMail(id).then(response => {
        this.form = response

        this.open = true
        this.title = '修改系统邮件记录'
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除系统邮件记录编号为"' + ids + '"的数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delMail(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      queryParams.excelName = '系统邮件记录'
      this.$confirm('是否确认导出所有系统邮件记录数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportMail(queryParams)
      }).then(response => {
        this.download(response)
      })
    }
  }
}
</script>
