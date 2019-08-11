<template>
  <div>
    <el-row style="text-align: left">
      <el-col>
        <el-form :inline="true" :model="certsForm" ref="certsFormRef">
          <el-form-item label="证书名称:" prop="certsName">
            <el-input v-model="certsForm.certsName" :maxLength="32" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findCertsList(1)">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('certsFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateCertsDialog">创建证书</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :border="true" :data="certsList" highlight-current-row v-loading="listLoading" fit>
            <el-table-column prop="certsName" label="证书名称" min-width="180"></el-table-column>
            <el-table-column prop="certsDesc" label="证书说明" min-width="220"></el-table-column>
            <el-table-column label="操作" min-width="180">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="showCertsExampleDialog(scope.$index, scope.row)">
                  查看样图
                </el-button>
                <el-button type="primary" size="mini" @click="showEditCertsDialog(scope.$index, scope.row)">
                  修改
                </el-button>
                <el-button type="primary" size="mini" @click="delCerts(scope.$index, scope.row)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-col>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </el-col>
        </div>
      </el-col>
    </el-row>

    <!--证书新增及编辑界面-->
    <el-dialog :title="title" :visible.sync="createOrEditCertsFormVisible" @closed="clearUploadShowImageUrlAndValidate"
               :close-on-click-modal="false">
      <el-form :model="createOrEditCertsForm" label-width="120px" :rules="createOrEditCertsFormRules"
               ref="createOrEditCertsFormRef">
        <el-form-item label="证书名称:" prop="certsName">
          <el-input placeholder="限32字内" :maxlength="32" v-model="createOrEditCertsForm.certsName"></el-input>
        </el-form-item>
        <el-form-item label="证书说明:" prop="desc">
          <el-input type="textarea" :rows="3" :cols="80" placeholder="限100字内" :maxlength="100"
                    v-model="createOrEditCertsForm.certsDesc"></el-input>
        </el-form-item>
        <el-form-item label="证书样图" prop="picUrl" style="text-align: left">
          <el-upload
            style="margin:0px;height:200px;"
            class="avatar-uploader"
            :action="uploadImageUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="uploadShowImageUrl" :src="uploadShowImageUrl" class="avatar">
            <i v-else class="el-upload el-icon-plus avatar-uploader-icon"></i>
            <div slot="tip" style="color: red" class="el-upload__tip">
              只能上传长:1754px 宽:1238px的jpg文件，且不超过3MB,可以为空,将使用默认图片
            </div>
          </el-upload>
          <el-input type="hidden" style="width:0px;height:0px;" v-model="createOrEditCertsForm.picUrl"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="primary" @click.native="submitCreateOrEditCerts" :loading="addLoading">保存</el-button>
        <el-button @click.native="cancelCreateOrEditCerts">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="样图预览" :visible.sync="showPdfDialogVisible">
      <iframe :src="'/onlineExam/b2cView/static/pdf/web/viewer.html?file=' + pdfFileUrl" height="560px"
              width="100%"></iframe>
    </el-dialog>
  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import config from '../../config'

  export default {
    data() {
      return {
        showPdfDialogVisible: false,
        pdfFileUrl: '',
        uploadShowImageUrl: '',
        certsList: [],
        certsForm: {
          certsName: ''
        },
        title: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        listLoading: false,
        createOrEditCertsForm: {
          id: null,
          certsName: '',
          certsDesc: '',
          picUrl: ''
        },
        createOrEditCertsFormVisible: false,
        addLoading: false,
        createOrEditCertsFormRules: {
          certsName: [
            {required: true, message: '证书名称不能为空', trigger: 'blur'}
          ],
        },
        uploadImageUrl: config.BASE_PATH + config.APP_CONTEXT + config.UPLOAD_FILE,
        createCerts: true,
        validImg: true,
      }
    },
    methods: {
      handleAvatarSuccess(res, file) {
        if (!res.success) {
          this.$message({message: '上传失败,请稍后再试', type: 'warning'})
          return
        }
        this.$refs['createOrEditCertsFormRef'].clearValidate('picUrl')
        this.uploadShowImageUrl = URL.createObjectURL(file.raw)
        this.createOrEditCertsForm.picUrl = res.data[0]
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt3M = file.size / 1024 / 1024 < 3

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
          return false
        }
        if (!isLt3M) {
          this.$message.error('上传头像图片大小不能超过 3MB!')
          return false
        }
        let that = this
        this.createReader(file, (width, height) => {
          if (!that.checkImg(width, height)) {
            this.$message.error(`只能上传长:1754px 宽:1238px的jpg文件,当前文件宽*高:${width}*${height}`)
            that.validImg = false
          } else {
            that.validImg = true
          }
        })
        return isJPG && isLt3M
      },
      checkImg(imgWidth, imgHeight) {
        if (imgWidth !== 1754) {
          return false
        }
        if (imgHeight !== 1238) {
          return false
        }
        return true
      },
      createReader(file, whenReady) {
        let reader = new FileReader
        reader.onload = function (evt) {
          let image = new Image()
          image.onload = function () {
            let width = this.width
            let height = this.height
            whenReady(width, height)
          }
          image.src = evt.target.result
        }
        reader.readAsDataURL(file)
      },
      clearUploadShowImageUrlAndValidate() {
        this.uploadShowImageUrl = ''
        this.$refs['createOrEditCertsFormRef'].clearValidate()
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.findCertsList(this.pageNum);
      },
      handleCurrentChange(val) {
        this.pageNum = val;
        this.findCertsList(this.pageNum);
      },
      findCertsList(pageNum) {
        this.pageNum = pageNum;
        let reqJsonParams = {
          page: {pageNum: this.pageNum, pageSize: this.pageSize},
          certsForm: this.certsForm
        }
        this.listLoading = true;
        baseAxios.post(config.FIND_CERTS_LIST, reqJsonParams).then((resJson) => {
          this.certsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      showCreateCertsDialog() {
        this.title = '新增证书'
        this.createCerts = true
        this.createOrEditCertsForm = {}
        this.uploadShowImageUrl = ''
        this.createOrEditCertsFormVisible = true
      },
      showEditCertsDialog(index, row) {
        this.title = '编辑证书'
        this.createCerts = false
        this.uploadShowImageUrl = row.picUrl
        this.createOrEditCertsForm = Object.assign({}, row)
        this.createOrEditCertsFormVisible = true
      },
      submitCreateOrEditCerts() {
        this.$refs.createOrEditCertsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          if (this.createOrEditCertsForm.picUrl !== '' && !this.validImg) {
            this.$message({message: '证书样图不符合要求', type: 'warning'})
            return
          }
          this.listLoading = true;
          let reqJsonParams = {certsForm: this.createOrEditCertsForm}
          let url = this.createCerts ? config.CREATE_CERTS : config.EDIT_CERTS
          baseAxios.post(url, reqJsonParams).then(() => {
            this.$message({message: '操作成功', type: 'success'})
            this.createOrEditCertsFormVisible = false
            this.findCertsList(this.pageNum)
          }).finally(() => {
            this.listLoading = false
          })
        })
      },
      cancelCreateOrEditCerts() {
        this.resetFormFields();
        this.createOrEditCertsFormVisible = false;
      },
      resetFormFields() {
        this.createOrEditCertsForm.id = null;
        this.createOrEditCertsForm.certsName = '';
        this.createOrEditCertsForm.certsDesc = '';
        this.createOrEditCertsForm.picUrl = '';
        this.$refs.createOrEditCertsFormRef.clearValidate();
      },
      showCertsExampleDialog(index, row) {
        this.showPdfDialogVisible = true
        this.pdfFileUrl = encodeURIComponent(`${config.APP_CONTEXT}${config.FIND_CERTS_EXAMPLE_PDF}?id=${row.id}`)
      },
      delCerts(index, row) {
        this.$confirm('是否确认删除?', '提示', {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'})
          .then(() => {
            baseAxios.post(config.DEL_CERTS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findCertsList(this.pageNum)
            })
          }).catch(() => {
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    },
    mounted() {
      this.findCertsList(1);
      window.vue = this;
    }
  }
</script>
<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 280px;
    height: 200px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 280px;
    height: 200px;
    display: block;
  }
</style>
