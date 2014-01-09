package u;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import u.SQL.SQLSimple;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

//had to review this file
@SuppressLint("DefaultLocale")
public class Shared {

	private static boolean _fakeConstructor = false;

	private static boolean mDebugAll = true;
	private static boolean mDebug = false;

	public static History mHistory;

	public static AsyncTask<Void, Void, Void> mCurrentTask;

	public static SQLiteDatabase mDatabase;
	public static SQLSimple mSQL;

	public static String mAppName;
	public static App mApplication;

	public static Activities mActivity;

	public static Menu mMenu;

	private static Locale mLocale;

	public static IntentManager mIntentReceiver;

	public static Context mContext;

	protected Shared() {}

	public static void _fakeConstructor() {
		if (!Shared._fakeConstructor) {
			Shared._fakeConstructor = true;
			Shared.mAppName = Shared.mApplication.getPackageName();

			if (Shared.mDebug || Shared.mDebugAll)
				Log.d(Shared.mAppName, "Shared()");
			Shared.mLocale = Locale.getDefault();
			// the first time SQL is instantiated automatically runs onCreate
			// which allows to create the tables to store the data.
			new SQL().open();

		}
	}

	public static void log(String string, boolean mDebug) {
		if (mDebug || Shared.mDebugAll) {
			StackTraceElement item = new Throwable().fillInStackTrace()
					.getStackTrace()[1];
			String caller = item.getClassName() + "()";
			if (!item.getMethodName().equals("<init>"))
				caller += "." + item.getMethodName() + "()";
			caller += ":" + item.getLineNumber() + ":";
			Log.d(Shared.mAppName, caller + string);
		}
	}

	public static void log(boolean bool, boolean mDebug) {
		if (mDebug || Shared.mDebugAll) {
			StackTraceElement item = new Throwable().fillInStackTrace()
					.getStackTrace()[1];
			String caller = item.getClassName() + "()";
			if (!item.getMethodName().equals("<init>"))
				caller += "." + item.getMethodName() + "()";
			caller += ":" + item.getLineNumber() + ":";
			Log.d(Shared.mAppName, caller + (bool ? "true" : "false"));
		}
	}

	public static void log(int integer, boolean mDebug) {
		if (mDebug || Shared.mDebugAll) {
			StackTraceElement item = new Throwable().fillInStackTrace()
					.getStackTrace()[1];
			String caller = item.getClassName() + "()";
			if (!item.getMethodName().equals("<init>"))
				caller += "." + item.getMethodName() + "()";
			caller += ":" + item.getLineNumber() + ":";
			Log.d(Shared.mAppName, caller + (Integer.toString(integer)));
		}
	}

	public static void log(JSONObject Object, boolean mDebug) {
		if (mDebug || Shared.mDebugAll) {
			StackTraceElement item = new Throwable().fillInStackTrace()
					.getStackTrace()[1];
			String caller = item.getClassName() + "()";
			if (!item.getMethodName().equals("<init>"))
				caller += "." + item.getMethodName() + "()";
			caller += ":" + item.getLineNumber() + ":";
			Log.d(Shared.mAppName, caller + Object.toString());
		}
	}

	public static void log(CharSequence Object, boolean mDebug) {
		if (mDebug || Shared.mDebugAll) {
			StackTraceElement item = new Throwable().fillInStackTrace()
					.getStackTrace()[1];
			String caller = item.getClassName() + "()";
			if (!item.getMethodName().equals("<init>"))
				caller += "." + item.getMethodName() + "()";
			caller += ":" + item.getLineNumber() + ":";
			Log.d(Shared.mAppName, caller + Object.toString());
		}
	}
	public static void log(ArrayList<String> Object, boolean mDebug) {
		if (mDebug || Shared.mDebugAll) {
			StackTraceElement item = new Throwable().fillInStackTrace()
					.getStackTrace()[1];
			String caller = item.getClassName() + "()";
			if (!item.getMethodName().equals("<init>"))
				caller += "." + item.getMethodName() + "()";
			caller += ":" + item.getLineNumber() + ":";
			Log.d(Shared.mAppName, caller + Object.toString());
		}
	}
	public static void log(boolean mDebug) {
		if (mDebug || Shared.mDebugAll) {
			StackTraceElement item = new Throwable().fillInStackTrace()
					.getStackTrace()[1];
			String caller = item.getClassName() + "()";
			if (!item.getMethodName().equals("<init>"))
				caller += "." + item.getMethodName() + "()";
			caller += ":" + item.getLineNumber() + ":";
			Log.d(Shared.mAppName, caller);
		}
	}

	public static String toString(String string) {
		return string;
	}

	public static String toString(boolean bool) {
		return bool ? "true" : "false";
	}

	public static String toString(int integer) {
		return Integer.toString(integer);
	}

	public static String toString(JSONObject Object) {
		return Object.toString();
	}

	public static void downloadFile(String uri, String name) {
		downloadFile(uri, name, "Download Has Started", "Downloading...");
	}
	@SuppressLint("NewApi")
	public static void downloadFile(String uri, String name, String msgAlert,
			String msgNotification) {
		DownloadManager.Request r = new DownloadManager.Request(Uri.parse(uri));
		r.setTitle(name);
		r.setDescription(msgNotification);
		r.setMimeType("audio/mpeg");

		r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			r.allowScanningByMediaScanner();
			r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		}

