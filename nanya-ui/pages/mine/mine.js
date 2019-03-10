// pages/mine/mine.js
import Dialog from '../../dist/dialog/dialog';
var app = getApp()
Page({
  data: {
    userInfo: {},
    motto: 'Hello World',
    show:false,
    // orderItems
    orderItems: [
      {
        typeId: 0,
        name: '待付款',
        url: 'bill',
        imageurl: 'pending-payment'
      },
      {
        typeId: 1,
        name: '待发货',
        url: 'bill',
        imageurl: 'exchange'
      },
      {
        typeId: 2,
        name: '待收货',
        url: 'bill',
        imageurl: 'tosend'
      },
      {
        typeId: 3,
        name: '待评价',
        url: 'bill',
        imageurl: 'records'
      },
      {
        typeId: 4,
        name: '售后服务',
        url: 'bill',
        imageurl: 'chat'
      }
    ],
  },
  //事件处理函数
  toOrder: function () {
    wx.navigateTo({
      url: '../order/order'
    })
  },
  login :function(){
      wx.getUserInfo({
        success: res => {
          // 可以将 res 发送给后台解码出 unionId
          app.globalData.userInfo = res.userInfo
          this.data.userInfo = app.globalData.userInfo;
          this.setData({
            "userinfo": app.globalData.userInfo
          })
          // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
          // 所以此处加入 callback 以防止这种情况
          if (app.userInfoReadyCallback) {
            app.userInfoReadyCallback(res)
          }
        }
      })    
  },
  //初始化时获取用户信息
  onLoad : function () {
    var that = this;
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
        //调用应用实例的方法获取全局数据
        that.login();
        }}
      })
  },
  //授权窗口信息
  toLogin:function(){
    this.setData({
      show: true
    });    
  },
  //关闭授权窗口
  onClose: function (event) {
    if (event.detail === 'confirm') {
      // 异步关闭弹窗
      setTimeout(() => {
        this.setData({
          show: false
        });
      }, 200);
    } else {
      this.setData({
        show: false
      });
    }
  },
  //确认授权之后，官方提供用户信息
  onConfirmed(e){
    app.globalData.userInfo = e.detail.userInfo;
    userInfo = e.detail.userInfo;
  }
  
})
 