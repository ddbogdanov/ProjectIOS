<template>
    <el-form :model="product" ref="productFormRef">

        <el-form-item label="Name" required>
            <el-input v-model="product.name"></el-input>
        </el-form-item>

        <el-form-item label="Size" required>
            <el-input-number v-model="product.size"></el-input-number>
        </el-form-item>

        <el-form-item label="SKU" required>
            <el-input v-model="product.sku"></el-input>
        </el-form-item>

        <el-form-item label="Material to Produce (in)" required>
            <el-input-number v-model="product.prodMatExpenditure"></el-input-number>
        </el-form-item>

        <el-form-item label="Material Type" required>
            <el-select v-model="product.materialType" filterable allow-create>
                <el-option
                    v-for="materialType in materialTypes"
                    :key="materialType.name"
                    :label="materialType.name"
                    :value="materialType"
                ></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="Accessories">
            <el-select v-model="product.productAccessories"></el-select>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="onSubmit()">Submit</el-button>
            <el-button @click="onCancel()">Cancel</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import {reactive} from "vue";

export default {
    name: "ProductForm",

    props: ['productProp'],
    computed: {

    },
    data() {
        return {
            product: reactive({name: '', size:'', sku:'', prodMatExpenditure:'', materialType:[], productAccessories:[] }),
            productAccessories: [ ],
            materialTypes: [ ],
        }
    },
    methods: {
        onSubmit() {
            alert(JSON.stringify(this.product))
        },
        onCancel() {
            this.$bus.trigger('cancelProductForm', false)
        }
    }
}
</script>

<style scoped>

</style>