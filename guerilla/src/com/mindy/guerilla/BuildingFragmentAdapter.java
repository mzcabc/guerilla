package com.mindy.guerilla;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.mindy.guerilla.fragment.BuildingFragment;
import com.mindy.guerilla.util.TjuDateTime;

/**
 * 
 * @author Mindy
 *
 */
public class BuildingFragmentAdapter extends PagerAdapter {

	private final FragmentManager mFragmentManager;
	private FragmentTransaction mTransaction = null;

	private List<BuildingFragment> mFragmentList = new ArrayList<BuildingFragment>(4);

	public BuildingFragmentAdapter(FragmentManager fm, int tjuWeekNumber, int buildingNumber) {
		mFragmentManager = fm;

		// debug
		int currentDayOfWeek = TjuDateTime.getCurrentDayOfWeekInt();
		for (int i = 0; i < 3; i++) {
			if (currentDayOfWeek + i < 8) {
				mFragmentList.add(new BuildingFragment(tjuWeekNumber, currentDayOfWeek + i, buildingNumber));
			} else {
				if (currentDayOfWeek + i == 8) {
					mFragmentList.add(new BuildingFragment("*_* 暂不支持跨周查询!"));
				} else if (currentDayOfWeek + i == 9) {
					mFragmentList.add(new BuildingFragment("*_* 相信我,现在真的不支持!"));
				}

			}
		}
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return ((Fragment) object).getView() == view;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (mTransaction == null) {
			mTransaction = mFragmentManager.beginTransaction();
		}
		String name = getTag(position);
		Fragment fragment = mFragmentManager.findFragmentByTag(name);
		if (fragment != null) {
			mTransaction.attach(fragment);
		} else {
			fragment = getItem(position);
			mTransaction.add(container.getId(), fragment, getTag(position));
		}
		return fragment;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (mTransaction == null) {
			mTransaction = mFragmentManager.beginTransaction();
		}
		mTransaction.detach((Fragment) object);
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		if (mTransaction != null) {
			mTransaction.commitAllowingStateLoss();
			mTransaction = null;
			mFragmentManager.executePendingTransactions();
		}
	}

	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	protected String getTag(int position) {
		return mFragmentList.get(position).getDebugText();
	}

}
