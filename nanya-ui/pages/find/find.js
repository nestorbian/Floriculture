var app = getApp();
import Toast from '../../dist/toast/toast';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:['aa','aa','aa','aa','aa','','','','','','','','','','',''],
    page : 0,
    pageSize : 15,
    hasMore : true,
    comList:[],
    srcHead:'https://www.ailejia.club'//图片网址
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getComList(this.data.page);
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
    var pages =0;
    this.setData({
      comList : []
    })
    this.getComList(pages);
  },

  /**
   * 页面上拉触底事件的处理函数
   *///潘家工商局电话：  85803082
  onReachBottom: function () {
    //分页查询
    var pages = this.data.page+this.data.pageSize;
    if (this.data.hasMOre) {
      this.getComList(pages)
    }else{
      Toast("没有更多了哦(*^▽^*)")
    }
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
  }, 
  //去除评论数组中的空元素
  removeEmptyArrayEle : function (arr){
        for(var i = 0; i<arr.length; i++) {
          if (arr[i] == "") {
            arr.splice(i, 1);
            i = i - 1; // i - 1 ,因为空元素在数组下标 2 位置，删除空之后，后面的元素要向前补位，
            // 这样才能真正去掉空元素,觉得这句可以删掉的连续为空试试，然后思考其中逻辑
          }
        }
        return arr;
  },
  getComList : function(pages){
    var that = this;
    //提示加载中
    Toast.loading({
      mask: true,
      message: '美好即将呈现...'
    });
    wx.request({
      url: app.globalData.baseRequestUrl + '/CommentsController/findComment', // 仅为示例，并非真实的接口地址
      data: {
        page: pages,
        pageSize: that.data.pageSize
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        var cList = res.data;
        for (let index in res.data) {
          cList[index].imageUrls = that.removeEmptyArrayEle(res.data[index].imageUrls.split(","));
        };
        
        // 分页查询 拼接前后列表
        var comList = that.data.comList;
        comList.push.apply(comList, cList);
        var hasMoreData = true;
        if(cList.length < that.data.pageSize){
          hasMoreData = false;
        }

        that.setData({
          comList: comList
          ,hasMore : hasMoreData
          ,page : pages
        })

        Toast.clear();
      }
    })
  },
  // 预览图片
  previewImg: function (e) {
    //获取当前图片的下标
    var index = e.currentTarget.dataset.index;
    console.log(e)
    var findex =e.currentTarget.dataset.findex;
    //所有图片
    var pics = this.data.comList[findex].imageUrls;
    wx.previewImage({
      //当前显示图片
      current: pics[index],
      //所有图片
      urls: pics
    })
  }
})