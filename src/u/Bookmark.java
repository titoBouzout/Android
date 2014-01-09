package u;

import java.util.ArrayList;

import u.SQL.SQLSimple;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Bookmark {

	private static boolean mDebug = false;

	public void onSQLCreated(SQLiteDatabase db) {
		Shared.log(mDebug);

		db.execSQL("CREATE TABLE IF NOT EXISTS `bookmarks` (`bookmarks_id` INTEGER PRIMARY KEY AUTOINCREMENT, `bookmarks_radiation` INTEGER NOT NULL DEFAULT 0, `bookmarks_item` TEXT NOT NULL, `bookmarks_category` TEXT NOT NULL);");
		db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `bookmarks_categories_items` ON `bookmarks` (`bookmarks_category`,`bookmarks_item`) ");

	}

	public void create(String item, String category, String radiation) {
		Shared.log(mDebug);

		SQLSimple sql = Shared.mSQL;
		String[] values = { category, item, radiation };
		sql.query(
				"insert into `bookmarks` (`bookmarks_category`,`bookmarks_item`,`bookmarks_radiation`) VALUES (?,?,?)",
				values);
	}

	public boolean exists(String category, String item) {
		Shared.log(mDebug);

		SQLSimple sql = Shared.mSQL;
		String[] values = { category, item };
		return sql
				.query(
						"select * from `bookmarks` where bookmarks_category = ? and bookmarks_item = ?",
						values).size() > 0;
	}

	public void delete(String category, String item) {
		Shared.log(mDebug);

		SQLSimple sql = Shared.mSQL;
		String[] values = { category, item };
		sql.query(
				"delete from `bookmarks` where `bookmarks_category` = ? and `bookmarks_item` = ?",
				values);
	}

	public ArrayList<ContentValues> getAll() {
		Shared.log(mDebug);

		SQLSimple sql = Shared.mSQL;
		return sql.query(
				"select * from `bookmarks` order by bookmarks_radiation desc", null);
	}

	public void radiation(String category, String item) {
		Shared.log(mDebug);

		SQLSimple sql = Shared.mSQL;
		String[] values = { category, item };
		sql.query(
				"update `bookmarks` set bookmarks_radiation = bookmarks_radiation+1 where `bookmarks_category` = ? and  `bookmarks_item` = ?",
				values);
	}
}
