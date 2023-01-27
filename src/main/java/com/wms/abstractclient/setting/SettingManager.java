package com.wms.abstractclient.setting;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {

	public static List<Setting> settings;
	
	public SettingManager() {
		settings = new ArrayList<>();	
	}
	public static void addSetting(Setting setting) {
		settings.add(setting);
	}
	public static void removeSetting(Setting setting) {
		settings.remove(setting);
	}
}
