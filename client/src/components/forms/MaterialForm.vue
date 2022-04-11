<template>
    <el-container class="form-container">
        <el-form label-position="top">
            <el-form-item label="Name" prop="name">
                <el-input v-model="material.name"></el-input>
            </el-form-item>
            <el-form-item label="Amount" prop="amount">
                <el-input-number v-model="material.amount"></el-input-number>
            </el-form-item>

            <el-form-item label="Color">
                <el-select v-model="material.color"></el-select>
            </el-form-item>

            <el-form-item label="Manufacturer">
                <el-select v-model="material.manufacturer"></el-select>
            </el-form-item>

            <el-form-item label="Material Type">
                <el-select v-model="material.materialType"></el-select>
            </el-form-item>
        </el-form>
    </el-container>
</template>

<script>
export default {
    name: "MaterialForm",

    data() {
        return {
            material: { name: '', amount: '', color: {color: ''}, manufacturer: {manufacturer: ''}, materialType: {type: ''}}
        }
    }
}
</script>

<style scoped>

</style>