package com.deehow.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import org.apache.shiro.SecurityUtils;

import com.deehow.core.Constants;
import com.deehow.core.config.Resources;
import com.deehow.core.exception.BusinessException;
import com.deehow.core.support.Assert;

public class CheckCodeUtil {
	
	/**
	 * 生成验证码///by sessionId
	 * @return
	 */
	public static BufferedImage getCheckCode(){
		
		int width = 80;
		int height = 32;
		//create the image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// set the background color
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// draw the border
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// create a random instance to generate the codes
		Random rdm = new Random();
		String hash1 = Integer.toHexString(rdm.nextInt());
		// make some confusion
		for (int i = 0; i < 50; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		// generate a random code
		String capstr = hash1.substring(0, 4);
		
		//验证码存入redis缓存，并设置过期时间
		Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
		if(sessionId != null){
			try {
				CacheUtil.getCache().set("CHECKCODE_"+sessionId.toString(),capstr,PropertiesUtil.getInt("checkCode.expiration"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("获取sessionId失败！！！");
		}
		
		g.setColor(new Color(0, 100, 0));
		g.setFont(new Font("Candara", Font.BOLD, 24));
		g.drawString(capstr, 8, 24);
		g.dispose();
		
		return image;
	}
	
	
	/**
	 *  验证码---validate
	 * @param code
	 * @return	null 验证码已经过期	
	 * 			true
	 * 			false
	 */
	public static Boolean validateCheckCode(String code){
		Object validateCode = null;
		Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
		if(sessionId != null){
			String key = "CHECKCODE_"+sessionId.toString();
			try {
				validateCode = CacheUtil.getCache().getFinal(key);
				if(validateCode != null){
					CacheUtil.getCache().del(key);	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("获取sessionId失败！！！");
		}
		
		if(validateCode == null){
			return null;	
		} else {
			return validateCode.toString().equals(code);
		}
	}
	
	
	/**
	 *  注册短信验证码---validate
	 * @param code
	 * @return	null 短信验证码已经过期	
	 * 			true
	 * 			false
	 */
	public static Boolean validateMessageCheckCode(String code,String key){
		
		Object validateCode = null;
		//String key = Constants.REGIN_CODE + ":" + phone;
		try {
			validateCode = CacheUtil.getCache().getFinal(key);
		} catch (Exception e) {
			throw new BusinessException(Resources.getMessage("服务器繁忙！！！"));
		}
		
		if(validateCode == null){
			return null;	
		} else {
			boolean flag = validateCode.toString().equals(code);
			if(flag){
				CacheUtil.getCache().del(key);	
			}
			return flag;
		}
	}
	
	/**
	 * 生成短信验证码///by 手机号
	 * @param phone	手机号
	 * @param key	
	 * @param msg
	 * @return
	 */
	public static String getMessageCheckCode(String phone,String key,String msg,int second){
		Assert.mobile(phone);
		
		String code = String.valueOf((int)((Math.random()*9+1)*100000));
		String url = Constants.URL;// 应用地址
		String un = Constants.UN;// 账号
		String pw = Constants.PW;// 密码
		msg = msg + code;// 短信内容
		String rd = Constants.RD;// 是否需要状态报告，需要1，不需要0
		String ex = null;// 扩展码
		
		try {
			String returnString = HttpUtil.batchSend(url, un, pw, phone, msg, rd, ex);
			CacheUtil.getCache().set(key, code, second);
		} catch (Exception e) {
			throw new BusinessException(Resources.getMessage("短信发送失败！！！"));
		}		
		return code;
	}
}
