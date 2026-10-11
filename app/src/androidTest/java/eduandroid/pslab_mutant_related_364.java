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
import java.util.concurrent.TimeUnit;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class pslab_mutant_related_364 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "io.pslab";

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
    public void testPslab364() throws IOException, UiObjectNotFoundException {
        //click accept
        UiObject2 accept = mDevice.wait(Until.findObject(By.text("ACCEPT")),2000);
        accept.click();
        //click Allow
        UiObject2 allow = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        allow.click();

        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 accept2 = mDevice.wait(Until.findObject(By.res("android:id/button1")),2000);
        accept2.click();
        UiObject2 allow2 = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        allow2.click();

        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 accept3 = mDevice.wait(Until.findObject(By.res("android:id/button1")),2000);
        accept3.click();
        UiObject2 allow3 = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        allow3.click();

        try {
            TimeUnit.MILLISECONDS.sleep(12000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject disconnected = mDevice.findObject(new UiSelector().clickable(true).instance(1));
        UiObject navigation_drawer = mDevice.findObject(new UiSelector().clickable(true).instance(0));
        assertNotNull(disconnected);
        assertNotNull(navigation_drawer);

        mDevice.swipe(100,1800,100,500,20);
        mDevice.swipe(100,1800,100,500,20);

        UiObject2 accelerometer = mDevice.wait(Until.findObject(By.text("ACCELEROMETER")),2000);

        UiObject2 accelerometer_text = mDevice.wait(Until.findObject(By.text("Measures the Linear acceleration in XYZ directions")),2000);
        assertNotNull(accelerometer_text);

        accelerometer.click();

        UiObject2 accelerometer_header = mDevice.wait(Until.findObject(By.text("Accelerometer")),2000);

        assertNotNull(accelerometer_header);
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
