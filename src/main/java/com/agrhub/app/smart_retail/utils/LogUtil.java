package com.agrhub.app.smart_retail.utils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public enum LogUtil {
    instance;

    private static Logger log = Logger.getLogger(LogUtil.class.getName());

    @SuppressWarnings("rawtypes")
    public void info(Class className, String msg) {
        System.out.println(className.getName() + " : " + msg);
    }

    @SuppressWarnings("rawtypes")
    public void debug(Class className, String msg) {
        System.out.println(className.getName() + " : " + msg);
    }

    @SuppressWarnings("rawtypes")
    public void error(Class className, String msg, Exception e) {
        System.out.println(className.getName() + " : " + msg);
        String trace = "";
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            // stack trace as a string
            trace = sw.toString();
            System.out.println(trace);
        }
        //send error notification to administrator
        //EmailUtil.sendErrorNotify(msg, trace);
    }
}
