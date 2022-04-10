<template>
    <el-menu
        :default-active="defaultIndex"
        class="nav-bar"
        mode="horizontal"
        @select="handleSelect"
        background-color="#41464b"
        text-color="white"
        active-text-color="#FFAE42"
        style="justify-content: right; flex-direction: row;"
    >
        <img src="../../public/logo.png" width="395" alt="img" style="margin-right: auto;">
        <el-menu-item index="/">Home</el-menu-item>
        <el-menu-item index="/products">Products</el-menu-item>
        <el-menu-item index="/material">Material</el-menu-item>
        <el-menu-item index="/login" style="margin-right: 20px;">Login</el-menu-item>
    </el-menu>
</template>

<script>
export default {
    name: "NavBar",

    data() {
        return {
            defaultIndex: this.$route.path
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