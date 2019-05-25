// pages/comment/comment.js
import regeneratorRuntime from '../lib/regenerator-runtime/runtime.js';
import Toast from '../../dist/toast/toast';
var app =getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    value: 5,
    tfps : null,
    productId:'9d02a5d91d9c47ddbff21ace91636b2b',
    text :'',
    fileUrls:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // var proIds = options.productId;
    // this.setData({
    //   productId : proIds
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
  onPhoto: function(){
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
  onBlur : function(e){
    this.setData({
      text : e.detail.value
    })
  },
  onClear : function(){
    this.setData({
      tfps : null
    })
  },
  //点击提交按钮
  onCommit : function(){
    var that =this;

    if (that.data.value != '' || that.data.tfps!= null){
      //提示加载中
      Toast.loading({
        mask: true,
        message: '加载中...'
      });
      //一张图片上传
      //this.upImage();
      //多张图片上传
      //this.init();
      this.uploadImage().then(res=>{this.commitFile(res)});
    }  
  },
  //上传图片到服务器
  uploadImage: function () {
    var that = this;
    return new Promise(function (resolve, reject) {
      var len = that.data.tfps.length;
      //图片路径的容器
      var fUrls =new Array(len);
      var successFlag= 0;
      console.log(len)
      for (var i = 0; i < len; i++) {
        wx.uploadFile({
          url: app.globalData.baseRequestUrl+'/admin/images/one', // 仅为示例，非真实的接口地址
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
            console.log("res.data= "+ res.data)
            console.log("json = "+ JSON.parse(res.data))
            that.setData({
              fileUrls: fUrls.toString()
            })
            successFlag++;
            if (successFlag == len) {
              console.log("success")              
              resolve(res)
            }
          },fail(){
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
    console.log("commitFile")
    return new Promise(function (resolve, reject) {
      wx.request({
        url: app.globalData.baseRequestUrl+'/CommentsController/addComments', // 仅为示例，并非真实的接口地址
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          value: that.data.value,
          fileUrls: that.data.fileUrls,
          productId: that.data.productId,
          text: that.data.text,
          thirdSession: thirdSession
        },
        success(res) {
          console.log(res);
          resolve(res)
        },fail(){
          reject();
        }
      })
    })

  },
  async init() {
    await this.uploadImage();
    await this.commitFile();
  },
  //上传单张图片到服务器
  upImage: function () {
    var that = this;
    var thirdSession = wx.getStorageSync('thirdSession')
    var fPath ='';
    if (that.data.tfps != null){
      fPath = that.data.tfps[0];
      wx.uploadFile({
        url: app.globalData.baseRequestUrl+'/CommentsController/addComment', // 仅为示例，非真实的接口地址
        filePath: fPath,
        name: "image",
        formData: {
          type: 'comment',
          value: that.data.value,
          productId: that.data.productId,
          text: that.data.text,
          thirdSession: thirdSession
        },
        header: {
          "Content-Type": "multipart/form-data"
        },
        success(res) {
          Toast(res.data);
          // wx.switchTab({
          //   url: '/pages/find/find'
          // })
        }
      })
    }else{
      Toast("请和照片一起上传哦！");
    }
  }
})