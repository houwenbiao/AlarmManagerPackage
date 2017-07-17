package cn.com.qtimes.alarmmanagepackage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by qtimes002 on 2017/7/17.
 */

public class AlarmManageUtil {
    public static final String ALARM_ACTION = "com.jackhou.clock";

    public static void setAlarmTime(Context context, long timeInMillis, Intent intent){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intent.getIntExtra("type", 0), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, intent.getLongExtra("intervalMillis", 0), pendingIntent);
        }
    }

    /**
     * 取消一个闹钟
     *
     * @param context
     * @param action
     * @param type
     */
    public static void cancelAlarm(Context context, String action, int type) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, type, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
    }

    public static void setAlarm(Context context, AlarmBean alarmBean) {
        long intervalMillis = 0;//间隔时间
        if (!alarmBean.hasData || !alarmBean.isAlarm()) {
            cancelAlarm(context, ALARM_ACTION, alarmBean.getType());
            return;
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                alarmBean.getHourOfDay(),
                alarmBean.getMinute(),
                alarmBean.getSecond());
        long currentTime = System.currentTimeMillis();
        long setTime = calendar.getTimeInMillis();
        if (currentTime > setTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (alarmBean.getFlag() == 0) {//一次性闹钟
            intervalMillis = 0;
        } else if (alarmBean.getFlag() == 1) {//每天的闹钟
            intervalMillis = 24 * 3600 * 1000;
        } else if (alarmBean.getFlag() == 2) {//以周为周期的闹钟
            intervalMillis = 7 * 24 * 3600 * 1000;
        }
        Intent intent = new Intent(ALARM_ACTION);
        intent.putExtra("type", alarmBean.getType());
        intent.putExtra("intervalMillis", intervalMillis);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmBean.getType(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, getTime(alarmBean.getWeek(), calendar.getTimeInMillis()), intervalMillis, pendingIntent);
        } else {
            if (alarmBean.getFlag() == 0) {//一次性闹钟
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getTime(alarmBean.getWeek(), calendar.getTimeInMillis()), intervalMillis, pendingIntent);
            }
        }
        Log.i("zx", "setAlarm: " + alarmBean.getTime() + "  type" + alarmBean.getType());
    }

    /**
     * 返回闹钟设置时间
     *
     * @param weekFlog =0：表示一次性闹钟或者按照天为周期的闹钟，!=0,表示按照周为周期的闹钟
     * @param dataTime 系统获取的当前年月日+选择的时分秒
     * @return
     */
    private static long getTime(int weekFlog, long dataTime) {
        long time = 0;
        Calendar calendar = Calendar.getInstance();
        if (weekFlog == 0) {
            if (dataTime > System.currentTimeMillis()) {
                time = dataTime;
            } else {
                time = dataTime + 24 * 3600 * 1000;
            }
        } else {
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            switch (week) {
                case 1:
                    week = 7;
                    break;
                case 2:
                    week = 6;
                    break;
                case 3:
                    week = 5;
                    break;
                case 4:
                    week = 4;
                    break;
                case 5:
                    week = 3;
                    break;
                case 6:
                    week = 2;
                    break;
                case 7:
                    week = 1;
                    break;
            }
            if (weekFlog == week) {
                if (dataTime < System.currentTimeMillis()) {
                    time = dataTime + 7 * 24 * 3600 * 1000;
                } else {
                    time = dataTime;
                }
            } else if (weekFlog > week) {
                time = dataTime + (weekFlog - week) * 24 * 3600 * 1000;
            } else if (weekFlog < week) {
                time = dataTime + (weekFlog - week + 7) * 24 * 3600 * 1000;
            }
        }
        return time;
    }
}
