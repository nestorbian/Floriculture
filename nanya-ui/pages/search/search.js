// pages/search/search.js
import Toast from '../../dist/toast/toast';
var app =getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    product :[]
    ,dis : "none"
    ,value:''
    ,isCategory : false
    ,categoryId:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (typeof (options.categoryId) != "undefined") {
      var categoryId = options.categoryId;
      this.searchCategory(categoryId);
      this.setData({
        isCategory : true
        , categoryId: categoryId 
      })
    } else {
      var values = options.value;
      if (values == null) {
        values = ''
      }
      this.setData({
        value: values
      })
      this.search(values);
    }
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
      url: app.globalData.baseRequestUrl+'/searchProduct', // 仅为示例，并非真实的接口地址
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

    if (this.data.isCategory){
      this.searchCategory(this.data.categoryId);
    } else {
      this.search(values, order);
    }
  },
  onHref : function(e){
    //商品编号
    var productId = e.currentTarget.id;
    console.log(e)
    wx.navigateTo({
      url: '../product-detail/product-detail?productId=' + productId
    })
  },
  searchCategory: function (categoryId) {
    var that = this ;
    //提示加载中
    Toast.loading({
      mask: true,
      message: '加载中...'
    });
    wx.request({
      url: app.globalData.baseRequestUrl+'/categories', // 仅为示例，并非真实的接口地址
      data: {
        id: categoryId
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        //产品列表
        var pLists = res.data.data[0].products;
        //产品分类名称
        var pName = res.data.data[0].categoryName;
        
        //关闭提示
        Toast.clear();
        that.setData({
          product: pLists
        })
      }
    })
  },
})