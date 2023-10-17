/* import java packages */
import android.widget.Toast;
import com.bug.getpost.BugHttpClient;
import com.bug.getpost.Result;
import java.io.*;
import java.lang.String;
import java.lang.StringBuffer;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.*;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;



/* 预设变量 */
public static final String scriptName = "Terminal.java";//本脚本名称
public static final String scriptVersion = "（20231017）";//本脚本版本
Toast(scriptName + " 开始加载，请稍候。\n许你一个美好的明天，愿世间的每一个角落都洒满璀璨的星光。");
public static final String linkText = "https://github.com/BatchClayderman/Terminal.java";//本脚本下载地址

public static final String allOrders = scriptName
	+ "\n\n"
	+ "自己或代管的命令：\n"
	+ "	#add 文字				添加 send 话语（区分大小写）\n"
	+ "	#af 文字				修改自动续火文字（区分大小写）\n"
	+ "	#atAll				@所有人\n"
	+ "	#atoS 文字			将 atList 转为文本（区分大小写）\n"
	+ "	#atoU 文字			将 atList 转为 \\uUnicode 的格式（区分大小写）\n"
	+ "	#boom 次数			炸弹\n"
	+ "	#bq				#bq 表示列出当前黑名单，#bqc 或 #bql 表示显示名单长度，#bq+@QQ 表示拉黑，#bq-@QQ 表示撤销拉黑\n"
	+ "	#brush/#flush			清屏（共提供三种方式）\n"
	+ "	#chk				群备注检查【年级专业姓名】（参数：/a 提醒、/f 禁言、/n仅检查空的、/x 顺便踢人、/r 顺便踢出并拒绝再次加入）\n"
	+ "	#clap QQ号 次数（或#clap 次数@QQ）	拍一拍\n"
	+ "	#countFriends			发送所有 QQ 好友数\n"
	+ "	#define longText 字段长		设置所管群消息最大上限（区分大小写）\n"
	+ "	#define gapTime 时间		设置操作时间间隔（毫秒）（区分大小写）\n"
	+ "	#define tailLength 小尾巴重载长度下限	设置翻译文本长度下限（区分大小写）\n"
	+ "	#define tipTime 时间			设置提示时间间隔（分钟）（0 视为停止）（区分大小写）\n"
	+ "	#define r[数字] 文字			设置指定位置的 send 话语（示例：#define r0 1）（区分大小写）\n"
	+ "	#define reply			重置 reply（区分大小写）\n"
	+ "	#del 位置				删除 send 话语（区分大小写）\n"
	+ "	#ds { 禁言值列表 }			设置禁言值\n"
	+ "	#dw				#dw 表示列出当前违禁词名单，#dwc 或 #dwl 表示显示名单长度，#dw+文字 表示违禁词入库，#dw-文字 表示撤销违禁词入库\n"
	+ "	#fb				#fb 表示列出当前禁区名单，#fbc 或 #fbl 表示显示名单长度，#fb+群号 表示添加禁区群，#fb-群号 表示撤销禁区群，批量操作使用空格隔开\n"
	+ "	#fr（此命令仅对本人生效）		#fr 表示列出当前代管名单，#frc 或 #frl 表示显示名单长度，#fr+@QQ 表示增加代管，#fr-@QQ 表示取消代管\n"
	+ "	#gc				getCurrentActivity()\n"
	+ "	#get 群成员QQ号			获取群成员（群聊）或好友（私聊）信息\n"
	+ "	#getAllMsg Q群号			发送群聊资料（请谨慎使用）\n"
	+ "	#getFriends			发送所有 QQ 好友信息（请勿随意使用）\n"
	+ "	#group				发送本脚本开发者所在 QQ 群号\n"
	+ "	#hack				偷流量\n"
	+ "	#header 文字（可加@列表）		获取头衔（加@列表后为批量修改群成员头衔）（区分大小写）\n"
	+ "	#hw 次数				发送作业卡片\n"
	+ "	#len				计算长度\n"
	+ "	#lk				发送 QQ 群信息\n"
	+ "	#link				发送本脚本下载地址\n"
	+ "	#md5 文字			发送文字的 md5 信息（区分大小写）\n"
	+ "	#nick 文字（可加@列表）		修改昵称（加@列表后为批量修改群成员头衔）（区分大小写）\n"
	+ "	#openAll				全体解禁\n"
	+ "	#Recall On			开启自动延时撤回群里/对方所有消息\n"
	+ "	#Recall Off			关闭自动延时撤回群里/对方所有消息\n"
	+ "	#recall（回复文本）		撤回回复内容（由于接口问题可能会失效）\n"
	+ "	#recall 文字 t时间			发送消息后延时撤回自己的消息（区分大小写）\n"
	+ "	#Repeat Enabled			开启复读增强\n"
	+ "	#Repeat Disabled			关闭复读增强\n"
	+ "	#Repeat On			开启自动复读\n"
	+ "	#Repeat Off			关闭自动复读\n"
	+ "	#reply				（用于检测正在使用此脚本的用户）\n"
	+ "	#rm QQ号				踢人（需要群主或管理员权限）（区分大小写）\n"
	+ "	#rf QQ号				踢人并拉入群黑名单（需要群主或管理员权限）（区分大小写）\n"
	+ "	#rp 图片发送次数 -> 图片		重复发送图片\n"
	+ "	#save QQ号			救群 / 救私聊 QQ（区分大小写）\n"
	+ "	#saveA 私聊QQ号			救私聊 QQ（区分大小写）\n"
	+ "	#saveW Q群号			救群（区分大小写）\n"
	+ "	#Secure On			开启安全监控\n"
	+ "	#Secure Off			关闭安全监控\n"
	+ "	#send				发送已设定好的话语（区分大小写）\n"
	+ "	#send 文字 d次数			多文字转多发（区分大小写）\n"
	+ "	#send 文字 f[开始:结束:步长:饱和值]	发送循环消息（饱和值为 0 为不限制）（区分大小写）\n"
	+ "	#send 文字 r次数			发送多个字符串（区分大小写）\n"
	+ "	#send 文字 s次数			消息多次发送（区分大小写）\n"
	+ "	#send 文字 x次数			发送多条消息（区分大小写）\n"
	+ "	#sfc				全局黑名单扫描（使用前请先刷新成员列表）（参数：/a 提醒、/f 禁言、/x 顺便踢人、/r 顺便踢出并拒绝再次加入）\n"
	+ "	#shake 次数			抖动\n"
	+ "	#shut QQ号 分钟（或#shut 分钟@QQ）	禁言（分钟数为 0 则解禁）\n"
	+ "	#shutAll 分钟			全体禁言\n"
	+ "	#tip 文字				发送系统消息（仅自己可见且可能已失效）（区分大小写）\n"
	+ "	#Terminal On			打开总开关（区分大小写）\n"
	+ "	#Terminal Off			关闭总开关（区分大小写）\n"
	+ "	#Toast On				开启 Toast 提示\n"
	+ "	#Toast Off				关闭 Toast 提示\n"
	+ "	#TranOn				开启小尾巴自动翻译\n"
	+ "	#TranOff				关闭小尾巴自动翻译\n"
	+ "	#Tran				重置小尾巴分割符为一个回车（不区分大小写）\n"
	+ "	#Tran 文字			小尾巴分割符（区分大小写）\n"
	+ "	#undef reply			清空 reply\n"
	+ "	#Welcome On			开启欢迎语\n"
	+ "	#Welcome Off			关闭欢迎语\n"
	+ "	#whoami				显示自己的备注和 QQ 号\n"
	+ "	投票清零@QQ			投票清零\n"
	+ "\n"
	+ "他人的命令（仅对 welcomeList 生效）：\n"
	+ "	#header 文字			群成员请求头衔（区分大小写）\n"
	+ "	#sleep 分钟			群成员请求禁言\n"
	//+ "	闭关				随机禁言\n"
	//+ "	抽签/打卡/签到/签退/冒泡		无意义\n"
	//+ "	[DirtyLists]			骂人的话（对私聊也生效）（文字识别过程不区分大小写）\n"
	//+ "	[Requests]				询问在不在（对私聊也生效）\n"
	//+ "	[TreatMeBad]@我			禁言 2 分钟\n"
	//+ "	[超长文字]			自动撤回\n"
	+ "\n"
	+ "通用命令（不区分大小写）：\n"
	+ "	#AM				发送驱动启发式音乐列表\n"
	+ "	#bad				发送坏人\n"
	+ "	#BM				发送标准启发式音乐列表\n"
	+ "	#CM				发送云启发式音乐列表\n"
	+ "	#countUIN Q群号			发送群聊成员数量\n"
	+ "	#edxp				发送 Edxposed 配置与部署流程\n"
	+ "	#getUIN Q群号			发送群聊成员 QQ 号\n"
	+ "	#h（#help或菜单）			发送帮助\n"
	+ "	#ifexists QQ号/@QQ			是否存在群成员（调用前请先刷新成员列表）\n"
	+ "	#lsp				发送 LSPosed 配置与部署流程\n"
	+ "	#play 随机数			发送“好玩”的文字\n"
	+ "	#qp				发送 QQ 接收文件的路径\n"
	+ "	#tr				转换时间\n"
	+ "	#voice				发送语音（需要确保 replyVoice 里的文件存在）\n"
	+ "	#time				发送时间\n"
	+ "	#xp/#太极				（建议的操作）\n"
	+ "	投票禁言@QQ			投票禁言\n"
	+ "	投票踢人@QQ			投票踢人";



/* 全局变量 */
boolean Terminal_Flag = true;//总开关
boolean Search_Flag = true;//副开关
boolean isSecure = true;//是否开启安全监控
boolean isToast = true;//是否提示
boolean isTran = true;//是否翻译
boolean repeatEnforce = false;//是否增强复读
short init_count = 5;// on_init()
int gapTime = 0;//间隔时间
int longText = 1000;//最长字符
int tailLength = 3;//小尾巴翻译字数下限
int tipTime = 30;//提示间隔时间
int upperLimit = 150;//群聊人数上线和消息长度最大限制的一半
int upperReturn = 15;//最大限制回车
int[] dealShut = { 120, 1200, 18000, 864000, 2592000 };
Map clockMap = new HashMap();//打卡计数
Map kickMap = new HashMap();//投票踢人
Map shutMap = new HashMap();//投票禁言
Map signMap = new HashMap();//打卡计数
Map verMap = new HashMap();//进群验证
StringBuffer sb = new StringBuffer();
sb.append("快出来看！！！有乌龟乌龟乌龟乌龟乌龟乌龟乌龟乌龟🐢😏🚒");
for (int i = 0; i < 80000; ++i)
	sb.append("🐢😏🚒🐢😏🚒🐢😏🚒🐢😏🚒");
String boomText = new String(sb);
String autoFire = "早午晚鸡鸭鹅咕唔";//自动续火
String delimText = "\n";//中英分隔符
String escText = "%";//转义符
String lastAtMe = "";//上次 @ 我的人
String lastFire = "";//上次续火的人
String lastText = "";//上次复读的内容
String myGroup = "1029321431";//交流群
String rpTmp = "";//重发图片路径
String total_text = scriptName + " 初始化成功！\n超越驱动，超越方纲，超越纲程。\n";// on_init()
String[] recallListGroup = new String[0];
String[] reply = {"我要闹啦！", "我快要闹啦！！", "我已经在闹啦！！！"};
String[] replyVoice = {"/storage/emulated/0/QQ复读机/PTT/atMe.slk", "/storage/emulated/0/QQ复读机/PTT/天狗.slk"};//语音路径
String[] Requests = {"在", "在？", "在吗", "在嘛", "在么", "在不", "在不？", "在吗？", "在嘛？", "在么？", "还在？", "不在？", "不在~", "不在～", "不在~~", "不在～～", "不在~~~", "不在～～～", "在不在", "群主在吗", "管理在吗", "老哥在吗", "大哥在吗"};
String[] swipeGroup = new String[0];//开启自动复读的群（仅针对群聊）
String[] TreatMeBad = {"禁言", "踢", "拉黑", "折磨", "强奸", "打", "抢劫", "买", "卖", "购买", "求婚", "查询", "日", "操", "#get", "#search", "#rm", "#rf", "#shut"};
private static final String APP_ID = "20201006000581880";
private static final String SECURITY_KEY = "Hg2NYBpPY3BFaHomJDBc";
private static final String searchURL = "aHR0cDovL2FwaS5janNyY3cuY24vcWItYXBpLnBocD9tb2Q9Y2hhJnFxPQ==";


/* 随机数 */
/**
 * 根据min和max随机生成一个范围在[min, max]的随机数，包括min和max
 * @param min
 * @param max
 * @return int
 */
public int getRandom(int min, int max)
{
	Random random = new Random();
	return random.nextInt(max - min + 1) + min;
}

/**
 * 根据min和max随机生成count个不重复的随机数组
 * @param min
 * @param max
 * @param count
 * @return int[]
 */
public int[] getRandoms(int min, int max, int count)
{
	if (count <= 0 || count > (max - min + 1))
		return null;
	int[] randoms = new int[count];
	List listRandom = new ArrayList();//List<Integer> listRandom = new ArrayList<Integer>();
	
	/* 将所有可能出现的数字放进候选 list */
	for (int i = min; i <= max; ++i)
		listRandom.add(i);
	
	/* 从候选 list 中取出放入数组，已经被选中的就从这个 list 中移除 */
	for (int i = 0; i < count; ++i)
	{
		int index = getRandom(0, listRandom.size() - 1);
		randoms[i] = listRandom.get(index);
		listRandom.remove(index);
	}
	return randoms;
}


/* 子函数 */
public static void pause()
{
	try
	{
		Thread.sleep(gapTime <= 0 ? getRandom(1000, 10000) : gapTime);
	}
	catch (InterruptedException ie) {}
	return;
}

public static String getTime()
{
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日"), df2 = new SimpleDateFormat("E", Locale.CHINA), df3 = new SimpleDateFormat("HH:mm:ss.SSS");
	Calendar calendar = Calendar.getInstance();
	String TimeMsg1 = df1.format(calendar.getTime()), TimeMsg2 = df2.format(calendar.getTime()), TimeMsg3 = df3.format(calendar.getTime());
	if (TimeMsg1.contains("年0"))//去掉多余的 0
		TimeMsg1 = TimeMsg1.replace("年0", "年");
	if (TimeMsg1.contains("月0"))
		TimeMsg1 = TimeMsg1.replace("月0", "月");
	if (TimeMsg2.contains("周"))
		TimeMsg2 = TimeMsg2.replace("周", "星期");//转换为星期
	return TimeMsg1 + TimeMsg2 + TimeMsg3;
}

public static void sendResponse(Object data, String s)
{
	if (data.isGroup)
		sendReply(data, s);
	else if (data.sendUin.equals(mQQ))
		send(data, s);
	else
	{
		for (Object oo : getFriends())
			if (oo.uin.equals(data.sendUin))
			{
				sendReply(data, s);
				return;
			}
		send(data, s);
	}
	return;
}

public static void sendBad(Object data, boolean isShut)
{
	if (isShut)
	{
		pause();
		shutUp(data.friendUin, data.sendUin, 60 * getRandom(1, (int)(dealShut[0] / 60)));
	}
	pause();
	send(data, "坏～坏人～～大坏蛋～～～");
	pause();
	sendPhoto(data, "http://gchat.qpic.cn/gchatpic_new/1306561600/1029321431-2587600298-4EEC26065EA08F0F51B0BEE0F61A462C/0?term=2");
	pause();
	sendCard(data, "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"5\" templateID=\"1\" action=\"\" brief=\"[图片表情]\" sourceMsgId=\"0\" url=\"\" flag=\"0\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"0\"><image uuid=\"4EEC26065EA08F0F51B0BEE0F61A462C.gif\" md5=\"4EEC26065EA08F0F51B0BEE0F61A462C\" GroupFiledid=\"0\" filesize=\"2964\" local_path=\"\" minWidth=\"400\" minHeight=\"400\" maxWidth=\"400\" maxHeight=\"400\" /></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>");
	return;
}

public static boolean isInLists(String targetValue, String[] arr)//是否在数组中
{
	if (arr.length <= 0)
		return false;
	for (String s : arr)
		if (s.equals(targetValue))
			return true;
	return false;
}

public static boolean isAllE(String isAllC)//判断是否全为英文字母
{
	return isAllC.replaceAll("[A-Za-z]", "").length() == 0;
}

public static boolean isAllP(String isAllC)//判断是否全为标点和数字
{
	return isAllC.replaceAll("\\p{P}", "").length() == 0 || isAllC.replaceAll("\\d+", "").length() == 0  || isAllC.replaceAll("\\p{P}", "").replaceAll("\\d+", "").length() == 0;
}

public static boolean isChinese(char c)
{
	return c >= 0x4E00 &&  c <= 0x9FA5;//根据字节码判断
}

public static boolean isContainsZH(String str)//判断是否含有中文字符
{
	if (null == str)
		return false;
	for (char c : str.toCharArray())
		if (isChinese(c))
			return true;// 有一个中文字符就返回
	return false;
}

public static boolean isoook(String str)//判断是否为“哈哈哈哈好”类型的字符串
{
	if (str == null)
		return false;
	if (str.charAt(str.length() - 1) == '好')
	{
		for (int i = 0; i < str.length() - 1; ++i)
			if (str.charAt(i) != '哈')
				return false;
		return true;
	}
	else
		return false;
}

public static boolean isSameChar(String msg)//是否同一字符
{
	char tmp = msg.charAt(0);
	for (int i = 1; i < msg.length(); ++i)
		if (msg.charAt(i) != tmp)
			return false;
	return true;
}

public static boolean isNotEmojiCharacter(char codePoint)//判断是否为表情符号
{
	return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
		|| (codePoint == 0xD)
		|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
		|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
		|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
}

public static boolean isEmojiOnly(String isAllCodes)//是否仅含有表情
{
	boolean bRet = true;
	for (int i = 0; i < isAllCodes.length(); ++i)
		if (isNotEmojiCharacter(isAllCodes.charAt(i)))
		{
			bRet = false;
			break;//节省时间
		}
	return bRet;
}

public static String transTime(int dShut)//时间转换
{
	String sShut = "";
	int[] delimsTime = {2592000, 86400, 3600, 60, 1};
	String[] delimsText = {"月", "天", "小时", "分钟", "秒钟"};
	for (int i = 1; i < delimsTime.length; ++i)
	{
		int tmp = (int)((dShut % delimsTime[i - 1]) / delimsTime[i]);
		if (tmp > 0)
			sShut += tmp + delimsText[i];
	}
	return sShut;
}

public static String transString(String ori_string)//转换字符串
{
	return "\"" + ori_string.replaceAll("\"", "\\\"").replaceAll("\\\\", "\\\\") + "\"";
}

public String getFileContent(File file)//获取文件内容
{
	FileInputStream inputStream = new FileInputStream(file);
	byte[] bytes = new byte[inputStream.available()];
	inputStream.read(bytes);
	inputStream.close();
	String sRet = new String(bytes, StandardCharsets.UTF_8);
	return sRet;
}

public static boolean isChk(String troopnick)//是否符合群备注
{
	String troopname = troopnick.toLowerCase().replace("本科生", "本").replace("本科", "本").replace("研究生", "研").replace("硕士", "硕").replace("博士", "博").replace("在读", "").replace("专业", "");
	//troopname = troopname.replace(" ", "").replace("\\-", "").replace("\t", "").replace("\r", "").replace("\n", "");
	if (troopname.startsWith("暨大"))
		troopname = troopname.substring(2);
	else if (troopname.startsWith("暨南大学"))
		troopname = troopname.substring(4);
	if (troopname.contains("老师") || troopname.contains("助教") || troopname.contains("学生会") || troopname.contains("团委") || troopname.contains("机器人"))
		return true;
	String ext_major = "本硕博研";
	if (troopname.contains("+"))
	{
		location = troopname.indexOf('+');
		if (troopname.length() - troopname.replace("+", "").length() > 1)//三学位？
			return false;
		else if (troopname.endsWith("+"))// + 空气？
			return false;
		else if (location < 2)//第一学位只有年级
			return false;
		else if (
			(troopname.charAt(0) == '1' || troopname.charAt(0) == '2')//年级
			&& (troopname.charAt(1) >= '0' && troopname.charAt(1) <= '9')//年级
			&& (
				(isChinese(troopname.charAt(2)) && isChinese(troopname.charAt(3)) && location == 4)
				|| (isChinese(troopname.charAt(2)) && isChinese(troopname.charAt(3)) && (
					ext_major.indexOf(troopname.charAt(4)) >= 0//防止研究生备注
				) && location == 5)
				|| (troopname.charAt(2) >= 'a' && troopname.charAt(2) <= 'z')
			)	
		)
			troopname = troopname.substring(location + 1);
		else
			return false;	
	}
	if (troopname.length() <= 2)//长度不足
		return false;
	if (
		(troopname.charAt(0) == '1' || troopname.charAt(0) == '2')//年级
		&& (troopname.charAt(1) >= '0' && troopname.charAt(1) <= '9')//年级
	)
		troopname = troopname.substring(2);
	else
		return false;
	if (isChinese(troopname.charAt(0)))//中文专业
		return (troopname.length() > 2 && isChinese(troopname.charAt(1))) || (troopname.length() > 3 && isChinese(troopname.charAt(1)) && ext_major.indexOf(troopname.charAt(2)) >= 0);
	else
		return true;//英文专业
}


