import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";

// dayjs
import dayjs from "dayjs";
// animated
import animated from "animate.css";
// highlight
import "highlight.js/styles/atom-one-dark.css";
// InfiniteLoading
import InfiniteLoading from "vue-infinite-loading";
// NProgress
import NProgress from "nprogress";
import "nprogress/nprogress.css";
// vue-image-swipe
import VueImageSwipe from "vue-image-swipe";
import "vue-image-swipe/dist/vue-image-swipe.css";

// 自定义js
import Toast from "./components/toast/toast";
import request from "@/utils/request";
// 自定义css
import "./assets/css/iconfont/iconfont.css";
import "./assets/css/global/index.css";
import "./assets/css/markdown/markdown.css";

Vue.config.productionTip = false;
Vue.prototype.request = request; //request
Vue.use(Toast); // Toast
Vue.use(animated); // animate
Vue.use(InfiniteLoading); // InfiniteLoading
Vue.use(VueImageSwipe); //vueImageSwipe
// 自定义过滤器
Vue.filter("date", function (value) {
  return dayjs(value).format("YYYY-MM-DD");
});

Vue.filter("year", function (value) {
  return dayjs(value).format("YYYY");
});

Vue.filter("hour", function (value) {
  return dayjs(value).format("HH:mm:ss");
});

Vue.filter("time", function (value) {
  return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
});

Vue.filter("num", function (value) {
  if (value >= 1000) {
    return (value / 1000).toFixed(1) + "k";
  }
  return value;
});

// 路由守卫
router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  next();
});
router.afterEach(() => {
  window.scroll({
    top: 0,
    behavior: "instant",
  });
  NProgress.done();
});

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
