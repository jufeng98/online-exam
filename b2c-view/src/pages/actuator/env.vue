<template>
  <div>
    <el-row>
      <el-col>
        <el-form :inline="true" style="text-align: left;margin: 0;">
          <el-form-item label="属性源:" prop="propertySource">
            <el-select v-model="propertySourceForm.name">
              <el-option
                v-for="item in propertySources"
                :key="item.name"
                :label="item.name"
                :value="item.name">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="属性名" prop="propertyName">
            <el-input v-model="propertySourceForm.propertyName" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="search" @click="searchPropertySource()">搜索</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row style="margin-bottom: 20px;text-align: left">
      <el-col>
        <el-button type="primary" icon="search" @click="showProfiles">查看激活的profiles</el-button>
        <el-button type="primary" icon="search" :loading="exportHeadLoading" @click="exportHeapDump">
          {{exportHeadTip}}
        </el-button>
        <el-tooltip placement="top">
          <div slot="content">
            <pre>
                    jps命令说明

            * 只输出LVMID，省略主类名称
            * jps -q
            * 输出启动时传递给main方法的参数
            * jps -m
            * 输出主类的全名，如果是jar包，输出jar包路径
            * jps -l
            * 输出启动时JVM参数
            * jps -v
            </pre>
          </div>
          <el-button type="primary" icon="search" @click="showJvms">查看系统所有运行中JVM(jps)</el-button>
        </el-tooltip>
        <el-tooltip placement="top">
          <div slot="content">
            jinfo -flags pid
          </div>
          <el-button type="primary" icon="search" @click="showJvmProperties">查看JVM参数(jinfo)</el-button>
        </el-tooltip>
        <el-tooltip placement="top">
          <div slot="content">
            java -XX:+PrintFlagsFinal
          </div>
          <el-button type="primary" icon="search" @click="showJvmPropertiesDefault">查看JVM所有参数默认值</el-button>
        </el-tooltip>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table :border="true" :data="propertiesCondition" highlight-current-row v-loading="listLoading" fit
                  style="margin-right:50px;">
          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column prop="key" label="属性名" width="300px"></el-table-column>
          <el-table-column prop="value" label="属性值"></el-table-column>
          <el-table-column prop="origin" label="源"></el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog title="JVM列表" :visible.sync="dialogJvmsVisible" width="75%">
      <el-table :data="jvms">
        <el-table-column property="pid" label="进程id"></el-table-column>
        <el-table-column property="jvm" label="主启动类名"></el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog title="JVM参数" :visible.sync="dialogJvmPropertyVisible" width="35%">
      <el-table :data="strs">
        <el-table-column property="str" label="参数"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>

  import baseAxios from '../../common/baseAxios'
  import downloadUtils from '../../common/downloadUtils'
  import config from '../../config'

  export default {
    data() {
      return {
        contexts: {},
        exportHeadLoading: false,
        exportHeadTip: '导出堆信息',
        properties: [],
        propertiesCondition: [],
        activeProfiles: [],
        propertySourceForm: {name: '', propertyName: ''},
        propertySources: [],
        listLoading: false,
        dialogJvmsVisible: false,
        dialogJvmPropertyVisible: false,
        jvms: [],
        strs: [],
      }
    },
    methods: {
      getPropertySourceNames() {
        baseAxios.get(config.GET_PROPERTY_SOURCE_NAMES).then((resJson) => {
          this.activeProfiles = resJson.activeProfiles
          this.propertySources = resJson.propertySources
          this.propertySourceForm.name = resJson.propertySources[0].name
          this.searchPropertySource()
        })
      },
      searchPropertySource() {
        this.properties = []
        let tmp = this.propertySources.filter((item) => {
          if (item.name === this.propertySourceForm.name) {
            return true
          }
          return false
        })[0].properties
        for (let obj in tmp) {
          let origin = tmp[obj].origin ? tmp[obj].origin : ''
          this.properties.push({key: obj, value: tmp[obj].value, origin: origin})
        }
        this.propertiesCondition = this.properties
        this.propertiesCondition = this.properties.filter((item => {
          let condi1 = true
          if (this.propertySourceForm.propertyName !== '') {
            condi1 = item.key.indexOf(this.propertySourceForm.propertyName) !== -1
          }
          return condi1
        }))
      },
      showProfiles() {
        this.$alert(`当前应用激活的profiles:${this.activeProfiles.join(' ')}`, '提示', {
          confirmButtonText: '确定',
        })
      },
      exportHeapDump() {
        let tmp = this.exportHeadTip
        downloadUtils.download(config.GET_HEAPDUMP, "heapdump", () => {
          this.exportHeadTip = '下载中,请稍候'
          this.exportHeadLoading = true
        }, () => {
          this.exportHeadTip = tmp
          this.exportHeadLoading = false
        }, (error) => {
          console.error(config.GET_HEAPDUMP, error)
          this.$message('下载失败,请稍后再试')
        })
      },
      showJvms() {
        baseAxios.get(config.GET_JPS).then((resJson) => {
          this.dialogJvmsVisible = true
          this.jvms = resJson.data.listMaps
        })
      },
      showJvmProperties() {
        baseAxios.get(config.GET_JINFO).then((resJson) => {
          this.dialogJvmPropertyVisible = true
          let tmp = resJson.data.strs
          this.strs = tmp.map((item) => {
            return {str: item}
          })
        })
      },
      showJvmPropertiesDefault() {
        baseAxios.get(config.GET_PRINTFLAGSFINAL).then((resJson) => {
          this.dialogJvmPropertyVisible = true
          let tmp = resJson.data.strs
          this.strs = tmp.map((item) => {
            return {str: item}
          })
        })
      },
    },
    mounted() {
      this.getPropertySourceNames()
      window.vue = this
    },
  }
</script>