/* 总开关 */
public static boolean Terminal_Check_Flag(String target)
{
	return Terminal_Flag && (target.length() > 0 ? (!forbiddenList.find(target)) : true);
}


/* 并发线程 */
public static void on_init(String text)
{
	total_text += "\n\n" + text;
	--init_count;
	if (init_count == 0)
	{
		if (("" + getCurrActivity()).toLowerCase().startsWith("com.tencent.mobileqq.activity."))
			Toast(total_text);
	}
	else
		return;
	new Thread(new Runnable()
	{
		public void run()
		{
			try
			{
				short attemptTime = 3;
				try
				{
					Thread.sleep(tipTime * 1000);//暂停 tipTime 秒
				}
				catch (InterruptedException ie) {}
				HashSet allFriendsA = new HashSet();
				HashSet allFriendsB = new HashSet();
				boolean usingA = true;
				for (;;)
				{
					if (tipTime <= 0)//功能被关闭
						return;
					if (usingA)//向 B 更新 A
					{
						allFriendsA = new HashSet();
						for (Object oo : getFriends())
							allFriendsA.add(oo.uin);
						if (allFriendsA.size() <= 1)
						{
							--attemptTime;
							Toast("QQ 好友数获取异常，将于 " + tipTime + " 秒后将重新获取，如果您的 QQ 没有 QQ 好友，请使用“#define tipTime 0”命令将 tipTime 设置为 0，否则，请尝试打开 QQ 主界面以激活 getFriends() 函数。");
							try
							{
								Thread.sleep(tipTime * 1000);//暂停 tipTime 秒
							}
							catch (InterruptedException ie) {}
							if (attemptTime > 0)
								continue;
							else
							{
								Toast("3 次尝试均失效，并发线程已退出。");
								return;
							}
						}
						if (allFriendsB.size() == 0)//首次统计
						{
							if (("" + getCurrActivity()).toLowerCase().startsWith("com.tencent.mobileqq.activity."))
								Toast(scriptName + " 并发线程开始运行，当前 QQ（" + mQQ + "）共有 QQ 好友 " + allFriendsA.size() + " 位。");
							else
								Toast(scriptName + " 并发线程已开始运行！");
						}
						else
						{
							int tmp = tipTime;
							Iterator it = allFriendsB.iterator();
							while (it.hasNext())
							{
								Object obj = it.next();
								if (!allFriendsA.contains(obj.toString()))
								{
									if (tmp > 0)
									{
										send(createData(false, obj.toString()), "在？在吗？？还在吗？？？");//说明此人已把你删除
										pause();//防止发生群发事件
										--tmp;
									}
									else
									{
										Toast("检测到您被好友删除的频率超过每分钟一次，如果这属于误报，这可能是程序运行异常导致的。目前，程序已停止向每一位将您删除的好友发送信息，如果您安装了 QNotified，请使用该插件查看历史好友。");
										break;
									}
								}
							}
							if (allFriendsA.size() == allFriendsB.size())
								Toast(scriptName + " 持续运行中！");
							else if (allFriendsA.size() < allFriendsB.size())//好友总数减少
								Toast("QQ好友  " + (allFriendsA.size() - allFriendsB.size()));
							else//好友总数增加
								Toast("QQ好友  +" + (allFriendsA.size() - allFriendsB.size()));
						}
					}
					else//向 A 更新 B
					{
						allFriendsB = new HashSet();
						for (Object oo : getFriends())
							allFriendsB.add(oo.uin);
						int tmp = tipTime;
						Iterator it = allFriendsA.iterator();
						while (it.hasNext())
						{
							Object obj = it.next();
							if (!allFriendsB.contains(obj.toString()))
							{
								if (tmp > 0)
								{
									send(createData(false, obj.toString()), "在？在吗？？还在吗？？？");//说明此人已把你删除
									pause();//防止发生群发事件
									--tmp;
								}
								else
								{
									Toast("检测到您被好友删除的频率超过每分钟一次，如果这属于误报，这可能是程序运行异常导致的。目前，程序已停止向每一位将您删除的好友发送信息，如果您安装并激活了 QNotified，请使用该插件查看历史好友。");
									break;
								}
							}
						}
						if (allFriendsA.size() == allFriendsB.size())
							Toast(scriptName + " 持续运行中！");
						else if (allFriendsA.size() > allFriendsB.size())//好友总数减少
							Toast("QQ好友  " + (allFriendsB.size() - allFriendsA.size()));
						else//好友总数增加
							Toast("QQ好友  +" + (allFriendsB.size() - allFriendsA.size()));
					}
					usingA = !usingA;//切换
					for (int i = 0; i < tipTime; ++i)
					{
						try
						{
							Thread.sleep(60000);
						}
						catch (InterruptedException ie)
						{
							tipTime = 0;
						}
					}
				}
			}
			catch (Throwable e)//减少其它模块开启未知异常抓取的弹窗频率
			{
				Toast(scriptName + " 并发线程发生异常！\n抛出异常信息如下：\n" + e);
			}
		}
	}).start();
	return;
}



/* 主类 */
public class Terminal
{
	private String name;
	private String path;
	private String delims;
	private HashSet hashset = new HashSet();
	
	public Terminal(String name, String path, String delims, HashSet hashset)
	{
		this.name = name;
		this.path = path;
		this.delims = delims;
		this.hashset = hashset;
		return;
	}
	public Terminal(String name, String path, String delims, boolean capital)
	{
		this.name = name;
		this.path = path;
		this.delims = delims;
		try
		{
			File file = new File(this.path);
			if (!file.exists())
			{
				Toast(this.name + "初始化失败！\n系统找不到指定文件！这将会为您创建相关文件！");
				return;
			}
			String list = getFileContent(file);
			
			if (capital && list.startsWith(delims))
				list = list.substring(delims.length());
			else if (!capital && list.toLowerCase().startsWith(delims.toLowerCase()))
				list = list.substring(delims.toLowerCase().length());
			if (capital && list.endsWith(delims))
				list = list.substring(0, list.length() - delims.length());
			else if (!capital && list.toLowerCase().endsWith(delims.toLowerCase()))
				list = list.substring(0, list.toLowerCase().length() - delims.toLowerCase().length());
			
			if (list.length() > 0)
			{
				String[] lists = list.split(delims);
				for (String element : lists)
					this.hashset.add(element);
			}
			on_init(this.name + "初始化成功！\n共有 " + this.hashset.size() + " 个元素！");
		}
		catch (Throwable e)
		{
			Toast(this.name + "初始化失败！\n抛出异常信息如下：\n" + e);
		}
		return;
	}
	
	public boolean update()//更新到文件
	{
		boolean success = true;
		FileWriter fwriter = null;
		try
		{
			fwriter = new FileWriter(this.path);
			fwriter.write(this.toString());
		}
		catch (Throwable e)
		{
			success = false;
		}
		finally
		{
			try
			{
				fwriter.flush();
				fwriter.close();
			}
			catch (Throwable ex)
			{
				success = false;
			}
		}
		return success;
	}
	public boolean find(String element)//简单查找
	{
		return this.hashset.contains(element);
	}
	public boolean find(String element, boolean capital)//复杂查找
	{
		if (this.hashset.size() == 0)
			return false;
		Iterator it = this.hashset.iterator();
		while (it.hasNext())
		{
			Object obj = it.next();
			if (capital && element.contains(obj.toString()))
				return true;
			else if (!capital && element.toLowerCase().contains(obj.toString().toLowerCase()))
				return true;
		}
		return false;
	}
	
	public boolean[] add(String element)//简单添加
	{
		if (element.toLowerCase().contains(this.delims.toLowerCase()))
			return this.add(element.toLowerCase().split(this.delims.toLowerCase()));
		boolean[] bRet = { true, true };
		if (this.hashset.contains(element))
		{
			bRet[1] = false;
			return bRet;
		}
		this.hashset.add(element);
		bRet[0] = this.update();
		return bRet;
	}
	public boolean[] add(String[] elements)//批量简单添加
	{
		boolean[] bRet = { true, true };
		for (String element : elements)
		{
			boolean[] tmp_Ret = this.add(element);
			bRet[0] = bRet[0] && tmp_Ret[0];
			bRet[1] = bRet[1] && tmp_Ret[1];
		}
		return bRet;
	}
	public boolean[] add(String element, boolean capital)//复杂添加
	{
		if (capital && element.contains(this.delims))
			return this.add(element.split(this.delims), capital);
		else if (!capital && element.toLowerCase().contains(this.delims.toLowerCase()))
			return this.add(element.toLowerCase().split(this.delims.toLowerCase()), capital);
		HashSet elements = new HashSet();
		boolean[] bRet = { true, true };
		if (this.find(element, capital))
		{
			bRet[1] = false;
			return bRet;
		}
		Iterator it = hashset.iterator();
		while (it.hasNext())
		{
			Object obj = it.next();
			if (capital && (obj.toString().contains(element))) {}
			else if (!capital && (obj.toString().toLowerCase().contains(element.toLowerCase()))) {}
			else { elements.add(capital ? obj.toString() : obj.toString().toLowerCase()); }
		}
		elements.add(capital ? element : element.toLowerCase());
		this.hashset = elements;
		bRet[0] = this.update();
		return bRet;
	}
	public boolean[] add(String[] elements, boolean capital)//批量复杂添加
	{
		boolean[] bRet = { true, true };
		for (String element : elements)
		{
			boolean[] tmp_Ret = this.add(element, capital);
			bRet[0] = bRet[0] && tmp_Ret[0];
			bRet[1] = bRet[1] && tmp_Ret[1];
		}
		return bRet;
	}
	
	public boolean[] remove(String element)//简单删除
	{
		if (element.toLowerCase().contains(this.delims.toLowerCase()))
			return this.remove(element.toLowerCase().split(this.delims.toLowerCase()));
		boolean[] bRet = { true, true };
		if (!this.hashset.contains(element))
		{
			bRet[1] = false;
			return bRet;
		}
		this.hashset.remove(element);
		bRet[0] = this.update();
		return bRet;
	}
	public boolean[] remove(String[] elements)//批量简单删除
	{
		boolean[] bRet = { true, true };
		for (String element : elements)
		{
			boolean[] tmp_Ret = this.remove(element);
			bRet[0] = bRet[0] && tmp_Ret[0];
			bRet[1] = bRet[1] && tmp_Ret[1];
		}
		return bRet;
	}
	public boolean[] remove(String element, boolean capital)//复杂删除
	{
		if (capital && element.contains(this.delims))
			return this.remove(element.split(this.delims), capital);
		else if (!capital && element.toLowerCase().contains(this.delims.toLowerCase()))
			return this.remove(element.toLowerCase().split(this.delims.toLowerCase()), capital);
		HashSet elements = new HashSet();
		boolean[] bRet = { true, true };
		if (!this.find(element, capital))
		{
			bRet[1] = false;
			return bRet;
		}
		Iterator it = hashset.iterator();
		while (it.hasNext())
		{
			Object obj = it.next();
			if (capital && (element.contains(obj.toString()))) {}
			else if (!capital && (element.toLowerCase().contains(obj.toString().toLowerCase()))) {}
			else { elements.add(capital ? obj.toString() : obj.toString().toLowerCase()); }
		}
		this.hashset = elements;
		bRet[0] = this.update();
		return bRet;
	}
	public boolean[] remove(String[] elements, boolean capital)//批量复杂删除
	{
		boolean[] bRet = { true, true };
		for (String element : elements)
		{
			boolean[] tmp_Ret = this.remove(element, capital);
			bRet[0] = bRet[0] && tmp_Ret[0];
			bRet[1] = bRet[1] && tmp_Ret[1];
		}
		return bRet;
	}
	
	public int count()
	{
		return this.hashset.size();
	}
	public String getDelims()
	{
		return this.delims;
	}
	public String toString()//输出
	{
		if (this.hashset.size() == 0)
			return "";
		String lists = "";
		Iterator it = this.hashset.iterator();
		while (it.hasNext())
		{
			Object obj = it.next();
			lists += this.delims + obj.toString();
		}
		return lists.substring(this.delims.length());
	}
}
Terminal blackQQ = new Terminal("QQ 黑名单", "/storage/emulated/0/QQ复读机/Terminal/blacklist.txt", ", ", false);
Terminal dirtyList = new Terminal("关键词表", "/storage/emulated/0/QQ复读机/Terminal/dirtylist.txt", "\n", false);
Terminal friendQQ = new Terminal("QQ 代管名单", "/storage/emulated/0/QQ复读机/Terminal/friendlist.txt", ", ", false);
Terminal welcomeList = new Terminal("QQ 群监控名单", "/storage/emulated/0/QQ复读机/Terminal/welcomelist.txt", ", ", false);
Terminal forbiddenList = new Terminal("QQ 群禁区名单", "/storage/emulated/0/QQ复读机/Terminal/forbidden.txt", ", ", false);
Map autoReply = new HashMap();//自动回复
File file = new File("/storage/emulated/0/QQ复读机/Terminal/autoReply.txt");
if (file.exists())
{
	String[] rules = getFileContent(file).split("\n");
	for (String rule : rules)
		if (!rule.startsWith("#") && !rule.startsWith("%") && !rule.startsWith("@") && !rule.startsWith("//") && rule.contains(" -> "))
			autoReply.put(rule.split(" -> ")[0], rule.split(" -> ")[1]);
}



/* 副类 */
public class Search
{
	/* 字段 */
	private String QQ = null;		// key = 0 : QQ
	private String Phone = null;		// key = 1 : Phone
	private String LOL = null;		// key = 2 : LOL
	private String Weibo = null;		// key = 3 : Weibo
	private String whiteList = "|1306561600|1434707902|823620148|";
	private final String tips = "目标位于白名单中！";
	private final String modeError = "查询模式有误！";
	private final String nullStatement = "没有找到任何结果！";
	
	/* API */
	private static final String[] APIs = {
		"https://zy.xywlapi.cc/qqapi?qq=", 		// 0 : 0 -> 1 : QQ -> Phone
		"https://zy.xywlapi.cc/qqphone?phone=", 		// 1 : 1 -> 0 : Phone -> QQ
		"https://zy.xywlapi.cc/qqlol?qq=", 		// 2 : 0 -> 2 : QQ -> LOL
		"https://zy.xywlapi.cc/lolname?name=", 		// 3 : 2 -> 0 : LOL -> QQ
		"https://zy.xywlapi.cc/wbapi?id=", 		// 4 : 3 -> 1 : Weibo -> Phone
		"https://zy.xywlapi.cc/wbphone?phone=", 		// 5 : 1 -> 3 : Phone -> Weibo
	};
	
	/* 请求 */
	public String get(String link)
	{
		try
		{
			BugHttpClient bug = new BugHttpClient();
			Result result = bug.get(null, link);
			return result.getResult();
		}
		catch (Throwable e)
		{
			return null;
		}
	}

	/* 提取信息 */
	public String getValue(String str, int key)
	{
		String[] dicts = str.substring(1, str.length() - 1).split(",");
		Map map = new HashMap();
		for (int i = 0; i < dicts.length; ++i)
		{
			String key = dicts[i].split(":")[0], value = dicts[i].split(":")[1];
			if (key.startsWith("\""))
				key = key.substring(1);
			if (key.endsWith("\""))
				key = key.substring(0, key.length() - 1);
			if (value.startsWith("\""))
				value = value.substring(1);
			if (value.endsWith("\""))
				value = value.substring(0, value.length() - 1);
			map.put(key, value);
		}
		switch(key)
		{
		case 0:// QQ
			return map.get("qq");
		case 1:// Phone
			return map.get("phone");
		case 2:// LOL
			return map.get("name") + "\n" + map.get("daqu");
		case 3:// Weibo
			return map.get("id");
		default:
			return null;
		}
	}
	
	/* 获取信息 */
	public String transformation(int in_key, int out_key)
	{
		switch(in_key)
		{
		case 0:// QQ
			switch(out_key)
			{
			case 1:// Phone
				return null == this.QQ ? null : this.getValue(this.get(APIs[0] + URLEncoder.encode(this.QQ)), out_key);
			case 2:// LOL
				return null == this.QQ ? null : this.getValue(this.get(APIs[2] + URLEncoder.encode(this.QQ)), out_key);
			default:
				return null;
			}
		case 1:// Phone
			switch(out_key)
			{
			case 0:// QQ
				return null == this.Phone ? null : this.getValue(this.get(APIs[1] + URLEncoder.encode(this.Phone)), out_key);
			case 3:// Weibo
				return null == this.Phone ? null : this.getValue(this.get(APIs[5] + URLEncoder.encode(this.Phone)), out_key);
			default:
				return null;
			}
		case 2:// LOL
			switch(out_key)
			{
			case 0:// QQ
				return null == this.LOL ? null : this.getValue(this.get(APIs[3] + URLEncoder.encode(this.LOL)), out_key);
			default:
				return null;
			}
		case 3:// Weibo
			switch(out_key)
			{
			case 1:// Phone
				return null == this.Weibo ? null : this.getValue(this.get(APIs[4] + URLEncoder.encode(this.Weibo)), out_key);
			default:
				return null;
			}
		default:
			return null;
		}
	}
	
	/* 构造和输出 */
	public String toString()
	{
		if (this.QQ.contains(this.tips))
			return this.QQ;
		int state = (null != this.QQ ? 0x1 : 0x0)
			| (null != this.Phone ? 0x2 : 0x0)
			| ((null != this.LOL && !this.LOL.equals("null\nnull")) ? 0x4 : 0x0)
			| (null != this.Weibo ? 0x8 : 0x0);
		switch(state)
		{
		case 0x0:// 4 个 null 则说明查询入口都不存在
			return this.modeError;
		case 0x1:// 3 个 null 则说明查询失败
			return "QQ = " + this.QQ + "\n" + this.nullStatement;
		case 0x2:// 3 个 null 则说明查询失败
			return "Phone = " + this.Phone + "\n" + this.nullStatement;
		case 0x4:// 3 个 null 则说明查询失败
			return "LOL = " + this.LOL + "\n" + this.nullStatement;
		case 0x8:// 3 个 null 则说明查询失败
			return "Weibo = " + this.Weibo + "\n" + this.nullStatement;
		default:
			return "QQ\n" + this.QQ + "\n\nPhone\n" + this.Phone +"\n\nLOL\n" + this.LOL + "\n\nWeibo\n" + this.Weibo;
		}
	}
	
	public Search(int key, String value)
	{
		switch(key)
		{
		case 0:// QQ
			this.QQ = value;
			this.Phone = this.transformation(0, 1);
			this.LOL = this.transformation(0, 2);
			this.Weibo = this.transformation(1, 3);
			break;
		case 1:// Phone
			this.Phone = value;
			this.QQ = this.transformation(1, 0);
			this.Weibo = this.transformation(1, 3);
			this.LOL = this.transformation(0, 2);
			break;
		case 2:// LOL
			this.LOL = value;
			this.QQ = this.transformation(2, 0);
			this.Phone = this.transformation(0, 1);
			this.Weibo = this.transformation(1, 3);
			break;
		case 3:// Weibo
			this.Weibo = value;
			this.Phone = this.transformation(3, 1);
			this.QQ = this.transformation(1, 0);
			this.LOL = this.transformation(0, 2);
			break;
		default:
			break;
		}
		if (this.QQ.equals(mQQ) || this.whiteList.contains("|" + this.QQ + "|") || friendQQ.find(this.QQ))//检测是否为代理好友
		{
			switch(key)
			{
			case 0:
				this.QQ = "QQ = ";
				break;
			case 1:
				this.QQ = "Phone = ";
				break;
			case 2:
				this.QQ = "LOL = ";
				break;
			case 3:
				this.QQ = "Weibo = ";
				break;
			default:
				break;
			}
			this.QQ += value + "\n" + this.tips;
		}
		return;
	}
}



