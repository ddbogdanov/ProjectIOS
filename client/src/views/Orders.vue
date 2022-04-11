<template>
    <section class="products-view">
        <el-container class="products-view-container">
            <el-aside class="products-dash-aside">
                <el-container class="products-dash-container">
                    <el-header class="products-dash-header">
                        <h1 id="products-aside-label">ORDERS</h1>
                    </el-header>

                    <el-divider id="main-divider"/>

                    <el-row id="products-actions-row">
                        <el-col>
                            <h1 style="font-size: 1.5em;">Actions</h1>
                            <el-button-group style="margin-top: 10px;">
                                <el-button type="success" plain round :icon="DocumentAdd" @click="drawer=true">Create New Order</el-button>
                            </el-button-group>
                        </el-col>
                    </el-row>

                    <el-divider id="secondary-divider"/>

                    <el-row id="products-total-row">
                        <el-col>
                            <h1 id="products-total-label">Total Orders:</h1>
                            <label id="products-count-label">{{products.length}}</label>
                        </el-col>
                    </el-row>
                </el-container>
            </el-aside>
            <el-main class="products-table-main">
                <el-table :data="products.filter((data) => !search || data.name.toLowerCase().includes(search.toLowerCase()) || data.sku.includes(search))"
                            class="products-table" v-loading="loadingTable"
                            stripe height="100%">

                    <el-table-column type="expand" #default="scope">
                        <el-table :data="products[(products.indexOf(scope.row))].productAccessories" class="products-subtable">
                            <el-table-column :label="'Accessories - ' + products[(products.indexOf(scope.row))].sku">
                                <el-table-column prop="name" label="Name"></el-table-column>
                                <el-table-column label="Size">
                                    <el-table-column prop="size" label="Accessory Size (in)"></el-table-column>
                                    <el-table-column prop="prodMatExpenditure" label="Material to Produce (in)"></el-table-column>
                                </el-table-column>
                                <el-table-column prop="materialType.type" label="Material Type"></el-table-column>
                                <el-table-column label="Actions" align="right">
                                    <template #default="scope">
                                        <el-popconfirm
                                            :title="'Are you sure you want to delete accessory: ' + scope.row.name + '?'"
                                            confirm-button-type="danger"
                                            cancel-button-type="info"
                                            cancel-button-text="No, Dont Delete"
                                            icon-color="red"
                                            @confirm.stop="handleAccessoryDelete(scope.$index, scope.row)"
                                        >
                                            <template #reference>
                                                <el-button :icon="Delete" type="danger" circle></el-button>
                                            </template>
                                        </el-popconfirm>
                                    </template>
                                </el-table-column>
                            </el-table-column>
                        </el-table>
                    </el-table-column>

                    <el-table-column>
                        <template #header>
                            <el-row justify="space-around">
                                <el-col :span="2" >
                                    <el-button :icon="Refresh" circle plain size="small" @click.stop="fetchProducts"></el-button>
                                    <label style="margin-left:10px">Orders</label>
                                </el-col>
                                <el-col :span="5" style="margin-left:auto;">
                                    <el-input v-model="search" placeholder="Search by name or SKU" :suffix-icon="Search"/>
                                </el-col>
                            </el-row>
                        </template>

                        <el-table-column prop="name" label="Name" sortable></el-table-column>
                        <el-table-column prop="manufacturer" label="Manufacturer" sortable></el-table-column>
                        <el-table-column prop="color" label="Color" sortable></el-table-column>

                        <el-table-column label="Size">
                            <el-table-column prop="size" label="Product Size (in)" sortable></el-table-column>
                            <el-table-column prop="prodMatExpenditure" label="Material to Produce (in)" sortable></el-table-column>
                            <el-table-column prop="materialType.type" label="Material Type"></el-table-column>
                        </el-table-column>

                        <el-table-column label="Actions" align="right">
                            <template #default="scope">

                                <el-tooltip effect="light" content="Edit" placement="left">
                                    <el-button :icon="Edit" type="primary" circle @click="handleProductEdit(scope.$index, scope.row)"></el-button>
                                </el-tooltip>

                                <el-popconfirm
                                    :title="'Are you sure you want to delete SKU: ' + scope.row.sku + '?'"
                                    confirm-button-type="danger"
                                    cancel-button-type="info"
                                    cancel-button-text="No, Dont Delete"
                                    icon-color="red"
                                    @confirm.stop="handleProductDelete(scope.$index, scope.row)"
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
                <OrderForm :productProp='selectedProduct'/>
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

export default {
    name: "Orders",
    components: { OrderForm },
    data() {
        return {
            products: [ ],
            search: '',
            loadingTable: false,
            drawer: shallowRef(false),
            Delete: shallowRef(Delete), Edit: shallowRef(Edit), Search: shallowRef(Search), Refresh: shallowRef(Refresh), DocumentAdd: shallowRef(DocumentAdd),
            selectedProduct: {name: '', size:'', sku:'', prodMatExpenditure:'', materialType:{materialTypeId: '', type: ''}, productAccessories:[{}] }
        }
    },
    mounted() {
        this.$bus.on('closeOrderForm', () => {
            this.drawer = false
            this.fetchProducts()
        })
    },
    created() {
        this.fetchProducts()
    },
    methods: {
        fetchProducts() {
            this.loadingTable = true
            let apiUrl = "/product/order"

            axios.get(apiUrl).then((res) => {
                this.products = res.data
                this.loadingTable = false
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        handleProductEdit(index, row) {
            this.selectedProduct = row
            this.drawer = true
        },
        handleProductDelete(index, row) {
            let apiUrl = "/product/orders" + row.productId

            axios.delete(apiUrl).then(() => {
                this.products.splice(this.products.indexOf(row), 1)
            }).catch(error => {
                ElMessageBox.alert('Product has likely been used in an order and can\'t be deleted. ' + error)
            })
        },
        handleAccessoryDelete(index, row) {
            let apiUrl = "/product-accessories/" + row.productAccessoryId

            axios.delete(apiUrl).then(() => {
                this.fetchProducts() //TODO get index of parent(expanded row) then splice
            }).catch(error => {
                ElMessageBox.alert('Something went wrong.' + error)
            })
        },
        handleCloseDrawer() {
            ElMessageBox.confirm('Are you sure want to close the product form?')
                .then(() => {
                    this.selectedProduct = {name: '', size:'', sku:'', prodMatExpenditure:'', materialType:{materialTypeId: '', type: ''}, productAccessories:[{}] }
                    this.drawer = false
                    this.fetchProducts()
                })
        }
    }

}

</script>



<style scoped>
    .products-view-container {
        height: 100%;
    }
    .products-dash-aside {
        width: 415px;
        height: 100%;
        background-color: #545c64;
        color: white;
    }
    .products-dash-container {
        font-family: 'Product Sans', sans-serif;
        text-align: center;
        justify-items: center;
        align-content: center;
        justify-content: center;
    }
    .products-dash-header {
        display: flex;
        margin: 0;
        justify-content: center;
        align-items: center;
        text-align: center;
        vertical-align: middle;
    }

    .products-table-main {
        max-height: 100%;
    }
    .products-table {
        height: 100%;
        width: 100%;
    }
    .products-subtable {
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
    #products-total-row {

    }
    #products-aside-label {
        font-size: 2em;
    }
    #products-total-label {
        font-size: 1.5em;
    }
    #products-count-label {
        font-size: 6em;
    }
    #products-actions-row {

    }
</style>