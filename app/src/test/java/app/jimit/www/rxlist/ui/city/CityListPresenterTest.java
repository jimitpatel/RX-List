package app.jimit.www.rxlist.ui.city;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import app.jimit.www.rxlist.data.AppRepository;
import app.jimit.www.rxlist.data.local.models.City;
import rx.Observable;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by jimit on 19-01-2018.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class CityListPresenterTest {

    @Mock CityListContract.View  mockView;
    @Mock AppRepository mockAppRepository;

    private CityListPresenter mCityPresenter;
    private TestScheduler mTestScheduler;
    private List<City> cityList;

    @BeforeClass
    public static void onlyOnce() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        mCityPresenter = new CityListPresenter(mockAppRepository, mockView);
        cityList = new ArrayList<>();
    }

    @Test
    public void testCityListObserver() {
        doReturn(Observable.just(mockAppRepository.getCityList()))
                .when(mockAppRepository)
                .getCityList();
        mTestScheduler.triggerActions();

        verify(mockView).showComplete();
        verify(mockView).showCities(cityList);
    }

    @After
    public void tearDown() throws Exception {
        mCityPresenter.onDetach();
    }
}
