import React, { Component } from 'react'
import { Toast, Modal, List, InputItem, Radio, Flex, DatePicker, ImagePicker } from 'antd-mobile'
import DatePickers from 'react-mobile-datepicker'
import { CSSTransition } from 'react-transition-group'
import lrz from 'lrz'
import Zmage from 'react-zmage'
// import ReactImageProcess from "react-image-process"
import { connect } from 'dva'
import moment from 'moment'
import router from 'umi/router'
import { createForm } from 'rc-form'
import styles from './nameCard.less'
import imgURL from '../../assets/default-pic.png'

const { alert } = Modal
const nowTimeStamp = Date.now()
const now = new Date(nowTimeStamp)
const showToastTime = 2

@connect(({ demo, loading }) => ({ demo, loading: loading.models.demo }))
@createForm()
class NameCard extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      isShowPersonalInfo: false, // 是否展示个人信息
      isShowModifyPage: false, // 是否展示个人信息修改界面
      date: now, // 生日
      checkedSex: '', // 选中的性别 男  女
      openTimeSelect: false, //打开时间选择
      files: [], // 选择的头像
      imagesrc: '', // lrz 压缩后的图片
      backgroundcolorself: '#00a3d2', // 背景颜色
      nameShowIsFocus: false, // 姓名输入
    }
  }

  showPage = key => () => { // 展现弹框
    if (key === 'isShowPersonalInfo') {
      const { dispatch } = this.props
      const userid = JSON.parse(localStorage.getItem('USER_INFO')).id
      dispatch({ type: 'demo/getUserInfoById', payload: { id: userid }, callback: (res) => {
        if (res && res.data) {
          this.setState({ date: new Date(res.data.birthday) })
        }
      } })
    }
    this.setState({ [key]: true })
    this.toggleBody(1) // 打开弹框
  }

  onClose = key => () => { // 隐藏弹框
    this.setState({ [key]: false })
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

  quit = () => { // 退出登入
    const { dispatch } = this.props
    alert('退出', '确定要退出？', [
      { text: '取消', onPress: () => console.log('cancel') },
      {
        text: '确定',
        onPress: () => { dispatch({ type: 'user/quit', payload: {} }) }
      },
    ])
  }

  changeCheckedSex = (e) => { // 改变性别选择
    if (e && e.target) {
      this.setState({ checkedSex: e.target.children === '男' ? '男' : '女' })
    }
  }

  savePersonalInfo = () => { // 保存个人信息修改
    const { dispatch, demo: { userInfo } } = this.props
    const { date, checkedSex, imagesrc } = this.state
    this.props.form.validateFields({ force: true }, (error, values) => {
      if (!error) {
        const req = {}
        Object.assign(req, values)
        for (let k of Object.keys(req)) {
          if (req[k] !== undefined && req[k] !== null && req[k].replace(/(^\s*)|(\s*$)/g, '') === '') { // 判断是否有只输入空格的数据
            alert('请检查输入项，输入的数据不能只是空格')
            return false
          }
        }
        dispatch({ type: 'demo/savePersonalInfo', payload: { ...req, birthday: moment(date).format('YYYY-MM-DD'), sex: checkedSex ? checkedSex === '男' ? '1' : '2' : userInfo.user.sex, id: JSON.parse(localStorage.getItem('USER_INFO')).id, picUrl: imagesrc === '' ? null : imagesrc }, callback: (res) => {
          if (res && res.code === 1) {
            Toast.success('操作成功，请重新登入', showToastTime)
            this.props.form.resetFields()
            this.setState({ isShowModifyPage: false })
            this.toggleBody(0) // 关闭弹框
            dispatch({ type: 'demo/save', payload: { avatar: res.data.picUrl } })
            router.push('/User/registerOrLogin')
            return false
          }
        } })
      } else {
        alert('请检查必输项')
      }
    });
  }

  changeImageSelect = (files, type, index) => { // 选择头像
    const { dispatch } = this.props
    if (type==='add') {
      // lrz(files[0].url, { quality: 0.1, width: 800 })
      lrz(files[0].url, { quality: 0.6, width: 800 })
      .then((rst)=>{
        // 处理成功会执行
        this.setState({ imagesrc: rst.base64 })
        dispatch({ type: 'demo/save', payload: { avatar: rst.base64 } })
      })
    }else{
        this.setState({imagesrc: ''})
    }
    this.setState({ files, })
  }

  forbiddenInput = (e) => {
    document.activeElement.blur()
    this.setState({ openTimeSelect: true })
  }

  render() {
    // const avatar = this.props.avatar || imgURL
    const image = localStorage.getItem('USER_INFO') ? JSON.parse(localStorage.getItem('USER_INFO')).picUrl : null
    const avatar = this.props.avatar || (image ? image : imgURL)
    const { isShowPersonalInfo, isShowModifyPage, checkedSex, date, openTimeSelect, files, backgroundcolorself, nameShowIsFocus } = this.state
    const { getFieldProps } = this.props.form
    const { demo: { userInfo } } = this.props
    return (
      <div className={styles.name_content} style={{ background: `${backgroundcolorself}` }}>
        <div className={styles.content_card}>
          <img onClick={this.showPage('isShowPersonalInfo')} className={styles.avatar} src={avatar} alt="" />
          <div className={styles.content_name}>It's {JSON.parse(localStorage.getItem('USER_INFO')).name}_Zone</div>
          <div style={{ marginLeft: 24 }} className={styles.quit} onClick={this.quit}>退出</div>
          {/* <div hidden={true}>
            <ReactImageProcess mode="primaryColor" onComplete={(color) => this.setState({ backgroundcolorself: color })}>
              <img src={avatar} alt="" />
            </ReactImageProcess>
          </div> */}
        </div>

        <Modal
          visible={isShowPersonalInfo}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={this.onClose('isShowPersonalInfo')}
          footer={[{ text: '关闭', onPress: () => { this.onClose('isShowPersonalInfo')() } }, { text: '去修改', onPress: () => { this.showPage('isShowModifyPage')() } }]}
        >
          <div>
            {/* <img src={avatar} style={{ width: '1rem', height: '1rem', border: 1, borderRadius: '2rem', background: '#fff', }} alt="" /> */}
            <Zmage src={avatar} style={{ width: '1rem', height: '1rem', border: 1, borderRadius: '2rem', background: '#fff', }} alt="" />
            <List renderHeader={() => '个人信息'}>
          <InputItem
            {...getFieldProps('nameShow', {
              initialValue: JSON.parse(localStorage.getItem('USER_INFO')).name,
            })}
            type="text"
            editable={false}
          ><div style={{ color: '#888' }}>用户名</div></InputItem>
          <InputItem
            {...getFieldProps('desShow', {
              initialValue: userInfo ? userInfo.des : null,
            })}
            type="text"
            placeholder="star_zone"
            editable={false}
          ><div style={{ color: '#888' }}>密钥</div></InputItem>
          <InputItem
            {...getFieldProps('passwordShow', {
              initialValue: userInfo ? userInfo.password : null,
            })}
            type="text"
            editable={false}
          ><div style={{ color: '#888' }}>密码</div></InputItem>
          <InputItem
            {...getFieldProps('sexShow', {
              initialValue: userInfo ? ( userInfo && userInfo.user && userInfo.user.sex === '1' ? '男' : '女') : null,
            })}
            type="text"
            editable={false}
          ><div style={{ color: '#888' }}>性别</div></InputItem>
          <InputItem
            {...getFieldProps('registDateShow', {
              initialValue: userInfo && userInfo.user ? userInfo.user.registDate : null,
            })}
            type="text"
            editable={false}
          ><div style={{ color: '#888' }}>注册时间</div></InputItem>
          <InputItem
            {...getFieldProps('birthdayShow', {
              initialValue: userInfo ? userInfo.birthday : null,
            })}
            type="text"
            editable={false}
          ><div style={{ color: '#888' }}>生日</div></InputItem>
          <InputItem
            {...getFieldProps('useSzDateShow', {
              initialValue: userInfo ? userInfo.useSzDate : null,
            })}
            type="text"
            editable={false}
          ><div style={{ color: '#888' }}>使用天数</div></InputItem>
        </List>
         </div>
        </Modal>

        <Modal
          visible={isShowModifyPage}
          transparent
          style={{ width: '90%' }}
          maskClosable={false}
          onClose={this.onClose('isShowModifyPage')}
          footer={[{ text: '关闭', onPress: () => { this.onClose('isShowModifyPage')() } }, { text: '保存', onPress: () => this.savePersonalInfo() }]}
        >
          <div>
            {/* <img src={avatar} style={{ width: '1rem', height: '1rem', border: 1, borderRadius: '2rem', background: '#fff' }} alt="" /> */}
            <Zmage src={avatar} style={{ width: '1rem', height: '1rem', border: 1, borderRadius: '2rem', background: '#fff', }} alt="" />
            <List renderHeader={() => <div>修改个人信息</div>}>

            <Flex style={{ padding: '15px' }}>
            <Flex.Item style={{ padding: '2px 0', color: '#000', flex: 'none',fontSize: 17, marginLeft: 0, marginRight: 5, textAlign: 'left', whiteSpace: 'nowrap', overflow: 'hidden', width: 85 }}><div style={{ color: '#888' }}>头像</div></Flex.Item>
            <Flex.Item>
            <ImagePicker
              files={files}
              onChange={this.changeImageSelect}
              onImageClick={(index, fs) => console.log(index, fs)}
              selectable={files.length < 1}
              multiple={false}
            />
            </Flex.Item>
            </Flex>

          <InputItem
            {...getFieldProps('name', {
              initialValue: JSON.parse(localStorage.getItem('USER_INFO')).name,
            })}
            type="text"
            placeholder="请输入"
            onFocus={() => { this.setState({ nameShowIsFocus: true }) }}
            onBlur={() => { this.setState({ nameShowIsFocus: false }) }}
          ><div style={{ color: '#888' }}>用户名</div></InputItem>
          {/* <Line show={nameShowIsFocus}/> */}
          <InputItem
            {...getFieldProps('des', {
              initialValue: userInfo ? userInfo.des : null,
            })}
            type="password"
            placeholder="请输入"
          ><div style={{ color: '#888' }}>密钥</div></InputItem>
          <InputItem
            {...getFieldProps('password', {
              initialValue: userInfo ? userInfo.password : null,
            })}
            type="password"
            placeholder="请输入"
          ><div style={{ color: '#888' }}>密码</div></InputItem>

          <Flex style={{ padding: '15px' }}>
            <Flex.Item style={{ padding: '2px 0', color: '#000', flex: 'none',fontSize: 17, marginLeft: 0, marginRight: 5, textAlign: 'left', whiteSpace: 'nowrap', overflow: 'hidden', width: 85 }}><div style={{ color: '#888' }}>性别</div></Flex.Item>
            <Flex.Item>
              <Radio className="my-radio" checked={checkedSex === '' ? userInfo && userInfo.user && userInfo.user.sex === '1' ? true : false : checkedSex === '男' ? true : false} onChange={(e) => this.changeCheckedSex(e)} style={{ marginRight: 16 }} >男</Radio>
              <Radio className="my-radio" checked={checkedSex === '' ? userInfo && userInfo.user && userInfo.user.sex === '2' ? true : false : checkedSex === '女' ? true : false} onChange={(e) => this.changeCheckedSex(e)}>女</Radio>
           </Flex.Item>
          </Flex>

          <DatePickers
            value={date}
            isOpen={openTimeSelect}
            style={{ 'z-index' : 99 }}
            onSelect={date => this.setState({ date, openTimeSelect: false })}
            onCancel={this.onClose('openTimeSelect')}
          />
          {/* </Flex> */}
          <InputItem
            {...getFieldProps('birthdayT', {
              initialValue: date ? moment(date).format('YYYY-MM-DD') : null,
            })}
            type="text"
            placeholder="请选择"
            id="datePicker"
            // onFocus={this.showPage('openTimeSelect')}
            onFocus={this.forbiddenInput}
            readOnly
          ><div style={{ color: '#888' }}>生日</div></InputItem>
        </List>
         </div>
        </Modal>

      </div>
    )
  }
}

// 分割线动画组件
class Line extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
  }
  render() {
    return (
      <div className="line-wrap">
        <CSSTransition
          in={this.props.show}
          timeout={500}
          classNames="line"
        >
          <div className="line"></div>
        </CSSTransition>
      </div>
    )
  }
}

export default NameCard
