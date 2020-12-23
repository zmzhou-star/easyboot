const steps = [
  {
    element: '#menu-scrollbar',
    popover: {
      title: '菜单导航',
      description: '点击菜单可切换右侧菜单内容',
      position: 'right'
    },
    padding: 0
  },
  {
    element: '#hamburger-container',
    popover: {
      title: '折叠按钮',
      description: '点击收缩和展开菜单导航',
      position: 'bottom'
    }
  },
  {
    element: '#breadcrumb-container',
    popover: {
      title: '页面导航',
      description: '指示当前页面位置',
      position: 'bottom'
    }
  },
  {
    element: '#header-search',
    popover: {
      title: 'Page Search',
      description: 'Page search, quick navigation',
      position: 'left'
    }
  },
  {
    element: '#screenfull',
    popover: {
      title: '全屏显示',
      description: '将页面设置为全屏显示',
      position: 'left'
    }
  },
  {
    element: '#size-select',
    popover: {
      title: '尺寸开关',
      description: '切换系统文字大小',
      position: 'left'
    }
  },
  {
    element: '#nick-name',
    popover: {
      title: '用户昵称',
      description: '点击可前往用户个人中心',
      position: 'left'
    },
    padding: 0
  },
  {
    element: '#avatar-container',
    popover: {
      title: '个人中心',
      description: '点击图标，显示个人中心等链接',
      position: 'left'
    },
    padding: 0
  },
  {
    element: '#tags-view-container',
    popover: {
      title: '标签视图',
      description: '您访问过的页面的历史记录',
      position: 'bottom'
    },
    padding: 0
  }
]

export default steps
