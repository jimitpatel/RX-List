package app.jimit.www.rxlist.data;

import java.util.List;

import javax.inject.Inject;

import app.jimit.www.rxlist.data.local.AppLocalDataStore;
import app.jimit.www.rxlist.data.local.models.City;
import app.jimit.www.rxlist.data.remote.AppRemoteDataStore;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jimit on 18-01-2018.
 */

public class AppRepository implements AppDataStore {

    private static final String TAG = AppRepository.class.getSimpleName();

    private AppLocalDataStore mAppLocalDataStore;
    private AppRemoteDataStore mAppRemoteDataStore;

    @Inject
    public AppRepository(AppLocalDataStore appLocalDataStore, AppRemoteDataStore appRemoteDataStore) {
        this.mAppLocalDataStore = appLocalDataStore;
        this.mAppRemoteDataStore = appRemoteDataStore;
    }

    @Override
    public Observable<List<City>> getCityList() {
        return Observable.concat(mAppLocalDataStore.getCityList(), mAppRemoteDataStore.getCityList())
                .first(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object cities) {
                        return cities != null;
                    }
                });
    }

    @Override
    public Observable<List<City>> getCityList(String keyword) {
        return mAppLocalDataStore.getCityList(keyword);
    }
}
