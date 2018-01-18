package app.jimit.www.rxlist.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import app.jimit.www.rxlist.BuildConfig;
import app.jimit.www.rxlist.data.local.AppLocalDataStore;
import app.jimit.www.rxlist.data.remote.AppRemoteDataStore;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jimit on 18-01-2018.
 */

@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPrederences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        return new Cache(application.getCacheDir(), 10 * 1024 *1024);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(providesInterceptor())
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BuildConfig.API)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    AppLocalDataStore providesAppLocalDataStore(Application context) {
        return new AppLocalDataStore(context);
    }

    @Provides
    @Singleton
    AppRemoteDataStore providesRepository() {
        return new AppRemoteDataStore();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
