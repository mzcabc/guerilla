package com.mindy.guerilla.domain;

public class TjuSeatInfo {
	private String id;
	private String buildingNum;
	private String weekNum;
	private String className;
	private String infos;

	// 样例 011100 111101 111101 110001 010101 001111 111111
	public String getCn(String dayOfWeek, String classCount) {
		infos = infos.replace("1", "√").replace("0", "×");

		int dayOfWeekInt = Integer.parseInt(dayOfWeek);
		int classCountInt = Integer.parseInt(classCount);
		int basecount = 0;
		switch (dayOfWeekInt) {
		case 1:
			basecount = 0;
			break;
		case 2:
			basecount = 6;
			break;
		case 3:
			basecount = 12;
			break;
		case 4:
			basecount = 18;
			break;
		case 5:
			basecount = 24;
			break;
		case 6:
			basecount = 30;
			break;
		case 7:
			basecount = 36;
			break;
		}

		int reInt = basecount + classCountInt;
		String info = String.valueOf(infos.charAt(reInt - 1));
		return info;
	}

	public boolean genTjuSeatInfoId() {
		// 可以把字符串中的数字提取出来，这是做项目时用到的一个小技巧，记录下来以备不时之需。原理很简单就是根据ASCII码值来判断，代码如下：
		String tmp = "";
		if (className != null && !"".equals(className)) {
			for (int i = 0; i < className.length(); i++) {
				if (className.charAt(i) >= 48 && className.charAt(i) <= 57) {
					tmp += className.charAt(i);
				}
			}
		}
		if ((this.buildingNum != null) && (this.weekNum != null)) {
			this.id = this.buildingNum + this.weekNum + tmp;
			return true;
		} else {
			this.id = "000000";
			return false;
		}
	}

	public TjuSeatInfo(String id, String buildingNum, String weekNum, String className, String infos) {
		super();
		this.id = id;
		this.buildingNum = buildingNum;
		this.weekNum = weekNum;
		this.className = className;
		this.infos = infos;
	}

	public TjuSeatInfo() {
	}

	@Override
	public String toString() {
		return "TjuSeatInfo [id=" + id + ", 建筑编号=" + buildingNum + ", 周数=" + weekNum + ", 教室名=" + className + ", 信息=" + infos + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(String weekNum) {
		this.weekNum = weekNum;
	}

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

	public String getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}

}
