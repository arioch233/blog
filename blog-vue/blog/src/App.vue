<template>
  <v-app>
    <!--  顶部菜单  -->
    <TopNavBar />
    <!--  侧边栏  -->
    <SideNavBar />
    <!--  中间内容  -->
    <v-main>
      <router-view :key="$route.fullPath" />
    </v-main>
    <!--  底部页脚  -->
    <Footer />
    <!--  回到顶部  -->
    <TopBack />
    <!--  搜索模态框  -->
    <SearchModel v-if="this.$store.state.searchFlag" />
    <!--  音乐播放器  -->
    <meting-js
      server="tencent"
      type="playlist"
      id="8574171521"
      fixed="true"
      autoplay="true"
      loop="all"
      order="list"
      preload="auto"
      list-folded="true"
      list-max-height="500px"
      lrc-type="1"
      v-if="this.$store.state.blogInfo.websiteConfig.isMusicPlayer === 1"
    >
    </meting-js>
  </v-app>
</template>

<script>
import Footer from "@/components/layout/Footer";
import SideNavBar from "@/components/layout/SideNavBar";
import TopNavBar from "@/components/layout/TopNavBar";
import TopBack from "@/components/layout/TopBack";
import SearchModel from "@/components/model/SearchModel";
export default {
  name: "App",
  // eslint-disable-next-line vue/no-unused-components
  components: { TopBack, Footer, SideNavBar, TopNavBar, SearchModel },
  comments: {
    Footer,
    SideNavBar,
    TopNavBar,
    TopBack,
    SearchModel,
  },
  created() {
    // 获取博客信息
    this.getBlogInfo();
    // 上传访客信息
    this.report();
  },
  methods: {
    getBlogInfo() {
      this.request.get("/").then((data) => {
        this.$store.commit("checkBlogInfo", data.data);
      });
    },
    report() {
      this.request.post("/report");
    },
  },
  computed: {
    blogInfo() {
      return this.$store.state.blogInfo;
    },
  },
};
</script>
