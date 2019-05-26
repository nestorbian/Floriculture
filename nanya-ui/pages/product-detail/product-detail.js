var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    product: {},
    indexNumber: 1,
    actionSheet: {
      show: false
    },
    showChooseNumber: false,
    num: 1
  },

  changeSwiperItem: function(event) {
    this.setData({indexNumber: event.detail.current + 1});
  },

  onClose() {
    this.setData({ actionSheet: { show: false, actions: this.data.actionSheet.actions} });
  },

  popupActionSheet: function(event) {
    this.setData({ actionSheet: { show: true, actions: this.data.actionSheet.actions } });
    console.log(event);
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.productId);
    wx.request({
      url: app.globalData.baseRequestUrl + '/products/' + options.productId,
      method: 'GET',
      dataType: 'json',
      success: (res) => {
        if (res.statusCode == 200) {
          var comments = res.data.data.comments;
          for (let index in comments) {
            comments[index].imageUrls = this.removeEmptyArrayEle(comments[index].imageUrls.split(","));
          };
          this.setData({ product: res.data.data});
          console.log(this.data.product)
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
  buy: function() {
    this.setData({ showChooseNumber: true });
  },
  onClose: function() {
    this.setData({ showChooseNumber: false });
  },
  finish: function() {
    var thirdSession = wx.getStorageSync('thirdSession');
    if (thirdSession) {
      wx.navigateTo({
        url: '/pages/order/confirm-order?productId=' + this.data.product.productId + '&num=' + this.data.num
      })
    } else {
      Notify('请先登录');
    }
  },
  // 改变购买数量
  onChange: function(event) {
    if (event.detail > this.data.product.productStock) {
      this.setData({ num: this.data.product.productStock });
    } else {
      this.setData({ num: event.detail });
    }
  },
  //去除评论数组中的空元素
  removeEmptyArrayEle: function (arr) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i] == "") {
        arr.splice(i, 1);
        i = i - 1; // i - 1 ,因为空元素在数组下标 2 位置，删除空之后，后面的元素要向前补位，
        // 这样才能真正去掉空元素,觉得这句可以删掉的连续为空试试，然后思考其中逻辑
      }
    }
    return arr;
  }
})