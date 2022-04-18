import { createRouter, createWebHistory } from "vue-router";
import Products from "@/views/Products";
import Material from "@/views/Material";
import Orders from "@/views/Orders";
import Login from "@/views/Login";


const routes = [
    {
        path: "/",
        name: "Login",
        component: Login
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

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;