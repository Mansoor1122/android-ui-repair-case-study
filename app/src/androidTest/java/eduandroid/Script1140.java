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
import android.os.RemoteException;

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
import static java.lang.Math.random;
/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1140 {

    private static final String BASIC_SAMPLE_PACKAGE = "com.automattic.simplenote";
    private static final String AndroidOS = "com.android.packageinstaller";
    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private static final String EMAIL_TO_BE_TYPED = "bugreportdataset@gmail.com";
    private static final String PASSWORD_TO_BE_TYPED = "abcd1234";
    private static final String FILENAME_TO_BE_TYPED = "test" + Integer.toString((int)(java.lang.Math.random() * 10000));
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
        UiObject2 Login = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "button_login")), 2000);
        Login.click();

        Login = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "button_email")), 2000);
        Login.click();



        UiObject2 Email = mDevice.wait(Until.findObject(By.text("Email")), 2000);
        Email.setText(EMAIL_TO_BE_TYPED);

        Email = mDevice.wait(Until.findObject(By.text("Password")), 2000);
        Email.setText(PASSWORD_TO_BE_TYPED);

        Email = mDevice.wait(Until.findObject(By.text("LOG IN")), 2000);
        Email.click();

        // Click 'new note'
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UiObject2 newNote = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"fab_button")), 2000);
        newNote.click();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UiObject2 Note = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "note_content")), 2000);
        Note.setText(FILENAME_TO_BE_TYPED);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            mDevice.pressRecentApps();
            TimeUnit.SECONDS.sleep(2);
            mDevice.click(500, 700);
        }
        catch(RemoteException e){
            System.out.println("Error: could not reach recent apps. Check connection to the device");
            e.printStackTrace();
        } catch (InterruptedException e) {
        e.printStackTrace();
    }

        Note = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "note_content")), 2000);
        Note.setText(FILENAME_TO_BE_TYPED + "1");

        try {
            mDevice.pressRecentApps();
        }
        catch(RemoteException e){
            System.out.println("Error: could not reach recent apps. Check connection to the device");
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        UiObject2 otherApp = mDevice.wait(Until.findObject(By.text( "Chrome")), 2000);
        otherApp.click();

        try {
            mDevice.pressRecentApps();
            TimeUnit.SECONDS.sleep(2);
            mDevice.pressRecentApps();
        }
        catch(RemoteException e){
            System.out.println("Error: could not reach recent apps. Check connection to the device");
            e.printStackTrace();
        } catch (InterruptedException e) {
        e.printStackTrace();
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
