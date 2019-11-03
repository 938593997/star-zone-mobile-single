import React from 'react'
import { SearchBar, Icon, NavBar } from 'antd-mobile'
import router from 'umi/router'
import styles from './search.less'

class Search extends React.Component {

  render() {
    const { searchSpendTypes } = this.props // 组件中方法的传递
    return (
      <div>
        <NavBar
          mode="dark"
          // leftContent="返回"
          icon={<Icon type="left" />}
          onLeftClick={() => router.push('/class')}
        ><SearchBar
            placeholder="请输入搜索内容"
            ref={ref => this.autoFocusInst = ref}
            onChange={searchSpendTypes}
            style={{ minWidth: window.screen.width*0.77, marginLeft: '10px' }}
            cancelText={<div style={{ color: '#fff' }}>取消</div>}
        /></NavBar>
      </div>
    )
  }
}

export default Search
