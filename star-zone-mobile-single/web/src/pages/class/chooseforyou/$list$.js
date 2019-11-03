import React, { Component } from 'react'
import { connect } from 'dva'
import { NoticeBar, NavBar, Icon, Modal , Toast, Button, WhiteSpace, List, InputItem, Radio, Carousel, WingBlank, Badge, Card } from 'antd-mobile'
import router from 'umi/router'
import styles from './index.less'
import styleLottery from './lottery.less'
import Search from './Search'
import { createForm } from 'rc-form'

const { alert } = Modal
const { RadioItem } = Radio
const { Item } = List
const showToastTime = 2

function closest(el, selector) {
  const matchesSelector = el.matches || el.webkitMatchesSelector || el.mozMatchesSelector || el.msMatchesSelector;
  while (el) {
    if (matchesSelector.call(el, selector)) {
      return el;
    }
    el = el.parentElement;
  }
  return null;
}

@connect(({ choosefoyou, loading }) => ({ choosefoyou, loading: loading.models.choosefoyou }))
@createForm()
class ChooseListPage extends Component {
  constructor(props) {
    super(props)
    this.state = {
      confVisible: false, // 展示配置
      addVisible: false, // 展示添加、编辑
      chooseName: '', // 选择名
      chooseType: 0, // 选择方式
      addOrEdit: null, // 添加或编辑
      editData: {}, // 编辑界面回显信息
      chooseId: '', // 选择项id，用户提交配置的选择项
      chooseItemType: '', // 选择项采用的选择类型，用户提交配置的选择项
      chooseVisible: false, // 展示选择界面（九宫格）
      choosePageChooseId: '', // 选择界面的选择id
      choosePageName: '', // 选择界面的选择名称

      activedId: '', // 被选中的格子的ID
      prizeId: null, // 中奖ID
      times: 0, // 获得prizeId之后计算出的动画次数
      actTimes: 0, // 当前动画次数
      isRolling: false, // 是否正在抽奖
      list: [0, 1, 2, 3, 4, 5, 6, 7],
    }
  }

  componentDidMount() {
    const { dispatch } = this.props
    const menuid = this.props.match.params.list
    const userid = JSON.parse(localStorage.getItem('USER_INFO')).id
    dispatch({ type: 'choosefoyou/getChooseLists', payload: { menuid, userid } })
    const queryParam = {}
    queryParam.userid = userid
    dispatch({ type: 'choosefoyou/save', payload: { queryParam } })
  }

  searchChoose = (val) => { // 模糊搜索选择
    const { dispatch } = this.props
    const menuid = this.props.match.params.list
    const userid = JSON.parse(localStorage.getItem('USER_INFO')).id
    const name = val
    dispatch({ type: 'choosefoyou/getChooseLists', payload: { menuid, userid, name } })
    const queryParam = {}
    queryParam.menuid = menuid
    queryParam.userid = userid
    queryParam.name = name
    dispatch({ type: 'choosefoyou/save', payload: { queryParam } })
  }

  showConfChoose = (val) => { // 展现配置选项界面
    const { dispatch } = this.props
    // 先判断是否有配置过，配置过了的不能再配置了
    dispatch({ type: 'choosefoyou/checkHasConfig', payload: { chooseId: val.id }, callback: (res) => {
      if (res && res.data && res.data.length > 0) {
        Toast.info('已经配置过了', showToastTime)
        return false
      } else {
        this.setState({ confVisible: true, chooseId: val.id, chooseItemType: val.type })
        this.toggleBody(1) // 打开弹框
      }
    } })
  }

  showChoosepage = (val) => { // 展现选择界面
    if (val.type !== '1') {
      Toast.fail('暂不支持的选择类型', showToastTime)
      return false
    } else {
      const { dispatch } = this.props
      dispatch({ type: 'choosefoyou/choicePageInit', payload: { chooseId: val.id }, callback: (res) => {
        if (res && res.data && res.data.length <= 0) {
          Toast.info('还没有配置选项，请先配置', showToastTime)
          return false
        } else{
          dispatch({ type: 'choosefoyou/findChooseRes', payload: { chooseId: val.id }, callback: (res) => {} })
          this.setState({ chooseVisible: true, choosePageChooseId: val.id, choosePageName: val.name, list: res.data.filter(item => item.name) })
          this.toggleBody(1) // 打开弹框
        }
      } })
    }
  }

