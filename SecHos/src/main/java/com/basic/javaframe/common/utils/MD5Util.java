package com.basic.javaframe.common.utils;

import java.security.MessageDigest;


public class MD5Util {
	
	 public static String md5Password(String key) {
		char hexDigits[] = {
	            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	    };
	    try {
	        byte[] btInput = key.getBytes("UTF-8");
	        // 获得MD5摘要算法的 MessageDigest 对象
	        MessageDigest mdInst = MessageDigest.getInstance("MD5");
	        // 使用指定的字节更新摘要
	        mdInst.update(btInput);
	        // 获得密文
	        byte[] md = mdInst.digest();
	        // 把密文转换成十六进制的字符串形式
	        int j = md.length;
	        char str[] = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            str[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(str);
	    } catch (Exception e) {
	        return null;
	    }
	 }
	 
	 public static String toMD5(String plainText) {
		          StringBuffer buf = new StringBuffer("");
		         try {
		              // 生成实现指定摘要算法的 MessageDigest 对象。
		              MessageDigest md = MessageDigest.getInstance("MD5");
		             // 使用指定的字节数组更新摘要。
		             md.update(plainText.getBytes());
		             // 通过执行诸如填充之类的最终操作完成哈希计算。
		             byte b[] = md.digest();
		             // 生成具体的md5密码到buf数组(32位小写)
		             int i;
		 
		             for (int offset = 0; offset < b.length; offset++) {
		                 i = b[offset];
		                 if (i < 0){
		                     i += 256;
		                }
		                 if (i < 16){
		                     buf.append("0");
		                 }else{
		                     buf.append(Integer.toHexString(i));
		                 }
		             }
		         } catch (Exception e) {
		             e.printStackTrace();
		        }
		        return buf.toString();
		     }
	 
	 
	 private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	 
	 private static String toHexString(byte[] b) {
	     StringBuilder sb = new StringBuilder(b.length * 2);
	     for (int i = 0; i < b.length; i++) {
	         sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
	         sb.append(HEX_DIGITS[b[i] & 0x0f]);
	     }
	     return sb.toString();
	 }
	  
	 public static String Bit32(String SourceString) throws Exception {
	     MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	     digest.update(SourceString.getBytes());
	     byte messageDigest[] = digest.digest();
	     return toHexString(messageDigest);
	 }
	  
	 public static String Bit16(String SourceString) throws Exception {
	     return Bit32(SourceString).substring(8, 24);
	 }
}
