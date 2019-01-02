package com.deehow.constant;

public enum LoginConstant {
	
	//发短信码
	regin,							//	注册短信
	web_dynamic_password,				//	web动态密码
	app_dynamic_password,				//	app动态密码
	retrieve_password,				//	找回密码的手机验证
	
	//区分是动态密码登录或者 本来短信注册却选择可租户登录
	nopassword_login_web,				//web动态密码登录
	nopassword_login_app,				//app动态密码登录
	regin_login,						//本来短信验证注册的，然后选择租户登录
	
	
	
	
	
	
	
	//二维码标识前缀
	deehow_login,
	
	
	
	
	
	
	
	
}
