<template>
    <el-container class="form-container">
        <el-main style="padding-top: 0">
            <el-form :model="productOrder" ref="orderFormRef" label-position="top" :rules="orderFormRules">
                <el-divider>
                    <h3>Product Order</h3>
                </el-divider>

                <el-form-item label="Quantity" prop="quantity">
                    <el-input-number v-model="productOrder.quantity" :controls="false"></el-input-number>
                </el-form-item>

                <el-form-item label="Color" prop="color">
                    <el-select v-model="productOrder.color" filterable clearable value-key="colorId">
                        <el-option
                            v-for="color in colors"
                            :key="color.colorId"
                            :label="color.color"
                            :value="color"
                        ></el-option>
                    </el-select>
                    <label style="margin-left: 10px; color: gray">{{productOrder.color.color}}</label>
                </el-form-item>

                <el-divider style="margin-top: 40px">

                <div style="display: inline-flex; align-items: center;">
                    <h3>Accessory Orders</h3>
                    <el-divider direction="vertical"></el-divider>
                    <el-tooltip effect="light" content="Add an additional accessory order" placement="right">
                        <el-button :icon="Plus" size="small" circle plain @click="addOneAccessoryOrder"></el-button>
                    </el-tooltip>
                </div>
                </el-divider>

                <el-form-item prop="accessoryOrders">
                    <template v-for="accessoryOrder in productOrder.accessoryOrders" :key="accessoryOrder.accessoryOrderId">
                        <div v-if="!accessoryOrder.completed">
                        <el-row style="width: 100%; align-items: center; justify-content: center">

                                <div style="display: inline-flex; align-items: center; justify-content: start">
                                    <el-tooltip effect="light" content="Remove this accessory from order" placement="right">
                                        <el-button :icon="Minus" type="danger" size="small" circle plain @click="removeOneAccessoryOrder(accessoryOrder)"></el-button>
                                    </el-tooltip>
                                    <el-divider direction="vertical"></el-divider>
                                    <h1 style="font-size: 1em;">Accessory:</h1>
                                    <p style="margin-left: 10px;">{{accessoryOrder.productAccessory.name}}</p>
                                </div>
                                <el-divider style="width: 100%; margin-top: 5px"></el-divider>


                        </el-row>
                        <el-form-item label="Quantity">
                            <el-input-number v-model="accessoryOrder.quantity" :controls="false"></el-input-number>
                        </el-form-item>
                        <el-form-item label="Color" style="margin-top: 5px">
                            <el-select v-model="accessoryOrder.color" filterable clearable value-key="colorId">
                                <el-option
                                    v-for="color in colors"
                                    :key="color.colorId"
                                    :label="color.color"
                                    :value="color"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        </div>
                        <el-divider class="accessory-divider"></el-divider>
                    </template>
                </el-form-item>
                <el-form-item label="Comments" prop="comments">
                    <el-input v-model="productOrder.comments" type="textarea"></el-input>
                </el-form-item>

                <el-divider></el-divider>
                <el-row>
                    <el-col>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit()">Submit</el-button>
                            <el-button @click="onCancel()">Cancel</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>

            <el-dialog v-model="accessoryOrderDialogVisible" title="Add Accessory(ies) to Order" destroy-on-close>
                <el-table :data="accessories.filter((data) => !dialogSearch || data.name.toLowerCase().includes(dialogSearch.toLowerCase()))"
                          ref="accTableRef" height="50vh" @selection-change="handleSelectionChange"
                >
                    <el-table-column>
                        <template #header>
                            <el-row justify="space-around" style="align-items: center"> <!-- TODO: ALIGN ITEMS CENTER EVERYWHERE -->
                                <el-col :span="5" >
                                    <label style="margin-left:10px; font-family: 'Product Sans', sans-serif">Accessories</label>
                                </el-col>
                                <el-col :span="5" style="margin-left:auto;">
                                    <el-input v-model="dialogSearch" placeholder="Search by name" :suffix-icon="Search"/>
                                </el-col>
                            </el-row>
                        </template>
                        <el-table-column type="selection" width="55"/>
                        <el-table-column prop="name" label="Name"></el-table-column>
                        <el-table-column prop="size" label="Size"></el-table-column>
                        <el-table-column prop="materialType.type" label="Material Type"></el-table-column>
                    </el-table-column>
                </el-table>

                <template #footer>
                    <el-button plain @click="handleCloseAccessoryDialog">Cancel</el-button>
                    <el-button type="success" @click.stop="handleAddAccessories">Confirm</el-button>
                </template>
            </el-dialog>
        </el-main>
    </el-container>
