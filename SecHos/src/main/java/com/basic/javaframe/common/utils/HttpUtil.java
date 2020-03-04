package com.basic.javaframe.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Encoder;


/**
 *  Http 工具类
  * @ClassName: HttpUtil 
  * @Description: 用于模拟HTTP请求中GET/POST方式 
  * @author keeny
  * @date 2019年1月8日 上午10:51:11 
  *
 */
public class HttpUtil {  
	
	static String tempPath = "C:/uploadFile/temp/";
	
	static String filePath = "https://p.zjgwsjk.com/2ysechos/";
	
	/** 
     * 发送base64
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGetWithPic(String url, Map<String, String> parameters) { 
        String result="";
        InputStream in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数  
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),  
                            "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }
            String full_url = url + "?" + params; 
            System.out.println(full_url); 
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            in = httpConn.getInputStream();
            byte[] bytes = new byte[in.available()];
            // 将文件中的内容读入到数组中
            in.read(bytes);
            
            
            String base64Str = new BASE64Encoder().encode(bytes);	
            result = base64Str;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
        return result ;
    }  
	
    /** 
     * 文件流转为文件存本地
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGetInto(String url, Map<String, String> parameters) { 
        String result="";
        InputStream in = null;// 读取响应输入流  
        FileOutputStream out = null;
        BufferedOutputStream bos = null;
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数  
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),  
                            "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }
            String full_url = url + "?" + params; 
            System.out.println(full_url); 
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
//            // 遍历所有的响应头字段  
//            for (String key : headers.keySet()) {  
//                System.out.println(key + "\t：\t" + headers.get(key));  
//            }  
            
            
            
         	//得到输入流
            in = httpConn.getInputStream();
            
            File file = new File(tempPath+UUID.randomUUID().toString()+".jpg");
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
                // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
            }
            fout.flush();
            fout.close();

            result = filePath+"file/temp/"+file.getName();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
		return result;
    }  
    
    
	
	/**
	 * 获取base64
	 */
	public static String sendGetWithPic(String url, String param) {
        String result = "";
        InputStream in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
            in = connection.getInputStream();
            byte[] bytes = new byte[in.available()];
            // 将文件中的内容读入到数组中
            in.read(bytes);
            
            
            String base64Str = new BASE64Encoder().encode(bytes);	
            result = base64Str;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	
    /** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGet(String url, Map<String, String> parameters) { 
        String result="";
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数  
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),  
                            "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }
            String full_url = url + "?" + params; 
            System.out.println(full_url); 
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
        return result ;
    }  
    
    /** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGetLine(String url, Map<String, String> parameters) { 
        String result="";
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数  
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),  
                            "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }
            String full_url = url + "?" + params; 
            System.out.println(full_url); 
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line+"\r\n";  
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
        return result ;
    }  
  
    /** 
     * 发送POST请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendPost(String url, Map<String, String> parameters) {  
    	System.out.println(JSONObject.toJSONString(parameters));
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                	System.out.println(name);
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();
                if (temp_params.length() != 0) {
                	params = temp_params.substring(0, temp_params.length() - 1);  
				}
            }  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            //httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);  
            // flush输出流的缓冲  
            out.flush();  
            if(httpConn.getResponseCode() == 200) {
                // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));  
              
            }else {
            	in = new BufferedReader(new InputStreamReader(httpConn.getErrorStream(), "UTF-8")); 
            }
            
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            } 
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    /** 
     * 发送POST请求 (对象入参形式)
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendPostwithObj(String url, Map<String, String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            System.out.println(params);
            out.write(params);  
            // flush输出流的缓冲  
            out.flush();  
            if(httpConn.getResponseCode() == 200) {
                // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));  
              
            }else {
            	in = new BufferedReader(new InputStreamReader(httpConn.getErrorStream(), "UTF-8")); 
            }
            
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            } 
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    
    /** 
     * 发送POST请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendPostWithXWwwForm(String url, Map<String, String> parameters) {  
    	String result = "";// 返回的结果  
    	BufferedReader in = null;// 读取响应输入流  
    	PrintWriter out = null;  
    	StringBuffer sb = new StringBuffer();// 处理请求参数  
    	String params = "";// 编码之后的参数  
    	try {  
    		// 编码请求参数  
    		if (parameters.size() == 1) {  
    			for (String name : parameters.keySet()) {  
    				sb.append(name).append("=").append(  
    						java.net.URLEncoder.encode(parameters.get(name),  
    								"UTF-8"));  
    			}  
    			params = sb.toString();  
    		} else {  
    			for (String name : parameters.keySet()) {  
    				System.out.println(">>>>>>>>>>>>>>>>name:"+name+"       params:"+parameters.get(name));
    				sb.append(name).append("=").append(  
    						java.net.URLEncoder.encode(parameters.get(name),  
    								"UTF-8")).append("&");  
    			}  
    			String temp_params = sb.toString();  
    			params = temp_params.substring(0, temp_params.length() - 1);  
    		}  
    		// 创建URL对象  
    		java.net.URL connURL = new java.net.URL(url);  
    		// 打开URL连接  
    		java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
    				.openConnection();  
    		// 设置通用属性  
    		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    		httpConn.setRequestProperty("Accept", "*/*");  
    		httpConn.setRequestProperty("Connection", "Keep-Alive");  
    		httpConn.setRequestProperty("User-Agent",  
    				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
    		// 设置POST方式  
    		httpConn.setDoInput(true);  
    		httpConn.setDoOutput(true);  
    		// 获取HttpURLConnection对象对应的输出流  
    		out = new PrintWriter(httpConn.getOutputStream());  
    		// 发送请求参数  
    		out.write(params);  
    		// flush输出流的缓冲  
    		out.flush();  
    		// 定义BufferedReader输入流来读取URL的响应，设置编码方式  
    		in = new BufferedReader(new InputStreamReader(httpConn  
    				.getInputStream(), "UTF-8"));  
    		String line;  
    		// 读取返回的内容  
    		while ((line = in.readLine()) != null) {  
    			result += line;  
    		}  
    	} catch (Exception e) {  
    		e.printStackTrace();  
    	} finally {  
    		try {  
    			if (out != null) {  
    				out.close();  
    			}  
    			if (in != null) {  
    				in.close();  
    			}  
    		} catch (IOException ex) {  
    			ex.printStackTrace();  
    		}  
    	}  
    	return result;  
    }  
    
