import React, { Component } from 'react'
import { connect } from 'dva'
import { Modal , Toast, Button, WhiteSpace, List, InputItem, PickerView, Tabs, Card } from 'antd-mobile'
import { Pie, Bar } from 'react-chartjs-2'
import { StickyContainer, Sticky } from 'react-sticky'
import DatePickers from 'react-mobile-datepicker'
import moment from 'moment'
import styles from './index.less'
import Search from './Search'
import { createForm } from 'rc-form'

const { alert } = Modal
const nowTimeStamp = Date.now()
const now = new Date(nowTimeStamp)
const showToastTime = 2
const dailyUseChooseList = [{value: 1, label: '化妆品'}, {value: 2, label: '面膜'}, {value: 3, label: '香水'}, {value: 4, label: '其他'}]
const educateChooseList = [{value: 1, label: '学费'}, {value: 2, label: '补习费'}, {value: 3, label: '培训'}]
const educatePeriodChooseList = [{value: 1, label: '托儿所'}, {value: 2, label: '幼儿园'}, {value: 3, label: '小学'}, {value: 4, label: '初中'}, {value: 5, label: '高中'}, {value: 6, label: '大学'}, {value: 7, label: '研究生'}, {value: 8, label: '博士'}]
const entertainmentChooseList = [{value: 1, label: '超级开心'}, {value: 2, label: '挺开心'}, {value: 3, label: '还好'}, {value: 4, label: '不愉快'}, {value: 5, label: '很不愉快'}]
const goHomeSpendsChooseList = [{value: 1, label: '带礼物'}, {value: 2, label: '带水果什么的'}, {value: 3, label: '给长辈零花钱'}, {value: 4, label: '买电器'}, {value: 5, label: '买家具'}, {value: 6, label: '装修房子'}, {value: 7, label: '其他'}]
const goOutChooseList = [{value: 1, label: '共享单车'}, {value: 2, label: '公交车'}, {value: 3, label: '地铁'}, {value: 4, label: '打的'}, {value: 5, label: '动车'}, {value: 6, label: '火车'}, {value: 7, label: '飞机'}, {value: 8, label: '轮船'}, {value: 9, label: '摩的（两/三轮）'}, {value: 10, label: '人力三轮车'}, {value: 11, label: '开车'}, {value: 12, label: '骑摩托'}, {value: 13, label: '骑电动车'}]
const houseTypeChooseList = [{value: 1, label: '民房'}, {value: 2, label: '小区套房'}, {value: 3, label: '其他'}]
const relationShipChooseList = [{value: 1, label: '出钱'}, {value: 2, label: '收钱'}]

const isIPhone = new RegExp('\\biPhone\\b|\\biPod\\b', 'i').test(window.navigator.userAgent)
let moneyKeyboardWrapProps
if (isIPhone) {
  moneyKeyboardWrapProps = {
    onTouchStart: e => e.preventDefault(),
  };
}
const mainTabs = [{ title: '饼状图' }, { title: '柱状图' }]
function renderTabBar(props) {
  return (<Sticky>
    {({ style }) => <div style={{ ...style, zIndex: 1 }}><Tabs.DefaultTabBar {...props} /></div>}
  </Sticky>);
}

@connect(({ spendsdetails, loading }) => ({ spendsdetails, loading: loading.models.spendsdetails }))
@createForm()
class SpendDetailListPage extends Component {
  constructor(props) {
    super(props)
    this.state = {
      editData: null, // 编辑界面回显信息
      isShowAddOrModifyTypePage: false, // 是否展示添加过修改消费类型界面
      urlDetailShowWay: null, // 地址栏传过来的消费类型
      SpendDetailsData: null, // 花费分类列表跳到具体花费列表时，具体花费的数据
      openTimeSelect: false, //打开时间选择
      date: now, // 选择的时间
      DetailsData: null, // 详情页展示数据
      openDailyUsed: false, // 是否打开选择日用品类型弹框
      selectedDailyUsedTypeVal: undefined, // 选择的日用品类型
      openEducate: false, // 是否打开选择教育支出缴费类型弹框
      selectedEducateTypeVal: undefined, // 选择缴费类型
      openEducatePeriod: false, // 是否打开选择教育支出教育时期弹框
      selectedEducatePeriodVal: undefined, // 选择教育时期
      openEntertainmentSelect: false, // 娱乐支出开心程度选择弹框
      selectedentertainmentVal: undefined, // 选择的娱乐支出的开心程度
      openGoHomeSpends: false, // 回家花销选择弹框
      selectedGoHomeSpendsVal: undefined, // 选择的回家花销
      openGoOut: false, // 出行方式选择弹框
      selectedGoOutTypeVal: undefined, // 选择的出行方式
      openHouseType: false, // 住房类型选择弹框
      selectedHouseTypeVal: undefined, // 选择的住房类型
      openRelationShip: false, // 人际关系出或者入选择弹框
      selectedRelationShipVal: undefined, // 选择的人际关系出或者入
      showChars: false, // 展示图表
    }
  }

  componentDidMount() {
    const { dispatch } = this.props
    const SpendDetailsData = JSON.parse(localStorage.getItem('SpendDetailsData')) // 接收点击的花费分类列表信息
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    const detailShowWay = this.props.match.params.list
    this.setState({ urlDetailShowWay: detailShowWay, SpendDetailsData })
    localStorage.removeItem('SpendDetailsData') // 接收完后移除
    dispatch({ type: 'spendsdetails/getSpendDetailsLists', payload: { owner, detailShowWay } })
    const queryParam = {}
    dispatch({
      type: 'spendsdetails/getSpendTypeLists',
      payload: { owner, detailShowWay },
      callback: (res) => {
        this.setState({ SpendDetailsData: res.data[0] })
      }
    })
    queryParam.owner = owner
    queryParam.detailShowWay = detailShowWay
    dispatch({ type: 'spendsdetails/save', payload: { queryParam } })

    dispatch({ type: 'spendsdetails/findPieAndBarDatas', payload: { owner, detailShowWay } }) // 查询具体支出中的图表数据
  }

  searchSpendDetail = (val) => { // 模糊搜索选择
    const { dispatch, spendsdetails: { queryParam } } = this.props
    const name = val
    queryParam.name = name
    dispatch({ type: 'spendsdetails/getSpendDetailsLists', payload: { ...queryParam } })
    dispatch({ type: 'spendsdetails/save', payload: { queryParam } })
  }

  showDetailpage = (val) => { // 展示消费详情页面
    this.setState({ isShowAddOrModifyTypePage: true, DetailsData: val, date: new Date(val.happenTime) })
    this.toggleBody(1) // 打开弹框
  }

  showEditSpendTypePage = (val) => { // 展示选择编辑界面
    this.setState({ isShowAddOrModifyTypePage: true, editData: val, date: new Date(val.happenTime) })
    this.toggleBody(1) // 打开弹框
  }

  onShow = (key) => { // 展示弹框
    this.setState({ [key]: true });
    this.toggleBody(1) // 打开弹框
  }

