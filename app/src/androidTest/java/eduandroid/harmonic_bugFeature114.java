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
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Basic example for unbundled UiAutomator.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class harmonic_bugFeature114 {

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
    public void testRealHarmonicFeatures114() throws IOException, UiObjectNotFoundException, RemoteException {

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

        List<UiObject2> allStories = mDevice.wait(Until.findObjects(By.res(BASIC_SAMPLE_PACKAGE,"story_comments")),2000);
        assertNotNull(allStories);
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (UiObject2 cur : allStories)
        {
            int num = Integer.parseInt(cur.getText());
            nums.add(num);
        }
        Collections.sort(nums);

        for(int i = 0; i < allStories.size(); i++)
        {
            if(Integer.parseInt(allStories.get(i).getText()) == nums.get(nums.size() - 1))
            {
                allStories.get(i).click();
                break;
            }
        }
        boolean enterLink = false;
        boolean linkClicked = false;
        while (!linkClicked) {
            List<UiObject2> allComments = mDevice.wait(Until.findObjects(By.res(BASIC_SAMPLE_PACKAGE, "comment_body")), 2000);
            if(allComments == null)
            {
                mDevice.swipe(540, 1200, 540, 900, 20);
                continue;
            }

            for (UiObject2 curComment : allComments) {
                String text = curComment.getText();

                if (text == null) continue;

                for (String curWord : text.split("\\s+")) {
                    if (curWord.startsWith("http") && curWord.length() > 60) {
                        Rect bounds = curComment.getVisibleBounds();
                        mDevice.swipe(500, bounds.top, 500, 100, 200);

                        curComment = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "comment_body")), 2000);

                        // Get updated bounds
                        bounds = curComment.getVisibleBounds();
                        int yStart = bounds.top + 40;
                        int yEnd = bounds.bottom;

                        int stepY = 46;
                        for (int y = yStart; y <= yEnd; y += stepY) {
                            mDevice.swipe(540, y, 540, y, 100); // long press
                            try {
                                TimeUnit.MILLISECONDS.sleep(750);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            UiObject2 select = mDevice.wait(Until.findObject(By.text("Copy text")), 2000);
                            assertNotNull(select);

                            mDevice.click(500, 1600);
                            try {
                                TimeUnit.MILLISECONDS.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        for (int y = yStart; y <= yEnd; y += stepY) {
                            mDevice.click(540, y); // click
                            try {
                                TimeUnit.MILLISECONDS.sleep(750);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            String select = mDevice.getCurrentPackageName();
                            if(!select.equals("com.simon.harmonichackernews"))
                            {
                                enterLink = true;
                            }
                            try {
                                TimeUnit.MILLISECONDS.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        linkClicked = true;
                        break;
                    }
                    if (linkClicked) break;
                }
                if (linkClicked) break;
            }

            if (!linkClicked) mDevice.swipe(500, 1600, 500, 1200, 20); // Scroll for more comments
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

