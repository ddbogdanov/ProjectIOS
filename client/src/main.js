// import Vue from 'vue'
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from "./store"
import axios from "axios"
import $bus from './event.js'
//import {Delete, Edit, Search, Refresh, DocumentAdd} from "@element-plus/icons-vue"
axios.defaults.baseURL = "http://localhost:3000/innovativestencils/api/v1";

require('./assets/main.css')

const app = createApp(App)

app.use(router)
    .use(ElementPlus)
    .use(store)
    .mount("#app");

app.config.globalProperties.$bus = $bus