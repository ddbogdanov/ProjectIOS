<template>
    
    <q-page padding>

<div style="width: 50%;">
    <q-form action="/some-url" method='post'>
    <h3>Name</h3>
    <q-input filled name="name" float-label="Float Label" placeholder="name" v-model="text" />

    <h3>Size</h3>
    <q-btn-toggle filled name="size" :options="[
    { label: 'Small', value: 'Small' },
    { label: 'Medium', value: 'Medium' },
    { label: 'Large', value: 'Large'}
    ]" 
    v-model="list"/>

    <h3>SKU</h3>
    <q-input filled name="sku" v-model="tex" />

    <h2>Date Created</h2>
    <q-date filled name="date" v-model="date" />
    <h4>Submit Button</h4>
    <q-btn type="submit" label="submit"/>
    </q-form>


</div>

        <q-page-sticky position="right" style="width: 50%;">
            <q-card>
                <q-card-section>
                    <pre> ({ $data })</pre>
                </q-card-section>
            </q-card>
            <q-card-actions>
                <q-btn @click="logger($data)"
                label="logger" />
            </q-card-actions>
        </q-page-sticky>


    </q-page>
</template>

<script>
export default {

        data () {
            return {
                name: 'Test',

                size: 'Test1',

                sku:'Test2',

                date: null,
            }
        },
    }

    


</script>

<style>
h2 {
    margin-bottom: 12px;
    margin-top: 120px;
}
</style>
