// pages/wuliu/wuliu.js
var util =require('../../utils/md5.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    wuLiu : []

  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    //物流单号
    var num = options.trackingNumber;
    //收件人手机号
    var phone = options.phoneNumber;


    var customer = '1D8BAC25EE2166D21E16A2885561D76B';
    var com = 'shunfeng';
    var param = "{\"com\":\"" + com + "\",\"num\":\"" + num + "\",\"phone\":\"" + phone + "\"}";
    var key = 'OISRgocN8714';
    var md5 = param + key + customer;
    var sign = util.hex_md5(md5).toUpperCase();

    wx.request({
      url: 'https://poll.kuaidi100.com/poll/query.do', // 仅为示例，并非真实的接口地址
      data: {
        customer: customer,
        sign: sign,
        param: param
      },
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success(res) {
        console.log(res.data.data);
        if (typeof (res.data.data) != 'undefined') {
          that.setData({
            wuLiu: res.data.data[0].data
          })
        }
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
  // kuaidibao : function(){
  //   var that = this;
  //   // var temp =Math.random();
  //   var timestamp = new Date().getTime();
  //   var api_key = 'be21e3a628835e8f2bde0194c134efac4d58905e';
  //   var md5 = '103021' + 'express.info.get' + timestamp + api_key;
  //   var sign = util.hex_md5(md5);
  //   console.log(sign)

  //   wx.request({
  //     url: 'https://kop.kuaidihelp.com/api', // 仅为示例，并非真实的接口地址
  //     data: {
  //       app_id: '103021',
  //       method: 'express.info.get',
  //       ts: timestamp,
  //       data: '{ "waybill_no":"254939750997", "exp_company_code":"sf","result_sort":"0"}',
  //       sign: sign
  //     },
  //     method: 'POST',
  //     header: {
  //       'cache-control': 'no-cache',
  //       'content-type': 'application/x-www-form-urlencoded'
  //     },
  //     success(res) {
  //       console.log(res.data);
  //       if (typeof (res.data.data[0]) != 'undefined') {
  //         that.setData({
  //           wuLiu: res.data.data[0].data
  //         })
  //         // console.log(that.data.wuLiu)
  //       }
  //     }
  //   })
  // },
  kuaidi100 : function(){
    var that = this;
    // var temp =Math.random();
    var customer = '1D8BAC25EE2166D21E16A2885561D76B';
    var com = 'shunfeng';
    var num ='254939750997';
    var phone = '18221457621';
    var param = "{\"com\":\"" + com + "\",\"num\":\"" + num + "\",\"phone\":\""+phone+"\"}";
    var key = 'OISRgocN8714';
    var md5 = param+key+customer;
    var sign = util.hex_md5(md5);

    wx.request({
      url: 'https://poll.kuaidi100.com/poll/query.do', // 仅为示例，并非真实的接口地址
      data: {
        customer: customer,
        param: param,
        sign: sign
      },
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success(res) {
        if (typeof (res.data.data[0].data) != undefined) {
          that.setData({
            wuLiu: res.data.data[0].data
          })
          // console.log(that.data.wuLiu)
        }
      }
    })
  }
})