import React, { Component } from 'react'
import { connect } from 'dva'
import { WhiteSpace} from 'antd-mobile'
import router from 'umi/router'
import styles from './index.less'
import NameCard from './NameCard'

@connect(({ demo, loading }) => ({ demo, loading: loading.models.demo }))
class ClassPage extends Component {
  constructor(props) {
    super(props)
    this.state = {
      // avatar: require('../../assets/default-pic.png'), // 头像
    }
  }

  componentDidMount() {
    const { dispatch } = this.props
    dispatch({ type: 'demo/reg' })
    const userid = JSON.parse(localStorage.getItem('USER_INFO')).id
    dispatch({ type: 'demo/getUserInfoById', payload: { id: userid }, callback: (res) => {
      if (res && res.data) {
        let picUrls = res.data.picUrl
        // this.setState({ avatar: require('c:/adb/nginx1.8/html/'+picUrls) })
        // this.setState({ avatar: picUrls })
        dispatch({ type: 'demo/save', payload: { avatar: picUrls } })
      }
    } })
  }

  linkurl = (id) => { // 菜单页面跳转
    if (id === '1') {
      router.push('/class/spendtypes/' + id)
    } else if (id === '2') {
      router.push('/class/chooseforyou/list')
    } else if (id === '3') {
      router.push('/class/chooseforyou/' + id)
    } else if (id === '4') {
      router.push('/class/chooseforyou/list')
    } else if (id === '5') {
      router.push('/class/approveforleave/' + id)
    }
  }

  render() {
    const { demo: { datas, avatar }, dispatch } = this.props
    // const { avatar } = this.state
    return (
      <div className={styles.content_me}>
        <NameCard
          name={JSON.parse(localStorage.getItem('USER_INFO')).name}
          avatar={avatar}
          dispatch={dispatch}
        />
        <div className={styles.service_info + ' ' + 'box_shadow'}>
          <div className={styles.service_title + ' ' + 'border_bottommin'}>我的服务</div>
          {datas && datas[0] ? datas.map((item, key) =>
          <div key={key} hidden={item.id==='2' || item.id === '4'}>
          <div className={styles.service_content}>
            <div className={styles.service_item} onClick={() => this.linkurl(item.id)}>
              <img
                className={styles.service_img}
                src={require('../../assets/recycleH5_' + item.name + '.png')}
                alt=""
              />
              <div className={styles.service_text}>{item.name}</div>
            </div>
          </div>
          <WhiteSpace size="lg" />
          </div>
          )
          :
          ''
          }
        </div>
      </div>
    )
  }
}

export default ClassPage
