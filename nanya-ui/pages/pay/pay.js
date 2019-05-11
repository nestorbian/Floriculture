var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderId: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
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
  pay: function() {
    var that = this;
    wx.request({
      url: app.globalData.baseRequestUrl + '/order/test',
      method: 'GET',
      success: (result) => {
        const preOrderInfo = result.data.data;
        wx.requestPayment({
          'timeStamp': preOrderInfo.timeStamp,
          'nonceStr': preOrderInfo.nonceStr,
          'package': preOrderInfo.package,
          'signType': 'MD5',
          'paySign': preOrderInfo.paySign,
          'success': function (res) {
            that.setData({ orderId: preOrderInfo.orderId });
            console.log("支付成功！");
          },
          'fail': function (res) {
            console.log("用户取消支付");
          }
        })
      },
      fail: () => {
        console.log("失败");
      }
    });
  },
  refund: function() {
    wx.request({
      url: app.globalData.baseRequestUrl + '/refund/test',
      method: 'GET',
      data: {orderId:this.data.orderId},
      dataType: 'json',
      success: (result) => {
        console.log("[退款成功] result is " + JSON.stringify(result));
      },
      fail: () => {
        console.log("[退款失败]");
      }
    })
  }
})