package app.jimit.www.rxlist.data;

import java.util.List;

import app.jimit.www.rxlist.data.local.models.City;
import rx.Observable;

/**
 * Created by jimit on 18-01-2018.
 */

public interface AppDataStore {
    Observable<List<City>> getCityList();
    Observable<List<City>> getCityList(String keyword);
}
