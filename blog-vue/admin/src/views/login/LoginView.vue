<template>
  <div class="background-image">
    <!--    <vue-particles-->
    <!--      color="#dedede"-->
    <!--      :particleOpacity="0.7"-->
    <!--      :particlesNumber="100"-->
    <!--      shapeType="circle"-->
    <!--      :particleSize="4"-->
    <!--      linesColor="#dedede"-->
    <!--      :linesWidth="1"-->
    <!--      :lineLinked="true"-->
    <!--      :lineOpacity="0.4"-->
    <!--      :linesDistance="150"-->
    <!--      :moveSpeed="3"-->
    <!--      :hoverEffect="true"-->
    <!--      hoverMode="grab"-->
    <!--      :clickEffect="true"-->
    <!--      clickMode="push"-->
    <!--    >-->
    <!--    </vue-particles>-->
    <el-card class="login-wrapper">
      <el-form
        :rules="rules"
        ref="loginForm"
        class="form-body"
        :model="loginForm"
      >
        <h3 class="loginTitle">后台管理</h3>
        <el-form-item prop="username">
          <el-input
            type="text"
            v-model="loginForm.username"
            placeholder="请输入用户名"
          >
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            v-model="loginForm.password"
            placeholder="请输入密码"
          >
          </el-input>
        </el-form-item>
        <el-button type="primary" style="width: 100%" @click="submitLogin"
          >登录
        </el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { generateMenu } from "@/assets/js/menu/menu";

export default {
  name: "LoginView",
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
        code: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 5,
            max: 20,
            message: "长度在 5 到 14 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, message: "密码长度要大于6", trigger: "blur" },
        ],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
    };
  },
  methods: {
    submitLogin() {
      const that = this;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.loginForm).then((data) => {
            if (data.code === 200) {
              // 登录后保存用户信息
              that.$store.commit("login", data.data);
              // 加载用户菜单
              generateMenu();
              this.$notify.success({
                title: "登录成功",
                message: data.message,
              });
              that.$router.push({ path: "/" });
            } else {
              this.$notify.error({
                title: "登录失败",
                message: data.message,
              });
            }
          });
        } else {
          this.$message.error("登录出错请重新输入");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
/*#particles-js {*/
/*  background-image: url("../../assets/images/bg2.png");*/
/*  background-attachment: fixed;*/
/*  background-repeat: no-repeat;*/
/*  background-size: 100% calc(100% + 4px);*/
/*  width: 100%;*/
/*  !*height: calc(100% - 4px);*!*/
/*  position: absolute;*/
/*}*/

.background-image {
  background-image: url("../../assets/images/login-bg.jpg");
  width: 100%;
  height: 100vh;
}

.login-wrapper {
  opacity: 0.9;
  text-align: center;
  background-color: #fff;
  border-radius: 20px;
  width: 300px;
  height: 250px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid #409eff;
  box-shadow: 0 0 25px #409eff;
}

.loginTitle {
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.loginRemember {
  padding-right: 180px;
  margin: 0 0 15px 0;
}
</style>
