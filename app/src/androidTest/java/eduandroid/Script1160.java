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
public class Script1160 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.moez.QKSMS";
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

        UiObject2 skip = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "welcome_skip")),2000);
        skip.click();

        UiObject2 set = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "sb__action")),2000);
        set.click();

        UiObject2 allow = mDevice.wait(Until.findObject(By.res("android", "button1")),2000);
        allow.click();

        UiObject2 more = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"fab")),2000);
        more.click();

        UiObject2 recip = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"compose_recipients")),2000);
        recip.setText("Test");

        UiObject2 reply = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"compose_reply_text")),2000);
        reply.click();
        reply.setText("Test");

        UiObject2 send = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"compose_letter_count")),2000);
        send.click();

        UiObject2 more2 = mDevice.wait(Until.findObject(By.desc("More options")),2000);
        more2.click();

        UiObject2 delete = mDevice.wait(Until.findObject(By.text("Delete conversations")),2000);
        delete.click();

//        send = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"sb__action")),2000);
//        send.click();
//
//        send = mDevice.wait(Until.findObject(By.res("android","button1")),2000);
//        send.click();
//
//        send = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"compose_letter_count")),2000);
//        send.click();
//
//
//        more = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"More options")),2000);
//        more.click();
//
//        UiObject2 delete  = mDevice.wait(Until.findObject(By.text("Delete conversations")),2000);
//        delete.click();
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mDevice.click(75,133);
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mDevice.click(75,1450);
//
//
//        UiObject2 lang = mDevice.wait(Until.findObject(By.text( "Language")),2000);
//        lang.click();
//
//
//        lang = mDevice.wait(Until.findObject(By.textStartsWith( "Espa")),2000);
//        lang.click();
//
//
//        mDevice.pressBack();
//        try {
//            TimeUnit.MILLISECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mDevice.click(75,133);
//        try {
//            TimeUnit.MILLISECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mDevice.click(75,1650);
//
//        lang = mDevice.wait(Until.findObject(By.textStartsWith( "Acerca")),2000);
//        lang.click();
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