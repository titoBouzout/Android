package u;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;

public class App extends Application {

	private static boolean mDebug = false;
	public static int mAPI;

	public App() {
		Shared
				.log(
						"-----------------------------------------------------------------------------------",
						mDebug);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mAPI = Build.VERSION.SDK_INT;
		Shared.mApplication = this;// do not move this line!

		Shared.mContext = getApplicationContext();// do not move this line!
		Shared.log(mDebug);
	}

	public void onPostCreate() {
		Shared.log(mDebug);
		Shared._fakeConstructor();// do not move this line!
	}

	public void useBookmarks() {
		EventHandler.addOnSQLCreate(new Bookmark());
	}
	public void useCache() {
		EventHandler.addOnSQLCreate(new Cache());
	}
	public void useHistory() {
		Shared.mHistory = new History();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Shared.log(mDebug);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Shared.log(mDebug);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Shared.log(mDebug);
	}

}
