package com.fzdkx.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Http工具类
 */
public class HttpClientUtil {

    private static final int TIMEOUT_MSEC = 5 * 1000;

    public static String doGet(String url, Map<String, String> paraMap) {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = "";
        CloseableHttpResponse response = null;

        try {
            // 创建URI对象
            URIBuilder uriBuilder = new URIBuilder(url);

            // 添加参数
            // 如果参数map不为null
            if (paraMap != null) {
                // 获取 key 的 set 集合
                Set<String> keySet = paraMap.keySet();
                // 遍历set集合，根据key 获取 value ，添加参数
                for (String key : keySet) {
                    uriBuilder.addParameter(key, paraMap.get(key));
                }
            }

            // 构建URI
            URI uri = uriBuilder.build();

            // 创建Get请求
            HttpGet httpGet = new HttpGet(uri);

            // 发送请求，获得响应
            response = httpClient.execute(httpGet);

            // 判断响应状态，成功则解析请求
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(response, httpClient);
        }

        return result;
    }

    public static String doPost(String url, Map<String, String> map) {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 构建 HttpPost请求
        HttpPost httpPost = new HttpPost(url);

        // 创建 List集合 保存 key value
        List<NameValuePair> nameValuePairs = new ArrayList<>();

        // 遍历map，封装 key value 为 BasicNameValuePair ，并添入集合
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                NameValuePair nameValuePair = new BasicNameValuePair(key, map.get(key));
                nameValuePairs.add(nameValuePair);
            }
        }

        CloseableHttpResponse response = null;
        String result = null;
        try {
            // 模拟表单 ，传入 key value 集合
            UrlEncodedFormEntity form = new UrlEncodedFormEntity(nameValuePairs);

            // 向 HttpPost 请求对象中 设置 请求体
            httpPost.setEntity(form);

            // 对 HttpPost请求 进行配置
            httpPost.setConfig(builderRequestConfig());

            // 执行 HttpPost请求 , 获得响应
            response = httpClient.execute(httpPost);

            result = "";
            // 解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(response, httpClient);
        }
        return result;
    }

    public static String doPostJson(String url, Map<String, String> map) {
        // 创建 HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost(url);

        // 创建 List集合 保存 key value
        List<NameValuePair> nameValuePairs = new ArrayList<>();

        // 遍历map集合 ，封装成 JSONObject 对象
        JSONObject jsonObject = new JSONObject();
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                jsonObject.put(key, map.get(key));
            }
        }

        // 将 JSONObject对象 转换成 StringEntity对象
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");

        // 设置请求编码 及 数据类型
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        // 向HttpPost中设置 StringEntity
        httpPost.setEntity(entity);

        // 对 HttpPost进行配置
        httpPost.setConfig(builderRequestConfig());

        CloseableHttpResponse response = null;
        String result = null;
        try {
            // 发送请求 ，获取响应
            response = httpClient.execute(httpPost);

            result = "";

            // 解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(response, httpClient);
        }
        return result;

    }

    private static RequestConfig builderRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_MSEC)   // 设置链接超时时间
                .setConnectionRequestTimeout(TIMEOUT_MSEC)  // 设置连接请求超时时间
                .setSocketTimeout(TIMEOUT_MSEC).build();  // 设置套接字超市时间
    }

    private static void close(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
