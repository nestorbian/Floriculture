// pages/mine/edit_address.js
import Toast from '../../dist/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    username: null,
    telnum :null,
    location: null,
    detail_add: null,
    province: "上海市",
    city: "上海市",
    country: "静安区",
    thirdSession: null,
    id: null, //地址信息编号
    error: null, //用于手机号码校验 ，error为空时 不展示
    locatPop: false,
    error_name : false,
    error_phone : false,
    error_locate : false,
    error_detail_add : false,
    areaList: {
      province_list: {
        310000: '上海市',
        320000: '江苏省',
        330000: '浙江省'
      },
      city_list: {
        310100: '上海市',
        320100: '南京市',
        320200: '无锡市',
        320300: '徐州市',
        320400: '常州市',
        320500: '苏州市',
        320600: '南通市',
        320700: '连云港市',
        320800: '淮安市',
        320900: '盐城市',
        321000: '扬州市',
        321100: '镇江市',
        321200: '泰州市',
        321300: '宿迁市',
        330100: '杭州市',
        330200: '宁波市',
        330300: '温州市',
        330400: '嘉兴市',
        330500: '湖州市',
        330600: '绍兴市',
        330700: '金华市',
        330800: '衢州市',
        330900: '舟山市',
        331000: '台州市',
        331100: '丽水市'
      },
      county_list: {
        310101: '黄浦区',
        310104: '徐汇区',
        310105: '长宁区',
        310106: '静安区',
        310107: '普陀区',
        310109: '虹口区',
        310110: '杨浦区',
        310112: '闵行区',
        310113: '宝山区',
        310114: '嘉定区',
        310115: '浦东新区',
        310116: '金山区',
        310117: '松江区',
        310118: '青浦区',
        310120: '奉贤区',
        310151: '崇明区',
        320102: '玄武区',
        320104: '秦淮区',
        320105: '建邺区',
        320106: '鼓楼区',
        320111: '浦口区',
        320113: '栖霞区',
        320114: '雨花台区',
        320115: '江宁区',
        320116: '六合区',
        320117: '溧水区',
        320118: '高淳区',
        320205: '锡山区',
        320206: '惠山区',
        320211: '滨湖区',
        320213: '梁溪区',
        320214: '新吴区',
        320281: '江阴市',
        320282: '宜兴市',
        320302: '鼓楼区',
        320303: '云龙区',
        320305: '贾汪区',
        320311: '泉山区',
        320312: '铜山区',
        320321: '丰县',
        320322: '沛县',
        320324: '睢宁县',
        320381: '新沂市',
        320382: '邳州市',
        320391: '工业园区',
        320402: '天宁区',
        320404: '钟楼区',
        320411: '新北区',
        320412: '武进区',
        320413: '金坛区',
        320481: '溧阳市',
        320505: '虎丘区',
        320506: '吴中区',
        320507: '相城区',
        320508: '姑苏区',
        320509: '吴江区',
        320581: '常熟市',
        320582: '张家港市',
        320583: '昆山市',
        320585: '太仓市',
        320590: '工业园区',
        320591: '高新区',
        320602: '崇川区',
        320611: '港闸区',
        320612: '通州区',
        320621: '海安县',
        320623: '如东县',
        320681: '启东市',
        320682: '如皋市',
        320684: '海门市',
        320691: '高新区',
        320703: '连云区',
        320706: '海州区',
        320707: '赣榆区',
        320722: '东海县',
        320723: '灌云县',
        320724: '灌南县',
        320803: '淮安区',
        320804: '淮阴区',
        320812: '清江浦区',
        320813: '洪泽区',
        320826: '涟水县',
        320830: '盱眙县',
        320831: '金湖县',
        320890: '经济开发区',
        320902: '亭湖区',
        320903: '盐都区',
        320904: '大丰区',
        320921: '响水县',
        320922: '滨海县',
        320923: '阜宁县',
        320924: '射阳县',
        320925: '建湖县',
        320981: '东台市',
        321002: '广陵区',
        321003: '邗江区',
        321012: '江都区',
        321023: '宝应县',
        321081: '仪征市',
        321084: '高邮市',
        321090: '经济开发区',
        321102: '京口区',
        321111: '润州区',
        321112: '丹徒区',
        321181: '丹阳市',
        321182: '扬中市',
        321183: '句容市',
        321202: '海陵区',
        321203: '高港区',
        321204: '姜堰区',
        321281: '兴化市',
        321282: '靖江市',
        321283: '泰兴市',
        321302: '宿城区',
        321311: '宿豫区',
        321322: '沭阳县',
        321323: '泗阳县',
        321324: '泗洪县',
        330102: '上城区',
        330103: '下城区',
        330104: '江干区',
        330105: '拱墅区',
        330106: '西湖区',
        330108: '滨江区',
        330109: '萧山区',
        330110: '余杭区',
        330111: '富阳区',
        330112: '临安区',
        330122: '桐庐县',
        330127: '淳安县',
        330182: '建德市',
        330203: '海曙区',
        330205: '江北区',
        330206: '北仑区',
        330211: '镇海区',
        330212: '鄞州区',
        330213: '奉化区',
        330225: '象山县',
        330226: '宁海县',
        330281: '余姚市',
        330282: '慈溪市',
        330302: '鹿城区',
        330303: '龙湾区',
        330304: '瓯海区',
        330305: '洞头区',
        330324: '永嘉县',
        330326: '平阳县',
        330327: '苍南县',
        330328: '文成县',
        330329: '泰顺县',
        330381: '瑞安市',
        330382: '乐清市',
        330402: '南湖区',
        330411: '秀洲区',
        330421: '嘉善县',
        330424: '海盐县',
        330481: '海宁市',
        330482: '平湖市',
        330483: '桐乡市',
        330502: '吴兴区',
        330503: '南浔区',
        330521: '德清县',
        330522: '长兴县',
        330523: '安吉县',
        330602: '越城区',
        330603: '柯桥区',
        330604: '上虞区',
        330624: '新昌县',
        330681: '诸暨市',
        330683: '嵊州市',
        330702: '婺城区',
        330703: '金东区',
        330723: '武义县',
        330726: '浦江县',
        330727: '磐安县',
        330781: '兰溪市',
        330782: '义乌市',
        330783: '东阳市',
        330784: '永康市',
        330802: '柯城区',
        330803: '衢江区',
        330822: '常山县',
        330824: '开化县',
        330825: '龙游县',
        330881: '江山市',
        330902: '定海区',
        330903: '普陀区',
        330921: '岱山县',
        330922: '嵊泗县',
        331002: '椒江区',
        331003: '黄岩区',
        331004: '路桥区',
        331022: '三门县',
        331023: '天台县',
        331024: '仙居县',
        331081: '温岭市',
        331082: '临海市',
        331083: '玉环市',
        331102: '莲都区',
        331121: '青田县',
        331122: '缙云县',
        331123: '遂昌县',
        331124: '松阳县',
        331125: '云和县',
        331126: '庆元县',
        331127: '景宁畲族自治县',
        331181: '龙泉市'
      }
    }

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    //如果之前是修改 则进方法，是新增则不进方法
    if (typeof(options.id) !="undefined"){
      wx.request({
        url: 'http://127.0.0.1:80/nanyahuayi/addressController/getAddress', // 仅为示例，并非真实的接口地址
        data: {
          id: options.id
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success(res) {
          //console.log(res.data);
          that.setData({
            detail_add: res.data.detail_add,
            id: res.data.id,
            location: res.data.location,
            openid: res.data.openid,
            telnum: res.data.telnum,
            username: res.data.username
          })
        }
      })
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
  //地址选择器开关
  onLocateClose: function () {
    this.setData({
      locatPop: !this.data.locatPop
    })
  },
  onLocateClose: function () {
    this.setData({
      "locatPop": !this.data.locatPop
    })
  },
  onConfirmLocate(e) {
    var that = this;
    //选择地址信息
    that.setData({
      location: e.detail.values[0].name + "/" + e.detail.values[1].name + "/" + e.detail.values[2].name,
      province: e.detail.values[0].name,
      city: e.detail.values[1].name,
      country: e.detail.values[2].name,
    })
    that.onLocateClose();
  },
  //新增地址
  onAdd: function (e) {
    var username = e.detail.value.username;
    var telnum = e.detail.value.telnum;
    var location = e.detail.value.location;
    var detail_add = e.detail.value.detail_add;
    var thirdSession = wx.getStorageSync('thirdSession');
    var error_name= this.data.error_name;
    var error_phone = this.data.error_phone;
    var error_locate = this.data.error_locate;
    var error_detail_add = this.data.error_detail_add;
    var id = this.data.id ;

    //如果有误 不提交
    if (username == null || telnum == null || location == null || detail_add == null || error_name == true || error_phone == true || error_locate == true || error_detail_add == true ){
      Toast("请确保信息正确哦！");
    }else{
      //提示加载中
      Toast.loading({
        mask: true,
        message: '加载中...'
      });
      wx.request({
        url: 'http://127.0.0.1:80/nanyahuayi/addressController/setAddress', // 仅为示例，并非真实的接口地址
        data: {
          thirdSession: thirdSession 
          ,username : username
          ,telnum : telnum
          ,location :location
          ,detail_add : detail_add
          , id: id == null ? 1 : id
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success(res) {
          Toast(res.data);
          if (res.data == "保存成功") {
            wx.redirectTo({
              url: '/pages/mine/address'
            })
          }
        }
      })
    }
  },
  onInput:function(e){
    var index = e.currentTarget.id;
    var value = e.detail;
    //校验手机号，正则表达式
    var myreg = /^1\d{10}$/;
    
    if ((value == null || value == "" || value == "null") || (index == "error_phone" && !myreg.test(value)) ){
      this.setError(index,"请输入正确信息");
    } else {
      this.setData({
        [index]: false
      })
    }
  },
  //字段不合法时，报错
  setError: function (cTarget, errorMsg) {
    Toast(errorMsg);
    //不加【】不能配置化
    this.setData({
      [cTarget]: true
    })
  }
})