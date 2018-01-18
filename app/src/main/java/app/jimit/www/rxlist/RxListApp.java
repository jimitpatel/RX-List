package app.jimit.www.rxlist;

import android.app.Application;

import app.jimit.www.rxlist.di.component.AppComponent;
import app.jimit.www.rxlist.di.component.DaggerAppComponent;
import app.jimit.www.rxlist.di.module.AppModule;
import app.jimit.www.rxlist.di.module.DataModule;

/**
 * Created by jimit on 17-01-2018.
 */

public class RxListApp extends Application {

    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public static AppComponent getApplication() {
        return mAppComponent;
    }

    public void setApplication(AppComponent appComponent) {
        mAppComponent = appComponent;
    }
}
