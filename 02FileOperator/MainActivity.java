package com.itheima.dialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	public void click1(View v){
		AlertDialog.Builder builder = new Builder(this);
		//设置图标
		builder.setIcon(android.R.drawable.alert_dark_frame);
		//设置标题
		builder.setTitle("欲练此功必先自宫");
		//设置文本
		builder.setMessage("李志平，想清楚哦");
		
		//设置确定按钮
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "感谢使用本软件,再见", 0).show();
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "若不自宫,一定不成功", 0).show();
			}
		});
		//使用创建器,生成一个对话框对象
		AlertDialog ad = builder.create();
		ad.show();
	}

	public void click2(View v){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("请选择性别");
		final String[] items = new String[]{
				"男",
				"女"
		};
		
		builder.setSingleChoiceItems(items, -1, new OnClickListener() {
			
			//which:用户所选的条目的下标
			//dialog:触发这个方法的对话框
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "您选择的是:" + items[which], 0).show();
				//关闭对话框
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	public void click3(View v){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("请选择您觉得帅的人");
		final String[] items = new String[]{
				"侃哥",
				"赵帅哥",
				"赵老师",
				"赵师兄"
		};
		final boolean[] checkedItems = new boolean[]{
				true,
				true,
				false,
				false
				
		};
		
		builder.setMultiChoiceItems(items, checkedItems, new OnMultiChoiceClickListener() {
			
			//which:用户点击的条目的下标
			//isChecked:用户是选中该条目还是取消该条目
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				checkedItems[which] = isChecked;
				
			}
		});
		
		//设置一个确定按钮
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String text = "";
				for(int i = 0; i < 4; i++){
					text += checkedItems[i]? items[i] + "," : "";
				}
				Toast.makeText(MainActivity.this, text, 0).show();
				dialog.dismiss();
			}
		});
		builder.show();
	}
}
