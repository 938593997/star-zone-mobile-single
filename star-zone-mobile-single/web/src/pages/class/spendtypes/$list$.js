import React, { Component } from 'react'
import { connect } from 'dva'
import { Pie, Bar, Bubble, Doughnut, HorizontalBar, Line, Polar, Radar, Scatter } from 'react-chartjs-2'
import color from 'rcolor'
import { Modal , Toast, Button, WhiteSpace, List, InputItem, PickerView, Tabs, Card } from 'antd-mobile'
import { StickyContainer, Sticky } from 'react-sticky'
import ReactPullLoad, { STATS } from 'react-pullload'
import router from 'umi/router'
import styles from './index.less'
import Search from './Search'
import { createForm } from 'rc-form/lib'
import "../../../../node_modules/react-pullload/dist/ReactPullLoad.css"

const { alert } = Modal
const showToastTime = 2
const mainTabs = [{ title: '饼状图' }, { title: '柱状图' }, { title: 'Bubble' }, { title: 'Doughnut' }, { title: 'HorizontalBar' }, { title: 'Line' }, { title: 'Polar' }, { title: 'Radar' }, { title: 'Scatter' }, { title: 'CrazyGraph' }, { title: 'DyDoughnut' }]
function renderTabBar(props) {
  return (<Sticky>
    {({ style }) => <div style={{ ...style, zIndex: 1 }}><Tabs.DefaultTabBar {...props} page={3} /></div>}
  </Sticky>)
}
const dataRadar = {
  labels: ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'],
  datasets: [
    {
      label: 'My First dataset',
      backgroundColor: 'rgba(179,181,198,0.2)',
      borderColor: 'rgba(179,181,198,1)',
      pointBackgroundColor: 'rgba(179,181,198,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(179,181,198,1)',
      data: [65, 59, 90, 81, 56, 55, 40]
    },
    {
      label: 'My Second dataset',
      backgroundColor: 'rgba(255,99,132,0.2)',
      borderColor: 'rgba(255,99,132,1)',
      pointBackgroundColor: 'rgba(255,99,132,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(255,99,132,1)',
      data: [28, 48, 40, 19, 96, 27, 100]
    }
  ]
}
const dataScatter = {
  labels: ['Scatter'],
  datasets: [
    {
      label: 'My First dataset',
      fill: false,
      backgroundColor: 'rgba(75,192,192,0.4)',
      pointBorderColor: 'rgba(75,192,192,1)',
      pointBackgroundColor: '#fff',
      pointBorderWidth: 1,
      pointHoverRadius: 5,
      pointHoverBackgroundColor: 'rgba(75,192,192,1)',
      pointHoverBorderColor: 'rgba(220,220,220,1)',
      pointHoverBorderWidth: 2,
      pointRadius: 1,
      pointHitRadius: 10,
      data: [
        { x: 65, y: 75 },
        { x: 59, y: 49 },
        { x: 80, y: 90 },
        { x: 81, y: 29 },
        { x: 56, y: 36 },
        { x: 55, y: 25 },
        { x: 40, y: 18 },
      ]
    }
  ]
}
const initialStateCrazyGraph = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
  datasets: [
    {
		label: 'My First dataset',
		backgroundColor: 'rgba(255,99,132,0.2)',
		borderColor: 'rgba(255,99,132,1)',
		borderWidth: 1,
		hoverBackgroundColor: 'rgba(255,99,132,0.4)',
		hoverBorderColor: 'rgba(255,99,132,1)',
		data: [65, 59, 80, 81, 56, 55, 40]
    }
  ]
}
class CrazyGraph extends React.Component {
  // displayName: 'Graph',
	componentWillMount(){
		this.setState(initialStateCrazyGraph)
	}
	componentDidMount(){

		var _this = this

		setInterval(function(){ // 在componentDidMount隔5秒加载一次数据，实现动态展示数据
			var oldDataSet = _this.state.datasets[0]
			var newData = []
			for(var x=0; x< _this.state.labels.length; x++){
				newData.push(Math.floor(Math.random() * 100))
			}
			var newDataSet = {
				...oldDataSet
			}
			newDataSet.data = newData;
			newDataSet.backgroundColor = color();
			newDataSet.borderColor = color();
			newDataSet.hoverBackgroundColor = color();
			newDataSet.hoverBorderColor = color();
			var newState = {
				...initialStateCrazyGraph,
				datasets: [newDataSet]
			};
			_this.setState(newState);
		}, 5000)
	}
	render() {
		return (
			<Bar data={this.state} />
		);
	}
}

