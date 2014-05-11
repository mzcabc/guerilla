package com.mindy.guerilla.fragment;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.mindy.guerilla.BuildingFragmentAdapter;
import com.mindy.guerilla.MainActivity;
import com.mindy.guerilla.R;
import com.mindy.guerilla.dao.TjuSeatDao;
import com.mindy.guerilla.util.TjuDateTime;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * menu fragment ，主要是用于显示menu菜单
 * 
 * @author mindy
 */
public class BuildingMenuFragment extends PreferenceFragment implements OnPreferenceClickListener {
	private int mPosition = -1;
	private ViewPager mViewPager = null;
	private FrameLayout mFrameLayout = null;
	private MainActivity mActivity = null;

	int tjuWeekNumber = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mActivity = (MainActivity) getActivity();
		mViewPager = (ViewPager) mActivity.findViewById(R.id.viewpager);
		mFrameLayout = (FrameLayout) mActivity.findViewById(R.id.content);
		// set the preference xml to the content view
		addPreferencesFromResource(R.xml.buildingsmenu);
		// add listener
		String[] buildings = { "0022", "1048", "0031", "0045", "0020", "0026", "1054", "0018", "0032", "0021", "0038", "0015", "1042", "1089", "1050", "1084", "1085", "1087", "1082", "1060", "1092", "1072", "1079", "1078", "0028", "1070" };
		int buildingCount = buildings.length;
		for (int i = 0; i < buildingCount; i++) {
			findPreference(buildings[i]).setOnPreferenceClickListener(this);
		}

		tjuWeekNumber = TjuDateTime.getTjuWeekNumberInt();
		// findPreference("n").setOnPreferenceClickListener(this);

	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		String key = preference.getKey();
		int buildingNumber = Integer.parseInt(key);

		BuildingFragment.queryInfos = null;
		TjuSeatDao dao = new TjuSeatDao(getActivity());
		BuildingFragment.queryInfos = dao.findByBuildAndWeek(buildingNumber, tjuWeekNumber);

		if (mPosition == buildingNumber) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			return true;
		}
		mPosition = buildingNumber;
		mFrameLayout.setVisibility(View.GONE);
		mViewPager.setVisibility(View.VISIBLE);

		BuildingFragmentAdapter buildingFragmentAdapter = new BuildingFragmentAdapter(mActivity.getFragmentManager(), tjuWeekNumber, buildingNumber);
		mViewPager.setOffscreenPageLimit(5);
		mViewPager.setAdapter(buildingFragmentAdapter);
		mViewPager.setOnPageChangeListener(onPageChangeListener);

		ActionBar actionBar = mActivity.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.removeAllTabs();
		actionBar.addTab(actionBar.newTab().setText("今天").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("明天").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("后天").setTabListener(tabListener));

		// anyway , show the sliding menu
		((MainActivity) getActivity()).getSlidingMenu().toggle();
		return false;
	}

	private SlidingMenu getSlidingMenu() {
		return mActivity.getSlidingMenu();
	}

	ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			getActivity().getActionBar().setSelectedNavigationItem(position);
			switch (position) {
			case 0:
				getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				break;
			default:
				getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				break;
			}
		}

	};
	ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
			if (mViewPager.getCurrentItem() != tab.getPosition())
				mViewPager.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

		}
	};
}
