package com.example.laba4;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.laba4.Database.StudentStorage;

import java.util.Arrays;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {

    final static String LOG_TAG = "myLogs";
    private static StudentStorage storage;
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, id);
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             int widgetID) {
        Log.d(LOG_TAG, "updateWidget " + widgetID);
        if (storage == null) {
            storage=new StudentStorage(context);
        }

        CharSequence widgetText = "Количество записей в бд: " + storage.getElementCount();

        // Настраиваем внешний вид виджета
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.tv, widgetText);
        Intent updateIntent = new Intent(context, MyWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{widgetID});
        PendingIntent pIntent = PendingIntent.getBroadcast(context, widgetID, updateIntent, 0);
        views.setOnClickPendingIntent(R.id.tv, pIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(widgetID, views);
    }
}