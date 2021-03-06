package com.nestor.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.entity.Comment;
import com.nestor.entity.CommentKey;
import com.nestor.entity.WxUser;
import com.nestor.enums.ImageType;
import com.nestor.enums.OrderStatus;
import com.nestor.service.CommentService;
import com.nestor.service.ImageService;
import com.nestor.service.OrderService;
import com.nestor.service.WxLoginService;
import com.nestor.util.CheckUtil;
import com.nestor.util.IdUtil;
import com.nestor.vo.CommentView;

//评论相关
@RestController
@RequestMapping(path = "/CommentsController")
public class CommentsController extends BaseController  {
	@Autowired
	private WxLoginService wxLogin;
	@Autowired
	private CommentService comService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private OrderService ordService;

	// 新增多图评论
	@PostMapping(path = "/addComments")
	public String addComments(String value, String fileUrls, String orderId, String text, String thirdSession) {
		Comment comment = newComment(value, orderId, text, thirdSession);
		if (comment == null) {
			return "评论失败请稍后重试";
		}

		comment.setImageUrls(fileUrls);

		if (comment.equals(null)) {
			return "保存失败，请稍后重试！";
		}
		String reString = comService.addComment(comment);
		if (reString.equals("ok")) {
			ordService.updateOrderStatus(orderId, OrderStatus.FINISH);
		}
		return reString;
	}

	// 新增单图评论
	@PostMapping(path = "/addComment")
	public String saveOneImage(@RequestParam(name = "image", required = true) MultipartFile image,
			@RequestParam(name = "type", required = true) ImageType type, String value, String productId, String text,
			String thirdSession) {
		// base check
		CheckUtil.imageFile(image, "上传图片的格式不正确");
		Comment comment = newComment(value, productId, text, thirdSession);
		if (comment == null) {
			return "评论失败请稍后重试";
		}

		comment.setImageUrls(imageService.saveOneImage(image, type).getImageUrl().toString());

		return comService.addComment(comment);
	}

	// 查询所有评论
	@GetMapping(path = "/findComment")
	public ArrayList<CommentView> findComment(Integer page, Integer pageSize) {
		ArrayList<CommentView> comList = comService.findComments(page, pageSize);
		return comList;
	}

	// 查询商品粒度评论
	@GetMapping(path = "/findProComment")
	public ArrayList<CommentView> findProComment(String productId, Integer page, Integer pageSize) {
		ArrayList<CommentView> comList = comService.findProComment(productId, page, pageSize);
		return comList;
	}

	// 评论对象属性赋值
	private Comment newComment(String value, String orderId, String text, String thirdSession) {
		CommentKey cKey = new CommentKey();
		Comment comment = new Comment();
		WxUser wxUser = wxLogin.getUInfo(thirdSession);
		// 如果用户不存在，让他重新登录
		if (wxUser == null) {
			return null;
		}

		cKey.setOpenid(wxUser.getOpenid());
		cKey.setCommentNumber(IdUtil.generateId());
		cKey.setOrderId(orderId);
		comment.setId(cKey);
		comment.setValue(value);
		// 脏话过滤 共7k+数据
		comment.setText(newWord(text));
		return comment;
	}