function getRandomInt (min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
const getState = () => ({
labels: [
  'Red',
  'Green',
  'Yellow'
],
datasets: [{
  data: [getRandomInt(50, 200), getRandomInt(100, 150), getRandomInt(150, 250)],
  backgroundColor: [
  '#CCC',
  '#36A2EB',
  '#FFCE56'
  ],
  hoverBackgroundColor: [
  '#FF6384',
  '#36A2EB',
  '#FFCE56'
  ]
}]
})
class DynamicDoughnut extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      labels: ['Red', 'Green', 'Yellow'],
      datasets: [{
        data: [getRandomInt(50, 200), getRandomInt(100, 150), getRandomInt(150, 250)],
        backgroundColor: [
        '#CCC',
        '#36A2EB',
        '#FFCE56'
        ],
        hoverBackgroundColor: [
        '#FF6384',
        '#36A2EB',
        '#FFCE56'
        ]
      }]
    }
  }
	componentWillMount() {
		setInterval(() => {
			this.setState(getState())
		}, 5000)
	}
  render() {
    return (
      <div>
        <Doughnut data={this.state} />
      </div>
    );
  }
}

@connect(({ spendtypes, loading }) => ({ spendtypes, loading: loading.models.spendtypes }))
@createForm()
class SpendTypeListPage extends Component {
  constructor(props) {
    super(props)
    this.state = {
      editData: null, // 编辑界面回显信息
      isShowAddOrModifyTypePage: false, // 是否展示添加过修改消费类型界面
      selectedTypeVal: undefined, // 选择的类型

      hasMore: true, // 是否还有数据可以加载
			action: STATS.init, // 操作类型（刷新还是加载操作，加载操作要判断是否是最后一页，最后一页的话就直接显示无更多数据）
      index: 1, // loading more test time limit 可以下拉查询几次数据，相当于分页的总页数，但是这里要总页数减一，比如有2页的数据，实际上只能下拉查询一次，之后的查询都不会跟数据库交互，默认给值 1
      num: 0, // 当前页
      searchVal: undefined, // 过滤条件
      showChars: false, // 展示图表
    }
  }

