package com.diploma.vmpay.driving_style.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.login.AuthActivity;
import com.diploma.vmpay.driving_style.controller.ActualUserWrapper;

import net.hockeyapp.android.BuildConfig;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.LoginManager;
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.metrics.MetricsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Andrew on 13/05/2017.
 */

public class BugTrackingUtils
{
	private static Activity currentActivity;
	private static String appId = "", appSecret = "", apiToken = "";
	private static String userId;
	private static String contact = "Unknown";
	private static Context context;
	private static ActualUserWrapper actualUserWrapper;

	public static void initialize(Context context, ActualUserWrapper actualUserWrapper)
	{
		BugTrackingUtils.context = context;
		BugTrackingUtils.actualUserWrapper = actualUserWrapper;
		ApplicationInfo ai = null;
		try
		{
			ai = context.getApplicationContext().getPackageManager().getApplicationInfo(
					context.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			appId = bundle.getString(BuildConfig.APPLICATION_ID + ".appIdentifier");
			appSecret = bundle.getString(BuildConfig.APPLICATION_ID + ".appSecret");
//			apiToken = bundle.getString(BuildConfig.APPLICATION_ID + ".apiToken");
//			Logger.v("BugTrackingUtils", "appID " + appId + "\nappSecret " + appSecret + "\napiToken " + apiToken);
		} catch(PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void registerMetricsManager(Application application)
	{
		MetricsManager.register(application);
	}

	public static void checkForCrashes(Application application, Activity activity)
	{
		currentActivity = activity;
		userId = Settings.Secure.getString(currentActivity.getContentResolver(),
				Settings.Secure.ANDROID_ID);
		CrashManager.register(application, appId, listener);
	}

	public static void checkForUpdates(Activity activity)
	{
		//TODO: Remove this for GooglePlay builds!
		UpdateManager.register(activity, appId);
	}

	public static void trackUsage(Activity activity, boolean work)
	{
		if (work)
		{
			Tracking.startUsage(activity);
		}
		else
		{
			Tracking.stopUsage(activity);
		}
	}

	public static void unregisterManagers()
	{
		UpdateManager.unregister();
	}

	/**
	 * Adds custom event for tracking.
	 *
	 * @param event - event name. Accepted characters for tracking events are: [a-zA-Z0-9_. -].
	 *              If you use other than the accepted characters, your events will not show up
	 *              in the HockeyApp web portal.
	 */
	public static void trackEvent(String event)
	{
		MetricsManager.trackEvent(event);
	}

	private static CrashManagerListener listener = new CrashManagerListener()
	{
		@Override
		public void onCrashesSent()
		{
			currentActivity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					Toast.makeText(currentActivity, currentActivity.getResources().getText(R.string.crashes_sent), Toast.LENGTH_LONG).show();
				}
			});
		}

		public String getUserID()
		{
			return userId;
		}

		public boolean shouldAutoUploadCrashes()
		{
			return true;
		}

		public void onCrashesNotSent()
		{
			currentActivity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					Toast.makeText(currentActivity, currentActivity.getResources().getText(R.string.crashes_not_sent), Toast.LENGTH_LONG).show();
				}
			});
		}

		@Override
		public String getContact()
		{
			if (actualUserWrapper != null)
			{
				contact = actualUserWrapper.getActualUser().getLogin();
			}
			return contact;
		}

		@Override
		public boolean includeDeviceData()
		{
			return true;
		}

		@Override
		public boolean includeDeviceIdentifier()
		{
			return true;
		}

		@Override
		public String getDescription()
		{
			String description = "";

			try
			{
				Process process = Runtime.getRuntime().exec("logcat -d driving_style");
				BufferedReader bufferedReader =
						new BufferedReader(new InputStreamReader(process.getInputStream()));

				StringBuilder log = new StringBuilder();
				String line;
				while((line = bufferedReader.readLine()) != null)
				{
					log.append(line);
					log.append(System.getProperty("line.separator"));
				}
				bufferedReader.close();

				description = log.toString();
			} catch(IOException e)
			{
				e.printStackTrace();
			}

			return description;
		}
	};

	public static void registerLoginManager(Activity activity)
	{
		//TODO: Remove this for GooglePlay builds!
		LoginManager.register(activity, appId, appSecret, LoginManager.LOGIN_MODE_ANONYMOUS, AuthActivity.class);
		LoginManager.verifyLogin(activity, activity.getIntent());
	}

	@Nullable
	public static String getAppVersion()
	{
		String version = null;
		try
		{
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch(PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return version;
	}
}
