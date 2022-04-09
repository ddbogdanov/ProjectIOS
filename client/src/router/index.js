import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "@/components/Login";

const routes = [
    {
        path:"/",
        name: "Login",
        component: Login,
    },
    {
        path: "/home",
        name: "Home",
        component: Home,
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;