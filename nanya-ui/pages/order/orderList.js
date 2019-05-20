// pages/order/orderList.js

// pages/order/order.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currtab: 0,
    swipertab: [    
      {
        typeId: 0,
        name: '付款',
        url: 'bill',
        imageurl: 'pending-payment'
      },
      {
        typeId: 1,
        name: '发货',
        url: 'bill',
        imageurl: 'exchange'
      },
      {
        typeId: 2,
        name: '收货',
        url: 'bill',
        imageurl: 'tosend'
      },
      {
        typeId: 3,
        name: '评价',
        url: 'bill',
        imageurl: 'records'
      }]
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
    // 页面渲染完成
    this.getDeviceInfo()
    this.orderShow()
  },

  getDeviceInfo: function () {
    let that = this
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          deviceW: res.windowWidth,
          deviceH: res.windowHeight
        })
      }
    })
  },

  /**
  * @Explain：选项卡点击切换
  */
  tabSwitch: function (e) {
    var that = this
    if (this.data.currtab === e.target.dataset.current) {
      return false
    } else {
      that.setData({
        currtab: e.target.dataset.current
      })
    }
  },

  tabChange: function (e) {
    this.setData({ currtab: e.detail.current })
    this.orderShow()
  },

  orderShow: function () {
    let that = this;
    switch (this.data.currtab) {
      case 0:
        that.alreadyShow()
        break
      case 1:
        that.waitPayShow()
        break
      case 2:
        that.lostShow()
        break
      case 3:
        that.waitForComment()
        break
    }
  },
  alreadyShow: function () {
    this.setData({
      alreadyOrder: [{ name: "航仔是你爸爸啊", state: "交易成功", time: "2018-09-30 14:00-16:00", status: "已结束", url: "http://129.204.173.36/images/demo2.jpg", money: "132" }, { name: "航仔是你爸爸啊", state: "交易成功", time: "2018-10-12 18:00-20:00", status: "未开始", url: "http://129.204.173.36/images/demo2.jpg", money: "205" }]
    })
  },

  waitPayShow: function () {
    this.setData({
      waitPayOrder: [{ name: "航仔是你爸爸啊", state: "待付款", time: "2018-10-14 14:00-16:00", status: "未开始", url: "http://129.204.173.36/images/demo2.jpg", money: "186" }],
    })
  },

  lostShow: function () {
    this.setData({
      lostOrder: [{ name: "航仔是你爸爸啊", state: "已取消", time: "2018-10-4 10:00-12:00", status: "未开始", url: "http://129.204.173.36/images/demo2.jpg", money: "122" }],
    })
  },
  waitForComment : function () {
    this.setData({
      waitForComment: [{ name: "航仔是你爸爸啊", state: "待评价", time: "2018-10-4 10:00-12:00", status: "未开始", url: "http://129.204.173.36/images/demo2.jpg", money: "122" }]
    })
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
