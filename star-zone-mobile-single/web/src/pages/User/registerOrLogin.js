import React, { Component } from 'react'
import { connect } from 'dva'
import { CSSTransition } from 'react-transition-group'
import { SearchBar, Modal, Menu, Toast, View } from 'antd-mobile'
import ExportJsonExcel from 'js-export-excel'
import classNames from 'classnames'
import router from 'umi/router'
import styles from './registerOrLogin.less'

@connect(({ user, loading }) => ({ user, loading: loading.models.user }))
class registerOrLoginPage extends Component {
  constructor(props) {
    super(props)
    this.state = {
      nameIsFocus: false, // 是否获取焦点
      name: '', // 用户名
      nameError: false, // 用户名是否格式错误
      passwordIsFocus: false, // 是否获取焦点
      password: '', // 密码
      passwordError: false, // 密码错误
      passwordKeyIsFocus: false, // 是否获取焦点
      passwordKey: '', // 秘钥
      passwordKeyError: false, // 密钥错误
      visible: false, // 协议弹窗
    }
  }
  componentDidMount() {
    const { dispatch } = this.props
    this.setState({ name: '', password: '', passwordKey: '' })
    dispatch({ type: 'user/save', payload: { actions: undefined } })
  }

  showProtocol() { // 协议弹窗
    this.setState({ visible: true });
    this.toggleBody(1)
  }

  onClose = key => () => {
    this.setState({ [key]: false, });
    this.toggleBody(0)
  }

  login = () => { // 用户登入
    const { name, passwordKey, password } = this.state
    const { dispatch } = this.props
    if (passwordKey.length < 9) {
      Toast.fail('秘钥长度不能小于9位', 3);
      return false
    }
    if (name.length === 0) {
      Toast.fail('请输入用户名', 3);
      return false
    }
    if (password.length === 0) {
      Toast.fail('请输入密码', 3);
      return false
    }
    dispatch({ type: 'user/login', payload: { name, des: passwordKey, password } })
  }

  register = () => { // 用户注册
    const { name, passwordKey, password } = this.state
    const { dispatch } = this.props
    if (passwordKey.length < 9) {
      Toast.fail('秘钥长度不能小于9位', 3);
      return false
    }
    if (name.length === 0) {
      Toast.fail('请输入用户名', 3);
      return false
    }
    if (password.length === 0) {
      Toast.fail('请输入密码', 3);
      return false
    }
    dispatch({
      type: 'user/register',
      payload: {
        name, des: passwordKey, password
      },
      callback: (res) => {
        if (res) { // 注册成功后，清空输入的注册信息
          this.setState({ name: '', password: '', passwordKey: '' })
        }
      }
    })
  }

  showRegisterOrLogin = () => { // 判断是登入还是注册
    const { name, passwordKey } = this.state
    const { dispatch } = this.props
    if (name.length === 0) {
      Toast.fail('请输入用户名', 3);
      return false
    }
    if (passwordKey.length < 9) {
      Toast.fail('秘钥长度不能小于9位', 3);
      return false
    }
    dispatch({ type: 'user/showRegisterOrLogin', payload: { name, des: passwordKey } })
  }

  scrollToView = (value) => {
    document.getElementById(value).scrollIntoView()
  }

  toggleBody = (isPin) => {
    let body = document.body;
    let top = body.scrollTop;
    if(isPin){
      body.style.cssText = 'width: 100%; height: 100%; position: fixed; top: -' + top + 'px; left: 0; overflow: hidden;';
    } else {
      body.removeAttribute('style');
      body.scrollTop = top;
    }
  }

  downloadExcel = () => { // 模板下载
    const data = [{ name: '杭峰', taskContent: '开发' }]
    var option={};
    let dataTable = [];
    if (data) {
      for (let i in data) {
        if(data){
          let obj = {
            '姓名': data[i].name,
            '任务内容': data[i].taskContent,
          }
          dataTable.push(obj);
        }
      }
    }
    option.fileName = '任务信息'
    option.datas=[
      {
        sheetData:dataTable,
        sheetName:'sheet',
        sheetFilter:['姓名','任务内容'],
        sheetHeader:['姓名','任务内容'],
      }
    ];

    var toExcel = new ExportJsonExcel(option);
    toExcel.saveExcel();
  }

