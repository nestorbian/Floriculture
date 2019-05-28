import Notify from '../../dist/notify/notify';
import Toast from '../../dist/toast/toast';
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    currtab: 0,
    swipertab: [
      {
        typeId: 0,
        name: '全部'
      },
      {
        typeId: 1,
        name: '待付款'
      },
      {
        typeId: 2,
        name: '待发货'
      },
      {
        typeId: 3,
        name: '待收货'
      },
      {
        typeId: 4,
        name: '待评价'
      },
      {
        typeId: 5,
        name: '售后'
      }],
      pageNumber: 1,
      pageSize: 3,
      totalPages: 1,
      orderList: [],
      isShowTip: false,
      typeId: 0,
      isShowLoading: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({ typeId: options.typeId });
    this.loadData(options.typeId);
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
  * @Explain：选项卡点击切换
  */
  tabSwitch: function (e) {
    this.setData({ typeId: e.target.dataset.current, pageNumber: 1});
    this.loadData(e.target.dataset.current);
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
  // 获取订单列表
  getOrderList: function(orderStatus) {
    wx.request({
      url: app.globalData.baseRequestUrl + '/orders/' + orderStatus,
      header: {"authorization": wx.getStorageSync("thirdSession")},
      data: { pageNumber: this.data.pageNumber, pageSize: this.data.pageSize},
      dataType: 'json',
      method: 'GET',
      success: (res) => {
        if (res.statusCode == 200) {
          console.log(res.data.data.content);
          this.setData({ orderList: res.data.data.content, pageNumber: res.data.data.number + 1, totalPages: res.data.data.totalPages});
          if (this.data.orderList.length == 0) {
            this.setData({ isShowTip: true });
          }
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      }
    })
  },
  // 评价
  comment: function(event) {
    wx.navigateTo({
      url: '../comment/comments?orderId=' + event.currentTarget.dataset.orderid
    });
  },
  // 继续支付
  continuePay: function(event) {
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
  contactService: function() {
    app.callPhone();
  },
  // 取消订单
  cancelOrder: function(event) {
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
      currtab: typeId, isShowTip: false
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
  confirmReceive: function(event) {
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
  //跳转到“订单详情”页面
  orderDetail : function(e){
    wx.navigateTo({
      url: "../order/order-detail?orderId=" + e.currentTarget.dataset.orderid
    })
  },
  loadMoreData: function() {
    if (!this.data.isShowLoading) {
      if (this.data.pageNumber < this.data.totalPages) {
        this.setData({ isShowLoading: true });
        var orderStatus;
        switch (parseInt(this.data.typeId)) {
          case 0: orderStatus = 'ALL'; break;
          case 1: orderStatus = 'PENDING_PAY'; break;
          case 2: orderStatus = 'PENDING_DELIVERY'; break;
          case 3: orderStatus = 'PENDING_RECEIVE'; break;
          case 4: orderStatus = 'PENDING_COMMENT'; break;
          case 5: orderStatus = 'PENDING_REFUND'; break;
        }
        wx.request({
          url: app.globalData.baseRequestUrl + '/orders/' + orderStatus,
          header: { "authorization": wx.getStorageSync("thirdSession") },
          data: { pageNumber: this.data.pageNumber + 1, pageSize: this.data.pageSize },
          dataType: 'json',
          method: 'GET',
          success: (res) => {
            this.setData({ isShowLoading: false });
            if (res.statusCode == 200) {
              console.log(res.data.data);
              var originList = this.data.orderList;
              var appendList = res.data.data.content;
              console.log(res.data.data.content);
              if (appendList.length == 0) {
                Toast('我是有底线的~');
              } else {
                appendList.forEach(item => originList.push(item));
                this.setData({ orderList: originList, pageNumber: res.data.data.number + 1, totalPages: res.data.data.totalPages });
              }
            } else {
              Notify('网络错误');
            }
          },
          fail: (res) => {
            this.setData({ isShowLoading: false });
            console.log(res);
          }
        });
      } else {
        Toast('我是有底线的~');
      }
    }
  }
})

