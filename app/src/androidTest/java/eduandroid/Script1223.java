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
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1223 {

    private static final String PACKAGE_NAME
            = "org.gnucash.android";

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
    public void performGUIActions() throws UiObjectNotFoundException, IOException, RemoteException, InterruptedException {
        // Setup GUI actions
        // Click "next" three times
        uiDevice.findObject(new UiSelector().text("Next")).click();
        uiDevice.findObject(new UiSelector().text("Next")).click();
        uiDevice.findObject(new UiSelector().text("Next")).click();
        // Click option 1
        uiDevice.findObject(new UiSelector().textContains("Automatically")).click();
        // Click "next"
        uiDevice.findObject(new UiSelector().text("Next")).click();
        // Click "done"
        uiDevice.findObject(new UiSelector().text("Done")).click();
        // Click "dismiss"
        uiDevice.findObject(new UiSelector().text("Dismiss")).click();
        // Click "assets"
        uiDevice.findObject(new UiSelector().text("Assets")).click();
        // Click "current assets"
        uiDevice.findObject(new UiSelector().text("Current Assets")).click();
        // Click "cash in wallet"
        uiDevice.findObject(new UiSelector().text("Cash in Wallet")).click();
        // Click plus
        uiDevice.findObject(new UiSelector().className("android.widget.ImageButton").instance(1)).click();
        // Type "test"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText")).legacySetText("test");
        // Click textbox 2
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(1)).click();
        // Click textbox 2
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(1)).legacySetText("1");
        // Click "save"
        uiDevice.findObject(new UiSelector().text("Save")).click();
        // Bug-specific GUI actions
        // Click "test"
        uiDevice.findObject(new UiSelector().text("test")).click();
        // Click edit
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        // Click split
        uiDevice.findObject(new UiSelector().clickable(true).instance(4)).click();
        // Click "memo"
        uiDevice.findObject(new UiSelector().text("Memo")).click();
        // Type "test"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(1)).legacySetText("test");
        // Click "save" twice
        uiDevice.findObject(new UiSelector().text("Save")).click();
        uiDevice.findObject(new UiSelector().text("Save")).click();
        // Click edit
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        // Click split
        uiDevice.findObject(new UiSelector().clickable(true).instance(4)).click();
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
