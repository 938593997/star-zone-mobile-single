import React from 'react'
import { SearchBar, Icon, NavBar } from 'antd-mobile'
import router from 'umi/router'

class Search extends React.Component {

  render() {
    const { searchInfo } = this.props // 组件中方法的传递
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
            onChange={searchInfo}
            style={{ minWidth: window.screen.width * 0.77, marginLeft: 10 }}
            cancelText={<div style={{ color: '#fff' }}>取消</div>}
        /></NavBar>
      </div>
    )
  }
}

export default Search
