<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="成果编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入成果 ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成果标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入成果标题"
          clearable
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mei:achievement:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mei:achievement:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mei:achievement:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mei:achievement:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="achievementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="成果 ID" align="center" prop="id" />
      <el-table-column label="所属项目" align="center">
        <template slot-scope="scope">
          {{ projectMap[scope.row.projectId] || scope.row.projectId }}
        </template>
      </el-table-column>
      <el-table-column label="成果标题" align="center" prop="title" />
      <el-table-column label="封面路径" align="center" prop="cover" />
      <el-table-column label="成果详情" align="center" prop="content" />
      <el-table-column label="展示状态" align="center" prop="isShow">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isShow === '1' ? 'success' : 'info'">
            {{ scope.row.isShow === '1' ? '展示中' : '已隐藏' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mei:achievement:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mei:achievement:remove']"
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

    <!-- 添加或修改成果对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="成果标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入成果标题" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="关联项目ID" prop="projectId">
              <el-input v-model="form.projectId" placeholder="请输入关联项目ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="封面路径" prop="cover">
              <file-upload v-model="form.cover"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="是否展示" prop="isShow">
              <el-select v-model="form.isShow" placeholder="请选择" style="width:100%">
                <el-option label="展示" value="1" />
                <el-option label="隐藏" value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="成果详情">
              <editor v-model="form.content" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAchievement, getAchievement, delAchievement, addAchievement, updateAchievement } from "@/api/mei/achievement"
import { listProject } from "@/api/mei/project"

export default {
  name: "Achievement",
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
      // 成果表格数据
      achievementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        projectId: null,
        title: null,
        cover: null,
        content: null,
        isShow: null,
      },
      // 表单参数
      form: {},
      projectMap: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "成果 ID不能为空", trigger: "blur" }
        ],
        projectId: [
          { required: true, message: "关联项目 ID不能为空", trigger: "change" }
        ],
        title: [
          { required: true, message: "成果标题不能为空", trigger: "blur" }
        ],
        cover: [
          { required: true, message: "封面路径不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "成果详情不能为空", trigger: "blur" }
        ],
        isShow: [
          { required: true, message: "是否展示不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
    this.loadProjects()
  },
  methods: {
    /** 查询成果列表 */
    getList() {
      this.loading = true
      listAchievement(this.queryParams).then(response => {
        this.achievementList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 加载项目列表用于关联显示 */
    loadProjects() {
      listProject({ pageSize: 1000 }).then(response => {
        const map = {}
        ;(response.rows || []).forEach(item => {
          map[item.id] = item.projectName
        })
        this.projectMap = map
      })
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
        projectId: null,
        title: null,
        cover: null,
        content: null,
        isShow: null,
        createTime: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
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
      this.open = true
      this.title = "添加成果"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAchievement(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改成果"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAchievement(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAchievement(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
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
      this.$modal.confirm('是否确认删除成果编号为"' + ids + '"的数据项？').then(function() {
        return delAchievement(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mei/achievement/export', {
        ...this.queryParams
      }, `achievement_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
