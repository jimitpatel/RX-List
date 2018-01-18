package app.jimit.www.rxlist;

/**
 * Created by jimit on 18-01-2018.
 */

public interface BasePresenter {
    void subscribe();
    void unsubscribe();
    void onDetach();
}
