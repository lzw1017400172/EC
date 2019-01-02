package com.deehow.core.util;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;
  
/**   
 * @ClassName: PoiWordToHtmlUtil 
 * @Description: TODO(poi work文档转换html) 
 * 
 */  
public class PoiWordToHtmlUtil {  
      
      
    private static final String ENCODING="UTF-8";  
      
    public static String wordToHtml(String realPath, final String saveName) throws TransformerException, IOException, ParserConfigurationException {              
        String ext = GetFileExt(saveName);  
        String docFile=realPath + "\\" + saveName;  
        //String htmlFile=realPath + "\\" + saveName + ".html";  
        final String picturesPath=docFile+".files";  
        File picturesDir=new File(picturesPath);  
        if(!picturesDir.isDirectory()){  
            picturesDir.mkdirs();  
        }  
        String content=null;  
        try {  
            if(ext.equals("doc")){  
                HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(docFile));     
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());      
                wordToHtmlConverter.setPicturesManager( new PicturesManager()      
                 {      
                     @Override  
                     public String savePicture( byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches )      
                     {      
                         File file = new File(picturesPath+"\\"+suggestedName);    
                         FileOutputStream fos = null;    
                         try {    
                             fos = new FileOutputStream(file);    
                             fos.write(content);    
                             fos.close();    
                         } catch (Exception e) {    
                             e.printStackTrace();    
                         }  
                         //return saveName+".files"+"/"+suggestedName;   
                         return picturesPath+"/"+suggestedName;      
                     }     
                 } );      
                wordToHtmlConverter.processDocument(wordDocument);      
                Document htmlDocument = wordToHtmlConverter.getDocument();      
                ByteArrayOutputStream out = new ByteArrayOutputStream();      
                DOMSource domSource = new DOMSource(htmlDocument);      
                StreamResult streamResult = new StreamResult(out);      
              
                TransformerFactory tf = TransformerFactory.newInstance();      
                Transformer serializer = tf.newTransformer();      
                serializer.setOutputProperty(OutputKeys.ENCODING, ENCODING);      
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");      
                serializer.setOutputProperty(OutputKeys.METHOD, "html");      
                serializer.transform(domSource, streamResult);      
                out.close();  
                //writeFile(new String(out.toByteArray()), htmlFile);                 
                content=out.toString();  
            }else if(ext.equals("docx")){  
                // 1) 加载word文档生成 XWPFDocument对象    
                InputStream in = new FileInputStream(new File(docFile));    
                XWPFDocument document = new XWPFDocument(in);   
                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)    
                XHTMLOptions options = XHTMLOptions.create();         
                options.setExtractor(new FileImageExtractor(picturesDir));  
                options.URIResolver(new BasicURIResolver(picturesPath));  
                //options.URIResolver(new BasicURIResolver(saveName+".files"));  
                /*options.setIgnoreStylesIfUnused(false);   
                options.setFragment(true);*/  
                // 3) 将 XWPFDocument转换成XHTML                                  
                ByteArrayOutputStream baos = new ByteArrayOutputStream();   
                XHTMLConverter.getInstance().convert(document, baos, options);                                
                baos.close();  
                content = baos.toString();  
                //writeFile(content, htmlFile);  
            }  
        }catch(Exception e){     
            e.printStackTrace();     
        }  
        return content;       
    }  
      
    private static String GetFileExt(String name) {   
        String ext = null;  
        int i = name.lastIndexOf('.');    
        if (i > 0 && i < name.length() - 1) {    
            ext = name.substring(i + 1).toLowerCase();    
        }    
        return ext;    
    }  
      
    private static void writeFile(String content, String path) {      
        OutputStream os = null;      
        BufferedWriter bw = null;      
        try {      
            File file = new File(path);      
            os = new FileOutputStream(file);      
            bw = new BufferedWriter(new OutputStreamWriter(os,ENCODING));      
            bw.write(content);      
        } catch (FileNotFoundException fnfe) {      
            fnfe.printStackTrace();      
        } catch (IOException ioe) {      
            ioe.printStackTrace();      
        } finally {      
            try {      
                if (bw != null)      
                    bw.close();      
                if (os != null)      
                    os.close();      
            } catch (IOException ie) {  
                ie.printStackTrace();   
            }      
        }      
    }  
      
}  