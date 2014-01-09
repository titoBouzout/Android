package u;

import java.util.ArrayList;

import android.content.Intent;

public class IntentManager {

	private static boolean mDebug = false;

	public String mText = "";
	public String mURL = "";
	public ArrayList<String> mURLs = new ArrayList<String>();

	public IntentManager() {}

	public void receive() {
		Shared.log(mDebug);

		// Get intent, action and MIME type
		Intent intent = Shared.mActivity.getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
				if (sharedText != null)
					mText = sharedText;
			}
		} else {
			String sharedText = intent.getDataString();
			if (sharedText != null) {
				mText = sharedText;
			}
		}

		String[] possibleURLs = mText.replaceAll("\\s", " ").trim().split(" ");
		for (int i = 0; i < possibleURLs.length; i++) {
			if (possibleURLs[i].startsWith("http://")
					|| possibleURLs[i].startsWith("https://")) {
				if (mURL == "")
					mURL = possibleURLs[i];
				mURLs.add(possibleURLs[i]);
			}
		}

		Shared.log(mText, mDebug);
		Shared.log(mURL, mDebug);
		Shared.log(mURLs, mDebug);
	}

	public static Intent set(String txt) {

		// set share intent
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, txt);
		// some times this function gets called before onCreateMenuOptions
		return shareIntent;
	}
}