  lottery = () => {
    // this.state.isRolling为false的时候才能开始抽，不然会重复抽取，造成无法预知的后果
    if (!this.state.isRolling) {
      // 点击抽奖之后，我个人做法是将于九宫格有关的状态都还原默认
      this.setState({
        activedId: '',
        prizeId: null,
        times: 0,
        actTimes: 0,
        isRolling: true
      }, () => {
        // 状态还原之后才能开始真正的抽奖
        this.handlePlay()
      })
    }
  }

  handlePlay() {
    // 随机获取一个中奖ID （前端模拟数据）
    // let prize = Math.floor(Math.random() * 8)
    // console.log(prize)
    // this.setState({  prizeId: prize, activedId: 0 })
    // 调后台选择接口选择
    const { dispatch } = this.props
    const { choosePageChooseId } = this.state
    dispatch({ type: 'choosefoyou/choice', payload: { chooseId: choosePageChooseId }, callback: (res) => {
      if (res && res.data && res.data.length === 1) {
        this.setState({ prizeId: parseInt(res.data[0].position),  activedId: 0 })
      }
    } })

    // 随机算出一个动画执行的最小次数，这里可以随机变更数值，按自己的需求来
    let times = this.state.list.length * Math.floor(Math.random() * 5 + 6)
    this.setState({
      times: times
    })
    // 抽奖正式开始↓↓
    this.begin = setInterval(() => {
      let num;

      if (this.state.activedId === this.state.prizeId && this.state.actTimes > this.state.times) {
        // 符合上述所有条件时才是中奖的时候，两个ID相同并且动画执行的次数大于(或等于也行)设定的最小次数
        const { choosefoyou: { chooseRes } } = this.props
        Toast.success('选到的是：'+chooseRes[0].name, showToastTime)
        dispatch({ type: 'choosefoyou/findChooseRes', payload: { chooseId: choosePageChooseId }, callback: (res) => {} })
        clearInterval(this.begin)
        this.setState({
          isRolling: false
        })
        return
      }

      // 以下是动画执行时对id的判断
      if (this.state.activedId === '') {
        num = 0
        this.setState({
          activedId: num
        })
      } else {
        num = this.state.activedId
        if (num === 7) {
          num = 0
          this.setState({
            activedId: num
          })
        } else {
          num = num + 1
          this.setState({ activedId: num })
        }
      }
      this.setState({ actTimes: this.state.actTimes + 1 })
    }, 40)
  }

  changeChooseName = (val) => { // 改变选择名
    this.setState({ chooseName: val })
  }

  changeChooseType = (val) => { // 改变选择类型
    this.setState({ chooseType: val })
  }

  showAddChoose = () => { // 显示添加界面
    this.setState({ addVisible: true, addOrEdit: 'add' })
    this.toggleBody(1) // 打开弹框
  }

  addChoose = () => { // 添加选择
    const { dispatch, choosefoyou: { queryParam } } = this.props
    const { chooseName, chooseType } = this.state
    const menuid = this.props.match.params.list
    const userid = JSON.parse(localStorage.getItem('USER_INFO')).id
    if (chooseName === '' || chooseName === null || chooseName === undefined) {
      Toast.fail('选择名不能为空', showToastTime)
      return false
    }
    if (chooseType === 0) {
      Toast.fail('请选择一个类型', showToastTime)
      return false
    }
    dispatch({
      type: 'choosefoyou/addChoose',
      payload: { menuid, userid, name: chooseName, type: chooseType },
      callback: (res) => {
        if (res && res.code === 1) {
          Toast.success('操作成功', showToastTime)
          dispatch({ type: 'choosefoyou/getChooseLists', payload: { ...queryParam } })
        }
      }
    })
    this.setState({ addVisible: false, chooseName: '', chooseType: 0, addOrEdit: null })
    this.toggleBody(0) // 关闭弹框
  }

  showEditChoose = (val) => { // 展示选择编辑界面
    this.setState({ addVisible: true, addOrEdit: 'edit', chooseName: val.name, chooseType: val.type, editData: val })
    this.toggleBody(1) // 打开弹框
  }

