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
		//����ͼ��
		builder.setIcon(android.R.drawable.alert_dark_frame);
		//���ñ���
		builder.setTitle("�����˹������Թ�");
		//�����ı�
		builder.setMessage("��־ƽ�������Ŷ");
		
		//����ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "��лʹ�ñ����,�ټ�", 0).show();
			}
		});
		
		//����ȡ����ť
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "�����Թ�,һ�����ɹ�", 0).show();
			}
		});
		//ʹ�ô�����,����һ���Ի������
		AlertDialog ad = builder.create();
		ad.show();
	}

	public void click2(View v){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ѡ���Ա�");
		final String[] items = new String[]{
				"��",
				"Ů"
		};
		
		builder.setSingleChoiceItems(items, -1, new OnClickListener() {
			
			//which:�û���ѡ����Ŀ���±�
			//dialog:������������ĶԻ���
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "��ѡ�����:" + items[which], 0).show();
				//�رնԻ���
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	public void click3(View v){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ѡ��������˧����");
		final String[] items = new String[]{
				"٩��",
				"��˧��",
				"����ʦ",
				"��ʦ��"
		};
		final boolean[] checkedItems = new boolean[]{
				true,
				true,
				false,
				false
				
		};
		
		builder.setMultiChoiceItems(items, checkedItems, new OnMultiChoiceClickListener() {
			
			//which:�û��������Ŀ���±�
			//isChecked:�û���ѡ�и���Ŀ����ȡ������Ŀ
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				checkedItems[which] = isChecked;
				
			}
		});
		
		//����һ��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			
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
