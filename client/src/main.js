// import Vue from 'vue'
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from "./store"
import axios from "axios"
import $bus from './event.js'
import VueApexCharts from "vue3-apexcharts"
import VueCookies from 'vue3-cookies'
import 'element-plus/theme-chalk/display.css'

//import {Delete, Edit, Search, Refresh, DocumentAdd} from "@element-plus/icons-vue"
axios.defaults.baseURL = "http://localhost:3000/innovativestencils/api/v1"

require('./assets/main.css')

const app = createApp(App)
app.config.globalProperties.$bus = $bus

app.use(router)
    .use(ElementPlus)
    .use(store)
    .use(VueApexCharts)
    .use(VueCookies)
    .mount("#app")




