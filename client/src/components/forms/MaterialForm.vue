<template>
    <el-container class="form-container">
        <el-form :model="material" ref="materialFormRef" label-position="top">

            <el-form-item label="Name" prop="name">
                <el-input v-model="material.name"></el-input>
            </el-form-item>

            <el-form-item label="Amount" prop="amount">
                <el-input-number v-model="material.amount"></el-input-number>
            </el-form-item>

            <el-form-item label="Color">
                <el-select v-model="material.color" filterable clearable value-key="colorId">
                    <el-option
                        v-for="color in colors"
                        :key="color.colorId"
                        :label="color.color"
                        :value="color"
                    ></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="Manufacturer">
                <el-select v-model="material.manufacturer" filterable clearable value-key="manufacturerId">
                    <el-option
                        v-for="manufacturer in manufacturers"
                        :key="manufacturer.manufacturerId"
                        :label="manufacturer.manufacturer"
                        :value="manufacturer"
                    ></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="Material Type">
                <el-select v-model="material.materialType" filterable clearable value-key="materialTypeId">
                    <el-option
                        v-for="materialType in materialTypes"
                        :key="materialType.materialTypeId"
                        :label="materialType.type"
                        :value="materialType"
                    ></el-option>
                </el-select>
            </el-form-item>

            <el-row>
                <el-col>
                    <el-form-item>
                        <el-button type="primary" @click.stop="onSubmit()">Submit</el-button>
                        <el-button @click="onCancel()">Cancel</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </el-container>
</template>

<script>
import axios from "axios";
import {ElMessageBox} from "element-plus";

export default {
    name: "MaterialForm",
    props: ['materialProp'],

    data() {
        return {
            material: {productionMaterialId: 0, name: '', amount: 0, color: {colorId: 0, color: ''}, manufacturer: {manufacturerId: 0, manufacturer: ''}, materialType: {materialTypeId: 0, type: ''}},
            colors: [],
            manufacturers: [],
            materialTypes: []
        }
    },
    mounted() {
        this.$bus.on('clearMaterialForm', () => { //UNUSED???
            this.onCancel()
        })
        if(this.materialProp !== { }) {
            this.material = this.materialProp
        }
    },
    created() {
        this.fetchColors()
        this.fetchManufacturers()
        this.fetchMaterialTypes()
    },
    methods: {
        fetchColors() {
            let apiUrl = "/color"

            axios.get(apiUrl).then((res) => {
                this.colors = res.data
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        fetchManufacturers() {
            let apiUrl = "/manufacturer"

            axios.get(apiUrl).then((res) => {
                this.manufacturers = res.data
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        fetchMaterialTypes() {
            let apiUrl = "/material-type"

            axios.get(apiUrl).then((res) => {
                this.materialTypes = res.data
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        onSubmit() {
            if(this.material.productionMaterialId !== undefined) {
                let apiUrl = "/production-material/" + this.material.productionMaterialId

                axios.put(apiUrl, this.material).then((res) => {
                    console.log(res)
                    this.$refs.materialFormRef.resetFields()
                    this.$bus.trigger('closeMaterialForm')
                }).catch((error) => {
                    ElMessageBox.alert('Something went wrong: ' + error)
                })
            } else {
                let apiUrl = "/production-material"

                axios.post(apiUrl, this.material).then((res) => {
                    console.log(res)
                    this.$refs.materialFormRef.resetFields()
                    this.$bus.trigger('closeMaterialForm')
                }).catch((error) => {
                    ElMessageBox.alert('Something went wrong: ' + error)
                })
            }
        },
        onCancel() {
            this.$refs.materialFormRef.resetFields()
            this.$bus.trigger('closeMaterialForm')
        },
    }
}
</script>

<style scoped>

</style>