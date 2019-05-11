// pages/search/search.js
import Toast from '../../dist/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    product :[]
    ,dis : "none"
    ,value:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var values = options.value;
    if (values == null) {
      values = ''
    }
    this.setData({
      value: values
    })
    this.search(values);
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
  search : function(values,order){
    var that =this;
    if(values == null){
      values =''
    }


    //提示加载中
    Toast.loading({
      mask: true,
      message: '加载中...'
    });
    wx.request({
      url: 'http://127.0.0.1:80/nanyahuayi/searchProduct', // 仅为示例，并非真实的接口地址
      data: {
        value: values,
        order : "3"
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        var t = typeof(res.data.true);
        var f = typeof (res.data.false);
        //关闭提示
        Toast.clear();

        if (t != 'undefined') {
          var pList = res.data.true;
          that.setData({
            product: pList
            ,dis : "none"
          })
        }
        if (f != 'undefined') {
          var pLists = res.data.false;
          that.setData({
            product: pLists
            ,dis : "block"
          })
        }
      }
    })
  },
  //搜索按钮
  onClick : function(e){
    this.setData({
      value :e.detail
    })
    this.search(e.detail)
  },
  //排序
  onSort : function(e){
    var order = e.detail.index;
    var values = this.data.value;


    this.search(values,order);
  },
  onHref : function(e){
    //商品编号
    var productId = e.target.id;
    wx.navigateTo({
      url: '../product-detail/product-detail?productId='+productId
    })
  }
})