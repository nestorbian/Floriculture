// pages/order/order-detail.js
import Notify from '../../dist/notify/notify';
var app = getApp();
Page({
  data: {
    orderId: '',
    orderData: {}
    ,show : false
  },
  onLoad: function (options) {
    this.setData({
      orderId: options.orderId
    })
    this.loadProductDetail();
  },
  loadProductDetail: function () {
    var that = this;
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/detail',
      data: {
        id: that.data.orderId
      },
      header: {
        'content-type': 'application/json' // 默认值
        ,'authorization': wx.getStorageSync('thirdSession')

      },
      success: function (res) {

        var status = res.data.status;
        if (status == 0) {
          var order = res.data.data;
          that.setData({
            orderData: order
          });
        } else {
          wx.showToast({
            title: res.data.err,
            duration: 2000
          });
        }
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  onPrompt:function(){
    wx.showToast({
      title: '已提醒商家发货，请耐心等待！',
      icon: 'success',
      duration: 2000
    })
  },
  // 评价
  comment: function (event) {
    wx.navigateTo({
      url: '../comment/comments?orderId=' + event.currentTarget.dataset.orderid
    });
  },  // 继续支付
  continuePay: function (event) {
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/continue-pay',
      header: { "authorization": wx.getStorageSync("thirdSession") },
      data: { id: event.currentTarget.dataset.orderid },
      dataType: 'json',
      method: 'GET',
      success: (res) => {
        if (res.statusCode == 200) {
          var preOrderInfo = res.data.data;
          // 调起微信支付控件
          wx.requestPayment({
            'timeStamp': preOrderInfo.timeStamp,
            'nonceStr': preOrderInfo.nonceStr,
            'package': preOrderInfo.package,
            'signType': 'MD5',
            'paySign': preOrderInfo.paySign,
            'success': (result) => {
              console.log("支付成功！");
              wx.navigateTo({
                url: './pay-success',
              });
            },
            'fail': (result) => {
              // TODO 跳转到待支付页面
              console.log("用户取消支付");
            }
          });
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      }
    })
  },
  // 联系客服
  contactService: function () {
    app.callPhone();
  },
  // 取消订单
  cancelOrder: function (event) {
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/cancel-order?id=' + event.currentTarget.dataset.orderid,
      header: { "authorization": wx.getStorageSync("thirdSession") },
      dataType: 'json',
      method: 'PUT',
      success: (res) => {
        if (res.statusCode == 200) {
          this.loadData(this.data.currtab);
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      }
    });
  },
  // 加载数据
  loadData: function (typeId) {
    this.setData({
      currtab: typeId
    });
    switch (parseInt(typeId)) {
      case 0: this.getOrderList('ALL'); break;
      case 1: this.getOrderList('PENDING_PAY'); break;
      case 2: this.getOrderList('PENDING_DELIVERY'); break;
      case 3: this.getOrderList('PENDING_RECEIVE'); break;
      case 4: this.getOrderList('PENDING_COMMENT'); break;
      case 5: this.getOrderList('PENDING_REFUND'); break;
    }
  },
  // 确认收货
  confirmReceive: function (event) {
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/confirm-product?id=' + event.currentTarget.dataset.orderid,
      header: { "authorization": wx.getStorageSync("thirdSession") },
      dataType: 'json',
      method: 'PUT',
      success: (res) => {
        if (res.statusCode == 200) {
          this.loadData(this.data.currtab);
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      }
    });
  },
  //申请退款
  refund: function (event) {
    this.setData({ show: false });
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/apply-refund?id=' + event.currentTarget.dataset.orderid,
      header: { "authorization": wx.getStorageSync("thirdSession") },
      dataType: 'json',
      method: 'PUT',
      success: (res) => {
        if (res.statusCode == 200) {
          wx.navigateTo({
            url: '../order/orderList?typeId=5',
          })
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      }
    });
  },
  //联系客服
  callPhone:function(){
    app.callPhone()
  },
  //弹出callphone界面
  setPopShow:function(){
    this.setData({
      show : true
    })
  },
  wuliu : function(){
    wx.navigateTo({
      url: '../wuliu/wuliu?trackingNumber=' + this.data.orderData.trackingNumber + "&phoneNumber=" + this.data.orderData.phoneNumber,
    })
  }
})