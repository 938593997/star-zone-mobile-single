import React, { Component } from 'react'
import { connect } from 'dva'
import { Modal, Button, WhiteSpace, List, InputItem, Tabs, Card } from 'antd-mobile'
import { StickyContainer, Sticky } from 'react-sticky'
import ReactPullLoad, { STATS } from 'react-pullload'
import DatePickers from 'react-mobile-datepicker'
import moment from 'moment'
import styles from './index.less'
import Search from './Search'
import { createForm } from 'rc-form/lib'
import "../../../../node_modules/react-pullload/dist/ReactPullLoad.css"

const { alert } = Modal
const nowTimeStamp = Date.now()
const now = new Date(nowTimeStamp)
const mainTabs = [{ title: '我的申请' }, { title: '我的审批' }, { title: '审批历史' }]
function renderTabBar(props) {
  return (
    <Sticky>
      {({ style }) => <div style={{ ...style, zIndex: 1 }}><Tabs.DefaultTabBar {...props} page={3} /></div>}
    </Sticky>
  )
}

@connect(({ approveforleave, loading }) => ({ approveforleave, loading: loading.models.approveforleave }))
@createForm()
class ApproveForLeaveListPage extends Component {
  constructor(props) {
    super(props)
    this.state = {
      editData: null, // 编辑界面回显信息
      isShowAddOrModifyTypePage: false, // 是否展示添加过修改消费类型界面

      hasMore: true, // 是否还有数据可以加载
			action: STATS.init, // 操作类型（刷新还是加载操作，加载操作要判断是否是最后一页，最后一页的话就直接显示无更多数据）
      index: 1, // loading more test time limit 可以下拉查询几次数据，相当于分页的总页数，但是这里要总页数减一，比如有2页的数据，实际上只能下拉查询一次，之后的查询都不会跟数据库交互，默认给值 1
      num: 0, // 当前页
      searchVal: undefined, // 过滤条件

      openTimeSelect_e: false, //打开时间选择
      openTimeSelect_s: false, //打开时间选择
      eDate: now, // 选择的时间
      sDate: now, // 选择的时间
      activedTitle: '我的申请', // 默认 我的申请  我的审批  审批历史
      showFollow: false, // 展示跟踪页
      imgFollowNginxUrl: null, // 流程跟踪图片
      isShowApplyPage: false, // 展示审批页面
      isShowResubmitPage: false, // 展示重新提交页面
      eDate_resubmit: null, // 选择的时间
      sDate_resubmit: null, // 选择的时间
    }
  }

  componentDidMount() {
    const { dispatch, approveforleave: { PageInfo: { pages } } } = this.props
    const { activedTitle } = this.state
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    dispatch({
      type: 'approveforleave/getListsByPage',
      payload: { owner, pageNum: 1, pageSize: 7, activedTitle },
      callback: (res) => {
        const PageInfo = {}
        PageInfo.list = res.data.list
        PageInfo.pageNum =  res.data.pageNum
        PageInfo.pages = res.data.pages
        PageInfo.pageSize = res.data.pageSize
        dispatch({ type: 'approveforleave/save', payload: { PageInfo } })
        this.setState({ index: res.data.pages - 1 })
      }
    })
    const queryParam = {}
    queryParam.owner = owner
    queryParam.activedTitle = activedTitle
    dispatch({ type: 'approveforleave/save', payload: { queryParam } })
    this.setState({ index: pages - 1 })
  }

