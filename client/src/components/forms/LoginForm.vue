<template>
<el-container style="height: 100%">
    <el-main class="login-form" style="padding-right: 0">
        <el-form ref="formRef" :model="account" :rules="loginFormRules" label-position="top" style="display: flex; flex-direction: column; height: 100%">
            <h1 style="font-size: 1.5em">Login</h1>
            <el-divider style="background-color: #FFAE42"></el-divider>
            <el-row>
                <el-col>
                    <el-form-item label="Username" prop="username">
                        <el-input v-model="account.username"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item label="Password" prop="password">
                        <el-input v-model="account.password" type="password" show-password></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row justify="center" style="margin-top: 30px">
                <el-form-item style="width: 100%">
                    <el-button id="login-button" type="primary" round @click="onSubmit">Sign-in</el-button>
                </el-form-item>
            </el-row>
            <el-row justify="center" style="margin-top: auto; align-items: center">
                <el-divider style="margin-top:auto; margin-bottom: 0"></el-divider>
                <el-form-item style="margin-top: auto; margin-bottom: 0; width: 100%; align-items: center">
                    <el-button type="text" size="small" @click="onRegister" style="width: 100%;">REGISTER</el-button>
                </el-form-item>
            </el-row>

        </el-form>
        <el-dialog v-model="registerDialogVisible" title="Register" width="25%">
            <el-form :model="registerUser" label-position="top" :rules="loginFormRules" ref="registerFormRef">
                <el-form-item label="Username" prop="username">
                    <el-input v-model="registerUser.username"></el-input>
                </el-form-item>
                <el-form-item label="Password" prop="password">
                    <el-input show-password v-model="registerUser.password"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button type="primary" @click="onSubmitUser">Submit</el-button>
                <el-button @click="onCancelUser">Cancel</el-button>
            </template>
        </el-dialog>
    </el-main>
</el-container>
</template>

<script>
import axios from "axios"
import {ElMessage, ElMessageBox} from "element-plus";


export default {
    name: "LoginForm",

    data() {
        return {
            registerDialogVisible: false,
            account: {
                username: '',
                password: '',
            },
            registerUser: {
                username: '',
                password: '',
            },

            loginFormRules: {
                username: [
                    {
                        required: true,
                        message: 'Username is required',
                        trigger: 'blur'
                    }
                ],
                password: [
                    {
                        required: true,
                        message: 'Password is required',
                        trigger: 'blur'

                    }
                ]
            }
        }
    },
    methods: {
        onSubmit() {
            this.$refs.formRef.validate((valid) => {
                if(!valid) return false
                else {
                    let apiUrl = '/user/login'

                    axios.post(apiUrl, this.account).then((res) => {
                        console.log(res)
                        ElMessage.success('Logged in!')
                        this.$bus.trigger('login')
                    }).catch(error => {
                        if(error.response.status === 401) {
                            ElMessage.error('Username and/or password not correct')
                        } else {
                            ElMessageBox.alert('Something went wrong ' + error)
                        }
                    })
                }
            })
        },
        onRegister() {
            this.registerDialogVisible = true
        },
        onSubmitUser() {
            this.$refs.registerFormRef.validate((valid) => {
                if(!valid) return false
                else {
                    let apiUrl = "/user"

                    axios.post(apiUrl, this.registerUser).then((res) => {
                        console.log(res)
                        ElMessage.success('New user added successfully')
                        this.$refs.registerFormRef.resetFields()
                        this.registerDialogVisible = false
                    }).catch((error) => {
                        if(error.response.status === 409) {
                            ElMessage.error("This user already exists")
                        }
                        else {
                            ElMessageBox.alert('Something went wrong ' + error)
                        }
                    })
                }
            })
        },
        onCancelUser() {
            this.$refs.registerFormRef.resetFields()
            this.registerDialogVisible = false
        }
    }
}
</script>

<style scoped>
.login-form {
    font-family: "Product Sans", sans-serif;
}
#login-button {
    width: 100%;
    border: none;
    background: rgb(255,174,66);
    background: linear-gradient(90deg, rgba(255,174,66,1) 0%, rgba(131,41,255,1) 50%, rgba(54,77,255,1) 100%);
}
</style>