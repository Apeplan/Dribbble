package com.simon.agiledevelop.utils;

import android.app.Application;

import com.simon.agiledevelop.log.LLog;

/**
 * describe: Return global application
 *
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public class App {
    public static final Application INSTANCE;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod
                    ("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on" +
                        " main thread.");
        } catch (final Exception e) {
            LLog.e("Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod
                        ("currentApplication").invoke(null);
            } catch (final Exception ex) {
                LLog.e("Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            INSTANCE = app;
        }
    }
}
