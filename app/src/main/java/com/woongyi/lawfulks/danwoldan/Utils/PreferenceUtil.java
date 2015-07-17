package com.woongyi.lawfulks.danwoldan.Utils;

import android.content.Context;

public class PreferenceUtil extends BasePreferenceUtil {
	private static PreferenceUtil _instance = null;

	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	public static synchronized PreferenceUtil instance(Context $context) {
		if (_instance == null)
			_instance = new PreferenceUtil($context);
		return _instance;
	}

	protected PreferenceUtil(Context $context)
	{
		super($context);
	}

	public void setRedId(String $regId)
	{
		put(PROPERTY_REG_ID, $regId);
	}

	public String getRegId()
	{
		return get(PROPERTY_REG_ID);
	}

	public void setAppVersion(int $appVersion)
	{
		put(PROPERTY_APP_VERSION, $appVersion);
	}

	public int getAppVersion()
	{
		return get(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	}
	
	public void setStringPref(String $key, String $str)
	{
		put($key, $str);
	}

	public String getStringPref(String $key)
	{
		return get($key);
	}

	public void setBooleanPref(String $key, boolean $bool) {
		put($key, $bool);
	}

	public boolean getBooleanPref(String $key) {
		return get($key, false);
	}
}