/* 子类 */
public class MD5
{
	private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static String byteArrayToHex(byte[] byteArray)
	{
		char[] resultCharArray = new char[byteArray.length << 1];
		int index = 0;
		for (byte b : byteArray)
		{
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}
	public static String md5(String input)
	{
		if (input == null)
			return null;
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = input.getBytes("utf-8");
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		}
		catch (Exception e)
		{
			Toast(e + "");
			return null;
		}
	}
	public static String md5(InputStream in)
	{
		try
		{
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = in.read(buffer)) != -1)
				messagedigest.update(buffer, 0, read);
			in.close();
			String result = byteArrayToHex(messagedigest.digest());
			return result;
		}
		catch (Exception e)
		{
			Toast(e + "");
		}
		return null;
	}
	public static String md5(File file)
	{
		try
		{
			if (!file.isFile())
				return null;
			FileInputStream in = new FileInputStream(file);
			String result = md5(in);
			in.close();
			return result;
		}
		catch (Exception e)
		{
			Toast(e + "");
		}
		return null;
	}
	public static String bgImage(String oriImage, boolean bg)//转大图或小图
	{
		File file = new File(oriImage);
		String sMd5 = MD5.md5(file);
		String sRet = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"5\" templateID=\"1\" action=\"\" brief=\"[图片表情]\" sourceMsgId=\"0\" url=\"\" flag=\"0\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"0\" advertiser_id=\"0\" aid=\"0\"><image uuid=\"";
		sRet += sMd5;
		sRet += ".gif\" md5=\"";
		sRet += sMd5;
		if (bg)
			sRet += "\" GroupFiledid=\"0\" filesize=\"2964\" local_path=\"\" minWidth=\"400\" minHeight=\"400\" maxWidth=\"400\" maxHeight=\"400\" /></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>";
		else
			sRet += "\" GroupFiledid=\"0\" filesize=\"2964\" local_path=\"\" minWidth=\"25\" minHeight=\"25\" maxWidth=\"25\" maxHeight=\"25\" /></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>";
		return sRet;
	}
}



/* 全局监控 */
public boolean secureAllow(Object data)//串行监控不采用并发编程
{
	try
	{
		if (isSecure && (data.isGroup && !data.sendUin.equals(mQQ) && !friendQQ.find(data.sendUin)))
		{
			if (data.type == 1 || data.type == 3 || data.type == 4 || data.type == 5)//广告与色情过滤
			{
				if (dirtyList.find(data.content, false))
				{
					if (data.isGroup && isAdmin(data.friendUin))
					{
						record(data);
						String[] atBadQQ = { data.sendUin };
						if (data.content.startsWith("#") || data.content.startsWith("投票禁言") || data.content.startsWith("投票踢人"))
						{
							shutUp(data.friendUin, data.sendUin, dealShut[1]);
							send(data, data.nickName + "，您触发了违禁词，已被禁言 " + transTime(dealShut[1]) + " 处理。", atBadQQ);
							return true;//这里是允许的
						}
						else
						{
							shutUp(data.friendUin, data.sendUin, dealShut[2]);
							send(data, data.nickName + "，您触发了违禁词，已被禁言 " + transTime(dealShut[2]) + " 处理。", atBadQQ);
						}
					}
					return false;//不再进行其它操作
				}
			}
			if ((data.type == 1 || data.type == 5) && (!data.content.startsWith(scriptName) && !data.content.startsWith("启发式音乐") && (data.content.length() > upperLimit << 1 || data.content.length() - data.content.replace("\r", "").replace("\n", "").length() > upperReturn)))//文字超长
			{
				if (data.isGroup && isAdmin(data.friendUin))
				{
					String[] atBadQQ = { data.sendUin };
					shutUp(data.friendUin, data.sendUin, dealShut[2]);
					record(data);
					send(data, data.nickName + "，您发送的消息太长，已被禁言 " + transTime(dealShut[2]) + " 处理。", atBadQQ);
				}
				return false;//不再进行其它操作
			}
			if (data.type == 2)//图片二维码识别
			{
				if (dirtyList.find(data.content, false))
				{
					if (data.isGroup && isAdmin(data.friendUin))
					{
						String[] atBadQQ = { data.sendUin };
						shutUp(data.friendUin, data.sendUin, dealShut[1]);
						record(data);
						send(data, data.nickName + "，您所发送的图片链接含有违禁词，已被禁言 " + transTime(dealShut[1]) + " 处理。", atBadQQ);
					}
					return false;//不再进行其它操作
				}
				try
				{
					BugHttpClient bug = new BugHttpClient();
					Result result = bug.get(null, "https://api.no0a.cn/api/qrdecode/query?imgurl=" + URLEncoder.encode(data.content));
					if (result.getResult().startsWith("{\"status\":1,"))
					{
						if (data.isGroup && welcomeList.find(data.friendUin))
						{
							String[] atBadQQ = { data.sendUin };
							shutUp(data.friendUin, data.sendUin, dealShut[2]);
							record(data);
							send(data, data.nickName + "，您发送的图片中含有二维码，已被禁言 " + transTime(dealShut[2]) + " 处理。", atBadQQ);
						}
						return false;//不再进行其它操作
					}
				}
				catch (Throwable ce)
				{
					return false;
				}
			}
			if (data.type == 3 || data.type == 4)//卡屏过滤
			{
				replyMsg = "";
				if (data.content.contains("icon=") && data.content.contains(".iso"))
					replyMsg += "检测到该卡片的代码中含有“.iso”关键字，可能是流量偷跑代码，请谨慎处理！";
				String toJudge = data.content.toLowerCase();
				if (
					toJudge.contains("w=\"-") || toJudge.contains("h=\"-")
					|| toJudge.contains("width=\"-") || toJudge.contains("height=\"-")
					|| toJudge.contains("\"width\":-") || toJudge.contains("\"height\":-")
				)
					replyMsg += "请注意，该卡片可能存在刷屏或清屏行为，已自动为您提供越过该卡片的回复按钮。";
				if (replyMsg.length() > 0)
				{
					if ((data.isGroup) && isInLists(data.friendUin, recallListGroup))
						record(data);
					sendReply(data, replyMsg + "\n\n卡片代码：" + data.content);
					return false;//不再进行其它操作
				}
			}
			if (dirtyList.find(getGroupMemberInfo(data.friendUin, data.sendUin).troopnick, false))
			{
				shutUp(data.friendUin, data.sendUin, dealShut[0]);
				String[] atBadQQ = { data.sendUin };
				send(data, "{\"QQ\":\"" + data.sendUin + "\", \"troopnick\":" + transString(getGroupMemberInfo(data.friendUin, data.sendUin).troopnick) + "}\n\n您的昵称含有违禁词，请您尽快修改！", atBadQQ);
			}
		}
		return true;//允许进行其它操作
	}
	catch (Throwable e)
	{
		Toast(scriptName + " 主线程于 安全监控 处发生异常，抛出异常信息如下：\n" + e);
		return false;//抛出异常默认不允许继续
	}
}


