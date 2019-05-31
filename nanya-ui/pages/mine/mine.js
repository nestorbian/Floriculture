// pages/mine/mine.js
//import 授权弹出框
import Dialog from '../../dist/dialog/dialog'; 
import Toast from '../../dist/toast/toast';
import Notify from '../../dist/notify/notify';

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
    orderCount: {}
  },
  //事件处理函数
  toOrder: function () {
    wx.navigateTo({
      url: '../order/order?typeId='+typeId
    })
  },
  //初始化时获取用户信息
  onLoad : function () {
    var that = this;

    if (app.globalData.userInfo) {
      that.setData({
        userInfo: app.globalData.userInfo
      })
    } else{
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        that.setData({
          userInfo: res.userInfo
        })
      }
    }
    
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (wx.getStorageSync("thirdSession")!='') {
      this.getOrderStatusCount();
    }
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
      userInfo : e.detail.userInfo
    })
    this.loginNy();
  },
  callPhone: function(){
    app.callPhone()
  },
  //跳转到个人中心
  toPerson:function(){
    var model = JSON.stringify(this.data.userInfo);
    if (wx.getStorageSync('thirdSession') == ''){
      Toast("请先点击头像登录");
    }else{
      wx.navigateTo({
        url: '../mine/ny_personal?userInfo=' + model
      })
    }
  },
  //跳转到收货地址
  toAddress: function () {
    if (wx.getStorageSync('thirdSession') == '') {
      Toast("请先点击头像登录");
    } else {
      wx.navigateTo({
        url: '/pages/mine/address'
      })
    }
  },
  //登录获取3rdsession
  loginNy: function () {
    var that =this;
    wx.login({
      success(res) {
        if (res.code) {
          console.log('code:' + res.code);
          // 发起网络请求
          wx.request({
            url: app.globalData.baseRequestUrl+'/WxLoginController/loginUser',
            data: {
              code: res.code
            },
            success(res) {
              //return 3rd_session = res.data
              wx.setStorageSync('thirdSession', res.data);
              that.getOrderStatusCount();
          }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  },
  onClickOrder : function(e){
    var typeId = e.currentTarget.dataset.typeid;
    var thirdSession = wx.getStorageSync("thirdSession");
    if (thirdSession != '') {
      wx.navigateTo({
        url: '../order/orderList?typeId=' + typeId
      })
    }else{
      Toast("请先点击头像登录");
    }

  },
  // 获取待支付、待发货订单状态下订单的数量，用于页面展示
  getOrderStatusCount: function() {
    var thirdSession = wx.getStorageSync("thirdSession");
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/count',
      method: 'GET',
      header: { 'authorization': thirdSession},
      dataType: 'json',
      success: (res) => {
        if (res.statusCode == 200) {
          this.setData({ orderCount: res.data.data });
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      }
    });
  },/**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var thirdSession = wx.getStorageSync("thirdSession");
    if (thirdSession != '') {
      this.loginNy()
    } else {
      Toast("请先点击头像登录");
    }
    wx.stopPullDownRefresh();
  }
  
})
 