  handleAction = (action) => {
    // console.info(action, this.state.action,action === this.state.action);
    const { dispatch } = this.props
		// new action must do not equel to old action
		if (action === this.state.action ||
			action === STATS.refreshing && this.state.action === STATS.loading ||
			action === STATS.loading && this.state.action === STATS.refreshing) {
			return false
		}
    if (action === STATS.refreshing) { // 刷新
      const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
      const { searchVal, activedTitle } = this.state
      const reqParam = {}
      reqParam.keyWords = searchVal
      searchVal === undefined ?  delete reqParam.keyWords : ''
      dispatch({
        type: 'approveforleave/getListsByPage',
        payload: { owner, pageNum: 1, pageSize: 7, ...reqParam, activedTitle },
        callback: (res) => {
          setTimeout(() => {
            const PageInfo = {}
            PageInfo.list = res.data.list // 刷新后，直接覆盖原来的数据
            PageInfo.pageNum =  res.data.pageNum
            PageInfo.pages = res.data.pages
            PageInfo.pageSize = res.data.pageSize
            dispatch({ type: 'approveforleave/save', payload: { PageInfo } })
            // refreshing complete
            this.setState({
              hasMore: true,
              action: STATS.refreshed,
              index: res.data.pages - 1, // 可刷新次数为总页数减一
            });
          }, 1000)
        }
      })
		} else if (action === STATS.loading) { // 加载更多
      this.setState({ hasMore: true })
      if(this.state.index === 0){
        setTimeout(() => {
          this.setState({ action: STATS.reset, hasMore: false })
        }, 500)
      } else {
        const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
        const { approveforleave: { PageInfo: { list, pageNum } } } = this.props
        const { searchVal, activedTitle } = this.state
        const reqParam = {}
        reqParam.keyWords = searchVal
        searchVal === undefined ?  delete reqParam.keyWords : ''
        dispatch({
          type: 'approveforleave/getListsByPage',
          payload: { owner, pageNum: pageNum + 1, pageSize: 7, ...reqParam, activedTitle },
          callback: (res) => {
            setTimeout(() => {
              const PageInfo = {}
              PageInfo.list = [...list, ...res.data.list] // 将新查出来的数据加到原来的数据中，一起展示
              PageInfo.pageNum =  res.data.pageNum
              PageInfo.pages = res.data.pages
              PageInfo.pageSize = res.data.pageSize
              dispatch({ type: 'approveforleave/save', payload: { PageInfo } })
              this.setState({
                action: STATS.reset,
                index: this.state.index - 1,  // 可刷新次数为总页数减一
              })
            }, 1000)
          }
        })
      }
		}
		//DO NOT modify below code
		this.setState({ action: action })
  }

  searchInfo = (val) => { // 模糊搜索选择
    const { dispatch, approveforleave: { queryParam } } = this.props
    const keyWords = val
    queryParam.keyWords = keyWords
    dispatch({
      type: 'approveforleave/getListsByPage',
      payload: { pageNum: 1, pageSize: 7, ...queryParam },
      callback: (res) => {
        setTimeout(() => {
          const PageInfo = {}
          PageInfo.list = res.data.list // 条件查询后，覆盖原有数据
          PageInfo.pageNum =  res.data.pageNum
          PageInfo.pages = res.data.pages
          PageInfo.pageSize = res.data.pageSize
          dispatch({ type: 'approveforleave/save', payload: { PageInfo } })
          this.setState({
            hasMore: true, // 默认让用户可以下拉
            action: STATS.reset,
            index: res.data.pages - 1, // 可刷新次数为总页数减一
            searchVal: val, // 过滤条件
          })
        }, 1000)
      }
     })

    dispatch({ type: 'approveforleave/save', payload: { queryParam } })
  }

  showDetailPage = (val) => { // 我的申请详情页面
    this.setState({ isShowAddOrModifyTypePage: true, editData: val })
    this.toggleBody(1)
  }

  onShow = (key) => { // 展示弹框
    this.setState({ [key]: true })
    this.toggleBody(1)
  }

  onCloseMadeal = (key) => { // 隐藏弹框
    this.props.form.resetFields()
    this.setState({ [key]: false, editData: null, imgFollowNginxUrl: null })
    this.toggleBody(0)
  }

