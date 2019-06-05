//app.js
App({
  onLaunch: function() {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    var init = this;
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    var that = this;


    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          this.loginNy();
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              that.globalData.userInfo = res.userInfo;
              that.updateNick(res.userInfo,"old");
              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    //baseRequestUrl: 'http://localhost:80/nanyahuayi'
    baseRequestUrl: 'https://www.ailejia.club/nanyahuayi'
    ,phoneNum  : "18221456048"
  },
  // 拨打电话
  callPhone: function() {
    var myDate = new Date();
    var hours =myDate.getHours();
    
    if (9 < hours < 18) {
      wx.makePhoneCall({
        phoneNumber: this.globalData.phoneNum // 仅为示例，并非真实的电话号码
      })
    }else{
      wx.showToast({
        title: '请在上午九点至晚上六点咨询客服！',
      })
    }
    return "ok"
  },
  loginNy: function() {
    var that = this;
    wx.checkSession({
      success() {
        //session未过期
        console.log("session is not expired")
      },
      fail() {
        // session_key 已经失效，需要重新执行登录流程
        // 重新登录
        wx.login({
          success(res) {
            if (res.code) {
              // 发起网络请求
              wx.request({
                url: that.globalData.baseRequestUrl+'/WxLoginController/loginUser',
                data: {
                  code: res.code
                },
                method: 'GET',
                success(res) {
                  //return 3rd_session = res.data
                  wx.setStorageSync('thirdSession', res.data);
                },
                fail(res) {
                  console.log(res)
                  console.log("shi bai le ")
                }
              })
            } else {
              console.log('登录失败！' + res.errMsg)
            }
          }
        })
      }

    })
  },
  updateNick : function(res,sign){
    var that = this;
    var trdSession = wx.getStorageSync('thirdSession');
    // 发起网络请求
    wx.request({
      url: that.globalData.baseRequestUrl + '/WxLoginController/updateNick',
      data: {
        nickname :  res.nickName, 
        gender : res.gender,
        language: res.language, 
        city : res.city, 
        province: res.province, 
        country: res.country, 
        avatarurl :res.avatarUrl,
        thirdSession : trdSession,
        sign : sign
      },
      method: 'GET'
    })
  }


})