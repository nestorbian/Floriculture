Page({

  /**
   * 页面的初始数据
   */
  data: {
    mainCategoryActive: 0,
    mainCategoryActiveForSkip: 0,
    categories:[
      {
        categoryName:'情人节告白系列',
        products:[
          {
            productName:'浓情蜜意',
            description:'99枝戴安娜',
            price:899,
            productImages:[
              { productUrl:'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3406300341,2036134685&fm=15&gp=0.jpg'}
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          }
        ]
      },
      {
        categoryName: '网红款系列',
        products: [
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          }
        ]
      }, {
        categoryName: '水晶之恋-定制系列',
        products: [
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          },
          {
            productName: '浓情蜜意',
            description: '99枝戴安娜',
            price: 899,
            productImages: [
              { productUrl: 'http://img01.hua.com/uploadpic/newpic/201801162043054106.jpg' }
            ]
          }
        ]
      }
    ]
  },
  selectCategory:function(event) {
    this.setData({ mainCategoryActive: event.currentTarget.dataset.index, mainCategoryActiveForSkip: event.currentTarget.dataset.index});
    console.log(event);
  },
  scroll: function(event) {
    this.setData({ mainCategoryActive: this.getCategoryActive(event.detail.scrollTop) });
  },
  getCategoryActive: function(scrollTop) {
    var categoryNameHeight = 36;
    var productHeight = 122;
    var totalHeight = 0;
    for (var i = 0; i < this.data.categories.length; i ++) {
      totalHeight += categoryNameHeight + productHeight * this.data.categories[i].products.length;
      if (scrollTop < totalHeight) {
        return i;
      }
    }
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