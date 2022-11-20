<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <mavon-editor
      ref="md"
      @imgAdd="uploadImg"
      v-model="aboutContent"
      style="height: calc(100vh - 250px); margin-top: 2.25rem"
    />
    <el-button
      type="danger"
      size="medium"
      class="edit-btn"
      @click="updateAbout"
    >
      修改
    </el-button>
  </el-card>
</template>

<script>
import * as imageConversion from "image-conversion";

export default {
  name: "AboutView",
  created() {
    this.getAbout();
  },
  data: function () {
    return {
      aboutContent: "",
    };
  },
  methods: {
    getAbout() {
      this.request.get("/admin/config/about/info").then((data) => {
        this.aboutContent = data.data;
      });
    },
    uploadImg(pos, file) {
      let formData = new FormData();
      if (file.size / 1024 < this.config.UPLOAD_SIZE) {
        formData.append("file", file);
        this.request.post("/admin/file/image/upload", formData).then((data) => {
          this.$refs.md.$img2Url(pos, data.data);
        });
      } else {
        // 压缩到200KB,这里的200就是要压缩的大小,可自定义
        imageConversion
          .compressAccurately(file, this.config.UPLOAD_SIZE)
          .then((res) => {
            formData.append(
              "file",
              new window.File([res], file.name, { type: file.type })
            );
            this.request
              .post("/admin/file/image/upload", formData)
              .then((data) => {
                this.$refs.md.$img2Url(pos, data.data);
              });
          });
      }
    },
    updateAbout() {
      this.request
        .put("/admin/config/about/update", {
          aboutContent: this.aboutContent,
        })
        .then((data) => {
          if (data.code === 200) {
            this.$notify.success({
              title: "成功",
              message: data.message,
            });
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message,
            });
          }
        });
    },
  },
};
</script>

<style scoped>
.edit-btn {
  float: right;
  margin: 1rem 0;
}
</style>
