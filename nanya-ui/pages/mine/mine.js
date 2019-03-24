// pages/mine/mine.js
//import 授权弹出框
import Dialog from '../../dist/dialog/dialog'; 
import Toast from '../../dist/toast/toast';

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
        imageurl: 'chat-o'
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
    var that =this;
      wx.getUserInfo({
        success: res => {
          // 可以将 res 发送给后台解码出 unionId
          app.globalData.userInfo = res.userInfo
          that.data.userInfo = app.globalData.userInfo;
          that.setData({
            "userInfo": app.globalData.userInfo
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
    var that = this;
    that.setData({
      show: true
    });
  },
  //关闭授权窗口
  onClose: function (event) {
    var that = this;
    if (event.detail === 'confirm') {
      // 异步关闭弹窗
      setTimeout(() => {
        that.setData({
          show: false
        });
      }, 200);
    } else {
      that.setData({
        show: false
      });
    }
  },
  //确认授权之后，官方提供用户信息
  onConfirmed(e) {
    var that = this ;
    app.globalData.userInfo = e.detail.userInfo;
    that.setData({
      "userInfo" : e.detail.userInfo
    })
    loginNy();
  },
  callPhone: function(){
    app.callPhone()
  },
  //跳转到个人中心
  toPerson:function(){
    var model = JSON.stringify(this.data.userInfo);
    if (wx.getStorageSync('thirdSession')==null){
      Toast("请先点击头像登录");
    }else{
      wx.navigateTo({
        url: '../test1/ny_personal?userInfo=' + model
      })
    }
  },
  //跳转到收货地址
  toAddress: function () {
    wx.navigateTo({
      url: '/pages/mine/address'
    })
  },
  //登录获取3rdsession
  loginNy: function () {
    wx.login({
      success(res) {
        if (res.code) {
          // 发起网络请求
          wx.request({
            url: 'http://127.0.0.1:80/nanyahuayi/WxLoginController/loginUser',
            data: {
              code: res.code
            },
            success(res) {
              //return 3rd_session = res.data
              wx.setStorageSync('thirdSession', res.data)            
          }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  }
  
})
 