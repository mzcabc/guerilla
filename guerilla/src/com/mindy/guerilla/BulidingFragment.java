package com.mindy.guerilla;

import java.util.ArrayList;
import java.util.List;

import com.mindy.guerilla.dao.TjuSeatDao;
import com.mindy.guerilla.domain.TjuSeatInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class BulidingFragment extends Fragment {

	private int mPosition = -1;
	List<TjuSeatInfo> queryInfos = new ArrayList<TjuSeatInfo>();
	public String TjuWeekNumber = "1";

	public TextView tv_className;
	public TextView tv_c1;
	public TextView tv_c2;
	public TextView tv_c3;
	public TextView tv_c4;
	public TextView tv_c5;
	public TextView tv_c6;

	public String CurrentDayOfWeek = "1";

	public BulidingFragment() {
		// this(R.color.white);
	}

	public BulidingFragment(int pos) {
		mPosition = pos;
		// setRetainInstance(true);
	}

	public String getBuildingNameById(int pos) {
		String[] buildings = getResources().getStringArray(R.array.building_pos_name);

		return buildings[pos];
	}

	public String getBuildingNumberById(int pos) {
		String[] buildings = getResources().getStringArray(R.array.building_no_name);

		return buildings[pos];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (savedInstanceState != null)
			mPosition = savedInstanceState.getInt("mPosition");

		// construct the RelativeLayout
		// RelativeLayout relativeLayout = new RelativeLayout(getActivity());
		TjuWeekNumber = TjuDateTime.getTjuWeekNumber();
		CurrentDayOfWeek= TjuDateTime.getCurrentDayOfWeek();
		// 查询数据
		TjuSeatDao dao = new TjuSeatDao(getActivity());
		queryInfos = dao.findByBuildAndWeek(getBuildingNumberById(mPosition), TjuWeekNumber);

		ListView listView = (ListView) inflater.inflate(R.layout.list_grid, null);

		listView.setAdapter(new SeatInfoAdaper());

		return listView;
	}

	private class SeatInfoAdaper extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return queryInfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item, null);
			}

			tv_className = (TextView) convertView.findViewById(R.id.textView_className);
			tv_c1 = (TextView) convertView.findViewById(R.id.textView_c1);
			tv_c2 = (TextView) convertView.findViewById(R.id.textView_c2);
			tv_c3 = (TextView) convertView.findViewById(R.id.textView_c3);
			tv_c4 = (TextView) convertView.findViewById(R.id.textView_c4);
			tv_c5 = (TextView) convertView.findViewById(R.id.textView_c5);
			tv_c6 = (TextView) convertView.findViewById(R.id.textView_c6);

			TjuSeatInfo info = queryInfos.get(position);
			tv_className.setText(info.getClassName());
			tv_c1.setText(info.getCn(CurrentDayOfWeek, "1"));
			tv_c2.setText(info.getCn(CurrentDayOfWeek, "2"));
			tv_c3.setText(info.getCn(CurrentDayOfWeek, "3"));
			tv_c4.setText(info.getCn(CurrentDayOfWeek, "4"));
			tv_c5.setText(info.getCn(CurrentDayOfWeek, "5"));
			tv_c6.setText(info.getCn(CurrentDayOfWeek, "6"));

			return convertView;
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mPosition);
	}

}
