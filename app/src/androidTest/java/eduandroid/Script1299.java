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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.provider.Contacts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1299 {

    private static final String PACKAGE_NAME
            = "com.fieldbook.tracker";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "test";

    private UiDevice uiDevice;

    @Before
    public void startActivityFromHomeScreen() throws UiObjectNotFoundException {
        // Initialize UiDevice instance
        uiDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        uiDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        uiDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        uiDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testScript1299() throws UiObjectNotFoundException, IOException, RemoteException, InterruptedException {
        // Bug-specific GUI actions
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();

        // Click "no" twice
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        // Click "fields"
        uiDevice.findObject(new UiSelector().text("Fields")).click();
        // Click plus
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        // Click "local storage"
        uiDevice.findObject(new UiSelector().text("Local storage")).click();
        // Click file
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        // Click spinner 2
        uiDevice.findObject(new UiSelector().className("android.widget.Spinner").instance(1)).click();
        // Click "row"
        uiDevice.findObject(new UiSelector().text("row")).click();
        // Click spinner 3
        uiDevice.findObject(new UiSelector().className("android.widget.Spinner").instance(2)).click();
        // Click "column"
        uiDevice.findObject(new UiSelector().text("column")).click();
        // Click "import"
        uiDevice.findObject(new UiSelector().className("android.widget.Button")).click();
        // Click back
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        // Click "collect"
        uiDevice.findObject(new UiSelector().text("Collect")).click();

        //check you're still in main page by checking a few buttons.
        //should not be able to enter collect because of empty csv
        UiObject2 mainMenuButtonCheck = uiDevice.wait(Until.findObject(By.text("Collect")), 2000);
        UiObject2 mainMenuButtonCheck2 = uiDevice.wait(Until.findObject(By.text("Settings")), 2000);
        UiObject2 mainMenuButtonCheck3 = uiDevice.wait(Until.findObject(By.text("About")), 2000);
        assertNotNull(mainMenuButtonCheck);
        assertNotNull(mainMenuButtonCheck2);
        assertNotNull(mainMenuButtonCheck3);

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
