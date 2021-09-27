package design.morristech.openweather.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import design.morristech.openweather.R
import design.morristech.openweather.data.local.db.WeatherDataBase.Companion.DATABASE_NAME
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirstAppLaunchTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(WeatherActivity::class.java)

    companion object {
        @JvmStatic
        @BeforeClass
        fun clearDatabaseBeforeFirstLaunch() {
            InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(DATABASE_NAME)
        }
    }

    @Test
    fun firstAppLaunchOpensSearchScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.enterTextHint))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
