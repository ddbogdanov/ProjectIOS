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
                        </el-col>
                    </el-row>

                    <el-divider id="secondary-divider"/>
                    <el-row id="orders-total-row">
                        <el-col>
                            <h1 id="orders-total-label">Total Orders:</h1>
                            <label id="orders-count-label">{{productOrders.length}}</label>
                        </el-col>
                    </el-row>
                </el-container>
            </el-aside>


            <el-main class="orders-table-main">
                <el-table :data="productOrders.filter((data) => !search || data.productOrderId.includes(search))"
                          :default-sort="{prop: 'completed', order: 'ascending'}"
                          class="orders-table" v-loading="loadingTable"
                          height="100%" :row-class-name="tableRowClassName">

                <el-table-column>
                <template #header>
                    <el-row justify="space-around" style="align-items: center">
                        <el-col :span="5" >
                            <el-button :icon="Refresh" circle plain size="small" @click.stop="fetchProductOrders"></el-button>
                            <label style="margin-left:10px; font-family: 'Product Sans', sans-serif">Product Orders</label>
                        </el-col>
                        <el-col :span="5" style="margin-left:auto;">
                                <el-input v-model="search" placeholder="Search by Order ID" :suffix-icon="Search"/>
                        </el-col>
                    </el-row>
                </template>

                <el-table-column type="expand" #default="scope">
                    <el-table :data="productOrders[(productOrders.indexOf(scope.row))].accessoryOrders"
                              :default-sort="{prop: 'completed', order: 'ascending'}"
                              class="orders-subtable" :row-class-name="tableRowClassName"
                    >
                        <el-table-column :label="'Accessory Orders - ID: #' + productOrders[(productOrders.indexOf(scope.row))].productOrderId">
                            <el-table-column label="Accessory Info">
                                <el-table-column prop="productAccessory.name" label="Name"></el-table-column>
                                <el-table-column prop="productAccessory.materialType.type" label="Quantity"></el-table-column>
                            </el-table-column>
                            <el-table-column label="Order Info">
                                <el-table-column prop="quantity" label="Quantity"></el-table-column>
                                <el-table-column prop="color.color" label="Color"></el-table-column>
                                <el-table-column prop="dateCreated" label="Date Entered" :formatter="dateFormatter"></el-table-column>
                                <el-table-column prop="completed" label="Done?" :formatter="booleanFormatter"></el-table-column>
                            </el-table-column>

                            <el-table-column label="Actions" align="right" width="160">
                                <template #default="scope">
                                    <el-button :icon="Check" type="success" circle v-if="!scope.row.completed" @click.stop="handleBeginOrderComplete(scope.$index, scope.row)"></el-button>

                                    <el-popconfirm
                                        :title="'Are you sure you want to delete accessory order ID: ' + scope.row.accessoryOrderId + '?'"
                                        confirm-button-type="danger"
                                        cancel-button-type="info"
                                        cancel-button-text="No, Dont Delete"
                                        icon-color="red"
                                        @confirm.stop="handleAccOrderDelete(scope.$index, scope.row)"
                                    >
                                        <template #reference>
                                            <el-button :icon="Delete" type="danger" circle></el-button>
                                        </template>
                                    </el-popconfirm>
                                </template>
                            </el-table-column>
                        </el-table-column>
                    </el-table>
                    <el-card class="orders-subtable" style="margin-top: 10px" shadow="hover" v-if="scope.row.comments !== ''">
                        <template #header>
                            <span style="font-family: 'Product Sans', sans-serif;"><h3>Order Comments</h3></span>
                        </template>
                        <p>{{scope.row.comments}}</p>
                    </el-card>
                </el-table-column>


                <el-table-column prop="productOrderId" label="ID" width="50"></el-table-column>
                <el-table-column label="Product Info">
                    <el-table-column prop="product.name" label="Product Name" sortable min-width="150"></el-table-column>
                    <el-table-column prop="product.sku" label="Product SKU" align="right"></el-table-column>
                </el-table-column>
                <el-table-column label="Order Info">
                    <el-table-column prop="color.color" label="Color" align="right"></el-table-column>
                    <el-table-column prop="quantity" label="Quantity" sortable align="right"></el-table-column>
                    <el-table-column prop="product.materialType.type" label="Material Type" align="right"></el-table-column>
                    <el-table-column prop="dateCreated" label="Date Created" sortable :formatter="dateFormatter" align="right"></el-table-column>
                    <el-table-column prop="completed" label="Done?" sortable align="right" width="100" :formatter="booleanFormatter"></el-table-column>
                </el-table-column>

                <el-table-column label="Actions" align="right" width="160">
                    <template #default="scope">

                        <el-button :icon="Check" type="success" circle v-if="!scope.row.completed" @click.stop="handleBeginOrderComplete(scope.$index, scope.row)"></el-button>

                        <el-tooltip effect="light" content="Edit" placement="top-start" v-if="!scope.row.completed">
                            <el-button :icon="Edit" type="primary" circle @click="handleOrderEdit(scope.$index, scope.row)"></el-button>
                        </el-tooltip>
                        <el-popconfirm
                            :title="'Are you sure you want to delete order ID: ' + scope.row.productOrderId + '?'"
                            confirm-button-type="danger"
                            cancel-button-type="info"
                            cancel-button-text="No, Dont Delete"
                            icon-color="red"
                            @confirm.stop="handleOrderDelete(scope.$index, scope.row)"
                            v-if="!scope.row.completed"
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
                <OrderForm :productProp="{}" :orderProp='selectedOrder'/>
            </el-drawer>

            <el-dialog v-model="materialSelectDialogVisible" title="Select the material you used">
                <el-table :data="applicableMaterials" highlight-current-row ref="appMaterialRef" @current-change="handleCurrentChange">
                    <el-table-column prop="name" label="Name" min-width="200" sortable></el-table-column>
                    <el-table-column prop="manufacturer.manufacturer" label="Manufacturer" align="right"></el-table-column>
                    <el-table-column prop="color.color" label="Color" align="right"></el-table-column>
                </el-table>

                <template #footer>
                    <el-button plain @click="handleCloseCompleteDialog">Cancel</el-button>
                    <el-popconfirm
                        :title="'Are you sure you want to set this order as completed? This will not complete accessory orders, and this action CANNOT be undone'"
                        confirm-button-type="success"
                        cancel-button-type="info"
                        cancel-button-text="No, It's Not Complete"
                        icon-color="green"
                        @confirm.stop="handleOrderComplete()"
                    >
                        <template #reference>
                            <el-button type="success">Confirm</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-dialog>
        </el-container>
    </section>
