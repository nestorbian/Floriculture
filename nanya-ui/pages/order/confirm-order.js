var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: '',
    dateRang: ['上午（8：00-12：00）', '下午（14：00-18：00）'],
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
    address: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.productId);
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
    var addressId = wx.getStorageSync('addressId');
    if (addressId) {
      wx.request({
        url: app.globalData.baseRequestUrl + '/addressController/getAddress',
        method: 'GET',
        data: { id: addressId },
        dataType: 'json',
        success: (res) => {
          if (res.statusCode == 200) {
            this.setData({ address: res.data.data });
            console.log(this.data.address);
          } else {
            Notify('网络错误');
          }
        },
        fail: (res) => {
          console.log(res);
        }
      })
    }
    console.log("addressId: " + addressId);
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
    this.setData({ remarkNumber: event.detail.value.length });
  },
  countLeaveMessage: function(event) {
    this.setData({ leaveMessageNumber: event.detail.value.length});
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
  }
})