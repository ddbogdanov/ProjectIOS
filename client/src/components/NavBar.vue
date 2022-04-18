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
        <el-menu-item index="/products">Products</el-menu-item>
        <el-menu-item index="/material">Material</el-menu-item>
        <el-menu-item index="/orders">Orders</el-menu-item>
        <el-menu-item index="/logout">Logout</el-menu-item>
    </el-menu>
</template>

<script>
import CompanyLogo from '../../public/logo-white.png'
import {ElMessageBox} from "element-plus";

export default {
    name: "NavBar",
    data() {
        return {
            companyLogo: CompanyLogo,
            currentRoute: this.$route.path
        }
    },
    mounted() {
        this.currentRoute = this.$route.path
    },
    methods: {
        handleSelect(index) {
            if(index === "/logout") {
                ElMessageBox.confirm('Logout? Are you sure?', 'Confirm logout', {
                    confirmButtonText: 'Yes',
                    cancelButtonText: 'No',
                }).then(() => {
                    this.$bus.trigger('logout')
                }).catch(() => {
                    this.$router.push(this.$route.path)
                })
            } else {
                this.$router.push(index)
            }
        }
    }
}
</script>

<style scoped>

</style>