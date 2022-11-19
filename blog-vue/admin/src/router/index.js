import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "/admin/login",
  },
  {
    path: "/admin/login",
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
  next();
});

router.afterEach(() => {});

export default router;
