<template>
    <el-container class="form-container">
        <el-form :model="material" ref="materialFormRef" label-position="top">

            <el-form-item label="Name" prop="name">
                <el-input v-model="material.name"></el-input>
            </el-form-item>

            <el-form-item label="Amount" prop="amount">
                <el-input-number v-model="material.amount" :controls="false"></el-input-number>
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
                <el-tooltip content="Add a new color" effect="light" placement="right">
                    <el-popover v-model:visible="colorFormVisible" placement="right" :persistent="false" width="300" @hide="clearColorForm" title="Add New Color">
                        <template #reference>
                            <el-button :icon="Plus" style="margin-left:20px" @click="colorFormVisible=true"></el-button>
                        </template>
                        <el-form :model="color" :rules="colorFormRules" ref="colorFormRef" label-position="top">
                            <el-form-item label="Color" prop="color">
                                <el-input v-model="color.color"></el-input>
                            </el-form-item>
                        </el-form>
                        <el-form-item>
                            <el-button type="primary" @click="handleColorSubmit">Submit</el-button>
                            <el-button @click="handleColorCancel">Cancel</el-button>
                        </el-form-item>
                    </el-popover>
                </el-tooltip>
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
                <el-tooltip content="Add a new manufacturer" effect="light" placement="right">
                    <el-popover v-model:visible="manufacturerFormVisible" placement="right" :persistent="false" width="300" @hide="clearManufacturerForm" title="Add New Manufacturer">
                        <template #reference>
                            <el-button :icon="Plus" style="margin-left:20px" @click="manufacturerFormVisible=true"></el-button>
                        </template>
                        <el-form :model="manufacturer" :rules="manufacturerFormRules" ref="manufacturerFormRef" label-position="top">
                            <el-form-item label="Manufacturer" prop="manufacturer">
                                <el-input v-model="manufacturer.manufacturer"></el-input>
                            </el-form-item>
                        </el-form>
                        <el-form-item>
                            <el-button type="primary" @click="handleManufacturerSubmit">Submit</el-button>
                            <el-button @click="handleManufacturerCancel">Cancel</el-button>
                        </el-form-item>
                    </el-popover>
                </el-tooltip>
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
                <el-tooltip content="Add a new material type" effect="light" placement="right">
                    <el-popover v-model:visible="materialTypeFormVisible" placement="right" :persistent="false" width="300" @hide="clearMaterialTypeForm" title="Add New Material Type">
                        <template #reference>
                            <el-button :icon="Plus" style="margin-left:20px" @click="materialTypeFormVisible=true"></el-button>
                        </template>
                        <el-form :model="materialType" :rules="materialTypeFormRules" ref="materialTypeFormRef" label-position="top">
                            <el-form-item label="Material Type" prop="type">
                                <el-input v-model="materialType.type"></el-input>
                            </el-form-item>
                        </el-form>
                        <el-form-item>
                            <el-button type="primary" @click="handleMaterialTypeSubmit">Submit</el-button>
                            <el-button @click="handleMaterialTypeCancel">Cancel</el-button>
                        </el-form-item>
                    </el-popover>
                </el-tooltip>
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
import {Plus} from "@element-plus/icons-vue"
import {shallowRef} from "vue";

export default {
    name: "MaterialForm",
    props: ['materialProp'],

    data() {
        return {
            material: {name: '', amount: 0, color: {color: ''}, manufacturer: {manufacturer: ''}, materialType: {type: ''}},
            colors: [],
            manufacturers: [],
            materialTypes: [],
            Plus: shallowRef(Plus),

            colorFormVisible: false,manufacturerFormVisible: false, materialTypeFormVisible: false,
            color: {color: ''}, manufacturer: {manufacturer: ''}, materialType: {type: ''},

            materialTypeFormRules: {
                type: [
                    {
                        required: true,
                        message: 'Type is required',
                        trigger: 'blur'
                    }
                ],
            },
            manufacturerFormRules: {
                manufacturer: [
                    {
                        required: true,
                        message: 'Manufacturer is required',
                        trigger: 'blur'
                    }
                ],
            },
            colorFormRules: {
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

        // COLOR FORM FUNCTIONS
        clearColorForm() {
            this.$refs.colorFormRef.resetFields()
        },
        handleColorSubmit() {
            this.$refs.colorFormRef.validate((valid) => {
                if(!valid) return false
                else {
                    let apiUrl = "/color"
                    this.color.color.toUpperCase()

                    axios.post(apiUrl, this.color).then((res) => {
                        console.log(res)
                        this.clearColorForm()
                        this.colorFormVisible = false
                        this.fetchColors()
                    }).catch((error) => {
                        ElMessageBox.alert("Something went wrong: " + error)
                    })
                }
            })
        },
        handleColorCancel() {
            this.colorFormVisible = false
            this.$refs.colorFormRef.resetFields()
        },

        // MANUFACTURER FORM FUNCTIONS
        clearManufacturerForm() {
            this.colorFormVisible = false
            this.$refs.manufacturerFormRef.resetFields()
        },
        handleManufacturerSubmit() {
            this.$refs.manufacturerFormRef.validate((valid) => {
                if(!valid) return false
                else {
                    let apiUrl = "/manufacturer"
                    this.manufacturer.manufacturer.toUpperCase()

                    axios.post(apiUrl, this.manufacturer).then((res) => {
                        console.log(res)
                        this.clearManufacturerForm()
                        this.manufacturerFormVisible = false
                        this.fetchManufacturers()
                    }).catch((error) => {
                        ElMessageBox.alert("Something went wrong: " + error)
                    })
                }
            })
        },
        handleManufacturerCancel() {
            this.clearManufacturerForm()
            this.manufacturerFormVisible = false
        },

        // MATERIAL FORM FUNCTIONS
        clearMaterialTypeForm() {
            this.$refs.materialTypeFormRef.resetFields()
        },
        handleMaterialTypeSubmit() {
            this.$refs.materialTypeFormRef.validate((valid) => {
                if(!valid) return false
                else {
                    let apiUrl = "/material-type"
                    this.materialType.type.toUpperCase()

                    axios.post(apiUrl, this.materialType).then((res) => {
                        console.log(res)
                        this.clearMaterialTypeForm()
                        this.materialTypeFormVisible = false
                        this.fetchMaterialTypes()
                    }).catch((error) => {
                        ElMessageBox.alert("Something went wrong" + error)
                    })
                }
            })
        },
        handleMaterialTypeCancel() {
            this.clearMaterialTypeForm()
            this.materialTypeFormVisible = false
        }
    }
}
</script>

<style scoped>

</style>