package app.kulture.kucherenko.init.com.newsapplication;

import android.app.Application;
import android.content.res.Resources;

/**
 * @author alex
 * Created on 4/11/17.
 */

public class App extends Application {

    private Resources res;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        res = getResources();
    }

    public static App getInstance() {
        return instance;
    }

    /**
     * Simplifying of getting application resources
     * @return resources
     */
    public Resources getMResources() {
        return res;
    }
}
