import Monitor from './components/monitor/monitor'
import Login from './components/managerCenter/login'
import Manager from './components/managerCenter/manager'
import Index from './components/managerCenter/index'
import page_Train from './components/managerCenter/page_Train'
import page_addUser from './components/managerCenter/page_addUser'
import page_User from './components/managerCenter/page_User'

export default {
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/monitor',
      name: 'monitor',
      component: Monitor
    },
    {
      path: '/manager',
      name: 'manager',
      component: Manager
    },
    {
      path: '/Index',
      name: 'Index',
      component: Index
    },
    {
      path: '/page_Train',
      name: 'page_Train',
      component: page_Train
    },
    {
      path: '/page_User',
      name: 'page_User',
      component: page_User
    },
    {
      path: '/page_addUser',
      name: 'page_addUser',
      component: page_addUser
    }
  ],
  // mode: 'history'
}