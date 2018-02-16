package com.njp.android.kuweather.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.njp.android.kuweather.bean.Location;
import com.njp.android.kuweather.bean.DaoMaster;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * 行政地区查询类
 */

public class LocationDao {

    private static Database sDatabase;

    public static void init(Context context) {
        sDatabase = new DaoMaster.DevOpenHelper(context, "locations.db").getWritableDb();
    }

    public static List<Location> queryProvince() {
        String sql = "SELECT * ,COUNT(DISTINCT ch_province )FROM tb_cities GROUP BY ch_province ORDER BY code_city";
        List<Location> list = new ArrayList<>();
        Cursor c = sDatabase.rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                do {
                    Location location = new Location();
                    location.setLocationName(c.getString(c.getColumnIndex("ch_province")));
                    location.setLocationId(c.getString(c.getColumnIndex("code_city")));
                    list.add(location);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;
    }

    public static List<Location> queryCity(String province) {
        String sql = "SELECT * ,COUNT(DISTINCT ch_sucity)FROM tb_cities WHERE ch_province=? GROUP BY ch_sucity ORDER BY code_city";
        List<Location> list = new ArrayList<>();
        Cursor c = sDatabase.rawQuery(sql, new String[]{province});
        try {
            if (c.moveToFirst()) {
                do {
                    Location location = new Location();
                    location.setLocationName(c.getString(c.getColumnIndex("ch_sucity")));
                    location.setLocationId(c.getString(c.getColumnIndex("code_city")));
                    list.add(location);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;
    }

    public static List<Location> queryDistrict(String city) {
        String sql = "SELECT * ,COUNT(DISTINCT ch_city)FROM tb_cities WHERE ch_sucity=? GROUP BY ch_city ORDER BY code_city";
        List<Location> list = new ArrayList<>();
        Cursor c = sDatabase.rawQuery(sql, new String[]{city});
        try {
            if (c.moveToFirst()) {
                do {
                    Location location = new Location();
                    location.setLocationName(c.getString(c.getColumnIndex("ch_city")));
                    location.setLocationId(c.getString(c.getColumnIndex("code_city")));
                    list.add(location);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;
    }

}
