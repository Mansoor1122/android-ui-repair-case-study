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
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class gpslogger_bug1138 {

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
    public void testChangeText_sameActivity() throws IOException, UiObjectNotFoundException, RemoteException {

        try {
            TimeUnit.MILLISECONDS.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //click OK
        UiObject2 ok = mDevice.wait(Until.findObject(By.text("OK")), 2000);
        ok.click();
        //click Allow
        UiObject2 allow = mDevice.wait(Until.findObject(By.text("ALLOW")), 2000);
        allow.click();

        allow = mDevice.wait(Until.findObject(By.text("ALLOW")), 2000);
        allow.click();

        UiObject2 allow2 = mDevice.wait(Until.findObject(By.res("android:id/button1")), 2000);
        allow2.click();

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mDevice.click(68, 132);
        UiObject2 loggingDetails = mDevice.wait(Until.findObject(By.text("Logging details")), 2000);
        loggingDetails.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mDevice.swipe(500, 1600, 500, 800, 20);
        UiObject2 folder = mDevice.wait(Until.findObject(By.text("Save to folder")), 2000);
        folder.click();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 storage = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "storage_name")), 2000);
        storage.click();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 download = mDevice.wait(Until.findObject(By.text("Download")), 2000);
        download.click();

        UiObject2 select = mDevice.wait(Until.findObject(By.text("SELECT")), 2000);
        select.click();
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mDevice.pressBack();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mDevice.click(100, 100);

        UiObject2 timing = mDevice.wait(Until.findObject(By.text("Performance")), 2000);
        timing.click();

        UiObject2 interval = mDevice.wait(Until.findObject(By.text("Logging interval")), 2000);
        interval.click();

        UiObject timeInput = mDevice.findObject(new UiSelector().text("60"));
        timeInput.setText("3");

        ok = mDevice.wait(Until.findObject(By.text("OK")), 2000);
        ok.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mDevice.pressBack();

        UiObject2 start = mDevice.wait(Until.findObject(By.text("START LOGGING")), 2000);
        start.click();

        mDevice.pressHome();
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mDevice.swipe(500, 1600, 500, 400, 20);

        UiObject2 settings = mDevice.wait(Until.findObject(By.text("Settings")), 2000);
        settings.click();

        UiObject2 app = mDevice.wait(Until.findObject(By.text("Apps & notifications")), 2000);
        app.click();

        UiObject2 gps = mDevice.wait(Until.findObject(By.text("GPSLogger")), 2000);
        gps.click();

        UiObject2 perms = mDevice.wait(Until.findObject(By.text("Permissions")), 2000);
        perms.click();

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 storagePerm = mDevice.wait(Until.findObject(By.text("Storage")), 2000);

        storagePerm.click();


        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        UiObject2 notifTitle = mDevice.wait(Until.findObject(By.text("Error")), 2000);
        UiObject2 notifBody = mDevice.wait(Until.findObject(By.text("Could not write to file")), 2000);

        assertNotNull(notifTitle);
        assertNotNull(notifBody);
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

