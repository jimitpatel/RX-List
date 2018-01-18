package app.jimit.www.rxlist.ui.city.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.jimit.www.rxlist.R;
import app.jimit.www.rxlist.data.local.models.City;
import app.jimit.www.rxlist.databinding.ListitemCityBinding;

/**
 * Created by jimit on 18-01-2018.
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CityCallback mCallback;
    private List<City> cityList;
    private Context context;

    public CityAdapter(List<City> cityList, Context context, CityCallback callback) {
        this.cityList = cityList;
        this.context = context;
        mCallback = callback;
    }

    public interface CityCallback {
        void onCityClick(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListitemCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.listitem_city, parent, false);
        return new CityViewHolder(binding, mCallback);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CityViewHolder)
            ((CityViewHolder) holder).onBind(cityList, position);
    }

    @Override
    public int getItemCount() {
        if (null == cityList) return 0;
        return cityList.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        private ListitemCityBinding binding;
        private CityCallback mCallback;

        public CityViewHolder(ListitemCityBinding binding, CityCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            mCallback = callback;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onCityClick(getAdapterPosition());
                }
            });
        }

        protected void onBind(List<City> cityList, int position) {
            City city = cityList.get(position);
            binding.setCity(city);
            binding.executePendingBindings();
            itemView.setTag(city);
        }
    }
}
