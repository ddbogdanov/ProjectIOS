import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "@/views/Login";
import Products from "@/views/Products";
import Material from "@/views/Material";
import Orders from "@/views/Orders";


const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
    },
    {
      path: "/products",
      name: "Products",
      component: Products
    },
    {
      path: "/material",
      name: "Material",
      component: Material
    },
    {
        path: "/orders",
        name: "Orders",
        component: Orders
    },
    {
        path:"/login",
        name: "Login",
        component: Login,
    },

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;