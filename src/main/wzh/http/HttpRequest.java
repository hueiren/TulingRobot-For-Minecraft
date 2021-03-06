package wzh.http;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 本类代码来源
 * http://www.cnblogs.com/zhuawang/archive/2012/12/08/2809380.html
 * 代码经过修改，详细请查看历史修改
 */
public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader in;
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
        //Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
        /*for (String key : map.keySet()) {
            System.out.println(key + "--->" + map.get(key));
        }*/
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(),"UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        // 使用finally块来关闭输入流
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws IOException 在请求失败，输出输入流错误时抛出
     */
    public static String sendPost(String url, String param,String ContentType) throws IOException {
        PrintWriter out;
        BufferedReader in;
        StringBuilder result = new StringBuilder();
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        if(!ContentType.equalsIgnoreCase("")){
            conn.setRequestProperty("Content-Type", ContentType);
        }
        conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(),"UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line).append("\n");
        }
        return result.toString();
    }
}