  editChoose = () => { // 编辑提交
    const { dispatch, choosefoyou: { queryParam } } = this.props
    const { editData, chooseName, chooseType } = this.state
    if (chooseName === '' || chooseName === null || chooseName === undefined) {
      Toast.fail('选择名不能为空', showToastTime)
      return false
    }
    if (chooseType === 0) {
      Toast.fail('请选择一个类型', showToastTime)
      return false
    }
    alert('修改', '确定要修改？修改后，选项配置信息及之前的选择结果都会被删除。', [
      { text: '取消', onPress: () => console.log('cancel') },
      {
        text: '确定',
        onPress: () => {
          dispatch({ type: 'choosefoyou/editChoose', payload: { id: editData.id, name: chooseName, type: chooseType, queryParam } })
          this.setState({ addVisible: false, chooseName: '', chooseType: 0, addOrEdit: null, editData: {} })
          this.toggleBody(0) // 关闭弹框
        }
      },
    ])
    // dispatch({ type: 'choosefoyou/editChoose', payload: { id: editData.id, name: chooseName, type: chooseType, queryParam } })
    // this.setState({ addVisible: false, chooseName: '', chooseType: 0, addOrEdit: null, editData: {} })
  }

  onClose = key => () => { // 隐藏弹框
    this.props.form.resetFields()
    this.setState({ [key]: false, chooseName: '', chooseType: 0, addOrEdit: null, editData: {}, choosePageChooseId: '' });
    this.toggleBody(0) // 关闭弹框
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

  delChoose = (val) => {
    const { dispatch, choosefoyou: { queryParam } } = this.props
    alert('删除', '确定要删除？点击确定后，选项配置信息及之前的选择结果都会被删除。', [
      { text: '取消', onPress: () => console.log('') },
      {
        text: '确定',
        onPress: () => {
          dispatch({ type: 'choosefoyou/deleteChoose',
          payload: { id: val.id },
          callback: (res) => {
            Toast.success('操作成功', showToastTime)
            dispatch({ type: 'choosefoyou/getChooseLists', payload: { ...queryParam } })
          }
        })
        }
      },
    ])
  }

  validateInput = (rule, value, callback) => { // 输入校验
    if (value && value.replace(/(^\s*)|(\s*$)/g, '') !== '' && value.length >= 1) {
      callback();
    } else {
      callback(new Error('至少输入一个字符'))
    }
  }

  onSubmit = () => { // 保存选择配置
    const { dispatch, choosefoyou: { queryParam } } = this.props
    const { chooseId, chooseItemType, activedId  } = this.state
    this.props.form.validateFields({ force: true }, (error, values) => {
      if (!error) {
        const req = {}
        Object.assign(req, values)
        const nameDatas = []
        for (let k of Object.keys(req)) {
          if (req[k] !== undefined && req[k] !== null && req[k].replace(/(^\s*)|(\s*$)/g, '') === '') { // 判断是否有只输入空格的数据
            alert('请检查输入项，输入的数据不能只是空格')
            return false
          }
          if (req[k] !== undefined && req[k] !== null && req[k].replace(/(^\s*)|(\s*$)/g, '') !== '') {
            nameDatas.push(req[k])
          }
        }
        dispatch({ type: 'choosefoyou/addChooseItems', payload: { chooseId: chooseId, type: chooseItemType, setList: nameDatas, queryParam }, callback: (res) => {
          if (res && res.code === 1) {
            Toast.success('操作成功', showToastTime)
            this.props.form.resetFields()
            this.setState({ confVisible: false })
            this.toggleBody(0) // 关闭弹框
            return false
          }
        } })
      } else {
        alert('请检查必输项')
      }
    });
  }

  onReset = () => {
    this.props.form.resetFields();
  }

  onWrapTouchStart = (e) => {
    // fix touch to scroll background page on iOS
    if (!/iPhone|iPod|iPad/i.test(navigator.userAgent)) {
      return;
    }
    const pNode = closest(e.target, '.am-modal-content');
    if (!pNode) {
      e.preventDefault();
    }
  }

  scrollToView = (value) => {
    document.getElementById(value).scrollIntoView()
  }

  render() {
    const { choosefoyou: { datas, choosePageInitData, resList }, dispatch, form: { getFieldProps, getFieldError } } = this.props
    const { addOrEdit, addVisible, chooseType, chooseName, confVisible, chooseVisible, activedId, choosePageName } = this.state
    const data = [
      { value: '1', label: '九宫格' },
      { value: '2', label: '转盘（暂不支持）' },
      { value: '3', label: '老虎机（暂不支持）' },
    ];
    const listSource = []
    resList && resList.length > 0 ? resList.map(item => { listSource.push(item.choosedName +'-'+item.ext1) }) : ''

    return (
      <div className={styles.content_me}>
        <Search dispatch={dispatch} searchChoose={this.searchChoose} />
        <div className={`${styles.service_info} box_shadow`}>
          <div className={`${styles.service_title} border_bottommin`}>
            我的选择
            <div style={{ float: 'right', marginTop: -8 }}><Button onClick={() => this.showAddChoose()} type="ghost" inline size="small" style={{ marginRight: 0 }}>添加</Button></div>
          </div>
          {datas && datas[0] ? datas.map((item, key) =>
          <div key={key} className={styles.amCard} style={{ padding: '0px 8px' }}>
          <WhiteSpace size="lg" />
          <Card>
            <Card.Header
              // title={item.name}
              onClick={() => this.showChoosepage(item)}
              thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
              extra={<span>{item.name}</span>}
            />
            <Card.Footer
              extra={
                <div style={{ display: 'inline-block'}}>
                  <div style={{ display: 'inline-block' }}><a disabled={item.ext1 ? true : false} onClick={() => this.showConfChoose(item)}>配置</a></div>
                  <div style={{ display: 'inline-block', padding: '0px 24px' }}><a onClick={() => this.showEditChoose(item)}>编辑</a></div>
                  <div style={{ display: 'inline-block' }}><a onClick={() => this.delChoose(item)}>删除</a></div>
                </div>
              }
            />
          </Card>
          </div>
          )
          :
          <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
          }
          {/* {datas && datas[0] ? datas.map((item, key) =>
          <div key={key}>
          <div className={styles.service_content}>
            <div className={styles.service_item} onClick={() => this.showChoosepage(item)}>
              <img
                className={styles.service_img}
                src={require('../../../assets/recycleH5_19.png')}
                alt=""
              />
              <div className={styles.service_text}>{item.name}</div>
            </div>
            <div style={{ float: 'right' }}><Button disabled={item.ext1 ? true : false} onClick={() => this.showConfChoose(item)} type="primary" inline size="small" style={{ marginRight: '4px' }}>配置</Button></div>
            <div style={{ float: 'right' }}><Button onClick={() => this.showEditChoose(item)} type="ghost" inline size="small" style={{ marginRight: '4px' }}>编辑</Button></div>
            <div style={{ float: 'right' }}><Button onClick={() => this.delChoose(item)} type="warning" inline size="small" style={{ marginRight: '4px' }}>删除</Button></div>
          </div>
          <WhiteSpace size="lg" />
          </div>
          )
          :
          <div style={{ textAlign: 'center', marginTop: '0.3rem' }}>无数据</div>
          } */}
        </div>

        <Modal
          title={<div style={{ color: '#108ee9'}}>{addOrEdit === 'add' ? '添加选择' : '修改选择'}</div>}
          popup
          visible={addVisible}
          transparent
          maskClosable={false}
          animationType="slide-up"
          // className="modal-service"
          // platform={'android'}
          footer={[
            { text: '取消', onPress: () => { this.onClose('addVisible')() } },
            { text: '确 定', onPress: () => addOrEdit === 'add' ? this.addChoose() : this.editChoose() }
          ]}
        >
          <div className="modal-coupon-center" style={{ height: 200 }}>
            <List>
              <InputItem
                placeholder={'请输入'}
                onChange={this.changeChooseName}
                defaultValue={chooseName}
               >选择名 :</InputItem>
             </List>
            <List renderHeader={() => <div style={{ textAlign: 'left'}}>从如下选项选择方式：</div>}>
               {data.map(i => (
                  <RadioItem key={i.value} checked={chooseType === i.value} onChange={() => this.changeChooseType(i.value)}>
                     {<div style={{ fontSize: 14 }}>{i.value + '-' +i.label}</div>}
                  </RadioItem>
              ))}
             </List>
         </div>
        </Modal>

        <Modal
          title={<div>配置选择项</div>}
          popup
          visible={confVisible}
          className="modal-service"
          animationType="slide-up"
          platform={'android'}
          footer={[
            { text: '取消', onPress: () => { this.onClose('confVisible')() } },
            { text: '保存', onPress: () => this.onSubmit() }
          ]}
        >
          <div className="modal-coupon-center" style={{ /*height: 390*/ minHeight: 150, maxHeight: 200 }}>
          <NoticeBar mode="closable" icon={null}>最多配8个,最少2个.没填的系统按已填项随机分配.</NoticeBar>
          <form>
            <List>
              <InputItem
                {...getFieldProps('choose1', {
                  // initialValue: 'little ant',
                  rules: [
                    { required: true, message: '必输入项' },
                    { validator: this.validateInput },
                  ],
                })}
                error={!!getFieldError('choose1')}
                onErrorClick={() => {
                  alert(getFieldError('choose1').join('、'));
                }}
                id="choose1"
                onFocus={() => this.scrollToView('choose1')}
                placeholder={'选项1'}
               />
               <InputItem
                {...getFieldProps('choose2', {
                  rules: [
                    { required: true, message: '必输入项' },
                  ],
                })}
                error={!!getFieldError('choose2')}
                onErrorClick={() => {
                  alert(getFieldError('choose2').join('、'));
                }}
                id="choose2"
                onFocus={() => this.scrollToView('choose2')}
                placeholder={'选项2'}
               />
               <InputItem
                {...getFieldProps('choose3')}
                id="choose3"
                onFocus={() => this.scrollToView('choose3')}
                placeholder={'选项3'}
               />
               <InputItem
                {...getFieldProps('choose4')}
                id="choose4"
                onFocus={() => this.scrollToView('choose4')}
                placeholder={'选项4'}
               />
               <InputItem
                {...getFieldProps('choose5')}
                id="choose5"
                onFocus={() => this.scrollToView('choose5')}
                placeholder={'选项5'}
               />
               <InputItem
                {...getFieldProps('choose6')}
                id="choose6"
                onFocus={() => this.scrollToView('choose6')}
                placeholder={'选项6'}
               />
               <InputItem
                {...getFieldProps('choose7')}
                id="choose7"
                onFocus={() => this.scrollToView('choose7')}
                placeholder={'选项7'}
               />
               <InputItem
                {...getFieldProps('choose8')}
                id="choose8"
                onFocus={() => this.scrollToView('choose8')}
                placeholder={'选项8'}
               />
             </List>
             {/* <Item>
               <Button type="primary" size="small" inline onClick={this.onSubmit}>保存</Button>
               <Button size="small" inline style={{ marginLeft: '2.5px' }} onClick={this.onReset}>重填</Button>
             </Item> */}
            </form>
         </div>
        </Modal>

        <Modal
          title={<div>{choosePageName}</div>}
          visible={chooseVisible}
          transparent
          style={{ width: '80%' }}
          maskClosable={false}
          onClose={this.onClose('chooseVisible')}
          footer={[{ text: '关闭', onPress: () => { this.onClose('chooseVisible')(); } }]}
          wrapProps={{ onTouchStart: this.onWrapTouchStart }}
        >
          <div>
          <div id="lottery">
<ul className={styleLottery.cj}>
	<li className={activedId === 0 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="0">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '0').name : null}</p>
		</div>
	</li>
	<li className={activedId === 1 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="1">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '1').name : null}</p>
		</div>
	</li>
	<li className={activedId === 2 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="2">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '2').name : null}</p>
		</div>
	</li>
	<li className={activedId === 7 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="7">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '7').name : null}</p>
		</div>
	</li>
	<li><p><a id="lotteryBtn"><img onClick={this.lottery} src={require('../../../assets/btncj.png')} /></a></p></li>
	<li className={activedId === 3 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="3">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '3').name : null}</p>
		</div>
	</li>
	<li className={activedId === 6 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="6">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '6').name : null}</p>
		</div>
	</li>
	<li className={activedId === 5 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="5">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '5').name : null}</p>
		</div>
	</li>
	<li className={activedId === 4 ? styleLottery.hidle : null}>
		<div>
			<span></span>
			<p id="4">{choosePageInitData && choosePageInitData.length === 8 ? choosePageInitData.find(item => item.position === '4').name : null}</p>
		</div>
	</li>
</ul>
</div>
<div>
  <Badge text="选择记录"
        style={{
          marginLeft: 12,
          padding: '0 3px',
          backgroundColor: '#fff',
          color: '#108ee9',
          marginTop: 10,
          fontSize: 15,
          marginBottom: 4,
        }}
      />
</div>
<WingBlank>
    <Carousel
      vertical
      dots={false}
      dragging={false}
      swiping={false}
      autoplay
      infinite={true}
      speed={1000}
      autoplayInterval={3000}
      resetAutoplay={false}
    >
      {listSource.length > 0 ? listSource.map(type => (
        <div key={type}>{type}</div>
      )) : <div key={1}>无数据</div>}
    </Carousel>
  </WingBlank>
         </div>
        </Modal>

      </div>
    )
  }
}

export default ChooseListPage
