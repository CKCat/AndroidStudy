package com.cat.httpclient.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class httpUtils {

    /**
     *
     * @param address   url地址
     * @param method    请求方式
     * @param params    POST请求数据
     * @param encode    编码方式
     * @param listener  回调函数
     */
    public static void sendHttpRequest(final String address, final String method, final Map<String, String> params,
                                       final String encode, final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection  = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(method);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    StringBuffer buffer = null;
                    if (method.equals("POST") && params != null && !params.isEmpty()){
                        buffer = new StringBuffer();
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            try {
                                buffer.append(entry.getKey())
                                        .append("=")
                                        .append(URLEncoder.encode(entry.getValue(), encode))
                                        .append("&");//请求的参数之间使用&分割。
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        byte[] data = buffer.toString().getBytes();
                        //设置请求报文头，设定请求数据类型
                        connection.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                        //设置请求数据长度
                        connection.setRequestProperty("Content-Length",
                                String.valueOf(data.length));
                        //设置POST方式请求数据
                        OutputStream outputStream = connection.getOutputStream();
                        outputStream.write(data);
                    }
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
