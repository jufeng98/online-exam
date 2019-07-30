<template>
  <div>
    <el-row>
      <el-col style="width:50%;">
        <div id="heapChart" :style="{width: '500px', height: '500px'}"></div>
      </el-col>
      <el-col style="width:50%;">
        <div id="nonheapChart" :style="{width: '500px', height: '500px'}"></div>
      </el-col>
    </el-row>
    <br/>
    <el-row>
      <el-col style="width:50%;text-align: left">
        <label style="font-weight: bold">处理器</label>
        <el-form>
          <el-form-item label="cpu数量:">
            {{cpuCount}}
          </el-form-item>
          <el-form-item label="cpu占用:">
            {{cpuUsage}}
          </el-form-item>
          <el-form-item label="已运行时间:">
            {{upTime+'秒'}}
          </el-form-item>
          <el-form-item label="启动时间:">
            {{startTime}}
          </el-form-item>
        </el-form>
      </el-col>
      <el-col style="width:50%;text-align: left">
        <label style="font-weight: bold">垃圾回收情况</label>
        <el-form>
          <el-form-item label="回收次数:">
            {{gcCount}}
          </el-form-item>
          <el-form-item label="总消耗时间:">
            {{gcTotalTime+'秒'}}
          </el-form-item>
          <el-form-item label="最大消耗时间:">
            {{gcMaxTime+'秒'}}
          </el-form-item>
        </el-form>
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
        heapInitTime: 0,
        xHeapTime: [],
        heapUse: [],
        heapCommittedUse: [],
        heapMaxUse: [],
        heapChart: null,
        heapTimeout: null,
        nonheapChart: null,
        nonheapInitTime: 0,
        xNonHeapTime: [],
        nonheapUse: [],
        nonheapCommittedUse: [],
        nonheapMaxUse: [],
        nonheapChart: null,
        cpuCount: 0,
        cpuUsage: 0.0,
        upTime: 0,
        startTime: '',
        gcCount: 0,
        gcTotalTime: 0,
        gcMaxTime: 0,
        MB: 1024 * 1024,
        REFRESH_PERIOD: 3000,
      }
    },
    methods: {
      drawHeapChart() {
        if (!this.heapChart) {
          this.heapChart = echarts.init(document.getElementById('heapChart'))
        }
        this.heapChart.setOption({
          title: {
            text: '堆使用情况(MB)'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          legend: {
            data: ['已用', '大小', '最大']
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: this.xHeapTime
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              color: '#fee479',
              name: '已用',
              type: 'line',
              stack: '总量',
              areaStyle: {},
              data: this.heapUse
            },
            {
              color: '#51aff4',
              name: '大小',
              type: 'line',
              stack: '总量',
              areaStyle: {},
              data: this.heapCommittedUse
            },
            {
              color: '#51ff94',
              name: '最大',
              type: 'line',
              stack: '总量',
              areaStyle: {},
              data: this.heapMaxUse
            },
          ]
        })
        this.heapChart.on('mouseover', () => {
          clearTimeout(this.heapTimeout)
        })
        this.heapChart.on('mouseout', () => {
          this.heapTimeout = setTimeout(this.getHeapMemory, this.REFRESH_PERIOD)
        })
      },
      drawNonHeapChart() {
        if (!this.nonheapChart) {
          this.nonheapChart = echarts.init(document.getElementById('nonheapChart'))
        }
        this.nonheapChart.setOption({
          title: {
            text: '非堆使用情况(MB)'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          legend: {
            data: ['已用', '大小', '最大']
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: this.xNonHeapTime
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              color: '#fee479',
              name: '已用',
              type: 'line',
              stack: '总量',
              areaStyle: {},
              data: this.nonheapUse
            },
            {
              color: '#51aff4',
              name: '大小',
              type: 'line',
              stack: '总量',
              areaStyle: {},
              data: this.nonheapCommittedUse
            },
            {
              color: '#51ff94',
              name: '最大',
              type: 'line',
              stack: '总量',
              areaStyle: {},
              data: this.nonheapMaxUse
            },
          ]
        })
      },
      getHeapMemory() {
        baseAxios.all([this.getMemoryHeapUse(), this.getMemoryHeapCommittedUse(), this.getMemoryHeapMaxUse()])
          .then(baseAxios.spread((res1, res2, res3) => {
            let tmp1 = Math.floor(res1.measurements[0].value / this.MB)
            let tmp2 = Math.floor(res2.measurements[0].value / this.MB)
            let tmp3 = Math.floor(res3.measurements[0].value / this.MB)
            this.dynamicChange(this.heapUse, tmp1)
            this.dynamicChange(this.heapCommittedUse, tmp2)
            this.dynamicChange(this.heapMaxUse, tmp3)
            this.heapInitTime += 5
            this.dynamicChange(this.xHeapTime, this.heapInitTime)
          }))
        this.drawHeapChart()
        this.heapTimeout = setTimeout(this.getHeapMemory, this.REFRESH_PERIOD)
      },
      getNonHeapMemory() {
        baseAxios.all([this.getMemoryNonHeapUse(), this.getMemoryNonHeapCommittedUse(), this.getMemoryNonHeapMaxUse()])
          .then(baseAxios.spread((res1, res2, res3) => {
            let tmp1 = Math.floor(res1.measurements[0].value / this.MB)
            let tmp2 = Math.floor(res2.measurements[0].value / this.MB)
            let tmp3 = Math.floor(res3.measurements[0].value / this.MB)
            this.dynamicChange(this.nonheapUse, tmp1)
            this.dynamicChange(this.nonheapCommittedUse, tmp2)
            this.dynamicChange(this.nonheapMaxUse, tmp3)
            this.nonheapInitTime += 5
            this.dynamicChange(this.xNonHeapTime, this.nonheapInitTime)
          }))
        this.drawNonHeapChart()
        setTimeout(this.getNonHeapMemory, this.REFRESH_PERIOD)
      },
      getCpuAndGc() {
        baseAxios.all([this.getSystemCpuCount(), this.getSystemCpuUsage(), this.getUpTime(), this.getStartTme(), this.getGc()])
          .then(baseAxios.spread((res1, res2, res3, res4, res5) => {
            let tmp1 = res1.measurements[0].value
            let tmp2 = res2.measurements[0].value
            let tmp3 = res3.measurements[0].value
            let tmp4 = res4.measurements[0].value
            this.cpuCount = tmp1
            this.cpuUsage = tmp2
            this.upTime = tmp3
            let date = new Date(tmp4 * 1000)
            this.startTime = date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
            this.gcCount = res5.measurements[0].value
            this.gcTotalTime = res5.measurements[1].value
            this.gcMaxTime = res5.measurements[2].value
          }))
      },
      dynamicChange(arr, newValue) {
        if (arr.length < 15) {
          arr.push(newValue)
        } else {
          arr.shift()
          arr.push(newValue)
        }
      },
      getMemoryHeapUse() {
        return baseAxios.get(config.GET_METRICS + '/jvm.memory.used?tag=area%3Aheap')
      },
      getMemoryHeapCommittedUse() {
        return baseAxios.get(config.GET_METRICS + '/jvm.memory.committed?tag=area%3Aheap')
      },
      getMemoryHeapMaxUse() {
        return baseAxios.get(config.GET_METRICS + '/jvm.memory.max?tag=area%3Aheap')
      },
      getMemoryNonHeapUse() {
        return baseAxios.get(config.GET_METRICS + '/jvm.memory.used?tag=area%3Anonheap')
      },
      getMemoryNonHeapCommittedUse() {
        return baseAxios.get(config.GET_METRICS + '/jvm.memory.committed?tag=area%3Anonheap')
      },
      getMemoryNonHeapMaxUse() {
        return baseAxios.get(config.GET_METRICS + '/jvm.memory.max?tag=area%3Anonheap')
      },
      getSystemCpuCount() {
        return baseAxios.get(config.GET_METRICS + '/system.cpu.count')
      },
      getSystemCpuUsage() {
        return baseAxios.get(config.GET_METRICS + '/system.cpu.usage')
      },
      getUpTime() {
        return baseAxios.get(config.GET_METRICS + '/process.uptime')
      },
      getStartTme() {
        return baseAxios.get(config.GET_METRICS + '/process.start.time')
      },
      getGc() {
        return baseAxios.get(config.GET_METRICS + '/jvm.gc.pause')
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
    },
    mounted() {
      this.getHeapMemory()
      this.heapTimeout = setTimeout(this.getHeapMemory, this.REFRESH_PERIOD)
      this.getNonHeapMemory()
      setTimeout(this.getNonHeapMemory, this.REFRESH_PERIOD)
      this.getCpuAndGc()
      window.vue = this
    },
  }
</script>