/* 主功能区 */
public void onMsg(Object data)
{
	if (!Terminal_Check_Flag(data.isGroup ? data.friendUin : ""))
	{
		if (data.content.equals("#Terminal On"))
		{
			Terminal_Flag = true;
			Toast(scriptName + " 主线程已恢复运行！");
		}
		return;
	}
	try
	{
		if (!secureAllow(data))
			return;
		
		/* 进群验证 */
		if (data.isGroup && verMap.get(data.friendUin + "/" + data.sendUin) != null)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						String[] AtLists = { data.sendUin };
						if (verMap.get(data.friendUin + "/" + data.sendUin).equals(data.content))
						{
							verMap.remove(data.friendUin + "/" + data.sendUin);
							//shutUp(data.friendUin, data.sendUin, 0);
							send(createData(true, data.friendUin), data.nickName + "，恭喜验证通过，再次欢迎您的到来，记得改改群昵称哦～～～", AtLists);
						}
						else
						{
							if (dirtyList.find(data.content, false))
							{
								send(createData(true, data.friendUin), data.nickName + "，您触发了违禁词，已直接被踢出群聊！", AtLists);
								removeTroop(data.friendUin, data.sendUin, false);//踢出群聊
							}
							else
							{
								send(createData(true, data.friendUin), data.nickName + "，很抱歉，您发送的验证码有误！", AtLists);
								record(data);//撤回信息
							}
						}
					}
					catch (Throwable suberror)
					{
						Toast(scriptName + " 子线程于 进群验证 处发生异常，抛出异常信息如下：\n" + suberror);
					}
				}
			}).start();//不要 return
		}
		
		/* 任何人都可调用的通用命令 */
		if (data.content.toLowerCase().equals("#bad"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendBad(data, false);
				}
			}).start();
			return;
		}
		else if (data.isGroup && (data.content.toLowerCase().equals("#countuin") || data.content.toLowerCase().startsWith("#countuin ")))
		{
			String groupUin = data.friendUin;
			try
			{
				groupUin = data.content.toLowerCase().split("#countuin ")[1];
			}
			catch (Throwable e)
			{
				groupUin = data.friendUin;
			}
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						String UinText = "friendUin = " + data.friendUin + "\n" + "sendUin = " + data.sendUin +  "\n\n\n";
						int tmp = 0;
						for (Object target : getGroupMembers(groupUin))
							++tmp;
						UinText += "经检测，该群（" + groupUin + "）共有 " + tmp + " 名群成员。";
						sendReply(data, UinText);
					}
					catch (Throwable e)
					{
						sendReply(data, "发生错误，本 QQ 可能不在群 " + groupUin + " 中！");
					}
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#edxp"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, "https://blog.csdn.net/weixin_51485807/article/details/115210246");
				}
			}).start();
			return;
		}
		else if (data.isGroup && (data.content.toLowerCase().equals("#getuin") || data.content.toLowerCase().startsWith("#getuin ")))
		{
			String groupUin = data.friendUin;
			try
			{
				groupUin = data.content.toLowerCase().split("#getUIN ")[1];
			}
			catch (Throwable e)
			{
				groupUin = data.friendUin;
			}
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						String UinText = "friendUin = " + data.friendUin + "\n" + "sendUin = " + data.sendUin +  "\n";
						int tmp = 0;
						for (Object target : getGroupMembers(groupUin))
						{
							UinText += "\n" + target.memberuin;
							++tmp;
						}
						UinText += "\n\n\n经检测，该群（" + groupUin + "）共有 " + tmp + " 名群成员。";
						sendReply(data, UinText);
					}
					catch (Throwable e)
					{
						sendReply(data, "发生错误，本 QQ 可能不在群 " + groupUin + " 中！");
					}
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#h") || data.content.toLowerCase().equals("#help") || data.content.equals("菜单"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, allOrders);
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().startsWith("#ifexists "))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						Map targetMap = new HashMap();
						String toSend = "";
						String[] targetList = new String[0];
						int tcount = 0, tfcount = 0;
						if (data.atList.length == 0)
							targetList = data.content.toLowerCase().split("#ifexists ")[1].split(" ");
						else
							targetList = data.atList;
						for (int i = 0; i < targetList.length; ++i)
						{
							target = targetList[i];
							if (target.length() > 0 && target.replaceAll("\\d+", "").length() == 0)//是数字
							{
								++tfcount;
								targetMap.put(target, false);
							}
							else if (target.toLowerCase().equals("mqq"))
							{
								++tfcount;
								targetList[i] = mQQ;
								targetMap.put(mQQ, false);
							}
							else
								targetList[i] = "";
						}
						for (Object target : getGroupMembers(data.friendUin))
							if (isInLists(target.memberuin, targetList))
								targetMap.put(target.memberuin, true);
						for (String target : targetList)
							if (target.length() > 0)
								if (targetMap.get(target))
								{
									++tcount;
									toSend += target + " : true\n";
								}
								else
									toSend += target + " : false\n";
						toSend += "exists " + tcount + " / " + tfcount;
						sendReply(data, toSend);
					}
					catch (Throwable suberror)
					{
						Toast(scriptName + " 子线程于 #ifexists 处发生异常，抛出异常信息如下：\n" + suberror);
					}
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#lsp"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, "https://blog.csdn.net/weixin_51485807/article/details/119981061");
				}
			}).start();
			return;
		}
		else if (data.content.toUpperCase().equals("#AM"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, "/*** 驱动启发式音乐 ***/\n\n\n"
						+ "AM—2→3（遥感指导修为）：《命运交响曲》\n"
						+ "AM—3→4（修为指导哲学）：《月光奏鸣曲》\n"
						+ "AM—4→5（哲学指导音乐）：《星空》\n"
						+ "AM—5→6（音乐指导生活）：《A Love for Life》\n"
						+ "相关资源请访问：https://cloud-inspired.goosebt.com:9090/home.html"
					);// Python 脚本技术支持
				}
			}).start();
			return;
		}
		else if (data.content.toUpperCase().equals("#BM"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, "/*** 标准启发式音乐 ***/\n\n\n"
						+ "BM—1：《雅尼·雅典卫城音乐会》\n"
						+ "BM—1—0：《Santorini》\n"
						+ "BM—1—1：《Until the Last Moment》\n"
						+ "BM—1—2：《Keys to Imagination》\n"
						+ "BM—1—3：《The Rain Must Fall》\n"
						+ "BM—1—4：《Felitsa》\n"
						+ "BM—1—5：《With Attraction》\n"
						+ "BM—1—6：《One Man's Dream》\n"
						+ "BM—1—7：《Marching Season》\n"
						+ "BM—1—8：《Nostalgia》\n"
						+ "BM—1—9：《Acroyali + Standing in Motion》\n"
						+ "BM—1—10：《Aria》\n"
						+ "BM—1—11：《Reflections of Passion》\n"
						+ "BM—1—12：《Swept Away》\n"
						+ "BM—1—13：《The End of August》\n\n"
						+ "BM—2：《雅尼·北京紫禁城音乐会》\n"
						+ "BM—2—0：《Dance With a Stranger》\n"
						+ "BM—2—1：《Deliverance》\n"
						+ "BM—2—2：《Love Is All》\n"
						+ "BM—2—3：《Nightingale》\n"
						+ "BM—2—4：《Niki Nana》\n"
						+ "BM—2—5：《Renegade》\n"
						+ "BM—2—6：《Tribute》\n"
						+ "BM—2—7：《Waltz in 7/8》\n"
						+ "BM—2—8：《Adagio in C Minor》\n"
						+ "BM—2—9：《Southern Exposure》\n\n"
						+ "BM—3：《雅尼·2006拉斯维加斯音乐会》\n"
						+ "BM—3—0：《Standing in Motion》\n"
						+ "BM—3—1：《Rainmaker》\n"
						+ "BM—3—2：《Keys to Imagination》\n"
						+ "BM—3—3：《Enchantment》\n"
						+ "BM—3—4：《On Sacred Ground》\n"
						+ "BM—3—5：《Play Time》\n"
						+ "BM—3—6：《Until the Last Moment》\n"
						+ "BM—3—7：《If I Could Tell You》\n"
						+ "BM—3—8：《For All Seasons》\n"
						+ "BM—3—9：《The Storm》\n"
						+ "BM—3—10：《Prelude》\n"
						+ "BM—3—11：《Nostalgia》\n"
						+ "BM—3—12：《World Dance Incredible》\n\n"
						+ "BM—4：《命运交响曲》\n"
						+ "BM—4—1：《Allegro con brio》\n"
						+ "BM—4—2：《Andante con moto》\n"
						+ "BM—4—3：《Scherzo》\n"
						+ "BM—4—4：《Allegro》\n\n"
						+ "BM—5：\n"
						+ "BM—5—1：《G大调小步舞曲》\n"
						+ "BM—5—2：《The Entertainer》\n"
						+ "BM—5—3：《Sonata in C, K. 545, Allegro》\n"
						+ "BM—5—4：《A dog's life》\n"
						+ "BM—5—5：《In the Hall of the Mountain King》\n"
						+ "BM—5—6：《G大调弦乐小夜曲》\n"
						+ "BM—5—7：《枫叶拉格》\n"
						+ "BM—5—8：《Waltz No. 1 Grande Valse Brillante》\n"
						+ "BM—5—9：《Nocturne E Flat Major Op.9 No.2》\n"
						+ "BM—5—10：《月光》\n"
						+ "BM—5—11：《匈牙利狂想曲第二号》\n"
						+ "BM—5—12：《幻想即兴曲》\n"
						+ "BM—5—13：《月光奏鸣曲》\n"
						+ "BM—5—14：《革命练习曲》\n"
						+ "BM—5—15：《冬风练习曲》\n\n"
						+ "BM—6：《Sayonara Concert》\n\n"
						+ "BM—7：《久石让在武道馆》\n\n\n\n"
						+ "相关资源请访问：https://cloud-inspired.goosebt.com:9090/home.html"
					);// Python 脚本技术支持
				}
			}).start();
			return;
		}
		else if (data.content.toUpperCase().equals("#CM"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, "/***** 云启发音乐 *****/\n\n"
						+ "CM—1：《星空》\n"
						+ "CM—2：《Still Water》\n"
						+ "CM—3：《亲亲宝贝》\n"
						+ "CM—4：《Santorini》\n"
						+ "CM—5：《小小竹排》\n"
						+ "CM—6：《Etude in B Major》\n"
						+ "CM—7：《Air For The G String》\n"
						+ "CM—8：《New Morning》\n"
						+ "CM—9：《心兰相随》\n"
						+ "CM—10：《圣托里尼》\n"
						+ "CM—11：《梦中的婚礼》\n"
						+ "CM—12：《瓦妮莎的微笑》\n"
						+ "CM—13：《水边的阿狄丽娜》\n"
						+ "CM—14：《梦中的鸟》\n"
						+ "CM—15：《给母亲的信》\n"
						+ "CM—16：《Felitsa》\n"
						+ "CM—17：《Victory Remix》\n"
						+ "CM—18：《The Storm》\n"
						+ "CM—19：《Vertigo》\n"
						+ "CM—20：《Into the Deep Blue》\n"
						+ "CM—21：《Dance for Me》\n"
						+ "CM—22：《Renegade》\n"
						+ "CM—23：《匈牙利奏鸣曲》\n"
						+ "CM—24：《加勒比海盗》\n"
						+ "CM—25：《爱的协奏曲》\n"
						+ "CM—26：《Desire》\n"
						+ "CM—27：《The End of August》\n"
						+ "CM—28：《月光奏鸣曲》\n"
						+ "CM—29：《Until the Last Moment》\n"
						+ "CM—30：《夜莺》\n"
						+ "CM—31：《Truth of Touch》\n"
						+ "CM—32：《Reflections of Passion》\n"
						+ "CM—33：《Swept Away》\n"
						+ "CM—34：《Waltz in 7/8》\n"
						+ "CM—35：《Adagio in C Minor》\n"
						+ "CM—36：《Standing in Motion》\n"
						+ "CM—37：《Aria》\n"
						+ "CM—38：《幽默曲》\n"
						+ "CM—39：《D大调小步舞曲》\n"
						+ "CM—40：《Kiki's Delivery Service》\n"
						+ "CM—41：《永远同在》\n"
						+ "CM—42：《夜的钢琴曲五》\n"
						+ "CM—43：《幻想之匙》\n"
						+ "CM—44：《渴望》\n"
						+ "CM—45：《A Love for Life》\n"
						+ "CM—46：《If I Could Tell You》\n"
						+ "CM—47：《Enchantment》\n"
						+ "CM—48：《天鹅》\n"
						+ "CM—49：《童年的回忆》\n"
						+ "CM—50：《Green Sleeves》\n"
						+ "CM—51：《绿袖子》\n"
						+ "CM—52：《4分33秒》\n"
						+ "CM—53：《C大调前奏曲》\n"
						+ "CM—54：《梦想人生》\n"
						+ "CM—55：《秋日私语》\n"
						+ "CM—56：《勃拉姆斯的摇篮曲》\n"
						+ "CM—57：《献给爱丽丝》\n"
						+ "CM—58：《森林波尔卡》\n"
						+ "CM—59：《天真烂漫》\n"
						+ "CM—60：《蓝色的爱》\n"
						+ "CM—61：《海边的星空》\n"
						+ "CM—62：《Night, moon, wind, you》\n"
						+ "CM—63：《Lady Di》\n"
						+ "CM—64：《土耳其进行曲》\n"
						+ "CM—65：《Over the Horizon》\n"
						+ "CM—66：《Tribute》\n"
						+ "CM—67：《Dance With a Stranger》\n"
						+ "CM—68：《Deliverance》\n"
						+ "CM—69：《暑夏清晨》\n"
						+ "CM—70：《克罗地亚狂想曲》\n"
						+ "CM—71：《出埃及记》\n"
						+ "CM—72：《野蜂飞舞》\n"
						+ "CM—73：《命运交响曲》\n"
						+ "CM—74：《音乐盒舞者》\n"
						+ "CM—75：《蜂鸟》\n"
						+ "CM—76：《Wonderland》\n"
						+ "CM—77：《The Ludlows》\n"
						+ "CM—78：《车尔尼练习曲Op.740 No.49》\n"
						+ "CM—79：《少女的祈祷》\n"
						+ "CM—80：《G大调快板》\n"
						+ "CM—81：《君的思念》\n"
						+ "CM—82：《黄昏之时》\n"
						+ "CM—83：《Summer》\n"
						+ "CM—84：《瓦格纳婚礼进行曲》\n"
						+ "CM—85：《梁祝》\n"
						+ "CM—86：《赛马》\n"
						+ "CM—87：《二泉映月》\n"
						+ "CM—88：《入殓师》\n"
						+ "CM—89：《雪之梦》\n"
						+ "CM—90：《雨的印记》\n"
						+ "CM—91：《River Flows In You》\n"
						+ "CM—92：《Time To Love》\n"
						+ "CM—93：《克罗地亚第二狂想曲》\n"
						+ "CM—94：《门德尔松婚礼进行曲》\n"
						+ "CM—95：《思乡病》\n"
						+ "CM—96：《思乡曲》\n"
						+ "CM—97：《Jardin Secret》\n"
						+ "CM—98：《珊瑚舞》\n"
						+ "CM—99：《蓝色的多瑙河》\n"
						+ "CM—100：《卡农》\n\n\n"
						+ "相关资源请访问：https://cloud-inspired.goosebt.com:9090/home.html"
					);// Python 脚本技术支持
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#play") || data.content.toLowerCase().startsWith("#play "))
		{
			int n = 1;
			if  (data.content.toLowerCase().startsWith("#play "))
			{
				try
				{
					n = Integer.parseInt(data.content.toLowerCase().split("#play ")[1]);
				}
				catch (Throwable e)
				{
					n = 1;
				}
			}
			new Thread(new Runnable()
			{
				public void run()
				{
					String[] playTexts = {"请双击并用朗读功能播放：\n燚兲兲殤拰螞眲菗畢廣睾，拰濕鈈濕嫐榟哊鼈，艸拰螞攬菗畢，窩聞拰輒鉻鎩畢拰裵螞，拰輒鉻菗畢，莴璺齁拰組綜濕覇靆，拰輒鉻菗鈈堯簾嘚，鼜隬嘜鎘萞簩禌欛淣礣铰䵧屬䵼，閷夶菄璽䵽儞鎷㯖萞隬撻鷌給簩禌篪螫，隬嘜鰳鎘綢萞摋萞菄戲，簩禌欛淣礣贛蘩，欛淣礣贛酨滌䵼腳瓁覇魃，歸黠賴甛嚄鍀寶鈹，隬嘜鰳鎘綢萞矟铧槊隬嘜鎘萞，摋萞菄戲。", "各位复读机，天气逐渐变得寒冷。请大家注意保暖，以免线路老化，影响复读。 各位复读机，天气逐渐变得寒冷。请大̧̧̓̓̐ ͆ͩ ̍ ̾̉̎̎ͩ家̔̅̉҉̡. ̴̡ͮ̽̐ ̷̉̇̈̆̚注̈́̇ͤ͡意̷̧͋ͩ̀ .͒ͧ͟͟͢ ̋͌̔͟保暖，以免 ̡̄ͥ̒̽̀ͣ̍̈̇͘ ̐ͭͪͯ̋҉线 ̌ͪ̾̃ ͩͯͣ̌路. ̸̨͆̒. ͧ̆̓老化，影 ͆响复 ̾̉̎̎ͩ̔̅̉҉̡. ̴̡ͮ̽读不然会像我这种复读不清楚 各̯͓̥ͭͥͩ̽͗̑̇̎ͩ̀͢位̛̍͒̀͌ͯ͌͋̎̉̎̚̚҉̵͏̵̨̝̪͎̪̤̺̣̺͈̘̻̦̱̯̙̜̯̫͈͇̭̺̟͓̘̓ͤͤͣ̄̄̈́ͧ̽̂̀̚͞ͅ读̸̜̠̭͍͙ͩ̀ͣ͆̍̈͒̉̌̉̂͛̓͜͠͠机̪͓̥̪͊̎̆̎̃͆̈́́̚，̵̸͓̣̣̳̭͓̞͎̍ͤ͊ͯͮ̓͑͌̈́̄̒̑͌ͪ̀̀̚̕ͅͅ天̶̛̯͙͚͖̝͔̱̣̼̲͍̮̳̎ͦ͛̿͑͗̏ͧ̍͛̓̾̈́ͧ͊ͧ̄气ͮͤͩͬ̆͏̧̺̠̺̗̼͔̞̻̥̗̬̱̦͓̥̺̗̩̩̂̏͌ͯͧ̿́͑̅ͯ̕͟͠͝ͅ渐̵̖̗̟͉̦̐̊ͬ̉́̐̃͘变̵̙̳̙̜ͬ̈̀̊̃̿̈́̋̈͋̈́͆ͪͫ̂̽͊̃͡得̡͇̙̜̖̠̻̱̣͊̐̊͊̏ͦ̈́̒͆ͫ̑͛͐ͤͬ̽ͩ͢͢ͅͅ寒̶̝͎̯ͫ̓̍̓ͧ͆ͩ͗ͤ̚泠̨͖̯̮̼̖̪̦̲̖͉̹̟̥̟͖̔̽͑̒̿͐͌̋͗͛͋̿̄̏̕͜͝。̜͈̖̗̖̐͛͆̂̈̿́ͫ͠请̵̢͔̘͕̲͖͈͖̯̠̗̤̗̪̝̩̘͑̌̌ͦ͗ͩ͗̈́͆ͨ̓ͥ̓ͫͪ́̄̂̈́ͪ͌̂̈́͑̉̾͗ͥ̾ͬ̚͞͞ͅ ̴̯̲͈͙̖̫͚̯̲̂̉̉ͭͬ̉͊̀ͫ̄̚͘͢ ̸̛̮̦͖͇͕͓͇̖̯̳̻͍̠̪͍̖̒̊ͪ̇͑̆ͯ͛̇ͪͨ̌̒ͨ͌͗̈̃ ̵̟̟̖̯͕̙̭̙̗̼̤͈͚̪͈̺̲͓ͬͧͫ̓ͩ̂̽̀ͪͨ̄͑͐̅̉͟͝家̴̰̘͉͓̺̠̪̙̜͔̲͎͎͓̼̆͗ͮͥ͂̓ͬ͑̄̊́͡.̵͖̗̘̼͙̼̰̤͖͓ͭ̽͒̍̆̓́͛̎̍ͪ͒ͥͮ̉ͭͭ̚ͅ ͚͓̻̰̺̍̍ͭͦ̃̈̎̉̀̓̕ ̧̹͍͎̑̂ͮͩ́注͒̉͌͗͒ͬͤ͂̌ͧ̏ͬ̇̅͑͜҉̟̝͈̬͇̝͇̦̤̹̤͎̪̭̟̳̱ͯ̃̏̆̎ͥ͜͠ ̤͎̝̝̭̻̮̏͑ͤ͌̏̿̈́͑̓̒̌̌͑̿̽̄͐͐͞ͅͅ.̨͗̑ͭ͏̢͇͔̤̟͍̲͕͓ͅ ̡͖̦͍̖͇̞̤͕̞̟͔̙̺͈̥͓̰̬̓̍̿̇͗保̡̹̙͖̰̦̬̼̣̻̦̤̓͗ͪ̄ͪͨ̓͌ͫ͋́͢暖̨̛͎̼̟̌̾ͧ͐͊͢，̴̸̶̮̗̙̥̦̘̫̞͍̩̪̼̣̞̺̄̉̌ͬͣ̄͛̈̾̋̌̏̌̊͞以͖̲̱̱̮̥̫̘̰̜̫̪̩͔̬̃ͤ̎̾̒͋̈́̀͝ͅ免各位复读机，天气逐渐变得寒冷。请大家注意保暖，以免线路老化，影响复读。 各位复读机，天气逐渐变得寒冷。请大̧̧̓̓̐ ͆ͩ ̍ ̾̉̎̎ͩ家̔̅̉҉̡. ̴̡ͮ̽̐ ̷̉. ̴̡ͮ̽读不然会像我这种复读不清楚 各̯͓̥ͭͥͩ̽͗̑̇̎ͩ̀͢位̛̍͒̀͌ͯ͌͋̎̉̎̚̚҉̵͏̵̨̝̪͎̪̤̺̣̺͈̘̻̦̱̯̙̜̯̫͈͇̭̺̟͓̘̓ͤͤͣ̄̄̈́ͧ̽̂̀̚͞ͅ读̸̜̠̭͍͙ͩ̀ͣ͆̍̈͒̉̌̉̂͛̓͜͠͠机̪͓̥̪͊̎̆̎̃͆̈́́̚，̵̸͓̣̣̳̭͓̞͎̍ͤ͊ͯͮ̓͑͌̈́̄̒̑͌ͪ̀̀̚̕ͅͅ天̶̛̯͙͚͖̝͔̱̣̼̲͍̮̳̎ͦ͛̿͑͗̏ͧ̍͛̓̾̈́ͧ͊ͧ̄气ͮͤͩͬ̆͏̧̺̠̺̗̼͔̞̻̥̗̬̱̦͓̥̺̗̩̩̂̏͌ͯͧ̿́͑̅ͯ̕͟͠͝ͅ渐̵̖̗̟͉̦̐̊ͬ̉́̐̃͘变̵̙̳̙̜ͬ̈̀̊̃̿̈́̋̈͋̈́͆ͪͫ̂̽͊̃͡得̡͇̙̜̖̠̻̱̣͊̐̊͊̏ͦ̈́̒͆ͫ̑͛͐ͤͬ̽ͩ͢͢ͅͅ寒̶̝͎̯ͫ̓̍̓ͧ͆ͩ͗ͤ̚泠̨͖̯̮̼̖̪̦̲̖͉̹̟̥̟͖̔̽͑̒̿͐͌̋͗͛͋̿̄̏̕͜͝。̜͈̖̗̖̐͛͆̂̈̿́ͫ͠请̵̢͔̘͕̲͖͈͖̯̠̗̤̗̪̝̩̘͑̌̌ͦ͗ͩ͗̈́͆ͨ̓ͥ̓ͫͪ́̄̂̈́ͪ͌̂̈́͑̉̾͗ͥ̾ͬ̚͞͞ͅ ̴̯̲͈͙̖̫͚̯̲̂̉̉ͭͬ̉͊̀ͫ̄̚͘͢ ̸̛̮̦͖͇͕͓͇̖̯̳̻͍̠̪͍̖̒̊ͪ̇͑̆ͯ͛̇ͪͨ̌̒ͨ͌͗̈̃ ̵̟̟̖̯͕̙̭̙̗̼̤͈͚̪͈̺̲͓ͬͧͫ̓ͩ̂̽̀ͪͨ̄͑͐̅̉͟͝家̴̰̘͉͓̺̠̪̙̜͔̲͎͎͓̼̆͗ͮͥ͂̓ͬ͑̄̊́͡.̵͖̗̘̼͙̼̰̤͖͓ͭ̽͒̍̆̓́͛̎̍ͪ͒ͥͮ̉ͭͭ̚ͅ ͚͓̻̰̺̍̍ͭͦ̃̈̎̉̀̓̕ ̧̹͍͎̑̂ͮͩ́注͒̉͌͗͒ͬͤ͂̌ͧ̏ͬ̇̅͑͜҉̟̝͈̬͇̝͇̦̤̹̤͎̪̭̟̳̱ͯ̃̏̆̎ͥ͜͠ ̤͎̝̝̭̻̮̏͑ͤ͌̏̿̈́͑̓̒̌̌͑̿̽̄͐͐͞ͅͅ.̨͗̑ͭ͏̢͇͔̤̟͍̲͕͓ͅ ̡͖̦͍̖͇̞̤͕̞̟͔̙̺͈̥͓̰̬̓̍̿̇͗保̡̹̙͖̰̦̬̼̣̻̦̤̓͗ͪ̄ͪͨ̓͌ͫ͋́͢暖̨̛͎̼̟̌̾ͧ͐͊͢\n\n\n", " 新年快乐！祝您全家幸福快乐，身体健康，必须发财！\n初一您 Twrp 卡刷必报错\n初二您第三方 rom 必变砖\n初三您️ download 模式必掉\n初四您 9008 救砖救不过\n初五您 vivo 原地黑屏\n初六您 Windows 游戏蓝屏\n初七您卡苹果进度条\n初八您 linux 打不开\n初九您基带原地爆炸", "祝各位大佬在新的一年：Windows开机蓝屏，Linux 开机 Kernel Panic，macOS 开机五国，服务器 IDRAC/LO/PM/KWM 全部失联，路由器全爆炸，路由表内存全溢出，交换机全环路，防火墙全阻断，无线信道全冲突，压接网线全短路，BGP 全漏表，机柜全断电，RAID 全爆炸，NAS 数据全丢，光模块全炸，光纤全不通，光猫全烫手，电表全倒转，空开全烧穿。", "祝各位新的一年：xray、burp 社区版，权限提不了，目录扫不来，ssrf 不出网，sqli 跑不出，禁止 whoami，xss 全 self，rce 全踩罐。Exp 只能本地用，csrf 有 token，挖逻辑有鉴权，靶机洞还没自己多。管理员不用弱口令，文件上传强制改名。应用已停止运行，bypass waf 遇长亭。好不容易挖到个，危害不足欢迎宁。重复感谢你提交，审核减 rank 不留情。", "祝各位大手子在新的一年 PHP 全 Fatal Error，fileinfo 全装不上，npm/composer install 全报错，Laravel Mix 全报未知错误，服务器全部宕机，电脑开机报错，Linux sudo rm -rf /*，数据库 Deleted，CN2 全绕路，线路全阻断，海外网站全被墙，服务器炸库，网关无响应，代理 500，网站 502，RAID 组几个一起炸几个，UPS 爆炸，一年到头DDOS CC不断，流量几千T，并发上亿，ping 全超时，备案全重审，资源 404，SSL 全重定向，CDN 全不回源，爬虫永远不来你家，收录零蛋，数据库超时，前端永远 502，IP 永远封80、443，Windows 开机蓝屏，Linux 开机 Kernel Panic，macos 开机五国，服务器 iDRAC/iLO/IPMI/KVM 全部失联，路由器全爆炸，路由表内存全溢出，交换机全环路，防火墙全阻断，无线信道全冲突，CC、DOS、DDOS 全来，IP 全被压接网线全短路，BGP 全漏表，机柜全断电，RAiD 全爆炸，NAS 数据全丢，光模块全炸，光纤全不同，光猫全烫手，电表全倒转，空开全烧穿，Java 空指针，Adobe 全家保存失败+闪退，制作程序做一半全关机，最后祝大家伪静态永远设置不上，服务器炸库。", "上联：你再过来我就叫啦\n下联：你再不叫我就过去啦\n横批：就这？就这？", "2022年了，感恩大家长久以来对我的忽视，我很喜欢这个群，大家都各聊各的，就是没人鸟我，很温馨的一个圈子，我很欣慰，我删了很多群，唯独你们，我舍不得删，因为大家都展现出真实的自己--色情、装逼、矫情、贪婪、伪善、两面三刀、笑里藏刀、道貌岸然、自私、虚荣、狡诈、虚伪、冷漠、龌龊、卑鄙、见利忘义、嫌贫爱富、厚颜无耻，还有超级加倍的阴阳怪气。我真的很喜欢你们这群吊毛，希望大家继续加油，我会一直和你们耗下去……"};
					if (data.content.toLowerCase().equals("#play"))
					{
						for (String playText : playTexts)
						{
							sendResponse(data, playText);
							pause();
						}
					}
					else
					{
						if (n < 1)
							n = 1;
						else if (n > playTexts.length)
							n = playTexts.length;
						int[] randoms = getRandoms(0, playTexts.length - 1, n);
						for (int r : randoms)
							sendResponse(data, playTexts[r]);
					}
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#qp"))
		{
			sendResponse(data, "/storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/");
			return;
		}
		else if (data.content.toLowerCase().startsWith("#tr "))
		{
			int to_tr = Integer.parseInt(data.content.toLowerCase().split("#tr ")[1]);
			sendResponse(data, transTime(to_tr));
			return;
		}
		else if (data.content.toLowerCase().equals("#reply"))//检测正在使用本脚本的用户
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					boolean tmp = isTran;
					isTran = false;
					sendResponse(data, scriptName + scriptVersion);
					isTran = tmp;
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#time"))//时间
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, getTime());
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#xp") || data.content.toLowerCase().equals("#太极"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					sendResponse(data, "亲，这边建议您将系统升级到安卓八及以上，然后使用 LSPosed 框架哦！");
				}
			}).start();
			return;
		}
		else if (data.content.toLowerCase().equals("#voice"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					boolean isError = false;
					for (String slk : replyVoice)
					{
						pause();
						try
						{						
							sendPtt(data, slk);
						}
						catch (Throwable e)
						{
							isError = true;
						}
					}
					if (isError)
						Toast("发送语音过程发生异常，请检查文件“" + slk + "”是否存在！\n抛出异常信息如下：\n" + e);
				}
			}).start();
			return;
		}
		else if (data.isGroup && isAdmin(data.friendUin) && data.content.startsWith("投票禁言"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						String[] atLists = { data.sendUin };
						if (data.atList.length <= 0)
						{
							if (!data.sendUin.equals(mQQ))
								send(data, "没有使用 @ 方法指定目标对象，请确保使用了 @ 方法而不只是文字，并使用“投票禁言@xxx”的形式重新指令。", atLists);
							return;
						}
						if (!data.sendUin.equals(mQQ) && isInLists(mQQ, data.atList))
							sendBad(data, true);
						for (String target : data.atList)
						{
							if (target.equals(mQQ) || target.equals("0"))//排除自己和全体成员
								continue;
							if (shutMap.get(data.friendUin + "/" + target) == null)//未被投票禁言过
							{
								shutMap.put(data.friendUin + "/" + target, data.sendUin);//首次不用加分号
								send(data, "投票成功，" + data.friendUin + "/" + target + " 被投票禁言 1 票，达到 3 票将会被禁言 " + transTime(dealShut[2]) + "！", atLists);
							}
							else if (!friendQQ.find(data.sendUin) && isInLists(data.sendUin, shutMap.get(data.friendUin + "/" + target).split(";")))
								send(data, data.friendUin + "/" + target + " 已被您投票过，请勿重复投票！", atLists);
							else
							{
								shutMap.put(data.friendUin + "/" + target, shutMap.get(data.friendUin + "/" + target) + ";" + data.sendUin);
								if (shutMap.get(data.friendUin + "/" + target).split(";").length >= 3)
								{
									shutUp(data.friendUin, target, dealShut[2]);
									String[] tarLists = { target };
									send(data, data.friendUin + "/" + target + " 被投票达到 3 票，禁言 " + transTime(dealShut[2]) + "！", tarLists);
									shutMap.remove(data.friendUin + "/" + target);
								}
								else
									send(data, "投票成功，" + data.friendUin + "/" + target + " 共被投票禁言 " + shutMap.get(data.friendUin + "/" + target).split(";").length + " 票，达到 3 票将会被禁言 " + transTime(dealShut[2]) + "！", atLists);
							}
							pause();
						}
					}
					catch (Throwable suberror)
					{
						Toast(scriptName + " 子线程于 投票禁言 处发生异常，抛出异常信息如下：\n" + suberror);
					}
				}
			}).start();
			return;
		}
		else if (data.isGroup && isAdmin(data.friendUin) && data.content.startsWith("投票踢人"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						String[] atLists = { data.sendUin };
						if (data.atList.length <= 0)
						{
							if (!data.sendUin.equals(mQQ))
								send(data, "没有使用 @ 方法指定目标对象，请确保使用了 @ 方法而不只是文字，并使用“投票踢人@xxx”的形式重新指令。", atLists);
							return;
						}
						if (!data.sendUin.equals(mQQ) && isInLists(mQQ, data.atList))
							sendBad(data, true);
						for (String target : data.atList)
						{
							if (target.equals(mQQ) || target.equals("0"))//排除自己和全体成员
								continue;
							if (kickMap.get(data.friendUin + "/" + target) == null)//未被投票踢人过
							{
								kickMap.put(data.friendUin + "/" + target, data.sendUin);//首次不用加分号
								send(data, "投票成功，" + data.friendUin + "/" + target + " 被投票踢人 1 票，达到 5 票将会被踢出！", atLists);
							}
							else if (!friendQQ.find(data.sendUin) && isInLists(data.sendUin, kickMap.get(data.friendUin + "/" + target).split(";")))
								send(data, data.friendUin + "/" + target + " 已被您投票过，请勿重复投票！", atLists);
							else
							{
								kickMap.put(data.friendUin + "/" + target, kickMap.get(data.friendUin + "/" + target) + ";" + data.sendUin);
								if (kickMap.get(data.friendUin + "/" + target).split(";").length >= 5)
								{
									removeTroop(data.friendUin, target, false);//允许成员再次加入（主要是防止乱投票）
									send(data, data.friendUin + "/" + target + " 被投票踢人达到 5 票，已踢出！");
									kickMap.remove(data.friendUin + "/" + target);
								}
								else
									send(data, "投票成功，" + data.friendUin + "/" + target + " 共被投票踢人 " + kickMap.get(data.friendUin + "/" + target).split(";").length + " 票，达到 5 票将会被踢出！", atLists);
							}
							pause();
						}
					}
					catch (Throwable suberror)
					{
						Toast(scriptName + " 子线程于 投票踢人 处发生异常，抛出异常信息如下：\n" + suberror);
					}
				}
			}).start();
			return;
		}
		
		/* 自己或代管才能调用的命令 */
		if (data.sendUin.equals(mQQ) || (data.isGroup && friendQQ.find(data.sendUin)))//可以手动添加代管 QQ
		{
			if (data.content.startsWith("#add "))
			{
				String tmpSingle = data.content.split("#add ")[1];
				if (reply.length > 0)
				{
					String[] tmpStr = new String[reply.length];
					for (int i = 0; i < tmpStr.length; ++i)
						tmpStr[i] = reply[i];
					reply = new String[tmpStr.length + 1];
					for (int i = 0; i < tmpStr.length; ++i)
						reply[i] = tmpStr[i];
					reply[tmpStr.length] = tmpSingle;
				}
				else
				{
					reply = new String[1];
					reply[0] = tmpSingle;
				}
				if (isToast)
					Toast("已成功添加！");
			}
			else if (data.content.startsWith("#af"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						if (data.content.length() > 4)
						{
							autoFire = data.content.substring(4);
							if (isToast)
								Toast("操作成功结束！");
						}
						else
							sendResponse(data, "autoFire = " + transString(autoFire));
					}
				}).start();
			}
			else if (data.isGroup && (data.content.toLowerCase().equals("#atall") || data.content.toLowerCase().startsWith("#atall ") || data.content.toLowerCase().contains("@all") || data.content.contains("#@")))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						int people = 0;//人数
						for (Object target : getGroupMembers(data.friendUin))
							++people;
						if (people <= 1)
						{
							Toast("获取全体成员列表失败，或该群聊只有您一人！");
							return;
						}
						else if (people > (upperLimit << 1))//else if 加快运行
						{
							Toast("当前群聊人数太多，操作存在风险，已回绝该请求！");
							return;
						}
						String[] atAllList = new String[people - 1];//去掉自己
						int i = 0;
						for (Object target : getGroupMembers(data.friendUin))
						{
							if (target.memberuin.equals(mQQ))
								continue;//略过自己 QQ
							if (i >= people - 1)
								break;//防止遍历过程中有新成员加入
							atAllList[i++] = target.memberuin;
						}
						send(data, "@全体成员", atAllList);
					}
				}).start();
			}
			else if (data.content.startsWith("#atoS "))
			{
				String preString = data.content.split("#atoS ")[1].split("@")[0];
				new Thread(new Runnable()
				{
					public void run()
					{
						boolean tmp = isTran;
						isTran = false;
						for (String aaa : data.atList)
						{
							pause();
							send(data, preString + aaa);
						}
						isTran = tmp;
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.startsWith("#atoU "))
			{
				String preString = data.content.split("#atoU ")[1];
				new Thread(new Runnable()
				{
					public void run()
					{
						String uniString = "";
						for (int i = 0; i < preString.length(); ++i)
							uniString += "\\u" + Integer.toHexString((int)preString.charAt(i));
						boolean tmp = isTran;
						isTran = false;
						send(data, uniString);
						isTran = tmp;
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.toLowerCase().equals("#boom"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						Toast("十秒后开始演示，请准备。");
						try
						{
							Thread.sleep(10000);
						}
						catch (InterruptedException e)
						{
							Toast("操作被中断！\n" + e);
						}
						send(data, "#recall#" + boomText);
					}
				}).start();
			}
			else if (data.content.toLowerCase().startsWith("#boom "))
			{
				if (data.isGroup && data.atList.length > 0)
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							for (String boomTarget : data.atList)
							{
								send(createPrivateChatData(data.friendUin, boomTarget), boomText);
								pause();
							}
							if (isToast)
								Toast("操作成功结束！");
						}
					}).start();
					return;//必须 return
				}
				int n = Math.abs(Integer.parseInt(data.content.toLowerCase().split("#boom ")[1]));
				new Thread(new Runnable()
				{
					public void run()
					{
						for(int i = 0; i < n; ++i)
						{
							send(data, boomText);
							pause();
						}
					}
				}).start();
			}
			else if (data.content.toLowerCase().startsWith("#bq"))
			{
				if (data.content.toLowerCase().equals("#bq"))
					sendResponse(data, "" + blackQQ);
				else if (data.content.toLowerCase().equals("#bqc") || data.content.toLowerCase().equals("#bql"))
					sendResponse(data, "blackQQ.length = " + blackQQ.count());
				else if (data.isGroup && data.content.toLowerCase().startsWith("#bq+"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							for (String bqq : data.atList)
								if (friendQQ.find(bqq))
								{
									Toast("QQ " + bqq + " 为代理好友，操作已中断！");
									return;
								}
							boolean[] bRet = blackQQ.add(data.atList);
							if (isToast)
							{
								String toToast = "";
								if (bRet[0])
									toToast += "操作成功结束！";
								else
									Toast("操作没有完全成功！");
								if (!bRet[1])
									toToast += "\n部分 QQ 已存在黑名单中！";
								Toast(toToast);
							}
						}
					}).start();
				}
				else if (data.isGroup && data.content.toLowerCase().startsWith("#bq-"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							boolean[] bRet = blackQQ.remove(data.atList);
							if (isToast)
							{
								String toToast = "";
								if (bRet[0])
									toToast += "操作成功结束！";
								else
									Toast("操作没有完全成功！");
								if (!bRet[1])
									toToast += "\n部分 QQ 不在黑名单中！";
								Toast(toToast);
							}
						}
					}).start();
				}
			}
			else if (data.content.toLowerCase().equals("#brush") || data.content.toLowerCase().equals("#flush"))
			{
				String[] bfCards = {
					"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"5\" templateID=\"1\" action=\"\" brief=\"[绝对清屏]\" sourceMsgId=\"0\" url=\"\" flag=\"0\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"0\" advertiser_id=\"0\" aid=\"0\"><image uuid=\"63617264507265766965772E6A7067.jpg\" md5=\"63617264507265766965772E6A7067\" GroupFiledid=\"0\" filesize=\"2964\" local_path=\"/storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/chatpic/chatimg/13a/\" minWidth=\"-400\" minHeight=\"-400\" maxWidth=\"-400\" maxHeight=\"-400\" /></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>", 
					"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"1\" templateID=\"-1\" action=\"plugin\" a_actionData=\"mqqapi://card/show_pslcard?src_type=internal&amp;source=sharecard&amp;version=1&amp;uin=823620148\" brief=\"[有人@我]在吗？你再不说话就踢了\" sourceMsgId=\"0\" url=\"\" flag=\"2\" adverSign=\"3\" multiMsgFlag=\"0\"><item layout=\"10\" bg=\"2\"><picture cover=\"https://img04.sogoucdn.com/app/a/100520146/838AE2D86C68D9EB62A1064441CCA91B\" w=\"0\" h=\"0\" /></item><item layout=\"6\"><summary size=\"350\" color=\"#FF4500\">超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏</summary></item><source name=\"点击删除清屏\" icon=\"https://mirrors.cloud.tencent.com/centos/7/isos/x86_64/CentOS-7-x86_64-Everything-2009.iso\" action=\"\" appid=\"-1\" /></msg>", 
					"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"1\" templateID=\"1\" action=\"web\" brief=\"[有人向你转账]\" sourceMsgId=\"0\" url=\"https://www.pep1.cn/cxk/\" flag=\"2\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"2\" mode=\"1\" bg=\"-23296\" advertiser_id=\"0\" aid=\"0\"><picture cover=\"http://tva3.sinaimg.cn/crop.0.0.1002.1002.180/00668lsWjw8f45ir8vwebj30ru0ru0t6.jpg\" w=\"0\" h=\"0\" /><title size=\"36\">恭喜发财大吉大利</title><summary> </summary></item><item layout=\"6\" advertiser_id=\"0\" aid=\"0\"><summary size=\"24\" color=\"#666666\">QQ钱包</summary></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>"
				};
				new Thread(new Runnable()
				{
					public void run()
					{
						boolean isSent = false;
						for (int i = 1; i <= bfCards.length; ++i)
							if (data.content.contains("" + i))
								{
									sendCard(data, bfCards[i - 1]);
									isSent = true;
								}
						if (!isSent)
							for (int i = 0; i < bfCards.length; ++i)
							{
								pause();
								sendCard(data, bfCards[i]);
							}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.isGroup && (data.content.toLowerCase().equals("#chk") || data.content.toLowerCase().startsWith("#chk ")))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							String blackHere = "";
							boolean emptyOnly = (data.content.toLowerCase().contains(" /n") || data.content.toLowerCase().contains(" -n"));
							for (Object target : getGroupMembers(data.friendUin))
							{
								if (target.memberuin.equals(mQQ))
									continue;
								String troopname = getGroupMemberInfo(data.friendUin, target.memberuin).troopnick;
								troopname = troopname.replace(" ", "").replace("\\-", "").replace("\t", "").replace("\r", "").replace("\n", "");
								if ((emptyOnly && troopname.length() <= 0) || (!emptyOnly && !isChk(troopname)))
								{
									blackHere += target.memberuin + "\t" + (troopname.length() <= 0 ? "（备注为空）" : troopname) + "\n";
									if (isAdmin(data.friendUin))
									{
										if (data.content.toLowerCase().contains(" /f") || data.content.toLowerCase().contains(" -f"))
											shutUp(data.friendUin, target.memberuin, 1296000);
										else if (data.content.toLowerCase().contains(" /x") || data.content.toLowerCase().contains(" -x"))
											removeTroop(data.friendUin, target.memberuin, false);
										else if (data.content.toLowerCase().contains(" /r") || data.content.toLowerCase().contains(" -r"))
											removeTroop(data.friendUin, target.memberuin, true);
									}
								}
							}
							if (blackHere.length() == 0)
								sendReply(data, emptyOnly ? "本群所有人都已修改备注，谢谢大家！" : "本群所有人都已按要求修改备注，感谢大家！");
							else
							{
								blackHere = blackHere.substring(0, blackHere.length() - 1);
								String[] blackH = blackHere.split("\n");
								for (int i = 0; i < blackH.length; ++i)
									blackH[i] = blackH[i].split("\t")[0];
								blackHere += "\n\n以上 " + blackH.length + (emptyOnly ? " 位同学未修改备注。" : " 位同学未按要求修改备注。");
								if (data.content.toLowerCase().contains(" /a") || data.content.toLowerCase().contains(" -a"))
								{
									blackHere += "\n请以上同学尽快修改备注，如有误报请在群内反馈。";
									send(data, blackHere, blackH);
								}
								else
									sendReply(data, blackHere);
							}
						}
						catch (Throwable suberror)
						{
							Toast(scriptName + " 子线程于 #chk 处发生异常，抛出异常信息如下：\n" + suberror);
						}
					}
				}).start();
			}
			else if (data.isGroup && data.content.toLowerCase().equals("#clap"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						int people = 0;//人数
						for (Object target : getGroupMembers(data.friendUin))//遍历所有群成员
							++people;
						if (people <= 1)
						{
							Toast("获取全体成员列表失败，或该群聊只有您一人！");
							return;
						}
						else if (people > (upperLimit << 1))//else if 加快运行
						{
							Toast("当前群聊人数太多，操作存在风险，已回绝该请求！");
							return;
						}
						Toast("开始执行拍拍拍！");
						for (Object target : getGroupMembers(data.friendUin))//遍历所有群成员
							if (!target.memberuin.equals(mQQ))
							{
								pause();
								pai(data.friendUin, target.memberuin);
							}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.isGroup && data.content.toLowerCase().startsWith("#clap "))
			{
				if (data.atList.length == 0)
				{
					String QQ = data.content.toLowerCase().split("#clap ")[1].split(" ")[0];
					int clapTime = Integer.parseInt(data.content.toLowerCase().split("#clap ")[1].split(" ")[1]);
					clapTime = Math.abs(clapTime);//使用绝对值处理负数
					clapTime = (clapTime > 0 || clapTime > upperLimit ? clapTime : 1);
					new Thread(new Runnable()
					{
						public void run()
						{
							for (int i = 0; i < clapTime; ++i)
								pai(data.friendUin, QQ);
							if (isToast)
								Toast("操作成功结束！");
						}
					}).start();
				}
				else
				{
					String myClapTimer = data.content.toLowerCase().split("#clap ")[1];
					if (myClapTimer.contains("@"))//定位到第一个@
						myClapTimer = myClapTimer.split("@")[0];
					while (myClapTimer.startsWith(" "))//先排除 #clap 后面接了多个空格的情况
						myClapTimer = myClapTimer.substring(1);
					if (myClapTimer.contains(" "))//然后去掉空格
						myClapTimer = myClapTimer.split(" ")[0];
					int clapTime = Integer.parseInt(myClapTimer);
					clapTime = Math.abs(clapTime);//使用绝对值处理负数
					clapTime = (clapTime > 0 || clapTime > upperLimit ? clapTime : 1);
					new Thread(new Runnable()
					{
						public void run()
						{
							for (int i = 0; i < clapTime; ++i)
								for (String u : data.atList)
									pai(data.friendUin, u);
							if (isToast)
								Toast("所有请求发送完毕！");
						}
					}).start();
				}
			}
			else if (data.content.toLowerCase().equals("#countfriends") || data.content.toLowerCase().equals("#getfriends"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						if (data.content.toLowerCase().equals("#getfriends"))
							sendReply(data, "" + getFriends());
						int tmp = 0;
						for (Object oo : getFriends())
							++tmp;
						sendReply(data, "经检验，本人共有 " + tmp + " 位 QQ 好友！");
					}
				}).start();
			}
			else if (data.content.equals("#define gapTime"))
			{
				gapTime = 0;
				if (isToast)
					Toast("已成功将时间间隔重置为随机数模式！");
			}
			else if (data.content.startsWith("#define gapTime "))
			{
				int tmpTime = Math.abs(Integer.parseInt(data.content.split("#define gapTime ")[1]));
				gapTime = tmpTime;
				if (isToast)
					Toast(gapTime == 0 ? "已设置为随机数！" : "已成功将时间间隔修改为 " + gapTime + " 毫秒！");
			}
			else if (data.content.equals("#define tailLength"))
			{
				tailLength = 3;
				if (isToast)
					Toast("已成功将小尾巴长度下限重置为 " + tailLength + " 个字符！");
			}
			else if (data.content.startsWith("#define tailLength "))
			{
				int tmpLength = Math.abs(Integer.parseInt(data.content.split("#define tailLength ")[1]));
				tailLength = tmpLength;
				if (isToast)
					Toast(tailLength == 0 ? "小尾巴长度下限已关闭！" : "已成功将小尾巴长度下限修改为 " + tailLength + " 个字符！");
			}
			else if (data.content.equals("#define tipTime"))
			{
				tailLength = 10;
				if (isToast)
					Toast("已成功将时间间隔重置为 " + tipTime +" 分钟！");
			}
			else if (data.content.startsWith("#define tipTime "))
			{
				int tmpTime = Math.abs(Integer.parseInt(data.content.split("#define tipTime ")[1]));
				tipTime = tmpTime;
				if (tipTime == 0)
					Toast("已关闭运行提示！");
				else
					Toast("已成功将时间间隔修改为 " + tipTime +" 分钟！");
			}
			else if (data.content.startsWith("#define longText "))
			{
				int tmpText = Math.abs(Integer.parseInt(data.content.split("#define longText ")[1]));
				if (tmpText <= 0)
					Toast("数值非法！");
				else
				{
					longText = tmpText;
					Toast("已成功将字段长上限修改为 " + longText +"！");
				}
			}
			else if (data.content.startsWith("#define r"))
			{
				if (data.content.equals("#define reply"))
				{
					reply = new String[3];
					reply[0] = "我要闹了！";
					reply[1] =  "我快要闹了！！";
					reply[2] =  "我已经在闹啦！！！";
					Toast("重置成功");
					return;
				}
				int k = Integer.parseInt(data.content.split("#define r")[1].split(" ")[0]);
				if (k < 0 || k >= reply.length)
				{
					Toast("越界设置，请检查索引值！");
					return;
				}
				String newText = data.content.split("#define r" + k + " ")[1];
				reply[k] = newText;
				Toast("设置成功");
			}
			else if (data.content.startsWith("#del "))
			{
				if (reply.length > 0)
				{
					int m = Integer.parseInt(data.content.split("#del ")[1]);
					if (m < 0 || m >= reply.length)
					{
						Toast("数值超出范围，最大索引为 " + (reply.length - 1) + "！");
						return;
					}
					String[] tmpStr = new String[reply.length - 1];
					for (int i = 0; i < m; ++i)
						tmpStr[i] = reply[i];
					for (int i = m; i < reply.length - 1; ++i)
						tmpStr[i] = reply[i + 1];
					reply = new String[tmpStr.length];
					for (int i = 0; i < tmpStr.length; ++i)
						reply[i] = tmpStr[i];
					if (isToast)
						Toast("索引 "+ m + " 已被移除");
				}
				else
					Toast("列表为空！");
			}
			else if (data.content.toLowerCase().equals("#ds"))
				sendResponse(data, "dealShut = { " + dealShut[0] + ", " + dealShut[1] + ", " + dealShut[2] + ", " + dealShut[3] + " }");
			else if (data.content.toLowerCase().startsWith("#ds "))
			{
				String ds_tmp = data.content.toLowerCase();
				ds_tmp = ds_tmp.replace("\t", " ");
				while (ds_tmp.contains("  "))//去掉多余的空格
					ds_tmp = ds_tmp.replace("  ", " ");
				String[] ds_lists = ds_tmp.split("#ds ")[1].split(" ");
				if (ds_lists.length == 4)
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							int[] new_ds = { 0, 0, 0, 0 };
							short dealLength = dealShut.length - 1;
							for (int i = 0; i < dealLength; ++i)
							{
								try
								{
									new_ds[i] = Integer.parseInt(ds_lists[i]);
								}
								catch (Throwable suberror) {}
								if (new_ds[i] > dealShut[dealLength])
								{
									Toast("禁言时长不得超过 " + transTime(dealShut[dealLength]) + "！");
									return;
								}
							}
							for (int i = 0; i < dealLength - 1; ++i)
								for (int j = i + 1; j < dealLength; ++j)
									if (new_ds[i] > 0 && new_ds[j] > 0 && new_ds[i] > new_ds[j])
									{
										Toast("低危禁言时长不得高于高危禁言时长！");
										return;
									}
							for (int i = 0; i < dealLength; ++i)
								if (new_ds[i] > 0)
									dealShut[i] = new_ds[i];
							Toast("操作成功结束！");
						}
					}).start();
				}
				else
					Toast("指令格式不正确，请检查您的指令！");
			}
			else if (data.content.toLowerCase().startsWith("#dw"))
			{
				if (data.content.toLowerCase().equals("#dw"))
					sendReply(data, "" + dirtyList);
				else if (data.content.toLowerCase().equals("#dwc") || data.content.toLowerCase().equals("#dwl"))
					sendReply(data, "dirtyList.length = " + dirtyList.count());
				else if (data.isGroup && data.content.toLowerCase().startsWith("#dw+"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							if (data.content.startsWith(dirtyList.getDelims()) || data.content.endsWith(dirtyList.getDelims()) || data.content.contains(dirtyList.getDelims() + dirtyList.getDelims()))
							{
								Toast("关键词数据有误！");
								return;
							}
							boolean[] bRet = dirtyList.add(data.content.substring(4), false);
							if (isToast)
							{
								String toToast = "";
								if (bRet[0])
									toToast += "操作成功结束！";
								else
									Toast("操作没有完全成功！");
								if (!bRet[1])
									toToast += "\n部分（子）关键词已存在！";
								Toast(toToast);
							}
						}
					}).start();
				}
				else if (data.isGroup && data.content.toLowerCase().startsWith("#dw-"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							if (data.content.startsWith(dirtyList.getDelims()) || data.content.endsWith(dirtyList.getDelims()) || data.content.contains(dirtyList.getDelims() + dirtyList.getDelims()))
							{
								Toast("关键词数据有误！");
								return;
							}
							boolean[] bRet = dirtyList.remove(data.content.substring(4), false);
							if (isToast)
							{
								String toToast = "";
								if (bRet[0])
									toToast += "操作成功结束！";
								else
									Toast("操作没有完全成功！");
								if (!bRet[1])
									toToast += "\n部分（子）关键词已存在！";
								Toast(toToast);
							}
						}
					}).start();
				}
			}
			else if (data.content.toLowerCase().startsWith("#flush "))
			{
				int n = Integer.parseInt(data.content.toLowerCase().split("#flush ")[1]);
				String card = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"5\" templateID=\"1\" action=\"\" brief=\"[绝对清屏]\" sourceMsgId=\"0\" url=\"\" flag=\"0\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"0\" advertiser_id=\"0\" aid=\"0\"><image uuid=\"63617264507265766965772E6A7067.jpg\" md5=\"63617264507265766965772E6A7067\" GroupFiledid=\"0\" filesize=\"2964\" local_path=\"/storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/chatpic/chatimg/13a/\" minWidth=\"-400\" minHeight=\"-400\" maxWidth=\"-400\" maxHeight=\"-400\" /></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>";
				new Thread(new Runnable()
				{
					public void run()
					{
						for (int i = 0; i < n; ++i)
						{
							sendCard(data, card);
							pause();
						}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.toLowerCase().startsWith("#fb"))
			{
				if (data.content.toLowerCase().equals("#fb"))
					sendResponse(data, "" + forbiddenList);
				else if (data.content.toLowerCase().equals("#fbc") || data.content.toLowerCase().equals("#fbl"))
					sendResponse(data, "forbiddenList.length = " + forbiddenList.count());
				else if (data.isGroup && data.content.toLowerCase().startsWith("#fb+"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							String targets = data.content.toLowerCase().substring(4);
							if (targets.length() > 0)
							{
								String[] fbdTargets = targets.split(" ");
								boolean[] bRet = forbiddenList.add(fbdTargets);
								if (isToast)
								{
									String toToast = "";
									if (bRet[0])
										toToast += "操作成功结束！";
									else
										Toast("操作没有完全成功！");
									if (!bRet[1])
										toToast += "\n部分 Q群 已存在禁区名单中！";
									Toast(toToast);
								}
							}
						}
					}).start();
				}
				else if (data.isGroup && data.content.toLowerCase().startsWith("#fb-"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							String targets = data.content.toLowerCase().substring(4);
							if (targets.length() > 0)
							{
								String[] fbdTargets = targets.split(" ");
								boolean[] bRet = forbiddenList.remove(fbdTargets);
								if (isToast)
								{
									String toToast = "";
									if (bRet[0])
										toToast += "操作成功结束！";
									else
										Toast("操作没有完全成功！");
									if (!bRet[1])
										toToast += "\n部分 Q群 不在禁区名单中！";
									Toast(toToast);
								}
							}
						}
					}).start();
				}
			}
			else if (data.sendUin.equals(mQQ) && data.content.toLowerCase().startsWith("#fr"))
			{
				if (data.content.toLowerCase().equals("#fr"))
					sendResponse(data, "" + friendQQ);
				else if (data.content.toLowerCase().equals("#frc") || data.content.toLowerCase().equals("#frl"))
					sendResponse(data, "friendQQ.length = " + friendQQ.count());
				else if (data.isGroup && data.content.toLowerCase().startsWith("#fr+"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							boolean[] bRet = friendQQ.add(data.atList);
							if (isToast)
							{
								String toToast = "";
								if (bRet[0])
									toToast += "操作成功结束！";
								else
									Toast("操作没有完全成功！");
								if (!bRet[1])
									toToast += "\n部分 QQ 已存在代管名单中！";
								Toast(toToast);
							}
							blackQQ.remove(data.atList);
						}
					}).start();
				}
				else if (data.isGroup && data.content.toLowerCase().startsWith("#fr-"))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							boolean[] bRet = friendQQ.remove(data.atList);
							if (isToast)
							{
								String toToast = "";
								if (bRet[0])
									toToast += "操作成功结束！";
								else
									Toast("操作没有完全成功！");
								if (!bRet[1])
									toToast += "\n部分 QQ 不在代管名单中！";
								Toast(toToast);
							}
						}
					}).start();
				}
			}
			else if (data.content.toLowerCase().equals("#gc"))
				sendResponse(data, "" + getCurrActivity());
			else if (data.content.toLowerCase().equals("#get") || data.content.toLowerCase().startsWith("#get "))
			{
				if (data.isGroup)
				{
					if (data.atList.length == 0)//没有 @ 列表
					{
						if (data.content.toLowerCase().equals("#get"))
						{
							new Thread(new Runnable()
							{
								public void run()
								{
									sendReply(data, "" + data);
									return;
								}
							}).start();
						}
						else
						{
							String target = data.content.split("get ")[1];
							if (target.toLowerCase().equals("mqq") || target.equals("%0") || target.equals("0"))//自己
								target = data.sendUin;
							if (target.replaceAll("\\d+", "").length() != 0)//不是纯数字
							{
								sendReply(data, "指令错误，请检查您的指令！");
								return;//中止查询
							}
							new Thread(new Runnable()
							{
								public void run()
								{
									sendReply(data, "Target = " + target + "\n\n" + getGroupMemberInfo(data.friendUin, target));
									return;
								}
							}).start();
						}
					}
					else
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								for (String target : data.atList)
									sendReply(data, "Target = " + target + "\n\n" + getGroupMemberInfo(data.friendUin, target));
							}
						}).start();
					}
				}
				else
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							sendReply(data, data.friendUin + "\n\n" + getFriendsInfo(data.friendUin));
						}
					}).start();
				}
			}
			else if (data.isGroup && (data.content.toLowerCase().equals("#getallmsg") || data.content.toLowerCase().startsWith("#getallmsg ")))
			{
				String groupUin = data.friendUin;
				try
				{
					groupUin = data.content.toLowerCase().split("#getAllMsg ")[1];
				}
				catch (Throwable e)
				{
					groupUin = data.friendUin;
				}
				new Thread(new Runnable()
				{
					public void run()
					{
						String UinText = "friendUin = " + data.friendUin + "\n\n";
						try
						{
							for (Object target : getGroupMembers(groupUin))
								UinText += "\n\n" + target;
							sendReply(data, UinText);
						}
						catch (Throwable e)
						{
							Toast("发生错误，请确保本 QQ 在群 " + groupUin + " 中！");
						}
					}
				}).start();
			}
			else if (data.content.toLowerCase().equals("#hack"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						StringBuffer hksb = new StringBuffer();
						for (int i = 0; i < 8192; ++i)
							hksb.append("</button><button /><button>");
						String[] hackTexts = {
							"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"1\" templateID=\"-1\" action=\"plugin\" a_actionData=\"mqqapi://card/show_pslcard?src_type=internal&amp;source=sharecard&amp;version=1&amp;uin=\" brief=\"您已成为群主\" sourceMsgId=\"0\" url=\"\" flag=\"2\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"5\" bg=\"2\" advertiser_id=\"0\" aid=\"0\"><title>点击查看本群色批</title></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>", 
							"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"33\" templateID=\"123\" action=\"web\" brief=\"偷你流量\" sourceMsgId=\"0\" url=\"http://jzi5.cn/Nzu8Be\" flag=\"8\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"2\" advertiser_id=\"0\" aid=\"0\"><picture cover=\"https://imgcache.qq.com/gc_img/gc/formal/common/1104466820/thumImg.png\" w=\"0\" h=\"0\" /><title>偷你流量</title><summary>偷你流量</summary></item><source name=\"\" icon=\"https://archive.kernel.org/centos-vault/6.1/isos/x86_64/CentOS-6.1-x86_64-bin-DVD1.iso\" action=\"\" appid=\"-1\" /></msg>", 
							"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"1\" templateID=\"-1\" action=\"plugin\" a_actionData=\"mqqapi://card/show_pslcard?src_type=internal&amp;source=sharecard&amp;version=1&amp;uin=\" brief=\"嘿嘿嘿\" sourceMsgId=\"0\" url=\"\" flag=\"2\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"5\" bg=\"2\" advertiser_id=\"0\" aid=\"0\"><title></title></item><source name=\"\" icon=\"https://mirrors.cloud.tencent.com/centos/7/isos/x86_64/CentOS-7-x86_64-Everything-2009.iso\" action=\"\" appid=\"-1\" /></msg>", 
							"{\"app\":\"com.tencent.gamecenter.gameshare\",\"desc\":\"\",\"view\":\"noDataView\",\"ver\":\"0.0.0.0\",\"prompt\":\"喵喵喵\",\"appID\":\"\",\"sourceName\":\"\",\"actionData\":\"\",\"actionData_A\":\"\",\"sourceUrl\":\"\",\"meta\":{\"shareData\":{\"height\":360,\"scene\":\"SCENE_SHARE_VIDEO\",\"buttons\":[{\"url\":\"https:\\/\\/y.music.163.com\\/m\\/song?id=1488132619\",\"text\":\"喵喵喵\"}],\"jumpUrl\":\"https:\\/\\/attachments-cdn.shimo.im\\/ozL6gi2dwLpsUdA9.mp4\",\"type\":\"video\",\"url\":\"http:\\/\\/game.gtimg.cn\\/images\\/yxzj\\/cp\\/a20200318bae\\/video.mp4\"}},\"config\":{\"forward\":1},\"text\":\"\",\"sourceAd\":\"\",\"extra\":\"\"}", 
							"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"1\" templateID=\"-1\" action=\"plugin\" a_actionData=\"mqqapi://card/show_pslcard?src_type=internal&amp;source=sharecard&amp;version=1&amp;uin=823620148\" brief=\"[有人@我]在吗？你再不说话就踢了\" sourceMsgId=\"0\" url=\"\" flag=\"2\" adverSign=\"3\" multiMsgFlag=\"0\"><item layout=\"10\" bg=\"2\"><picture cover=\"https://img04.sogoucdn.com/app/a/100520146/838AE2D86C68D9EB62A1064441CCA91B\" w=\"0\" h=\"0\" /></item><item layout=\"6\"><summary size=\"350\" color=\"#FF4500\">超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏超级清屏</summary></item><source name=\"点击删除清屏\" icon=\"https://mirrors.cloud.tencent.com/centos/7/isos/x86_64/CentOS-7-x86_64-Everything-2009.iso\" action=\"\" appid=\"-1\" /></msg>", 
							"<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"0\" templateID=\"1\" action=\"\" brief=\"举报反馈通知\" sourceMsgId=\"0\" url=\"\" flag=\"37\" sType=\"1\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"0\" advertiser_id=\"0\" aid=\"0\"><title style=\"1\">举报成功通知</title></item><item layout=\"0\" advertiser_id=\"0\" aid=\"0\"><summary type=\"1\">2022年03月06日</summary></item><item layout=\"6\" advertiser_id=\"0\" aid=\"0\"><summary>举报时间：2022-03-011 11:25:14\n举报对象：您\n审核结果：已确定好人行为\n处理方式：奖励一千块处理</summary></item><item layout=\"6\" advertiser_id=\"0\" aid=\"0\"><summary>根据您当前提供的信息暂判定奖励该对象一千元作为奖励，后续有什么情况可以向我们反馈，我们将持续关注该帐号后续的行为情况，一经核实，将再奖励一千元处理！感谢您的支持。</summary></item><item layout=\"3\" advertiser_id=\"0\" aid=\"0\"><button size=\"32\">我要评价" + hksb.toString() + "</button><button action=\"web\" url=\"https://mp.weixin.qq.com/mp/homepage?__biz=MzA3OTY4MzU1MA==&amp;hid=10&amp;sn=abde2d521469b793c6000eb901556f38\" /></item><source name=\"\" icon=\"\" action=\"\" appid=\"0\" /></msg>"
						};
						for (String hackText : hackTexts)
						{
							pause();
							sendCard(data, hackText);
						}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.toLowerCase().equals("#group"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						sendReply(data, "请加入群聊：" + myGroup);
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.isGroup && data.content.startsWith("#header "))
			{
				String myHeader = data.content.split("#header ")[1];
				new Thread(new Runnable()
				{
					public void run()
					{
						if (data.atList.length > 0)
						{
							if (myHeader.contains("@"))//定位到第一个@
								myHeader = myHeader.split("@")[0];//然后不用去空格
							for (String atQQ : data.atList)
							{
								setTitle(data.friendUin, atQQ, myHeader);
								pause();
							}
						}
						else
							setTitle(data.friendUin, mQQ, myHeader);
					}
				}).start();
			}
			else if (data.content.toLowerCase().startsWith("#hw "))
			{
				int n = Integer.parseInt(data.content.toLowerCase().split("#hw ")[1]);
				String card = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"60\" templateID=\"123\" action=\"web\" brief=\"[作业]\" sourceMsgId=\"0\" url=\"https://qun.qq.com/homework/features/detail.html?_wv=1027&amp;_bid=2146#web=1&amp;src=6&amp;hw_id=2010042002829024&amp;puin=1101089689&amp;hw_type=0&amp;need_feedback=1&amp;gid=924020028&amp;group_code=924020028&amp;group_id=924020028&amp;open_web=1\" flag=\"3\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"2\" advertiser_id=\"0\" aid=\"0\"><picture cover=\"http://p.qpic.cn/qqconadmin/0/8a0b8bcb4d512461014d527fc2520002/0\" w=\"0\" h=\"0\" /><title>10月4日作业</title><summary>123456789</summary></item><source name=\"班级作业\" icon=\"\" url=\"https://qun.qq.com/homework/?channel=1014&amp;_wv=1027&amp;src=2\" action=\"web\" i_actionData=\"tencent101846357://\" appid=\"101846357\" /></msg>";
				new Thread(new Runnable()
				{
					public void run()
					{
						for (int i = 0; i < n; ++i)
						{
							sendCard(data, card);
							pause();
						}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.toLowerCase().startsWith("#len "))
				sendResponse(data, "data.content.substring(5).length() = " + data.content.substring(5).length());
			else if (data.content.toLowerCase().equals("#lk"))
				sendResponse(data, scriptName + " 开发分群：\n" + myGroup);
			else if (data.content.toLowerCase().equals("#link"))
				sendResponse(data, scriptName + "\n" + scriptVersion + "\n\n脚本下载地址：\n" + linkText + "\n\n如有疑问，请加入群聊：\n" + myGroup);
			else if (data.content.startsWith("#md5 "))
			{
				String mdd = data.content.split("#md5 ")[1];
				new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							mdd = MD5.md5(mdd);//也可以充当延时
							pause();
						}
						catch (Exception e)
						{
							Toast(e + "");
						}
						send(data, escText + mdd);//不用翻译
					}
				}).start();
			}
			else if (data.isGroup && data.content.startsWith("#nick "))
			{
				String myHeader = data.content.split("#nick ")[1];
				new Thread(new Runnable()
				{
					public void run()
					{
						if (data.atList.length > 0)
						{
							if (myHeader.contains("@"))//定位到第一个@
								myHeader = myHeader.split("@")[0];//然后不用去空格
							for (String atQQ : data.atList)
							{
								alterNickName(data.friendUin, atQQ, myHeader);
								pause();
							}
						}
						else
							alterNickName(data.friendUin, mQQ, myHeader);
					}
				}).start();
			}
			else if (data.content.toLowerCase().equals("#openall"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						if (data.isGroup)
						{
							shutUp(data.friendUin, false);
							if (isToast)
								Toast("全体解禁请求发送成功！");
						}
						else
							Toast("此命令仅对群聊有效！");
					}
				}).start();
			}
			else if (data.content.toLowerCase().equals("#repeat enabled"))
			{
				if (repeatEnforce)
					Toast("复读增强模式已打开，请勿重复打开！");
				else
				{
					repeatEnforce = true;
					if (isToast)
						Toast("复读增强模式已打开！");
				}
			}
			else if (data.content.toLowerCase().equals("#repeat disabled"))
			{
				if (repeatEnforce)
				{
					repeatEnforce = false;
					if (isToast)
						Toast("复读增强模式已关闭！");
				}
				else
					Toast("复读增强模式已关闭，无需再次关闭！");
			}
			else if (data.content.toLowerCase().equals("#repeat on"))
			{
				if (data.isGroup)
				{
					if (isInLists(data.friendUin, swipeGroup))
						Toast("已开启，无需重复开启！");
					else
					{
						String[] tmpList = new String[swipeGroup.length];
						for (int i = 0; i < swipeGroup.length; ++i)
							tmpList[i] = swipeGroup[i];
						swipeGroup = new String[swipeGroup.length + 1];
						for (int i = 0; i < tmpList.length; ++i)
							swipeGroup[i] = tmpList[i];
						swipeGroup[swipeGroup.length - 1] = data.friendUin;
						if (isToast)
							Toast("已开启自动复读！");
					}
				}
				else
					Toast("本功能仅限群聊使用！");
			}
			else if (data.content.toLowerCase().equals("#repeat off"))
			{
				if (data.isGroup)
				{
					if (isInLists(data.friendUin, swipeGroup))
					{
						String[] tmpList = new String[swipeGroup.length - 1];
						int i = 0;
						for (String s : swipeGroup)
							if (!s.equals(data.friendUin))
								tmpList[i++] = s;
						swipeGroup = new String[tmpList.length];
						for (i = 0; i < swipeGroup.length; ++i)
							swipeGroup[i] = tmpList[i];
						if (isToast)
							Toast("已关闭自动复读！");
					}
					else
						Toast("未开启，无需关闭！");
				}
				else
					Toast("本功能仅限群聊使用！");
			}
			else if (data.isGroup && (data.content.startsWith("#rm ") || data.content.startsWith("#rf ")))
			{
				if (!isAdmin(data.friendUin))
				{
					Toast("对不起，您不是群主或管理员！");
					return;
				}
				if (data.atList.length == 0)
				{
					String QQ = "";
					if (data.content.startsWith("#rm "))
						QQ = data.content.split("#rm ")[1];
					else if (data.content.startsWith("#rf "))
						QQ = data.content.split("#rf ")[1];
					else
						return;
					if (friendQQ.find(QQ))
						return;
					new Thread(new Runnable()
					{
						public void run()
						{
							if (data.content.startsWith("#rm "))
								removeTroop(data.friendUin, QQ, false);
							else if (data.content.startsWith("#rf "))
							{
								removeTroop(data.friendUin, QQ, true);
								if (!blackQQ.add(QQ)[0])
										Toast("全局拉黑 QQ = " + QQ + " 失败！");
							}
							else
								return;
							if (isToast)
							{
								send(data, "已处理！");
								Toast("所有请求发送完毕！");
							}
						}
					}).start();
				}
				else
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							if (data.content.startsWith("#rm "))
							{
								for (int i = 0; i < data.atList.length; ++i)
									if (!friendQQ.find(data.atList[i]))
									{
										pause();
										removeTroop(data.friendUin, data.atList[i], false);
									}
								if (isToast)
								{
									send(data, "已处理！");
									Toast("所有请求发送完毕！");
								}
							}
							else if (data.content.startsWith("#rf "))
							{
								for (int i = 0; i < data.atList.length; ++i)
									if (friendQQ.find(data.atList[i]))
									{
										Toast("QQ " + data.atList[i] + " 为代理好友，操作已中断！");
										return;
									}
									else
									{
										pause();
										removeTroop(data.friendUin, data.atList[i], true);
									}
								boolean[] bRet = blackQQ.add(data.atList);
								if (isToast)
								{
									send(data, "已处理并更新黑名单！");
									String toToast = "";
									if (bRet[0])
										toToast += "操作成功结束！";
									else
										Toast("操作没有完全成功！");
									if (!bRet[1])
										toToast += "\n部分 QQ 已存在黑名单中！";
									Toast(toToast);
								}
							}
							else
								return;
						}
					}).start();
				}
			}
			else if (data.content.toLowerCase().startsWith("#recall#"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							Thread.sleep(10000);
						}
						catch (InterruptedException e)
						{
							Toast("操作被中断！\n" + e);
						}
						record(data);
					}
				}).start();
			}
			else if (data.content.toLowerCase().equals("#recall on"))
			{
				if (data.isGroup)
				{
					if (isInLists(data.friendUin, recallListGroup))
						Toast("已开启，无需重复开启！");
					else
					{
						String[] tmpList = new String[recallListGroup.length];
						for (int i = 0; i < recallListGroup.length; ++i)
							tmpList[i] = recallListGroup[i];
						recallListGroup = new String[recallListGroup.length + 1];
						for (int i = 0; i < tmpList.length; ++i)
							recallListGroup[i] = tmpList[i];
						recallListGroup[recallListGroup.length - 1] = data.friendUin;
						if (isToast)
							Toast("已开启自动撤回群聊消息！");
					}
				}
				else
					Toast("私聊撤回已被和谐，请勿尝试继续使用！");
			}
			else if (data.content.toLowerCase().equals("#recall off"))
			{
				if (data.isGroup)
				{
					if (isInLists(data.friendUin, recallListGroup))
					{
						String[] tmpList = new String[recallListGroup.length - 1];
						int i = 0;
						for (String s : recallListGroup)
							if(!s.equals(data.friendUin))
							{
								tmpList[i] = s;
								i++;
							}
						recallListGroup = new String[tmpList.length];
						for (i = 0; i < recallListGroup.length; ++i)
							recallListGroup[i] = tmpList[i];
						if (isToast)
							Toast("已关闭自动撤回群聊消息！");
					}
					else
						Toast("未开启，无需关闭！");
				}
				else
					Toast("私聊撤回已被和谐，请勿尝试继续使用！");
			}
			else if (data.content.startsWith("#recall "))
			{
				int RTime = Math.abs(Integer.parseInt(data.content.split("#recall ")[1].split(" t")[1]));
				new Thread(new Runnable()
				{
					public void run()
					{
						if (RTime > 120000)
							Toast("提示：超过两分钟，撤回可能会不成功哦！");
						try
						{
							Thread.sleep(RTime);
						}
						catch (InterruptedException ie) {}
						record(data);
						if (isToast)
							Toast("自动撤回成功！");
					}
				}).start();
			}
			else if (data.content.startsWith("#save "))
			{
				if (data.isGroup && data.atList.length > 0)
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							for (String saveTarget : data.atList)
							{
								for (int i = 0; i < 10; ++i)
								{
									send(createPrivateChatData(data.friendUin, saveTarget), i + "\n" + i);
									pause();
								}
								pause();
							}
							if (isToast)
								Toast("操作成功结束！");
						}
					}).start();
				}
				else
				{
					String QQtoSave = data.content.split("#save ")[1];
					new Thread(new Runnable()
					{
						public void run()
						{
							for (int i = 0; i < 10; ++i)
							{
								pause();
								send(createData(true, QQtoSave), i + "\n" + i);
								send(createData(false, QQtoSave), i + "\n" + i);
							}
							if (isToast)
								Toast("操作成功结束！");
						}
					}).start();
				}
			}
			else if (data.content.startsWith("#saveA "))//仅私聊 QQ
			{
				String QQtoSave = data.content.split("#saveA ")[1];
				new Thread(new Runnable()
				{
					public void run()
					{
						for (int i = 0; i < 10; ++i)
						{
							pause(); 
							send(createData(false, QQtoSave), i + "\n" + i);
						}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.startsWith("#saveW "))//仅群聊
			{
				String QQtoSave = data.content.split("#saveW ")[1];
				new Thread(new Runnable()
				{
					public void run()
					{
						for (int i = 0; i < 10; ++i)
						{
							pause();
							send(createData(true, QQtoSave), i + "\n" + i);
						}
						if (isToast)
						{
							Toast("操作成功结束！");
						}
					}
				}).start();
			}
			else if (data.content.toLowerCase().equals("#secure on"))
			{
				isSecure = true;
				Toast("安全监控已打开");
			}
			else if (data.content.toLowerCase().equals("#secure off"))
			{
				isSecure = false;
				Toast("安全监控已关闭");
			}
			else if (data.content.equals("#send"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						if (reply.length <= 0)
						{
							Toast("列表为空，请使用 #add 指令！");
							return;
						}
						for (int i = 0; i < reply.length; ++i)
						{
							pause();
							send(data, reply[i]);
						}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.startsWith("#send ") && (!data.content.equals("#send ")))
			{
				int n = 1, lp = 0;
				String loopStrings = "", tmpLoopStrings = "";
				try
				{
					n = Math.abs(Integer.parseInt(data.content.split(" x")[1]));
					loopStrings = data.content.split("#send ")[1].split(" x")[0];
				}
				catch (ArrayIndexOutOfBoundsException e1)
				{
					try
					{
						lp = Math.abs(Integer.parseInt(data.content.split(" r")[1]));
						n = 1;
						tmpLoopStrings = data.content.split("#send ")[1].split(" r")[0];
						loopStrings = tmpLoopStrings;
						for (int i = 1; i < lp; ++i)
							loopStrings += tmpLoopStrings;
					}
					catch (ArrayIndexOutOfBoundsException e2)
					{
						try
						{
							lp = Math.abs(Integer.parseInt(data.content.split(" s")[1]));
							lp = (lp > 0 ? lp : 1);
							tmpLoopStrings = data.content.split("#send ")[1].split(" s")[0];
							int i = 0;
							for (; i + lp < tmpLoopStrings.length(); i += lp)
							{
								send(data, tmpLoopStrings.substring(i, i + lp));
								pause();
							}
							send(data, tmpLoopStrings.substring(i));
							return;
						}
						catch (ArrayIndexOutOfBoundsException e3)
						{
							try
							{
								tmpLoopStrings = data.content.split("#send ")[1].split(" f")[0];
								String tmpRStrings = data.content.split("#send ")[1].split(" f")[1];//取后方
								if (tmpRStrings.contains(","))
									tmpRStrings = tmpRStrings.replaceAll(",", ":");
								if (tmpRStrings.contains("["))
									tmpRStrings = tmpRStrings.replaceAll("\\[", "");
								if (tmpRStrings.contains("]"))
									tmpRStrings = tmpRStrings.replaceAll("\\]", "");
								int aa = Integer.parseInt(tmpRStrings.split(":")[0]), bb = Integer.parseInt(tmpRStrings.split(":")[1]), cc = Integer.parseInt(tmpRStrings.split(":")[2]), dd = 0;
								try
								{
									dd = Math.abs(Integer.parseInt(tmpRStrings.split(":")[3]));
								}
								catch (Exception e)
								{
									dd = 0;//0 为不受限
								}
								new Thread(new Runnable()
								{
									public void run()
									{
										if (isToast)
											Toast("开始执行操作，如果陷入死循环或发生线程死锁，请取消加载或重启 QQ。");
										for (int i = aa, zz = 0; (i != bb) && (dd == 0 ? true : zz < dd); i += cc)
										{
											loopStrings = "";
											++zz;
											if (i <= 0)
												continue;
											for (int j = 0; j < i; ++j)
												loopStrings += tmpLoopStrings;
											pause();
											send(data, loopStrings);
										}
										if (isToast)
											Toast("所有信息发送完毕！");
									}
								}).start();
								return;//记得返回
							}
							catch (ArrayIndexOutOfBoundsException e4)
							{
								try
								{
									tmpLoopStrings = data.content.split("#send ")[1].split(" d")[0];
									int delimsTimes = Math.abs(Integer.parseInt(data.content.split("#send ")[1].split(" d")[1]));//取后方
									new Thread(new Runnable()
									{
										public void run()
										{
											if (isToast)
											{
												Toast("开始执行操作，如果陷入死循环或发生线程死锁，请取消加载或重启 QQ。");
											}
											int i = 0;
											for (; i + delimsTimes < tmpLoopStrings.length(); i += delimsTimes)
											{
												pause();
												send(data, tmpLoopStrings.substring(i, i + delimsTimes));
											}
											if (i < tmpLoopStrings.length())
											{
												pause();
												send(data, tmpLoopStrings.substring(i));
											}
											if (isToast)
												Toast("所有信息发送完毕！");
										}
									}).start();
									return;//记得返回
								}
								catch(Throwable e5)
								{
									loopStrings = data.content.split("#send ")[1];
									n = 1;
								}
							}
						}
					}
				}
				new Thread(new Runnable()
				{
					public void run()
					{
						for (int i = 0; i < n; ++i)
						{
							send(data, loopStrings);
							pause();
						}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.isGroup && (data.content.toLowerCase().equals("#sfc") || data.content.toLowerCase().startsWith("#sfc ")))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							String blackHere = "";
							for (Object target : getGroupMembers(data.friendUin))
							{
								if (target.memberuin.equals(mQQ))
									continue;//略过自己 QQ
								else if (blackQQ.find(target.memberuin))
								{
									blackHere += target.memberuin + "\n";
									if (isAdmin(data.friendUin))
									{
										if (data.content.toLowerCase().contains(" /f") || data.content.toLowerCase().contains(" -f"))
											shutUp(data.friendUin, target.memberuin, 1296000);
										else if (data.content.toLowerCase().contains(" /x") || data.content.toLowerCase().contains(" -x"))
											removeTroop(data.friendUin, target.memberuin, false);
										else if (data.content.toLowerCase().contains(" /r") || data.content.toLowerCase().contains(" -r"))
											removeTroop(data.friendUin, target.memberuin, true);
									}
								}
							}
							if (blackHere.length() == 0)
								sendReply(data, "恭喜，本群暂未发现黑名单成员。");
							else
							{
								blackHere = blackHere.substring(0, blackHere.length() - 1);
								String[] blackH = blackHere.split("\n");
								blackHere += "\n\n以上 " + blackH.length + " 位群成员位于黑名单中。";
								if (data.content.toLowerCase().contains(" /a") || data.content.toLowerCase().contains(" -a"))
								{
									blackHere += "\n请以上黑名单中的成员自觉退出该群，如有误报请在群内反馈。";
									send(data, blackHere, blackH);
								}
								else
									sendReply(data, blackHere);
							}
						}
						catch (Throwable suberror)
						{
							Toast(scriptName + " 子线程于 #sfc 处发生异常，抛出异常信息如下：\n" + suberror);
						}
					}
				}).start();
			}
			else if (data.content.startsWith("#shake "))
			{
				int n = Integer.parseInt(data.content.split("#shake ")[1]);
				new Thread(new Runnable()
				{
					public void run()
					{
						for (int i = 0; i < n; ++i)
						{
							sendShake(data);
							pause();
						}
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.isGroup && data.content.toLowerCase().startsWith("#shut "))
			{
				if (data.atList.length == 0)
				{
					String QQ = data.content.toLowerCase().split("#shut ")[1].split(" ")[0];
					if (!friendQQ.find(QQ))
					{	
						long setTimer = Long.parseLong(data.content.toLowerCase().split("#shut ")[1].split(" ")[1]);
						setTimer = Math.abs(setTimer);//使用绝对值处理负数
						long RealTimer = Math.abs(setTimer * 60);//转为分钟并防止算术溢出造成错误
						new Thread(new Runnable()
						{
							public void run()
							{
								if (RealTimer == 0)
								{
									shutUp(data.friendUin, QQ, RealTimer);
									Toast("解禁请求已发送！");
								}
								else if (RealTimer < dealShut[4] && RealTimer > 0)
								{
									shutUp(data.friendUin, QQ, RealTimer);
									Toast("禁言请求已发送！");
								}
								else
									Toast("数据非法，应为小于 " + (int)(dealShut[4] / 60) + " 的自然数！");
							}
						}).start();
					}
				}
				else
				{
					String mySetTimer = data.content.toLowerCase().split("#shut ")[1];
					if (mySetTimer.contains("@"))//定位到第一个@
						mySetTimer = mySetTimer.split("@")[0];
					while (mySetTimer.startsWith(" "))//先排除 #shut 后面接了多个空格的情况
						mySetTimer = mySetTimer.substring(1);
					if (mySetTimer.contains(" "))//然后去掉空格
						mySetTimer = mySetTimer.split(" ")[0];
					long setTimer = Long.parseLong(mySetTimer);
					setTimer = Math.abs(setTimer);//使用绝对值处理负数
					long RealTimer = Math.abs(setTimer * 60);//转为分钟并防止算术溢出造成错误
					if (RealTimer >= 259200 || RealTimer < 0)
					{
						Toast("数据非法，应为小于 43200 的自然数！");
						return;
					}
					new Thread(new Runnable()
					{
						public void run()
						{
							for (String u : data.atList)
								if (!friendQQ.find(u))
								{
									pause();
									shutUp(data.friendUin, u, RealTimer);
								}
							Toast("所有请求发送完毕！");
						}
					}).start();
				}
			}
			else if (data.isGroup && data.content.equals("#shutAll"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						shutUp(data.friendUin, true);
						if (isToast)
							Toast("全体禁言请求发送成功！");
					}
				}).start();
			}
			else if (data.isGroup && data.content.startsWith("#shutAll "))
			{
				long setTimer = Math.abs(Long.parseLong(data.content.split("#shutAll ")[1]));
				long RealTimer = Math.abs(setTimer * 60000);//转为分钟并防止算术溢出造成错误
				if (RealTimer >= 259200000 || RealTimer <= 0)
				{
					Toast("数据非法，应为小于 43200 的自然数！");
					return;
				}
				new Thread(new Runnable()
				{
					public void run()
					{
						shutUp(data.friendUin, true);
						if (isToast)
							Toast("全体禁言请求发送成功！");
						try
						{
							Thread.sleep(RealTimer);
							shutUp(data.friendUin, false);
						}
						catch (InterruptedException e)
						{
							Toast("计时中断，请手动解禁！");
						}
					}
				}).start();
			}
			else if (data.content.startsWith("#tip "))
			{
				String sendText = data.content.split("#tip ")[1];
				new Thread(new Runnable()
				{
					public void run()
					{
						pause();
						sendTip(data, sendText);
						if (isToast)
							Toast(sendText);
					}
				}).start();
			}
			else if (data.content.equals("#Terminal Off"))
			{
				Toast(scriptName + " 主线程已暂停运行！");
				Terminal_Flag = false;
			}
			else if (data.content.toLowerCase().equals("#toast on"))
			{
				isToast = true;
				Toast("提示已打开");
			}
			else if (data.content.toLowerCase().equals("#toast off"))
			{
				isToast = false;
				Toast("提示已关闭");
			}
			else if (data.content.toLowerCase().equals("#tranon"))
			{
				if (isTran)
					Toast("已打开，请勿重复打开！");
				else
				{
					isTran = true;
					if (isToast)
						Toast("自动翻译小尾巴已打开");
				}
			}
			else if (data.content.toLowerCase().equals("#tranoff"))
			{
				if (isTran)
				{
					isTran = false;
					if (isToast)
						Toast("自动翻译小尾巴已关闭");
				}
				else
					Toast("已关闭，请勿重复关闭！");
			}
			else if (data.content.toLowerCase().equals("#tran") || data.content.toLowerCase().equals("#tran "))
			{
				delimText = "\n";
				if (isToast)
				{
					Toast("重置成功！");
				}
			}
			else if (data.content.startsWith("#Tran"))
			{
				delimText = data.content.split("#Tran ")[1];
				if (isToast)
					Toast("设置成功！");
			}
			else if (data.content.toLowerCase().equals("#undef reply"))
			{
				reply = new String[0];
				if (isToast)
					Toast("Send 列表已清空！");
			}
			else if (data.content.toLowerCase().equals("#welcome on"))
			{
				if (data.isGroup)
				{
					boolean[] bRet = welcomeList.add(data.friendUin);
					if (isToast)
					{
						if (!bRet[1])
							Toast("已开启，无需重复开启！");
						else if (bRet[0])
							Toast("操作成功结束！");
						else
							Toast("操作失败！");
					}
				}
				else
					Toast("本功能仅限群聊使用！");
			}
			else if (data.content.toLowerCase().equals("#welcome off"))
			{
				if (data.isGroup)
				{
					boolean[] bRet = welcomeList.remove(data.friendUin);
					if (isToast)
					{
						if (!bRet[1])
							Toast("未开启，无需执行关闭！");
						else if (bRet[0])
							Toast("操作成功结束！");
						else
							Toast("操作失败！");
					}
				}
				else
					Toast("本功能仅限群聊使用！");
			}
			else if (data.content.toLowerCase().equals("#whoami"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						sendReply(data, mName + " " + mQQ);
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
			}
			else if (data.content.startsWith("查询"))
			{
				if (data.content.equals("查询开"))
				{
					Search_Flag = true;
					if (isToast)
						Toast("查询已打开！");
				}
				else if (data.content.equals("查询关"))
				{
					Search_Flag = false;
					if (isToast)
						Toast("查询已关闭！");
				}
				else if (Search_Flag)//总开关
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							try
							{
								if (data.atList.length > 0 && data.content.startsWith("查询"))
									for (String target : data.atList)
									{
										Search search = new Search(0, target);
										sendReply(data, search.toString());
									}
								else
								{
									if (data.content.startsWith("查询QQ"))
									{
										Search search = new Search(0, data.content.substring(4));
										sendReply(data, search.toString());
									}
									else if (data.content.startsWith("查询手机") || data.content.startsWith("查询电话"))
									{
										Search search = new Search(1, data.content.substring(4));
										sendReply(data, search.toString());
									}
									else if (data.content.startsWith("查询LOL"))
									{
										Search search = new Search(2, data.content.substring(5));
										sendReply(data, search.toString());
									}
									else if (data.content.startsWith("查询微博"))
									{
										Search search = new Search(3, data.content.substring(4));
										sendReply(data, search.toString());
									}
								}
							}
							catch (Throwable search_exception)
							{
								Toast(scriptName + " 查询子线程发生异常，请检查您的指令。捕获异常信息：\n" + search_exception);
							}
						}
					}).start();
				}
			}
			else if (data.isGroup && welcomeList.find(data.friendUin) && data.content.startsWith("投票清零") &&  data.atList.length != 0)
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						if (data.content.startsWith("投票清零禁言"))
						{
							for (String target : data.atList)
								if (shutMap.get(data.friendUin + "/" + target) != null)
									shutMap.remove(data.friendUin + "/" + target);
						}
						else if (data.content.startsWith("投票清零踢人"))
						{
							for (String target : data.atList)
								if (kickMap.get(data.friendUin + "/" + target) != null)
									kickMap.remove(data.friendUin + "/" + target);
						}
						else
						{
							for (String target : data.atList)
								if (shutMap.get(data.friendUin + "/" + target) != null)
									shutMap.remove(data.friendUin + "/" + target);
							for (String target : data.atList)
								if (kickMap.get(data.friendUin + "/" + target) != null)
									kickMap.remove(data.friendUin + "/" + target);
						}
						if (isToast)
						{
							send(data, "投票清零完成！");
							Toast("清零完毕！");
						}
					}
				}).start();
				return;
			}
		}
		
		/* 他人的命令 */
		else
		{
			if (data.isGroup)
			{
				if (autoReply.containsKey(data.friendUin + "/" + data.sendUin))//针对“群/个人”的自动回复
				{
					send(data, autoReply.get(data.friendUin + "/" + data.sendUin));
					return;
				}
				else if (autoReply.containsKey("/" + data.sendUin))//针对“*/个人”的自动回复
				{
					send(data, autoReply.get("/" + data.sendUin));
					return;
				}
				else if (autoReply.containsKey(data.friendUin + "/"))//针对“群/*”的自动回复
				{
					send(data, autoReply.get(data.friendUin + "/"));
					return;
				}
				if (isInLists(data.friendUin, recallListGroup))
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							pause();
							record(data);
						}
					}).start();
					return;//自动撤回则不继续进行
				}
				if (welcomeList.find(data.friendUin))
				{
					if (data.content.startsWith("#header ") && isAdmin(data.friendUin))
					{
						String myHeader = data.content.split("#header ")[1];
						new Thread(new Runnable()
						{
							public void run()
							{
								setTitle(data.friendUin, data.sendUin, myHeader);
							}
						}).start();
					}
					else if (data.content.toLowerCase().startsWith("#sleep ") || data.content.equals("闭关")  || data.content.equals("睡觉套餐"))
					{
						long setTimer = 0;
						if (data.content.toLowerCase().startsWith("#sleep "))
						{
							try
							{
								setTimer = Integer.parseInt(data.content.toLowerCase().split("#sleep ")[1]);
							}
							catch (NumberFormatException nfe)
							{
								int max = 1000, min = 100;
								setTimer = (int)(Math.random() * (max - min) + min);
								send(data, "数值异常，作随机化超长时间禁言处理！");
							}
						}
						else if (data.content.equals("闭关"))
							setTimer = getRandom((int)(dealShut[0] / 60), (int)(dealShut[1] / 60));
						else if (data.content.equals("睡觉套餐"))
							setTimer = getRandom((int)(dealShut[1] / 60), (int)(dealShut[2] / 60));
						else
							return;
						setTimer = Math.abs(setTimer);//使用绝对值处理负数
						long RealTimer = Math.abs(setTimer * 60);//分钟转为秒钟并防止算术溢出造成错误
						new Thread(new Runnable()
						{
							public void run()
							{
								String msg = null;
								String[] AtLists = { data.sendUin };
								if (RealTimer == 0)
									msg = data.nickName + "，您未被禁言，无需解禁。";
								else if (RealTimer < dealShut[4] && RealTimer > 0)
								{
									shutUp(data.friendUin, data.sendUin, RealTimer);
									msg = data.nickName + "，您已被成功禁言 " + setTimer + " 分钟！祝闭关修仙成功，美梦成真～";
								}
								else
									msg = data.nickName + "，数据不合法，该值应为小于 " + (int)(dealShut[4] / 60) + " 的自然数！";
								pause();
								send(data, msg, AtLists);
							}
						}).start();
					}
					else if (data.content.equals("抽签"))
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								String[] AtLists ={ data.sendUin };
								send(data, "" + getRandom(0, dealShut[4]), AtLists);
							}
						}).start();
					}
					else if (data.content.equals("打卡") || data.content.equals("打卡打卡"))
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								String[] AtLists ={ data.sendUin };
								if (clockMap.get(data.sendUin) == null)
								{
									clockMap.put(data.sendUin, 1);
									send(data, data.nickName + "打卡成功，卡说：“啊！好痛！！轻点！！！”", AtLists);
								}
								else
								{
									int clock_time = clockMap.get(data.sendUin) + 1;
									clockMap.put(data.sendUin, clock_time);
									send(data, data.nickName + "打卡" + clock_time + "连击 成功，卡说：“啊！好痛！！轻点！！！”", AtLists);
								}
							}
						}).start();
					}
					else if (data.content.equals("冒泡"))
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								String[] AtLists ={ data.sendUin };
								send(data, data.nickName + "，冒泡成果，群主或管理员已收到您的泡泡，将会在近期的不活跃成员清理中将您排除，感谢您的参与～～～", AtLists);								
							}
						}).start();
					}
					else if (data.content.equals("签到"))
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								String[] AtLists ={ data.sendUin };
								if (signMap.get(data.sendUin) == null)
								{
									signMap.put(data.sendUin, 1);
									send(data, data.nickName + "，签到成功，群主或管理员已收到您的签到，感谢您的参与～～～", AtLists);
								}
								else
								{
									int sign_time = signMap.get(data.sendUin) + 1;
									signMap.put(data.sendUin, sign_time);
									send(data, data.nickName + "，签到成功，目前已签到了 " + sign_time + " 次～～～”", AtLists);
								}
								
							}
						}).start();
					}
					else if (data.content.equals("签退"))
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								String[] AtLists ={ data.sendUin };
								if (signMap.get(data.sendUin) == null)
									send(data, data.nickName + "，您还未签到，无法签退，请先签到哦！", AtLists);
								else
								{
									signMap.remove(data.sendUin);
									send(data, data.nickName + "，签退成功，群主或管理员已收到您的签退，去玩耍吧～～～", AtLists);
								}
							}
						}).start();
					}
					else if (isInLists(data.content, Requests))
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								int rr = getRandom(1, 10);
								String toResponse = "不在";
								for (int i = 0; i < rr; ++i)
									toResponse += "～";
								sendResponse(data, toResponse);
							}
						}).start();
					}
					else if (!data.sendUin.equals(lastFire) && isSameChar(data.content) && autoFire.contains(data.content.substring(0, 1)))
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								lastFire = data.sendUin;
								String toReply = "";
								for (int i = 0; i < data.content.length(); ++i)
									toReply += "鹅";
								send(data, toReply);
							}
						}).start();
						return;
					}
					else if (data.atMe)
					{
						new Thread(new Runnable()
						{
							public void run()
							{
								for (String F : TreatMeBad)
									if (data.content.toLowerCase().startsWith(F.toLowerCase()))
									{
										sendBad(data, true);
										return;//节省时间
									}
								if (data.sendUin.equals(lastAtMe))
									sendBad(data, true);
								else
								{
									lastAtMe = data.sendUin;
									boolean isError = false;
									for (String slk : replyVoice)
									{
										pause();
										try
										{						
											sendPtt(data, slk);
										}
										catch (Throwable ptt_error)
										{
											isError = true;
										}
									}
									if (isError)
										Toast("发送语音过程发生异常，请检查相关语音文件是否存在！");
									String ori_nick = getGroupMemberInfo(data.friendUin, data.sendUin).troopnick;
									if (ori_nick.length() > 16)									
										alterNickName(data.friendUin, data.sendUin, "坏人（" + ori_nick.substring(0, ori_nick.length() - 5) + "…）");
									else
										alterNickName(data.friendUin, data.sendUin, "坏人（" + ori_nick + "）");
									String[] AtLists = { data.sendUin };
									send(data, "@坏人（" + ori_nick + "）", AtLists);
									setTitle(data.friendUin, data.sendUin, "宇宙第一坏蛋");
								}
							}
						}).start();
					}
					//这里不执行 return，执行了 welcomeList 的还可以执行swipeGroup
				}

				if (isInLists(data.friendUin, swipeGroup))//放在后面处理以节省时间
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							if ((!data.content.equals(lastText)) && (!data.content.startsWith("#")) && (!data.content.startsWith("投票")))//防止相互调用复读或触发指令
							{
								lastText = data.content;
								send(data, (data.content.startsWith(escText) ? escText : "") + escText + data.content);//避免 escText 被转义
							}
						}
					}).start();
				}
			}
			else
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						if (autoReply.containsKey(data.sendUin))//针对个人的自动回复
						{
							send(data, autoReply.get(data.sendUin));
							return;
						}
						if (isInLists(data.content, Requests))
						{
							int rr = getRandom(1, 10);
							String toResponse = "不在";
							for (int i = 0; i < rr; ++i)
								toResponse += "～";
							sendResponse(data, toResponse);
							return;//节省时间
						}
					}
				}).start();
			}
		}
		return;
	}
	catch (Throwable onmsgerror)
	{
		Toast(scriptName + " 主线程发生异常，请检查您的指令。捕获异常信息：\n" + onmsgerror);
	}
	return;
}

