//本文件只是示例以及API说明,如果出现功能BUG请反馈
//SDKVer:9
//更新说明 频道的用户是使用用户ID标识的并不是QQ号,频道有频道ID和频道聊天ID两个,如果要使用老接口在频道发送消息需要在群号写   频道ID&聊天ID  用户账号请留空
//目前私聊还没有做出直接私聊,私聊其实也算是一个频道,有频道ID和频道聊天ID,和群聊发送方法一样

/*
全局变量
context  QQ的Application
MyUin    当前登录的账号
PluginID 当前脚本随机分配的ID号
SDKVer   脚本适配版本号
MyChannelTinyID  当前账号的Channel使用的ID


全局方法
sendMsg(String,String,String)   群号,好友账号,消息内容  发送群的好友号码留空,发送好友群号码留空
发送消息,插入图片[PicUrl=图片地址或本地位置]   艾特  [AtQQ=xxx] 0为艾特全体,艾特仅在纯文字消息时有效,图文混合不生效

sendPic(String,String,String)   群号,好友账号,图片地址或本地位置

sendCard(String,String,String)  群号,好友账号,卡片消息内容

sendShake(String)   群号   发送窗口抖动

sendShow(String,String,int)   发送秀图  群号,图片本地位置,秀图类型0-5

sendLike(String,int) 点赞该用户,参数 用户名,点赞次数

sendAntEmo(String,String,int,int) 发送大表情,参数 群号 QQ号  SevrID   StickerID

sendVoice(String,String,String)  发送语音   群号,好友账号,语音本地地址

sendTip(Object,String) 第一个参数写收到的消息对象,第二个消息写内容

sendReply(String,Object,String) 第一个参数群号,第二个参数收到的消息对象,第三个参数回复的内容

getGroupList()  取得群列表,返回ArrayList<GroupInfo>
class GroupInfo
    {
        String GroupUin;  群号
        String GroupName; 群名
        String GroupOwner;群主账号
        String[] AdminList;管理账号列表
        Object sourceInfo;原始对象
    }

getGroupMemberList(String)  群号,取得群成员列表,返回ArrayList<GroupMemberInfo>
class GroupMemberInfo
    {
        String UserUin; 成员账号
        String UserName;成员名字
        int UserLevel;成员等级
        long Join_Time;加群时间戳
        long Last_AvtivityTime; 最后发言时间
        Object sourceInfo; 来源对象
    }

getForbiddenList(String)   群号,取得禁言列表,有管理权限的人可用,返回ArrayList<GroupBanInfo>
	class GroupBanInfo
    {
        String UserUin;  成员号码
        String UserName;  成员名字
        long Endtime;   结束的时间戳
    }

setCard(String,String,String)   群号,成员号,名片   设置一个群成员的名片

setTitle(String,String,String)   群号,成员号码,头衔  设置一个成员的头衔

AddItem(String,String,String)   显示名字,调用方法名,PluginID   在群快捷菜单添加一个项目,单击后调用指定方法,

请指定三个参数,String String int ,第三个参数为类型,1为群聊,2为好友,3为群私聊,
如果为群聊,第一个参数为群号,第二个参数为空,如果为好友,第一个参数为空,第二个参数是还有号码,如果是群私聊,第一个参数为群号,第二个参数为私聊的人的号码



Toast(String)  弹出提示

revokeMsg(Object)  撤回消息,传递收到的消息对象

Forbidden(String,String,int) 禁言,0为解禁,参数为群号,成员号,时间,单位秒,成员留空为全员禁言,时间0为解禁,非0为禁言

Kick(String,String,boolean)  踢出群成员,群号,成员号,是否禁止再次加入

GetActivity()  取得一个活动的Activity

load(String)  加载一个附带的java,命名空间保留,参数为路径

下面为敏感接口,加载脚本后第一次获取需要用户确认,加载每个接口只需要确认一次

String getSkey()  取得当前登录账户的Skey
String getPskey(String)  取得当前登录账号的Pskey,参数为域名,例如qzone.qq.com
String getSuperkey()   取得当前登录账户的SuperKey
String getPT4Token(String)   取得PT4Token 参数为域名

HandleRequest(Object,boolean,String,boolean),处理入群的信息
参数1写回调得到的对象,不支持缓存到本地,重启QQ失效,参数2是否同意,参数3是拒绝时的拒绝原因,参数4是拒绝时是否拒绝再次申请
(注意:目前ID不可缓存,必须保持QQ存活,重启失效)


回调

void onMsg(Object)  收到普通消息时调用,具体消息类型  图片,文字,图文
具体成员
    public static class MessageData
    {
        public String MessageContent;  消息内容,图片会被转换成[PicUrl=xxx]的形式
        public String GroupUin;  群号
        public String UserUin;   发送者QQ
		public String SenderNickName;   发送者名字
		
		public boolean IsGroup;  是否为群组消息,频道群租时也为true
		public boolean IsChannel; 是否为频道的消息
		public boolean IsSend;  是否为自己发送的消息
		
		public String ChannelID;//频道ID
		public String ChannelChatID;//频道聊天ID
		
        public int MessageType;  消息类型  文字或纯图片  1   卡片消息  2   混合消息  3        语音消息为4   文件消息为5  回复消息为6
		 public long MessageTime;   消息时间
        public String[] AtList;   艾特列表
        public ArrayList mAtList;  艾特列表2
        public Object msg;        原始消息对象
		
        public Object AppInterface;
       
        public String[] PicList;   图片列表
		
		public String FileUrl;  语音文件时为语音下载地址,其他消息时没有意义
		public String FileName; 群文件时为文件名,其他消息时没有意义
		public long FileSize;  群文件时为文件大小,其他消息时没有意义
		
		public String ReplyTo;回复消息时为回复的用户的号码,其他消息时没有意义
    }
	
	void onRevokeMsg(Object)  当有消息被撤回时调用,具体消息类型  图片,文字,图文
具体成员
    public static class MessageData
    {
        public String MessageContent;  消息内容,图片会被转换成[PicUrl=xxx]的形式
        public String GroupUin;  群号
        public String UserUin;   发送者QQ
        public int MessageType;  消息类型  文字或纯图片为1             卡片消息为2           混合消息为3
        public boolean IsGroup;  是否为群组消息  (私聊还暂不支持)
        public String SenderNickName;   发送者名字
        public Object AppInterface;
        public long MessageTime;   消息时间
        public String[] AtList;   艾特列表
        public ArrayList mAtList;  艾特列表2
        public Object msg;        原始消息对象
        public String[] PicList;   图片列表
		public String AdminUin;   如果是被管理员撤回,这里是管理员的号码
		
		public boolean IsSend;  是否为自己发送的消息
		
		
		
		
    }
	

void OnTroopEvent(String,String,int)  群号,成员号,消息类型  1为退群,2为加群
void OnTroopEvent(String,String,String,long)  当有人被禁言时调用,   群号,成员号,管理员号,禁言时间,成员号为空时为全员禁言,全员禁言时时间不为0为禁言,时间为0为解禁

void Callback_OnRawMsg(Object)  收到消息时调用,直接传递原始消息对象

void onUnload()脚本取消时调用

String getMsg(String)  在点击发送按钮发送消息时调用,传递发送消息的内容,返回文本则会修改为文本的内容

String getMsg(String,String,int)在点击发送按钮发送消息时调用,传递发送消息的内容,返回文本则会修改为文本的内容, 参数1将要发送的消息,参数2为好友号码或群号,参数3为类型,1为群组,2为好友或私聊

void onRequestJoin(Object RequestInfo)在有成员申请入群时调用

void onMemberExit(String TroopUin,String UserUin,int Type,String OPUin) 在群员退出时调用
参数1群号,参数2用户QQ,参数3类型,1为自己退群,2为被踢,参数4为被踢时的管理员QQ

public static class RequestInfo{
        public String GroupUin; //请求群号
        public String UserUin; //请求的用户
        public String Answer;  //如果是问题则是问题的答案,如果是普通请求则为空
        public String RequestText;//完整的请求信息,包括问题以及答案
		public String RequestSource;//请求来源
    }



int GetChatType() 取得当前聊天模式 -1 不在对话框,0好友,1群聊,1000私聊  暂时不支持群助手(都丢进群助手了还整天去聊天干啥)
String GetGroupUin() 取得当前聊天群号,私聊,群聊,频道群聊时有效
String GetFriendUin()   取得当前聊天对象,私聊,好友时有效





####修改消息的接口为最先修改的脚本为准,如果第一个调用的脚本修改了,后续的脚本将不再调用,如果脚本不需要修改,可以不写回调或者直接返回null
####优先调用第一个接口


*/