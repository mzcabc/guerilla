package com.mindy.guerilla;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mindy.guerilla.fragment.BuildingFragment;
import com.mindy.guerilla.fragment.BuildingMenuFragment;
import com.mindy.guerilla.util.DataBaseUtil;
import com.mindy.guerilla.util.TjuDateTime;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

/**
 * 
 * @author Mindy
 * 
 */
public class MainActivity extends SlidingActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 把数据库从res/raw中COPY到数据库路径
		copyDataBaseToPhone();
		setTitle("天津大学自习室查询");
		setContentView(R.layout.frame_content);

		// set the Behind View
		setBehindContentView(R.layout.frame_menu);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		BuildingMenuFragment buildingMenuFragment = new BuildingMenuFragment();
		fragmentTransaction.replace(R.id.menu, buildingMenuFragment);
		fragmentTransaction.replace(R.id.content, new BuildingFragment(getString(R.string.welcome)), "Welcome");
		fragmentTransaction.commit();

		initSlidingMenu();
		// 提示周次
		Toast.makeText(getApplicationContext(), "天大第" + TjuDateTime.getTjuWeekNumberInt() + "周", Toast.LENGTH_SHORT).show();
		// Toast.makeText(getApplicationContext(), "星期" + TjuDateTime.getCurrentDayOfWeekInt() + "", Toast.LENGTH_SHORT).show();
	}

	private void initSlidingMenu() {

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		// 设置滑动菜单的属性值
		setSlidingActionBarEnabled(true);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
		// 设置slding menu的几种手势模式
		// TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动，可以打开sliding menu
		// TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开slding ,你需要在屏幕边缘滑动才可以打开slding menu
		// TOUCHMODE_NONE 自然是不能通过手势打开啦
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 使用左上方icon可点，这样在onOptionsItemSelected里面才可以监听到R.id.home
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// toggle就是程序自动判断是打开还是关闭
			toggle();
			// getSlidingMenu().showMenu();// show menu
			// getSlidingMenu().showContent();//show content
			return true;

		case R.id.mainMenuAbout:
			MyDialogFragment myDialogFragment = new MyDialogFragment();
			myDialogFragment.show(getFragmentManager(), "about");

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * 把数据库从res/raw中COPY到数据库路径
	 */
	private void copyDataBaseToPhone() {
		DataBaseUtil util = new DataBaseUtil(this);
		// 判断数据库是否存在
		boolean dbExist = util.checkDataBase();

		if (dbExist) {
			Log.i("tag", "The database is exist.");
		} else {// 不存在就把raw里的数据库写入手机
			try {
				util.copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}
}