public void onCardMsg(Object data)
{
	if (!Terminal_Check_Flag(data.isGroup ? data.friendUin : ""))
		return;
	try
	{
		if (!secureAllow(data))
			return;
		if (!data.sendUin.equals(mQQ))//非自己 QQ
		{				
			if (data.isGroup && isInLists(data.friendUin, recallListGroup))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						pause();
						record(data);
					}
				}).start();
				return;
			}
		}
		if (!(data.sendUin.equals(mQQ) || data.content.equals(lastText)) && isInLists(data.friendUin, swipeGroup))
		{
			lastText = data.content;
			if (repeatEnforce)
			{
				send(data, data.content);
				pause();
			}
			sendCard(data, data.content);
		}
	}
	catch (Throwable e)
	{
		Toast(e + "");
	}
	return;
}

public void onPicMsg(Object data)
{
	if (!Terminal_Check_Flag(data.isGroup ? data.friendUin : ""))
		return;
	try
	{
		if (!secureAllow(data))
			return;
		if (!data.sendUin.equals(mQQ) && data.isGroup && isInLists(data.friendUin, recallListGroup))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					pause();
					record(data);
				}
			}).start();
			return;
		}
		if (data.sendUin.equals(mQQ) || data.content.equals(lastText))
			return;
		if (isInLists(data.friendUin, swipeGroup))
		{
			lastText = data.content;
			if (repeatEnforce)
			{
				send(data, data.content);
				pause();
			}
			sendPhoto(data, data.content);
			if (repeatEnforce)
				for (int i = 0; i <= 5; ++i)
				{
					pause();
					sendShowPhoto(data, data.content, i);
				}
		}
	}
	catch (Throwable e)
	{
		Toast(e + "");
	}
	return;
}

