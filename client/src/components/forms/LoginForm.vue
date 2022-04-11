<template>
<div class="login-form">
    <h1>Login</h1>
    <el-form ref="formRef" :model="account" label-position="right">
        <el-form-item label="Username" prop="username">
            <el-input v-model="account.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
            <el-input v-model="account.password" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">Sign-in</el-button>
            <el-button @click="onCancel">Clear</el-button>
        </el-form-item>
    </el-form>
</div>
</template>

<script>
import axios from "axios"
//import { mapActions } from "vuex";

export default {
    name: "LoginForm",

    data() {
        return {
            account: {
                username: '',
                password: '',
            }
        }
    },
    methods: {
        onSubmit() {
            let apiUrl = '/user/login'

            axios.post(apiUrl, this.account).then((res) => {
                console.log(res.data)
                alert("Successfully logged in")
                this.$router.push('/home')
            }).catch(error => {
                alert(error)
                this.$router.go()
            })
        },
        onCancel() {
            console.log("CLEARING")
            this.$refs.formRef.resetFields()
        }
    }
}
</script>

<style scoped>

</style>