import * as service from '../../../services/user';
import router from 'umi/router';
import { Toast } from 'antd-mobile'
export default {
  namespace: 'user',
  state: {
    actions: undefined, // 操作类型： login 登入  register 注册
  },
  effects: {
    * login({ payload, callback }, { call, put }) {
      const response = yield call(service.login, payload);
      if (response && response.code === 1 && response.message === '成功') {
        localStorage.setItem('USER_INFO', JSON.stringify(response.data))
        localStorage.setItem('recovery-buss-web-token', response.data.id) // 设置token用户表id作为token
        router.push('/class')
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
      } else if (response && response.code === 1 && response.message === '失败') {
        Toast.fail('秘钥或密码错误', 3);
        return false
      } else {
        console.info(response)
        Toast.fail('登入失败，请稍后再试', 3);
        return false
      }
    },
    * showRegisterOrLogin({ payload, callback }, { call, put }) {
      const response = yield call(service.CheckName, payload);
      if (response && response.code === 1) {
        yield put({ type: 'save', payload: { actions: response.data.result } });
      }
    },
    * register({ payload, callback }, { call, put }) {
      const response = yield call(service.CheckName, payload);
      if (response && response.code === 1) {
        if (response.data && response.data.result === 'register') {
          const response = yield call(service.register, payload);
            if (response && response.code === 1) {
              // router.push('/User/registerOrLogin')
              if (callback && typeof callback === 'function') { // callback用法
                callback(response); // 返回结果
              }
              // window.location.reload()
              Toast.success('注册成功，请登入', 3)
            } else {
              Toast.fail('注册失败，请稍后再试', 3);
              return false
            }
        } else {
          Toast.fail('该用户名已被注册', 3);
        }
      }
    },
    * quit({ payload, callback }, { call, put }) {
      const response = yield call(service.quit, payload);
      if (response && response.code === 1) {
        // localStorage.setItem('USER_INFO', null)localStorage.setItem('USER_INFO', null)
        localStorage.removeItem('USER_INFO')
        localStorage.removeItem('recovery-buss-web-token')
        router.push('/User/registerOrLogin')
      }
    },
  },
  reducers: {
    setData(state, action) {
      return {
        ...state,
        datas: action.payload,
      }
    },
    save(state, action) {
      const req = {}
      Object.assign(req, action.payload)
      return {
        ...state,
        ...req,
      }
    },
    saveData (state, action) {
      return Object.assign({ ...state }, action.payload)
    }
  },
};
