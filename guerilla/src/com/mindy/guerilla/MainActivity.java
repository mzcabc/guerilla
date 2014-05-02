package com.mindy.guerilla;

import java.io.IOException;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mindy.guerilla.dao.TjuSeatSQLiteOpenHelper;
import com.mindy.guerilla.util.DataBaseUtil;

public class MainActivity extends SlidingFragmentActivity {

	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 把数据库从res/raw中COPY到数据库路径
		copyDataBaseToPhone();

		setTitle("天津大学自习室" );

		// 提示周次
		Toast.makeText(getApplicationContext(), "天大第" + TjuDateTime.getTjuWeekNumber() + "周", Toast.LENGTH_SHORT).show();

		initSlidingMenu(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Toast.makeText(getApplicationContext(),
		// getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();

		// 数据库绝对路径
		// Toast.makeText(getApplicationContext(),
		// getDatabasePath("TjuSeat.db").getAbsolutePath(),
		// Toast.LENGTH_LONG).show();

	}

	/**
	 * 初始化滑动菜单
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到ColorFragment，否则实例化ColorFragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new BulidingFragment(0);

		// 设置主视图界面
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();

		// 设置当打开滑动菜单时，ActionBar不能够跟随着一起滑动
		setSlidingActionBarEnabled(false);

		// 设置滑动菜单视图界面
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new BulidingMenuFragment()).commit();

		// 设置滑动菜单的属性值
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);

	}

	/**
	 * 切换Fragment，也是切换视图的内容
	 */
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	/**
	 * 菜单按钮点击事件，通过点击ActionBar的Home图标按钮来打开滑动菜单
	 */
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 保存Fragment的状态
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
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
