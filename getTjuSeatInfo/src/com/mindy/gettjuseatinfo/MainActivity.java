package com.mindy.gettjuseatinfo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.mindy.gettjuseatinfo.dao.TjuSeatDao;
import com.mindy.gettjuseatinfo.dao.TjuSeatSQLiteOpenHelper;
import com.mindy.gettjuseatinfo.domain.TjuSeatInfo;
import com.mindy.gettjuseatinfo.service.TjuSeatInfoPullParse;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public String url = "http://mindy.tk/tmp/pipe.xml";

	public List<TjuSeatInfo> infos = new ArrayList<TjuSeatInfo>();

	public Button addButton;
	public TextView textView;
	public EditText editText;
	public Button restartButton;

	public boolean isFinishParser = false;
	public String infosStr = "";

	final int startWeek = 1;
	final int endWeek = 26;

	// final String[] buildings = { "1073", "0036", "0022", "1048", "0030",
	// "0031", "0045", "1076", "1049", "0020", "0026", "1047", "0024", "1052",
	// "1054", "1071", "0018", "0032", "0021", "0038", "0015", "1042", "1089",
	// "1050", "1090", "1084", "1085", "1086", "1087", "1088", "1080", "1082",
	// "1060", "0040", "1083", "1074", "1058", "1092", "1081", "1072", "1079",
	// "1078", "1075", "1091", "0028", "1077", "1070" };

	// public String[] buildings = { "0032" };
	// public String[] buildings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addButton = (Button) findViewById(R.id.add);
		textView = (TextView) findViewById(R.id.view);
		editText = (EditText) findViewById(R.id.editText);

		textView.setText("32\n21\n38\n15\n1042\n1089\n1050\n1090\n1084\n1085\n1086\n1087\n1088\n1080\n1082\n1060\n40\n1083\n1074\n1058\n1092\n1081\n1072\n1079\n1078\n1075\n1091\n28\n1077\n1070\n");

		restartButton = (Button) findViewById(R.id.restartButton);

		addButton.setOnClickListener(addButtonOnClickListener);
		restartButton.setOnClickListener(addButtonOnClickListener);
		// Button button = (Button) findViewById(R.id.button1);

		// totalTjuSeatInfos = TjuSeatService.getTjuSeatInfos(startWeek,
		// endWeek, buildings);

		TjuSeatSQLiteOpenHelper helper = new TjuSeatSQLiteOpenHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();

	}

	/**
	 * 多线程加载网络端的xml，若xml文件过大也需要用该方式加载
	 */
	Handler mHandler = new Handler();
	Runnable mRunnable = new Runnable() {
		public void run() {
			if (!isFinishParser) {
				mHandler.postDelayed(mRunnable, 1000);
			} else {
				textView.setText(infosStr);
				mHandler.removeCallbacks(mRunnable);
			}
		}
	};

	/**
	 * 比较耗时操作新建一个线程，避免UI线程ANR
	 */
	public void parserWhitThread() {
		new Thread() {
			@Override
			public void run() {
				// infos = TjuSeatInfoPullParse.getInfosByUrl(url);

				String building = editText.getText().toString();

				infos = TjuSeatInfoPullParse.getAllInfos(startWeek, endWeek, building);
				for (TjuSeatInfo info : infos) {

					// TjuSeatDao dao = new TjuSeatDao(getApplicationContext());
					// dao.add(info.getId(), info.getBuildingNum(),
					// info.getWeekNum(), info.getClassName(), info.getInfos());

					// infosStr += info.toString();
					infosStr += ("楼编号:" + info.getBuildingNum() + " 周数:" + info.getWeekNum() + " 教室" + info.getClassName());
					infosStr += "\n";
					// Log.i("i", "1111111111111111111111111");
					Log.i("parser", "========================> 已加入一个: " + "BuildingNum:" + info.getBuildingNum() + " WeekNum:" + info.getWeekNum());

				}
				isFinishParser = true;
			}
		}.start();
	}

	private OnClickListener addButtonOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.restartButton:
				infosStr = "";
				infos = null;
				isFinishParser = false;
				Log.i("i", "=========================>1");
				parserWhitThread();
				Log.i("i", "=========================>2");
				mHandler.postDelayed(mRunnable, 200);
				Log.i("i", "=========================>3");
				textView.setText("开始解析了...");

				break;

			case R.id.add:
				for (TjuSeatInfo info : infos) {

					TjuSeatDao dao = new TjuSeatDao(getApplicationContext());
					dao.add(info.getId(), info.getBuildingNum(), info.getWeekNum(), info.getClassName(), info.getInfos());
					// Log.i("i", "1111111111111111111111111");
					Log.i("parser", "========================> 已加入数据库一个: " + "BuildingNum:" + info.getBuildingNum() + " WeekNum:" + info.getWeekNum());

					// textView.setText("===> 已加入数据库一个: " + "BuildingNum:" +
					// info.getBuildingNum() + " WeekNum:" + info.getWeekNum());

				}

				textView.setText("本次分析的所有数据已加入数据库");
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
