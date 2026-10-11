/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eduandroid;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.RemoteException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Basic example for unbundled UiAutomator.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class harmonic_bugFeature130 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.simon.harmonichackernews";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "UiAutomator";

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen()  {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();
        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testRealHarmonicFeatures130() throws IOException, UiObjectNotFoundException, RemoteException {


        mDevice.swipe(500, 1600, 500, 800, 20);

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //click OK
        UiObject2 start = mDevice.wait(Until.findObject(By.text("Get started")),2000);
        if(start != null)
        {
            start.click();
        }

        start = mDevice.wait(Until.findObject(By.text("Done")),2000);
        if(start != null)
        {
            start.click();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 topSort = mDevice.wait(Until.findObject(By.text("Top Stories")),4000);
        topSort.click();
        UiObject2 pastDay = mDevice.wait(Until.findObject(By.text("Last 24 hours")),2000);
        pastDay.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String pastDayStory = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"story_title")),2000).getText();

        UiObject2 pastDay2 = mDevice.wait(Until.findObject(By.text("Last 24 hours")),4000);
        pastDay2.click();

        UiObject2 dropDownTop = mDevice.wait(Until.findObject(By.text("Top Stories")),2000);
        dropDownTop.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 topSort2 = mDevice.wait(Until.findObject(By.text("Top Stories")),4000);
        topSort2.click();

        UiObject2 pastDay3 = mDevice.wait(Until.findObject(By.text("Last 24 hours")),2000);
        pastDay3.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 pastDay4 = mDevice.wait(Until.findObject(By.text("Last 24 hours")),4000);
        pastDay4.click();

        UiObject2 topSort3 = mDevice.wait(Until.findObject(By.text("Top Stories")),2000);
        topSort3.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String topStory = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"story_title")),2000).getText();
        assertNotEquals(topStory, pastDayStory);


        UiObject2 search = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "stories_header_search_button")), 2000);
        assertNotNull(search);
        assert(search.isClickable());

        UiObject2 more = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "stories_header_more")), 2000);
        assertNotNull(more);
        assert(more.isClickable());

    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}

