package com.cat.downpic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cat.downpic.loopj.android.image.SmartImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends Activity{

    public List<News> newslist = null;

    Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ListView listView=findViewById(R.id.lv);
            listView.setAdapter(new MyAdapter());
        }
    };

    class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return newslist.size();
        }

        @Override
        public Object getItem(int position) {


            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class ViewHolder{
            //条目的布局文件中有什么组件，这里就定义什么属性
            TextView tv_title;
            TextView tv_detail;
            TextView tv_comment;
            SmartImageView siv;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            News news = newslist.get(position);
            View view = null;
            ViewHolder mHolder = null;
            if (convertView == null){
                view = View.inflate(NewsActivity.this, R.layout.item_listview, null);

                mHolder = new ViewHolder();
                mHolder.tv_title = view.findViewById(R.id.tv_title);
                mHolder.tv_detail = view.findViewById(R.id.tv_detail);
                mHolder.tv_comment = view.findViewById(R.id.tv_comment);
                mHolder.siv = view.findViewById(R.id.iv_smart);

                view.setTag(mHolder);
            }else {
                view = convertView;
                mHolder = (ViewHolder) view.getTag();
            }

            //给三个文本框设置内容
            mHolder.tv_title.setText(news.getTitle());

            mHolder.tv_detail.setText(news.getDetail());

            mHolder.tv_comment.setText(news.getComment() + "条评论");
            //给新闻图片imageview设置内容
            mHolder.siv.setImageUrl(news.getImageUrl());

            return view;
        }
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        getNewsInfo();
//        System.out.println("=========================================");
//        InputStream is = getClassLoader().getResourceAsStream("news.xml");
//        System.out.println(is);
    }

    public void getNewsInfo(){
        Thread t = new Thread(){
            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = getAssets().open("news.xml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parseNewsXml(is);
            }
        };
        t.start();
    }

    public void parseNewsXml(InputStream is){
        XmlPullParser xp = Xml.newPullParser();

        try {
            xp.setInput(is, "utf-8");
            int type = xp.getEventType();
            News news = null;

            while (type != XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_TAG:
                        if ("newslist".equals(xp.getName())){
                            newslist = new ArrayList<News>();
                        }else if ("news".equals(xp.getName())){
                            news = new News();
                        }else if ("title".equals(xp.getName())){
                            String title = xp.nextText();
                            news.setTitle(title);
                        }else if("detail".equals(xp.getName())){
                            String detail = xp.nextText();
                            news.setDetail(detail);
                        }else if("comment".equals(xp.getName())){
                            String comment = xp.nextText();
                            news.setComment(comment);
                        }else if ("image".equals(xp.getName())){
                            String image = xp.nextText();
                            news.setImageUrl(image);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("news".equals(xp.getName())){
                            newslist.add(news);
                        }
                        break;
                }
                type = xp.next();
            }
//            hander.sendEmptyMessage(1);
//            for (News n:newslist){
//                System.out.println(n.toString());
//            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
