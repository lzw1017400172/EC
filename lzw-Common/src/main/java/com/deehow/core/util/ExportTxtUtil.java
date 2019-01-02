package com.deehow.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 导出Excel---csv格式
 * 
 * @author wangshengchao
 */
public class ExportTxtUtil {

	/**
	 * 导出
	 * 
	 * @param file
	 *            Txt文件(路径+文件名)，Txt文件不存在会自动创建
	 * @param dataList
	 *            数据
	 * @return
	 */
	public static boolean exportTxt(File file, List<String> dataList) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			return exportTxtByOS(out, dataList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 导出
	 * 
	 * @param out
	 *            输出流
	 * @param dataList
	 *            数据
	 * @return
	 */
	public static boolean exportTxtByOS(OutputStream out, List<String> dataList) {
		boolean isSucess = false;

		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			// 循环数据
			for (int i = 0; i < dataList.size(); i++) {
				bw.append(dataList.get(i)).append("\r\n");
			}

			isSucess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSucess = false;

		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isSucess;
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
	public static void exportFile(HttpServletResponse response, String csvFilePath, String fileName)
			throws IOException {
		response.setContentType("application/csv;charset=GBK");
		response.setHeader("Content-Disposition",
				"attachment;  filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		// URLEncoder.encode(fileName, "GBK")

		InputStream in = null;
		try {
			in = new FileInputStream(csvFilePath);
			int len = 0;
			byte[] buffer = new byte[1024];
			response.setCharacterEncoding("GBK");
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				// out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF
				// });
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
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
						break;
					}
				}
			}
			String dir = filePath;
			dir = dir.toString();
			java.io.File myFilePath = new java.io.File(dir);
			myFilePath.delete(); // 删除空文件夹
		}
	}

	/**
	 * 使用demo！
	 * 
	 * @param request
	 * @param response
	 */
	public static void demo(HttpServletRequest request, HttpServletResponse response) {
		// 文件内容
		List<String> list = new ArrayList<String>();
		list.add(
				"Products, Sub-Assemblies, Designators and Components	Comparison Result	Quantity	Toradex Ixora Carrier Board 1.0A.002 [Sample]	test2 test1 [Sample]");
		list.add("Toradex Ixora Carrier Board 1.0A.002 [Sample] ≠ test2 test1 [Sample]");
		list.add("BAT1		1");
		list.add("Base Component	Deleted		X7R-100n-16V-10%-0402 : Ceramic Capacitor");
		// 生成空文本文件
		String fileName = "xxx.txt";
		// BusinessConstant.EXPORT_TXT_PATH = /home/data/file/txtFile/
		String filePath = "/home/data/file/txtFile/" + new Date().getTime() + "/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File txtFile;
		try {
			txtFile = new File(filePath + fileName);
			if (!txtFile.exists()) {
				txtFile.createNewFile();
			}
			// 写入文件内容
			new ExportTxtUtil().exportTxt(txtFile, list);
			// 导出文件内容
			ExportTxtUtil.exportFile(response, filePath + fileName, fileName);
			// 删除文件
			ExportTxtUtil.deleteFile(filePath, fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 写文件
	 * 
	 * @param newStr
	 *            新内容
	 * @throws IOException
	 */
	public static boolean writeTxtFile(String filenameTemp, String newStr) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = newStr + "\r\n";
		String temp = "";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该文件原有的内容
			for (int j = 1; (temp = br.readLine()) != null; j++) {
				buf = buf.append(temp);
				// System.getProperty("line.separator")
				// 行与行之间的分隔符 相当于“\n”
				buf = buf.append(System.getProperty("line.separator"));
			}
			buf.append(filein);

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}

}
