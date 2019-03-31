var WxSearch = require('../wxSearchView/wxSearchView.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    mainHeight: wx.getSystemInfoSync().windowHeight,
    mainCategoryActive: 0,
    mainCategoryActiveForSkip: 0,
    categories:[
      {
        categoryName:'情人节告白系列',
        products:[
          {
            productName:'浓情蜜意',
            description:'99枝戴安娜',
            price:899,
            productImages:[
              { productUrl:'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3406300341,2036134685&fm=15&gp=0.jpg'}
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          }
        ]
      },
      {
        categoryName: '网红款系列',
        products: [
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          }
        ]
      }, {
        categoryName: '水晶之恋-定制系列',
        products: [
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          }
        ]
      }
    ]
  },
  selectCategory:function(event) {
    this.setData({ 
      mainCategoryActive: event.currentTarget.dataset.index, 
      mainCategoryActiveForSkip: event.currentTarget.dataset.index});
  },
  scroll: function(event) {
    this.setData({ mainCategoryActive: this.getCategoryActive(event.detail.scrollTop) });
  },
  getCategoryActive: function(scrollTop) {
    var categoryNameHeight = 36;
    var productHeight = 122;
    var totalHeight = 0;
    for (var i = 0; i < this.data.categories.length; i ++) {
      totalHeight += categoryNameHeight + productHeight * this.data.categories[i].products.length;
      if (scrollTop < totalHeight) {
        return i;
      }
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 2 搜索栏初始化
    var that = this;
    WxSearch.init(
      that,  // 本页面一个引用
      ['情人', '网红', "水晶之恋", "定制", '家人', '老师','领导'], // 热点搜索推荐，[]表示不使用
      ['情人', '网红', "水晶之恋", "定制", '家人', '老师', '领导'],// 搜索匹配，[]表示不使用
      that.mySearchFunction, // 提供一个搜索回调函数
      that.myGobackFunction //提供一个返回回调函数
    );
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  },
  // 3 转发函数，固定部分，直接拷贝即可
  wxSearchInput: WxSearch.wxSearchInput,  // 输入变化时的操作
  wxSearchKeyTap: WxSearch.wxSearchKeyTap,  // 点击提示或者关键字、历史记录时的操作
  wxSearchDeleteAll: WxSearch.wxSearchDeleteAll, // 删除所有的历史记录
  wxSearchConfirm: WxSearch.wxSearchConfirm,  // 搜索函数
  wxSearchClear: WxSearch.wxSearchClear,  // 清空函数
  onClick: WxSearch.onClick,  // 点击事件
  onCancle: WxSearch.onCancle, //不显示


  // 4 搜索回调函数  
  mySearchFunction: function (value) {
    // do your job here
    // 示例：跳转
    this.setData({
      showCancle: false
    })
    //value 是 搜索的值
    wx.navigateTo({
      url: '/pages/search/search?value='+value
    })
  },

  // 5 返回回调函数
  myGobackFunction: function () {
    // do your job here
    // 示例：返回
    wx.redirectTo({
      url: '../index/index?searchValue=返回'
    })
  }


})
