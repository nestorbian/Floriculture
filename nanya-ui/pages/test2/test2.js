var app = getApp();
import Notify from '../../dist/notify/notify';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    categories: {},
    imgUrls: [
      '../../images/demo1.jpg',
      '../../images/demo1.jpg',
      '../../images/demo1.jpg'
    ],
    categoryUrl: 'http://img02.hua.com/slider/18_birthday_pc.jpg',
    productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg'
  },

  viewProductDetail: function(event) {
    const productId = event.currentTarget.dataset.productid;
    wx.navigateTo({
      url: '/pages/product-detail/product-detail?productId=' + productId,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.startPullDownRefresh({
    });
    wx.request({
      url: app.globalData.baseRequestUrl + '/categories/home',
      method: 'GET',
      dataType: 'json',
      success: (res) => {
        if(res.statusCode == 200) {
          this.setData({ categories: res.data.data})
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      },
      complete: () => {
        wx.stopPullDownRefresh();
      }
    });
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
    // wx.showNavigationBarLoading();
    wx.request({
      url: app.globalData.baseRequestUrl + '/categories/home',
      method: 'GET',
      dataType: 'json',
      success: (res) => {
        if (res.statusCode == 200) {
          this.setData({ categories: res.data.data })
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      },
      complete: () => {
        wx.stopPullDownRefresh();
      }
    })
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
  navigateToCategoryDetail: function(e) {
    //传递商品类型编号
    var categoryId = e.currentTarget.id ;
    wx.navigateTo({
      url: '../search/search?categoryId=' + categoryId,
    })
  }
})