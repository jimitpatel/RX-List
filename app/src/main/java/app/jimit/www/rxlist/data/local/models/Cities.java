package app.jimit.www.rxlist.data.local.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jimit on 18-01-2018.
 */

public class Cities implements Serializable {

    @Expose private Meta meta;
    @Expose private List<City> objects;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<City> getObjects() {
        return objects;
    }

    public void setObjects(List<City> objects) {
        this.objects = objects;
    }
}
