package u;

import java.util.ArrayList;

import u.SQL.SQLSimple;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Cache {

	private static boolean mDebug = false;

	public void onSQLCreated(SQLiteDatabase database) {
		Shared.log(mDebug);

		database
				.execSQL("CREATE TABLE IF NOT EXISTS `cache` (`cache_id` INTEGER PRIMARY KEY AUTOINCREMENT,  `cache_category` TEXT NOT NULL, `cache_data` TEXT NOT NULL);");
		database
				.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `cache_category` ON `cache` (`cache_category` ASC) ");
	}

	public void update(String item, String data) {
		Shared.log(mDebug);

		SQLSimple sql = Shared.mSQL;
		String[] values = { item, data };
		sql.query(
				"INSERT OR REPLACE INTO `cache` (`cache_category`, `cache_data`) VALUES (?, ?)",
				values);
	}

	public ArrayList<ContentValues> get(String item) {
		Shared.log(mDebug);

		SQLSimple sql = Shared.mSQL;
		String[] values = { item };
		return sql.query("select * from `cache` where cache_category = ?", values);
	}

}
