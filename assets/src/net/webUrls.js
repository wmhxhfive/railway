export default {
  version: '1.0.5',
  urlList:{
    login: process.env.API_ROOT+'/train/sys/login',
  	trainInfo: process.env.API_ROOT+'/train/trainInfo/screen',
  	trainList: process.env.API_ROOT+'/train/trainInfo/list',
    trainDetail: process.env.API_ROOT+'/train/trainInfo/detail',
    checkUser: process.env.API_ROOT+'/train/sys/check',
    register: process.env.API_ROOT+'/train/sys/register',
    update:process.env.API_ROOT+'/train/trainInfo/update',
    delete: process.env.API_ROOT+'/train/trainInfo/delete',
  },
  INAPP: 'INAPP'
}
