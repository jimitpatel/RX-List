package app.jimit.www.rxlist.ui.city;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.jimit.www.rxlist.data.AppRepository;
import app.jimit.www.rxlist.data.local.models.City;
import app.jimit.www.rxlist.data.remote.AppRemoteDataStore;
import app.jimit.www.rxlist.utils.NetworkUtils;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jimit on 18-01-2018.
 */

public class CityListPresenter implements CityListContract.Presenter {

    private static final String TAG = CityListPresenter.class.getSimpleName();
    private Subscription mSubscription;
    private AppRepository mAppRepository;
    private CityListContract.View mView;
    private List<City> cityList;

    public CityListPresenter(AppRepository appRepository, CityListContract.View view) {
        mAppRepository = appRepository;
        mView = view;
//        mView.setPresenter(this);
        cityList = new ArrayList<>();
    }

    @Override
    public void subscribe() {
        loadCities();
    }

    @Override
    public void unsubscribe() {
        if (null != mSubscription && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
    }

    @Override
    public void onDetach() {
        unsubscribe();
        mView = null;
    }

    @Override
    public void loadCities() {
        mSubscription = mAppRepository.getCityList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("Error loading post");
                    }

                    @Override
                    public void onNext(List<City> cities) {
                        if (null != cities && cities.size() > 0) {
                            cityList = cities;
                            mView.showCities(cities);
                        } else {
                            loadCitiesFromRemoteDataStore();
                        }

                    }
                });
    }

    @Override
    public void loadCities(String keyword) {
        if (TextUtils.isEmpty(keyword)) loadCities();
        else mSubscription = mAppRepository.getCityList(keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("Error loading post");
                    }

                    @Override
                    public void onNext(List<City> cities) {
                        cityList = cities;
                        mView.showCities(cities);
                    }
                });
    }

    @Override
    public void loadCitiesFromRemoteDataStore() {
        if (NetworkUtils.haveNetworkConnection(mView.getActivity())) {
            new AppRemoteDataStore().getCityList().observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Observer<List<City>>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "Complete");
                            mView.showComplete();
                            loadCities();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, e.toString());
                            mView.showError("Error loading post");
                        }

                        @Override
                        public void onNext(List<City> cities) {
                            cityList = cities;
                        }
                    });
        } else {
            mView.showError("Internet not available");
        }
    }

    @Override
    public List<City> getCityList() {
        return cityList;
    }

    @Override
    public City getCity(int position) {
        if (null != cityList && cityList.size() > position && position >= 0)    return cityList.get(position);
        return null;
    }
}
