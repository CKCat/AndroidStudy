package com.cat.fileoperator;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends Activity {

    List<Person> personList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);

        personList = new ArrayList<Person>();

        MyOpenHelper oh = new MyOpenHelper(this, "people.db", null, 1);
        SQLiteDatabase db = oh.getWritableDatabase();
        Cursor cursor = db.query("person", null, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            String _id = cursor.getString(0);
            String name = cursor.getString(1);
            String salary = cursor.getString(2);
            String phone = cursor.getString(3);

            Person p = new Person(_id, name, phone, salary);
            personList.add(p);
        }

        ListView lv = findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter());

//        String[] objects = new String[]{
//                "小志",
//                "小志的儿子",
//                "萌萌"
//        };
//
//        ListView lv = (ListView) findViewById(R.id.lv);
//        //		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.item_listview, R.id.tv_name, objects));
//
//        //集合中每个元素都包含ListView条目需要的所有数据,该案例中每个条目需要一个字符串和一个整型,所以使用一个map来封装这两种数据
//        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
//
//        Map<String, Object> map1 = new HashMap<String, Object>();
//        map1.put("photo", R.drawable.photo1);
//        map1.put("name", "小志的儿子");
//        data.add(map1);
//
//        Map<String, Object> map2 = new HashMap<String, Object>();
//        map2.put("photo", R.drawable.photo2);
//        map2.put("name", "小志");
//        data.add(map2);
//
//        Map<String, Object> map3 = new HashMap<String, Object>();
//        map3.put("photo", R.drawable.photo3);
//        map3.put("name", "赵帅哥");
//        data.add(map3);
//
//        lv.setAdapter(new SimpleAdapter(this, data, R.layout.item_listview,
//                new String[]{"photo", "name"}, new int[]{R.id.iv_photo, R.id.tv_name}));
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return personList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Person p = personList.get(position);

            View v = null;
            if (convertView == null){
                v = View.inflate(ShowActivity.this, R.layout.item_layout, null);
            }else {
                v = convertView;
            }

            //获取布局填充器对象
            //LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            //使用布局填充器填充布局文件
            //View v2 = inflater.inflate(R.layout.item_listview, null);

            //LayoutInflater inflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            //View v3 = inflater2.inflate(R.layout.item_listview, null);

            //通过资源id查找组件,注意调用的是View对象的findViewById
            TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_name.setText(p.getName());
            TextView tv_phone = (TextView) v.findViewById(R.id.tv_phone);
            tv_phone.setText(p.getPhone());
            TextView tv_salary = (TextView) v.findViewById(R.id.tv_salary);
            tv_salary.setText(p.getSalary());
            return v;
        }
    }
}
