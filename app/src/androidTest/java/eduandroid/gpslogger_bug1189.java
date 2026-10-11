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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.*;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class gpslogger_bug1189 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.mendhak.gpslogger";

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
    public void testChangeText_sameActivity() throws IOException, UiObjectNotFoundException {

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //click OK
        UiObject2 ok = mDevice.wait(Until.findObject(By.text("OK")),2000);
        ok.click();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //click Allow
        UiObject2 allow = mDevice.wait(Until.findObject(By.text("While using the app")),2000);
        allow.click();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        allow = mDevice.wait(Until.findObject(By.text("Allow")),2000);
        allow.click();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ok = mDevice.wait(Until.findObject(By.text("OK")),2000);
        ok.click();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 allTime = mDevice.wait(Until.findObject(By.text("Allow all the time")),2000);
        allTime.click();

        mDevice.pressBack();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        allow = mDevice.wait(Until.findObject(By.text("Allow")),2000);
        allow.click();

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ArrayList<UiObject2> gpsElements = new ArrayList<>();
        gpsElements.add(mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "mnuAnnotate")), 2000));
        gpsElements.add(mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "mnuOnePoint")), 2000));
        gpsElements.add(mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "mnuUpload")), 2000));
        gpsElements.add(mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "mnuShare")), 2000));


        String launcherPackage = getLauncherPackageName();
        ArrayList<UiObject2> homeElements = new ArrayList<>();
        homeElements.add(mDevice.wait(Until.findObject(By.desc("Back")), 2000));
        homeElements.add(mDevice.wait(Until.findObject(By.desc("Home")), 2000));
        homeElements.add(mDevice.wait(Until.findObject(By.desc("Recents")), 2000));

        //remove after test is fixed
        for (int i = 0; i < gpsElements.size(); i++) {
            assertNotNull(gpsElements.get(i));
        }

        for (int i = 0; i < homeElements.size(); i++) {
            assertNotNull(homeElements.get(i));
        }

        for (int i = 0; i < homeElements.size(); i++) {
            for (int j = 0; j < gpsElements.size(); j++) {
                Rect gpsRect = gpsElements.get(j).getVisibleBounds();
                Rect homeRect = homeElements.get(i).getVisibleBounds();

                System.out.println("Testing intersection between home[" + i + "] and gps[" + j + "]");
                System.out.println("Home bounds: " + homeRect);
                System.out.println("GPS bounds: " + gpsRect);

                assertFalse(Rect.intersects(homeRect, gpsRect));
            }
        }
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
