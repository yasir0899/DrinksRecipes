package com.example.providerportal.utils;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.sql.DriverManager.println;


public class DateUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_FORMAT_2 = "yyyy-MM-dd";
    private static final String DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_FORMAT_6 = "MM/dd/yyyy HH:mm:ss a";
    private  static  final  String DATE_FORMAT_4="dd\nMMM";
    private static final String DATE_FORMAT_5="MM-dd-yyyy";
    private static final String DATE_FORMAT_7="MM/dd/yyyy";


    /**
     * @param years Required Years in Integer
     * @return this methods takes @Code{int years} as input parameters. e.g. 2 for moving 2 years later, -2 for going 2 years back
     */
    public static String getTwoYearsDiffDate(int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Format f = new SimpleDateFormat(DATE_FORMAT_2, Locale.getDefault());
        calendar.add(Calendar.YEAR, years);
        return f.format(calendar.getTime());
    }

    public static Long convertDateInMilliseconds(String selectedDate) {
        String givenDateString = selectedDate;
        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: $timeInMilliseconds");
        } catch (ParseException e) {
        }
        return timeInMilliseconds;
    }

    public static String getFormatedDate(Date date) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DateUtils.DATE_FORMAT, Locale.getDefault());
        return DATE_FORMAT.format(date);

    }

    public static long getDateInLong(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            Date date = sdf.parse(dateString);
            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentMonthYear() {
        String PATTERN = "MMM - yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN, Locale.getDefault());
//        dateFormat.applyPattern(PATTERN);
        return dateFormat.format(Calendar.getInstance().getTime());
    }


    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_7, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDateFormat1() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public static Calendar getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String currentTime = sdf.format(cal.getTime());
        try {
            cal.setTime(sdf.parse(currentTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    static String getTimeFromLong(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * This method will get start and end time and will check if the current date time lies between start and end time or not?
     *
     * @param eventStartDate Event starting date
     * @param eventEndDate   Event ending date
     */
    public static boolean checkTimeIntervals(Calendar eventStartDate, Calendar eventEndDate) {
        Calendar currentDate = getTime();
// only subtract hour if hours are greater than 00
        if (eventStartDate.get(Calendar.HOUR) > 0) {
            // subtracting an hour
            eventStartDate.add(Calendar.HOUR, -1);
        }
        // only add hour when hour is less than 23
        if (eventEndDate.get(Calendar.HOUR) < 11) {
            // adding  an hour
            eventEndDate.add(Calendar.HOUR, 1);
        }

        long startTime = eventStartDate.getTimeInMillis();
        long currentTime = currentDate.getTimeInMillis();
        long endTime = eventEndDate.getTimeInMillis();

        return startTime <= currentTime && currentTime <= endTime;
    }


    public static Calendar getEventDate(String time, String orderDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date strDate = null;
        try {
            strDate = sdf.parse(orderDate + " " + time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(strDate);
        return cal;
    }

    public static String getTimeDifference(String givenStartTime, String givenEndTime) {
        DateTime startTime, endTime;
        String antalTimer;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        startTime = formatter.parseDateTime(givenStartTime);
        endTime = formatter.parseDateTime(givenEndTime);

        Period p = new Period(startTime, endTime);
        long hours = p.getHours();
        long minutes = p.getMinutes();


        if (hours == 0) {
            antalTimer = p.getMinutes() + " m";
        } else if (minutes == 0) {
            antalTimer = p.getHours() + " t";
        } else {
            antalTimer = p.getHours() + " t " + p.getMinutes() + " m";

        }
        return antalTimer;
    }

    public static String parseDate(String date) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat(DATE_FORMAT_2, Locale.getDefault());

        try {

            return newFormat.format(oldFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String parseDateForServer(String date) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("mm/dd/yyyy", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat(DATE_FORMAT_2, Locale.getDefault());

        try {

            return newFormat.format(oldFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String parseDateToServer(String date) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat(DATE_FORMAT_3, Locale.getDefault());

        try {

            return newFormat.format(oldFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseTime(String time) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("H:m", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {

            return newFormat.format(oldFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static boolean checkTimes(String timeFrom, String timeTo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        long statTime = 0;
        long endTime = 0;

        try {
            statTime = dateFormat.parse(timeFrom).getTime();
            endTime = dateFormat.parse(timeTo).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endTime > statTime;
    }

    public static String dateFormat(String date1) throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm aa", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        String inputText = date1;
        Date date = inputFormat.parse(inputText);
        String outputText = outputFormat.format(date);
        return outputText;
    }


    public static String getTodayDate() {
        Calendar c = Calendar.getInstance();
        println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }

    public static String parseDateTODateMonth(String date) {
        SimpleDateFormat oldFormat = new SimpleDateFormat(DATE_FORMAT_3, Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat(DATE_FORMAT_4, Locale.getDefault());

        try {

            return newFormat.format(oldFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static  String covertToTime(String yourMilliSeconds){

        DateFormat inputFormat = new SimpleDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss'Z'", Locale.getDefault());
        DateFormat outputFormat = new SimpleDateFormat("hh:mm aa");
        String startDateStr =yourMilliSeconds;
        Date date = null;
        String startDateStrNewFormat="";
        try {
            date = inputFormat.parse(startDateStr);
            startDateStrNewFormat = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDateStrNewFormat;
    }

    public static String convertDateTime(String dateTime){
        DateFormat inputFormat = new SimpleDateFormat(DATE_FORMAT_3, Locale.getDefault());
        DateFormat outputFormat = new SimpleDateFormat("MM/dd");
        String startDateStr =dateTime;
        Date date = null;
        String startDateStrNewFormat="";
        try {
            date = inputFormat.parse(startDateStr);
            startDateStrNewFormat = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDateStrNewFormat;


    }
    public static String convertDateTime1(String dateTime){
        DateFormat inputFormat = new SimpleDateFormat(DATE_FORMAT_6, Locale.getDefault());
        DateFormat outputFormat = new SimpleDateFormat("MM/dd/YYYY");
        String startDateStr =dateTime;
        Date date = null;
        String startDateStrNewFormat="";
        try {
            date = inputFormat.parse(startDateStr);
            startDateStrNewFormat = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDateStrNewFormat;


    }

    public static String parseDateForTable(String date) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM\ndd-yy", Locale.getDefault());

        try {

            return newFormat.format(oldFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getPreviousMonthDate(Integer amount){
        // final SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        Format f = new SimpleDateFormat(DATE_FORMAT_7, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        //cal.set(Calendar.DAY_OF_MONTH,4);
       // cal.add(Calendar.MONTH, -amount);
        cal.add(Calendar.DATE,-90);

        Date preMonthDate = cal.getTime();
        return f.format(preMonthDate);
    }

    public static String getCurrentMonthFirstDate(){
        // final SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        Format f = new SimpleDateFormat(DATE_FORMAT_7, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
       // cal.add(Calendar.MONTH, -1);

        Date preMonthDate = cal.getTime();
        return f.format(preMonthDate);
    }

    public static String getFirstDateOfCurrentYear(){
        // final SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        Format f = new SimpleDateFormat(DATE_FORMAT_7, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        cal.set(Calendar.MONTH,0);
        cal.set(Calendar.DATE,1);
        Date preMonthDate = cal.getTime();
        return f.format(preMonthDate);
    }

    public static String getPreviousMonthFirstDate(){
        // final SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        Format f = new SimpleDateFormat(DATE_FORMAT_7, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH, -1);

        Date preMonthDate = cal.getTime();
        return f.format(preMonthDate);


    }

    public static String getPreviousMonthLastDate(){
        // final SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        Format f = new SimpleDateFormat(DATE_FORMAT_7, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
       // cal.getActualMaximum(Calendar.DAY_OF_MONTH );
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

        Date preMonthDate = cal.getTime();
        return f.format(preMonthDate);


    }
}
