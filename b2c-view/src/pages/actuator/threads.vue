<template>
  <div>
    <el-row>
      <el-col>
        <div id="threadChart" :style="{width: '100%', height: '1200px'}"></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col style="text-align: left">
        <label style="font-weight: bold">线程详细信息</label>
        <table class="threadTableClz">
          <tr>
            <td>线程名字</td>
            <td>线程id</td>
            <td>阻塞时间</td>
            <td>阻塞次数</td>
            <td>等待时间</td>
            <td>等待次数</td>
            <td>锁名称</td>
            <td>锁拥有者id</td>
            <td>锁拥有者名称</td>
            <td>本地线程</td>
            <td>是否挂起</td>
            <td>线程状态</td>
            <td>lockedMonitors</td>
            <td>lockedSynchronizers</td>
            <td>lockInfo</td>
          </tr>
          <tr>
            <td>{{thread.threadName}}</td>
            <td>{{thread.threadId}}</td>
            <td>{{thread.blockedTime}}</td>
            <td>{{thread.blockedCount}}</td>
            <td>{{thread.waitedTime}}</td>
            <td>{{thread.waitedCount}}</td>
            <td>{{thread.lockName}}</td>
            <td>{{thread.lockOwnerId}}</td>
            <td>{{thread.lockOwnerName}}</td>
            <td>{{thread.inNative?'是':'否'}}</td>
            <td>{{thread.suspended?'是':'否'}}</td>
            <td>{{thread.threadState}}</td>
            <td>{{thread.lockedMonitors.join(' ')}}</td>
            <td>{{thread.lockedSynchronizers.join(' ')}}</td>
            <td>{{JSON.stringify(thread.lockInfo)}}</td>
          </tr>
        </table>
      </el-col>
    </el-row>
    <el-row style="text-align: left">
      <el-col>
        <label>堆栈信息</label>
        <table>
          <tr v-for="item in thread.stackTrace">
            <td>{{item.lineNumber}}</td>
            <td>{{item.className+'.'+item.methodName}}</td>
            <td>本地方法:{{item.nativeMethod?'是':'否'}}</td>
            <td>所在文件:{{item.fileName}}</td>
          </tr>
        </table>
      </el-col>
    </el-row>
  </div>
</template>
<script>

  import baseAxios from '../../common/baseAxios'
  import config from '../../config'
  import echarts from 'echarts'

  export default {
    data() {
      return {
        thread: {lockedMonitors: [], lockedSynchronizers: []},
        threads: [],
        threadNames: [],
        threadTimeout: null,
        heapInitTime: 0,
        xHeapTime: [],
        heapUse: [],
        heapCommittedUse: [],
        heapMaxUse: [],
        threadChart: null,
        data: {
          'NEW': [],
          'RUNNABLE': [],
          'RUNNING': [],
          'BLOCKED': [],
          'WAITING': [],
          'TIMED_WAITING': [],
          'TERMINATED': [],
        },
      }
    },
    methods: {
      drawThreadChart() {
        if (!this.threadChart) {
          this.threadChart = echarts.init(document.getElementById('threadChart'))
        }
        this.threadChart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            data: ['新建', '就绪', '运行中', '等待', '限时等待', '阻塞', '结束']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value'
          },
          yAxis: {
            type: 'category',
            data: this.threadNames,
          },
          series: [
            {
              name: '阻塞',
              type: 'bar',
              stack: '总量',
              data: this.data['BLOCKED']
            },
            {
              name: '新建',
              type: 'bar',
              stack: '总量',
              data: this.data['NEW']
            },
            {
              name: '限时等待',
              type: 'bar',
              stack: '总量',
              data: this.data['TIMED_WAITING']
            },
            {
              name: '等待',
              type: 'bar',
              stack: '总量',
              data: this.data['WAITING']
            },
            {
              name: '就绪',
              type: 'bar',
              stack: '总量',
              data: this.data['RUNNABLE']
            },
            {
              color: '#51ff94',
              name: '运行中',
              type: 'bar',
              stack: '总量',
              data: this.data['RUNNING']
            },
            {
              color: '#888888',
              name: '结束',
              type: 'bar',
              stack: '总量',
              data: this.data['TERMINATED']
            },
          ]
        })
        this.threadChart.on('click', (params) => {
          console.log(params.name)
          clearInterval(this.threadTimeout)
          this.thread = this.threads.filter((item) => {
            if (item.threadName === params.name) {
              return true
            }
            return false
          })[0]
        })
      },
      getTheads() {
        baseAxios.get(config.GET_THREADS).then((resJson) => {
          this.thread = resJson.threads[0]
          this.threads = resJson.threads
          this.threadNames = this.threads.map((item) => {
            return item.threadName
          })
          this.threads.forEach((item, index) => {
            let targetState = item.threadState
            this.data[targetState][index] = 5
            for (let name in this.data) {
              if (name !== targetState) {
                this.data[name][index] = 0
              }
            }
          })

        })
        this.drawThreadChart()
      },
    },
    mounted() {
      this.getTheads()
      this.threadTimeout = setInterval(this.getTheads, 5000)
      window.vue = this
    },
  }
</script>
<style scoped>
  .threadTableClz {
    width: 100%;
  }

  .threadTableClz tr td {
    width: 55px;
    word-break: break-all;
  }
</style>
