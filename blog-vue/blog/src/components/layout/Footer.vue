<template>
  <v-footer app padless absolute>
    <div class="footer-wrap">
      <!--      <div>-->
      <!--        我的博客已运行&nbsp;:&nbsp;<span>{{ currentTime }}</span>-->
      <!--        (*๓´╰╯`๓)-->
      <!--      </div>-->
      <div>
        ©{{ blogInfo.websiteConfig.websiteCreateTime | year }} -
        {{ new Date().getFullYear() }} By
        {{ blogInfo.websiteConfig.websiteAuthor }}
      </div>
      <a href="https://beian.miit.gov.cn/" target="_blank">
        {{ blogInfo.websiteConfig.websiteRecordNo }}
      </a>
    </div>
  </v-footer>
</template>

<script>
import dayjs from "dayjs";
import toArray from "dayjs/plugin/toArray";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Footer",
  data() {
    return {
      currentTime: "999年99天23时59分59秒",
    };
  },
  computed: {
    blogInfo() {
      return this.$store.state.blogInfo;
    },
  },
  mounted() {
    // 每间1s刷新一次
    this.timer = setInterval(() => {
      this.setTime();
    }, 1000);
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    secondToDate(second) {
      if (!second) {
        return 0;
      }
      const time = [0, 0, 0, 0, 0];
      if (second >= 365 * 24 * 3600) {
        time[0] = parseInt(second / (365 * 24 * 3600));
        second %= 365 * 24 * 3600;
      }
      if (second >= 24 * 3600) {
        time[1] = parseInt(second / (24 * 3600));
        second %= 24 * 3600;
      }
      if (second >= 3600) {
        time[2] = parseInt(second / 3600);
        second %= 3600;
      }
      if (second >= 60) {
        time[3] = parseInt(second / 60);
        second %= 60;
      }
      if (second > 0) {
        time[4] = second;
      }
      return time;
    },
    setTime() {
      let websiteCreateTime =
        this.$store.state.blogInfo.websiteConfig.websiteCreateTime;
      dayjs.extend(toArray);
      const websiteCreateTimeArray = dayjs(websiteCreateTime).toArray(); // [ 2022, 6, 1, 0, 0, 0, 0 ];
      const create_time = Math.round(
        new Date(
          Date.UTC(
            websiteCreateTimeArray[0],
            websiteCreateTimeArray[1],
            websiteCreateTimeArray[2],
            websiteCreateTimeArray[3],
            websiteCreateTimeArray[4],
            websiteCreateTimeArray[5]
          )
        ).getTime() / 1000
      );
      const timestamp = Math.round(
        (new Date().getTime() + 8 * 60 * 60 * 1000) / 1000
      );
      let currentTimeArray = this.secondToDate(timestamp - create_time);
      this.currentTime =
        currentTimeArray[0] +
        "年" +
        currentTimeArray[1] +
        "天" +
        currentTimeArray[2] +
        "时" +
        currentTimeArray[3] +
        "分" +
        currentTimeArray[4] +
        "秒";
    },
  },
};
</script>

<style scoped>
.footer-wrap {
  width: 100%;
  line-height: 2;
  position: relative;
  padding: 20px 20px;
  color: #eee;
  font-size: 14px;
  text-align: center;
  background: linear-gradient(-45deg, #ee7752, #ce3e75, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: Gradient 10s ease infinite;
}

.footer-wrap a {
  color: #eee !important;
}

@keyframes Gradient {
  0% {
    background-position: 0 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0 50%;
  }
}
</style>
