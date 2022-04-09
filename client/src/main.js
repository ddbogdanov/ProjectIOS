import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ElementPlus from 'element-plus'
import ElementPlusIcons from '@element-plus/icons'
import 'element-plus/dist/index.css'

require('./assets/main.css');
createApp(App)
    .use(router)
    .use(ElementPlus)
    .use(ElementPlusIcons)
    .mount("#app");
