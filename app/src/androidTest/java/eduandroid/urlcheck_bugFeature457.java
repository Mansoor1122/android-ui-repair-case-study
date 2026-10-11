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
import static org.junit.Assert.assertNull;
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
public class urlcheck_bugFeature457 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.trianguloy.urlchecker";

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
    public void testRealUrlcheckFeatures457() throws IOException, UiObjectNotFoundException, RemoteException {

        UiObject2 skip = mDevice.wait(Until.findObject(By.text("Skip tutorial")), 2000);
        skip.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 module = mDevice.wait(Until.findObject(By.text("Modules")), 2000);
        UiObject2 automations = mDevice.wait(Until.findObject(By.text("Automations")), 2000);
        UiObject2 settings = mDevice.wait(Until.findObject(By.text("Settings")), 2000);
        UiObject2 about = mDevice.wait(Until.findObject(By.text("About")), 2000);

        assertNotNull(module);
        assertNotNull(automations);
        assertNotNull(settings);
        assertNotNull(about);

        UiObject2 automate = mDevice.wait(Until.findObject(By.text("Automations")), 2000);
        automate.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UiObject2 edit = mDevice.wait(Until.findObject(By.text("Json editor")), 2000);
        edit.click();

        UiObject input = mDevice.findObject(new UiSelector().resourceId("com.trianguloy.urlchecker:id/data"));

        String json = "{\"Unshort Tinyurl\":{\"regex\":\"https?:\\/\\/tinyurl.com\\/.*\",\"action\":\"unshort\",\"enabled\":true},\"Unshort Shorturl\":{\"regex\":\"https?:\\/\\/shorturl.at\\/.*\",\"action\":\"unshort\",\"enabled\":true}}";
        input.setText(json);

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mDevice.click(72, 136);
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mDevice.click(901, 1065);
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mDevice.click(72, 136);


        UiObject2 link = mDevice.wait(Until.findObject(By.text("http://www.google.com/?ref=referrer&foo=bar#tag")), 3000);
        link.click();



        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject url = mDevice.findObject(new UiSelector().text("http://www.google.com/?ref=referrer&foo=bar#tag"));
        url.click();
        url.setText("https://tinyurl.com/2fcpre6");
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UiObject2 accept = mDevice.wait(Until.findObject(By.text("OK")), 3000);
        accept.click();

        UiObject2 unshortenCheck = mDevice.wait(Until.findObject(By.textContains("Unshortens to")), 3000);
        assertNotNull(unshortenCheck);
        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        url = mDevice.findObject(new UiSelector().text("https://tinyurl.com/2fcpre6"));
        url.click();
        url.setText("https://shorturl.at/djZ2B");

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        accept = mDevice.wait(Until.findObject(By.text("OK")), 3000);
        accept.click();

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        unshortenCheck = mDevice.wait(Until.findObject(By.textContains("Unshortens to")), 3000);
        assertNotNull(unshortenCheck);

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject url2 = mDevice.findObject(new UiSelector().text("https://shorturl.at/djZ2B"));
        url2.click();
        url2.setText("https://fb.me");
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UiObject2 accept2 = mDevice.wait(Until.findObject(By.text("OK")), 3000);
        accept2.click();

        UiObject2 unshortenCheck2 = mDevice.wait(Until.findObject(By.textContains("Unshortens to")), 3000);
        assertNull(unshortenCheck2);
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
