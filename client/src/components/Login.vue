<template>
    <div>
        <p>Login or Register</p>
        <LoginForm/>
    </div>
</template>

<script>
import LoginForm from "@/components/forms/LoginForm";
export default {
  name: 'HelloWorld',
    components: {LoginForm},
    props: {
        msg: String
  }
}
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
