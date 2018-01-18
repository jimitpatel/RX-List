package app.jimit.www.rxlist;

import android.content.Context;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import app.jimit.www.rxlist.di.component.TestComponent;

/**
 * Created by jimit on 18-01-2018.
 */

public class TestComponentRule implements TestRule {

    private TestComponent mTestComponent;
    private Context mContext;

    public TestComponentRule(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    private void setupDaggerTestComponentInApplication() {
        RxListApp application = (RxListApp) mContext.getApplicationContext();
        application.setApplication(mTestComponent);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return null;
    }
}
