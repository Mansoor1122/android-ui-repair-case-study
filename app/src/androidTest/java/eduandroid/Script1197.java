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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1197 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "swati4star.createpdf";
    private static final String AndroidOS = "com.android.packageinstaller";
    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "UiAutomator";

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
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
    public void testChangeText_sameActivity() {

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Permissions
        UiObject2 allow = mDevice.wait(Until.findObject(By.res(AndroidOS, "permission_allow_button")),2000);
        allow.click();
        allow = mDevice.wait(Until.findObject(By.res(AndroidOS, "permission_allow_button")),2000);
        allow.click();

        // Create pdf
        UiObject2 create = mDevice.wait(Until.findObject(By.text( "SELECT IMAGES")),2000);
        create.click();

        UiObject2 picture = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "btn_take_picture")),2000);
        picture.click();

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UiObject2 done = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "action_done")),2000);
        done.click();
        create = mDevice.wait(Until.findObject(By.text( "CREATE PDF")),2000);
        create.click();

        UiObject2 input = mDevice.wait(Until.findObject(By.res("android", "input")),2000);
        input.setText("abc");

        done = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "md_buttonDefaultPositive")),2000);
        done.click();


        // Navigate to view files
        UiObject2 desc = mDevice.wait(Until.findObject(By.desc( "Open navigation drawer")),2000);
        desc.click();

        UiObject2 view = mDevice.wait(Until.findObject(By.text( "View Files")),2000);
        view.click();

        UiObject2 pdf = mDevice.wait(Until.findObject(By.text( "abc.pdf")),2000);
        pdf.click();

        pdf = mDevice.wait(Until.findObject(By.text( "Add Password")),2000);
        pdf.click();

        pdf = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "password")),2000);
        pdf.setText("test");

        done = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "md_buttonDefaultPositive")),2000);
        done.click();

        pdf = mDevice.wait(Until.findObject(By.textStartsWith( "abc_encry")),2000);
        pdf.click();

        pdf = mDevice.wait(Until.findObject(By.text( "Add Password")),2000);
        pdf.click();

        pdf = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "password")),2000);
        pdf.setText("test");

        done = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "md_buttonDefaultPositive")),2000);
        done.click();
        


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