  render() {
    const { user: { datas, actions } } = this.props
    const { visible, nameIsFocus, name, nameError, passwordIsFocus, password, passwordError, passwordKeyIsFocus, passwordKey, passwordKeyError } = this.state
    const info = '本系统是一个基于个人信息管理而开发的。系统的的登入需要三个要素，分别是用户名、秘钥及密码，用户可以在系统首页输入用户名、秘钥，系统会根据用户输入的信息去验证当前用户是登入还是注册。注册成功时需要再次输入登入系统。'
    return (
      <div style={{ paddingTop: 48 }}>
        <div className={styles.ac}>
          <img className={styles.rz} src={require('../../assets/user/logo.jpg')}/>
        </div>
        <p className={styles.rzTitle }>STAR_ZONE</p>
        <div className="form-wraper">
          <div className={classNames('input-wrap')}>
            <CSSTransition
              in={name.length > 0}
              timeout={400}
              classNames="fade"
              unmountOnExit
            >
              <span>用户名</span>
            </CSSTransition>
            <CSSTransition
              in={name.length > 0}
              timeout={400}
              classNames="input"
            >
            <input
              className={ nameError ? 'error' : ''}
              value={name}
              onChange={e => {
                if(e.target.value.length <= 11) {
                  this.setState({ name: e.target.value })
                }
              }}
              onFocus={() => {
                this.setState({ nameIsFocus: true });
                // this.scrollToView('name');
              }}
              onBlur={() => {
                this.setState({ nameIsFocus: false });
              }}
              id="name"
              type="text"
              placeholder="请输入用户名"/>
            </CSSTransition>
          </div>
          <Line show={nameIsFocus}/>

          <div className={classNames('input-wrap')}>
            <CSSTransition
              in={passwordKey.length > 0}
              timeout={400}
              classNames="fade"
              unmountOnExit
            >
              <span>密钥</span>
            </CSSTransition>
            <CSSTransition
              in={passwordKey.length > 0}
              timeout={400}
              classNames="input"
            >
            <input
              className={ passwordKeyError ? 'error' : ''}
              value={passwordKey}
              onChange={e => {
                if(e.target.value.length <= 11) {
                  this.setState({ passwordKey: e.target.value })
                }
              }}
              onFocus={() => {
                this.setState({ passwordKeyIsFocus: true });
                this.scrollToView('passwordKey');
              }}
              onBlur={() => {
                this.setState({ passwordKeyIsFocus: false });
                this.showRegisterOrLogin();
              }}
              type="password"
              id="passwordKey"
              placeholder="请输入密钥"/>
            </CSSTransition>
          </div>
          <Line show={passwordKeyIsFocus}/>

          <div className={classNames('input-wrap')}>
            <CSSTransition
              in={password.length > 0}
              timeout={400}
              classNames="fade"
              unmountOnExit
            >
              <span>密码</span>
            </CSSTransition>
            <CSSTransition
              in={password.length > 0}
              timeout={400}
              classNames="input"
            >
            <input
              className={ passwordError ? 'error' : ''}
              value={password}
              onChange={e => {
                if(e.target.value.length <= 11) {
                  this.setState({ password: e.target.value })
                }
              }}
              onFocus={() => {
                this.setState({ passwordIsFocus: true });
                this.showRegisterOrLogin();
                this.scrollToView('password');
              }}
              onBlur={() => {
                this.setState({ passwordIsFocus: false });
              }}
              type="password"
              id="password"
              placeholder="请输入密码"/>
            </CSSTransition>
          </div>
          <Line show={passwordIsFocus}/>
          <div className="login-wrap">
            {/* <div hidden={actions === 'login' ? false : true} className="login" onClick={this.login}>登入</div>
            <div hidden={actions === 'register' ? false : true} className="login" onClick={this.register}>注册</div> */}
            <div disabled={actions === 'login' ? false : true} className="login" onClick={actions === 'login' ? this.login : (actions === 'register' ? this.register : this.showRegisterOrLogin)}>{actions === 'login' ? '登入' : (actions === 'register' ? '注册' : '登入/注册')}</div>
            {/* <div disabled={actions === 'register' ? false : true} className="login" onClick={this.register}>注册</div> */}
          </div>
          <p className="agree-wrap" onClick={(e) => {
            this.showProtocol();
            }}>系统介绍及说明<span className="agree">《说明书》</span></p>
            {/* <p onClick={() => this.downloadExcel()}>下载</p> */}
          </div>
          <Modal
            popup
            visible={visible}
            animationType="slide-up"
            className="modal-service"
            platform={'android'}
            onClose={this.onClose('visible')}
            footer={[{ text: '确 定', onPress: () => { console.log('ok'); this.onClose('visible')(); } }]}
          >
          <div className="modal-coupon-center" style={{ height: 200 }}>
            <iframe
              ref={(f) => {
                this.iframe = f;
              }}
              width="100%"
              height="0%"
              // height = 'auto'
              frameBorder={0}
              // src="https://umijs.org/zh/guide/"
            />
            {<div style={{ marginRight: 8, marginLeft: 8, marginTop: 8 }}>{info}</div>}
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

export default registerOrLoginPage