		DownloadManager dm = (DownloadManager) Shared.mActivity
				.getSystemService(Context.DOWNLOAD_SERVICE);
		dm.enqueue(r);

		alert(msgAlert);
	}
	public static String encodeURIComponent(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8").replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!").replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(").replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String decodeURIComponent(String string) {
		String result = null;
		try {
			result = URLDecoder.decode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return string;
		}
		return result;
	}

	@SuppressLint("NewApi")
	public static ClipData copyToClipboardURI(String string) {
		return ClipData.newUri(Shared.mActivity.getContentResolver(), "URI",
				Uri.parse(string));
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void copyToClipboard(String string) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) Shared.mActivity
					.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(string);
		} else {
			android.content.ClipboardManager clipboard = (ClipboardManager) Shared.mActivity
					.getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData.newPlainText(
					string, string);
			clipboard.setPrimaryClip(clip);
		}
	}

	public static void openInBrowser(String string) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(string));
		Shared.mActivity.startActivity(browserIntent);
	}

	public static void alert(String string) {
		Toast.makeText(Shared.mActivity, string, Toast.LENGTH_LONG).show();
	}

	public static String serialize(Object obj) throws IOException {
		Shared.log(Shared.mDebug);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream, 0);
		ObjectOutputStream oos = new ObjectOutputStream(base64OutputStream);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		return byteArrayOutputStream.toString();
	}

	public static Object unserialize(String string) throws IOException,
			ClassNotFoundException {
		Shared.log(Shared.mDebug);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				string.getBytes());
		Base64InputStream base64InputStream = new Base64InputStream(
				byteArrayInputStream, 0);
		@SuppressWarnings("resource")
		ObjectInputStream iis = new ObjectInputStream(base64InputStream);
		return iis.readObject();
	}

	public static String getPrefS(String aName) {
		SharedPreferences preferences = Shared.mApplication.getSharedPreferences(
				Shared.mAppName, Context.MODE_PRIVATE);
		return preferences.getString(aName, "");
	}

	public static int getPrefI(String aName) {
		SharedPreferences preferences = Shared.mApplication.getSharedPreferences(
				Shared.mAppName, Context.MODE_PRIVATE);
		return preferences.getInt(aName, 0);
	}

	public static boolean getPrefB(String aName) {
		SharedPreferences preferences = Shared.mApplication.getSharedPreferences(
				Shared.mAppName, Context.MODE_PRIVATE);
		return preferences.getBoolean(aName, false);
	}

	public static void setPrefS(String aName, String aValue) {
		SharedPreferences preferences = Shared.mApplication.getSharedPreferences(
				Shared.mAppName, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(aName, aValue);
		edit.commit();
	}

	public static void setPrefI(String aName, int aValue) {
		SharedPreferences preferences = Shared.mApplication.getSharedPreferences(
				Shared.mAppName, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putInt(aName, aValue);
		edit.commit();
	}

	public static void setPrefB(String aName, boolean aValue) {
		SharedPreferences preferences = Shared.mApplication.getSharedPreferences(
				Shared.mAppName, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(aName, aValue);
		edit.commit();
	}

	// WTF JAVA!?
	public static String join(List<String> list, String delim) {
		StringBuilder sb = new StringBuilder();
		String loopDelim = "";

		for (String s : list) {
			sb.append(loopDelim);
			sb.append(s);
			loopDelim = delim;
		}

		return sb.toString();
	}

	public static String removeNewLines(String string) {
		return string.replace("\\u000d", " ").replace("\\u000a", " ")
				.replace("\r", " ").replace("\n", " ");
	}

	public static ArrayList<String> reverse(ArrayList<String> array) {
		ArrayList<String> copy = new ArrayList<String>(array);
		Collections.reverse(copy);
		return copy;
	}

	public static String numberLocale(int interger) {
		return NumberFormat.getNumberInstance(Shared.mLocale).format(interger);
	}

	// returns true or false if searchQuery is found in aString
	public static boolean searchEngineSearch(String searchQuery, String aString) {
		// normalizing aString - search is caseinsensitive
		aString = aString.trim().toLowerCase();

		if (searchQuery.equals(""))
			return true;
		// finding "or" conditions
		searchQuery = searchQuery.trim().toLowerCase().replaceAll(" or ", ",")
				.replaceAll(" +", " ");
		String[] query = searchQuery.split(",");
		// for each "or" condition - if no "or" conditions was found, this will loop
		// one time.
		for (int i = 0; i < query.length; i++) {
			// getting words
			String[] subQuery = query[i].trim().split(" ");
			// found flag
			boolean found = false;

			// foreach word to search
			for (int a = 0; a < subQuery.length; a++) {
				String word = subQuery[a].trim();

				if (word.equals("") || word.equals("-") || word.equals("+")) {
					continue;
				} else if (word.startsWith("-")
						&& aString.indexOf(word.replaceAll("^-", "")) != -1) {
					found = false;
					break;
				} else if (!word.startsWith("-") && aString.indexOf(word) == -1) {
					found = false;
					break;
				} else {
					found = true;
				}
			}
			if (found)
				return true;
		}
		return false;
	}
	public static String getDomainFromURL(String aURL) throws URISyntaxException {
		String[] aURI = aURL.split("/");
		if (aURI.length > 2 && aURI[2] != null)
			return aURI[2].startsWith("www.") ? aURI[2].substring(4) : aURI[2];
		else
			return "";
	}

}