  componentDidMount() {
    const { dispatch, spendtypes: { PageInfo: { pages } } } = this.props
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    // dispatch({ type: 'spendtypes/getSpendTypeLists', payload: { owner } })
    dispatch({
      type: 'spendtypes/getSpendTypeListsByPage',
      payload: { owner, pageNum: 1, pageSize: 7 },
      callback: (res) => {
        const PageInfo = {}
        PageInfo.list = res.data.list
        PageInfo.pageNum =  res.data.pageNum
        PageInfo.pages = res.data.pages
        PageInfo.pageSize = res.data.pageSize
        dispatch({ type: 'spendtypes/save', payload: { PageInfo } })
        this.setState({ index: res.data.pages - 1 })
      }
    })
    const queryParam = {}
    queryParam.owner = owner
    dispatch({ type: 'spendtypes/save', payload: { queryParam } })
    this.setState({ index: pages - 1 })

    dispatch({ type: 'spendtypes/findPieAndBarDatas', payload: { owner } }) // 查询我的花费中的图表数据
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
      // const { spendtypes: { queryParam } } = this.props
      const { searchVal } = this.state
      const reqParam = {}
      reqParam.name = searchVal
      searchVal === undefined ?  delete reqParam.name : ''
      dispatch({
        type: 'spendtypes/getSpendTypeListsByPage',
        payload: { owner, pageNum: 1, pageSize: 7, ...reqParam },
        callback: (res) => {
          setTimeout(() => {
            const PageInfo = {}
            PageInfo.list = res.data.list // 刷新后，直接覆盖原来的数据
            PageInfo.pageNum =  res.data.pageNum
            PageInfo.pages = res.data.pages
            PageInfo.pageSize = res.data.pageSize
            dispatch({ type: 'spendtypes/save', payload: { PageInfo } })
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
        const { spendtypes: { PageInfo: { list, pageNum } } } = this.props
        const { searchVal } = this.state
        const reqParam = {}
        reqParam.name = searchVal
        searchVal === undefined ?  delete reqParam.name : ''
        dispatch({
          type: 'spendtypes/getSpendTypeListsByPage',
          payload: { owner, pageNum: pageNum + 1, pageSize: 7, ...reqParam },
          callback: (res) => {
            setTimeout(() => {
              const PageInfo = {}
              PageInfo.list = [...list, ...res.data.list] // 将新查出来的数据加到原来的数据中，一起展示
              PageInfo.pageNum =  res.data.pageNum
              PageInfo.pages = res.data.pages
              PageInfo.pageSize = res.data.pageSize
              dispatch({ type: 'spendtypes/save', payload: { PageInfo } })
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

  getScrollTop = ()=>{
		if(this.refs.reactpullload){
			console.info(this.refs.reactpullload.getScrollTop());
		}
  }

	setScrollTop = ()=>{
		if(this.refs.reactpullload){
			console.info(this.refs.reactpullload.setScrollTop(100));
		}
	}

  searchSpendTypes = (val) => { // 模糊搜索选择
    const { dispatch, spendtypes: { queryParam } } = this.props
    const name = val
    queryParam.name = name
    // dispatch({ type: 'spendtypes/getSpendTypeLists', payload: { ...queryParam } })
    dispatch({
      type: 'spendtypes/getSpendTypeListsByPage',
      payload: { pageNum: 1, pageSize: 7, ...queryParam },
      callback: (res) => {
        setTimeout(() => {
          const PageInfo = {}
          PageInfo.list = res.data.list // 条件查询后，覆盖原有数据
          PageInfo.pageNum =  res.data.pageNum
          PageInfo.pages = res.data.pages
          PageInfo.pageSize = res.data.pageSize
          dispatch({ type: 'spendtypes/save', payload: { PageInfo } })
          this.setState({
            hasMore: true, // 默认让用户可以下拉
            action: STATS.reset,
            index: res.data.pages - 1, // 可刷新次数为总页数减一
            searchVal: val, // 过滤条件
          });
        }, 1000)
      }
     })

    dispatch({ type: 'spendtypes/save', payload: { queryParam } })
  }

  linkurlToDetailPage = (val) => { // 跳转到相应的花费列表页面
    localStorage.setItem('SpendDetailsData', JSON.stringify(val))
    router.push('/class/spendtypes/spendsdetails/' + val.detailShowWay)
  }

  showEditSpendTypePage = (val) => { // 展示选择编辑界面
    this.setState({ isShowAddOrModifyTypePage: true, editData: val, selectedTypeVal: val.detailShowWayInt })
    // this.showPop()
    this.toggleBody(1)
  }

  onShow = (key) => { // 展示弹框
    this.setState({ [key]: true })
    // this.stopScroll()
    // this.showPop()
    this.toggleBody(1)
  }

  delSpendType = (val) => { // 删除花销分类
    const { dispatch, spendtypes: { queryParam } } = this.props
    alert('删除', '点击确定后，该分类的所有消费记录也将会被删除，确定要删除？', [
      { text: '取消', onPress: () => console.log('cancel') },
      {
        text: '确定',
        onPress: () => { dispatch({ type: 'spendtypes/delSpendType', payload: { id: val.id, queryParam } }) }
      },
    ])
  }

  onCloseMadeal = (key) => { // 隐藏弹框
    this.props.form.resetFields()
    this.setState({ [key]: false, editData: null, selectedTypeVal: undefined });
    this.toggleBody(0)
  }

  changeTypeSelect = (value) => {
    this.setState({ selectedTypeVal: value[0] })
  }

  // stopScroll = (e) => {
  //   e = e || window.event
  //   e.stopPropagation()
  //   e.preventDefault()
  // }

  // showPop = () => {
  //   document.body.addEventListener("touchmove", self.stopScroll, { passive: false })
  //   document.body.style.overflow = 'hidden'
  // }

  // allowScroll = () => {
  //   document.body.removeEventListener('touchmove',self.stopScroll);
  //   //添加事件监听时添加了passive参数，在ios9中移除事件监听也需要加此参数
  //   document.body.removeEventListener('touchmove',self.stopScroll,{passive: true});
  //   document.body.style.overflow = 'auto';
  // }

  saveSpendType = () => { // 保存分类
    const { dispatch, spendtypes: { queryParam } } = this.props
    const { selectedTypeVal } = this.state
    const name = this.props.form.getFieldValue('typeName')
    const type = this.props.match.params.list
    if (!name || name.replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请输入分类名')
      return false
    }
    if (!selectedTypeVal || (selectedTypeVal + '').replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请选择分类类型')
      return false
    }
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    dispatch({
      type: 'spendtypes/saveSpendType',
      payload: { name, detailShowWay: selectedTypeVal, type, owner, queryParam },
      callback: (res) => {
        if (res.message === '-2') {
          alert('当前添加的分类类型已存在')
        } else {
          dispatch({ type: 'spendtypes/findPieAndBarDatas', payload: { owner } }) // 查询我的花费中的图表数据
          this.setState({ isShowAddOrModifyTypePage: false, selectedTypeVal: undefined })
          this.toggleBody(0) // 关闭弹框
          this.props.form.resetFields()
        }
      }
    })
  }

  modifySpendType = () => { // 修改分类信息
    const { dispatch, spendtypes: { queryParam } } = this.props
    const { selectedTypeVal, editData: { id, detailShowWayInt } } = this.state
    const name = this.props.form.getFieldValue('typeName')
    if (!name || name.replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请输入分类名')
      return false
    }
    if (!selectedTypeVal || (selectedTypeVal + '').replace(/(^\s*)|(\s*$)/g, '') === '') {
      alert('请选择分类类型')
      return false
    }
    const owner = JSON.parse(localStorage.getItem('USER_INFO')).id
    dispatch({
      type: 'spendtypes/modifySpendType',
      payload: { id, name, detailShowWay: selectedTypeVal, owner, queryParam, ext1: selectedTypeVal === detailShowWayInt },
      callback: (res) => {
        if (selectedTypeVal !== detailShowWayInt) {
          if (res.message === '-2') {
            alert('当前修改的分类类型已存在')
          } else {
            this.setState({ isShowAddOrModifyTypePage: false, selectedTypeVal: undefined })
            this.toggleBody(0) // 关闭弹框
            this.props.form.resetFields()
          }
        } else {
          dispatch({ type: 'spendtypes/findPieAndBarDatas', payload: { owner } }) // 查询我的花费中的图表数据
          this.setState({ isShowAddOrModifyTypePage: false, selectedTypeVal: undefined })
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
    let body = document.body;
    let top = body.scrollTop;
    if(isPin){
      body.style.cssText = 'width: 100%; height: 100%; position: fixed; top: -' + top + 'px; left: 0; overflow: hidden;';
    } else {
      body.removeAttribute('style');
      body.scrollTop = top;
    }
  }

  render() {
    const { spendtypes: { PageInfo: { list }, dataGroupByYear, dataGroupByType }, dispatch, form: { getFieldProps } } = this.props
    const { isShowAddOrModifyTypePage, selectedTypeVal, editData, hasMore, showChars } = this.state
    const nineTypes = [
      { value: 1, label: '衣服鞋子' },
      { value: 2, label: '教育支出' },
      { value: 3, label: '娱乐支出' },
      { value: 5, label: '回趟家花销' },
      { value: 6, label: '住房花费' },
      { value: 7, label: '吃花销' },
      { value: 8, label: '日用品花销' },
      { value: 9, label: '人际关系' },
      { value: 10, label: '其他花销' },
      { value: 11, label: '借款' },
      { value: 12, label: '外出乘坐交通工具花销' }
    ];
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
            labelsData.push(`${item.prices}`)
            return labelsData
          }) : [],
        }
      ]
    }
    const dataPieType = {
      labels: dataGroupByType ? dataGroupByType.map(item => {
        const labelsData = []
        labelsData.push(`${item.name}`)
        return labelsData
      }) : [],
      datasets: [{
        data: dataGroupByType ? dataGroupByType.map(item => {
          const labelsData = []
          labelsData.push(`${item.typePrices}`)
          return labelsData
        }) : [],
        backgroundColor: dataGroupByType ? dataGroupByType.map((item, index) => {
          const labelsData = []
          labelsData.push(backColors[index])
          return labelsData
        }) : [],
        hoverBackgroundColor: dataGroupByType ? dataGroupByType.map((item, index) => {
          const labelsData = []
          labelsData.push(backColors[index])
          return labelsData
        }) : [],
      }]
    }
    const dataBarType = {
      labels: dataGroupByType ? dataGroupByType.map(item => {
        const labelsData = []
        labelsData.push(`${item.name}`)
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
          data: dataGroupByType ? dataGroupByType.map(item => {
            const labelsData = []
            labelsData.push(`${item.typePrices}`)
            return labelsData
          }) : [],
        }
      ]
    }
    const dataBubble = {
      labels: dataGroupByYear ? dataGroupByYear.map(item => {
        const labelsData = []
        labelsData.push(`${item.years/1}`)
        return labelsData
      }) : [],
      datasets: [
        {
          label: '我的支出',
          fill: false,
          lineTension: 0.1,
          backgroundColor: 'rgba(75,192,192,0.4)',
          borderColor: 'rgba(75,192,192,1)',
          borderCapStyle: 'butt',
          borderDash: [],
          borderDashOffset: 0.0,
          borderJoinStyle: 'miter',
          pointBorderColor: 'rgba(75,192,192,1)',
          pointBackgroundColor: '#fff',
          pointBorderWidth: 1,
          pointHoverRadius: 5,
          pointHoverBackgroundColor: 'rgba(75,192,192,1)',
          pointHoverBorderColor: 'rgba(220,220,220,1)',
          pointHoverBorderWidth: 2,
          pointRadius: 1,
          pointHitRadius: 10,
          // data: [{ x:10, y:20, r:5}],
          data: dataGroupByYear ? dataGroupByYear.map((item, index) => {
            const labelsData = []
            labelsData.push({ x: item.years/1, y: item.prices/1, z: 5 })
            return labelsData
          }) : [],
        }
      ]
    }
    const dataDoughnutYear = {
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
    const dataDoughnutType = {
      labels: dataGroupByType ? dataGroupByType.map(item => {
        const labelsData = []
        labelsData.push(`${item.name}`)
        return labelsData
      }) : [],
      datasets: [{
        data: dataGroupByType ? dataGroupByType.map(item => {
          const labelsData = []
          labelsData.push(`${item.typePrices}`)
          return labelsData
        }) : [],
        backgroundColor: dataGroupByType ? dataGroupByType.map((item, index) => {
          const labelsData = []
          labelsData.push(backColors[index])
          return labelsData
        }) : [],
        hoverBackgroundColor: dataGroupByType ? dataGroupByType.map((item, index) => {
          const labelsData = []
          labelsData.push(backColors[index])
          return labelsData
        }) : [],
      }]
    }

    return (
      <div className={styles.content_me} >

        <Search dispatch={dispatch} searchSpendTypes={this.searchSpendTypes} />
        <div className={`${styles.service_info} box_shadow`}>
          <div className={`${styles.service_title} border_bottommin`}>
            <a onClick={() => this.onShow('showChars')}>我的花费</a>
            <div style={{ float: 'right', marginTop: -8 }}><Button onClick={() => this.onShow('isShowAddOrModifyTypePage')} type="ghost" inline size="small" style={{ marginRight: 0 }}>添加</Button></div>
          </div>

          {/* {datas && datas[0] ? datas.map((item, key) =>
          <div key={key}>
          <div className={styles.service_content}>
            <div className={styles.service_item} onClick={() => this.linkurlToDetailPage(item)}>
              <img
                className={styles.service_img}
                src={require('../../../assets/recycleH5_17.png')}
                alt=""
              />
              <div className={styles.service_text}>{item.name}</div>
            </div>
            <div style={{ float: 'right' }}><Button onClick={() => this.showEditSpendTypePage(item)} type="ghost" inline size="small" style={{ marginRight: '4px' }}>编辑</Button></div>
            <div style={{ float: 'right' }}><Button onClick={() => this.delSpendType(item)} type="warning" inline size="small" style={{ marginRight: '4px' }}>删除</Button></div>
          </div>
          <WhiteSpace size="lg" />
          </div>
          )
          :
          <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
          } */}

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
            {/* <Card>
              <Card.Header
                title="This is title"
                thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
                extra={<span>this is extra</span>}
              />
              <Card.Body>
                <div>This is content of `Card`</div>
              </Card.Body>
              <Card.Footer content="footer content" extra={<div>extra footer content</div>} />
            </Card> */}
            {
                list && list[0] ? list.map((item, key) =>
                  <div key={key} className={styles.amCard} style={{ padding: '0px 8px' }}>
                  <WhiteSpace size="lg" />
                  <Card>
                    <Card.Header
                      // title={item.name}
                      onClick={() => this.linkurlToDetailPage(item)}
                      thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
                      extra={<span>{item.name}</span>}
                    />
                    <Card.Footer content={<a onClick={() => this.showEditSpendTypePage(item)}>编辑</a>} extra={<div><a onClick={() => this.delSpendType(item)}>删除</a></div>} />
                  </Card>
                  </div>
                  )
                  :
                  <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
              }
              {/* {
                list && list[0] ? list.map((item, key) =>
                  <div key={key}>
                  <div className={styles.service_content}>
                    <div className={styles.service_item} onClick={() => this.linkurlToDetailPage(item)}>
                      <img
                        className={styles.service_img}
                        src={require('../../../assets/recycleH5_17.png')}
                        alt=""
                      />
                      <div className={styles.service_text}>{item.name}</div>
                    </div>
                    <div style={{ float: 'right' }}><Button onClick={() => this.showEditSpendTypePage(item)} type="ghost" inline size="small" style={{ marginRight: '4px' }}>编辑</Button></div>
                    <div style={{ float: 'right' }}><Button onClick={() => this.delSpendType(item)} type="warning" inline size="small" style={{ marginRight: '4px' }}>删除</Button></div>
                  </div>
                  <WhiteSpace size="lg" />
                  </div>
                  )
                  :
                  <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
              } */}
            </ul>
				  </ReactPullLoad>

        </div>

        <Modal
          visible={isShowAddOrModifyTypePage}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={() => this.onCloseMadeal('isShowAddOrModifyTypePage')}
          footer={[{ text: '关闭', onPress: () => this.onCloseMadeal('isShowAddOrModifyTypePage') }, { text: '保存', onPress: editData ? () => this.modifySpendType() : () => this.saveSpendType() }]}
        >
          <div style={{ maxHeight: 200, minHeight: 150 }}>
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>{editData ? '修改花费类型' : '添加花费类型'}</div>}>

              <InputItem
                {...getFieldProps('typeName', {
                  initialValue: editData ? editData.name : undefined,
                })}
                type="text"
                placeholder="请输入"
                id="typeName"
                onFocus={() => this.scrollToView('typeName')}
                style={{ textAlign: 'left' }}
              ><div style={{ color: '#000' }}>分类名</div></InputItem>

              <InputItem
                {...getFieldProps('detailShowWay', {
                  initialValue: selectedTypeVal && nineTypes.find(item => item.value === selectedTypeVal) ? nineTypes.find(item => item.value === selectedTypeVal).label : (editData ? nineTypes.find(item => item.value === editData.detailShowWayInt).label : undefined),
                })}
                type="text"
                placeholder="请选择"
                readOnly
                onFocus={this.forbiddenInput}
                style={{ textAlign: 'left' }}
              ><div style={{ color: '#000' }}>分类类型</div></InputItem>
             </List>
            <div>
              <PickerView
                onScrollChange={this.changeTypeSelect}
                value={selectedTypeVal}
                data={nineTypes}
                cascade={false}
              />
            </div>
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
            <List renderHeader={() => <div style={{ color: 'rgb(16, 142, 233)', fontSize: 20, marginTop: -20 }}>我的消费</div>}>
            <div style={{ height: 400 }}>
              <StickyContainer>
                <Tabs tabs={mainTabs}
                  initalPage={'t2'}
                  renderTabBar={renderTabBar}
                >
                  <div>
                    <h4 style={{ paddingTop: 16 }}>每年消费金额（单位：元）</h4>
                    <Pie data={dataPie} />
                    <h4 style={{ paddingTop: 24 }}>各分类在所有时间的支出（单位：元）</h4>
                    <Pie data={dataPieType} height={260} />
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
                    <div style={{ height: 300, paddingTop: 32 }}>
                    <h4 style={{ paddingTop: 32 }}>各分类在所有时间的支出（单位：元）</h4>
                    <Bar
                      data={dataBarType}
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

                  <div>
                    <div>
                      <h4 style={{ paddingTop: 16 }}>每年消费金额（单位：元）</h4>
                      <Bubble data={dataBubble} />
                    </div>
                  </div>

                  <div>
                    <div>
                      <h4 style={{ paddingTop: 16 }}>每年消费金额（单位：元）</h4>
                      <Doughnut data={dataDoughnutYear} />
                      <h4 style={{ paddingTop: 24 }}>各分类在所有时间的支出（单位：元）</h4>
                      <Doughnut data={dataDoughnutType} height={260} />
                      <div style={{ paddingBottom: 16 }}></div>
                    </div>
                    <div style={{ paddingBottom: 16 }}></div>
                  </div>

                  <div>
                    <div style={{ height: 200 }}>
                    <h4 style={{ paddingTop: 8 }}>每年消费金额（单位：元）</h4>
                    <HorizontalBar data={dataBar} />
                    </div>
                    <div style={{ height: 380, paddingTop: 32 }}>
                    <h4 style={{ paddingTop: 32 }}>各分类在所有时间的支出（单位：元）</h4>
                    <HorizontalBar height={300} data={dataBarType} />
                    </div>
                  </div>

                  <div>
                    <div style={{ height: 200 }}>
                    <h4 style={{ paddingTop: 16 }}>每年消费金额（单位：元）</h4>
                    <Line data={dataBar} />
                    </div>
                    <div style={{ height: 300, paddingTop: 32 }}>
                    <h4 style={{ paddingTop: 32 }}>各分类在所有时间的支出（单位：元）</h4>
                    <Line height={300} data={dataBarType} />
                    </div>
                  </div>

                  <div>
                    <h4 style={{ paddingTop: 16 }}>每年消费金额（单位：元）</h4>
                    <Polar data={dataPie} />
                    <h4 style={{ paddingTop: 24 }}>各分类在所有时间的支出（单位：元）</h4>
                    <Polar data={dataPieType} height={260} />
                    <div style={{ paddingBottom: 16 }}></div>
                  </div>

                  <div>
                    <h4 style={{ paddingTop: 16 }}>Radar Example</h4>
                    <Radar height={260} data={dataRadar} />
                  </div>

                  <div>
                    <h4 style={{ paddingTop: 16 }}>Scatter Example</h4>
                    <Scatter data={dataScatter} />
                  </div>

                  <div>
                    <h4 style={{ paddingTop: 16 }}>crazy graphs</h4>
                      <CrazyGraph />
                  </div>

                  <div>
                    <h4 style={{ paddingTop: 16 }}>Dynamicly refreshed Doughnut Example</h4>
                    <DynamicDoughnut />
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

export default SpendTypeListPage
