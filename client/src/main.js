import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ElementPlus from 'element-plus'
import ElementPlusIcons from '@element-plus/icons'
import 'element-plus/dist/index.css'
import store from "./store"
import axios from "axios"

axios.defaults.baseURL = "http://localhost:3000/innovativestencils/api/v1";

require('./assets/main.css');
createApp(App)
    .use(router)
    .use(ElementPlus)
    .use(ElementPlusIcons)
    .use(store)
    .mount("#app");
