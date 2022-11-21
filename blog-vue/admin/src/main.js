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
// mavonEditor
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
// NProgress
import NProgress from "nprogress";
// iconfont
import "../src/assets/css/iconfont/iconfont.css";
// ECharts
import ECharts from "vue-echarts";
import "echarts/lib/chart/line";
import "echarts/lib/chart/pie";
import "echarts/lib/chart/bar";
import "echarts/lib/chart/map";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
// tagCloud
import tagCloud from "v-tag-cloud";

// 自定义js
import config from "@/assets/js/config";
// 自定义css
import "../src/assets/css/global.css";
import "../src/assets/css/index.css";

Vue.use(ElementUI); // ElementUI
Vue.use(VueParticles); // VueParticles
Vue.use(mavonEditor); // mavonEditor
Vue.use(tagCloud); // tagCloud
Vue.component("v-chart", ECharts); // ECharts
Vue.config.productionTip = false;
Vue.prototype.config = config; // config
Vue.prototype.request = request; // Axios request
Vue.prototype.$moment = dayjs; // dayjs

NProgress.configure({
  easing: "ease", // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3, // 初始化时的最小百分比
});
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
