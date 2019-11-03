import * as service from '../../../../services/spendtypes'
import { Toast } from 'antd-mobile'

export default {
  namespace: 'spendtypes',
  state: {
    queryParam: {}, // 查询条件
    datas: [], // 列表数据（不分页数据）
    PageInfo: {
      list: [], // 查询返回的数据
      pageNum: 1, // 第几页（从1开始）
      pageSize: 7, // 每页显示
      total: 0, // 有几条数据
      pages: 1, // 有几页 默认给值1
    },
    dataGroupByYear: [], // pie、Bar按年分组查询到的数据
    dataGroupByType: [], // pie、Bar按消费类型分组查的数据，可以按年过滤
  },
  effects: {
    * getSpendTypeLists({ payload, callback }, { call, put }) {
      const response = yield call(service.getSpendTypeLists, payload)
      yield put({ type: 'setData', payload: response.data })
    },
    * delSpendType({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.delSpendType, payload)
      if (response && response.code === 1) {
        Toast.success('操作成功', 2)
        const res = yield call(service.getSpendTypeListsByPage, { pageNum: 1, pageSize: 7, ...queryParam })
        if (res && res.code === -1) {
          Toast.fail('查询消费类型列表失败', 2)
        } else if (res && res.code === 1) {
          // yield put({ type: 'setData', payload: res.data })
          yield put({ type: 'save', payload: { PageInfo: { list: res.data.list, pageNum: res.data.pageNum, pages: res.data.pages, pageSize: res.data.pageSize } } })
        }
      }
    },
    * saveSpendType({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.saveSpendType, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        if (response.message !== '-2') {
          Toast.success('操作成功', 2)
          const res = yield call(service.getSpendTypeListsByPage, { pageNum: 1, pageSize: 7, ...queryParam })
          if (res && res.code === -1) {
            Toast.fail('查询消费类型列表失败', 2)
          } else if (res && res.code === 1) {
            // yield put({ type: 'setData', payload: res.data })
            yield put({ type: 'save', payload: { PageInfo: { list: res.data.list, pageNum: res.data.pageNum, pages: res.data.pages, pageSize: res.data.pageSize } } })
          }
        }
      }
    },
    * modifySpendType({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.modifySpendType, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        if (response.message !== '-2') {
          Toast.success('操作成功', 2)
          const res = yield call(service.getSpendTypeListsByPage, { pageNum: 1, pageSize: 7, ...queryParam })
          if (res && res.code === -1) {
            Toast.fail('查询消费类型列表失败', 2)
          } else if (res && res.code === 1) {
            // yield put({ type: 'setData', payload: res.data })
            yield put({ type: 'save', payload: { PageInfo: { list: res.data.list, pageNum: res.data.pageNum, pages: res.data.pages, pageSize: res.data.pageSize } } })
          }
        }
      }
    },
    * getSpendTypeListsByPage({ payload, callback }, { call, put }) {
      const res = yield call(service.getSpendTypeListsByPage, payload)
      if (res && res.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(res); // 返回结果
        }
      } else {
        Toast.fail('分页查询消费类型列表失败', 2)
      }
    },
    * findPieAndBarDatas({ payload, callback }, { call, put }) {
      const response = yield call(service.findPieAndBarDatas, payload)
      yield put({ type: 'save', payload: { dataGroupByYear: response.data.dataGroupByYear, dataGroupByType: response.data.dataGroupByType } })
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
