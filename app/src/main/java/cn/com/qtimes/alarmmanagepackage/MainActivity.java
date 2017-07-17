package cn.com.qtimes.alarmmanagepackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlarmBean alarmBean = new AlarmBean(20, 8, 0, 1, true, true);
        alarmBean.setFlag(1);//设为以天为周期的闹钟
        alarmBean.setHasData(true);
        alarmBean.setAlarm(true);
        AlarmManageUtil.setAlarm(this, alarmBean);
    }
}
