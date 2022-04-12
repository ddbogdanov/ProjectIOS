<template>
    <section class="orders-view">
        <el-container class="orders-view-container">
            <el-aside class="orders-dash-aside">
                <el-container class="orders-dash-container">
                    <el-header class="orders-dash-header">
                        <h1 id="orders-aside-label">ORDERS</h1>
                    </el-header>

                    <el-divider id="main-divider"/>

                    <el-row id="orders-actions-row">
                        <el-col>
                            <h1 style="font-size: 1.5em;">Actions</h1>
                            <el-button-group style="margin-top: 10px;">
                                <el-button type="success" plain round :icon="DocumentAdd" @click="drawer=true">Create New Order</el-button>
                            </el-button-group>
                        </el-col>
                    </el-row>

                    <el-divider id="secondary-divider"/>

                    <el-row id="orders-total-row">
                        <el-col>
                            <h1 id="orders-total-label">Total Orders:</h1>
                            <label id="orders-count-label">{{orders.length}}</label>
                        </el-col>
                    </el-row>
                </el-container>
            </el-aside>
            <el-main class="orders-table-main">
              <el-table :data="orders.filter((data) => !search || data.productOrderId.includes(search))"
                        class="orders-table" v-loading="loadingTable"
                        stripe height="100%" >
                <el-table-column>
                <template #header>
                  <el-row justify="space-around">
                    <el-col :span="2" >
                      <el-button :icon="Refresh" circle plain size="small" @click.stop="fetchOrders"></el-button>
                    </el-col>
                    <el-col :span="5" style="margin-left:auto;">
                      <el-input v-model="search" placeholder="Search by Order ID" :suffix-icon="Search"/>
                    </el-col>
                  </el-row>
                </template>

                <el-table-column prop="product.name" label="Name" sortable min-width="199"></el-table-column>
                <el-table-column prop="productOrderId" label="ID" align="right"></el-table-column>
                <el-table-column prop="product.sku" label="SKU" align="right"></el-table-column>
                <el-table-column prop="color.color" label="Color" align="right"></el-table-column>
                <el-table-column prop="product.materialType.type" label="Material Type" align="right"></el-table-column>
                <el-table-column prop="quantity" label="Quantity" sortable align="right"></el-table-column>
                <el-table-column prop="dateCreated" label="Date Created" sortable :formatter="dateFormatter" align="right"></el-table-column>
                <el-table-column label="Actions" align="right" width="130">
                  <template #default="scope">

                    <el-tooltip effect="light" content="Edit" placement="left">
                      <el-button :icon="Edit" type="primary" circle @click="handleOrderEdit(scope.$index, scope.row)"></el-button>
                    </el-tooltip>
                    <el-popconfirm
                        :title="'Are you sure you want to delete order: ' + scope.row.productOrderId + '?'"
                        confirm-button-type="danger"
                        cancel-button-type="info"
                        cancel-button-text="No, Dont Delete"
                        icon-color="red"
                        @confirm.stop="handleOrderDelete(scope.$index, scope.row)"
                    >
                      <template #reference>
                        <el-button :icon="Delete" type="danger" circle></el-button>
                      </template>
                    </el-popconfirm>
                  </template>
                </el-table-column>
                </el-table-column>
              </el-table>
            </el-main>
            <el-drawer v-model="drawer" title="Create New Order" direction="ltr" :before-close="handleCloseDrawer" destroy-on-close>
                <OrderForm :productProp='selectedOrder'/>
            </el-drawer>
        </el-container>
    </section>
</template>



<script>
import axios from "axios";
import {ElMessageBox} from "element-plus";
import {Delete, Edit, Search, Refresh, DocumentAdd} from "@element-plus/icons-vue"
import OrderForm from "@/components/forms/OrderForm";
import {shallowRef} from "vue";
import moment from "moment";

export default {
    name: "Orders",
    components: { OrderForm },
    data() {
        return {
            orders: [ ],
            search: '',
            loadingTable: false,
            drawer: shallowRef(false),
            Delete: shallowRef(Delete), Edit: shallowRef(Edit), Search: shallowRef(Search), Refresh: shallowRef(Refresh), DocumentAdd: shallowRef(DocumentAdd),
          selectedOrder: {productOrderId: '', quantity: '', dateCreated: '', color: {colorId: '', color: ''}, product: {productId:'', name: '', sku:''}, materialType: {materialTypeId: '', type: ''}},

          }
    },
    mounted() {
        this.$bus.on('closeOrderForm', () => {
            this.drawer = false
            this.fetchOrders()
        })
    },
    created() {
        this.fetchOrders()
    },
    methods: {
      fetchOrders() {
            this.loadingTable = true
            let apiUrl = "/product/order"

            axios.get(apiUrl).then((res) => {
                this.orders = res.data
                this.loadingTable = false
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        handleOrderEdit(index, row) {
            this.selectedOrder = row
            this.drawer = true
        },
        handleOrderDelete(index, row) {
            let apiUrl = "/product/orders" + row.productOrderId

            axios.delete(apiUrl).then(() => {
                this.orders.splice(this.orders.indexOf(row), 1)
            }).catch(error => {
                ElMessageBox.alert('Product has likely been used in an order and can\'t be deleted. ' + error)
            })
        },
        handleCloseDrawer() {
            ElMessageBox.confirm('Are you sure want to close the order form?')
                .then(() => {
                    this.selectedOrder = {name: '', product:{ sku:''}, sku:'', color:'', prodMatExpenditure:'', materialType:{materialTypeId: '', type: ''} }
                    this.drawer = false
                    this.fetchOrders()
                })
        },
      dateFormatter(row) {
        return moment(row).format('MM-DD-YYYY')
      }
    }

}

</script>



<style scoped>
    .orders-view-container {
        height: 100%;
    }
    .orders-dash-aside {
        width: 415px;
        height: 100%;
        background-color: #545c64;
        color: white;
    }
    .orders-dash-container {
        font-family: 'Product Sans', sans-serif;
        text-align: center;
        justify-items: center;
        align-content: center;
        justify-content: center;
    }
    .orders-dash-header {
        display: flex;
        margin: 0;
        justify-content: center;
        align-items: center;
        text-align: center;
        vertical-align: middle;
    }

    .orders-table-main {
        max-height: 100%;
    }
    .orders-table {
        height: 100%;
        width: 100%;
    }
    .orders-subtable {
        margin-left: 48px;
        max-width:Calc(100% - 65px);
    }

    #main-divider {
        height: 2px;
        width: 100%;
        border: none;
        color: #FFAE42;
        background-color: #FFAE42;
    }
    #secondary-divider {
        height: 2px;
        width: 50%;
        margin-left: 25%;
        margin-right: 25%;
        border: none;
        color: #FFAE42;
        background-color: #FFAE42;
    }
    #orders-total-row {

    }
    #orders-aside-label {
        font-size: 2em;
    }
    #orders-total-label {
        font-size: 1.5em;
    }
    #orders-count-label {
        font-size: 6em;
    }
    #orders-actions-row {

    }
</style>