    /** 
     * 发送POST请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     * @throws Exception 
     */  
    public static String sendPostWithFile(String url, Map<String, String> parameters,MultipartFile file) throws Exception {  
    	String BOUNDARY = java.util.UUID.randomUUID().toString();  
        String PREFIX = "--", LINEND = "\r\n";  
        String MULTIPART_FROM_DATA = "multipart/form-data";  
        String CHARSET = "UTF-8";  
  
        URL uri = new URL(url);  
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();  
        conn.setReadTimeout(30 * 1000); // 缓存的最长时间  
        conn.setDoInput(true);// 允许输入  
        conn.setDoOutput(true);// 允许输出  
        conn.setUseCaches(false); // 不允许使用缓存  
        conn.setRequestMethod("POST");  
        conn.setRequestProperty("connection", "keep-alive");  
        conn.setRequestProperty("Charsert", "UTF-8");  
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA  
                + ";boundary=" + BOUNDARY);  
        
        StringBuilder sb = new StringBuilder();  
  
        if (parameters!=null) {  
            // 首先组拼文本类型的参数  
            for (Map.Entry<String, String> entry : parameters.entrySet()) {  
                sb.append(PREFIX);  
                sb.append(BOUNDARY);  
                sb.append(LINEND);  
                sb.append("Content-Disposition: form-data; name=\""  
                        + entry.getKey() + "\"" + LINEND);  
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);  
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);  
                sb.append(LINEND);  
                sb.append(entry.getValue());  
                sb.append(LINEND);  
            }  
              
        }  
          
        DataOutputStream outStream = new DataOutputStream(  
                conn.getOutputStream());  
        if (!StringUtils.isEmpty(sb.toString())) {  
            outStream.write(sb.toString().getBytes());  
        }  
          
  
        // 发送文件数据  
        if (file != null) {  
            StringBuilder sb1 = new StringBuilder();  
            sb1.append(PREFIX);  
            sb1.append(BOUNDARY);  
            sb1.append(LINEND);  
            sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""  
                    +  file.getOriginalFilename() + "\"" + LINEND);  
            sb1.append("Content-Type: application/octet-stream; charset="+ CHARSET + LINEND);  
            sb1.append(LINEND);  
            outStream.write(sb1.toString().getBytes());  
  
            InputStream is = file.getInputStream();  
            byte[] buffer = new byte[1024];  
            int len = 0;  
            while ((len = is.read(buffer)) != -1) {  
                outStream.write(buffer, 0, len);  
            }  
  
            is.close();  
            outStream.write(LINEND.getBytes());  
        }
  
        // 请求结束标志  
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();  
        outStream.write(end_data);  
        outStream.flush();  
  
        // 得到响应码  
        int res = conn.getResponseCode();  
        InputStream in = conn.getInputStream();  
        if (res == 200) {  
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));    
                   StringBuffer buffer = new StringBuffer();    
                 String line = "";    
             while ((line = bufferedReader.readLine()) != null){    
                   buffer.append(line);    
             }    
  