  saveApply = () => { // 保存分类
    const { dispatch, approveforleave: { queryParam } } = this.props
    const applyReason = this.props.form.getFieldValue('applyReason')
    const startDate = this.props.form.getFieldValue('startDate')
    const endDate = this.props.form.getFieldValue('endDate')
    // const type = this.props.match.params.list
    if (!applyReason || applyReason.replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请输入请假原因')
      return false
    }
    if (!startDate || (startDate + '').replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请选择开始时间')
      return false
    }
    if (!endDate || (endDate + '').replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请选择结束时间')
      return false
    }
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    dispatch({
      type: 'approveforleave/saveApply',
      payload: { applyReason, startDate, endDate, owner, queryParam },
      callback: (res) => {
        if (res) {
          // dispatch({ type: 'approveforleave/getListsByPage', payload: { owner, pageNum: 1, pageSize: 7 } })
          this.setState({ isShowAddOrModifyTypePage: false, sDate: now, eDate: now })
          this.toggleBody(0) // 关闭弹框
          this.props.form.resetFields()
        }
      }
    })
  }

  forbiddenInput = (e) => {
    document.activeElement.blur()
  }

  scrollToView = (value) => {
    document.getElementById(value).scrollIntoView()
  }

  toggleBody = (isPin) => { // 防止底层滚动
    let body = document.body
    let top = body.scrollTop
    if(isPin){
      body.style.cssText = 'width: 100%; height: 100%; position: fixed; top: -' + top + 'px; left: 0; overflow: hidden;'
    } else {
      body.removeAttribute('style')
      body.scrollTop = top
    }
  }

  onChangeModal = (key, value) => { // 打开，关闭弹框
    document.activeElement.blur()
    this.setState({ [key]: value })
    if (!value) {
      if (key === 'isShowAddOrModifyTypePage' || key === 'isShowApplyPage' || key === 'isShowResubmitPage') {
        this.toggleBody(1) // 打开弹框
      }
    } else {
      if (key === 'isShowAddOrModifyTypePage' || key === 'isShowApplyPage' || key === 'isShowResubmitPage') {
        this.toggleBody(0) // 关闭弹框
      }
    }
  }

  TabsChange = (val) => {
    const { dispatch, approveforleave: { queryParam } } = this.props
    queryParam.activedTitle = val.title
    dispatch({ type: 'approveforleave/save', payload: { queryParam } }) // 改变查询条件
    this.setState({ activedTitle: val.title })
    dispatch({
      type: 'approveforleave/getListsByPage',
      payload: { pageNum: 1, pageSize: 7, ...queryParam },
      callback: (res) => {
        dispatch({ type: 'approveforleave/save', payload: { PageInfo: {} } }) // 清空原来数据
        setTimeout(() => {
          const PageInfo = {}
          PageInfo.list = res.data.list // 条件查询后，覆盖原有数据
          PageInfo.pageNum =  res.data.pageNum
          PageInfo.pages = res.data.pages
          PageInfo.pageSize = res.data.pageSize
          dispatch({ type: 'approveforleave/save', payload: { PageInfo } })
          this.setState({
            hasMore: true, // 默认让用户可以下拉
            action: STATS.reset,
            index: res.data.pages - 1, // 可刷新次数为总页数减一
            searchVal: val, // 过滤条件
          })
        }, 1000)
      }
     })

  }

  queryApplyFollow = (value) => { // 流程跟踪
    const { dispatch } = this.props
    dispatch({
      type: 'approveforleave/queryApplyFollow',
      payload: { processId: value.processId },
      callback: (res) => {
        if (res && res.data && res.data.imgFollowNginxUrl) {
          this.setState({ imgFollowNginxUrl: res.data.imgFollowNginxUrl })
          this.onShow('showFollow')
        }
      },
    }) // 改变查询条件
  }

  showApplyPage = (val) => { // 展示审批页面
    const { dispatch } = this.props
    dispatch({
      type: 'approveforleave/queryApplyInfoByProcessId',
      payload: { processId: val.processId },
      callback: (res) => {
        if (res && res.data) {
          if (val.isReject) { // 是驳回到第一个节点，展示重新提交申请页面
            this.setState({ isShowResubmitPage: true, editData: res.data })
          } else { // 展示审批页面
            this.setState({ isShowApplyPage: true, editData: res.data })
          }
          this.toggleBody(1)
        }
      },
    })
  }

