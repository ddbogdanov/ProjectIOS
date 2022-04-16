<template>
    <el-container class="form-container">
        <el-main>
            <el-form :model="productOrder" ref="orderFormRef" label-position="top" :rules="orderFormRules">

                <el-form-item label="Quantity" prop="quantity">
                    <el-input-number v-model="productOrder.quantity" :controls="false"></el-input-number>
                </el-form-item>

                <el-form-item label="Color">
                    <el-select v-model="productOrder.color" filterable clearable value-key="colorId">
                        <el-option
                            v-for="color in colors"
                            :key="color.colorId"
                            :label="color.color"
                            :value="color"
                        ></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item prop="accessoryOrders">
                    <template v-for="accessoryOrder in productOrder.accessoryOrders" :key="accessoryOrder.accessoryOrderId">
                        <el-divider class="accessory-divider"></el-divider>

                        <el-form-item label="Quantity">
                            <el-input-number v-model="accessoryOrder.quantity"></el-input-number>
                        </el-form-item>
                        <el-form-item label="Color">
                            <el-select v-model="accessoryOrder.color" filterable clearable value-key="colorId">
                                <el-option
                                    v-for="color in colors"
                                    :key="color.colorId"
                                    :label="color.color"
                                    :value="color"
                                ></el-option>
                            </el-select>
                        </el-form-item>
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
        </el-main>
    </el-container>
</template>

<script>
import axios from "axios";
import {ElMessageBox} from "element-plus";
import {Plus, Minus} from "@element-plus/icons-vue"
import {shallowRef} from "vue";

export default {
    name: "OrderForm",
    props: ['productProp', 'orderProp'],


    data() {
        return {
            colors: [],
            product: {}, editing: false,
            productOrder: {accessoryOrders: [], color: {color:''}, quantity: 0, comments: ''},
            Plus: shallowRef(Plus), Minus: shallowRef(Minus),

            orderFormRules: [{}]
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
                        let apiUrl = "/product/order/" + this.productOrderId

                        axios.put(apiUrl, this.productOrder).then((res) => {
                            console.log(res)
                            this.$refs.orderFormRef.resetFields()
                            this.$bus.trigger('closeOrderForm')
                        }).catch((error) => {
                            ElMessageBox.alert('Someting went wrong' + error)
                        })
                    }
                    else {
                        let apiUrl = "/product/" + this.productProp.productId + '/order'

                        axios.post(apiUrl, this.productOrder).then((res) => {
                            console.log(res)
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
        onCancel() {
            this.$refs.orderFormRef.resetFields()
            this.$bus.trigger('closeOrderForm')
        },
    }
}
</script>

<style scoped>

</style>