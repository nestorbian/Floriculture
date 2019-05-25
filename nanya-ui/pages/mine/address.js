// pages/mine/address.js
import Toast from '../../dist/toast/toast';
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    address: [],
    delBtnWidth: 180

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // var that = this;
    // wx.request({
    //   url: app.globalData.baseRequestUrl+'/addressController/getAllAddress', // 仅为示例，并非真实的接口地址
    //   data: {
    //     thirdSession: wx.getStorageSync('thirdSession')
    //   },
    //   header: {
    //     'content-type': 'application/json' // 默认值
    //   },
    //   success(res) {
    //     that.setData({
    //       address : res.data
    //     })
    //   }
    // })
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
    var that = this;
    wx.request({
      url: app.globalData.baseRequestUrl+'/addressController/getAllAddress', // 仅为示例，并非真实的接口地址
      data: {
        thirdSession: wx.getStorageSync('thirdSession')
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        that.setData({
          address: res.data
        })
      }
    })
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
  onAdd : function(){
    wx.navigateTo({
      url: '/pages/mine/edit_address'
    })
  },

  edit: function (e) {
    //编辑收货地址
    wx.navigateTo({
      url: '/pages/mine/edit_address?id='+e.currentTarget.dataset.id
    })
  },


  delItem: function (e) {
    var id = e.currentTarget.dataset.id;
    var index = e.currentTarget.dataset.index;
    var list = this.data.address;
    
    var that = this;
    wx.request({
      url: app.globalData.baseRequestUrl+'/addressController/delAddress', // 仅为示例，并非真实的接口地址
      data: {
        id: id
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        Toast(res.data);
        if(res.data == "ok"){
          //SPLICE(INDEX,个数，数组元素)用提删除、替换、修
          list.splice(index,1);
          that.setData({
            address : list
          })
     
        }
      }
    })
  },

  touchS: function (e) {
    if (e.touches.length == 1) {
      this.setData({
        //设置触摸起始点水平方向位置
        startX: e.touches[0].clientX
      });
    }
  },

  touchM: function (e) {
    if (e.touches.length == 1) {
      //手指移动时水平方向位置
      var moveX = e.touches[0].clientX;
      //手指起始点位置与移动期间的差值
      var disX = this.data.startX - moveX;
      var delBtnWidth = this.data.delBtnWidth;
      var city = "";
      if (disX == 0 || disX < 0) {//如果移动距离小于等于0，文本层位置不变
        city = "left:0rpx";
      } else if (disX > 0) {//移动距离大于0，文本层left值等于手指移动距离
        city = "left:-" + disX + "rpx";
        if (disX >= delBtnWidth) {
          //控制手指移动距离最大值为删除按钮的宽度
          city = "left:-" + delBtnWidth + "rpx";
        }
      }
      //获取手指触摸的是哪一项
      var index = e.currentTarget.dataset.index;
      var list = this.data.address;
      list[index]['city'] = city;
      //更新列表的状态
      this.setData({
        address: list
      });
    }
  },
  touchE: function (e) {
    if (e.changedTouches.length == 1) {
      //手指移动结束后水平位置
      var endX = e.changedTouches[0].clientX;
      //触摸开始与结束，手指移动的距离
      var disX = this.data.startX - endX;
      var delBtnWidth = this.data.delBtnWidth;
      //如果距离小于删除按钮的1/2，不显示删除按钮
      var city = disX > delBtnWidth / 2 ? "left:-" + delBtnWidth + "rpx" : "left:0rpx";
      //获取手指触摸的是哪一项
      var index = e.currentTarget.dataset.index;
      var list = this.data.address;
      var del_index = '';
      disX > delBtnWidth / 2 ? del_index = index : del_index = '';
      list[index].city = city;
      //更新列表的状态
      this.setData({
        address: list,
        del_index: del_index
      });
    }
  }
})