package app.jimit.www.rxlist.data.local.models;

import com.google.gson.annotations.Expose;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverColumn;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverType;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.io.Serializable;

import app.jimit.www.rxlist.data.local.DatabaseContract;

/**
 * Created by jimit on 18-01-2018.
 */

@StorIOSQLiteType(table = DatabaseContract.City.TABLE_NAME)
@StorIOContentResolverType(uri = DatabaseContract.City.CONTENT_URI_STRING)
public class City implements Serializable {

    @Expose
    @StorIOSQLiteColumn(name = DatabaseContract.City.COLUMN_ID, key = true)
    @StorIOContentResolverColumn(name = DatabaseContract.City.COLUMN_ID, key = true)
    public Integer id;

    @Expose
    @StorIOSQLiteColumn(name = DatabaseContract.City.COLUMN_NAME)
    @StorIOContentResolverColumn(name = DatabaseContract.City.COLUMN_NAME)
    public String name;

    @Expose
    @StorIOSQLiteColumn(name = DatabaseContract.City.COLUMN_SLUG)
    @StorIOContentResolverColumn(name = DatabaseContract.City.COLUMN_SLUG)
    public String slug;

    public City(Integer id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public City() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
