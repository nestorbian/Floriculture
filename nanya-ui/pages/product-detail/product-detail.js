var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    product: {},
    indexNumber: 1,
    actionSheet: {
      show: false
      // actions: [
      //   {
      //     name: '花 材',
      //     subname: '红色玫瑰19枝，黄莺点缀红色玫瑰19枝，黄莺点缀红色玫瑰19枝，黄莺点缀红色玫瑰19枝，黄莺点缀红色玫瑰19枝，黄莺点缀',
      //     openType: 'share'
      //   },
      //   {
      //     name: '包 装',
      //     subname: '韩国进口银色和透明雾面纸及黑色螺纹丝带',
      //     openType: 'share'
      //   },
      //   {
      //     name: '场 景',
      //     subname: '适合送爱人/女友',
      //     openType: 'share'
      //   }
      // ]
    }
  },

  changeSwiperItem: function(event) {
    this.setData({indexNumber: event.detail.current + 1});
  },

  onClose() {
    this.setData({ actionSheet: { show: false, actions: this.data.actionSheet.actions} });
  },

  popupActionSheet: function(event) {
    this.setData({ actionSheet: { show: true, actions: this.data.actionSheet.actions } });
    console.log(event);
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.productId);
    wx.request({
      url: app.globalData.baseRequestUrl + '/products/' + options.productId,
      method: 'GET',
      dataType: 'json',
      success: (res) => {
        if (res.statusCode == 200) {
          this.setData({ product: res.data.data});
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
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
    
  }
})