import * as service from '../../../../services/approveforleave'
import { Toast } from 'antd-mobile'

export default {
  namespace: 'approveforleave',
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
  },
  effects: {
    * saveApply({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.saveApply, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        Toast.success('操作成功', 2)
        const res = yield call(service.getListsByPage, { pageNum: 1, pageSize: 7, ...queryParam })
        yield put({ type: 'save', payload: { PageInfo: { list: res.data.list, pageNum: res.data.pageNum, pages: res.data.pages, pageSize: res.data.pageSize } } })
      }
    },
    * getListsByPage({ payload, callback }, { call, put }) { // 分页查询
      const res = yield call(service.getListsByPage, payload)
      if (res && res.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(res); // 返回结果
        }
      } else {
        Toast.fail('分页查询申请管理列表失败', 2)
      }
    },
    * queryApplyFollow({ payload, callback }, { call, put }) { // 分页查询
      const res = yield call(service.queryApplyFollow, payload)
      if (res && res.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(res); // 返回结果
        }
      } else {
        Toast.fail('流程跟踪获取流程图失败', 2)
      }
    },
    * queryApplyInfoByProcessId({ payload, callback }, { call, put }) { // 审批时，查询审批信息
      const res = yield call(service.queryApplyInfoByProcessId, payload)
      if (res && res.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(res); // 返回结果
        }
      } else {
        Toast.fail('查询审批信息失败', 2)
      }
    },
    * submitApprove({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.submitApprove, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        Toast.success('操作成功', 2)
        const res = yield call(service.getListsByPage, { pageNum: 1, pageSize: 7, ...queryParam })
        yield put({ type: 'save', payload: { PageInfo: { list: res.data.list, pageNum: res.data.pageNum, pages: res.data.pages, pageSize: res.data.pageSize } } })
      }
    },
    * reSubmitApply({ payload, callback }, { call, put }) {
      const { queryParam } = payload
      const response = yield call(service.reSubmitApply, payload)
      if (response && response.code === 1) {
        if (callback && typeof callback === 'function') { // callback用法
          callback(response); // 返回结果
        }
        Toast.success('操作成功', 2)
        const res = yield call(service.getListsByPage, { pageNum: 1, pageSize: 7, ...queryParam })
        yield put({ type: 'save', payload: { PageInfo: { list: res.data.list, pageNum: res.data.pageNum, pages: res.data.pages, pageSize: res.data.pageSize } } })
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
};
