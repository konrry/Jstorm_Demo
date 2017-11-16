package net.galvin.jstorm.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	/**
	 * 获取异常详细信息
	 * @param e
	 * @return
	 */
	public static String getExceptionDetails(Throwable e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);   
		pw.flush();   
		sw.flush(); 
		String msg = sw.toString();
		pw.close();
		try {
			sw.close();
		} catch (IOException exception) {
			Logging.error("StringWriter关闭异常：" + exception.getMessage());
		}
		return msg;
	} 
	
	/**
	 * 获取异常详细信息
	 * @param e
	 * @return
	 */
	public static String getExceptionDetails(Throwable e, int length){
		String exceptoinMsg = getExceptionDetails(e);
		if(exceptoinMsg != null && exceptoinMsg.length() > length) {
			return exceptoinMsg.substring(0, length);
		}
		return exceptoinMsg;
	} 

}