</template>

<script>
import axios from "axios";
import {ElMessageBox} from "element-plus";
import {Delete, Edit, Search, Refresh, DocumentAdd, Check,} from "@element-plus/icons-vue"
import OrderForm from "@/components/forms/OrderForm";
import {shallowRef} from "vue";
import moment from "moment";

export default {
    name: "Orders",
    components: { OrderForm },
    data() {
        return {
            productOrders: [],
            search: '',
            loadingTable: false,
            drawer: shallowRef(false),
            Delete: shallowRef(Delete), Edit: shallowRef(Edit), Search: shallowRef(Search), Refresh: shallowRef(Refresh), DocumentAdd: shallowRef(DocumentAdd), Check: shallowRef(Check),
            selectedOrder: {productOrderId: '', quantity: '', dateCreated: '', color: {colorId: '', color: ''}, product: {productId:'', name: '', sku:''}, materialType: {materialTypeId: '', type: ''}},
            materialSelectDialogVisible: false, applicableMaterials: [], orderToComplete: {}, selectedMaterial: {}
          }
    },
    mounted() {
        this.$bus.on('closeOrderForm', () => {
            this.drawer = false
            this.fetchProductOrders()
        })
    },
    created() {
        this.fetchProductOrders()
    },
    methods: {
        fetchProductOrders() {
            this.loadingTable = true
            let apiUrl = "/product/order"

            axios.get(apiUrl).then((res) => {
                this.productOrders = res.data
                this.loadingTable = false
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        fetchApplicableMaterials(matTypeId, colorId) {
            let apiUrl = "/production-material/calculated/"
                + matTypeId
                + "/"
                + colorId

            axios.get(apiUrl).then((res) => {
                this.applicableMaterials = res.data
            }).catch((error) => {
                ElMessageBox.alert('Something went wrong getting applicable materials' + error)
            })
        },
        handleOrderEdit(index, row) {
            this.selectedOrder = row
            this.drawer = true
        },
        handleOrderDelete(index, row) {
            let apiUrl = "/product/order/" + row.productOrderId

            axios.delete(apiUrl).then(() => {
                this.productOrders.splice(this.productOrders.indexOf(row), 1)
            }).catch(error => {
                ElMessageBox.alert('Something went wrong: ' + error)
            })
        },
        handleCloseDrawer() {
            ElMessageBox.confirm('Are you sure want to close the order form?')
                .then(() => {
                    this.selectedOrder = {name: '', product:{ sku:''}, sku:'', color:'', prodMatExpenditure:'', materialType:{materialTypeId: '', type: ''} }
                    this.drawer = false
                    this.fetchProductOrders()
                })
        },
        dateFormatter(row) {
            return moment(row.dateCreated).format('MM-DD-YYYY')
        },
        booleanFormatter(row) {
            return row.completed ? 'Yes' : 'No'
        },

        tableRowClassName({row}) {
            if(row.completed === true) {
                return 'success-row'
            }
            return ''
        },

        handleBeginOrderComplete(index, row) {
            if(row.product) {
                this.orderToComplete = row
                this.fetchApplicableMaterials(row.product.materialType.materialTypeId, row.color.colorId)
                this.materialSelectDialogVisible = true
            }
            else {
                this.orderToComplete = row
                this.fetchApplicableMaterials(row.productAccessory.materialType.materialTypeId, row.color.colorId)
                this.materialSelectDialogVisible = true
            }
        },
        handleCurrentChange(newRow) {
            this.selectedMaterial = newRow
        },
        handleOrderComplete() {
            let apiUrl = ''
            let calcMaterialUsedId = null
            this.orderToComplete.product ?
                apiUrl = "/product/order/complete/" + this.orderToComplete.productOrderId
                :
                apiUrl = "/product-accessory/order/complete/" + this.orderToComplete.accessoryOrderId

            calcMaterialUsedId = this.selectedMaterial.productionMaterialCalculatedId
            if(calcMaterialUsedId === undefined || null) {
                ElMessageBox.alert('Please select a material from the table', 'Error')
            }
            else {
                axios.put(apiUrl, null, {params: {calcMaterialUsedId}}).then((res) => {
                    console.log(res)
                    this.fetchProductOrders()
                    this.materialSelectDialogVisible = false
                }).catch((error) => {
                    ElMessageBox.alert('Something went wrong' + error)
                })
            }
        },
        handleCloseCompleteDialog() {
            this.materialSelectDialogVisible = false
        },

        handleAccOrderDelete(index, row) {
            let apiUrl = "/product-accessory/order/" + row.accessoryOrderId

            axios.delete(apiUrl).then(() => {
                this.fetchProductOrders()
            }).catch(error => {
                ElMessageBox.alert('Something went wrong: ' + error)
            })
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