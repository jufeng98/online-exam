<template>
  <el-container class="home-container" style="overflow: hidden">
    <el-header class="el-header">
      <div class="home-title">
        在线学习考试管理系统
        <el-switch
          v-model="expanded"
          @change="changeMenusAside"
          active-color="#409eff"
          inactive-color="#13ce66">
        </el-switch>
      </div>
      <div class="home-user-info-container">
        <span>
          <img v-if="loginUserPicUrl" style="width:30px;height:30px;vertical-align: middle" :src="loginUserPicUrl"/>
          <img v-else style="width:30px;height:30px;vertical-align: middle" src="../assets/logo.png"/>
        </span>
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link home-user-info">
            {{username}}
            <i class="el-icon-arrow-down el-icon--right home-user-info"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>系统消息</el-dropdown-item>
            <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside style="overflow-x:hidden;backgroundColor:#545c64;text-align: left" class="el-menu-aside"
                :width="asideWidth + '%'">
        <el-menu :default-openeds="defaultOpeneds" class="el-menu-vertical-demo"
                 :collapse="!expanded" background-color="#545c64" text-color="#ffffff" @select="addTab">
          <el-submenu v-for="(menu, index) in menus" :index="menu.name+'-'+menu.path" :key="Math.random()">
            <template slot="title"><i :class="menu.icon"></i>{{menu.name}}</template>
            <template v-if="menu.hasSubMenu">
              <tree v-for="(subMenu,subIndex) in menu.subMenus" :sub-menu="subMenu" :parent-index="index+1"
                    :sub-index="subIndex+1" :key="Math.random()">
              </tree>
            </template>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main class="el-main" style="overflow: hidden">
        <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab">
          <el-tab-pane
            v-for="(item) in editableTabs"
            :key="item.name"
            :label="item.title"
            :name="item.name">
            <iframe id="mainIframe" :src="item.path" style="width:100%;border:0;margin:0;allowtransparency:yes;"
                    :height="iframeHeight">
            </iframe>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
  import baseAxios from '../common/baseAxios'
  import appConsts from '../common/appConsts'
  import config from '../config'
  import tree from '../components/tree'

  export default {
    components: {
      tree
    },
    data() {
      return {
        loginUserPicUrl: localStorage.getItem(appConsts.LOGIN_USER_PIC_URL),
        iframeHeight: '',
        defaultOpeneds: ['系统监控-'],
        expanded: true,
        username: localStorage.getItem(appConsts.LOGIN_USERNAME),
        menus: [],
        editableTabsValue: '',
        editableTabs: [{
          title: '欢迎页',
          name: '欢迎页',
          path: '/#/helloWorld'
        }],
        asideWidth: 10.2,
        clearTimeout: null,
      }
    },
    methods: {
      changeMenusAside() {
        if (this.expanded) {
          this.expandAsideWidth();
        } else {
          this.collapseAsideWidth()
        }
      },
      expandAsideWidth() {
        this.asideWidth += 1
        if (this.asideWidth < 10.2) {
          if (this.clearTimeout !== null) {
            clearTimeout(this.clearTimeout)
          }
          this.clearTimeout = setTimeout(this.expandAsideWidth, 35);
        }
      },
      collapseAsideWidth() {
        this.asideWidth -= 1
        if (this.asideWidth > 2.2) {
          if (this.clearTimeout !== null) {
            clearTimeout(this.clearTimeout)
          }
          this.clearTimeout = setTimeout(this.collapseAsideWidth, 25)
        }
      },
      handleCommand(command) {
        if (command === 'logout') {
          this.$confirm('注销登录吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            baseAxios.post(config.LOGOUT_URL).then(() => {
              localStorage.clear()
              this.$router.replace('/')
            })
          })
        }
      },
      addTab(index, indexPath) {
        let pathInfo = indexPath[indexPath.length - 1].split('\-')
        let newTabName = pathInfo[0]
        if (this.editableTabs.length === 15) {
          this.$message({message: '打开的tab数量超过限制!', type: 'error'})
          return
        }
        for (let i = 0; i < this.editableTabs.length; i++) {
          if (this.editableTabs[i].name === newTabName) {
            this.editableTabsValue = newTabName
            return
          }
        }
        this.editableTabs.push({
          title: newTabName,
          name: newTabName,
          path: pathInfo[1],
        })
        this.editableTabsValue = newTabName
      },
      removeTab(targetName) {
        let tabs = this.editableTabs
        let activeName = this.editableTabsValue
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1]
              if (nextTab) {
                activeName = nextTab.name
              }
            }
          })
        }
        this.editableTabsValue = activeName
        this.editableTabs = tabs.filter(tab => tab.name !== targetName)
      },
      getHeight() {
        let pageHeight = window.innerHeight
        if (typeof pageHeight != "number") {
          if (document.compatMode == "CSS1Compat") {
            pageHeight = document.documentElement.clientHeight;
          } else {
            pageHeight = document.body.clientHeight;
          }
        }
        return pageHeight
      },
    },
    mounted() {
      this.editableTabsValue = '欢迎页'
      baseAxios.post(config.GET_MENUS_LIST).then(resJson => {
        this.menus = resJson.data.menusEntities
      })
      this.iframeHeight = this.getHeight() - 150 + 'px'
      let that = this
      window.onresize = function windowResize() {
        that.iframeHeight = that.getHeight() - 150 + 'px'
      }
      localStorage.setItem("baseUrl", config.BASE_PATH + config.APP_CONTEXT)
      window.vue = this
    }
  }
</script>
<style scoped>
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }

  .home-container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }

  .el-header {
    background-color: #40D4A6;
    color: #333;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 100%;
  }

  .el-main {
    background-color: #fff;
    color: #000;
    text-align: center;
  }

  .home-title {
    color: #fff;
    font-size: 22px;
    display: inline;
  }

  .home-user-info {
    color: #fff;
    cursor: pointer;
  }

  .home-user-info-container {
    display: inline;
  }
</style>
