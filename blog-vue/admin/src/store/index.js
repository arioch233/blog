import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    collapse: false,
    tabList: [{ name: "首页", path: "/" }],
    userId: null,
    token: null,
    roleList: null,
    avatar: null,
    nickname: null,
    intro: null,
    webSite: null,
    userMenuList: [],
  },
  mutations: {
    saveTab(state, tab) {
      if (state.tabList.findIndex((item) => item.path === tab.path) === -1) {
        state.tabList.push({ name: tab.name, path: tab.path });
      }
    },
    removeTab(state, tab) {
      let index = state.tabList.findIndex((item) => item.name === tab.name);
      state.tabList.splice(index, 1);
    },
    resetTab(state) {
      state.tabList = [{ name: "首页", path: "/" }];
    },
    trigger(state) {
      state.collapse = !state.collapse;
    },
    login(state, user) {
      state.userId = user.userInfoId;
      state.token = user.token;
      state.roleList = user.roleList;
      state.avatar = user.avatar;
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.webSite = user.webSite;
      sessionStorage.setItem("user", JSON.stringify(user));
    },
    saveUserMenuList(state, userMenuList) {
      state.userMenuList = userMenuList;
    },
    logout(state) {
      state.userId = null;
      state.roleList = null;
      state.avatar = null;
      state.nickname = null;
      state.intro = null;
      state.token = null;
      state.webSite = null;
      state.userMenuList = [];
      sessionStorage.removeItem("user");
    },
    updateAvatar(state, avatar) {
      let user = {
        userId: state.userId,
        token: state.token,
        roleList: state.roleList,
        avatar: avatar,
        nickname: state.nickname,
        intro: state.intro,
        webSite: state.webSite,
        userMenuList: state.userMenuList,
      };
      sessionStorage.setItem("user", JSON.stringify(user));
    },
    updateUserInfo(state, user) {
      let userInfo = {
        userId: state.userId,
        token: state.token,
        roleList: state.roleList,
        avatar: user.avatar,
        nickname: user.nickname,
        intro: user.intro,
        webSite: user.webSite,
        userMenuList: state.userMenuList,
      };
      state.avatar = user.avatar;
      state.nickname = user.nickname;
      sessionStorage.setItem("user", JSON.stringify(userInfo));
    },
  },
  actions: {},
  modules: {},
  plugins: [],
});
