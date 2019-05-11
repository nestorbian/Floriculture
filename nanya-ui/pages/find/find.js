Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:['aa','aa','aa','aa','aa','','','','','','','','','','',''],
    page :'1',
    pageSize : '60',
    hasMore : true,
    comList:[],
    srcHead:'https://www.ailejia.club'//图片网址
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: 'http://127.0.0.1:80/nanyahuayi/CommentsController/findComment', // 仅为示例，并非真实的接口地址
      data: {
        page: this.data.page,
        pageSize: this.data.pageSize
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        that.setData({
          comList : res.data
        })
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
  onClick: function(e){
    var productId = e.currentTarget.id.replace(/^\s*|\s*$/g, "");;
    wx.navigateTo({
      url: '../product-detail/product-detail?productId=' + productId
    })
  }
})