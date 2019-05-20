import Toast from '../../dist/toast/toast';
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: '',
    dateRang: ['上午（8：00-12：00）', '下午（14：00-18：00）', '晚上（18：00-21：00）'],
    currentDate: null,
    index: null,
    remarkNumber: 0,
    leaveMessageNumber: 0,
    isShowLabel: false,
    labelArray: [],
    birthdayLabel: false,
    commemorationLabel: false,
    festivalLabel: false,
    greetLabel: false,
    congratulationLabel: false,
    address: null,
    product: null,
    num: 1,
    remark: '',
    leaveMessage: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.productId);
    wx.request({
      url: app.globalData.baseRequestUrl + '/products/single-image/' + options.productId,
      dataType: 'json',
      method: 'GET',
      success: (res) => {
        if (res.statusCode == 200) {
          this.setData({ product: res.data.data });
        } else {
          Notify('网络错误');
        }
      },
      fail: (res) => {
        console.log(res);
      }
    })
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
    // 获取当前日期
    var now = new Date();
    this.setData({ currentDate: now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate()});
    // 获取地址信息
    wx.request({
      url: app.globalData.baseRequestUrl + '/user-addresses',
      method: 'GET',
      header: {
        'authorization': wx.getStorageSync('thirdSession')
      },
      dataType: 'json',
      success: (res) => {
        if (res.statusCode == 200) {
          var userAddress = res.data.data;
          if (userAddress) {
            wx.request({
              url: app.globalData.baseRequestUrl + '/addressController/getAddress',
              method: 'GET',
              data: { id: userAddress.addressId },
              dataType: 'json',
              success: (res) => {
                if (res.statusCode == 200) {
                  this.setData({ address: res.data.data });
                } else {
                  Notify('网络错误');
                }
              },
              fail: (res) => {
                console.log(res);
              }
            })
          }
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
  bindDateChange: function(event) {
    this.setData({date: event.detail.value});
  },
  bindPickerChange: function(event) {
    this.setData({ index: event.detail.value });
  },
  countRemark: function (event) {
    this.setData({ remarkNumber: event.detail.value.length, remark: event.detail.value });
  },
  countLeaveMessage: function(event) {
    this.setData({ leaveMessageNumber: event.detail.value.length, leaveMessage: event.detail.value});
  },
  onClose: function(event) {
    this.setData({ isShowLabel: false });
  },
  showLabel: function() {
    this.setData({isShowLabel:true});
  },
  closeLabel: function() {
    this.setData({ birthdayLabel: false, commemorationLabel: false, festivalLabel: false, greetLabel: false, congratulationLabel: false});
    if (this.data.labelArray.includes('生日')) {
      this.setData({ birthdayLabel: true});
    }
    if (this.data.labelArray.includes('纪念日')) {
      this.setData({ commemorationLabel: true });
    }
    if (this.data.labelArray.includes('节日')) {
      this.setData({ festivalLabel: true });
    }
    if (this.data.labelArray.includes('问候')) {
      this.setData({ greetLabel: true });
    }
    if (this.data.labelArray.includes('祝贺')) {
      this.setData({ congratulationLabel: true });
    }
    this.setData({ isShowLabel: false });
  },
  setLabel: function(event) {
    var label = event.currentTarget.dataset.label;
    switch (label) {
      case '生日': this.setData({ birthdayLabel: !this.data.birthdayLabel});break;
      case '纪念日': this.setData({ commemorationLabel: !this.data.commemorationLabel });break;
      case '节日': this.setData({ festivalLabel: !this.data.festivalLabel }); break;
      case '问候': this.setData({ greetLabel: !this.data.greetLabel }); break;
      case '祝贺': this.setData({ congratulationLabel: !this.data.congratulationLabel }); break;
    }
  },
  finish: function() {
    var array = [];
    if (this.data.birthdayLabel) {
      array.push("生日");
    }
    if (this.data.commemorationLabel) {
      array.push("纪念日");
    }
    if (this.data.festivalLabel) {
      array.push("节日");
    }
    if (this.data.greetLabel) {
      array.push("问候");
    }
    if (this.data.congratulationLabel) {
      array.push("祝贺");
    }
    this.setData({ labelArray: array, isShowLabel: false});
  },
  chooseAddress: function() {
    wx.navigateTo({
      url: './choose-address',
    });
  },
  // 提交订单
  onSubmit: function() {
    // 参数验证
    if (!this.data.address) {
      Toast.fail('请选择收货地址');
    } else if (!this.data.date) {
      Toast.fail('请选择配送日期');
    } else if (this.data.index == null) {
      Toast.fail('请选择时间段');
    } else if (!this.data.product) {
      Toast.fail('请选择商品');
    } else {
      wx.request({
        url: app.globalData.baseRequestUrl + '/orders',
        method: 'POST',
        header: {
          'authorization': wx.getStorageSync('thirdSession'),
          'content-type': 'application/json;charset=utf8'
        },
        data: {
          addressId: this.data.address.id,
          productId: this.data.product.productId,
          buyAmount: this.data.num,
          expectedDeliveryTime: this.data.date + ' ' + this.data.dateRang[this.data.index],
          label: this.data.labelArray.length == 0 ? null : this.data.labelArray.join(','),
          remark: this.data.remark ? this.data.remark : null,
          leaveMessage: this.data.leaveMessage ? this.data.leaveMessage : null
        },
        dataType: 'json',
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
                })
              },
              'fail': (result) => {
                // TODO 跳转到待支付页面
                console.log("用户取消支付");
              }
            })
          } else {
            Notify('网络错误');
          }
        },
        fail: (res) => {
          console.log(res);
        }
      })
    }
  }
})