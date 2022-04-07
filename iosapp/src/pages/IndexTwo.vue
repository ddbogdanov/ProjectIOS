<template>
  
  <q-page class="row items-center justify-evenly">
   

    <q-body>
     <div class="q-pa-md" style="max-width: 400px">

    <q-form
      
      @submit="onSubmit"
      @reset="onReset"
      class="q-gutter-md"
    >
      <q-input filled
        
        v-model="text"
        label="Enter Work Order #"
        
      />

        <q-input filled
        
        v-model="text"
        label="Enter Product ID"
        
      />

      <q-input filled
        
        v-model="name"
        label="Enter Quantity"
        
      />

      <q-input filled
        
        type="date"
        v-model="date"
        label=""
        hint="Date Ordered"
        
        
      />


      <div>
        <q-btn label="Submit" type="submit" color="primary"/>
        <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
      </div>
    </q-form>

  </div>

   
    </q-body>
   
  </q-page>
</template>


