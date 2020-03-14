<template>
  <div>
    <el-form :model="createOrEditSectionsForm" label-width="120px" :rules="createOrEditSectionsFormRules"
             ref="createOrEditSectionsFormRef">
      <el-form-item label="章节名称" prop="sectionsName">
        <el-input v-model="createOrEditSectionsForm.sectionsName" auto-complete="off"
                  :maxlength="30"></el-input>
      </el-form-item>
      <el-form-item label="章节封面" prop="sectionsCoverImage" style="text-align: left">
        <el-upload
          style="margin:0px;height:200px;"
          class="avatar-uploader"
          action=""
          :http-request="showImage"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload">
          <img v-if="uploadShowImageUrl" :src="uploadShowImageUrl" class="avatar">
          <i v-else class="el-upload el-icon-plus avatar-uploader-icon"></i>
          <div slot="tip" style="color: red" class="el-upload__tip">
            只能上传jpg文件，且不超过3MB
          </div>
        </el-upload>
        <el-input type="hidden" style="width:0px;height:0px;"
                  v-model="createOrEditSectionsForm.sectionsCoverImage">
        </el-input>
      </el-form-item>
      <el-form-item label="所属主题" prop="topicsCode" style="text-align: left">
        <el-select v-model="createOrEditSectionsForm.topicsCode">
          <el-option
            v-for="item in topicsList"
            :key="item.topicsCode"
            :label="item.topicsName"
            :value="item.topicsCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sortOrder" style="text-align: left">
        <el-input-number controls-position="right" v-model="createOrEditSectionsForm.sortOrder"></el-input-number>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button @click.native="closeDialog('cancel')">取消</el-button>
      <el-button type="primary" @click.native="submitCreateOrEditSections" :loading="loading">提交</el-button>
    </div>
  </div>
</template>

<script>
  import appConsts from "../../common/appConsts";
  import baseAxios from "../../common/baseAxios";
  import config from "../../config";

  export default {
    name: 'addOrEditSections',
    props: {
      sections: Object
    },
    data() {
      return {
        base64Prefix: appConsts.BASE64_JPG_PREFIX,
        createOrEditSectionsForm: {
          id: null,
          sectionsName: '',
          sectionsCoverImage: null,
          topicsCode: '',
          sortOrder: 1,
        },
        uploadShowImageUrl: '',
        loading: false,
        createSections: false,
        topicsList: [],
        topicsMap: new Map(),
        createOrEditSectionsFormRules: {
          sectionsName: [
            {required: true, message: '请输入章节名称', trigger: 'blur'}
          ],
          sectionsCoverImage: [
            {required: true, message: '请选择章节封面', trigger: 'blur'}
          ],
          topicsCode: [
            {required: true, message: '请选择所属主题', trigger: 'blur'}
          ],
        },
      }
    },
    methods: {
      closeDialog(action) {
        this.$emit('close', {action})
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt3M = file.size / 1024 / 1024 < 3

        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG 格式!')
        }
        if (!isLt3M) {
          this.$message.error('上传图片大小不能超过 3MB!')
        }
        return isJPG && isLt3M
      },
      showImage(obj) {
        let file = obj.file
        this.uploadShowImageUrl = top.URL.createObjectURL(file)
        let reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => {
          this.createOrEditSectionsForm.sectionsCoverImage = reader.result
        }
      },
      findTopicsList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999, orderBy: 'create_time desc'},
          topicsForm: {}
        }
        baseAxios.post(config.FIND_TOPICS_LIST, reqJsonParams).then((resJson) => {
          this.topicsList = resJson.data
          for (let i = 0; i < this.topicsList.length; i++) {
            this.topicsMap.set(this.topicsList[i].topicsCode, this.topicsList[i].topicsName)
          }
        })
      },
      submitCreateOrEditSections() {
        this.$refs.createOrEditSectionsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.loading = true
          let reqJsonParams = {
            sectionsForm: this.createOrEditSectionsForm,
          }
          let url = this.createSections ? config.CREATE_SECTIONS : config.EDIT_SECTIONS
          baseAxios.post(url, reqJsonParams).then((resJson) => {
            this.closeDialog('success')
          }).finally(() => {
            this.loading = false
          })
        })
      },
    },
    mounted() {
      window.vue = this
      this.createSections = this.sections.createSections
      this.createOrEditSectionsForm.id = this.sections.id
      this.createOrEditSectionsForm.sectionsName = this.sections.sectionsName
      if (!this.createSections) {
        this.createOrEditSectionsForm.sectionsCoverImage = this.base64Prefix + this.sections.sectionsCoverImage
        this.uploadShowImageUrl = this.createOrEditSectionsForm.sectionsCoverImage
      }
      this.createOrEditSectionsForm.topicsCode = this.sections.topicsCode
      this.createOrEditSectionsForm.sortOrder = this.sections.sortOrder
      this.findTopicsList()
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
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
