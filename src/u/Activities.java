package u;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Activities extends Activity {

	private static boolean mDebug = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Shared.log(mDebug);

		Shared.mActivity = this;

		Shared.mIntentReceiver = new IntentManager();// receive Intents from
																									// external applications
		Shared.mIntentReceiver.receive();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Shared.log(mDebug);
		Shared.mMenu = menu;
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Shared.log(mDebug);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Shared.log(mDebug);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Shared.log(mDebug);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Shared.log(mDebug);
	}

	@Override
	protected void onStop() {
		super.onStop();
		Shared.log(mDebug);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Shared.log(mDebug);
	}

}