  submitApprove = (type) => { // 提交审批 type为： reject  or agree
    const { dispatch, approveforleave: { queryParam } } = this.props
    const description = this.props.form.getFieldValue('description_approve')
    const { editData } = this.state
    if (!description || description.replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请输入审批意见')
      return false
    }
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    const condition = type && type === 'agree' ? (editData && editData.applyDate / 1 >= 2 ? '3' : '1') : '0'
    dispatch({
      type: 'approveforleave/submitApprove',
      payload: { ...editData, description, owner, queryParam, condition },
      callback: (res) => {
        if (res) {
          this.setState({ isShowApplyPage: false, editData: null })
          this.toggleBody(0) // 关闭弹框
          this.props.form.resetFields()
        }
      }
    })
  }

  reSubmitApply = () => {
    const { dispatch, approveforleave: { queryParam } } = this.props
    const { editData } = this.state
    const applyReason = this.props.form.getFieldValue('applyReason_resubmit')
    const startDate = this.props.form.getFieldValue('startDate_resubmit')
    const endDate = this.props.form.getFieldValue('endDate_resubmit')
    // const type = this.props.match.params.list
    if (!applyReason || applyReason.replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请输入请假原因')
      return false
    }
    if (!startDate || (startDate + '').replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请选择开始时间')
      return false
    }
    if (!endDate || (endDate + '').replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请选择结束时间')
      return false
    }
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    dispatch({
      type: 'approveforleave/reSubmitApply',
      payload: { applyReason, startDate, endDate, owner, queryParam, processId: editData.processId },
      callback: (res) => {
        if (res) {
          this.setState({ isShowResubmitPage: false, sDate_resubmit: null, eDate_resubmit: null })
          this.toggleBody(0) // 关闭弹框
          this.props.form.resetFields()
        }
      }
    })
  }

