package app.jimit.www.rxlist.di.component;

import javax.inject.Singleton;

import app.jimit.www.rxlist.di.module.ApplicationTestModule;
import dagger.Component;

/**
 * Created by jimit on 18-01-2018.
 */

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}
