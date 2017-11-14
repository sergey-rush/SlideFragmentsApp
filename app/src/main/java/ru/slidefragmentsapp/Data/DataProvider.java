package ru.slidefragmentsapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.slidefragmentsapp.Core.Month;
import ru.slidefragmentsapp.Core.SalePoint;

public class DataProvider extends DataAccess{

    private Context context;
    public DataProvider(Context context) {

        super(context);
        this.context = context;
    }

    @Override
    public int insertSalePoint(SalePoint salePoint) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", salePoint.Id);
        contentValues.put("City", salePoint.City);
        contentValues.put("Name", salePoint.Name);
        contentValues.put("Metro", salePoint.Metro);
        contentValues.put("OpenHours", salePoint.OpenHours);
        contentValues.put("StreetName", salePoint.StreetName);
        contentValues.put("House", salePoint.House);
        contentValues.put("PostIndex", salePoint.PostIndex);
        contentValues.put("Address", salePoint.Address);
        contentValues.put("Latitude", salePoint.Latitude);
        contentValues.put("Longitude", salePoint.Longitude);
        contentValues.put("Email", salePoint.Email);
        int ret = (int) db.insert("SalePoints", null, contentValues);
        return ret;
    }

    @Override
    public List<SalePoint> getSalePoints(int limit) {
        List<SalePoint> salePoints = new ArrayList<SalePoint>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT Id, City, Name, Metro, OpenHours, StreetName, House, PostIndex, Address, Latitude, Longitude, Email FROM SalePoints ORDER BY Id Limit ?", new String[]{String.valueOf(limit)});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SalePoint salePoint = new SalePoint();
                salePoint.Id = cursor.getInt(cursor.getColumnIndex("Id"));
                salePoint.City = cursor.getString(cursor.getColumnIndex("City"));
                salePoint.Name = cursor.getString(cursor.getColumnIndex("Name"));
                salePoint.Metro = cursor.getString(cursor.getColumnIndex("Metro"));
                salePoint.OpenHours = cursor.getString(cursor.getColumnIndex("OpenHours"));
                salePoint.StreetName = cursor.getString(cursor.getColumnIndex("StreetName"));
                salePoint.House = cursor.getString(cursor.getColumnIndex("House"));
                salePoint.PostIndex = cursor.getString(cursor.getColumnIndex("PostIndex"));
                salePoint.Address = cursor.getString(cursor.getColumnIndex("Address"));
                salePoint.Latitude = cursor.getString(cursor.getColumnIndex("Latitude"));
                salePoint.Longitude = cursor.getString(cursor.getColumnIndex("Longitude"));
                salePoint.Email = cursor.getString(cursor.getColumnIndex("Email"));
                salePoints.add(salePoint);
                cursor.moveToNext();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return salePoints;
    }

    @Override
    public int countSalePoints() {
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) AS Total FROM SalePoints", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(cursor.getColumnIndex("Total"));
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }

    @Override
    public int addSalePoints()
    {
        String line = ReadSalePointData();
        String input = convertToString(line);
        List<SalePoint> salePoints = parseToSalePointList(input);
        for (SalePoint salePoint : salePoints) {
            insertSalePoint(salePoint);
        }
        return salePoints.size();
    }

    private String ReadSalePointData() {

        String line = null;
        BufferedReader reader = null;
        try {
            InputStream stream = this.context.getAssets().open(SHOPS_FILE);
            reader = new BufferedReader(new InputStreamReader(stream));
            line = reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return line;
    }

    protected List<SalePoint> parseToSalePointList(String input) {

        ArrayList<SalePoint> salePoints = new ArrayList<SalePoint>();
        try {
            JSONObject resultData = new JSONObject(input);
            JSONArray items = resultData.getJSONArray("shop");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                SalePoint salePoint = new SalePoint();
                salePoint.Id = item.getInt("Id");
                salePoint.City = item.getString("City");
                salePoint.Name = item.getString("Name");
                salePoint.Metro = item.getString("Metro");
                salePoint.OpenHours = item.getString("OpenHours");
                salePoint.StreetName = item.getString("StreetName");
                salePoint.House = item.getString("House");
                salePoint.PostIndex = item.getString("PostIndex");
                salePoint.Address = item.getString("Address");
                salePoint.Latitude = item.getString("Latitude");
                salePoint.Longitude = item.getString("Longitude");
                salePoint.Email = item.getString("Email");
                salePoints.add(salePoint);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return salePoints;
    }

    private String convertToString(String unicodeString) {
        String output = null;
        try {
            byte[] utf8Bytes = unicodeString.getBytes("UTF8");
            output = new String(utf8Bytes, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return output;
    }

    private static final String MONTHS_FILE = "months.csv";

    public static List<Month> getMonths(Context context){
        List<Month> months = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open(MONTHS_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";

            while ((line = reader.readLine()) !=null){
                String[] fields = line.split(",");
                Month month = new Month();
                month.setId(Integer.parseInt(fields[0]));
                month.setName(fields[1]);
                months.add(month);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return months;
    }
}
