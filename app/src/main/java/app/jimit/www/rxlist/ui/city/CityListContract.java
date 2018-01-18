package app.jimit.www.rxlist.ui.city;

import android.app.Activity;

import java.util.List;

import app.jimit.www.rxlist.BasePresenter;
import app.jimit.www.rxlist.BaseView;
import app.jimit.www.rxlist.data.local.models.City;

/**
 * Created by jimit on 18-01-2018.
 */

public class CityListContract {

    interface View extends BaseView<Presenter> {
        void showCities(List<City> cities);
        void showError(String message);
        void showComplete();
        Activity getActivity();
    }

    interface Presenter extends BasePresenter {
        void loadCities();
        void loadCities(String keyword);
        void loadCitiesFromRemoteDataStore();
        List<City> getCityList();
        City getCity(int position);
    }
}
