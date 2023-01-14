import Vue from "vue";
import VueRouter from "vue-router";
// NProgress
import NProgress from "nprogress";
Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/login/LoginView"),
  },
];

const createRouter = () =>
  new VueRouter({
    mode: "history",
    routes: routes,
  });

const router = createRouter();

export const resetRouter = () => {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
};

router.beforeEach((to, form, next) => {
  NProgress.start();
  next();
});

router.afterEach(() => {
  NProgress.done();
});

export default router;