  render() {
    const { approveforleave: { PageInfo: { list } }, dispatch, form: { getFieldProps } } = this.props
    const {
      isShowAddOrModifyTypePage, editData, hasMore, sDate, eDate, openTimeSelect_s, openTimeSelect_e, showFollow, imgFollowNginxUrl, isShowApplyPage, isShowResubmitPage, openTimeSelect_s_resubmit, openTimeSelect_e_resubmit, sDate_resubmit, eDate_resubmit,
    } = this.state

    return (
      <div className={styles.content_me} >

        <Search dispatch={dispatch} searchInfo={this.searchInfo} />
        <div className={`${styles.service_info} box_shadow`}>

        <StickyContainer>
            <Tabs tabs={mainTabs}
              initalPage={'t1'}
              renderTabBar={renderTabBar}
              onChange={(val) => this.TabsChange(val)}
            >
              <div style={{ padding: '8px 8px' }}>
                <div style={{ textAlign: 'right', marginRight: 8 }}>
                  <div style={{ marginBottom: -12, marginTop: 8 }}><Button onClick={() => this.onShow('isShowAddOrModifyTypePage')} type="ghost" inline size="small">添加</Button></div>
                </div>

                <ReactPullLoad
                  downEnough={100}
                  ref="reactpullload"
                  className="block"
                  isBlockContainer={false}
                  action={this.state.action}
                  handleAction={this.handleAction}
                  hasMore={hasMore}
                  style={{paddingTop: 0}}
                  distanceBottom={1000}>
                  <ul className="test-ul" style={{ marginLeft: -36 }}>
                  {
                      list && list[0] ? list.map((item, key) =>
                        <div key={key} className={styles.amCard}>
                        <WhiteSpace size="lg" />
                        <Card>
                          <Card.Header
                            // title={item.name}
                            // onClick={() => this.linkurlToDetailPage(item)}
                            thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
                            extra={<span>{item && item.applyReason ? item.applyReason : null}</span>}
                          />
                          <Card.Footer content={<a onClick={() => this.showDetailPage(item)}>详情</a>} extra={<div><a onClick={() => this.queryApplyFollow(item)}>跟踪</a></div>} />
                        </Card>
                        </div>
                        )
                        :
                        <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
                    }
                  </ul>
                </ReactPullLoad>
              </div>

              <div style={{ padding: '8px 8px' }}>
                <ReactPullLoad
                  downEnough={100}
                  ref="reactpullload"
                  className="block"
                  isBlockContainer={false}
                  action={this.state.action}
                  handleAction={this.handleAction}
                  hasMore={hasMore}
                  style={{paddingTop: 0}}
                  distanceBottom={1000}>
                  <ul className="test-ul" style={{ marginLeft: -36 }}>
                  {
                      list && list[0] ? list.map((item, key) =>
                        <div key={key} className={styles.amCardApproving}>
                        <WhiteSpace size="lg" />
                        <Card>
                          <Card.Header
                            // title={item.name}
                            // onClick={() => this.linkurlToDetailPage(item)}
                            thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
                            extra={<span>审批节点：{item && item.taskName ? item.taskName : null}</span>}
                          />
                          <Card.Footer content={<a onClick={() => this.showApplyPage(item)}>查看</a>} extra={<div><a onClick={() => this.queryApplyFollow(item)}>跟踪</a></div>} />
                        </Card>
                        </div>
                        )
                        :
                        <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
                    }
                  </ul>
                </ReactPullLoad>
              </div>

              <div style={{ padding: '8px 8px' }}>
                <ReactPullLoad
                  downEnough={100}
                  ref="reactpullload"
                  className="block"
                  isBlockContainer={false}
                  action={this.state.action}
                  handleAction={this.handleAction}
                  hasMore={hasMore}
                  style={{paddingTop: 0}}
                  distanceBottom={1000}>
                  <ul className="test-ul" style={{ marginLeft: -36 }}>
                  {
                      list && list[0] ? list.map((item, key) =>
                        <div key={key} className={styles.amCardApproved} onClick={() => this.queryApplyFollow(item)}>
                        <WhiteSpace size="lg" />
                        <Card>
                          <Card.Header
                            // title={item.name}
                            // onClick={() => this.linkurlToDetailPage(item)}
                            thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
                            extra={<span>审批节点：{item && item.taskName ? item.taskName : null}</span>}
                          />
                          <Card.Body>
                            <div>申请情况：{item && item.desc ? item.desc : ''}</div>
                            <div>处理状态：{item && item.deleteReason ? item.deleteReason : ''}</div>
                            <div>处理人：{item && item.userId ? item.userId : ''}</div>
                            <div>处理时间：{item && item.endTime ? item.endTime : ''}</div>
                            <div>耗时：{item && item.durationInMillis ? item.durationInMillis / 1000 : ''}s</div>
                          </Card.Body>
                          {/* <Card.Footer content={<a onClick={() => this.showDetailPage(item)}>查看</a>} extra={<div><a onClick={() => this.queryApplyFollow(item)}>跟踪</a></div>} /> */}
                        </Card>
                        </div>
                        )
                        :
                        <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
                    }
                  </ul>
                </ReactPullLoad>
              </div>
            </Tabs>
          </StickyContainer>
        </div>

        <Modal
          visible={isShowAddOrModifyTypePage}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={() => this.onCloseMadeal('isShowAddOrModifyTypePage')}
          footer={editData ? [{ text: '关闭', onPress: () => this.onCloseMadeal('isShowAddOrModifyTypePage') }] : [{ text: '关闭', onPress: () => this.onCloseMadeal('isShowAddOrModifyTypePage') }, { text: '保存', onPress: editData ? () => this.onCloseMadeal('isShowAddOrModifyTypePage') : () => this.saveApply() }]}
        >
          <div style={{ maxHeight: 200, minHeight: 150 }}>
            <DatePickers
              value={sDate}
              isOpen={openTimeSelect_s}
              onSelect={sDate => this.setState({ sDate, openTimeSelect_s: false })}
              onCancel={() => this.onChangeModal('openTimeSelect_s', false)}
            />
            <DatePickers
              value={eDate}
              isOpen={openTimeSelect_e}
              onSelect={eDate => this.setState({ eDate, openTimeSelect_e: false })}
              onCancel={() => this.onChangeModal('openTimeSelect_e', false)}
            />
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>{editData ? '申请详情' : '填写申请'}</div>}>

              <InputItem
                {...getFieldProps('applyReason', {
                  initialValue: editData ? editData.applyReason : undefined,
                })}
                type="text"
                placeholder="请输入"
                id="applyReason"
                onFocus={() => this.scrollToView('applyReason')}
                style={{ textAlign: 'left' }}
                disabled={editData ? true : false}
              ><div style={{ color: editData ? 'rgb(136, 136, 136)' : '#000' }}>请假原因</div></InputItem>

              <InputItem
                  {...getFieldProps('startDate', {
                      initialValue: editData && editData.startDate ? editData.startDate : (sDate ? moment(sDate).format('YYYY-MM-DD') : null),
                    })
                  }
                  placeholder={'请选择'}
                  readOnly
                  id="startDate"
                  onFocus={() => { this.onChangeModal('openTimeSelect_s', true); this.scrollToView('startDate') }}
                  disabled={editData ? true : false}
                ><div style={{ color: editData ? 'rgb(136, 136, 136)' : '#000' }}>开始时间</div></InputItem>

                <InputItem
                  {...getFieldProps('endDate', {
                     initialValue: editData && editData.endDate ? editData.endDate : (eDate ? moment(eDate).format('YYYY-MM-DD') : null),
                    })
                  }
                  placeholder={'请选择'}
                  readOnly
                  id="endDate"
                  onFocus={() => { this.onChangeModal('openTimeSelect_e', true); this.scrollToView('endDate') }}
                  disabled={editData ? true : false}
                ><div style={{ color: editData ? 'rgb(136, 136, 136)' : '#000' }}>结束时间</div></InputItem>
             </List>
         </div>
        </Modal>

        <Modal
          visible={showFollow}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={() => this.onCloseMadeal('showFollow')}
          footer={[{ text: '关闭', onPress: () => this.onCloseMadeal('showFollow') }]}
        >
          <div style={{ maxHeight: 500, minHeight: 150 }}>
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>{'流程信息'}</div>}>
              <img src={imgFollowNginxUrl} alt="" />
             </List>
         </div>
        </Modal>

