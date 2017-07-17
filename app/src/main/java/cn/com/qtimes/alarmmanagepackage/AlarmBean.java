package cn.com.qtimes.alarmmanagepackage;

/**
 * Created by qtimes002 on 2017/7/17.
 */

import java.io.Serializable;

/**
 * 定时器的实体
 * Created by liutao on 2017/2/7.
 */

public class AlarmBean implements Serializable {
    /**
     * @param flag            周期性时间间隔的标志,flag = 0 表示一次性的闹钟, flag = 1 表示每天提醒的闹钟(1天的时间间隔),flag = 2
     * 表示按周每周提醒的闹钟（一周的周期性时间间隔）
     * @param hour            时
     * @param minute          分
     * @param id              闹钟的id，指定要弹出的dialog类型
     * @param week            week=0表示一次性闹钟或者按天的周期性闹钟，非0 的情况下是几就代表以周为周期性的周几的闹钟
     * @param type            弹出闹钟的类型
     */
    private int hourOfDay;
    private int minute;
    private int second;
    private int type;
    private int flag;//定时闹钟
    private int week;
    public boolean isAlarm;
    public boolean hasData;
    private int mediaType;
    private int day;

    public AlarmBean(int hourOfDay, int minute, int second, int type, boolean isAlarm, boolean hasData) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
        this.isAlarm = isAlarm;
        this.hasData = hasData;
        this.type = type;
    }

    public AlarmBean(int hourOfDay, int minute, int second, int type, int flag, int week, boolean isAlarm, boolean hasData, int mediaType) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
        this.type = type;
        this.flag = flag;
        this.week = week;
        this.isAlarm = isAlarm;
        this.hasData = hasData;
        this.mediaType = mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getMediaType() {
        return mediaType;
    }

    public int getIndex() {
        if(mediaType==100){
            return 1;
        }
        return mediaType >= 2 ? mediaType - 1 : mediaType;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }
    public int getDay(){
        return  day;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
    public int getAllHourNoMinute(){
        return day*12+hourOfDay;
    }
    public float getAllHours(){
        return day*12+hourOfDay+minute/60f;
    }
    public String getTime() {
        return (getAllHourNoMinute() < 10 ? "0" + getAllHourNoMinute() : getAllHourNoMinute()) + ":" + (minute >= 10 ? minute : "0" + minute);
    }

    public AlarmBean getAlarmBean() {
        AlarmBean alarmBean = new AlarmBean(hourOfDay, minute, second, type, flag, week, isAlarm, hasData, mediaType);
        alarmBean.day=day;
        return alarmBean;
    }

    public void addMinutes(int minutes) {
        minute+=minutes;
        int h = minute / 60;
        int m = minute % 60;
        hourOfDay+=h;
        minute=m;
        if(minute<0){
            minute+=60;
            hourOfDay-=1;
            if(hourOfDay<0){
                hourOfDay+=12;
                day--;
                if(day<0){
                    day=0;
                }
            }
        }
        if(hourOfDay>=12){
            day++;
            hourOfDay=hourOfDay%12;
        }

    }
}
