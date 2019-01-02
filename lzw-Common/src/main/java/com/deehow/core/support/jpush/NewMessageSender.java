package com.deehow.core.support.jpush;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.deehow.core.Constants;
import com.deehow.core.support.jpush.model.MessageEntry;

public class NewMessageSender{
	private static final Logger log = LoggerFactory.getLogger(NewMessageSender.class);
	
	/**
	 * 极光推送
	 */
	public static void  sendMessage(MessageEntry msg,String appKey,String masterSecret) {
		Long tagTenantId =msg.getTenantId();
		String tagIp =msg.getMsgIp();
		String msgType = msg.getMsgType();
		
		JPushClient jpushClient= new JPushClient(masterSecret, appKey, null,ClientConfig.getInstance());
		
		// 1、发送个人
		if (Constants.MESSAGE_TYPE_PERSON.equals(msgType)) {
			String receiveUserId = msg.getReceiveUserId();
			String[] userIds = receiveUserId.split(",");
			// 2、发送手机端消息
			try {
				//十字水对应masterSecret:19e3635e79c5363d8efc8105,对应appKey:68e5cfffeb9dd01cf772db47
				//114对应masterSecret:b378685649fdcda57d703d55,对应appKey:2753cadd43696b7390fb562a
				for (String userId : userIds) {
					PushPayload payloadtag = buildPushObject_android_tag_alertWithTitle_user("userId"+userId,"tenantId"+tagTenantId.toString(),"ip"+tagIp,
							msg);
					PushPayload payloadtagIos = buildPushObject_ios_tag_alertWithTitle_user("userId"+userId,"tenantId"+tagTenantId.toString(),"ip"+tagIp,
							msg);
					System.out.println("极光推推推=======userId"+userId+"tenantId"+tagTenantId.toString()+"ip"+tagIp+msg+"===================");
					log.info("极光推推推=[{}]","userId"+userId+"tenantId"+tagTenantId.toString()+"ip"+tagIp+msg);
					try {
						jpushClient.sendPush(payloadtag);
					} catch (APIConnectionException e) {
						e.printStackTrace();
					} catch (APIRequestException e) {
						e.printStackTrace();
					}
					try {
						jpushClient.sendPush(payloadtagIos);
					} catch (APIConnectionException e) {
						e.printStackTrace();
					} catch (APIRequestException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// TODO 3、发送IM
		}

		// 2、发送部门
		if (Constants.MESSAGE_TYPE_DEPT.equals(msgType)) {
			String deptId = msg.getReceiveDeptId();
			if (deptId == null)
				deptId = "";
			deptId = deptId.replaceAll("#", "");
			String[] deptIds = deptId.split(",");
			// 2、发送手机端消息
			try {											//19e3635e79c5363d8efc8105				//68e5cfffeb9dd01cf772db47
				for (String deId : deptIds) {
					System.out.println("deptId"+deId+"tenantId"+tagTenantId.toString()+"ip"+tagIp);
					PushPayload payloadtag = buildPushObject_android_tag_alertWithTitle_user("deptId"+deId,"tenantId"+tagTenantId.toString(),"ip"+tagIp,
							msg);
					PushPayload payloadtagIos = buildPushObject_ios_tag_alertWithTitle_user("deptId"+deId,"tenantId"+tagTenantId.toString(),"ip"+tagIp,
							msg);
					
					try {
						jpushClient.sendPush(payloadtag);
					} catch (APIConnectionException e) {
						e.printStackTrace();
					} catch (APIRequestException e) {
						e.printStackTrace();
					}
					
					try {
						jpushClient.sendPush(payloadtagIos);
					} catch (APIConnectionException e) {
						e.printStackTrace();
					} catch (APIRequestException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// TODO 3、发送IM

		}

		// 2、发送公司
		if (Constants.MESSAGE_TYPE_COMPANCY.equals(msgType)) {
			Long tenantIds = msg.getTenantId();
			// 2、发送手机端消息
			try {
				PushPayload payloadtag = buildPushObject_android_tag_alertWithTitle_user(null,"tenantId"+tenantIds.toString(),"ip"+tagIp,
						msg);
				PushPayload payloadtagIos = buildPushObject_ios_tag_alertWithTitle_company(null,"tenantId"+tenantIds.toString(),"ip"+tagIp,
						msg);
				
				try {
					jpushClient.sendPush(payloadtag);
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}
				
				try {
					jpushClient.sendPush(payloadtagIos);
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// TODO 3、发送IM

		}

	}
	

	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.alertAll(alert);
	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle_user(String tag1,String tag2,String tag3,MessageEntry msg) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag_and(tag1,tag2,tag3))//Audience.all()
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(msg.getMsgContent()).setTitle(msg.getMsgTitle())
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtra("model", msg.getMsgModel()).build())
						.build())
				  //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
				  // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
				// [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
				.setMessage(Message.newBuilder()
				.setMsgContent(msg.getMsgContent())
				.setTitle(msg.getMsgTitle())
				.addExtra("modelId",msg.getModelId())
				.addExtra("modelState",msg.getModelState())
				.build())
				.setOptions(Options.newBuilder()
				 //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
				.setApnsProduction(false)
				//此字段是给开发者自己给推送编号，方便推送者分辨推送记录
				.setSendno(1)
				//此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
				.setTimeToLive(86400)
				.build())
				.build();

	}
	public static PushPayload buildPushObject_ios_tag_alertWithTitle_user(String tag1,String tag2,String tag3,MessageEntry msg) {
		String model = "";
		switch (msg.getMsgModel()) {
		case "announce":
			model = "公告";
			break;
		case "journel":
			model = "日报";
			break;
		case "flowcontrol":
			model = "系统";
			break;
		case "task":
			model = "任务";
			break;
		case "order":
			model = "订单管理";
			break;
		case "application":
			model = "应用中心";
			break;
		case "spc":
			model = "SPC";
			break;
		default:
			break;
		}
		String[] split = null;
		String msgContent = msg.getMsgContent();
		if(StringUtils.isNotBlank(msg.getMsgContent())){
			split = msg.getMsgContent().split("#");
		}
		String alertContent = "";
		if(split!=null&&split.length>0){
			if(split.length>1){
				alertContent = split[0]+split[split.length-1];
			}else{
				alertContent = msg.getMsgContent();
			}
		}else{
			alertContent = msg.getMsgContent();
		}
		msg.setMsgContent(alertContent);
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag_and(tag1,tag2,tag3))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(IosAlert.newBuilder().setTitleAndBody("【"+model+"】"+msg.getMsgTitle(), "", msg.getMsgContent()).build())
								.setSound("default")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtra("model", msg.getMsgModel()).addExtra("infoKey", msgContent).build())
						.build())
				  //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
				  // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
				// [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
				.setMessage(Message.newBuilder()
				.setMsgContent(msg.getMsgContent())
				.setTitle(msg.getMsgTitle())
				.addExtra("modelId",msg.getModelId())
				.addExtra("modelState",msg.getModelState())
				.build())
				.setOptions(Options.newBuilder()
				 //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
				.setApnsProduction(true)
				//此字段是给开发者自己给推送编号，方便推送者分辨推送记录
				.setSendno(1)
				//此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
				.setTimeToLive(86400)
				.build())
				.build();

	}
	public static PushPayload buildPushObject_ios_tag_alertWithTitle_company(String tag1,String tag2,String tag3,MessageEntry msg) {
		String model = "";
		switch (msg.getMsgModel()) {
		case "announce":
			model = "公告";
			break;
		case "journel":
			model = "日报";
			break;
		case "flowcontrol":
			model = "系统";
			break;
		case "order":
			model = "订单管理";
			break;
		case "application":
			model = "应用中心";
			break;
		case "spc":
			model = "SPC";
			break;
		default:
			break;
		}
		String[] split = null;
		String msgContent = msg.getMsgContent();
		if(StringUtils.isNotBlank(msg.getMsgContent())){
			split = msg.getMsgContent().split("#");
		}
		String alertContent = "";
		if(split!=null&&split.length>0){
			if(split.length>1){
				alertContent = split[0]+split[split.length-1];
			}else{
				alertContent = msg.getMsgContent();
			}
		}else{
			alertContent = msg.getMsgContent();
		}
		msg.setMsgContent(alertContent);
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag_and(tag2,tag3))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(IosAlert.newBuilder().setTitleAndBody("【"+model+"】"+msg.getMsgTitle(), "", msg.getMsgContent()).build())
								.setSound("default")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtra("model", msg.getMsgModel()).addExtra("infoKey", msgContent).build())
						.build())
				  //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
				  // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
				// [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
				.setMessage(Message.newBuilder()
				.setMsgContent(msg.getMsgContent())
				.setTitle(msg.getMsgTitle())
				.addExtra("modelId",msg.getModelId())
				.addExtra("modelState",msg.getModelState())
				.build())
				.setOptions(Options.newBuilder()
				 //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
				.setApnsProduction(true)
				//此字段是给开发者自己给推送编号，方便推送者分辨推送记录
				.setSendno(1)
				//此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
				.setTimeToLive(86400)
				.build())
				.build();

	}

}
