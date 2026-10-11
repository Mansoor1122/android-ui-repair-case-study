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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class harmonic_mutant_related_544 {

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
    public void testHarmonic544() throws IOException, UiObjectNotFoundException {
        mDevice.swipe(100,1800,100,500,20);

        UiObject2 materials = mDevice.wait(Until.findObject(By.text("Material You (auto)")),2000);
        UiObject2 gray = mDevice.wait(Until.findObject(By.text("Gray")),2000);
        UiObject2 thumbnailsOn = mDevice.wait(Until.findObject(By.text("Thumbnails ON")),2000);
        assertNotNull(materials);
        assertNotNull(gray);
        assertNotNull(thumbnailsOn);

        //click Get Started
        UiObject2 get_started = mDevice.wait(Until.findObject(By.text("Get started")),2000);
        get_started.click();


        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject comment = mDevice.findObject(new UiSelector().clickable(true).instance(4));
        comment.click();

        UiObject2 bookmark = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "comments_header_button_bookmark")),2000);
        bookmark.click();

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 back = mDevice.wait(Until.findObject(By.res("com.android.systemui", "back")),2000);
        back.click();

        UiObject2 more = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "stories_header_more")),2000);
        more.click();

        UiObject2 settings = mDevice.wait(Until.findObject(By.text("Settings")),2000);
        settings.click();
        mDevice.swipe(100,1800,100,500,20);
        mDevice.swipe(100,1800,100,500,20);
        mDevice.swipe(100,1800,100,500,20);
        mDevice.swipe(100,1800,100,500,20);
        mDevice.swipe(100,1800,100,500,20);
        mDevice.swipe(100,1800,100,500,20);

        UiObject2 export_bookmarks = mDevice.wait(Until.findObject(By.text("Export bookmarks")),2000);
        export_bookmarks.click();

        UiObject2 bookmark_file = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),2000);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

        assertEquals(bookmark_file.getText(),"HarmonicBookmarks"+today.format(formatter)+".txt");
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
