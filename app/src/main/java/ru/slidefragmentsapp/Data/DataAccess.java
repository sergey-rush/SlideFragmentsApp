package ru.slidefragmentsapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import ru.slidefragmentsapp.Core.SalePoint;

public abstract class DataAccess extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SPDB.db";
    private static final int DATABASE_VERSION = 1;
    protected static final String SHOPS_FILE = "shops.json";

    private Context _context;
    protected SQLiteDatabase db;

    protected DataAccess(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
        db = getWritableDatabase();
    }

    private static DataAccess _instance = null;

    public static DataAccess getInstance(Context context) {
        if (_instance == null) {
            _instance = new DataProvider(context);
        }
        return _instance;
    }

    public abstract List<SalePoint> getSalePoints(int limit);
    public abstract int countSalePoints();
    public abstract int insertSalePoint(SalePoint salePoints);
    public abstract int addSalePoints();


    /**
     * The method is called on the application start up
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SalePoints(Id INTEGER PRIMARY KEY, City TEXT, Name TEXT, Metro TEXT, OpenHours TEXT, StreetName TEXT, House TEXT, PostIndex TEXT, Address TEXT, Latitude TEXT, Longitude TEXT, Email TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SalePoints");
        onCreate(db);
    }

    /**
     * Drops all tables in current database
     */
    public void onDrop() {
        _context.deleteDatabase(DATABASE_NAME);
        Log.d("Drop", "Database is dropped");
    }
}