//          int ch;  
//          StringBuilder sb2 = new StringBuilder();  
//          while ((ch = in.read()) != -1) {  
//              sb2.append((char) ch);  
//          }  
            return buffer.toString();  
        }  
        outStream.close();  
        conn.disconnect();  
        return in.toString();  
   
    }  
    
    
    /** 
     * 发送POST请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     * @throws Exception 
     */  
    public static String sendPostWithFiles(String url, Map<String, String> parameters,MultipartFile file) throws Exception {  
    	String BOUNDARY = java.util.UUID.randomUUID().toString();  
        String PREFIX = "--", LINEND = "\r\n";  
        String MULTIPART_FROM_DATA = "multipart/form-data";  
        String CHARSET = "UTF-8";  
  
        URL uri = new URL(url);  
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();  
        conn.setReadTimeout(30 * 1000); // 缓存的最长时间  
        conn.setDoInput(true);// 允许输入  
        conn.setDoOutput(true);// 允许输出  
        conn.setUseCaches(false); // 不允许使用缓存  
        conn.setRequestMethod("POST");  
        conn.setRequestProperty("connection", "keep-alive");  
        conn.setRequestProperty("Charsert", "UTF-8");  
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA  
                + ";boundary=" + BOUNDARY);  
        
        StringBuilder sb = new StringBuilder();  
  
        if (parameters!=null) {  
            // 首先组拼文本类型的参数  
            for (Map.Entry<String, String> entry : parameters.entrySet()) {  
                sb.append(PREFIX);  
                sb.append(BOUNDARY);  
                sb.append(LINEND);  
                sb.append("Content-Disposition: form-data; name=\""  
                        + entry.getKey() + "\"" + LINEND);  
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);  
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);  
                sb.append(LINEND);  
                sb.append(entry.getValue());  
                sb.append(LINEND);  
            }  
              
        }  
          
        DataOutputStream outStream = new DataOutputStream(  
                conn.getOutputStream());  
        if (!StringUtils.isEmpty(sb.toString())) {  
            outStream.write(sb.toString().getBytes());  
        }  
          
  
        // 发送文件数据  
        if (file != null) {  
            StringBuilder sb1 = new StringBuilder();  
            sb1.append(PREFIX);  
            sb1.append(BOUNDARY);  
            sb1.append(LINEND);  
            sb1.append("Content-Disposition: form-data; name=\"files\"; filename=\""  
                    +  file.getOriginalFilename() + "\"" + LINEND);  
            sb1.append("Content-Type: application/octet-stream; charset="+ CHARSET + LINEND);  
            sb1.append(LINEND);  
            outStream.write(sb1.toString().getBytes());  
  
            InputStream is = file.getInputStream();  
            byte[] buffer = new byte[1024];  
            int len = 0;  
            while ((len = is.read(buffer)) != -1) {  
                outStream.write(buffer, 0, len);  
            }  
  
            is.close();  
            outStream.write(LINEND.getBytes());  
        }
  
        // 请求结束标志  
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();  
        outStream.write(end_data);  
        outStream.flush();  
  
        // 得到响应码  
        int res = conn.getResponseCode();  
        InputStream in = conn.getInputStream();  
        if (res == 200) {  
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));    
                   StringBuffer buffer = new StringBuffer();    
                 String line = "";    
             while ((line = bufferedReader.readLine()) != null){    
                   buffer.append(line);    
             }    
  
