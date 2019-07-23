package com.eijs.creditscore.others;

public class Global {
    public static String ecode = null;
    public static String ename = null;
    public static String password = null;
    public static String date = null;
    public static String d1d2 = null;
    public static String netid = null;
    public static String hname = null;
    public static String finyear = null;
    public static String emplevel = null;
    public static String day = null;
    public static String mth = null;
    public static String yr = null;

    public Global() {
        ecode = null;
        ename = null;
        password = null;
        date = null;
        d1d2 = null;
        netid = null;
        hname = null;
        finyear = null;
        emplevel = null;
        day = null;
        mth = null;
        yr = null;
    }

    public static String getFinancialYr(String logMth,String logYr){
        String finStrtMth = "04";
        //String logMth = "01";
        //String logYr = "2019";

        int endMth = Integer.parseInt(finStrtMth)-1;
        int strtYr = getFinStrtYr(Integer.parseInt(finStrtMth),Integer.parseInt(logMth),Integer.parseInt(logYr));
        int endYr=0;

        if(endMth==0)
        {
            endMth=12;
            endYr=strtYr;
        }else{
            endYr=strtYr+1;
        }
        //System.out.println("Financial Year is :"+finStrtMth+strtYr+" to "+endMth+""+endYr);

        //return strtYr+finStrtMth+"-"+endYr+""+((endMth+"").length()<2 ? "0"+endMth : endMth);
        //String syr = Integer.toString(strtYr).substring(2);
        //String eyr = Integer.toString(endYr).substring(2);
        return strtYr+"~"+endYr;
    }



    public static int getFinStrtYr(int strtMth,int logMth,int logYr){
        if(logMth < strtMth){ //login date=012017 -> (logmth)01 <= (endMth)0
            return (logYr-1); //2017
        }else{ //login date=042017 -> 04>03
            return logYr; //2016
        }
    }
}
