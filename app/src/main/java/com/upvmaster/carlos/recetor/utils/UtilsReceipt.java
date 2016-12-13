package com.upvmaster.carlos.recetor.utils;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.GroupDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Carlos on 03/12/2016.
 */

public class UtilsReceipt {

    public static String getGroupName(int group_id, Context context){
        SQLiteDatabase db = DBHelper.getDatabase(context);
        try {
            GroupDao dao = new GroupDao(db);
            return dao.getName(group_id);
        } catch (Exception e) {
            Log.e("Utils - GetGroupName",e.getMessage(),e);
        } finally {
            db.close();
        }
        return "--";
    }
    public static String getPaso(int paso){
        return paso+"º";
    }
    public static int getintFromPaso(String paso){
        return Integer.parseInt(paso.replace("º",""));
    }

    public static void copiarBBDD(Activity activity) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            String currentDBPath = "/data" + data.toString() + "/" + activity.getPackageName() + "/databases/recetor.sqlite";
            String backupDBPath = "/Download/recetor.sqlite";
            File currentDB = new File(currentDBPath);
            File backupDB = new File(sd, backupDBPath);
            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            Log.e(UtilsReceipt.class.getName(),e.getMessage(),e);
        }
    }

    public static void pintarFondo(Context context, Canvas c, RecyclerView.ViewHolder viewHolder, float dX) {
        // Background
        RectF background = new RectF((float) viewHolder.itemView.getRight() + dX, (float) viewHolder.itemView.getTop(), (float) viewHolder.itemView.getRight(), (float) viewHolder.itemView.getBottom());
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.borrado));
        c.drawRect(background, paint);
        // Bitmap
        float height = viewHolder.itemView.getBottom() - viewHolder.itemView.getTop();
        float width = height / 3;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_delete_black_24dp);
        RectF dst = new RectF((float) viewHolder.itemView.getRight() - 2 * width, (float) viewHolder.itemView.getTop() + width, (float) viewHolder.itemView.getRight() - width, (float) viewHolder.itemView.getBottom() - width);
        c.drawBitmap(bitmap, null, dst, paint);
    }

}
