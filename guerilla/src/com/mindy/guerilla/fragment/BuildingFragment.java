package com.mindy.guerilla.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindy.guerilla.R;
import com.mindy.guerilla.dao.TjuSeatDao;
import com.mindy.guerilla.domain.BuildingInfo;
import com.mindy.guerilla.domain.TjuSeatInfo;

/**
 * 
 * @author Mindy
 *
 */
@SuppressLint("ValidFragment")
public class BuildingFragment extends Fragment {
	String debugtext = null;
	int tjuWeekNumber = 0;
	int currentDayOfWeek = 0;
	int buildingNumber = 0;

	int fragmentType = -1;

	public static final int HOME_PAGE = 0;
	public static final int SEAT_INFO = 1;

	private ListViewHander hander;
	public static List<TjuSeatInfo> queryInfos = new ArrayList<TjuSeatInfo>();

	public BuildingFragment() {
	}

	public BuildingFragment(String text) {
		Log.e("Krislq", text);
		this.debugtext = text;
		fragmentType = HOME_PAGE;
	}

	/**
	 * 定义BuildingFragment的天大周数和当前星期的第几天
	 * 
	 * @param tjuWeekNumber
	 *            天大周数
	 * @param currentDayOfWeek
	 *            当前星期的第几天
	 */
	public BuildingFragment(int tjuWeekNumber, int currentDayOfWeek, int buildingNumber) {
		this.tjuWeekNumber = tjuWeekNumber;
		this.currentDayOfWeek = currentDayOfWeek;
		this.buildingNumber = buildingNumber;
		fragmentType = SEAT_INFO;
		this.debugtext = "tjuWeekNumber:" + tjuWeekNumber + " currentDayOfWeek" + currentDayOfWeek + " buildingNumber:" + buildingNumber;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Log.e("Krislq", "onCreate:" + debugtext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e("mindy", "onCreateView:" + debugtext);

		if (fragmentType == HOME_PAGE) {
			// inflater the layout
			View view = inflater.inflate(R.layout.fragment_text, null);
			TextView textView = (TextView) view.findViewById(R.id.textView);
			if (!TextUtils.isEmpty(debugtext)) {
				textView.setText(debugtext);
			}
			return view;
		} else if (fragmentType == SEAT_INFO) {
			// 查询数据
			
			//debug
			//Toast.makeText(getActivity(), debugtext, Toast.LENGTH_SHORT).show();

			// TjuSeatDao dao = new TjuSeatDao(getActivity());
			// queryInfos = dao.findByBuildAndWeek(buildingNumber, tjuWeekNumber);

			ListView listView = (ListView) inflater.inflate(R.layout.list_grid, null);

			View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, null);
			headerView.setBackgroundColor(Color.parseColor("#99DAF2"));
			listView.addHeaderView(headerView);

			listView.setAdapter(new SeatInfoAdaper());

			return listView;
		} else {
			View view = inflater.inflate(R.layout.fragment_text, null);
			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText("未知类型");
			return view;
		}

	}

	public String getDebugText() {
		return debugtext;
	}

	// 为适配器要用到的控件对象创建类
	private class ListViewHander {
		TextView tv_className;
		TextView tv_c1;
		TextView tv_c2;
		TextView tv_c3;
		TextView tv_c4;
		TextView tv_c5;
		TextView tv_c6;
	}

	@Override
	public void onDestroy() {
		Log.e("Krislq", "onDestroy:" + debugtext);
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.e("Krislq", "onDetach:" + debugtext);
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.e("Krislq", "onPause:" + debugtext);
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.e("Krislq", "onResume:" + debugtext);
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.e("Krislq", "onStart:" + debugtext);
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.e("Krislq", "onStop:" + debugtext);
		super.onStop();
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

			hander = new ListViewHander();

			hander.tv_className = (TextView) convertView.findViewById(R.id.textView_className);
			hander.tv_c1 = (TextView) convertView.findViewById(R.id.textView_c1);
			hander.tv_c2 = (TextView) convertView.findViewById(R.id.textView_c2);
			hander.tv_c3 = (TextView) convertView.findViewById(R.id.textView_c3);
			hander.tv_c4 = (TextView) convertView.findViewById(R.id.textView_c4);
			hander.tv_c5 = (TextView) convertView.findViewById(R.id.textView_c5);
			hander.tv_c6 = (TextView) convertView.findViewById(R.id.textView_c6);

			TjuSeatInfo info = queryInfos.get(position);
			hander.tv_className.setText(info.getClassName());
			hander.tv_c1.setText(info.getCn(currentDayOfWeek, 1));
			hander.tv_c2.setText(info.getCn(currentDayOfWeek, 2));
			hander.tv_c3.setText(info.getCn(currentDayOfWeek, 3));
			hander.tv_c4.setText(info.getCn(currentDayOfWeek, 4));
			hander.tv_c5.setText(info.getCn(currentDayOfWeek, 5));
			hander.tv_c6.setText(info.getCn(currentDayOfWeek, 6));

			// 设置隔行颜色
			if (position % 2 != 0) {
				convertView.setBackgroundColor(Color.parseColor("#EBF8FC"));
			} else {
				convertView.setBackgroundColor(Color.parseColor("#D6F0FA"));
			}

			return convertView;
		}

	}
}
