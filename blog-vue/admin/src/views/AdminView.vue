<template>
  <el-container style="min-height: 100vh">
    <!-- 侧边栏 -->
    <el-aside width="auto" class="aside-shadow">
      <Aside />
    </el-aside>
    <el-container :class="'main-container ' + isHide">
      <!-- 导航栏 -->
      <el-header height="84px" style="padding: 0">
        <Header :key="$route.fullPath" />
      </el-header>
      <!-- 内容 -->
      <el-main style="background: #f7f9fb">
        <div class="fade-transform-box">
          <transition name="fade-transform" mode="out-in">
            <router-view :key="$route.fullPath" />
          </transition>
        </div>
      </el-main>
      <el-footer
        style="height: 30px; background: #f7f9fb; margin: 0; padding: 0"
        class="mt-10"
      >
        <Footer />
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import Footer from "@/components/layout/Footer";
import Header from "@/components/layout/Header";
import Aside from "@/components/layout/Aside";

export default {
  name: "AdminManageView",
  components: { Footer, Header, Aside },
  methods: {},
  computed: {
    isHide() {
      return this.$store.state.collapse ? "hideSideBar" : "";
    },
  },
};
</script>

<style scoped>
.main-container {
  transition: margin-left 0.45s;
  margin-left: 210px;
  min-height: 100vh;
}
.hideSideBar {
  margin-left: 64px;
}
.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.5s ease 0s;
}
.fade-transform-enter {
  opacity: 0;
  transform: translateX(-30px);
}
.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
.fade-transform-box {
  position: relative;
  top: 0px;
  bottom: 0px;
  width: 100%;
  overflow: hidden;
}
</style>
