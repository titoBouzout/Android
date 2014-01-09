package u;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

@SuppressWarnings("rawtypes")
public class EventHandler {

	private static boolean mDebug = false;

	public static boolean mMenuCreated = false;

	public static ArrayList mOnMenuUpdate = new ArrayList();
	public static ArrayList mOnSQLCreated = new ArrayList();

	@SuppressWarnings("unchecked")
	public static void addOnSQLCreate(Bookmark Object) {
		Shared.log(mDebug);

		mOnSQLCreated.add(Object);
	}

	@SuppressWarnings("unchecked")
	public static void addOnSQLCreate(Cache Object) {
		Shared.log(mDebug);

		mOnSQLCreated.add(Object);
	}

	public static void dispatchOnSQLCreated(SQLiteDatabase database) {
		Shared.log(mDebug);

		for (int i = 0; i < mOnSQLCreated.size(); i++) {
			String className = mOnSQLCreated.get(i).getClass().getSimpleName();

			if (className.equals("Bookmark"))
				((Bookmark) mOnSQLCreated.get(i)).onSQLCreated(database);
			else if (className.equals("Cache"))
				((Cache) mOnSQLCreated.get(i)).onSQLCreated(database);
		}
	}

	public static void reset() {
		mMenuCreated = false;
		mOnMenuUpdate = new ArrayList();
		mOnSQLCreated = new ArrayList();
	}
}