public void onReplyMsg(Object data)
{
	if (!Terminal_Check_Flag(data.isGroup ? data.friendUin : ""))
		return;
	try
	{
		if (!secureAllow(data))
			return;
		if (data.sendUin.equals(mQQ) || (data.isGroup && friendQQ.find(data.sendUin)))//可以手动添加代管 QQ
		{
			if (data.content.toLowerCase().equals("#get"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						sendReply(data, "" + data.source);
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
				return;
			}
			if (data.content.toLowerCase().equals("#recall"))
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						record(data.source);
						if (isToast)
							Toast("操作成功结束！");
					}
				}).start();
				return;
			}
		}
	}
	catch (Throwable e)
	{
		Toast("发生异常，请检查您的指令。\n抛出异常信息如下：\n" + e);
	}
	return;
}

public void onMixedMsg(Object data)
{
	if (!Terminal_Check_Flag(data.isGroup ? data.friendUin : ""))
		return;
	new Thread(new Runnable()
	{
		public void run()
		{
			try
			{
				if (data.sendUin.equals(mQQ))
				{
					if (data.content[0].type == 1 && data.content[0].content.toLowerCase().startsWith("#rp"))
					{
						int rpTimes = 0;
						try
						{
							if (data.content[0].content.toLowerCase().startsWith("#rp "))
								rpTimes = Integer.parseInt(data.content[0].content.toLowerCase().split("#rp ")[1]);
							else
								rpTimes = 0;
						}
						catch (Throwable suberror)
						{
							rpTimes = 0;
						}
						if (rpTimes > 0)
						{
							for (int i = 0; i < rpTimes; ++i)
								for (int j = 1; j < data.content.length; ++j)
									if (data.content[j].type == 2)//是图片
									{
										pause();
										sendPhoto(data, data.content[j].content);
									}
						}
						else
							for (int i = 1; i < data.content.length; ++i)
								for (int j = 0; j <= 5; ++j)
									if (data.content[i].type == 2)//是图片
									{
										pause();
										sendShowPhoto(data, data.content[i].content, j);
									}
						if (isToast)
							Toast("操作成功结束，发送结束时间取决于您的网络状况。");
					}
				}
				else
				{
					if (data.isGroup)
						for (int i = 0; i < data.content.length; ++i)
						{
							Object subdata = data.content[i];
							secureAllow(subdata);
						}
				}
			}
			catch (Throwable e)
			{
				Toast(e + "");
			}
		}
	}).start();
	return;
}


