import axios from "axios";
import router from "@/router";
import store from "@/store";
import Vue from "vue";

export const request = axios.create({
  baseURL: "/api",
  timeout: 5000,
});

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(
  (config) => {
    if (store.state.userId !== null) {
      let user = sessionStorage.getItem("user")
        ? JSON.parse(sessionStorage.getItem("user"))
        : null;
      if (user) {
        config.headers["Content-Type"] = "application/json;charset=utf-8";
        config.headers["token"] = user.token; // 设置请求头
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
  function (response) {
    let res = response.data;
    switch (response.data.code) {
      case 40001:
        Vue.prototype.$message({
          type: "error",
          message: response.data.message,
        });
        router.push({ path: "/admin/login" });
        break;
      case 50000:
        Vue.prototype.$message({
          type: "error",
          message: response.data.message,
        });
        break;
    }
    return res;
  },
  function (error) {
    return Promise.reject(error);
  }
);

export default request;
