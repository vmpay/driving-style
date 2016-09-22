package com.diploma.vmpay.driving_style.controller;

import android.content.Context;
import android.util.Log;

import com.diploma.vmpay.driving_style.database.dbutils.DatabaseAccess;
import com.diploma.vmpay.driving_style.interfaces.IAccelerometerListener;
import com.diploma.vmpay.driving_style.interfaces.IDatabaseClient;
import com.diploma.vmpay.driving_style.interfaces.ILocationListener;
import com.diploma.vmpay.driving_style.interfaces.IOnActualUserChangedListener;
import com.diploma.vmpay.driving_style.presenters.HistoryPresenter;
import com.diploma.vmpay.driving_style.presenters.SensorPresenter;
import com.diploma.vmpay.driving_style.presenters.UserLoginPresenter;
import com.diploma.vmpay.driving_style.sensors.AccelerometerSensor;
import com.diploma.vmpay.driving_style.sensors.LocationSensor;
import com.diploma.vmpay.driving_style.utils.ListenerList;

/**
 * Created by Andrew on 05/09/2016.
 */
public class AppController
{

	public static final String TAG = "AppController";
	private static AppController ourInstance = new AppController();

	private ActualUserWrapper actualUserWrapper;

	//-----------------------APP--------------------
	private AppState appState = AppState.NOT_LOGGED_IN;
	private boolean initialized = false;
	private ContextWrapper appContextWrapper;
	private ContextOwner appContextOwner = ContextOwner.UNKNOWN;
	private int appContextId = 0;

	//------------------LISTENERS------------------
	private ListenerList<IOnActualUserChangedListener> actualUserChangedListenerListenerList = new ListenerList<>();
	private ListenerList<IAccelerometerListener> accelerometerSensorListenerList = new ListenerList<>();
	private ListenerList<ILocationListener> locationListenerListenerList = new ListenerList<>();


	//------------------SERVICES------------------
	private IDatabaseClient databaseClient;
	private AccelerometerSensor accelerometerSensor;
	private LocationSensor locationSensor;

	//------------------PRESENTERS------------------
	private SensorPresenter sensorPresenter;
	private UserLoginPresenter userLoginPresenter;
	private HistoryPresenter historyPresenter;

	private AppController()
	{
	}

	public static AppController getInstance()
	{
		return ourInstance;
	}

	public void setUp(Context context, ContextOwner contextOwner, int contextId)
	{
		Log.d(TAG, "setUp appContextOwner: " + appContextOwner + " contextOwner: " + contextOwner + " appContextId: " + appContextId + " contextId: " + contextId);
		Log.d(TAG, "setUp: context: " + context.toString());
		this.appContextId = contextId;
		appContextOwner = contextOwner;

		if(contextOwner == ContextOwner.AUTHORISING_ACTIVITY)
		{
			setAppState(AppState.NOT_LOGGED_IN);
		}
		else
		{
			if (contextOwner == ContextOwner.MAIN_ACTIVITY)
			{
				setAppState(AppState.LOGGED_IN);
			}
		}

		if(initialized)
		{
			appContextWrapper.setContext(context);
		}
		else
		{
			appContextWrapper = new ContextWrapper(context);

			clearListeners();
			createDataAccessModels();
			createBusinessLoginLayer();

			initialized = true;
		}
	}

	public void tearDown(ContextOwner contextOwner, int contextId)
	{
		Log.d(TAG, "tearDown appContextOwner: " + appContextOwner + " contextOwner: " + contextOwner + " appContextId: " + appContextId + " contextId: " + contextId);
	}

	public void setAppState(AppState appState)
	{
		this.appState = appState;
	}

	private void createDataAccessModels()
	{
		databaseClient = new DatabaseAccess(appContextWrapper.getContext());

		actualUserWrapper = new ActualUserWrapper(actualUserChangedListenerListenerList, databaseClient);
	}

	private void createBusinessLoginLayer()
	{
		accelerometerSensor = new AccelerometerSensor(appContextWrapper, accelerometerSensorListenerList);
		locationSensor = new LocationSensor(appContextWrapper, locationListenerListenerList);

		createPresenters();
		initializeListeners();
	}

	private void createPresenters()
	{
		sensorPresenter = new SensorPresenter(actualUserWrapper, databaseClient, accelerometerSensor, locationSensor);
		userLoginPresenter = new UserLoginPresenter(databaseClient, actualUserWrapper, appContextWrapper);
		historyPresenter = new HistoryPresenter(actualUserWrapper, databaseClient, appContextWrapper);
	}

	private void initializeListeners()
	{
		accelerometerSensorListenerList.add(sensorPresenter);
		locationListenerListenerList.add(sensorPresenter);
		actualUserChangedListenerListenerList.add(historyPresenter);
	}

	private void clearListeners()
	{
		actualUserChangedListenerListenerList.clear();
	}

	public SensorPresenter getSensorPresenter()
	{
		return sensorPresenter;
	}

	public AccelerometerSensor getAccelerometerSensor()
	{
		return accelerometerSensor;
	}

	public LocationSensor getLocationSensor()
	{
		return locationSensor;
	}

	public UserLoginPresenter getUserLoginPresenter()
	{
		return userLoginPresenter;
	}

	public HistoryPresenter getHistoryPresenter()
	{
		return historyPresenter;
	}
}