  onChangeModal = (key, value) => { // 打开，关闭弹框
    document.activeElement.blur()
    this.setState({ [key]: value })
    if (!value) {
      if (key === 'isShowAddOrModifyTypePage' || key === 'showChars') {
        this.toggleBody(1) // 打开弹框
      }
    } else {
      if (key === 'isShowAddOrModifyTypePage' || key === 'showChars') {
        this.toggleBody(0) // 关闭弹框
      }
    }
  }

  onChangeSelectModal = (key, value) => { // 打开，关闭选择的弹框
    document.activeElement.blur()
    this.setState({ [key]: value })
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

  delSpendDetails = (val) => { // 删除花销分类
    const { dispatch, spendsdetails: { queryParam } } = this.props
    alert('删除', '确定要删除？', [
      { text: '取消', onPress: () => console.log('cancel') },
      {
        text: '确定',
        onPress: () => {
          dispatch({
            type: 'spendsdetails/delSpendDetails',
            payload: { id: val.id, queryParam },
            callback: (res) => {
              const detailShowWay = this.props.match.params.list
              const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
              dispatch({ type: 'spendsdetails/findPieAndBarDatas', payload: { owner, detailShowWay } }) // 查询具体支出中的图表数据
            }
          })
        }
      },
    ])
  }

  onCloseMadeal = (key) => { // 隐藏弹框*****
    this.props.form.resetFields()
    this.setState({ [key]: false, DetailsData: null, editData: null, selectedDailyUsedTypeVal: undefined, date: now, selectedEducateTypeVal: undefined, selectedEducatePeriodVal: undefined, selectedentertainmentVal: undefined, selectedGoHomeSpendsVal: undefined, selectedGoOutTypeVal: undefined, selectedHouseTypeVal: undefined, selectedRelationShipVal: undefined })
    this.toggleBody(0) // 关闭弹框
  }

  validateInput = (rule, value, callback) => { // 输入校验
    const { urlDetailShowWay } = this.state
    if (rule.field.match(urlDetailShowWay)) {
      if (value && value.replace(/(^\s*)|(\s*$)/g, '') !== '' && value.length >= 1) {
        callback();
      } else {
        callback(new Error('至少输入一个字符'))
      }
    }
  }

  saveSpendDetails = () => { // 保存分类花销/支出
    const { dispatch, spendsdetails: { queryParam } } = this.props
    const { urlDetailShowWay, SpendDetailsData: { id, owner } } = this.state

    this.props.form.validateFields({ force: true }, (error, values) => {
      if (!error) {
        const req = {}
        const saveReq = {}
        Object.assign(req, values)
        for (let k of Object.keys(req)) {
          if (req[k] !== undefined || k.match(urlDetailShowWay)) { // 把输入的字段的detailShowWay去掉
            saveReq[k.replace(urlDetailShowWay, '')] = req[k]
          }
        }
        const { birthday } = saveReq
        saveReq.happenTime = birthday
        if (urlDetailShowWay === '8') {
          saveReq.detailType = dailyUseChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '2') {
          saveReq.detailType = educateChooseList.find(item => item.label === saveReq.detailType).value
          saveReq.period = educatePeriodChooseList.find(item => item.label === saveReq.period).value
        }
        if (urlDetailShowWay === '3') {
          saveReq.happyDegree = entertainmentChooseList.find(item => item.label === saveReq.happyDegree).value
        }
        if (urlDetailShowWay === '5') {
          saveReq.detailType = goHomeSpendsChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '12') {
          saveReq.detailType = goOutChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '6') {
          saveReq.detailType = houseTypeChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '9') {
          saveReq.detailType = relationShipChooseList.find(item => item.label === saveReq.detailType).value
        }
        dispatch({ type: 'spendsdetails/saveSpendDetails', payload: { detailShowWay: urlDetailShowWay, type: id, owner, ...saveReq, queryParam }, callback: (res) => {
          if (res && res.code === 1) {

            const detailShowWay = this.props.match.params.list
            dispatch({ type: 'spendsdetails/findPieAndBarDatas', payload: { owner, detailShowWay } }) // 查询具体支出中的图表数据

            Toast.success('操作成功', showToastTime)
            this.props.form.resetFields()
            this.setState({ isShowAddOrModifyTypePage: false })
            this.toggleBody(0) // 关闭弹框
            return false
          }
        } })
      } else {
        alert('请检查必输项')
      }
    });
  }

  modifySpendDetails = () => { // 修改分类信息
    const { dispatch, spendsdetails: { queryParam } } = this.props
    const { urlDetailShowWay, SpendDetailsData: { id, owner }, editData } = this.state

    this.props.form.validateFields({ force: true }, (error, values) => {
      if (!error) {
        const req = {}
        const saveReq = {}
        Object.assign(req, values)
        for (let k of Object.keys(req)) {
          if (req[k] !== undefined || k.match(urlDetailShowWay)) { // 把输入的字段的detailShowWay去掉
            saveReq[k.replace(urlDetailShowWay, '')] = req[k]
          }
        }
        const { birthday } = saveReq
        saveReq.happenTime = birthday
        if (urlDetailShowWay === '8') {
          saveReq.detailType = dailyUseChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '2') {
          saveReq.detailType = educateChooseList.find(item => item.label === saveReq.detailType).value
          saveReq.period = educatePeriodChooseList.find(item => item.label === saveReq.period).value
        }
        if (urlDetailShowWay === '3') {
          saveReq.happyDegree = entertainmentChooseList.find(item => item.label === saveReq.happyDegree).value
        }
        if (urlDetailShowWay === '5') {
          saveReq.detailType = goHomeSpendsChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '12') {
          saveReq.detailType = goOutChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '6') {
          saveReq.detailType = houseTypeChooseList.find(item => item.label === saveReq.detailType).value
        }
        if (urlDetailShowWay === '9') {
          saveReq.detailType = relationShipChooseList.find(item => item.label === saveReq.detailType).value
        }
        saveReq.happenTime = birthday
        dispatch({ type: 'spendsdetails/modifySpendDetails', payload: { id: editData.id, detailShowWay: urlDetailShowWay, type: id, owner, ...saveReq, queryParam }, callback: (res) => {
          if (res && res.code === 1) {

            const detailShowWay = this.props.match.params.list
            dispatch({ type: 'spendsdetails/findPieAndBarDatas', payload: { owner, detailShowWay } }) // 查询具体支出中的图表数据

            Toast.success('操作成功', showToastTime)
            this.props.form.resetFields()
            this.setState({ isShowAddOrModifyTypePage: false })
            this.toggleBody(0) // 关闭弹框
            return false
          }
        } })
      } else {
        alert('请检查必输项')
      }
    });
  }

  changeDailyUsedSelect = (value) => { // 日用品类型选择的值
    this.setState({ selectedDailyUsedTypeVal: value[0] })
  }

  changeEducateSelect = (value) => { // 教育支出缴费类型选择的值
    this.setState({ selectedEducateTypeVal: value[0] })
  }

  changeEducatePeriodSelect = (value) => { // 教育支出教育时期选择的值
    this.setState({ selectedEducatePeriodVal: value[0] })
  }

  entertainmentSelect = (value) => { // 娱乐支出开心程度选择的值
    this.setState({ selectedentertainmentVal: value[0] })
  }

  changeGoHomeSpendsSelect = (value) => { // 回家消费  原来是多选
    this.setState({ selectedGoHomeSpendsVal: value[0] })
  }

  changeGoOutSelect = (value) => { // 出行方式  原来是多选
    this.setState({ selectedGoOutTypeVal: value[0] })
  }

  changeHouseTypeSelect = (value) => { // 住房类型
    this.setState({ selectedHouseTypeVal: value[0] })
  }

  changeRelationShipSelect = (value) => { // 人际关系 出或入
    this.setState({ selectedRelationShipVal: value[0] })
  }

  forbiddenInput = (e) => {
    document.activeElement.blur()
  }

  scrollToView = (value) => {
    document.getElementById(value).scrollIntoView()
  }

  render() {
    const { spendsdetails: { datas, dataGroupByYear }, dispatch, form: { getFieldProps, getFieldError } } = this.props
    const { isShowAddOrModifyTypePage, selectedDailyUsedTypeVal, editData, SpendDetailsData, urlDetailShowWay, openTimeSelect, date, DetailsData, openDailyUsed, showChars } = this.state
    const { selectedEducateTypeVal, openEducate, selectedEducatePeriodVal, openEducatePeriod, selectedentertainmentVal, openEntertainmentSelect, selectedGoHomeSpendsVal, openGoHomeSpends, openGoOut, selectedGoOutTypeVal, openHouseType, selectedHouseTypeVal, selectedRelationShipVal, openRelationShip } = this.state
    const backColors = ['#FF6384', '#36A2EB', '#FFCE56', '#2CC555', '#2CC5BF', '#C52C39', '#FF5B05', '#8A9CAD', '#C7832C', '#585BEA', '#B379BD', '#B95636']
    const dataPie = {
      labels: dataGroupByYear ? dataGroupByYear.map(item => {
        const labelsData = []
        labelsData.push(`${item.years}年`)
        return labelsData
      }) : [],
      datasets: [{
        data: dataGroupByYear ? dataGroupByYear.map(item => {
          const labelsData = []
          labelsData.push(`${item.prices}`)
          return labelsData
        }) : [],
        backgroundColor: dataGroupByYear ? dataGroupByYear.map((item, index) => {
          const labelsData = []
          labelsData.push(backColors[index])
          return labelsData
        }) : [],
        hoverBackgroundColor: dataGroupByYear ? dataGroupByYear.map((item, index) => {
          const labelsData = []
          labelsData.push(backColors[index])
          return labelsData
        }) : [],
      }]
    }
    const dataBar = {
      labels: dataGroupByYear ? dataGroupByYear.map(item => {
        const labelsData = []
        labelsData.push(`${item.years}年`)
        return labelsData
      }) : [],
      datasets: [
        {
          label: '我的支出',
          backgroundColor: 'rgba(255,99,132,0.2)',
          borderColor: 'rgba(255,99,132,1)',
          borderWidth: 1,
          hoverBackgroundColor: 'rgba(255,99,132,0.4)',
          hoverBorderColor: 'rgba(255,99,132,1)',
          data: dataGroupByYear ? dataGroupByYear.map(item => {
            const labelsData = []
            labelsData.push(`${item.prices/1}`)
            return labelsData
          }) : [],
        }
      ]
    }

    return (
      <div className={styles.content_me} style={{ overflow: showChars || isShowAddOrModifyTypePage ? 'hidden' : '' }} >
        <div><Search dispatch={dispatch} searchSpendDetail={this.searchSpendDetail} /></div>
        <div className={`${styles.service_info} box_shadow`}>
          <div className={`${styles.service_title} border_bottommin`}>
            {SpendDetailsData ? <a onClick={() => this.onShow('showChars')}>{SpendDetailsData.name}</a> : ''}
            <div style={{ float: 'right', marginTop: -8 }}><Button onClick={() => this.onShow('isShowAddOrModifyTypePage')} type="ghost" inline size="small" style={{ marginRight: -0 }}>添加</Button></div>
          </div>
          {datas && datas[0] ? datas.map((item, key) =>
          <div key={key} className={styles.amCard} style={{ padding: '0px 8px' }}>
          <WhiteSpace size="lg" />
          <Card>
            <Card.Header
              // title={item.name}
              onClick={() => this.showDetailpage(item)}
              thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
              extra={<span>{item.name}</span>}
            />
            <Card.Footer content={<a onClick={() => this.showEditSpendTypePage(item)}>编辑</a>} extra={<div><a onClick={() => this.delSpendDetails(item)}>删除</a></div>} />
          </Card>
          </div>
          )
          :
          <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
          }
          {/* {datas && datas[0] ? datas.map((item, key) =>
          <div key={key} style={{ marginRight: 36 }}>
          <div className={styles.service_content}>
            <div className={styles.service_item} onClick={() => this.showDetailpage(item)}>
              <img
                className={styles.service_img}
                src={require('../../../../assets/recycleH5_17.png')}
                alt=""
              />
              <div className={styles.service_text}>{`${item.name}(¥${item.price}， ${item.happenTime})`}</div>
            </div>
            <div style={{ float: 'right' }}><Button onClick={() => this.showEditSpendTypePage(item)} type="ghost" inline size="small" style={{ marginRight: '4px' }}>编辑</Button></div>
            <div style={{ float: 'right' }}><Button onClick={() => this.delSpendDetails(item)} type="warning" inline size="small" style={{ marginRight: '4px' }}>删除</Button></div>
          </div>
          <WhiteSpace size="lg" />
          </div>
          )
          :
          <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
          } */}
        </div>

        <Modal
          visible={isShowAddOrModifyTypePage}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={() => this.onCloseMadeal('isShowAddOrModifyTypePage')}
          footer={DetailsData ? [{ text: '确定', onPress: () => this.onCloseMadeal('isShowAddOrModifyTypePage') }] : [{ text: '关闭', onPress: () => this.onCloseMadeal('isShowAddOrModifyTypePage') }, { text: '保存', onPress: editData ? () => this.modifySpendDetails() : () => this.saveSpendDetails() }]}
        >
          <div style={{ maxHeight: 200, minHeight: 150 }}>
            <DatePickers
              value={date}
              isOpen={openTimeSelect}
              onSelect={date => this.setState({ date, openTimeSelect: false })}
              onCancel={() => this.onChangeModal('openTimeSelect', false)}
            />
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>{editData ? `修改${SpendDetailsData.name}支出` : (DetailsData ? '详情' : `添加${SpendDetailsData.name}支出`)}</div>}>
              {/* 11 借款 */}
              <div hidden={urlDetailShowWay === '11' ? false : true}>
                <InputItem
                  {...getFieldProps('name11', {
                    rules: [
                      { required: urlDetailShowWay === '11' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '11' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name11')}
                  onErrorClick={() => {
                    alert(getFieldError('name11').join('、'));
                  }}
                  id="name11"
                  onFocus={() => this.scrollToView('name11')}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>借钱原因</div></InputItem>
                <InputItem
                  {...getFieldProps('price11', {
                    rules: [
                      { required: urlDetailShowWay === '11' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '11' ? this.validateInput: null },
                    ], initialValue: editData ? editData.price : DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  error={!!getFieldError('price11')}
                  onErrorClick={() => {
                    alert(getFieldError('price11').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  // id="money"
                  // onFocus={() => this.scrollToView('money')}
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>借了多少</div></InputItem>
                <InputItem
                  {...getFieldProps('who11', {
                    rules: [
                      { required: urlDetailShowWay === '11' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '11' ? this.validateInput: null },
                    ], initialValue: editData ? editData.who : DetailsData ? DetailsData.who : null,
                    })
                  }
                  error={!!getFieldError('who11')}
                  onErrorClick={() => {
                    alert(getFieldError('who11').join('、'));
                  }}
                  placeholder={'请输入'}
                  id="who11"
                  onFocus={() => this.scrollToView('who11')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>向谁借</div></InputItem>
                <InputItem
                  {...getFieldProps('birthday11', {
                    rules: [
                      { required: urlDetailShowWay === '11' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '11' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday11')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday11').join('、'));
                  }}
                  placeholder={'请输入'}
                  readOnly
                  id="birthday11"
                  // onFocus={() => this.scrollToView('birthday11')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday11') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/* 1 衣服鞋子 */}
              <div hidden={urlDetailShowWay === '1' ? false : true}>
                <InputItem
                  {...getFieldProps('name1', {
                    rules: [
                      { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name1')}
                  onErrorClick={() => {
                    alert(getFieldError('name1').join('、'));
                  }}
                  placeholder={'买的是什么'}
                  id="name1"
                  onFocus={() => this.scrollToView('name1')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>粗略介绍</div></InputItem>
                <InputItem
                  {...getFieldProps('price1', {
                    rules: [
                      { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price1"
                  // onFocus={() => this.scrollToView('price1')}
                  error={!!getFieldError('price1')}
                  onErrorClick={() => {
                    alert(getFieldError('price1').join('、'));
                  }}
                  placeholder={'一不小心花了多少'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>花了多少</div></InputItem>
                <InputItem
                  {...getFieldProps('color1', {
                    // rules: [
                    //   { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                    //   { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.color: DetailsData ? DetailsData.color : null,
                    })
                  }
                  // error={!!getFieldError('color1')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('color1').join('、'));
                  // }}
                  id="color1"
                  onFocus={() => this.scrollToView('color1')}
                  placeholder={DetailsData ? null : '它是什么颜色的'}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>颜色</div></InputItem>
                <InputItem
                  {...getFieldProps('discount1', {
                    // rules: [
                    //   { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                    //   { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.discount: DetailsData ? DetailsData.discount : null,
                    })
                  }
                  // error={!!getFieldError('discount1')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('discount1').join('、'));
                  // }}
                  placeholder={DetailsData ? null : '打几折的时候买的'}
                  id="discount1"
                  onFocus={() => this.scrollToView('discount1')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>打几折</div></InputItem>
                <InputItem
                  {...getFieldProps('buyAge1', {
                    // rules: [
                    //   { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                    //   { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.buyAge: DetailsData ? DetailsData.buyAge : null,
                    })
                  }
                  // error={!!getFieldError('buyAge1')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('buyAge1').join('、'));
                  // }}
                  placeholder={DetailsData ? null : '请输入'}
                  id="buyAge1"
                  onFocus={() => this.scrollToView('buyAge1')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>几岁买的</div></InputItem>
                <InputItem
                  {...getFieldProps('favoriteTime1', {
                    // rules: [
                    //   { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                    //   { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.favoriteTime: DetailsData ? DetailsData.favoriteTime : null,
                    })
                  }
                  // error={!!getFieldError('favoriteTime1')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('favoriteTime1').join('、'));
                  // }}
                  placeholder={DetailsData ? null : '请输入'}
                  id="favoriteTime1"
                  onFocus={() => this.scrollToView('favoriteTime1')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>喜欢了多久</div></InputItem>
                <InputItem
                  {...getFieldProps('season1', {
                    // rules: [
                    //   { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                    //   { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.season: DetailsData ? DetailsData.season : null,
                    })
                  }
                  // error={!!getFieldError('season1')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('season1').join('、'));
                  // }}
                  placeholder={DetailsData ? null : '适合什么季节穿'}
                  id="season1"
                  onFocus={() => this.scrollToView('season1')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>适合季节</div></InputItem>
                <InputItem
                  {...getFieldProps('size1', {
                    // rules: [
                    //   { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                    //   { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.size: DetailsData ? DetailsData.size : null,
                    })
                  }
                  // error={!!getFieldError('size1')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('size1').join('、'));
                  // }}
                  placeholder={DetailsData ? null : '请输入'}
                  id="size1"
                  onFocus={() => this.scrollToView('size1')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>尺码</div></InputItem>
                <InputItem
                  {...getFieldProps('favoriteDegree1', {
                    // rules: [
                    //   { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                    //   { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.favoriteDegree: DetailsData ? DetailsData.favoriteDegree : null,
                    })
                  }
                  // error={!!getFieldError('favoriteDegree1')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('favoriteDegree1').join('、'));
                  // }}
                  placeholder={DetailsData ? null : '买的时候有多喜欢'}
                  id="favoriteDegree1"
                  onFocus={() => this.scrollToView('favoriteDegree1')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>喜欢程度</div></InputItem>
                <InputItem
                  {...getFieldProps('birthday1', {
                    rules: [
                      { required: urlDetailShowWay === '1' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '1' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday1')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday1').join('、'));
                  }}
                  placeholder={'请输入'}
                  id="birthday1"
                  // onFocus={() => this.scrollToView('birthday1')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday1')}}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/* 日用品花销  8 */}
              <div hidden={urlDetailShowWay === '8' ? false : true}>
                <InputItem
                  {...getFieldProps('name8', {
                    rules: [
                      { required: urlDetailShowWay === '8' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '8' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name8')}
                  onErrorClick={() => {
                    alert(getFieldError('name8').join('、'));
                  }}
                  id="name8"
                  onFocus={() => this.scrollToView('name8')}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>买的什么</div></InputItem>
                <InputItem
                  {...getFieldProps('price8', {
                    rules: [
                      { required: urlDetailShowWay === '8' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '8' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price8"
                  // onFocus={() => this.scrollToView('price8')}
                  error={!!getFieldError('price8')}
                  onErrorClick={() => {
                    alert(getFieldError('price8').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>花了多少</div></InputItem>
                <InputItem
                  {...getFieldProps('detailType8', {
                    rules: [
                      { required: urlDetailShowWay === '8' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '8' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '8' ? (selectedDailyUsedTypeVal && dailyUseChooseList.find(item => item.value === selectedDailyUsedTypeVal) ? dailyUseChooseList.find(item => item.value === selectedDailyUsedTypeVal).label : (editData ? dailyUseChooseList.find(item => item.value === editData.detailTypeInt).label : (DetailsData ? dailyUseChooseList.find(item => item.value === DetailsData.detailTypeInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('detailType8')}
                  onErrorClick={() => {
                    alert(getFieldError('detailType8').join('、'));
                  }}
                  placeholder={'请选择'}
                  readOnly
                  id="detailType8"
                  // onFocus={() => this.scrollToView('detailType8')}
                  onFocus={() => { this.onChangeSelectModal('openDailyUsed', true); this.scrollToView('detailType8') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>日用品类型</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择日用品类型</div>}
                  popup
                  visible={openDailyUsed}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openDailyUsed', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openDailyUsed', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.changeDailyUsedSelect}
                      data={dailyUseChooseList}
                      value={selectedDailyUsedTypeVal}
                      cascade={false}
                    />
                </div>
                </Modal>
                <InputItem
                  {...getFieldProps('birthday8', {
                    rules: [
                      { required: urlDetailShowWay === '8' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '8' ? this.validateInput : null },
                    ],
                    initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday8')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday8').join('、'));
                  }}
                  placeholder={'请输选择'}
                  id="birthday8"
                  // onFocus={() => this.scrollToView('birthday8')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday8') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/* 教育支出  2 */}
              <div hidden={urlDetailShowWay === '2' ? false : true}>
                <InputItem
                  {...getFieldProps('name2', {
                    rules: [
                      { required: urlDetailShowWay === '2' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '2' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name2')}
                  onErrorClick={() => {
                    alert(getFieldError('name2').join('、'));
                  }}
                  placeholder={'请输入'}
                  id="name2"
                  onFocus={() => this.scrollToView('name2')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>缴费原因</div></InputItem>
                <InputItem
                  {...getFieldProps('price2', {
                    rules: [
                      { required: urlDetailShowWay === '2' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '2' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price2"
                  // onFocus={() => this.scrollToView('price2')}
                  error={!!getFieldError('price2')}
                  onErrorClick={() => {
                    alert(getFieldError('price2').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>缴费金额</div></InputItem>
                <InputItem
                  {...getFieldProps('detailType2', {
                    rules: [
                      { required: urlDetailShowWay === '2' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '2' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '2' ? (selectedEducateTypeVal && educateChooseList.find(item => item.value === selectedEducateTypeVal) ? educateChooseList.find(item => item.value === selectedEducateTypeVal).label : (editData ? educateChooseList.find(item => item.value === editData.detailTypeInt).label : (DetailsData ? educateChooseList.find(item => item.value === DetailsData.detailTypeInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('detailType2')}
                  onErrorClick={() => {
                    alert(getFieldError('detailType2').join('、'));
                  }}
                  placeholder={'请选择'}
                  readOnly
                  id="detailType2"
                  // onFocus={() => this.scrollToView('detailType2')}
                  onFocus={() => { this.onChangeSelectModal('openEducate', true); this.scrollToView('detailType2')}}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>缴费类型</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择缴费类型</div>}
                  popup
                  visible={openEducate}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openEducate', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openEducate', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.changeEducateSelect}
                      data={educateChooseList}
                      value={selectedEducateTypeVal}
                      cascade={false}
                    />
                </div>
                </Modal>
                <InputItem
                  {...getFieldProps('period2', {
                    rules: [
                      { required: urlDetailShowWay === '2' ? true : false, message: '必选入项' },
                      { validator: urlDetailShowWay === '2' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '2' ? (selectedEducatePeriodVal && educatePeriodChooseList.find(item => item.value === selectedEducatePeriodVal) ? educatePeriodChooseList.find(item => item.value === selectedEducatePeriodVal).label : (editData ? educatePeriodChooseList.find(item => item.value === editData.periodInt).label : (DetailsData ? educatePeriodChooseList.find(item => item.value === DetailsData.periodInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('period2')}
                  onErrorClick={() => {
                    alert(getFieldError('period2').join('、'));
                  }}
                  placeholder={'请选择'}
                  id="period2"
                  // onFocus={() => this.scrollToView('period2')}
                  onFocus={() => { this.onChangeSelectModal('openEducatePeriod', true); this.scrollToView('period2') }}
                  disabled={DetailsData ? true : false}
                  readOnly
                ><div style={{ color: 'rgb(136, 136, 136)' }}>教育时期</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择教育时期</div>}
                  popup
                  visible={openEducatePeriod}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openEducatePeriod', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openEducatePeriod', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.changeEducatePeriodSelect}
                      data={educatePeriodChooseList}
                      value={selectedEducatePeriodVal}
                      cascade={false}
                    />
                  </div>
                </Modal>
                <InputItem
                  {...getFieldProps('birthday2', {
                    rules: [
                      { required: urlDetailShowWay === '2' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '2' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday2')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday2').join('、'));
                  }}
                  placeholder={'请输选择'}
                  readOnly
                  id="birthday2"
                  // onFocus={() => this.scrollToView('birthday2')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday2') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/* 娱乐消费  3 */}
              <div hidden={urlDetailShowWay === '3' ? false : true}>
                <InputItem
                  {...getFieldProps('name3', {
                    rules: [
                      { required: urlDetailShowWay === '3' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '3' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name3')}
                  onErrorClick={() => {
                    alert(getFieldError('name3').join('、'));
                  }}
                  id="name3"
                  onFocus={() => this.scrollToView('name3')}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>玩了什么</div></InputItem>
                <InputItem
                  {...getFieldProps('price3', {
                    rules: [
                      { required: urlDetailShowWay === '3' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '3' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price3"
                  // onFocus={() => this.scrollToView('price3')}
                  error={!!getFieldError('price3')}
                  onErrorClick={() => {
                    alert(getFieldError('price3').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>一天花了</div></InputItem>
                <InputItem
                  {...getFieldProps('who3', {
                    rules: [
                      { required: urlDetailShowWay === '3' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '3' ? this.validateInput : null },
                    ], initialValue: editData ? editData.who: DetailsData ? DetailsData.who : null,
                    })
                  }
                  id="who3"
                  onFocus={() => this.scrollToView('who3')}
                  error={!!getFieldError('who3')}
                  onErrorClick={() => {
                    alert(getFieldError('who3').join('、'));
                  }}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>跟谁一起</div></InputItem>
                <InputItem
                  {...getFieldProps('address3', {
                    rules: [
                      { required: urlDetailShowWay === '3' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '3' ? this.validateInput : null },
                    ], initialValue: editData ? editData.address: DetailsData ? DetailsData.address : null,
                    })
                  }
                  error={!!getFieldError('address3')}
                  onErrorClick={() => {
                    alert(getFieldError('address3').join('、'));
                  }}
                  id="address3"
                  onFocus={() => this.scrollToView('address3')}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>在哪里</div></InputItem>
                <InputItem
                  {...getFieldProps('happyDegree3', {
                    rules: [
                      { required: urlDetailShowWay === '3' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '3' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '3' ? (selectedentertainmentVal && entertainmentChooseList.find(item => item.value === selectedentertainmentVal) ? entertainmentChooseList.find(item => item.value === selectedentertainmentVal).label : (editData ? entertainmentChooseList.find(item => item.value === editData.happyDegreeInt).label : (DetailsData ? entertainmentChooseList.find(item => item.value === DetailsData.happyDegreeInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('happyDegree3')}
                  onErrorClick={() => {
                    alert(getFieldError('happyDegree3').join('、'));
                  }}
                  placeholder={'请选择'}
                  id="happyDegree3"
                  // onFocus={() => this.scrollToView('happyDegree3')}
                  onFocus={() => { this.onChangeSelectModal('openEntertainmentSelect', true); this.scrollToView('happyDegree3') }}
                  disabled={DetailsData ? true : false}
                  readOnly
                ><div style={{ color: 'rgb(136, 136, 136)' }}>开心程度</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择开心程度</div>}
                  popup
                  visible={openEntertainmentSelect}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openEntertainmentSelect', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openEntertainmentSelect', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.entertainmentSelect}
                      data={entertainmentChooseList}
                      value={selectedentertainmentVal}
                      cascade={false}
                    />
                  </div>
                </Modal>
                <InputItem
                  {...getFieldProps('birthday3', {
                    rules: [
                      { required: urlDetailShowWay === '3' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '3' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday3')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday3').join('、'));
                  }}
                  placeholder={'请输选择'}
                  readOnly
                  id="birthday3"
                  // onFocus={() => this.scrollToView('birthday3')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday3') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/* 回家花销 5 */}
              <div hidden={urlDetailShowWay === '5' ? false : true}>
                <InputItem
                  {...getFieldProps('name5', {
                    rules: [
                      { required: urlDetailShowWay === '5' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '5' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name5')}
                  onErrorClick={() => {
                    alert(getFieldError('name5').join('、'));
                  }}
                  placeholder={'请输入'}
                  id="name5"
                  onFocus={() => this.scrollToView('name5')}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>回家原因</div></InputItem>
                <InputItem
                  {...getFieldProps('price5', {
                    rules: [
                      { required: urlDetailShowWay === '5' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '5' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price : DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price5"
                  // onFocus={() => this.scrollToView('price5')}
                  error={!!getFieldError('price5')}
                  onErrorClick={() => {
                    alert(getFieldError('price5').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>一天花了</div></InputItem>
                <InputItem
                  {...getFieldProps('detailType5', {
                    rules: [
                      { required: urlDetailShowWay === '5' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '5' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '5' ? (selectedGoHomeSpendsVal && goHomeSpendsChooseList.find(item => item.value === selectedGoHomeSpendsVal) ? goHomeSpendsChooseList.find(item => item.value === selectedGoHomeSpendsVal).label : (editData ? goHomeSpendsChooseList.find(item => item.value === editData.detailTypeInt).label : (DetailsData ? goHomeSpendsChooseList.find(item => item.value === DetailsData.detailTypeInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('detailType5')}
                  onErrorClick={() => {
                    alert(getFieldError('detailType5').join('、'));
                  }}
                  placeholder={'请选择'}
                  id="detailType5"
                  // onFocus={() => this.scrollToView('detailType5')}
                  onFocus={() => { this.onChangeSelectModal('openGoHomeSpends', true); this.scrollToView('detailType5') }}
                  disabled={DetailsData ? true : false}
                  readOnly
                ><div style={{ color: 'rgb(136, 136, 136)' }}>主要花在了</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择主要花在了哪里</div>}
                  popup
                  visible={openGoHomeSpends}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openGoHomeSpends', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openGoHomeSpends', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.changeGoHomeSpendsSelect}
                      data={goHomeSpendsChooseList}
                      value={selectedGoHomeSpendsVal}
                      cascade={false}
                    />
                </div>
                </Modal>
                <InputItem
                  {...getFieldProps('birthday5', {
                    rules: [
                      { required: urlDetailShowWay === '5' ? true : false, message: '必选入项' },
                      { validator: urlDetailShowWay === '5' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday5')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday5').join('、'));
                  }}
                  placeholder={'请输选择'}
                  readOnly
                  id="birthday5"
                  // onFocus={() => this.scrollToView('birthday5')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday5') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/* 外出乘坐交通工具花销  12 */}
              <div hidden={urlDetailShowWay === '12' ? false : true}>
                <InputItem
                  {...getFieldProps('name12', {
                    rules: [
                      { required: urlDetailShowWay === '12' ?true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '12' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name12')}
                  onErrorClick={() => {
                    alert(getFieldError('name12').join('、'));
                  }}
                  id="name12"
                  onFocus={() => this.scrollToView('name12')}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>外出原因</div></InputItem>
                <InputItem
                  {...getFieldProps('price12', {
                    rules: [
                      { required: urlDetailShowWay === '12' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '12' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price12"
                  // onFocus={() => this.scrollToView('price12')}
                  error={!!getFieldError('price12')}
                  onErrorClick={() => {
                    alert(getFieldError('price12').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>一天花了</div></InputItem>
                <InputItem
                  {...getFieldProps('oil12', {
                    // rules: [
                    //   { required: urlDetailShowWay === '12' ? true : false, message: '必输项' },
                    //   { validator: urlDetailShowWay === '12' ? this.validateInput : null },
                    // ],
                    initialValue: editData ? editData.oil: DetailsData ? DetailsData.oil : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="oil12"
                  // onFocus={() => this.scrollToView('oil12')}
                  // error={!!getFieldError('oil12')}
                  // onErrorClick={() => {
                  //   alert(getFieldError('oil12').join('、'));
                  // }}
                  placeholder={DetailsData ? null : '请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>开车加油</div></InputItem>
                <InputItem
                  {...getFieldProps('detailType12', {
                    rules: [
                      { required: urlDetailShowWay === '12' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '12' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '12' ? (selectedGoOutTypeVal && goOutChooseList.find(item => item.value === selectedGoOutTypeVal) ? goOutChooseList.find(item => item.value === selectedGoOutTypeVal).label : (editData ? goOutChooseList.find(item => item.value === editData.detailTypeInt).label : (DetailsData ? goOutChooseList.find(item => item.value === DetailsData.detailTypeInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('detailType12')}
                  onErrorClick={() => {
                    alert(getFieldError('detailType12').join('、'));
                  }}
                  placeholder={'请选择'}
                  id="detailType12"
                  // onFocus={() => this.scrollToView('detailType12')}
                  onFocus={() => { this.onChangeSelectModal('openGoOut', true); this.scrollToView('detailType12') }}
                  disabled={DetailsData ? true : false}
                  readOnly
                ><div style={{ color: 'rgb(136, 136, 136)' }}>交通工具</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择交通工具</div>}
                  popup
                  visible={openGoOut}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openGoOut', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openGoOut', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.changeGoOutSelect}
                      data={goOutChooseList}
                      value={selectedGoOutTypeVal}
                      cascade={false}
                    />
                </div>
                </Modal>
                <InputItem
                  {...getFieldProps('birthday12', {
                    rules: [
                      { required: urlDetailShowWay === '12' ? true : false, message: '必选入项' },
                      { validator: urlDetailShowWay === '12' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday12')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday12').join('、'));
                  }}
                  placeholder={'请输选择'}
                  readOnly
                  id="birthday12"
                  // onFocus={() => this.scrollToView('birthday12')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday12') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>出行时间</div></InputItem>
              </div>

              {/* 住房花费  6 */}
              <div hidden={urlDetailShowWay === '6' ? false : true}>
                <InputItem
                  {...getFieldProps('name6', {
                    rules: [
                      { required: urlDetailShowWay === '6' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '6' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name6')}
                  onErrorClick={() => {
                    alert(getFieldError('name6').join('、'));
                  }}
                  id="name6"
                  onFocus={() => this.scrollToView('name6')}
                  placeholder={'在哪里'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>房租</div></InputItem>
                <InputItem
                  {...getFieldProps('price6', {
                    rules: [
                      { required: urlDetailShowWay === '6' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '6' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price6"
                  // onFocus={() => this.scrollToView('price6')}
                  error={!!getFieldError('price6')}
                  onErrorClick={() => {
                    alert(getFieldError('price6').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>费用</div></InputItem>
                <InputItem
                  {...getFieldProps('waterSpend6', {
                    rules: [
                      { required: urlDetailShowWay === '6' ? true : false, message: '必输项' },
                      { validator: urlDetailShowWay === '6' ? this.validateInput : null },
                    ], initialValue: editData ? editData.waterSpend : DetailsData ? DetailsData.waterSpend : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="waterSpend6"
                  // onFocus={() => this.scrollToView('waterSpend6')}
                  error={!!getFieldError('waterSpend6')}
                  onErrorClick={() => {
                    alert(getFieldError('waterSpend6').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>水费</div></InputItem>
                <InputItem
                  {...getFieldProps('electricSpend6', {
                    rules: [
                      { required: urlDetailShowWay === '6' ? true : false, message: '必输项' },
                      { validator: urlDetailShowWay === '6' ? this.validateInput : null },
                    ], initialValue: editData ? editData.electricSpend : DetailsData ? DetailsData.electricSpend : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="electricSpend6"
                  // onFocus={() => this.scrollToView('electricSpend6')}
                  error={!!getFieldError('electricSpend6')}
                  onErrorClick={() => {
                    alert(getFieldError('electricSpend6').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>电费</div></InputItem>
                <InputItem
                  {...getFieldProps('detailType6', {
                    rules: [
                      { required: urlDetailShowWay === '6' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '6' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '6' ? (selectedHouseTypeVal && houseTypeChooseList.find(item => item.value === selectedHouseTypeVal) ? houseTypeChooseList.find(item => item.value === selectedHouseTypeVal).label : (editData ? houseTypeChooseList.find(item => item.value === editData.detailTypeInt).label : (DetailsData ? houseTypeChooseList.find(item => item.value === DetailsData.detailTypeInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('detailType6')}
                  onErrorClick={() => {
                    alert(getFieldError('detailType6').join('、'));
                  }}
                  placeholder={'请选择'}
                  id="detailType6"
                  // onFocus={() => this.scrollToView('detailType6')}
                  onFocus={() => { this.onChangeSelectModal('openHouseType', true); this.scrollToView('detailType6') }}
                  disabled={DetailsData ? true : false}
                  readOnly
                ><div style={{ color: 'rgb(136, 136, 136)' }}>房子类型</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择房子类型</div>}
                  popup
                  visible={openHouseType}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openHouseType', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openHouseType', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.changeHouseTypeSelect}
                      data={houseTypeChooseList}
                      value={selectedHouseTypeVal}
                      cascade={false}
                    />
                </div>
                </Modal>
                <InputItem
                  {...getFieldProps('birthday6', {
                    rules: [
                      { required: urlDetailShowWay === '6' ? true : false, message: '必选入项' },
                      { validator: urlDetailShowWay === '6' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday6')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday6').join('、'));
                  }}
                  placeholder={'请输选择'}
                  readOnly
                  id="birthday6"
                  // onFocus={() => this.scrollToView('birthday6')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday6') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/*  吃花销（水果、吃饭，柴米油盐）  7 */}
              <div hidden={urlDetailShowWay === '7' ? false : true}>
                <InputItem
                  {...getFieldProps('name7', {
                    rules: [
                      { required: urlDetailShowWay === '7' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '7' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name : DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name7')}
                  onErrorClick={() => {
                    alert(getFieldError('name7').join('、'));
                  }}
                  id="name7"
                  onFocus={() => this.scrollToView('name7')}
                  placeholder={'如：买了、吃了什么'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>简单描述</div></InputItem>
                <InputItem
                  {...getFieldProps('price7', {
                    rules: [
                      { required: urlDetailShowWay === '7' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '7' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price : DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price7"
                  // onFocus={() => this.scrollToView('price7')}
                  error={!!getFieldError('price7')}
                  onErrorClick={() => {
                    alert(getFieldError('price7').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>一天花了</div></InputItem>
                <InputItem
                  {...getFieldProps('simpleEat7', {
                    initialValue: editData ? editData.simpleEat : DetailsData ? DetailsData.simpleEat : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="simpleEat7"
                  // onFocus={() => this.scrollToView('simpleEat7')}
                  placeholder={DetailsData ? null : '简单吃一天花了多少'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>简单吃</div></InputItem>
                <InputItem
                  {...getFieldProps('bigEat7', {
                    initialValue: editData ? editData.bigEat : DetailsData ? DetailsData.bigEat : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  placeholder={DetailsData ? null : '大餐一天花了多少'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>大餐</div></InputItem>

                <InputItem
                  {...getFieldProps('rice7', {
                    initialValue: editData ? editData.rice : DetailsData ? DetailsData.rice : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="rice7"
                  // onFocus={() => this.scrollToView('rice7')}
                  placeholder={DetailsData ? null : '买米花了多少'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>买米</div></InputItem>
                <InputItem
                  {...getFieldProps('oil7', {
                    initialValue: editData ? editData.oil : DetailsData ? DetailsData.oil : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="oil7"
                  // onFocus={() => this.scrollToView('oil7')}
                  placeholder={DetailsData ? null : '买油花了多少'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>买油</div></InputItem>
                <InputItem
                  {...getFieldProps('snacks7', {
                    initialValue: editData ? editData.snacks : DetailsData ? DetailsData.snacks : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="snacks7"
                  // onFocus={() => this.scrollToView('snacks7')}
                  placeholder={DetailsData ? null : '请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>水果零食</div></InputItem>
                <InputItem
                  {...getFieldProps('birthday7', {
                    rules: [
                      { required: urlDetailShowWay === '7' ? true : false, message: '必选入项' },
                      { validator: urlDetailShowWay === '7' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday7')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday7').join('、'));
                  }}
                  placeholder={'请输选择'}
                  id="birthday7"
                  // onFocus={() => this.scrollToView('birthday7')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday7') }}
                  disabled={DetailsData ? true : false}
                  readOnly
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/*  其他花销  10 */}
              <div hidden={urlDetailShowWay === '10' ? false : true}>
                <InputItem
                  {...getFieldProps('name10', {
                    rules: [
                      { required: urlDetailShowWay === '10' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '10' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  id="name10"
                  onFocus={() => this.scrollToView('name10')}
                  error={!!getFieldError('name10')}
                  onErrorClick={() => {
                    alert(getFieldError('name10').join('、'));
                  }}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>用到哪了</div></InputItem>
                <InputItem
                  {...getFieldProps('price10', {
                    rules: [
                      { required: urlDetailShowWay === '10' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '10' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price10"
                  // onFocus={() => this.scrollToView('price10')}
                  error={!!getFieldError('price10')}
                  onErrorClick={() => {
                    alert(getFieldError('price10').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>花了多少</div></InputItem>
                <InputItem
                  {...getFieldProps('birthday10', {
                    rules: [
                      { required: urlDetailShowWay === '10' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '10' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday10')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday10').join('、'));
                  }}
                  placeholder={'请输选择'}
                  id="birthday10"
                  // onFocus={() => this.scrollToView('birthday10')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday10') }}
                  disabled={DetailsData ? true : false}
                  readOnly
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>

              {/*  人际关系  9 */}
              <div hidden={urlDetailShowWay === '9' ? false : true}>
                <InputItem
                  {...getFieldProps('name9', {
                    rules: [
                      { required: urlDetailShowWay === '9' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '9' ? this.validateInput : null },
                    ], initialValue: editData ? editData.name: DetailsData ? DetailsData.name : null,
                    })
                  }
                  error={!!getFieldError('name9')}
                  onErrorClick={() => {
                    alert(getFieldError('name9').join('、'));
                  }}
                  id="name9"
                  onFocus={() => this.scrollToView('name9')}
                  placeholder={'因为什么事情出钱、或收入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>什么事情</div></InputItem>
                <InputItem
                  {...getFieldProps('price9', {
                    rules: [
                      { required: urlDetailShowWay === '9' ? true : false, message: '必输入项' },
                      { validator: urlDetailShowWay === '9' ? this.validateInput : null },
                    ], initialValue: editData ? editData.price: DetailsData ? DetailsData.price : null,
                    normalize: (v, prev) => {
                      if (v && !/^(([1-9]\d*)|0)(\.\d{0,2}?)?$/.test(v)) {
                        if (v === '.') {
                          return '0.';
                        }
                        return prev;
                      }
                      return v;
                    },
                    })
                  }
                  // id="price9"
                  // onFocus={() => this.scrollToView('price9')}
                  error={!!getFieldError('price9')}
                  onErrorClick={() => {
                    alert(getFieldError('price9').join('、'));
                  }}
                  placeholder={'请输入'}
                  type='money'
                  ref={el => this.inputRef = el}
                  clear
                  moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                  moneyKeyboardAlign="left"
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>金额</div></InputItem>
                <InputItem
                  {...getFieldProps('who9', {
                    rules: [
                      { required: urlDetailShowWay === '9' ? true : false, message: '必输项' },
                      { validator: urlDetailShowWay === '9' ? this.validateInput : null },
                    ], initialValue: editData ? editData.who : DetailsData ? DetailsData.who : null,
                    })
                  }
                  id="who9"
                  onFocus={() => this.scrollToView('who9')}
                  error={!!getFieldError('who9')}
                  onErrorClick={() => {
                    alert(getFieldError('who9').join('、'));
                  }}
                  placeholder={'请输入'}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>谁请客</div></InputItem>
               <InputItem
                  {...getFieldProps('detailType9', {
                    rules: [
                      { required: urlDetailShowWay === '9' ? true : false, message: '必选项' },
                      { validator: urlDetailShowWay === '9' ? this.validateInput : null },
                    ],
                    initialValue: urlDetailShowWay === '9' ? (selectedRelationShipVal && relationShipChooseList.find(item => item.value === selectedRelationShipVal) ? relationShipChooseList.find(item => item.value === selectedRelationShipVal).label : (editData ? relationShipChooseList.find(item => item.value === editData.detailTypeInt).label : (DetailsData ? relationShipChooseList.find(item => item.value === DetailsData.detailTypeInt).label : undefined))) : undefined,
                    })
                  }
                  error={!!getFieldError('detailType9')}
                  onErrorClick={() => {
                    alert(getFieldError('detailType9').join('、'));
                  }}
                  placeholder={'请选择'}
                  readOnly
                  id="detailType9"
                  // onFocus={() => this.scrollToView('detailType9')}
                  onFocus={() => { this.onChangeSelectModal('openRelationShip', true); this.scrollToView('detailType9') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>收或出</div></InputItem>
                <Modal
                  title={<div style={{ color: '#108ee9'}}>请选择收或出</div>}
                  popup
                  visible={openRelationShip}
                  transparent
                  maskClosable={false}
                  animationType="slide-up"
                  footer={[
                    { text: '取消', onPress: () => this.onChangeSelectModal('openRelationShip', false) },
                    { text: '确 定', onPress: () => this.onChangeSelectModal('openRelationShip', false) }
                  ]}
                >
                  <div className="modal-coupon-center" style={{ height: 120 }}>
                    <PickerView
                      onScrollChange={this.changeRelationShipSelect}
                      data={relationShipChooseList}
                      value={selectedRelationShipVal}
                      cascade={false}
                    />
                </div>
                </Modal>
                <InputItem
                  {...getFieldProps('birthday9', {
                    rules: [
                      { required: urlDetailShowWay === '9' ? true : false, message: '必选入项' },
                      { validator: urlDetailShowWay === '9' ? this.validateInput : null },
                    ], initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
                    })
                  }
                  error={!!getFieldError('birthday9')}
                  onErrorClick={() => {
                    alert(getFieldError('birthday9').join('、'));
                  }}
                  placeholder={'请输选择'}
                  readOnly
                  id="birthday9"
                  // onFocus={() => this.scrollToView('birthday9')}
                  onFocus={() => { this.onChangeModal('openTimeSelect', true); this.scrollToView('birthday9') }}
                  disabled={DetailsData ? true : false}
                ><div style={{ color: 'rgb(136, 136, 136)' }}>时间</div></InputItem>
              </div>
        </List>
         </div>
        </Modal>

        <Modal
          visible={showChars}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={() => this.onCloseMadeal('showChars')}
          footer={[{ text: '关闭', onPress: () => this.onCloseMadeal('showChars') }]}
        >
          <div>
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>{SpendDetailsData ? SpendDetailsData.name : ''}</div>}>
            <div style={{ height: 400 }}>
              <StickyContainer>
                <Tabs tabs={mainTabs}
                  initalPage={'t2'}
                  renderTabBar={renderTabBar}
                >
                  <div>
                    <h4 style={{ paddingTop: 16 }}>每年消费金额（单位：元）</h4>
                    <Pie data={dataPie} />
                    <div style={{ paddingBottom: 16 }}></div>
                  </div>
                  <div>
                    <div style={{ height: 300 }}>
                    <h4 style={{ paddingTop: 8 }}>每年消费金额（单位：元）</h4>
                    <Bar
                      data={dataBar}
                      options={{
                        maintainAspectRatio: false,
                        scales: {
                          yAxes: [{
                            gridLines: 0,
                            ticks: { beginAtZero: true }
                          }]
                        }
                      }}
                    />
                    </div>
                  </div>
                </Tabs>
              </StickyContainer>
              </div>
            </List>
          </div>
        </Modal>

      </div>
    )
  }
}

export default SpendDetailListPage
