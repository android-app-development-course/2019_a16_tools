// 随机数生成器UI测试

package com.example.dingdangpocket;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RandomNumberActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<RandomNumberActivity> mActivityRule = new ActivityTestRule<>(
            RandomNumberActivity.class);


    @Test
    public void gen_random() throws InterruptedException {

        for(int i = 0; i < 10; i++){
            int min = (new Random()).nextInt(100);
            int max = (new Random()).nextInt(123456789) + 101;

            onView(withId(R.id.edittext_random_min)).perform(typeText(String.valueOf(min)), closeSoftKeyboard());
            onView(withId(R.id.edittext_random_max)).perform(typeText(String.valueOf(max)), closeSoftKeyboard());
            onView(withId(R.id.random_generate)).perform(click());
            onView(withId(R.id.textview_random_number)).check(matches(isDisplayed()));
            Thread.sleep(1000);
            onView(withId(R.id.edittext_random_min)).perform(clearText());
            onView(withId(R.id.edittext_random_max)).perform(clearText());
        }

    }
}
