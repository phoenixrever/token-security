<template>
  <el-dialog
    :title="!dataForm.menuId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="上级菜单ID" prop="pid">
      <el-input v-model="dataForm.pid" placeholder="上级菜单ID"></el-input>
    </el-form-item>
    <el-form-item label="子菜单数目" prop="subCount">
      <el-input v-model="dataForm.subCount" placeholder="子菜单数目"></el-input>
    </el-form-item>
    <el-form-item label="菜单类型" prop="type">
      <el-input v-model="dataForm.type" placeholder="菜单类型"></el-input>
    </el-form-item>
    <el-form-item label="菜单标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="菜单标题"></el-input>
    </el-form-item>
    <el-form-item label="组件名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="组件名称"></el-input>
    </el-form-item>
    <el-form-item label="组件" prop="component">
      <el-input v-model="dataForm.component" placeholder="组件"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="menuSort">
      <el-input v-model="dataForm.menuSort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="图标" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="图标"></el-input>
    </el-form-item>
    <el-form-item label="链接地址" prop="path">
      <el-input v-model="dataForm.path" placeholder="链接地址"></el-input>
    </el-form-item>
    <el-form-item label="是否外链" prop="iFrame">
      <el-input v-model="dataForm.iFrame" placeholder="是否外链"></el-input>
    </el-form-item>
    <el-form-item label="缓存" prop="cache">
      <el-input v-model="dataForm.cache" placeholder="缓存"></el-input>
    </el-form-item>
    <el-form-item label="隐藏" prop="hidden">
      <el-input v-model="dataForm.hidden" placeholder="隐藏"></el-input>
    </el-form-item>
    <el-form-item label="权限" prop="permission">
      <el-input v-model="dataForm.permission" placeholder="权限"></el-input>
    </el-form-item>
    <el-form-item label="创建者" prop="createBy">
      <el-input v-model="dataForm.createBy" placeholder="创建者"></el-input>
    </el-form-item>
    <el-form-item label="更新者" prop="updateBy">
      <el-input v-model="dataForm.updateBy" placeholder="更新者"></el-input>
    </el-form-item>
    <el-form-item label="创建日期" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建日期"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          menuId: 0,
          pid: '',
          subCount: '',
          type: '',
          title: '',
          name: '',
          component: '',
          menuSort: '',
          icon: '',
          path: '',
          iFrame: '',
          cache: '',
          hidden: '',
          permission: '',
          createBy: '',
          updateBy: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          pid: [
            { required: true, message: '上级菜单ID不能为空', trigger: 'blur' }
          ],
          subCount: [
            { required: true, message: '子菜单数目不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '菜单类型不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '菜单标题不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '组件名称不能为空', trigger: 'blur' }
          ],
          component: [
            { required: true, message: '组件不能为空', trigger: 'blur' }
          ],
          menuSort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '图标不能为空', trigger: 'blur' }
          ],
          path: [
            { required: true, message: '链接地址不能为空', trigger: 'blur' }
          ],
          iFrame: [
            { required: true, message: '是否外链不能为空', trigger: 'blur' }
          ],
          cache: [
            { required: true, message: '缓存不能为空', trigger: 'blur' }
          ],
          hidden: [
            { required: true, message: '隐藏不能为空', trigger: 'blur' }
          ],
          permission: [
            { required: true, message: '权限不能为空', trigger: 'blur' }
          ],
          createBy: [
            { required: true, message: '创建者不能为空', trigger: 'blur' }
          ],
          updateBy: [
            { required: true, message: '更新者不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建日期不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.menuId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.menuId) {
            this.$http({
              url: this.$http.adornUrl(`/securityuaa/menu/info/${this.dataForm.menuId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.pid = data.menu.pid
                this.dataForm.subCount = data.menu.subCount
                this.dataForm.type = data.menu.type
                this.dataForm.title = data.menu.title
                this.dataForm.name = data.menu.name
                this.dataForm.component = data.menu.component
                this.dataForm.menuSort = data.menu.menuSort
                this.dataForm.icon = data.menu.icon
                this.dataForm.path = data.menu.path
                this.dataForm.iFrame = data.menu.iFrame
                this.dataForm.cache = data.menu.cache
                this.dataForm.hidden = data.menu.hidden
                this.dataForm.permission = data.menu.permission
                this.dataForm.createBy = data.menu.createBy
                this.dataForm.updateBy = data.menu.updateBy
                this.dataForm.createTime = data.menu.createTime
                this.dataForm.updateTime = data.menu.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/securityuaa/menu/${!this.dataForm.menuId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'menuId': this.dataForm.menuId || undefined,
                'pid': this.dataForm.pid,
                'subCount': this.dataForm.subCount,
                'type': this.dataForm.type,
                'title': this.dataForm.title,
                'name': this.dataForm.name,
                'component': this.dataForm.component,
                'menuSort': this.dataForm.menuSort,
                'icon': this.dataForm.icon,
                'path': this.dataForm.path,
                'iFrame': this.dataForm.iFrame,
                'cache': this.dataForm.cache,
                'hidden': this.dataForm.hidden,
                'permission': this.dataForm.permission,
                'createBy': this.dataForm.createBy,
                'updateBy': this.dataForm.updateBy,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
