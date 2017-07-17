package cn.com.qtimes.alarmmanagepackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by qtimes002 on 2017/7/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", 0);
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0){
            AlarmManageUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis, intent);
        }
        intent.setClass(context, AlarmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
