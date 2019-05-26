// pages/order/order-detail.js
var app = getApp();

Page({
  data: {
    orderId: '',
    orderData: {}
  },
  onLoad: function (options) {
    this.setData({
      // orderId: options.orderId,
      orderId: '40288b156aef06bd016aef09cdd90000'
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
  onclick : function(res){
    console.log(res);
    var url ='';
    var id = res.currentTarget.id;
    if(id == 'pay'){
      url = '支付路径'
    } else if (id == 'confirm'){
      url = '确认收货路径'
    } else if (id == 'comment'){
      url = '../comment/comments'  //评论路径
      wx.navigateTo({
        url: url + '?orderId=' + this.data.orderId
      })
    } else if (id == 'refund'){
      url = '申请退款路径'
    }else {
      url ='#'
    }
  },
  onPrompt:function(){
    wx.showToast({
      title: '已提醒商家发货，请耐心等待！',
      icon: 'success',
      duration: 2000
    })
  },
  onConfirm: function (url , data ,) {
    var that = this;
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/confirm-product',
      data: {
        id: that.data.orderId
      },
      header: {
        'content-type': 'application/json' // 默认值
        ,'authorization': wx.getStorageSync('thirdSession')

      },
      success: function (res) {

        // var status = res.data.status;
        // if (status == 0) {
        //   var order = res.data.data;
        //   that.setData({
        //     orderData: order
        //   });
        // } else {
        //   wx.showToast({
        //     title: res.data.err,
        //     duration: 2000
        //   });
        // }
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  }
})