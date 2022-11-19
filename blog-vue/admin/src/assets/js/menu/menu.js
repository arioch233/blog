import Vue from "vue";
import router from "../../../router";
import store from "../../../store";
import Layout from "@/views/AdminView";
import request from "@/utils/request";

export function generateMenu() {
  request.get("/admin/menu/user").then((data) => {
    if (data.code === 200) {
      const userMenuList = data.data;
      userMenuList.forEach((item) => {
        if (item.icon != null) {
          item.icon = "iconfont " + item.icon;
        }
        if (item.component === "Layout") {
          item.component = Layout;
        }
        if (item.children && item.children.length > 0) {
          item.children.forEach((route) => {
            route.icon = "iconfont " + route.icon;
            route.component = loadView(route.component);
          });
        }
      });
      // 添加侧边栏菜单
      store.commit("saveUserMenuList", userMenuList);
      // 添加菜单到路由
      router.addRoutes(userMenuList);
    } else {
      Vue.prototype.$message.error(data.message);
      router.push({ path: "/admin/login" });
    }
  });
}

export const loadView = (view) => {
  return () => import(`@/views${view}`);
};
