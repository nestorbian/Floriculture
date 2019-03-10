Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgUrls: [
      '../../images/demo1.jpg',
      '../../images/demo1.jpg',
      '../../images/demo1.jpg'
    ],
    categoryUrl: 'http://img02.hua.com/slider/18_birthday_pc.jpg',
    productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg'
  },

  viewProductDetail: function(event) {
    wx.navigateTo({
      url: '/pages/product-detail/product-detail',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
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
    
  }
})