package com.cat.httpclient;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cat.httpclient.Utils.HttpCallBackListener;
import com.cat.httpclient.Utils.httpUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText et_name;
    private EditText et_pass;
    private String name;
    private String pass;

    static int ThreadCount = 3;
    static int finishedThread = 0;
    int currentProgress;
    String filename = "网易云音乐.apk";
    String path = "http://p.gdown.baidu.com/57b7f3c9128c8187ebbbb7f938e46125ab27ab35b6ccbf27d0569d7a117efc9556d842feb839a35e113fa076f97f35a25463ae73adecefb60331423513a1820b8331e06912338dcf2093102d7e6c0d3ef282472090b9aa5b5beea7a825667bcb9e11e93ab56db3bb88ca07605ba57a7c66fb210ba1193aa3115b0f7f7873b9dca08f6f4e80a19b511b33a98be65420155f2f8cfe99a5b711ca7d791a1e7eea9a";
    ProgressBar pb;
    TextView tv;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //把变量改成long， 在long下运算
            tv.setText((long)pb.getProgress()*100 / pb.getMax() + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        name = et_name.toString();
        pass = et_pass.toString();
        pb =findViewById(R.id.pb);
        tv = findViewById(R.id.tv);
    }

    public void post(View view) {

    }

    public void get(View view) {
        String address = "http://www.baidu.com";
        httpUtils.sendHttpRequest(address, "GET", null, null, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Log.d(TAG, "onFinish: " + response);
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError: ");
            }
        });

    }

    public void download(View view) {

        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    if (connection.getResponseCode() == 200){
                        //获取文件的长度
                        int length = connection.getContentLength();
                        //设置进度条的最大值
                        pb.setMax(length);

                        File file = new File(Environment.getExternalStorageDirectory(), filename);
                        //生成临时文件
                        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                        //设置临时文件大小
                        raf.setLength(length);
                        raf.close();

                        //计算每个线程应该下载的大小
                        int size = length / ThreadCount;

                        for(int i = 0; i < ThreadCount; i++){
                            //计算线程开始位置和结束位置
                            int startIndex = i*size;
                            int endIndex = (i+1) * size -1;
                            //如果是最后一个线程，那么结束位置写死
                            if(i == (ThreadCount-1)){
                                endIndex = length -1;
                            }
                            new DownLoadThread(startIndex, endIndex, i).start();

                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    class DownLoadThread extends Thread{
        int startIndex;
        int endIndex;
        int threadId;

        public DownLoadThread(int startIndex, int endIndex, int threadId) {
            super();
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            File progressFile = new File(Environment.getExternalStorageDirectory(), threadId + ".txt");
            //判断临时文件是否存在
            if(progressFile.exists()){
                try {
                    FileInputStream fis = new FileInputStream(progressFile);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    //读取文件中保存的进度
                    int lastProgress = Integer.parseInt(reader.readLine());
                    startIndex +=lastProgress;

                    //显示进度条
                    currentProgress += lastProgress;
                    pb.setProgress(currentProgress);
                    handler.sendEmptyMessage(1);
                    fis.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //请求数据
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                //https://blog.csdn.net/blackice1015/article/details/51018815
                connection.setRequestProperty("Connection", "close");
                //设置本次http请求的数据区间
                connection.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);

                //请求部分数据，响应码应该是206
                if (connection.getResponseCode() == 206){
                    InputStream is = connection.getInputStream();
                    byte[] b  = new byte[1024];
                    int len = 0;
                    int total = 0;
                    File file = new File(Environment.getExternalStorageDirectory(), filename);
                    RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                    raf.seek(startIndex);
                    while ((len = is.read(b)) != -1){
                        raf.write(b, 0, len);
                        total += len;
                        currentProgress += len;
                        pb.setProgress(currentProgress);
                        handler.sendEmptyMessage(1);

                        //记录当前下载的位置
                        RandomAccessFile progressRaf = new RandomAccessFile(progressFile, "rwd");
                        progressRaf.write((total + "").getBytes());
                        progressRaf.close();
                    }
                    Log.d(TAG, "run: 线程" + threadId + "下载完成");
                    raf.close();
                    finishedThread++;
                    synchronized (path){
                        if (finishedThread == ThreadCount){
                            for (int i = 0; i<ThreadCount; i++){
                                File f = new File(Environment.getExternalStorageDirectory(), i+".txt");
                                f.delete();
                            }
                            finishedThread = 0;
                        }
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
