<template>
    <NavBar class="nav-bar" v-if="isLoggedIn"/>
    <router-view v-if="isLoggedIn"/>
    <Login v-if="!isLoggedIn"/>
</template>

<script>
import Login from "@/views/Login";
import NavBar from "@/components/NavBar";

export default {
    name: 'App',

    data() {
        return {
            isLoggedIn: false
        }
    },
    mounted() {
        this.$bus.on('login', () => {
            this.isLoggedIn = true
            this.$router.push('/products')
        })
        this.$bus.on('logout', () => {
            this.isLoggedIn = false
        })
    },
    components: {
        NavBar, Login
    },
    computed : {

    },
}
</script>

<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    height: 100%
}
</style>
