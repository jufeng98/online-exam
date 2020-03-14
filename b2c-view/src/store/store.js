import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    id: null,
    topicsName: '',
    topicsCoverImage: null,
    topicsType: '',
    examsCode: '',
    createTopics: false,
  },
  mutations: {
    init(state, topicsData) {
      state.id = null
      state.topicsName = ''
      state.topicsCoverImage = null
      state.topicsType = ''
      state.examsCode = ''
      state.createTopics = false
      Object.assign(state, topicsData);
    }
  }
})

