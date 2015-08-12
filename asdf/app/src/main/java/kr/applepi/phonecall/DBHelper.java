package kr.applepi.phonecall;

/**
 * Created by Seung on 2015. 3. 21..
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context c) {
        super(c, "jalchu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE theme (name int, value int)");

        db.execSQL("INSERT INTO theme VALUES (1, 1);");

        for (int i=2; i<=9; i++) {
            db.execSQL("INSERT INTO theme VALUES (" + i + ", 0);");
        }

        db.execSQL("INSERT INTO music VALUES (1, 1);");

        for (int i=2; i<=7; i++) {
            db.execSQL("INSERT INTO music VALUES (" + i + ", 0);");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists jalchu");
        onCreate(db);
    }
}