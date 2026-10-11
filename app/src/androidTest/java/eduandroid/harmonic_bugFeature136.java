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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Basic example for unbundled UiAutomator.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class harmonic_bugFeature136 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.simon.harmonichackernews";

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
    public void testRealHarmonicFeatures136() throws IOException, UiObjectNotFoundException, RemoteException {


        mDevice.swipe(500, 1600, 500, 800, 20);

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //click OK
        UiObject2 start = mDevice.wait(Until.findObject(By.text("Get started")),2000);
        if(start != null)
        {
            start.click();
        }

        start = mDevice.wait(Until.findObject(By.text("Done")),2000);
        if(start != null)
        {
            start.click();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<UiObject2> story = mDevice.wait(Until.findObjects(By.res(BASIC_SAMPLE_PACKAGE,"story_comments")),2000);

        assert(story.size() > 1);
        story.get(1).click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 comment = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"comments_header_button_comment")),2000);
        while(comment == null)
        {
            mDevice.swipe(540, 1200, 540, 900, 20);
            comment = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"comments_header_button_comment")),2000);
        }

        comment.click();

        UiObject user = mDevice.findObject(new UiSelector().text("Username"));
        user.click();
        user = mDevice.findObject(new UiSelector().text("Username"));
        user.setText("bugtestering");

        mDevice.pressBack();

        UiObject pass = mDevice.findObject(new UiSelector().text("Password"));
        pass.click();
        pass = mDevice.findObject(new UiSelector().text("Password"));
        pass.setText("unomomento");

        mDevice.pressBack();
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        UiObject2 login = mDevice.wait(Until.findObject(By.text("Save")),2000);
        login.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        comment = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE,"comments_header_button_comment")),2000);
        comment.click();
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UiObject enterComment = mDevice.findObject(new UiSelector().text("Type here..."));
        enterComment.click();
        enterComment.setText("A");

        mDevice.pressBack();
        mDevice.pressBack();
        UiObject2 discard = mDevice.wait(Until.findObject(By.text("Discard")),2000);
        UiObject2 cancel = mDevice.wait(Until.findObject(By.text("Cancel")),2000);

        Rect discardBounds = discard.getVisibleBounds();
        Rect cancelBounds = cancel.getVisibleBounds();

        // Check if discard is to the right of cancel
        assert(discardBounds.left > cancelBounds.right && !discardBounds.intersect(cancelBounds));
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

