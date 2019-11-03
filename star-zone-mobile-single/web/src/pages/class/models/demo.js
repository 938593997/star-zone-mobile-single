import * as service from '../../../services/demo'
import { Toast } from 'antd-mobile'
import router from 'umi/router';
// const image = localStorage.getItem('USER_INFO') !== 'null' &&  localStorage.getItem('USER_INFO') !== null ? JSON.parse(localStorage.getItem('USER_INFO')).picUrl : null
const image = localStorage.getItem('USER_INFO') ? JSON.parse(localStorage.getItem('USER_INFO')).picUrl : null
export default {
  namespace: 'demo',
  state: {
    userInfo: {}, // 个人信息
    // avatar: require('../../../assets/default-pic.png'), // 头像
    avatar: image ? image : require('../../../assets/default-pic.png'), // 头像
  },
  effects: {
    * reg({ payload, callback }, { call, put }) {
      const response = yield call(service.querySzMenuList, payload);
      yield put({
        type: 'setData',
        payload: response.data
      });
    },
    * getUserInfoById({ payload, callback }, { call, put }) {
      const response = yield call(service.getUserInfoById, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        yield put({ type: 'save', payload: { userInfo: response.data } })
      } else {
        Toast.info('个人信息失联了', 2)
      }
    },
    * savePersonalInfo({ payload, callback }, { call, put }) {
      const response = yield call(service.savePersonalInfo, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
      } else {
        Toast.fail('个人信息修改异常', 2)
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
    save (state, action) {
      return Object.assign({ ...state }, action.payload)
    },
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, search }) => {
        if (pathname === '/class') {
          // dispatch({ type: 'reg' });
        }
      });
    },
  },
};
