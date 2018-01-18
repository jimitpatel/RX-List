package app.jimit.www.rxlist.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import java.util.List;

import javax.inject.Inject;

import app.jimit.www.rxlist.data.AppDataStore;
import app.jimit.www.rxlist.data.local.models.City;
import app.jimit.www.rxlist.data.local.models.CityStorIOContentResolverDeleteResolver;
import app.jimit.www.rxlist.data.local.models.CityStorIOContentResolverGetResolver;
import app.jimit.www.rxlist.data.local.models.CityStorIOContentResolverPutResolver;
import rx.Observable;

/**
 * Created by jimit on 18-01-2018.
 */

public class AppLocalDataStore implements AppDataStore {

    private StorIOContentResolver mStorIoContentResolver;

    @Inject
    public AppLocalDataStore(@NonNull Context context) {
        mStorIoContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(context.getContentResolver())
                .addTypeMapping(City.class, ContentResolverTypeMapping.<City>builder()
                    .putResolver(new CityStorIOContentResolverPutResolver())
                    .getResolver(new CityStorIOContentResolverGetResolver())
                    .deleteResolver(new CityStorIOContentResolverDeleteResolver())
                    .build()
                ).build();
    }

    @Override
    public Observable<List<City>> getCityList() {
        return mStorIoContentResolver.get()
                .listOfObjects(City.class)
                .withQuery(Query.builder().uri(DatabaseContract.City.CONTENT_URI).build())
                .prepare()
                .asRxObservable();
    }

    @Override
    public Observable<List<City>> getCityList(String keyword) {
        return mStorIoContentResolver.get()
                .listOfObjects(City.class)
                .withQuery(Query.builder().uri(DatabaseContract.City.CONTENT_URI).where("LOWER(" + DatabaseContract.City.COLUMN_NAME + ") LIKE LOWER('%" + keyword + "%')").build())
                .prepare()
                .asRxObservable();
    }

    public void saveCityToDatabase(List<City> cities) {
        mStorIoContentResolver.put().objects(cities).prepare().executeAsBlocking();
    }
}
