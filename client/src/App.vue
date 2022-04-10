<template>
    <NavBar class="nav-bar"/>
    <router-view/>
</template>

<script>
//import Login from "@/components/Login";

import NavBar from "@/components/NavBar";
export default {
    name: 'App',
    setup() {
    },
    components: {
        NavBar
    },
    computed : {
        //isLoggedIn : function(){ return this.$store.getters.isAuthenticated}
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
