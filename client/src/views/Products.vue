<template>
    <section class="products-view">
        <el-container class="products-view-container">
            <el-aside class="products-dash-aside">
                <el-container class="products-dash-container">
                    <el-header class="el-header"><h1 id="products-aside-label">PRODUCTS</h1></el-header>
                    <el-divider/>
                    <el-col id="products-total-column">
                        <h1 id="products-total-label">Total Products:</h1>
                        <label id="products-count-label">{{products.length}}</label>
                    </el-col>
                </el-container>
            </el-aside>
            <el-main class="products-table-main">
                <el-table :data="products.filter((data) => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                            class="products-table" :default-sort="{prop: 'name', order: 'descending'}">
                    <el-table-column type="expand" #default="scope">
                        <el-table :data="products[(scope.$index)].productAccessories" style="padding-left: 45px" max-height="200px">
                            <el-table-column prop="name" label="Name"></el-table-column>
                            <el-table-column prop="size" label="Size"></el-table-column>
                            <el-table-column prop="materialType.type" label="Material Type"></el-table-column>
                        </el-table>
                    </el-table-column>
                    <el-table-column>
                        <template #header>
                            <el-row justify="space-around" align="center">
                                <el-col :span="2" >
                                    <label style="display: inline">Products</label>
                                </el-col>
                                <el-col :span="6" style="margin-right:auto;">
                                    <el-input v-model="search" size="small" placeholder="Search by name"/>
                                </el-col>
                            </el-row>
                        </template>
                        <el-table-column prop="name" label="Name"></el-table-column>
                        <el-table-column prop="sku" label="SKU"></el-table-column>
                        <el-table-column prop="size" label="Size"></el-table-column>
                    </el-table-column>
                </el-table>
            </el-main>
        </el-container>
    </section>
</template>



<script>
import axios from "axios";

export default {
    name: "Products",

    data() {
        return {
            products: [ ],
            search: '',
        }
    },
    created() {
        this.fetchProducts()
    },
    methods: {
        fetchProducts() {
            let apiUrl = "/product"

            axios.get(apiUrl).then((res) => {
                this.products = res.data
            }).catch(error => {
                alert(error)
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
    }
    .el-header {
        display: flex;
        margin: 0;
        justify-content: center;
        align-items: center;
        text-align: center;
        vertical-align: middle;
    }
    el-divider {
        height: 2px;
        color: #FFAE42;
        background-color: #FFAE42;
    }

    #products-total-column {
        margin-top: 20px;
    }
    #products-aside-label {
        font-size: 1.5em
    }
    #products-total-label {
        font-size: 1.5em
    }
    #products-count-label {
        font-size: 6em
    }
</style>