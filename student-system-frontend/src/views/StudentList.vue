<template>
  <div class="app-container">
    <!-- 1. 搜索栏 -->
    <div class="search-bar">
      <el-input v-model="searchName" placeholder="输入学生姓名搜索" style="width: 200px;" @clear="fetchData" clearable />
      <el-button type="primary" @click="fetchData">搜索</el-button>
      <el-button type="success" @click="handleAdd">新增学生</el-button>
    </div>

    <!-- 2. 数据表格 -->
    <el-table :data="tableData" border style="width: 100%; margin-top: 20px;">
      <el-table-column prop="studentNo" label="学号" width="120" />
      <el-table-column prop="name" label="姓名" width="100" />
      <el-table-column prop="gender" label="性别" width="80">
        <template #default="scope">
          {{ scope.row.gender === '1' ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
                    <el-upload
            style="display: inline-block; margin-right: 10px;"
            action="http://localhost:8080/api/file/upload"
            :show-file-list="false"
            :on-success="(res) => handleUploadSuccess(res, scope.row)"
            >
          <el-button size="small" type="warning">上传课件</el-button>
          </el-upload>
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
      <el-table-column label="课件" width="120">
        <template #default="scope">
            <el-link v-if="scope.row.courseFile" :href="scope.row.courseFile" target="_blank" type="primary">查看课件</el-link>
            <span v-else>未上传</span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 3. 分页组件 -->
    <el-pagination
      style="margin-top: 20px;"
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      @current-change="handlePageChange"
    />

    <!-- 4. 新增/修改对话框 (中等难度联动重点) -->
    <el-dialog v-model="dialogVisible" :title="form.id ? '修改学生' : '新增学生'">
      <el-form :model="form" label-width="80px">
        <el-form-item label="学号">
          <el-input v-model="form.studentNo" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option v-for="item in genderDict" :key="item.k" :label="item.v" :value="item.k" />
          </el-select>
        </el-form-item>
        
        <!-- 重点：级联选择实现院系专业班级联动 -->
        <el-form-item label="班级">
          <el-cascader
            v-model="orgValue"
            :options="orgOptions"
            :props="cascaderProps"
            placeholder="请选择 院系/专业/班级"
            @change="handleOrgChange"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

const API_BASE = 'http://localhost:8080/api'

// 数据状态
const tableData = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const searchName = ref('')
const dialogVisible = ref(false)
const genderDict = ref([])
const form = ref({ id: null, studentNo: '', name: '', gender: '', deptId: null, majorId: null, classId: null })

// 级联选择器配置
const orgValue = ref([])
const orgOptions = ref([])
const cascaderProps = {
  label: 'name',
  value: 'id',
  checkStrictly: false,
  // 动态加载逻辑
  lazy: true,
  lazyLoad(node, resolve) {
    const { level, value } = node
    axios.get(`${API_BASE}/common/org/${level === 0 ? 0 : value}`).then(res => {
      const nodes = res.data.data.map(item => ({
        id: item.id,
        name: item.name,
        leaf: level >= 2 // 到了第三层（班级）就是叶子节点了
      }))
      resolve(nodes)
    })
  }
}
//上传课件
const handleUploadSuccess = async (res, row) => {
  if (res.code === 200) {
    // 上传成功，将返回的 URL 绑定给该学生
    row.courseFile = res.data
    // 调用后端的 update 接口保存到数据库
    await axios.put(`${API_BASE}/student/update`, row)
    ElMessage.success('课件上传并关联成功')
    fetchData() // 刷新列表
  } else {
    ElMessage.error('上传失败')
  }
}
// 1. 获取列表数据
const fetchData = async () => {
  const res = await axios.get(`${API_BASE}/student/list`, {
    params: { current: currentPage.value, size: pageSize.value, name: searchName.value }
  })
  tableData.value = res.data.data.records
  total.value = res.data.data.total
}

// 2. 获取性别字典
const fetchDict = async () => {
  const res = await axios.get(`${API_BASE}/common/dict/gender`)
  genderDict.value = res.data.data
}

// 分页处理
const handlePageChange = (val) => {
  currentPage.value = val
  fetchData()
}

// 级联选择处理
const handleOrgChange = (val) => {
  form.value.deptId = val[0]
  form.value.majorId = val[1]
  form.value.classId = val[2]
}

// 新增/编辑/删除逻辑
const handleAdd = () => {
  form.value = { id: null, studentNo: '', name: '', gender: '', deptId: null, majorId: null, classId: null }
  orgValue.value = []
  dialogVisible.value = true
}

const handleEdit = (row) => {
  form.value = { ...row }
  orgValue.value = [row.deptId, row.majorId, row.classId] // 回显级联选择器
  dialogVisible.value = true
}

const submitForm = async () => {
  const url = form.value.id ? `${API_BASE}/student/update` : `${API_BASE}/student/add`
  const method = form.value.id ? 'put' : 'post'
  const res = await axios[method](url, form.value)
  if (res.data.code === 200) {
    ElMessage.success('保存成功')
    dialogVisible.value = false
    fetchData()
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定删除该学生吗？', '提示', { type: 'warning' }).then(async () => {
    const res = await axios.delete(`${API_BASE}/student/delete/${id}`)
    if (res.data.code === 200) {
      ElMessage.success('删除成功')
      fetchData()
    }
  })
}

onMounted(() => {
  fetchData()
  fetchDict()
})
</script>

<style scoped>
.app-container { padding: 20px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 20px; }
</style>