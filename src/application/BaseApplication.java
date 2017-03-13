package application;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.crashreport.CrashReport;

import android.app.Application;
import cn.bmob.v3.Bmob;
import utils.L;
import utils.StaticClass;

public class BaseApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		//初始化 Bugly
		CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
		//初始化 Bmob 云服务
		Bmob.initialize(getApplicationContext(), StaticClass.BMOB_APPLICATION_ID);
		
		//初始化百度地图
		SDKInitializer.initialize(getApplicationContext());  
		L.d("init_OK");
	}
	
	
}