public String getMsg(String msg)//需要复读机开启小尾巴
{
	if (!Terminal_Check_Flag(""))//总开关被关闭
		return msg;
	if (msg.endsWith(escText))//清空文字
		return "";
	if (msg.startsWith(escText))//临时解除翻译
		return msg.substring(1, msg.length());
	if (
		((!isTran) || msg.startsWith("#") || msg.startsWith("//") || msg.startsWith("查询") || msg.startsWith("投票") || isEmojiOnly(msg) || isoook(msg))//不翻译注释、指令和表情等
		|| (msg.length() <= tailLength || isAllP(msg) || isAllE(msg) || isSameChar(msg) || msg.contains("%") || msg.contains(delimText))//单字或仅含有标点符号不翻译（含有 % 不作处理否则会出错）
		|| (msg.contains("http://") || msg.contains("https://") || msg.contains("<?xml ") || msg.startsWith("{\"app\":") || msg.contains(".com"))//链接和卡片消息不翻译
	)
		return msg;
	String from = "auto", to = "";//源语言（默认自动检测）
	if (isContainsZH(msg))//纯中文
		to = "en";//目标语言（默认英文 en）
	else//否则转中文
		to = "zh";
	try
	{
		final String[] res = { "" };
		final boolean[] flag = { false };
		new Thread(new Runnable()
		{
			public void run()
			{
				String salt = String.valueOf(System.currentTimeMillis());
				Result result = null;
				String sign = APP_ID + msg + salt + SECURITY_KEY;
				String md5 = MD5.md5(sign);
				String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q=" + msg + "&from=" + from + "&to=" + to + "&appid=" + APP_ID + "&salt=" + salt + "&sign=" + md5;
				BugHttpClient bug = new BugHttpClient();
				result = bug.get(null, url);
				res[0] = result.getResult();
				flag[0] = true;
			}
		}).start();
		for (int i = 0; i < 300; ++i)
		{
			if (flag[0])
				break;
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException ie) {}
		}
		JSONObject json = new JSONObject(res[0]);
		String ret = json.getJSONArray("trans_result").getJSONObject(0).getString("dst");
		try
		{
			res[0] = URLDecoder.decode(ret, "UTF-8");
		}
		catch (Exception e)
		{
			Toast(e + "");
		}
		return msg + delimText + res[0];
	}
	catch (Throwable e) {}
	return msg;
}