//          int ch;  
//          StringBuilder sb2 = new StringBuilder();  
//          while ((ch = in.read()) != -1) {  
//              sb2.append((char) ch);  
//          }  
            return buffer.toString();  
        }  
        outStream.close();  
        conn.disconnect();  
        return in.toString();  
   
    }
    
    /**
     * 多文件
     * @Title: sendPostWithFiles 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param url
     * @param @param parameters
     * @param @param file
     * @param @return
     * @param @throws Exception    设定文件 
     * @return String    返回类型 
     * @throws
     */
    public static String sendPostWithFiles(String url, Map<String, String> parameters,MultipartFile[] files) throws Exception {  
    	String BOUNDARY = java.util.UUID.randomUUID().toString();  
        String PREFIX = "--", LINEND = "\r\n";  
        String MULTIPART_FROM_DATA = "multipart/form-data";  
        String CHARSET = "UTF-8";  
  
        URL uri = new URL(url);  
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();  
        conn.setReadTimeout(30 * 1000); // 缓存的最长时间  
        conn.setDoInput(true);// 允许输入  
        conn.setDoOutput(true);// 允许输出  
        conn.setUseCaches(false); // 不允许使用缓存  
        conn.setRequestMethod("POST");  
        conn.setRequestProperty("connection", "keep-alive");  
        conn.setRequestProperty("Charsert", "UTF-8");  
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA  
                + ";boundary=" + BOUNDARY);  
        
        StringBuilder sb = new StringBuilder();  
  
        if (parameters!=null) {  
            // 首先组拼文本类型的参数  
            for (Map.Entry<String, String> entry : parameters.entrySet()) {  
                sb.append(PREFIX);  
                sb.append(BOUNDARY);  
                sb.append(LINEND);  
                sb.append("Content-Disposition: form-data; name=\""  
                        + entry.getKey() + "\"" + LINEND);  
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);  
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);  
                sb.append(LINEND);  
                sb.append(entry.getValue());  
                sb.append(LINEND);  
            }  
              
        }  
          
        DataOutputStream outStream = new DataOutputStream(  
                conn.getOutputStream());  
        if (!StringUtils.isEmpty(sb.toString())) {  
            outStream.write(sb.toString().getBytes());  
        }  
          
  
        // 发送文件数据  
        if (files != null && files.length > 0) {
        	for (MultipartFile file : files) {
        		 StringBuilder sb1 = new StringBuilder();  
                 sb1.append(PREFIX);  
                 sb1.append(BOUNDARY);  
                 sb1.append(LINEND);  
                 sb1.append("Content-Disposition: form-data; name=\"files\"; filename=\""  
                         +  file.getOriginalFilename() + "\"" + LINEND);  
                 sb1.append("Content-Type: application/octet-stream; charset="+ CHARSET + LINEND);  
                 sb1.append(LINEND);  
                 outStream.write(sb1.toString().getBytes());  
       
                 InputStream is = file.getInputStream();  
                 byte[] buffer = new byte[1024];  
                 int len = 0;  
                 while ((len = is.read(buffer)) != -1) {  
                     outStream.write(buffer, 0, len);  
                 }  
       
                 is.close();  
                 outStream.write(LINEND.getBytes());  
			}
           
        }
  
        // 请求结束标志  
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();  
        outStream.write(end_data);  
        outStream.flush();  
  
        // 得到响应码  
        int res = conn.getResponseCode();  
        InputStream in = conn.getInputStream();  
        if (res == 200) {  
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));    
                   StringBuffer buffer = new StringBuffer();    
                 String line = "";    
             while ((line = bufferedReader.readLine()) != null){    
                   buffer.append(line);    
             }    
  
