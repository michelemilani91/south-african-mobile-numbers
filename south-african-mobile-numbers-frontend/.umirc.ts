import { defineConfig } from 'umi';

export default defineConfig({
  routes: [
    {
      path: '/',
      component: '../layouts/index',
      routes: [
        { path: '/', component: '@/pages/index', exact: true },
        { path: '/single', component: '@/pages/single/index', exact: true },
        { path: '/list', component: '@/pages/list/index', exact: true },
        { path: '/list/:id', component: '@/pages/list/[id]', exact: true }
      ]
    }
  ],
  antd: {},
  locale: {
    default: 'en-US',
    antd: true,
    baseNavigator: true,
  },
  proxy: {
    '/api': {
      'target': 'http://localhost:8080/',
      'changeOrigin': true,
    },
  },
});
