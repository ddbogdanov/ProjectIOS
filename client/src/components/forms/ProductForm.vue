<template>
    <h1>Hello Product Form! {{product.sku}}</h1>
</template>

<script>
export default {
    name: "ProductForm",

    props: ['product'],
    data() {
        return {

        }
    }
}
</script>

<style scoped>

</style>