package com.deehow.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 导出Excel---csv格式
 * 
 * @author wangshengchao
 */
public class ExportCSVExcelUtil {
	/** CSV文件列分隔符 */
	private static final String CSV_COLUMN_SEPARATOR = ",";

	/** CSV文件列分隔符 */
	private static final String CSV_RN = "\r\n";

	private final static Logger logger = Logger.getLogger(ExportCSVExcelUtil.class);

	/**
	 * 数据初始化
	 * 
	 * @param data
	 *            数据库查出来的数据
	 * @param displayColNames
	 *            csv表头
	 * @param matchColNames
	 *            data中的key ，可以说是数据库字段了,原本为”0001”类型的数据在excel中打开会被默认改变为”1”的数据。
	 *            解决方法 :key前加"'"用于特殊处理；
	 * @param 例如
	 *            输入列名为"num"数字为 001，则传入的key值为"-num",保证输出为字符串
	 * @return
	 */
	public static String formatCsvData(List<Map<String, Object>> data, String displayColNames, String matchColNames) {

		StringBuffer buf = new StringBuffer();

		String[] displayColNamesArr = null;
		String[] matchColNamesMapArr = null;

		displayColNamesArr = displayColNames.split(",");
		matchColNamesMapArr = matchColNames.split(",");

		// 输出列头
		for (int i = 0; i < displayColNamesArr.length; i++) {
			buf.append(displayColNamesArr[i]).append(CSV_COLUMN_SEPARATOR);
		}
		buf.append(CSV_RN);

		if (null != data) {
			// 输出数据
			for (int i = 0; i < data.size(); i++) {

				for (int j = 0; j < matchColNamesMapArr.length; j++) {
					// 处理list<Map>中 value=null的数据
					Object object = data.get(i).get(matchColNamesMapArr[j]);
					if (object == null) {
						object = data.get(i).get(matchColNamesMapArr[j].substring(1));
					}
					if (object == null) {
						buf.append(CSV_COLUMN_SEPARATOR);
					} else {
						if (matchColNamesMapArr[j].startsWith("-")) {
							buf.append("\t" + object.toString()).append(CSV_COLUMN_SEPARATOR);
						} else {
							buf.append(object).append(CSV_COLUMN_SEPARATOR);
						}
					}
				}
				buf.append(CSV_RN);
			}
		}
		logger.info("csv file Initialize successfully");
		return buf.toString();
	}

	/**
	 * 导出
	 * 
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportCsv(String fileName, String content, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 读取字符编码
		String csvEncoding = "UTF-8";

		// 设置响应
		response.setCharacterEncoding(csvEncoding);
		response.setContentType("text/csv; charset=" + csvEncoding);
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=30");

		final String userAgent = request.getHeader("USER-AGENT");
		if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
			fileName = URLEncoder.encode(fileName, "UTF8");
		} else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
			fileName = new String(fileName.getBytes(), "ISO8859-1");
		} else {
			fileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
		}
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		// 写出响应
		OutputStream os = response.getOutputStream();
		os.write(content.getBytes("GBK"));
		os.flush();
		os.close();
		logger.info("csv file download completed");
	}

	/**
	 * 生成为CVS文件
	 * 
	 * @param exportData
	 *            源数据List
	 * @param map
	 *            csv文件的列表头map
	 * @param outPutPath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static File createCSVFile(List exportData, LinkedHashMap map, String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			File cFile = new File(outPutPath + fileName + ".csv");
			cFile.createNewFile();
			csvFile = cFile;
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"),
					1024);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				csvFileOutputStream.write("\"" + (String) propertyEntry.getValue() != null
						? (String) propertyEntry.getValue() : "" + "\"");
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
					// 以下部分根据不同业务做出相应的更改
					StringBuilder sbContext = new StringBuilder("");
					if (null != BeanUtils.getProperty(row, (String) propertyEntry.getKey())) {
						if ("证件号码".equals(propertyEntry.getValue())) {
							// 避免：身份证号码 ，读取时变换为科学记数 - 解决办法：加
							// \t(用Excel打开时，证件号码超过15位后会自动默认科学记数)
							sbContext.append(BeanUtils.getProperty(row, (String) propertyEntry.getKey()) + "\t");
						} else {
							sbContext.append(BeanUtils.getProperty(row, (String) propertyEntry.getKey()));
						}
					}
					csvFileOutputStream.write(sbContext.toString());
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param csvFilePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @throws IOException
	 */
	public static void exportFile(HttpServletRequest request, HttpServletResponse response, String csvFilePath,
			String fileName) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/csv;charset=GBK");

		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
		InputStream in = null;
		try {
			in = new FileInputStream(csvFilePath);
			int len = 0;
			byte[] buffer = new byte[1024];
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}
		}
	}

	/**
	 * 删除该目录filePath下的所有文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 * @param fileName
	 *            文件名称
	 */
	public static void deleteFile(String filePath, String fileName) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
						return;
					}
				}
			}
		}
	}

	/**
	 * 使用demo！
	 * 
	 * @param request
	 * @param response
	 */
	public static void demo(HttpServletRequest request, HttpServletResponse response) {
		// csv表头
		String header = "openid,手机号,日期";
		// 下面 data里的key，可以说是数据库字段了
		String key = "xx,xxx,xxxx";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = sdf.format(new Date()).toString() + "-test.csv";
		// 数据处理
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map1.put("xx", "ssss");
		map1.put("xxx", 22222);
		map1.put("xxxx", new Date());
		map2.put("xx", "");
		map2.put("xxx", 0);
		map2.put("xxxx", new Date());
		data.add(map1);
		data.add(map2);
		String content = ExportCSVExcelUtil.formatCsvData(data, header, key);
		try {
			ExportCSVExcelUtil.exportCsv(fileName, content, request, response);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
