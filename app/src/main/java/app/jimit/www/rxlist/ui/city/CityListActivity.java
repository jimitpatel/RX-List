package app.jimit.www.rxlist.ui.city;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import app.jimit.www.rxlist.R;
import app.jimit.www.rxlist.RxListApp;
import app.jimit.www.rxlist.data.AppRepository;
import app.jimit.www.rxlist.data.local.models.City;
import app.jimit.www.rxlist.ui.city.list.CityAdapter;
import app.jimit.www.rxlist.utils.KeyboardUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CityListActivity extends AppCompatActivity implements CityListContract.View, SwipeRefreshLayout.OnRefreshListener, CityAdapter.CityCallback {

    private static final String TAG = CityListActivity.class.getSimpleName();

    private CityListContract.Presenter mPresenter;
    private CityAdapter mAdapter;

    @BindView(R.id.swipe_refresh) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.txt_search) EditText txtSearch;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Inject AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        RxListApp.getApplication().inject(this);
        mPresenter = new CityListPresenter(repository, this);

        refreshLayout.setOnRefreshListener(this);
        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                    KeyboardUtils.hideSoftInput(CityListActivity.this);
                    String keyword = txtSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        mPresenter.loadCities();
                    } else {
                        mPresenter.loadCities(keyword);
                    }
                    return true;
                }
                return false;
            }
        });
        mPresenter.loadCities();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mPresenter.loadCitiesFromRemoteDataStore();
    }

    @Override
    public void setPresenter(CityListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showCities(List<City> cities) {
        if (null != cities && cities.size() > 0) {
            mAdapter = new CityAdapter(cities, this, this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        toggleRefreshing(false);
    }

    @Override
    public void showComplete() {
        toggleRefreshing(false);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onCityClick(int position) {
        Toast.makeText(this, mPresenter.getCity(position).getName(), Toast.LENGTH_SHORT).show();
    }

    private void toggleRefreshing(final boolean refresh) {
        if (null != refreshLayout) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(refresh);
                    mRecyclerView.setVisibility(!refresh ? View.VISIBLE : View.GONE);
                    progressBar.setVisibility(refresh ? View.VISIBLE : View.GONE);
                    if (refresh)
                        mPresenter.loadCitiesFromRemoteDataStore();
                }
            });
        }
    }
}
