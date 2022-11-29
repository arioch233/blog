import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    searchFlag: false,
    forgetFlag: false,
    emailFlag: false,
    drawer: false,
    userId: null,
    avatar: null,
    nickname: null,
    intro: null,
    webSite: null,
    email: null,
    articleLikeSet: [],
    commentLikeSet: [],
    blogInfo: {},
  },
  getters: {},
  mutations: {
    saveLikeInfo(state, likeInfo) {
      state.articleLikeSet = likeInfo.articleLikeSet;
      state.commentLikeSet = likeInfo.commentLikeSet;
    },
    savePageInfo(state, pageList) {
      state.pageList = pageList;
    },
    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    checkBlogInfo(state, blogInfo) {
      state.blogInfo = blogInfo;
    },
    closeModel(state) {
      state.registerFlag = false;
      state.loginFlag = false;
      state.searchFlag = false;
      state.emailFlag = false;
    },
    articleLike(state, articleId) {
      const articleLikeSet = state.articleLikeSet;
      if (articleLikeSet.indexOf(articleId) !== -1) {
        articleLikeSet.splice(articleLikeSet.indexOf(articleId), 1);
      } else {
        articleLikeSet.push(articleId);
      }
    },
    commentLike(state, commentId) {
      const commentLikeSet = state.commentLikeSet;
      if (commentLikeSet.indexOf(commentId) !== -1) {
        commentLikeSet.splice(commentLikeSet.indexOf(commentId), 1);
      } else {
        commentLikeSet.push(commentId);
      }
    },
    talkLike(state, talkId) {
      const talkLikeSet = state.talkLikeSet;
      if (talkLikeSet.indexOf(talkId) !== -1) {
        talkLikeSet.splice(talkLikeSet.indexOf(talkId), 1);
      } else {
        talkLikeSet.push(talkId);
      }
    },
  },

  actions: {},
  modules: {},
});
