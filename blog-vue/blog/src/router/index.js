import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("../views/home/HomeView"),
  },
  {
    path: "/archives",
    name: "ArchiveView",
    meta: {
      title: "归档",
    },
    component: () => import("../views/archive/ArchiveView"),
  },
  {
    path: "/articles/:articleId",
    name: "ArticleView",
    component: () => import("../views/article/ArticleView"),
  },
  {
    path: "/categories",
    name: "Categories",
    meta: {
      title: "分类",
    },
    component: () => import("../views/category/CategoryView"),
  },
  {
    path: "/categories/:categoryId",
    name: "CategoriesList",
    meta: {
      title: "分类列表",
    },
    component: () => import("../views/article/ArticleListView"),
  },
  {
    path: "/tags",
    name: "Tags",
    meta: {
      title: "标签",
    },
    component: () => import("../views/tag/TagView"),
  },
  {
    path: "/tags/:tagId",
    name: "TagsList",
    meta: {
      title: "标签列表",
    },
    component: () => import("../views/article/ArticleListView"),
  },
  {
    path: "/about",
    name: "About",
    meta: {
      title: "关于我",
    },
    component: () => import("../views/about/AboutView"),
  },
  // // 娱乐
  // {
  //   path: "/music",
  //   name: "Music",
  //   meta: {
  //     title: "音乐盒",
  //   },
  //   component: () => import("../views/AboutView"),
  // },
  // {
  //   path: "/videos",
  //   name: "Video",
  //   meta: {
  //     title: "视频",
  //   },
  //   component: () => import("../views/VideoView"),
  // },
  // {
  //   path: "/albums",
  //   name: "Albums",
  //   meta: {
  //     title: "相册",
  //   },
  //   component: () => import("../views/AboutView"),
  // },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
