package and;

import android.content.Context;

/**
 * Created by fuzhipeng on 2016/12/9.
 * 例如:Configuration.Build.init(this).perform();
 */
public class Configuration {

    private final Context appContext;

    private static Configuration instance;

    private Configuration(Build build) {
        instance = this;
        appContext = build.appContext;
    }

    public static Configuration getInstance() {
        if (instance == null)
            throw new IllegalStateException("please use Inner Build's perform() ");
        else
            return instance;
    }

    public Context getAppContext() {
        return appContext;
    }

    public static class Build {

        private Context appContext;

        private Build() {
        }

        public static Build init(Context appContext) {
            Build build = new Build();
            build.appContext = appContext.getApplicationContext();
            return build;
        }

        public Configuration perform() {
            return new Configuration(this);
        }
    }
}
