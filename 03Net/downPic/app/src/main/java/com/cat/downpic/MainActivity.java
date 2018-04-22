package com.cat.downpic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cat.downpic.Utils.Utils;
import com.cat.downpic.loopj.android.image.SmartImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    MainActivity ma;

    Handler handler = new Handler(){
        //此方法在主线程中调用，可以用来刷新ui
        public void handleMessage(Message msg) {
            //处理消息时，需要知道到底是成功的消息，还是失败的消息
            switch (msg.what) {
                case 0:
                    Toast.makeText(ma, "请求失败", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    //把位图对象显示至imageview
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 2:
                    TextView tv = findViewById(R.id.tv);
                    tv.setText((String)msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv_image);
        ma = this;

    }

    public void onclick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_down:
                downPicbyApi();
                break;
            case R.id.btn_smart_down:
                downPicbyLib();
                break;
            case R.id.btn_show_html:
                showhtml();
                break;
            case R.id.btn_show_news:
                Intent intent = new Intent(this, NesActivity.class);
                startActivity(intent);
                break;
        }

    }

    //使用封装好的库
    public void downPicbyLib(){
        final String path = "http://img.hb.aicdn.com/ed4cbafb784b9108943624ebd0ff370d6d946f1c39d19-PaZIPe_fw658";
        SmartImageView siv = findViewById(R.id.siv_image);
        siv.setImageUrl(path);
    }
    //使用Api下载图片
    public void downPicbyApi(){
        final String path = "http://img.hb.aicdn.com/ed4cbafb784b9108943624ebd0ff370d6d946f1c39d19-PaZIPe_fw658";
        final File file = new File(getCacheDir(), getFileName(path));

        if(file.exists()){
            System.out.println("从缓存中读取");
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(bm);
        }else {
            System.out.println("从网络读取");
            Thread t = new Thread(){
                @Override
                public void run() {

                    try {
                        //把网址封装成一个url对象
                        URL url = new URL(path);
                        //获取客户端和服务器的连接对象，此时还没有建立连接
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        //设置请求方式
                        httpURLConnection.setRequestMethod("GET");
                        //设置连接超时
                        httpURLConnection.setConnectTimeout(5000);
                        //设置读取超时
                        httpURLConnection.setReadTimeout(5000);
                        //发送请求，与服务器建立连接
                        if(httpURLConnection.getResponseCode() == 200){
                            //获取服务器响应头中的流，流里的数据就是客户端请求的数据
                            InputStream is = httpURLConnection.getInputStream();

                            //将文件缓存起来
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] b  = new byte[1024];
                            int len = 0;
                            while((len = is.read(b)) != -1){
                                fos.write(b, 0, len);
                            }
                            fos.close();
                            //读取出数据，并构造成位图对象,此时网络流里已经没有数据了
                            //Bitmap bm = BitmapFactory.decodeStream(is);
                            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());

                            Message msg = new Message();
                            msg.obj = bm;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }else{
                            //非ui线程不能更新ui
                            //Toast.makeText(MainActivity.this, "请求失败", 0).show();

                            Message msg = handler.obtainMessage();
                            msg.what = 0;
                            handler.sendMessage(msg);
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
    }

    public void showhtml(){
        Thread t = new Thread(){
            @Override
            public void run() {
                String path = "http://www.baidu.com";

                try {
                    URL url= new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);

                    //直接使用 getResponseCode先建立连接，然后获取响应码
                    if (httpURLConnection.getResponseCode() == 200){
                        InputStream is = httpURLConnection.getInputStream();

                        //获取文本
                        String text = Utils.getTextFormStream(is);

                        Message msg = handler.obtainMessage();
                        msg.obj = text;
                        msg.what = 2;
                        handler.sendMessage(msg);
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

    public String getFileName(String path){
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }
}
