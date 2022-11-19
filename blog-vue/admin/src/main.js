import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

// ElementUI
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
// VueParticles
import VueParticles from "vue-particles";
// Axios request
import request from "@/utils/request";
// dayjs
import dayjs from "dayjs";
// iconfont
import "../src/assets/css/iconfont/iconfont.css";
// 自定义js
import config from "@/assets/js/config";
// 自定义css
import "../src/assets/css/global.css";
import "../src/assets/css/index.css";

Vue.use(ElementUI); // ElementUI
Vue.use(VueParticles); // VueParticles
Vue.config.productionTip = false;
Vue.prototype.config = config; // config
Vue.prototype.request = request; // Axios request
Vue.prototype.$moment = dayjs; // dayjs

// 时间格式
Vue.filter("date", function (value, formatStr = "YYYY-MM-DD") {
  return dayjs(value).format(formatStr);
});

Vue.filter("dateTime", function (value, formatStr = "YYYY-MM-DD HH:mm:ss") {
  return dayjs(value).format(formatStr);
});
Vue.filter("hour", function (value) {
  return dayjs(value).format("HH:mm:ss");
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