	// 脏话过滤
	private String newWord(String text) {
		String goodWord = new String("#富强民主#");
		List<String> badList = new ArrayList<String>();
		// 全量版本
		badList = Arrays.asList("按摩棒", "扒屄", "扒光", "扒穴", "拔屄", "掰穴", "白痴", "白癡", "白粉", "白烂", "班禅", "包pi", "包二奶", "鸡8",
				"鸡八", "鸡巴", "鸡巴靠", "鸡叭", "鸡芭", "鸡吧", "鸡掰", "鸡店", "鸡鸡", "鸡奸", "鸡女", "鸡歪", "鸡院", "機八", "機巴", "機吧", "機戰",
				"激插", "激情MM", "激情色", "激情淫", "雞8", "雞八", "雞巴", "雞叭", "雞芭", "雞吧", "雞掰", "雞雞", "雞奸", "雞女", "雞歪", "雞院",
				"鷄巴", "极景", "極景", "集体淫", "集體淫", "几八", "几巴", "几叭", "几芭", "幾八", "幾巴", "幾叭", "幾芭", "擠母奶", "奸", "奸暴", "奸你",
				"奸情", "奸染", "奸他", "奸她", "奸污", "奸一奸", "奸淫", "奸幼", "姦", "姦情", "姦染", "姦淫", "姦汙", "贱B", "贱b", "贱bi", "贱逼",
				"贱比", "贱货", "贱人", "贱种", "賤", "賤B", "賤bi", "賤逼", "賤比", "賤貨", "賤人", "賤種", "降半旗", "酱猪媳", "醬豬媳", "姣西", "脚交",
				"腳交", "叫床", "叫春", "叫鸡", "叫雞", "叫小姐", "金毛穴", "紧穴", "锦涛", "锦天", "緊穴", "劲爆", "劲乐", "劲舞团", "劲樂", "勁暴", "勁爆",
				"勁乐", "勁樂", "禁书", "经血", "經血", "精蟲", "精水", "精童", "精液", "精液浴", "精子", "菊花洞", "菊花蕾", "巨屌", "巨奶", "巨乳", "巨骚",
				"巨騷", "聚丰", "军妓", "军委", "军转", "軍妓", "开苞", "开发", "开房", "开天", "開苞", "柯赐海", "柯賜海", "柯庆施", "柯慶施", "嗑药",
				"磕药", "磕藥", "可待因", "可卡叶", "可卡葉", "可卡因", "可可精", "抠穴", "摳穴", "口爆", "口合", "口活", "口交", "口交靠", "口肯", "口射",
				"口淫", "寇晓伟", "哭么", "哭夭", "裤袜", "褲襪", "垮台", "垮臺", "快感", "快克", "快樂AV", "狂操", "狂插", "葵", "坤邁", "拉案);",
				"拉丹", "拉登", "拉凳", "拉客", "拉皮条", "拉皮條", "拉手冲", "喇嘛", "来插我", "来爽我", "赖昌星", "賴昌星", "瀨名", "拦截器", "览叫", "懒8",
				"懒八", "懒叫", "懒教", "懶8", "懶八", "懶叫", "懶教", "懶趴", "烂b", "烂B", "烂屄", "烂逼", "烂比", "烂屌", "烂货", "烂鸟", "烂人",
				"烂游戏", "滥B", "滥逼", "滥比", "滥货", "滥交", "濫B", "濫逼", "濫比", "濫貨", "濫交", "爛B", "爛逼", "爛比", "爛貨", "狼友", "浪妇",
				"浪婦", "浪叫", "浪女", "浪穴", "劳教", "老b", "老B", "老鸨", "老逼", "老比", "老瘪三", "老癟三", "老江", "老卵", "老毛", "老母", "老骚比",
				"老骚货", "老騷比", "老騷貨", "老味", "亮屄", "亮穴", "淋病", "灵游记", "凌辱", "靈遊記", "露B", "露b", "露屄", "露逼", "露点", "露點",
				"露毛", "露乳", "露穴", "露阴照", "露陰照", "卵子", "乱交", "乱伦", "亂交", "亂倫", "抡功", "掄功", "仑功", "伦功", "沦功", "纶功", "轮暴",
				"轮操", "轮大", "轮干", "轮公", "轮功", "轮攻", "轮奸", "轮流干", "轮盘赌", "轮盘机", "轮子功", "侖功", "倫功", "淪", "淪功", "耣", "輪暴",
				"輪公", "輪功", "輪攻", "輪奸", "輪姦", "骡干", "羅幹", "騾幹", "裸聊", "裸陪", "躶", "氯胺酮", "妈b", "妈B", "妈逼", "妈逼靠", "妈比",
				"妈的", "妈的b", "妈的B", "妈的靠", "妈个b", "妈个B", "妈个比", "妈妈的", "妈批", "妈祖", "媽B", "媽逼", "媽比", "媽的", "媽的B", "媽個B",
				"媽個比", "媽媽的", "媽祖", "麻痹", "麻黄素", "麻黃素", "麻醉枪", "麻醉药", "嗎b", "嗎逼", "嗎比", "嗎的", "嗎啡", "嗎個", "吗b", "吗逼",
				"吗比", "吗的", "吗的靠", "吗啡", "吗啡碱", "吗啡片", "吗个", "买财富", "买春", "买春堂", "買幣", "買財富", "買春", "買賣", "買月卡", "麦角酸",
				"麦叫酸", "売春婦", "卖.国", "卖。国", "卖b", "卖B", "卖ID", "卖QQ", "卖逼", "卖比", "卖财富", "卖国", "卖号", "卖号靠", "卖卡", "卖软件",
				"卖骚", "卖淫", "賣B", "賣ID", "賣逼", "賣比", "賣騷", "賣淫", "賣月卡", "馒头屄", "瞒报", "满洲国", "滿洲國", "曼德拉", "蔓ぺ", "猫扑",
				"貓撲", "毛XX", "毛鲍", "毛鮑", "禽兽", "禽獸", "青楼", "青樓", "情报", "情報", "情妇", "情色", "情色谷", "情兽", "情獸", "穷b", "穷逼",
				"区委", "去你的", "去你妈", "去妳的", "去妳妈", "去死", "去他妈", "去她妈", "全裸", "拳交", "瘸腿帮", "瘸腿幫", "群p", "群P", "群奸", "群交",
				"群阴会", "群陰會", "然后", "让你操", "讓你操", "日b", "日B", "日Gm", "日GM", "日gM", "日gm", "日X 妈", "日X 媽", "日X妈", "日啊",
				"日本人", "日屄", "日逼", "日比", "日穿", "日蛋", "日翻", "日九城", "日军", "日軍", "日领馆", "日你", "日你爸", "日你妈", "日你媽", "日你娘",
				"日批", "日爽", "日死", "日死你", "日他", "日他娘", "日她", "日王", "鈤", "柔阴术", "肉棒", "肉逼", "肉壁", "肉便器", "肉唇", "肉洞", "肉缝",
				"肉縫", "肉沟", "肉棍", "肉棍子", "肉壶", "肉茎", "肉莖", "肉具", "肉蒲团", "肉蒲團", "肉箫", "肉簫", "肉穴", "肉欲", "肉慾", "乳霸", "乳爆",
				"乳房", "乳峰", "乳沟", "乳溝", "乳尖", "乳交", "乳尻", "乳射", "乳头", "乳頭", "乳腺", "乳晕", "乳暈", "乳罩", "润星", "潤星", "撒尿",
				"撒泡尿", "撒切尔", "萨达姆", "萨斯", "塞白", "塞你爸", "塞你公", "塞你母", "塞你娘", "赛你娘", "赛妳娘", "赛他娘", "三p", "三八淫", "三挫仑",
				"三国策", "三级片", "三級片", "三角裤", "三陪", "三陪女", "三唑仑", "氵去", "桑国卫", "桑國衛", "骚", "骚b", "骚B", "骚B贱", "骚棒", "骚包",
				"骚屄", "骚屄儿", "骚逼", "骚比", "骚洞", "骚棍", "骚货", "骚鸡", "骚姐姐", "骚浪", "骚卵", "骚妈", "骚妹", "骚妹妹", "骚母", "骚女", "骚批",
				"骚妻", "骚乳", "骚水", "骚穴", "骚姨妈", "懆您妈", "懆您娘", "騒", "騷", "騷B", "騷B賤", "騷棒", "騷包", "騷屄", "騷逼", "騷比", "騷洞",
				"騷棍", "騷貨", "騷雞", "騷姐姐", "騷浪", "騷卵", "騷媽", "騷妹", "騷妹妹", "騷母", "騷女", "騷批", "騷妻", "騷乳", "騷水", "騷穴", "騷姨媽",
				"色97爱", "色97愛", "色成人", "色弟弟", "色电影", "色鬼", "色界", "色空寺", "色链", "色猫", "色貓", "色咪咪", "色迷城", "色魔", "色情",
				"色情靠", "色区", "色區", "色色", "色色连", "色书库", "色图乡", "色窝窝", "色窩窩", "色影院", "色诱", "色欲", "色慾", "杀人", "杀人犯", "傻×",
				"傻b", "傻B", "傻B ", "傻B靠", "傻屄", "傻逼", "傻逼靠", "傻比", "傻吊", "傻瓜", "傻卵", "傻鸟", "傻鳥", "傻批", "傻子", "煞逼", "煞笔",
				"煞笔靠", "煞筆", "上你", "上妳", "少妇", "少妇穴", "少修正", "邵家健", "舌头穴", "舌頭穴", "射　精", "射精", "射了", "射奶", "射你", "射屏",
				"射爽", "射颜", "射顏", "身寸", "生殖器", "圣火", "圣母", "湿了", "湿穴", "濕穴", "十八代", "十八摸", "十景缎", "十三点", "十三點", "石戈",
				"石进", "石首", "食精", "食捻屎", "食屎", "驶你爸", "驶你公", "驶你母", "驶你娘", "屎你娘", "屎妳娘", "駛你爸", "駛你公", "駛你母", "駛你娘",
				"事屎", "试看片", "是鸡", "是雞", "释欲", "釋欲", "手淫", "受精", "受虐狂", "受伤", "受傷", "受灾", "受災", "兽奸", "兽交", "兽欲", "獸奸",
				"獸交", "獸欲", "熟妇", "熟婦", "熟母", "熟女", "丝诱", "私！服", "私#服", "私%服", "私**服", "私*服", "私/服", "私？服", "死GM",
				"死gm", "死全家", "酥穴", "酥痒", "酥癢", "他NND", "他ㄇㄉ", "他ㄇ的", "他干", "他妈", "他妈的", "他妈地", "他妈靠", "他媽", "他媽的",
				"他媽地", "他嗎的", "他马的", "他馬的", "他吗的", "他母亲", "他奶奶", "他娘", "他娘的", "他祖宗", "它NND", "它爸爸", "它妈", "它妈的", "它妈地",
				"它媽的", "它媽地", "她NND", "她爸爸", "她妈", "她妈的", "她妈地", "她妈靠", "她媽的", "她媽地", "她马的", "她馬的", "她娘", "体奸", "體奸",
				"甜嫩穴", "舔 b", "舔b", "舔B", "舔屄", "舔逼", "舔鸡巴", "舔雞巴", "舔脚", "舔腳", "舔奶", "舔屁眼", "捅B", "捅逼", "捅比", "捅你",
				"捅死你", "捅他", "捅她", "捅我", "統治", "痛经", "偷欢", "偷歡", "偷拍", "偷情", "偷情網", "凸点装", "凸肉优", "凸肉優", "屠城", "屠杀",
				"推翻", "推推侠", "推推俠", "推油", "退党", "退黨", "退役", "吞精", "臀部", "脫內褲", "脫衣舞", "脱内裤", "脱衣舞", "外陰", "完蛋操", "玩逼",
				"玩穴", "伟哥", "尾行", "猥亵", "猥褻", "卫生部", "卫生巾", "尉健行", "慰安妇", "慰安婦", "慰春情", "我操", "我操靠", "我操你", "我草", "我干",
				"我幹", "我和她", "我奸", "我就色", "我考", "我靠", "我咧干", "我日", "我日靠", "我日你", "无界", "无码", "无码片", "无毛穴", "无网界", "无修正",
				"無碼", "無毛穴", "無網界", "無修正", "五不", "午夜场", "伍凡", "武雷", "西藏", "西藏独", "西藏国", "西藏國", "吸毒", "吸毒犯", "吸精", "习近平",
				"习仲勋", "習近平", "洗脑", "洗脑班", "洗腦班", "洗钱", "系統", "狭义道", "狹義道", "下贱", "下賤", "下流", "下三烂", "下三滥", "下三濫",
				"下三爛", "下身", "下体", "下體", "下阴", "下陰", "相奸", "想上你", "嚮導", "肖强", "销魂洞", "銷魂洞", "小B", "小b", "小B样", "小B樣",
				"小逼", "小比样", "小比樣", "小瘪三", "小癟三", "小電影", "小鸡巴", "小鸡鸡", "小雞巴", "小雞雞", "小灵通", "小卵泡", "小卵子", "小嫩逼", "小嫩鸡",
				"小嫩雞", "小平", "小泉", "小日本", "小肉粒", "小乳头", "小乳頭", "小骚逼", "小骚比", "小骚货", "小騷比", "小騷貨", "小穴", "小淫女", "小淫穴",
				"歇b", "歇逼", "褻", "幸存", "性爱", "性愛", "性病", "性高潮", "性虎", "性饥渴", "性飢渴", "性交", "性交靠", "性交课", "性交課", "性交图",
				"性交圖", "性奴", "性奴会", "性奴會", "性虐", "性虐待", "性器", "性情", "性趣", "性骚扰", "性生活", "性无能", "性無能", "性息", "性佣", "性傭",
				"性欲", "性之站", "倖存", "熊焱", "羞耻母", "羞恥母", "学生妹", "學生妹", "血逼", "血比", "鸭店", "亚洲色", "亞情", "亞無碼", "亞洲色", "烟膏",
				"煙膏", "颜射", "顏騎", "顏射", "嚴雋琪", "艳乳", "艳照", "艳照门", "艳照門", "艷照门", "艷照門", "豔乳", "豔照", "豔照門", "阳精", "阳具",
				"阳萎", "阳痿", "阳物", "陽精", "陽具", "陽萎", "陽痿", "陽物", "摇头丸", "摇头玩", "摇頭丸", "搖头丸", "搖頭丸", "要色色", "要射了", "野合",
				"野鸡", "野雞", "陰莖", "陰精", "陰毛", "陰門", "陰囊", "陰水", "隂", "银民吧", "淫", "淫B", "淫b", "淫meimei", "淫屄", "淫屄儿",
				"淫逼", "淫痴", "淫癡", "淫虫", "淫蟲", "淫荡", "淫蕩", "淫电影", "淫店", "淫东方", "淫洞", "淫妇", "淫婦", "淫告白", "淫棍", "淫河",
				"淫护士", "淫秽", "淫穢", "淫货", "淫貨", "淫奸", "淫间道", "淫贱", "淫賤", "淫浆", "淫漿", "淫叫", "淫浪", "淫流", "淫乱", "淫亂", "淫驴屯",
				"淫驢屯", "淫毛", "淫妹", "淫妹妹", "淫糜", "淫靡", "淫蜜", "淫民堂", "淫魔", "淫母", "淫妞", "淫奴", "淫虐", "淫女", "淫女穴", "淫妻",
				"淫腔", "淫情", "淫色", "淫少妇", "淫湿", "淫书", "淫書", "淫水", "淫图", "淫圖", "淫娃", "淫网", "淫窝窝", "淫西", "淫穴", "淫样", "淫樣",
				"淫液", "淫欲", "淫贼", "淫汁", "婬", "滛", "遊戲幣", "游行", "游衍", "幼逼", "幼齿", "幼妓", "幼交", "幼男", "幼女", "幼图", "幼圖",
				"幼香阁", "幼香閣", "诱奸", "诱色uu", "誘姦", "誘色uu", "玉杵", "玉蒲团", "玉蒲團", "玉乳", "玉穴", "郁慕明", "育碧", "浴尿", "预审查",
				"欲火", "欲女", "慾", "慾火", "援交", "援交妹", "曰GM", "曰Gm", "曰gM", "曰gm", "曰gＭ", "曰你", "早泄", "早洩", "造爱", "造愛",
				"招鸡", "招雞", "招妓", "兆鸿", "姫辱", "姫野爱", "致幻剂", "致幻劑", "智傲", "智凡迪", "智能H3", "智障", "作爱", "作愛", "作弊器", "坐脸",
				"坐台", "坐台的", "坐庄", "做ai", "做爱", "做爱图", "做愛", "做雞", "做鸭", "做鴨");
		// String texts ="woshinibab";
		for (int i = 0; i < badList.size(); i++) {
			text = text.replace(badList.get(i), goodWord);
		}
		System.out.println("=======================================================");
		return text;
	}
}
