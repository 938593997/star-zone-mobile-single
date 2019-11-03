import * as service from '../../../../../services/spendsdetails'
import { Toast } from 'antd-mobile'

export default {
  namespace: 'spendsdetails',
  state: {
    queryParam: {}, // 查询条件
    datas: [], // 列表数据

    dataGroupByYear: [], // pie、Bar加 detail_show_way 按年分组查询到的数据
  },
  effects: {
    * getSpendDetailsLists({ payload, callback }, { call, put }) {
      const response = yield call(service.getSpendDetailsLists, payload)
      yield put({ type: 'setData', payload: response.data })
    },
    * delSpendDetails({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.delSpendDetails, payload)
      if (response && response.code === 1) {
        // Toast.success('操作成功', 2)
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        const res = yield call(service.getSpendDetailsLists, {...queryParam})
        if (res && res.code === -1) {
          Toast.fail('查询选择列表失败', 2)
        } else if (res && res.code === 1) {
          yield put({ type: 'setData', payload: res.data })
        }
      }
    },
    * saveSpendDetails({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.saveSpendDetails, payload)
      if (response && response.code === 1) {
        // Toast.success('操作成功', 2);
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        const res = yield call(service.getSpendDetailsLists, {...queryParam})
        if (res && res.code === -1) {
          Toast.fail('查询选择列表失败', 2)
        } else if (res && res.code === 1) {
          yield put({ type: 'setData', payload: res.data })
        }
      }
    },
    * modifySpendDetails({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.modifySpendDetails, payload)
      if (response && response.code === 1) {
        // Toast.success('操作成功', 2);
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        const res = yield call(service.getSpendDetailsLists, {...queryParam})
        if (res && res.code === -1) {
          Toast.fail('查询选择列表失败', 2)
        } else if (res && res.code === 1) {
          yield put({ type: 'setData', payload: res.data })
        }
      }
    },
    * getSpendTypeLists({ payload, callback }, { call, put }) {
      const response = yield call(service.getSpendTypeLists, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
      }
    },
    * findPieAndBarDatas({ payload, callback }, { call, put }) {
      const response = yield call(service.findPieAndBarDatas, payload)
      yield put({ type: 'save', payload: { dataGroupByYear: response.data.dataGroupByYear } })
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
};