        <Modal
          visible={isShowApplyPage}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          closable={true}
          onClose={() => this.onCloseMadeal('isShowApplyPage')}
          footer={[{ text: '驳回', onPress: () => this.submitApprove('reject') }, { text: '同意', onPress: () => this.submitApprove('agree') }]}
        >
          <div style={{ maxHeight: 200, minHeight: 150 }}>
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>{'审批'}</div>}>
              <InputItem
                {...getFieldProps('userId_approve', {
                  initialValue: editData ? editData.userId : undefined,
                })}
                type="text"
                // placeholder="请输入"
                id="userId_approve"
                // onFocus={() => this.scrollToView('userId_approve')}
                style={{ textAlign: 'left' }}
                disabled={editData ? true : false}
              ><div style={{ color: editData ? 'rgb(136, 136, 136)' : '#000' }}>申请人</div></InputItem>

              <InputItem
                {...getFieldProps('applyReason_approve', {
                  initialValue: editData ? editData.applyReason : undefined,
                })}
                type="text"
                // placeholder="请输入"
                id="applyReason_approve"
                // onFocus={() => this.scrollToView('applyReason')}
                style={{ textAlign: 'left' }}
                disabled={editData ? true : false}
              ><div style={{ color: editData ? 'rgb(136, 136, 136)' : '#000' }}>请假原因</div></InputItem>

              <InputItem
                  {...getFieldProps('startDate_approve', {
                      initialValue: editData ? editData.startDate : undefined,
                    })
                  }
                  // placeholder={'请选择'}
                  readOnly
                  id="startDate_approve"
                  // onFocus={() => { this.onChangeModal('openTimeSelect_s', true); this.scrollToView('startDate') }}
                  disabled={editData ? true : false}
                ><div style={{ color: editData ? 'rgb(136, 136, 136)' : '#000' }}>开始时间</div></InputItem>

                <InputItem
                  {...getFieldProps('endDate_approve', {
                     initialValue: editData ? editData.endDate : undefined,
                    })
                  }
                  // placeholder={'请选择'}
                  readOnly
                  id="endDate_approve"
                  // onFocus={() => { this.onChangeModal('openTimeSelect_e', true); this.scrollToView('endDate') }}
                  disabled={editData ? true : false}
                ><div style={{ color: editData ? 'rgb(136, 136, 136)' : '#000' }}>结束时间</div></InputItem>
                <InputItem
                  {...getFieldProps('description_approve', {
                    initialValue: undefined,
                  })}
                  type="text"
                  placeholder="请输入"
                  id="description_approve"
                  onFocus={() => this.scrollToView('description_approve')}
                  style={{ textAlign: 'left' }}
                ><div style={{ color: '#000' }}>审批意见</div></InputItem>
             </List>
         </div>
        </Modal>

        <Modal
          visible={isShowResubmitPage}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={() => this.onCloseMadeal('isShowResubmitPage')}
          footer={[{ text: '关闭', onPress: () => this.onCloseMadeal('isShowResubmitPage') }, { text: '提交', onPress: () => this.reSubmitApply() }]}
        >
          <div style={{ maxHeight: 200, minHeight: 150 }}>
            <DatePickers
              value={editData && editData.startDate && !sDate_resubmit ? new Date(editData.startDate) : sDate_resubmit}
              isOpen={openTimeSelect_s_resubmit}
              onSelect={sDate_resubmit => this.setState({ sDate_resubmit, openTimeSelect_s_resubmit: false })}
              onCancel={() => this.onChangeModal('openTimeSelect_s_resubmit', false)}
            />
            <DatePickers
              // value={eDate_resubmit}
              value={editData && editData.endDate && !eDate_resubmit ? new Date(editData.endDate) : eDate_resubmit}
              isOpen={openTimeSelect_e_resubmit}
              onSelect={eDate_resubmit => this.setState({ eDate_resubmit, openTimeSelect_e_resubmit: false })}
              onCancel={() => this.onChangeModal('openTimeSelect_e_resubmit', false)}
            />
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>{'重新填写申请'}</div>}>

              <InputItem
                {...getFieldProps('applyReason_resubmit', {
                  initialValue: editData ? editData.applyReason : undefined,
                })}
                type="text"
                placeholder="请输入"
                id="applyReason_resubmit"
                onFocus={() => this.scrollToView('applyReason_resubmit')}
                style={{ textAlign: 'left' }}
                // disabled={editData ? true : false}
              ><div style={{ color: '#000' }}>请假原因</div></InputItem>

              <InputItem
                  {...getFieldProps('startDate_resubmit', {
                      // initialValue: sDate_resubmit ? moment(sDate_resubmit).format('YYYY-MM-DD') : null,
                      initialValue: editData && editData.startDate && !sDate_resubmit ? editData.startDate : moment(sDate_resubmit).format('YYYY-MM-DD'),
                    })
                  }
                  placeholder={'请选择'}
                  readOnly
                  id="startDate_resubmit"
                  onFocus={() => { this.onChangeModal('openTimeSelect_s_resubmit', true); this.scrollToView('startDate_resubmit') }}
                  // disabled={editData ? true : false}
                ><div style={{ color: '#000' }}>开始时间</div></InputItem>

                <InputItem
                  {...getFieldProps('endDate_resubmit', {
                    //  initialValue: eDate_resubmit ? moment(eDate_resubmit).format('YYYY-MM-DD') : null,
                    initialValue: editData && editData.endDate && !eDate_resubmit ? editData.endDate : moment(eDate_resubmit).format('YYYY-MM-DD'),
                    })
                  }
                  placeholder={'请选择'}
                  readOnly
                  id="endDate_resubmit"
                  onFocus={() => { this.onChangeModal('openTimeSelect_e_resubmit', true); this.scrollToView('endDate_resubmit') }}
                  // disabled={editData ? true : false}
                ><div style={{ color: '#000' }}>结束时间</div></InputItem>
             </List>
         </div>
        </Modal>

      </div>
    )
  }
}

export default ApproveForLeaveListPage