</template>

<script>
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";
import {Plus, Minus, Search} from "@element-plus/icons-vue"
import {shallowRef} from "vue";

export default {
    name: "OrderForm",
    props: ['productProp', 'orderProp'],


    data() {
        return {
            colors: [],
            product: {}, editing: false,
            productOrder: {accessoryOrders: [], color: {color:''}, quantity: 0, comments: ''},
            Plus: shallowRef(Plus), Minus: shallowRef(Minus), Search: shallowRef(Search),

            accessoryOrderDialogVisible: false, accessories: [], dialogSearch: '', tableSelection: [],
            orderFormRules: {
                quantity: [
                    {
                        required: true,
                        message: 'Quantity is required',
                        trigger: 'blur'
                    },
                ],
                color: [
                    {
                        required: true,
                        message: 'Color is required',
                        trigger: 'blur'
                    }
                ],
            }
        }
    },
    mounted() {
        this.$bus.on('clearProductForm', () => {
            this.onCancel()
        })
        if(JSON.stringify(this.productProp) !== '{}') {
            this.product = this.productProp

            for(let i=0; i<this.product.productAccessories.length; i++) {
                this.productOrder.accessoryOrders.push({})
                this.productOrder.accessoryOrders[i].productAccessory = this.product.productAccessories[i]
            }
        }
        else if(JSON.stringify(this.orderProp) !== '{}') {
            this.editing = true
            this.productOrder = this.orderProp
        }
        else {
            ElMessageBox.alert('Something went wrong loading the order form')
        }
    },
    created() {
        this.fetchColors()
    },
    methods: {
        onSubmit() {
            this.$refs.orderFormRef.validate((valid) => {
                if(!valid) return false
                else {

                    if(this.editing) {
                        let apiUrl = "/product/order/" + this.productOrder.productOrderId

                        axios.put(apiUrl, this.productOrder).then((res) => {
                            console.log(res)
                            this.$refs.orderFormRef.resetFields()
                            this.$bus.trigger('closeOrderForm')
                        }).catch((error) => {
                            ElMessageBox.alert('Something went wrong' + error)
                        })
                    }
                    else {
                        let apiUrl = "/product/" + this.productProp.productId + '/order'

                        axios.post(apiUrl, this.productOrder).then((res) => {
                            console.log(res)
                            ElMessage.success('Order created successfully!')
                            this.$refs.orderFormRef.resetFields()
                            this.$bus.trigger('closeOrderForm')
                        }).catch((error) => {
                            ElMessageBox.alert('Something went wrong: ' + error)
                        })
                    }
                }
            })
        },
        fetchColors() {
            let apiUrl = "/color"

            axios.get(apiUrl).then((res) => {
                this.colors = res.data
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        fetchAccessories() {
            let apiUrl = "/product-accessories"

            axios.get(apiUrl).then((res) => {
                this.accessories = res.data
            }).catch((error) => {
                ElMessageBox.alert('Something went wrong' + error)
            })
        },
        onCancel() {
            this.$refs.orderFormRef.resetFields()
            this.$bus.trigger('closeOrderForm')
        },

        addOneAccessoryOrder() {
            this.fetchAccessories()
            this.accessoryOrderDialogVisible = true
        },
        handleSelectionChange(selection) {
            this.tableSelection = selection
        },
        handleCloseAccessoryDialog() {
            this.accessoryOrderDialogVisible = false
        },
        handleAddAccessories() {
            for(let i=0; i<this.tableSelection.length; i++) {
                let ts = {}
                ts["productAccessory"] = this.tableSelection[i]
                this.productOrder.accessoryOrders.push(ts)
            }
            this.accessoryOrderDialogVisible = false
        },
        removeOneAccessoryOrder(accessory) {
            this.productOrder.accessoryOrders.splice(this.productOrder.accessoryOrders.indexOf(accessory), 1)
        }
    }
}
</script>

<style scoped>

</style>