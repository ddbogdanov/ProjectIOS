<template>
    <el-menu
        :default-active="currentRoute"
        class="nav-bar"
        mode="horizontal"
        @select="handleSelect"
        background-color="#41464b"
        text-color="white"
        active-text-color="#FFAE42"
        style="justify-content: right; flex-direction: row;"
    >
        <el-image :src="companyLogo" fit="scale-down" style="width: 360px; margin-left: 10px; margin-right: auto;" class="hidden-xs-only"/>
        <el-menu-item index="/">Home</el-menu-item>
        <el-menu-item index="/products">Products</el-menu-item>
        <el-menu-item index="/material">Material</el-menu-item>
        <el-menu-item index="/orders">Orders</el-menu-item>
        <el-menu-item index="/login">Login</el-menu-item>
    </el-menu>
</template>

<script>
import CompanyLogo from '../../public/logo-white.png'

export default {
    name: "NavBar",
    data() {
        return {
            companyLogo: CompanyLogo
        }
    },
    computed: {
        currentRoute() {
            return this.$route.path
        }
    },
    methods: {
        handleSelect(index) {
            this.$router.push(index)
        }
    }
}
</script>

<style scoped>

</style>