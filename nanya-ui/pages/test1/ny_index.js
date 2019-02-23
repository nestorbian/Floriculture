// pages/test1/ny_index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUse : wx.canIUse('button.open-type.getUserInfo')
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onload : function (options) {
    wx.checkSession({
      sucess(){
        //session未过期
      },
      fail() {
        // session_key 已经失效，需要重新执行登录流程
        // 重新登录
        wx.login({
          success(res) {
            if (res.code) {
              // 发起网络请求
              wx.request({
                url: 'http://127.0.0.1:80/nanyahuayi/WxLoginController/loginUser',
                data: {
                  code: res.code
                },
                sucess(res){
                  //return 3rd_session = res.data
                  wx.setStorageSync('thirdSession', res.data)
                },
                fail(res){
                   console.log("woshinibab")
                }
              })
            } else {
              console.log('登录失败！' + res.errMsg)
            }
          }
        })
      },
      
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
  loginWx : function(){
    console.log("nihao")
    wx.checkSession({
      fail() {
        console.log("checksession success");  //session未过期
      },
      success() {
        // session_key 已经失效，需要重新执行登录流程
        // 重新登录
        console.log("checksession fail")
        wx.login({
          success(res) {
            console.log("login success1")
            if (res.code) {
              // 发起网络请求
              wx.request({
                url: 'http://127.0.0.1:80/nanyahuayi/WxLoginController/loginUser',
                data: {
                  code: res.code
                },
                success(res) {
                  console.log("login success")
                  console.log(res.data)
                },
                fail(res) {
                  console.log("woshinibab")
                }
              })
            } else {
              console.log('登录失败！' + res.errMsg)
            }
          },
          fail(){
            console.log("我是航仔3")
          }
        })
      }
    })
  }

})