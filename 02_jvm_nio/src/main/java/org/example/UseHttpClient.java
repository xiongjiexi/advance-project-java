package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class UseHttpClient {

    private static final String URL = "http://192.168.3.29:8088";

    public static void main(String[] args) {
        UseHttpClient use = new UseHttpClient();
        use.doGet();
        use.doPost();
    }

    public void doGet() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("name", "&"));
            params.add(new BasicNameValuePair("age", "18"));
            URI uri = new URIBuilder().setScheme("http").setHost("192.168.3.29")
                    .setPort(8088).setPath("").setParameters(params).build();

            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println("响应状态："+ response.getStatusLine());
            if (entity != null) {
                System.out.println("响应内容长度为："+entity.getContentLength());
                System.out.println("响应内容："+ EntityUtils.toString(entity));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    public void doPost() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        StringBuilder params = new StringBuilder();

        try {
            params.append("name=" + URLEncoder.encode("&", "UTF-8"));
            params.append("&");
            params.append("age=24");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpPost httpPost = new HttpPost(URL+"?"+params);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
