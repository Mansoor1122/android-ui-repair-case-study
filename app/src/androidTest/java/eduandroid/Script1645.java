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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1645 {

    private static final String PACKAGE_NAME
            = "com.german_software_engineers.trainerapp";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "test";

    private UiDevice uiDevice;

    @Before
    public void startActivityFromHomeScreen() {
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
    public void testScript1645() throws UiObjectNotFoundException {
        // Bug-specific GUI actions
        // Click menu
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        // Click "edit schedule"
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        // Click plus
        uiDevice.findObject(new UiSelector().clickable(true).instance(2)).click();
        // Click textbox 1
        uiDevice.findObject(new UiSelector().className("android.widget.EditText")).click();
        // Type "test"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText")).legacySetText("test");
        // Click textbox 2
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(1)).click();
        // Type "0"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(1)).legacySetText("0");
        // Click textbox 3
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(2)).click();
        // Type "0"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(2)).legacySetText("0");
        // Click textbox 4
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(3)).click();
        // Type "0"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(3)).legacySetText("0");
        // Click textbox 5
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(4)).click();
        // Type "0"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(4)).legacySetText("0");
        // Click back
        uiDevice.pressBack();
        // Click textbox 6
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(5)).click();
        // Type "test"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(4)).legacySetText("test");
        // Click back
        uiDevice.pressBack();
        // Click textbox 7
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(6)).click();
        // Type "0"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(5)).legacySetText("0");
        // Click back
        uiDevice.pressBack();
        // Click textbox 8
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(7)).click();
        // Type "0"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(5)).legacySetText("0");
        // Click next
        uiDevice.findObject(new UiSelector().className("android.widget.ImageButton")).click();
        // Click plus
        uiDevice.findObject(new UiSelector().clickable(true).instance(1)).click();
        // Click textbox 1
        uiDevice.findObject(new UiSelector().className("android.widget.EditText")).click();
        // Type "test"
        uiDevice.findObject(new UiSelector().className("android.widget.EditText")).legacySetText("test");
        // Click checkbox 4
        uiDevice.findObject(new UiSelector().className("android.widget.CheckBox").instance(3)).click();
        // Click textbox 5
        uiDevice.findObject(new UiSelector().className("android.widget.EditText").instance(4)).click();

        uiDevice.click(408, 1518);

        UiObject2 ok = uiDevice.wait(Until.findObject(By.text("OK")), 2000);
        ok.click();

        UiObject2 curScreen = uiDevice.wait(Until.findObject(By.text("Add Exercise")), 2000);
        assertNotNull(curScreen);



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
