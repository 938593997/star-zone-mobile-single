import * as service from '../../../../services/choosefoyou'
import router from 'umi/router'
import { Toast, Modal } from 'antd-mobile'

const { alert } = Modal

export default {
  namespace: 'choosefoyou',
  state: {
    queryParam: {}, // 查询条件
    datas: [], // 列表数据
    choosePageInitData: [], // 选择界面初始化信息
    chooseRes: {}, // 选择结果
    resList: [], // 轮播信息
  },
  effects: {
    * getChooseLists({ payload, callback }, { call, put }) {
      const response = yield call(service.getChooseLists, payload)
      yield put({ type: 'setData', payload: response.data })
    },
    * deleteChoose({ payload, callback }, { call }) {
      const response = yield call(service.deleteChoose, payload)
      if (response && response.code === 1) {
        if (response && response.code === 1) {
          if (callback && typeof callback === 'function') { // callback用法
            callback(response); // 返回结果
          }
        }
      } else {
        Toast.fail('操作失败', 2)
      }
    },
    * addChoose({ payload, callback }, { call }) {
      const response = yield call(service.addChoose, payload)
      if (response && response.code === 1) {
        if (response && response.code === 1) {
          if (callback && typeof callback === 'function') { // callback用法
            callback(response); // 返回结果
          }
        }
      } else {
        Toast.fail('操作失败', 2)
      }
    },
    * editChoose({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.editChoose, payload)
      if (response && response.code === 1) {
        Toast.success('操作成功', 2);
        const res = yield call(service.getChooseLists, {...queryParam})
        if (res && res.code === -1) {
          Toast.fail('查询选择列表失败', 2)
        } else if (res && res.code === 1) {
          yield put({ type: 'setData', payload: res.data })
        }
      }
    },
    * checkHasConfig({ payload, callback }, { call, put }) {
      const response = yield call(service.checkHasConfig, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
      }
    },
    * addChooseItems({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.addChooseItems, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        const res = yield call(service.getChooseLists, {...queryParam})
        if (res && res.code === -1) {
          Toast.fail('查询选择列表失败', 2)
        } else if (res && res.code === 1) {
          yield put({ type: 'setData', payload: res.data })
        }
      } else {
        Toast.fail('操作失败', 2)
      }
    },
    * choicePageInit({ payload, callback }, { call, put }) {
      const response = yield call(service.checkHasConfig, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        yield put({ type: 'save', payload: { choosePageInitData: response.data } })
      }
    },
    * choice({ payload, callback }, { call, put }) {
      const response = yield call(service.choice, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        yield put({ type: 'save', payload: { chooseRes: response.data } })
      }
    },
    * findChooseRes({ payload, callback }, { call, put }) {
      const response = yield call(service.findChooseRes, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        yield put({ type: 'save', payload: { resList: response.data } })
      }
    },
  },
  reducers: {
    setData (state, action) {
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
        if (pathname === '/class/chooseforyou/list') {
          // dispatch({ type: 'reg' });
        }
      });
    },
  },
};
