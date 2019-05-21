import Vue from 'vue'
import Router from 'vue-router'
import Polls from './views/Polls.vue'
import Poll from './views/Poll.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Polls
    },
    {
      path: '/create',
      name: 'create',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/CreatePoll.vue')
    },
    {
      path: '/polls/:id',
      name: 'show',
      component: Poll,
    }
  ]
})
