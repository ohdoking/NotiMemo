package com.ohdoking.kimdongmemo;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.InboxStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText memoEt; 
	Button inputBtn;
	
	String memo = "none";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String tempMemo = getPreferences();

		memoEt = (EditText) findViewById(R.id.memoEt);
		inputBtn = (Button) findViewById(R.id.inputBtn);
		
		
		if(!tempMemo.equals("")){
			memoEt.setText(tempMemo);
		}
		
		
		
		
		inputBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				savePreferences(memoEt.getText().toString());
				memo = memoEt.getText().toString();
				makeNotification();
				finish();
			}
		});
		
		
	}
	
	// 값 불러오기
    private String getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("memo", "");
    }
     
    // 값 저장하기
    private void savePreferences(String memo){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("memo", memo);
        editor.commit();
    }
	
	void makeNotification(){
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder mBuilder = new Notification.Builder(this);
		
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setTicker("오 메모장");
		mBuilder.setWhen(System.currentTimeMillis());
//		mBuilder.setNumber(list.size());
		mBuilder.setContentTitle("오 메모장");
		mBuilder.setContentText(memo);
		mBuilder.setDefaults(Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE);
		mBuilder.setContentIntent(pendingIntent);
		mBuilder.setAutoCancel(true);
		mBuilder.setOngoing(true) ;
		

		mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

		Notification.InboxStyle style = new InboxStyle(mBuilder);
		
			
			style.addLine(memo);
//		style.setSummaryText(" More");
		 
		mBuilder.setStyle(style);
		
		nm.notify(111, mBuilder.build());
	}

}
