<template>
    <el-container class="form-container">
        <el-main>
            <el-form :model="product" ref="productFormRef" label-position="top" :rules="productFormRules">
                <h3>Product</h3>
                <el-divider></el-divider>
                <el-form-item prop="productId"></el-form-item>

                <el-row>
                    <el-col>
                        <el-form-item label="Name" prop="name">
                            <el-input v-model="product.name"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col>
                        <el-form-item label="Color" prop="color">
                            <el-input v-model="product.sku"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row justify="space-between">
                    <el-col :span="7">
                        <el-form-item label="Size" prop="size">
                            <el-input-number v-model="product.size" :controls="false"></el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="Material to Produce" prop="prodMatExpenditure">
                            <el-input-number v-model="product.prodMatExpenditure" :controls="false"></el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Material Type" prop="materialType">
                            <el-select v-model="product.materialType" filterable clearable value-key="materialTypeId">
                                <el-option
                                    v-for="materialType in materialTypes"
                                    :key="materialType.materialTypeId"
                                    :label="materialType.type"
                                    :value="materialType"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-divider></el-divider>
                <div style="display: inline-flex">
                    <h3>Accessories</h3>
                    <el-divider direction="vertical"></el-divider>
                    <el-tooltip effect="light" content="Add an additional accessory" placement="right">
                        <el-button :icon="Plus" type="plain" size="small" circle plain @click="addOneAccessory"></el-button>
                    </el-tooltip>
                </div>


                <el-form-item prop="productAccessories" style="font-size: 2em">
                        <template v-for="productAccessory in product.productAccessories" :key="productAccessory.productAccessoryId">
                            <el-divider class="accessory-divider"></el-divider>

                            <el-row style="width: 100%; vertical-align: center;">
                                <div>
                                    <div style="display: inline-flex; vertical-align:middle;">
                                        <h1 style="font-size: 1em;">Accessory: {{product.productAccessories.indexOf(productAccessory)}}</h1>
                                        <el-divider direction="vertical"></el-divider>
                                        <el-tooltip effect="light" content="Remove this accessory. (This will NOT delete the accessory if it already exists)" placement="right">
                                            <el-button :icon="Minus" type="danger" size="small" circle plain @click="removeOneAccessory(productAccessory)"></el-button>
                                        </el-tooltip>
                                    </div>
                                <el-divider style="width: 100%; margin-top: 5px"></el-divider>
                                </div>
                            </el-row>

                            <el-col>
                                <el-form-item label="Name">
                                    <el-input v-model="productAccessory.name" ></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col style="margin-top: 20px">
                                <el-form-item label="Size">
                                    <el-input-number v-model="productAccessory.size" :controls="false"></el-input-number>
                                </el-form-item>
                            </el-col>

                            <el-col style="margin-top: 20px">
                                <el-form-item label="Material to Produce">
                                    <el-input-number v-model="productAccessory.prodMatExpenditure" :controls="false"></el-input-number>
                                </el-form-item>
                            </el-col>

                            <el-col style="margin-top: 20px">
                                <el-form-item label="Material Type">
                                    <el-select v-model="productAccessory.materialType" filterable clearable value-key="materialTypeId">
                                        <el-option
                                            v-for="materialType in materialTypes"
                                            :key="materialType.materialTypeId"
                                            :label="materialType.type"
                                            :value="materialType"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </template>
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
    name: "ProductForm",
    props: ['productProp'],


    data() {
        return {
            product: { name: '', size:'', sku:'', prodMatExpenditure:'', materialType:{materialTypeId: '', type: ''}, productAccessories:[] },
            materialTypes: [ ],
            materialType: { },
            Plus: shallowRef(Plus), Minus: shallowRef(Minus),

            productFormRules: {
                name: [
                    {
                        required: true,
                        message: 'Name is required',
                        trigger: 'blur'
                    }
                ],
                sku: [
                    {
                        required: true,
                        message: 'Sku is required',
                        trigger: 'blur'
                    }
                ],
                size: [
                    {
                        required: true,
                        message: 'Size is required and must be a number',
                        trigger: 'blur',
                    }
                ],
                prodMatExpenditure: [
                    {
                        required: true,
                        message: 'Material to produce is required and must be a number',
                        trigger: 'blur'
                    }
                ],
                materialType: [
                    {
                        required: true,
                        message: 'Material type is required',
                        trigger: blur
                    }
                ]
            }
        }
    },
    mounted() {
        this.$bus.on('clearProductForm', () => {
            this.onCancel()
        })
        if(this.productProp !== { }) {
            this.product = this.productProp
        }
    },
    created() {
        this.fetchMaterialTypes()
    },
    methods: {
        onSubmit() {
            this.$refs.productFormRef.validate((valid) => {
                if(!valid) return false
                else {
                    let apiUrl = "/product"

                    axios.post(apiUrl, this.product).then((res) => {
                        console.log(res)
                        this.$refs.productFormRef.resetFields()
                        this.$bus.trigger('closeProductForm')
                    }).catch((error) => {
                        ElMessageBox.alert('Something went wrong: ' + error)
                    })
                }
            })
        },
        onCancel() {
            this.$refs.productFormRef.resetFields()
            this.$bus.trigger('closeProductForm')
        },
        fetchMaterialTypes() {
            let apiUrl = "/material-type"

            axios.get(apiUrl).then((res) => {
                this.materialTypes = res.data
            }).catch((error) => {
                ElMessageBox.alert('Something went wrong: ' + error)
            })
        },
        addOneAccessory() {
            this.product.productAccessories.push({})
        },
        removeOneAccessory(row) {
            this.product.productAccessories.splice(this.product.productAccessories.indexOf(row), 1)
        }
    }
}
</script>

<style scoped>

</style>