package com.njp.android.kuweather.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import com.njp.android.kuweather.bean.Background;
import com.njp.android.kuweather.bean.DaoMaster;
import com.njp.android.kuweather.bean.Location;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * 背景颜色查询类
 */

public class BackgroundDao {

    private static Database sDatabase;
    private static Resources sResources;
    private static String packageName;

    public static void init(Context context) {
        sDatabase = new DaoMaster.DevOpenHelper(context, "backgrounds.db").getWritableDb();
        sResources = context.getResources();
        packageName = context.getPackageName();
    }

    public static List<Background> getBackgroundList() {

        String sql = "SELECT * FROM tb_backgrounds";
        List<Background> backgroundList = new ArrayList<>();
        Cursor c = sDatabase.rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                do {
                    Background background = new Background();
                    background.setName(c.getString(c.getColumnIndex("name")));
                    background.setResId(sResources.getIdentifier(
                            c.getString(c.getColumnIndex("_id")),
                            "drawable",
                            packageName
                    ));
                    backgroundList.add(background);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return backgroundList;
    }

}
