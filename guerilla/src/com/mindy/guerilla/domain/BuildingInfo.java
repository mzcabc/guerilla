package com.mindy.guerilla.domain;

import com.mindy.guerilla.R;

import android.app.Fragment;

public class BuildingInfo {

	/**
	 * 根据位置获得建筑中文名
	 * 
	 * @param pos
	 * @return
	 */
	static public String getBuildingNameById(int pos) {
		Fragment fragment = new Fragment();
		String[] buildings = fragment.getResources().getStringArray(R.array.building_pos_name);
		return buildings[pos];
	}

	/**
	 * 根据位置获取建筑编号名字
	 * 
	 * @param pos
	 * @return
	 */
	static public int getBuildingNumberById(int pos) {
		Fragment fragment = new Fragment();
		// String[] buildings = fragment.getResources().getStringArray(R.array.building_no_name);
		int[] buildings = fragment.getResources().getIntArray(R.array.building_no_name);
		return buildings[pos];
	}
}
