// pages/comment/comment.js
import regeneratorRuntime from '../lib/regenerator-runtime/runtime.js';
import Toast from '../../dist/toast/toast';
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    value: 5,
    tfps: null,
    text: '',
    fileUrls: '',
    orderId: '',
    attitude: true,
    time: true,
    efficiency: true,
    environment: true,
    professional: true,
    currentWordNumber:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var orderId = options.orderId;
    this.setData({
      orderId: orderId
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
  onChange(event) {
    this.setData({
      value: event.detail
    });
  },
  onPhoto: function () {
    var that = this;
    wx.chooseImage({
      count: 5,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success(res) {
        // tempFilePath可以作为img标签的src属性显示图片
        that.setData({
          tfps: res.tempFilePaths
        })
      }
    })
  },
  onBlur: function (e) {
    this.setData({
      text: e.detail.value
    })
  },
  onClear: function () {
    this.setData({
      tfps: null
    })
  },
  //点击提交按钮
  onCommit: function () {
    var that = this;

    if (that.data.value != '' || that.data.tfps != null) {
      //提示加载中
      Toast.loading({
        mask: true,
        message: '加载中...'
      });
      //一张图片上传
      //this.upImage();
      //多张图片上传
      this.uploadImage().then(res => { this.commitFile(res) }).then(res => { wx.switchTab({ url: '../find/find' }) });
    }
  },
  //上传图片到服务器
  uploadImage: function () {
    var that = this;
    return new Promise(function (resolve, reject) {
      var len = that.data.tfps.length;
      //图片路径的容器
      var fUrls = new Array(len);
      var successFlag = 0;
      for (var i = 0; i < len; i++) {
        wx.uploadFile({
          url: app.globalData.baseRequestUrl + '/admin/images/one', // 仅为示例，非真实的接口地址
          filePath: that.data.tfps[i],
          name: "image",
          formData: {
            type: 'comment'
          },
          header: {
            "Content-Type": "multipart/form-data"
          },
          success(res) {
            // do something
            //fUrls = fUrls + JSON.parse(res.data).data.imageUrl + ";";
            fUrls.push(JSON.parse(res.data).data.imageUrl);

            that.setData({
              fileUrls: fUrls.toString()
            })
            successFlag++;
            if (successFlag == len) {
              console.log("success")
              resolve(res)
            }
          }, fail() {
            reject();
            console.log("fail")
          }
        })
      }
    })
  },
  //将评论信息上传到数据库
  commitFile: function (e) {
    var that = this;
    var thirdSession = wx.getStorageSync('thirdSession');
    return new Promise(function (resolve, reject) {
      wx.request({
        url: app.globalData.baseRequestUrl + '/CommentsController/addComments', // 仅为示例，并非真实的接口地址
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          value: that.data.value,
          fileUrls: that.data.fileUrls,
          orderId: that.data.orderId,
          text: that.data.text,
          thirdSession: thirdSession
        },
        success(res) {
          console.log(res);
          resolve(res)
        }, fail() {
          reject();
        }
      })
    })

  },
  // 标签
  label: function (e) {
    var that = this;
    that.setData({
      [e.currentTarget.id]: !e.currentTarget.dataset.index,
      text: this.data.text + e.currentTarget.id
    })
  },
  // 删除图片
  deleteImg: function (e) {
    var tfps = this.data.tfps;
    var index = e.currentTarget.dataset.index;
    tfps.splice(index, 1);
    this.setData({
      tfps: tfps
    });
  },
  // 预览图片
  previewImg: function (e) {
    //获取当前图片的下标
    var index = e.currentTarget.dataset.index;
    //所有图片
    var tfps = this.data.tfps;
    wx.previewImage({
      //当前显示图片
      current: tfps[index],
      //所有图片
      urls: tfps
    })
  },
  // 留言
  //字数限制  
  inputs: function (e) {
    // 获取输入框的内容
    var value = e.detail.value + "[" + this.data.text+"]" ;
    // 获取输入框内容的长度
    var len = parseInt(value.length);
    //最多字数限制
    if (len > 60) return;
    // 当输入框内容的长度大于最大长度限制（max)时，终止setData()的执行
    this.setData({
      currentWordNumber: len //当前字数  
    });
  }
})