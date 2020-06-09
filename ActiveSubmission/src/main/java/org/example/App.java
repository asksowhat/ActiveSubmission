package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect(args[0]).get();
        String str = doc.toString();
        String pattern = "https://srcrs.top/posts/[\\d]{4}/[\\d]{2}/[\\d]{2}/.+.html";
        Pattern regex = Pattern.compile(pattern);
        Matcher m = regex.matcher(str);
        String json = "";
        while (m.find()) {
            json = json + m.group() + "\n";
        }
        System.out.println(App.postTuiSong(args[1],json));
    }

    /**
     * 发送请求的工具类
     * @param url
     * @param Parameters
     * @return
     */
    public static String postTuiSong(String url, String Parameters) {
        String PostUrl = url;
        if (null == PostUrl ||  Parameters.length() == 0) {
            return null;
        }
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //建立URL之间的连接
            URLConnection conn = new URL(PostUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Host", "data.zz.baidu.com");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Content-Length", "83");
            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //获取conn对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(Parameters.trim());
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            //}
        } catch (Exception e) {
            System.out.println("post推送出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("post推送结果：" + result);
        return result;
    }
}
