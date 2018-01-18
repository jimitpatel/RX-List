package app.jimit.www.rxlist.data.remote;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import app.jimit.www.rxlist.BuildConfig;
import app.jimit.www.rxlist.RxListApp;
import app.jimit.www.rxlist.data.AppDataStore;
import app.jimit.www.rxlist.data.local.AppLocalDataStore;
import app.jimit.www.rxlist.data.local.models.Cities;
import app.jimit.www.rxlist.data.local.models.City;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jimit on 18-01-2018.
 */

public class AppRemoteDataStore implements AppDataStore {
    private static final String TAG = AppRemoteDataStore.class.getSimpleName();

    @Inject Retrofit retrofit;
    @Inject AppLocalDataStore appLocalDataStore;

    private List<City> cityList;

    public AppRemoteDataStore() {
        RxListApp.getApplication().inject(this);
    }

    @Override
    public Observable<List<City>> getCityList() {
        return callApi(null).flatMap(new Func1<Cities, Observable<List<City>>>() {
            @Override
            public Observable<List<City>> call(Cities cities) {
                return Observable.fromCallable(new Callable<List<City>>() {
                    @Override
                    public List<City> call() throws Exception {
                        if (null != cityList && cityList.size() > 0) appLocalDataStore.saveCityToDatabase(cityList);
                        return cityList;
                    }
                });
            }
        }) ;
    }

    @Override
    public Observable<List<City>> getCityList(String keyword) {
        return getCityList();
    }

    private Observable<Cities> callApi(String url) {
        return retrofit.create(CityService.class).getCities(!TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches() ? url : BuildConfig.API + CityService.CITY_PATH)
                .concatMap(new Func1<Cities, Observable<Cities>>() {
                    @Override
                    public Observable<Cities> call(Cities cities) {
                        if (null == cityList) cityList = cities.getObjects();
                        else    cityList.addAll(cities.getObjects());
                        if (TextUtils.isEmpty(cities.getMeta().getNext()))
                            return Observable.just(cities);
                        return Observable.just(cities)
                                .concatWith(callApi(cities.getMeta().getNext()));
                    }
                });
    }

    private interface CityService {

        String CITY_PATH = "city/";

        @GET(CITY_PATH)
        Observable<Cities> getCities();

        @GET
        Observable<Cities> getCities(@Url String url);
    }
}
