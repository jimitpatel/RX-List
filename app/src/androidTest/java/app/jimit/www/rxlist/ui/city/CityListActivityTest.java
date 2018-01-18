package app.jimit.www.rxlist.ui.city;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.jimit.www.rxlist.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jimit on 18-01-2018.
 */

@RunWith(AndroidJUnit4.class)
public class CityListActivityTest {

    @Rule
    public ActivityTestRule<CityListActivity> cityListActivityActivityTestRule = new ActivityTestRule<>(CityListActivity.class);
//    public TestRule chain = RuleChain.outerRule(component).around(city);

    @Before
    public void setup() {

    }

    @Test
    public void checkViewDisplay() {
        onView(withId(R.id.swipe_refresh)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_search)).check(matches(isDisplayed()));
    }
}
