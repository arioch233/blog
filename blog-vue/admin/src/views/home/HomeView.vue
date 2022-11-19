<template>
  <div>
    <!-- 统计数据 -->
    <el-row :gutter="30">
      <el-col :span="6">
        <el-card>
          <div class="card-icon-container">
            <i
              class="iconfont icon-fangwenliang"
              style="color: #40c9c6; font-size: 3rem"
            />
          </div>
          <div class="card-desc">
            <div class="card-title">访问量</div>
            <div class="card-count">{{ viewsCount }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-icon-container">
            <i
              class="iconfont icon-dianzan"
              style="color: #34bfa3; font-size: 3rem"
            />
          </div>
          <div class="card-desc">
            <div class="card-title">点赞量</div>
            <div class="card-count">{{ userCount }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-icon-container">
            <i
              class="iconfont icon-16"
              style="color: #f4516c; font-size: 3rem"
            />
          </div>
          <div class="card-desc">
            <div class="card-title">文章量</div>
            <div class="card-count">{{ articleCount }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-icon-container">
            <i class="el-icon-s-comment" style="color: #36a3f7" />
          </div>
          <div class="card-desc">
            <div class="card-title">留言量</div>
            <div class="card-count">{{ messageCount }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 一周访问量展示 -->
    <el-card style="margin-top: 1.25rem">
      <div class="e-title">一周访问量</div>
      <div style="height: 350px">
        <v-chart :options="viewCount" v-loading="loading" />
      </div>
    </el-card>
    <el-row :gutter="20" style="margin-top: 1.25rem">
      <!-- 文章浏览量排行 -->
      <el-col :span="16">
        <el-card>
          <div class="e-title">文章浏览量排行</div>
          <div style="height: 350px">
            <v-chart :options="articleRank" v-loading="loading" />
          </div>
        </el-card>
      </el-col>
      <!-- 分类数据统计 -->
      <el-col :span="8">
        <el-card>
          <div class="e-title">文章分类统计</div>
          <div style="height: 350px">
            <v-chart :options="category" v-loading="loading" />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 1.25rem">
      <el-col :span="8">
        <el-card>
          <div class="e-title">文章标签统计</div>
          <div style="height: 350px" v-loading="loading">
            <tag-cloud style="margin-top: 1.5rem" :data="tagDTOList" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      loading: true,
      type: 1,
      viewsCount: 0,
      messageCount: 0,
      userCount: 0,
      articleCount: 0,
      articleStatisticsList: [],
      tagDTOList: [],
      viewCount: {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
          },
        },
        color: ["#3888fa"],
        legend: {
          data: ["访问量"],
        },
        grid: {
          left: "0%",
          right: "0%",
          bottom: "0%",
          top: "10%",
          containLabel: true,
        },
        xAxis: {
          data: [],
          axisLine: {
            lineStyle: {
              // 设置x轴颜色
              color: "#666",
            },
          },
        },
        yAxis: {
          axisLine: {
            lineStyle: {
              // 设置y轴颜色
              color: "#048CCE",
            },
          },
        },
        series: [
          {
            name: "访问量",
            type: "line",
            data: [],
            smooth: true,
          },
        ],
      },
      articleRank: {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
          },
        },
        color: ["#58AFFF"],
        grid: {
          left: "0%",
          right: "0%",
          bottom: "0%",
          top: "10%",
          containLabel: true,
        },
        xAxis: {
          data: [],
        },
        yAxis: {},
        series: [
          {
            name: ["浏览量"],
            type: "bar",
            data: [],
          },
        ],
      },
      category: {
        color: [
          "#7EC0EE",
          "#FF9F7F",
          "#FFD700",
          "#C9C9C9",
          "#E066FF",
          "#C0FF3E",
        ],
        legend: {
          data: [],
          bottom: "bottom",
        },
        tooltip: {
          trigger: "item",
        },
        series: [
          {
            name: "文章分类",
            type: "pie",
            roseType: "radius",
            data: [],
          },
        ],
      },
    };
  },
  created() {
    this.getChartData();
  },
  methods: {
    getChartData() {
      this.viewCount.xAxis.data.push("1", "2", "3", "4", "5", "6", "7");
      this.viewCount.series[0].data.push(100, 200, 50, 60, 40, 30, 100);

      this.loading = false;
    },
  },
};
</script>

<style scoped>
.card-icon-container {
  display: inline-block;
  font-size: 3rem;
}

.area-wrapper {
  display: flex;
  justify-content: center;
}

.card-desc {
  font-weight: bold;
  float: right;
}

.card-title {
  margin-top: 0.3rem;
  line-height: 18px;
  color: rgba(0, 0, 0, 0.45);
  font-size: 1rem;
}

.card-count {
  margin-top: 0.75rem;
  color: #666;
  font-size: 1.25rem;
}

.echarts {
  width: 100%;
  height: 100%;
}

.e-title {
  font-size: 13px;
  color: #202a34;
  font-weight: 700;
}
</style>
