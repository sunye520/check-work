package com.yogee.youge.common.weixin.msg.out;


import com.yogee.youge.common.weixin.msg.in.InMsg;

/**
 回复文本消息
 <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>12345678</CreateTime>
 <MsgType><![CDATA[text]]></MsgType>
 <Content><![CDATA[你好]]></Content>
 </xml>
 */
public class OutTextMsg extends OutMsg {
	public static final String TEMPLATE =
			"<xml>\n" +
					"<ToUserName><![CDATA[${__msg.toUserName}]]></ToUserName>\n" +
					"<FromUserName><![CDATA[${__msg.fromUserName}]]></FromUserName>\n" +
					"<CreateTime>${__msg.createTime}</CreateTime>\n" +
					"<MsgType><![CDATA[${__msg.msgType}]]></MsgType>\n" +
					"<Content><![CDATA[${__msg.content}]]></Content>\n" +
					"</xml>";

	private String content;

	public OutTextMsg() {
		this.msgType = "text";
	}

	public OutTextMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "text";
	}

	public String getContent() {
		return content;
	}

	public OutTextMsg setContent(String content) {
		this.content = content;
		return this;
	}
}