//          int ch;  
//          StringBuilder sb2 = new StringBuilder();  
//          while ((ch = in.read()) != -1) {  
//              sb2.append((char) ch);  
//          }  
            return buffer.toString();  
        }  
        outStream.close();  
        conn.disconnect();  
        return in.toString();  
   
    }
    
    
    /** 
     * 发送POST请求  换行
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendPostLine(String url, Map<String, String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line+"\r\n";  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 
    
    /**
     * 
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            下载存放目录
     * @return 返回下载文件
     */
    public static File downloadFile(String urlPath, String downloadDir, Map<String, String> parameters,String fileName) {
        File file = null;
        
        PrintWriter outConn = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {
        	
        	 
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
            }
            String temp_params = sb.toString();  
            params = temp_params.substring(0, temp_params.length() - 1);
        	
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            
            // 设置POST方式  
            httpURLConnection.setDoInput(true);  
            httpURLConnection.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            outConn = new PrintWriter(httpURLConnection.getOutputStream());  
            // 发送请求参数  
            outConn.write(params);  
            // flush输出流的缓冲  
            outConn.flush();  
            
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            System.out.println("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                if (!file.exists()) {
					file.createNewFile();
				}
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }

    }
    
    /**
     * 
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            下载存放目录
     * @return 返回下载文件
     */
    public static void provideDownloadFile(String urlPath,Map<String, String> parameters,HttpServletResponse response) {
        PrintWriter outConn = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
            }
            String temp_params = sb.toString();  
            params = temp_params.substring(0, temp_params.length() - 1);
        	
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            
            // 设置POST方式  
            httpURLConnection.setDoInput(true);  
            httpURLConnection.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            outConn = new PrintWriter(httpURLConnection.getOutputStream());  
            // 发送请求参数  
            outConn.write(params);  
            // flush输出流的缓冲  
            outConn.flush();  
            
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
    		
            response.setHeader("Accept-Ranges", "bytes");
            response.setCharacterEncoding("utf-8");
            //response.setContentType("application/octet-stream");
            response.setContentType("application/zip");
            
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            byte[] buf = new byte[1024];
            int size = 0;
            while (((size=bin.read(buf)) != -1)) {
            	os.write(buf, 0, size);
            }
            os.flush();
            os.close();
            bin.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String method = "POST";
	private static String charset = "UTF-8";
	private static String contentType = "text/xml";
    public static String sendXmlMsg(String address, String xmlMsg) throws Exception {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(address);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setInstanceFollowRedirects(true);
		conn.setRequestProperty("Content-Type", contentType);
		conn.connect();
		// 向输出流写出数据
		PrintWriter pw = new PrintWriter(conn.getOutputStream());
		pw.write(xmlMsg);
		pw.flush();
		pw.close();
		String contentType = conn.getContentType();
		Pattern pattern = Pattern.compile("charset=.*:?");
		Matcher matcher = pattern.matcher(contentType);
		if (matcher.find()) {
			charset = matcher.group();
			if (charset.endsWith(";")) {
				charset = charset.substring(charset.indexOf("=") + 1, charset.indexOf(";"));
			} else {
				charset = charset.substring(charset.indexOf("=") + 1);
			}
			if (charset.contains("\"")) {
				charset = charset.substring(1, charset.length() - 1);
			}
			charset = charset.trim();
		}
 
		InputStream inStream = conn.getInputStream();
		BufferedReader ufferedReader = new BufferedReader(new InputStreamReader(inStream, charset));
		String line;
		while ((line = ufferedReader.readLine()) != null) {
			sb.append(line);
		}
		ufferedReader.close();
		conn.disconnect();
		return sb.toString();
	}
    
    /**
     * post请求 以json格式入参
     * <p>Title: doPost</p>  
     * <p>Description: </p>
     * @author hero  
     * @param url
     * @param myclass
     * @return
     */
    public static String doPost(String url, Object myclass) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost posturl = new HttpPost(url);
        String result = null;
        String jsonSting = JSON.toJSONString(myclass);
        StringEntity entity = new StringEntity(jsonSting, "UTF-8");
        posturl.setEntity(entity);
        posturl.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
           response = httpClient.execute(posturl);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                //EntityUtils.toString 只可调用一次
                String resStr = EntityUtils.toString(responseEntity);
                System.out.println("响应内容为:" + resStr);
                return resStr;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      return null;
    }
    
    
    public static String dourl(String url,Object clzz){
        try {
            String jsonString = JSON.toJSONString(clzz);
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            //设置允许输出
            conn.setDoOutput(true);
            //设置允许输入
            conn.setDoInput(true);
            //设置不用缓存
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            //设置传递方式
            conn.setRequestProperty("contentType", "application/json");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            //开始请求
            byte[] bytes = jsonString.toString().getBytes();
            //写流
            OutputStream stream = conn.getOutputStream();
            stream.write(bytes);
            stream.flush();
            stream.close();
            int resultCode=conn.getResponseCode();
              if(conn.getResponseCode()==200){
              InputStream inputStream = conn.getInputStream();
              byte[] bytes1 = new byte[inputStream.available()];
              inputStream.read(bytes1);
              //转字符串
              String s = new String(bytes);
              System.out.println(s);
                  return s;
          }else {
                  //获取响应内容
                  int code = conn.getResponseCode();
                  String responseMessage = conn.getResponseMessage();
                  System.out.println(code);
                  String s = String.valueOf(code);
                  return "响应状态码是:"+s+"响应内容是：======="+responseMessage;
              }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
                  return null;
    }

    
    
    /** 
     * 主函数，测试请求 
     *  
     * @param args 
     */  
    public static void main(String[] args) {
        String signMap = "{\"appId\":\"jdxjcxml180814@jdtest\",\"businessType\":\"2000\",\"caseNumber\":\"AJ20190128160746478290181\",\"charset\":\"utf-8\",\"entrustedAgentInfos\":\"[{\\\"lawOfficeName\\\":\\\"李一仁\\\",\\\"entrustedAgentName\\\":\\\"单杨\\\",\\\"identityTypeText\\\":\\\"32091119940127121X\\\",\\\"entrustedAgentMobile\\\":\\\"18762671144\\\",\\\"agentAuthTypeText\\\":\\\"0\\\",\\\"email\\\":\\\"liyiren@qq.com\\\"}]\",\"executorInfos\":\"[{\\\"permanentAddress\\\":\\\"江苏省无锡市梁溪区兴隆桥社区6号\\\",\\\"userId\\\":\\\"ff808081688795df016893299c55014c\\\",\\\"habitualResidenceAddress\\\":\\\"江苏省无锡市梁溪区兴隆桥社区6号\\\",\\\"shroffAccountInfos\\\":[{\\\"shroffAccountOpenBankName\\\":\\\"中国工商银行无锡东绛支行\\\",\\\"shroffAccountName\\\":\\\"吴杰\\\",\\\"shroffAccountCardNumber\\\":\\\"9558801103107432891\\\"}]}]\",\"format\":\"json\",\"orderNumbers\":\"[{\\\"orderNumber\\\":\\\"2019012861026\\\"}]\",\"performanceInfo\":\"{\\\"repaymentMethod\\\":\\\"还款方式(按文书约定填写，如按月等额本息还款/按月等额本金还款，按日计息)\\\",\\\"penaltyCalculationFormula\\\":\\\"违约金计算公式（违约金=应还未还本金×违约金比例×违约天数\\\",\\\"paidInterest\\\":\\\"1000.00\\\",\\\"loanLimitEndTime\\\":\\\"20190126\\\",\\\"ensureDeadline\\\":\\\"三年\\\",\\\"penalty\\\":\\\"20.00\\\",\\\"interestCalculationFormula\\\":\\\"年收益率\\\",\\\"compoundInterest\\\":\\\"6.58\\\",\\\"debtOverdueCalculationDeadlineTime\\\":\\\"20190130\\\",\\\"loanRecordInfos\\\":[{\\\"loanDuration\\\":\\\"1年\\\",\\\"loanPeriods\\\":\\\"1期\\\",\\\"interest\\\":\\\"1200.00\\\",\\\"agreementName\\\":\\\"合同jc20190128\\\",\\\"borrowStartTime\\\":\\\"20180127\\\",\\\"agreementCode\\\":\\\"jc20190128\\\",\\\"borrowEndTime\\\":\\\"20190126\\\",\\\"loanAmount\\\":\\\"12000.00\\\",\\\"loanIssueTime\\\":\\\"20180126\\\"}],\\\"loadDefaultTime\\\":\\\"20190128\\\",\\\"eachRepaymentCalculationFormula\\\":\\\"每期还款金额的计算公式,每期还款金额=贷款本金/贷款期数+贷款本金×贷款月利率+违约金（若有）每期还款金额=每期还款金额=贷款本金/贷款期数+（贷款本金-累计已还本金）×贷款日利率×当期实际天数+违约金（若有）\\\",\\\"owePrincipal\\\":\\\"2000.00\\\",\\\"notaryFees\\\":\\\"2000.00\\\",\\\"attorneyFees\\\":\\\"1000.00\\\",\\\"lendingRate\\\":\\\"10%\\\",\\\"collectionNotificationTime\\\":\\\"20190128\\\",\\\"fine\\\":\\\"6.58\\\",\\\"repaymentDate\\\":\\\"10日\\\",\\\"applicationAmount\\\":\\\"2200.00\\\",\\\"fineCalculationFormula\\\":\\\"罚息计算公式=年利息÷365×违约天数\\\",\\\"ensureForm\\\":\\\"书面保证\\\",\\\"returnedPrincipal\\\":\\\"10000.00\\\",\\\"oweInterest\\\":\\\"200.00\\\",\\\"loanLimitStartTime\\\":\\\"20180127\\\",\\\"maximumLoanAmount\\\":\\\"12000.00\\\",\\\"compoundInterestCalculationFormula\\\":\\\"复利计算公式=年利息÷365×违约天数\\\"}\",\"sign\":\"f563e6bdb7cdc00f918607e85cf75e6a\",\"signTime\":\"20190129095216\",\"signType\":\"MD5\",\"token\":\"3d28f1cb86f508ac1aaf1762bfec5407\",\"version\":\"2.1\"}"; 
        JSONObject jsonObject = JSONObject.parseObject(signMap);
        Map maps = jsonObject;
        String result =sendPostWithXWwwForm("https://testsign.egongzheng.com/opensignapi/sign/bidorder/addNotarizationApplyByCase.json", maps);
        System.out.println(result); 
    } 
}