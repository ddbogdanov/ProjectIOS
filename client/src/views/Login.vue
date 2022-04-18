<template>
    <el-container class="login-background">
        <el-header style="width:40%; margin-left: auto; margin-right: auto; margin-top: 10px">
               <el-image :src="companyLogo" fit="fill" style="width: 70%"></el-image>
        </el-header>
        <el-main style="display: flex; justify-content: center; align-items: center; padding-top: 0">
            <el-row class="login" style="width: 40%; height: 60%">
                <el-col :span="12">
                    <el-image :src="trees" fit="none" style="width: 100%; height: 100%; border-radius: 15px 0 0 15px;"></el-image>
                </el-col>
                <el-divider direction="vertical" style="height: 100%; background-color: #424754; margin-left: 0; margin-right: 0; "></el-divider>
                <el-col :span="11">
                    <LoginForm/>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</template>

<script>
import LoginForm from "@/components/forms/LoginForm"
import TreesImg from "../../public/trees.png"
import CompanyLogo from '../../public/logo-white.png'

export default {
    name: 'HelloWorld',
    components: {LoginForm},

    data() {
        return {
            trees: TreesImg,
            companyLogo: CompanyLogo
        }
    }
}
</script>

<style scoped>
.login-background {
    height: 100%;
    background: #33333D;
}
.login {
    background-color: white;
    border-radius: 15px;

}
</style>
