package app.jimit.www.rxlist.di.component;

import javax.inject.Singleton;

import app.jimit.www.rxlist.data.remote.AppRemoteDataStore;
import app.jimit.www.rxlist.di.module.AppModule;
import app.jimit.www.rxlist.di.module.DataModule;
import app.jimit.www.rxlist.ui.city.CityListActivity;
import dagger.Component;

/**
 * Created by jimit on 18-01-2018.
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject(AppRemoteDataStore appRemoteDataStore);
    void inject(CityListActivity cityListActivity);
}
