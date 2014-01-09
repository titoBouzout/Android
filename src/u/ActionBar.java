package u;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class ActionBar {

	private static android.app.ActionBar bar;

	@SuppressLint("NewApi")
	public static void setColour(String colour) {
		if (App.mAPI >= 11) {
			bar = Shared.mActivity.getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colour)));
		}
	}
}
