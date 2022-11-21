<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { generateMenu } from "@/assets/js/menu/menu";
export default {
  created() {
    let user = JSON.parse(sessionStorage.getItem("user"));
    if (user != null) {
      this.$store.commit("login", user);
      generateMenu();
    }
    this.request.post("/report");
  },
};
</script>
<style></style>