public void onJoin(String GroupUin, String QQUin, String nickName)
{
	if (!Terminal_Check_Flag(GroupUin))
		return;
	new Thread(new Runnable()
	{
		public void run()
		{
			try
			{
				if (blackQQ.find(QQUin))
				{
					if (isAdmin(GroupUin))
					{
						removeTroop(GroupUin, QQUin, false);
						send(createData(true, GroupUin), "用户 " + QQUin + " 被踢出。\n踢出来源：全局黑名单扫描。");
					}
					else
						send(createData(true, GroupUin), "警告：用户 " + QQUin + " 位于黑名单中。\n请注意安全，谨防诈骗！");
					return;
				}
				else if (dirtyList.find(nickName, false))
				{
					if (isAdmin(GroupUin))
					{
						shutUp(GroupUin, QQUin, dealShut[4]);
						send(createData(true, GroupUin), "用户 " + QQUin + " 被禁言。\n禁言原因：昵称含有违禁词。");
					}
					else
						send(createData(true, GroupUin), "警告：用户 " + QQUin + " 昵称含有违禁词。\n请注意安全，谨防诈骗！");
					return;
				}
				else if (welcomeList.find(GroupUin))
				{
					String[] AtLists = { QQUin };
					if (friendQQ.find(QQUin))
						send(createData(true, GroupUin), nickName + "，您好，欢迎小萌新加入，来了就别想走了哦！", AtLists);
					else
					{
						pause();
						int random = getRandom(1000, 5000);
						send(createData(true, GroupUin), nickName + "，您好，欢迎小萌新加入，来了就别想走了哦！\n为防止广告色情类机器人入群，您需要在 " + upperLimit + " 秒内将四位随机验证码→ " + random + " ←发送到群里通过人机检验，否则将会被禁言 " + transTime(dealShut[3]) + " 哦！", AtLists);
						verMap.put(GroupUin + "/" + QQUin, "" + random);
						for (int i = 0; i < upperLimit; ++i)
						{
							try
							{
								Thread.sleep(1000);
							}
							catch (InterruptedException ie) {}
						}				
						if (verMap.get(GroupUin + "/" + QQUin) != null)
						{
							verMap.remove(GroupUin + "/" + QQUin);
							shutUp(GroupUin, QQUin, dealShut[3]);
							send(createData(true, GroupUin), nickName + "，很抱歉，您已超时，请联系群主或群管理解除禁言。", AtLists);
						}
					}
				}
			}
			catch (Throwable e)
			{
				Toast(scriptName + " 子线程于 进群监控 处发生异常，抛出异常信息如下：\n" + e);
			}
		}
	}).start();
	return;
}

public void onExit(String GroupUin, String QQUin, boolean isRightNow)
{
	if (!Terminal_Check_Flag(GroupUin))
		return;
	new Thread(new Runnable()
	{
		public void run()
		{
			if (welcomeList.find(GroupUin))
				send(createData(true, GroupUin), "退群监控：" + QQUin + " 于 " + getTime() + (isRightNow ? " " : "前 ") + "退出了此群（或被群主或管理员踢出此群）。");
		}
	}).start();
	return;
}

public void onShutUp(String fromUin, String GroupUin, String toUin, String time)
{
	if (!Terminal_Check_Flag(GroupUin))
		return;
	new Thread(new Runnable()
	{
		public void run()
		{
			if (welcomeList.find(GroupUin))
				send(createData(true, GroupUin), "禁言监控：" + toUin + " 于 " + getTime() + " 被 " + fromUin + " 禁言 " + time + "。");
		}
	}).start();
	return;
}

public void onShutUp(String fromUin, String GroupUin, boolean isOn)
{
	if (!Terminal_Check_Flag(GroupUin))
		return;
	new Thread(new Runnable()
	{
		public void run()
		{
			if (welcomeList.find(GroupUin))
			{
				send(createData(true, GroupUin), "禁言监控：本群于 " + getTime() + (isOn ? " 开启" : " 关闭") + "了全体禁言。");
			}
		}
	}).start();
	return;
}

public void onUnload()
{
	tipTime = 0;
	Toast(scriptName + " 取消加载成功！\n时间扑面而来，我们终将释怀。");
	return;
}


/* 附录 */
/*
已定义全局变量
context  QQ 上下文
mName  自己的昵称
mQQ  自己的 QQ 号

可以监听的接口
onMsg(data)接收到文字消息将会调用
onCardMsg(data)接收到卡片消息将会调用
onPicMsg(data)接收到图片消息将会调用
onRawMsg(data)接收原始消息
onReplyMsg(data)接收回复消息，data.source为被回复消息
onMixedMsg(data)图文消息，data.content实际是data[]，获取每一项请使用data.content[下标],或使用for获取所有，可以根据data.type判断是文字还是图片
onUnload() 实现此方法，可以监测脚本取消加载
onJoin(String, String)监听进群，群号，qq号
onJoin(String qun, String uin, String nickName)监听进群：参数是群号、QQ号、昵称，优先调用新接口，未实现新接口时调用原先监听接口（onJoin(String qun, String uin)）
onExit(String qun, String qq, boolean self)监听退群：参数是群号、QQ号、是否自己退群（false为被踢）
onShutUp(String, String, String, String)监听禁言：操作者、群号、被禁言者、时间
onShutUp(String, String, boolean)监听禁言：操作者、群号、是否全体禁言

data 参数字段
int type  消息类型 1文字 2图片 3xml卡片 4json卡片 5回复消息 6图文消息 0未处理原始消息
boolean atMe  是否@自己
String content  文字内容
String friendUin  如果是群聊，为群号，否则为发送者QQ号
boolean isGroup  是否群聊
String nickName  昵称
String sendUin  发送者 QQ
long time  消息时间
String[] atList  @列表

定义的方法
Toast(String) 弹出提示
print(String) 输出日志
load(String) 加载指定位置的java文件
createData(boolean，String) 创建会话，是否群聊，群号码
createPrivateChatData(String，String) 创建群私聊会话，群号码，qq号码
getGroups() 获取群聊列表，类型为数组，单个对象字段有：String uin 群号，String name 群名称
getGroupMembers(String) 获取指定群号所有成员，群号
getGroupMemberInfo(String, String) 获取指定群号指定成员，群号，qq号
getFriends() 获取所有好友
getFriendsInfo(String) 获取指定好友
alterNickName(String, String, String)设置昵称，群号，qq号，昵称
shutUp(String, String, long)禁言，群号，qq号，禁言时间，0为解禁
shutUp(String, boolean)全体禁言，群号，开关
removeTroop(String, String, boolean)踢人，群号，qq号，是否不再接收加群
removeTroop(String, ArrayList, boolean)踢人，群号，qq号列表，是否不再接收加群
setTitle(String, String, String)设置群头衔，群号，qq号，头衔
send(MessageData, String) 发送消息
send(MessageData, String, String[]) 发送消息,带@列表
sendCard(MessageData, String) 发送卡片消息
sendPhoto(MessageData, String) 发送图片消息，参数2为路径
sendShowPhoto(MessageData, String, int) 发送秀图消息，参数2为路径，参数3为flag 0-5 秀图 幻影 抖动 生日 爱你 征友
sendPtt(MessageData, String) 发送语音消息，参数2为路径
sendShake(MessageData) 发送抖动
sendTip(MessageData, String) 发送Tip提示
record(MessageData) 撤回这条消息
getCurrActivity() 获取当前前台Activity
loadDex(String) 加载dex文件，获取类加载器，参数路径
loadApp(String) 加载已安装app，获取类加载器
sendReply(MessageData, String) 对消息进行回复，参数2是回复内容
sendReply(MessageData, String[][]) 对消息进行回复，参数2是回复内容
sendMixed(MessageData, String[][]) 发送图文消息，参数2为二维字符串数组，格式new String[][]{{"1","文字"},{"2","本地图片路径"}}
getNickName(String, String)，参数：群号、QQ号
getCookie(String) 获取cookie，参数host(如qzone.qq.com)、调用需要用户授权

群信息字段
String troopnick		群昵称
String friendnick		昵称
String memberuin		QQ号
int level			等级
long last_active_time		最后发言时间
long join_time		加群时间
String alias		化名
byte age			年龄
int distance		距离
byte sex			性别

好友信息字段
int age			年龄
String alias		化名
byte isIphoneOnline		是否手机 QQ 在线
boolean isMqqOnLine	是否 Mqq 在线
String name		昵称
String signature		签名
String singerName
String uin			QQ 号
byte memberLevel		账号等级
*/

/**
 * 源语言语种不确定时可设置为 auto，目标语言语种不可设置为 auto。但对于非常用语种，语种自动检测可能存在误差。
 * | 名称          | 代码 | 名称       | 代码 | 名称       | 代码 |
 * | ------------ | ---- | ---------- | ---- | ---------- | ---- |
 * | 自动检测      | auto | 中文       | zh   | 英语       | en   |
 * | 粤语         | yue  | 文言文     | wyw  | 日语       | jp   |
 * | 韩语         | kor  | 法语       | fra  | 西班牙语   | spa  |
 * | 泰语         | th   | 阿拉伯语   | ara  | 俄语       | ru   |
 * | 葡萄牙语     | pt   | 德语       | de   | 意大利语   | it   |
 * | 希腊语       | el   | 荷兰语     | nl   | 波兰语     | pl   |
 * | 保加利亚语    | bul  | 爱沙尼亚语 | est  | 丹麦语     | dan  |
 * | 芬兰语       | fin  | 捷克语     | cs   | 罗马尼亚语 | rom  |
 * | 斯洛文尼亚语  | slo  | 瑞典语     | swe  | 匈牙利语   | hu   |
 * | 繁体中文     | cht  | 越南语     | vie  |
 * 
 */


Toast(scriptName + " 加载成功！\n星空，深邃、静谧、净化、清幽，跨域多开，包容并蓄，最灿烂，最顽强，而又最孤独。");