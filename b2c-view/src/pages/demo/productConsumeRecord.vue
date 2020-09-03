<template>
  <div>
    <el-form :inline="true" :model="editForm" label-width="100px" ref="editFormFormRef">
      <el-row>
        <el-col>
          <el-form-item label="订单总数">
            <el-input disabled v-model="editForm.totalNum"/>
          </el-form-item>
          <el-form-item label="总消费金额">
            <el-input disabled v-model="editForm.totalReceivedAmt"/>
          </el-form-item>
          <el-form-item label="查看产品购买明细:">
            <el-button @click="openProductDetailPage" type="text">查看</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <span style="font-size: 12px;margin-left: 5px">创单时间：</span>
          <el-date-picker :editable="true" clearable format="yyyy-MM-dd HH:mm:ss" type="datetime"
                          v-model="editSearchProdForm.startTime" value-format="timestamp"></el-date-picker>
          至
          <el-date-picker :editable="true" clearable format="yyyy-MM-dd HH:mm:ss" type="datetime"
                          v-model="editSearchProdForm.endTime" value-format="timestamp"></el-date-picker>
          <el-button icon="search" type="primary" v-on:click="findUserProducts(1)">查询</el-button>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <div class="grid-content bg-purple">
            <el-table :data="productDataList" border element-loading-text="拼命加载中" fit max-height="480" row-key="id"
                      tooltip-effect="dark" v-loading="listProdLoading">
              <el-table-column fixed header-align="center" label="序号" type="index" width="50"/>
              <el-table-column :show-overflow-tooltip="true" label="订单编号" min-width="200" prop="orderCode"/>
              <el-table-column :show-overflow-tooltip="true" label="产品总数量" min-width="100" prop="proTotalNum"/>
              <el-table-column :formatter="formatData" :show-overflow-tooltip="true" label="订单实付金额" prop="orderPayAmt"/>
              <el-table-column :formatter="formatData" :show-overflow-tooltip="true" label="创建时间" min-width="200"
                               prop="platStartTime"/>
              <el-table-column :formatter="formatData" :show-overflow-tooltip="true" label="支付时间" min-width="200"
                               prop="payTime"/>
            </el-table>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col style="text-align: left">
          <el-pagination
            :current-page="pageNum"
            :page-size="pageSize"
            :page-sizes="[10, 20, 30, 40]"
            :total="total"
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            layout="total, sizes, prev, pager, next, jumper">
          </el-pagination>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import appConsts from "../../common/appConsts";
  import baseAxios from "../../common/baseAxios";
  import config from "../../config";
  import moment from 'moment'
  import {BigNumber} from 'bignumber.js'

  export default {
    name: 'productConsumeRecord',
    props: {
      username: String,
    },
    data() {
      return {
        bigNumber100: new BigNumber(100),
        ENABLED: appConsts.ENABLED,
        GENDER: appConsts.GENDER,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        editForm: {
          totalNum: null,
          totalReceivedAmt: null,
        },
        listProdLoading: false,
        editSearchProdForm: {
          startTime: null,
          endTime: null,
        },
        productDataList: [],
      }
    },
    methods: {
      formatData(row, column) {
        if (column.property === "platStartTime") {
          return moment(row.platStartTime).format("YYYY-MM DD-hh:mm:ss")
        }
        if (column.property === "payTime") {
          return moment(row.payTime).format("YYYY-MM-DD hh:mm:ss")
        }
        if (column.property === "orderPayAmt") {
          return new BigNumber(row.orderPayAmt).dividedBy(this.bigNumber100).toFixed(2)
        }
      },
      handleSizeChange(val) {
        this.pageSize = val
        this.findUsers(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findUsers(this.pageNum)
      },
      openProductDetailPage() {

      },
      findUserProductSummary(username) {
        let self = this;
        baseAxios.post(config.FIND_USER_PRODUCT_SUMMARY, {username}).then((resJson) => {
          self.editForm = resJson.data
        }).finally(() => {

        })
      },
      findUserProducts(pageNum) {
        this.pageNum = pageNum
        let self = this;
        this.listProdLoading = true
        baseAxios.post(config.FIND_USER_PRODUCTS, {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          startTime: this.editSearchProdForm.startTime,
          endTime: this.editSearchProdForm.endTime,
        }).then((resJson) => {
          self.productDataList = resJson.data
          self.total = resJson.total
        }).finally(() => {
          this.listProdLoading = false
        })
      },
    },
    mounted() {
      this.findUserProductSummary(this.username)
      this.findUserProducts(1)
    }
  }
</script>

<style scoped>

</style>
