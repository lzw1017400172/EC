package com.deehow.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ImgToZipUtil {
	public static void download(String[] files ,HttpServletRequest request, HttpServletResponse response){

        try {
        	String date = DateUtil.format(new Date(), "yyyyMMddHHmmss");
            String downloadFilename =date+ ".zip";//文件的名称
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//转换中文否则可能会产生乱码
            response.reset();
     //       response.setContentType("bin");
            response.setCharacterEncoding("utf-8");  
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流 
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            for (int i=0;i<files.length;i++) {
            //    URL url = new URL(files[i]);
               zos.putNextEntry(new ZipEntry(new File(files[i]).getName()));
               FileInputStream fis = new FileInputStream(new File(files[i]));  
             //  InputStream fis = url.openConnection().getInputStream();   
               byte[] buffer = new byte[1024];     
               int r = 0;     
               while ((r = fis.read(buffer)) != -1) {     
                   zos.write(buffer, 0, r);     
               }     
               fis.close();   
              }  
            zos.flush();     
            zos.close();
            System.out.println("成功了");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  
}

}
