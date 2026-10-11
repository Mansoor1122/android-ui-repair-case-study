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
import static org.junit.Assert.assertThat;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1568 {

    private static final String PACKAGE_NAME
            = "io.lerk.lrkFM";

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
    public void testScript1568() throws UiObjectNotFoundException {
        // Setup GUI actions
        // Click unlock
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        // Click "allow"
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        // Click right
        uiDevice.findObject(new UiSelector().clickable(true)).click();
        // Bug-specific GUI actions
        UiObject2 allow = uiDevice.wait(Until.findObject(By.text("ALLOW")), 2000);
        allow.click();

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Click on 3 lines top left
        uiDevice.click(72, 140);


        //click on home
        UiObject2 home = uiDevice.wait(Until.findObject(By.text("Home")), 2000);
        home.click();
        // Click up
        uiDevice.findObject(new UiSelector().clickable(true).instance(2)).click();
        // Click checkbox 1
        uiDevice.findObject(new UiSelector().className("android.widget.CheckBox")).click();
        // Click checkbox 2
        uiDevice.findObject(new UiSelector().className("android.widget.CheckBox").instance(1)).click();
        // Click checkbox 1
        uiDevice.findObject(new UiSelector().className("android.widget.CheckBox")).click();

        String pack = uiDevice.getCurrentPackageName();
        assertEquals("io.lerk.lrkFM", pack);
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent    public static void show(final MainActivity callingActivity, Settings settings, final EntriesCardAdapter adapter, Entry oldEntry) {
        //        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //        boolean isNewEntry = oldEntry == null;
        //
        //        ViewGroup container = callingActivity.findViewById(R.id.main_content);
        //        View inputView = callingActivity.getLayoutInflater().inflate(R.layout.dialog_manual_entry, container, false);
        //
        //        if (settings.getBlockAccessibility())
        //            inputView.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS);
        //
        //        final Spinner typeInput = inputView.findViewById(R.id.manual_type);
        //        final EditText issuerInput = inputView.findViewById(R.id.manual_issuer);
        //        final EditText labelInput = inputView.findViewById(R.id.manual_label);
        //        final EditText secretInput = inputView.findViewById(R.id.manual_secret);
        //        final TextView secretView = inputView.findViewById(R.id.manual_secret_view);
        //        final EditText counterInput = inputView.findViewById(R.id.manual_counter);
        //        final EditText periodInput = inputView.findViewById(R.id.manual_period);
        //        final EditText digitsInput = inputView.findViewById(R.id.manual_digits);
        //        final LinearLayout counterLayout = inputView.findViewById(R.id.manual_layout_counter);
        //        final LinearLayout periodLayout = inputView.findViewById(R.id.manual_layout_period);
        //        final Spinner algorithmInput = inputView.findViewById(R.id.manual_algorithm);
        //        final Button tagsInput = inputView.findViewById(R.id.manual_tags);
        //
        //        final ArrayAdapter<TokenCalculator.HashAlgorithm> algorithmAdapter = new ArrayAdapter<>(callingActivity, android.R.layout.simple_expandable_list_item_1, TokenCalculator.HashAlgorithm.values());
        //        final ArrayAdapter<Entry.OTPType> typeAdapter = new ArrayAdapter<>(callingActivity, android.R.layout.simple_expandable_list_item_1, Entry.OTPType.values());
        //
        //        typeInput.setAdapter(typeAdapter);
        //        algorithmInput.setAdapter(algorithmAdapter);
        //
        //        periodInput.setText(String.format(Locale.US, "%d", TokenCalculator.TOTP_DEFAULT_PERIOD));
        //        digitsInput.setText(String.format(Locale.US, "%d", TokenCalculator.TOTP_DEFAULT_DIGITS));
        //        counterInput.setText(String.format(Locale.US, "%d", TokenCalculator.HOTP_INITIAL_COUNTER));
        //
        //        typeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //            @Override
        //            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //                Entry.OTPType type = (Entry.OTPType) adapterView.getSelectedItem();
        //
        //                if (type == Entry.OTPType.STEAM) {
        //                    counterLayout.setVisibility(View.GONE);
        //                    periodLayout.setVisibility(View.VISIBLE);
        //
        //                    digitsInput.setText(String.format(Locale.US, "%d", TokenCalculator.STEAM_DEFAULT_DIGITS));
        //                    periodInput.setText(String.format(Locale.US, "%d", TokenCalculator.TOTP_DEFAULT_PERIOD));
        //                    algorithmInput.setSelection(algorithmAdapter.getPosition(TokenCalculator.HashAlgorithm.SHA1));
        //
        //                    digitsInput.setEnabled(false);
        //                    periodInput.setEnabled(false);
        //                    algorithmInput.setEnabled(false);
        //                } else if (type == Entry.OTPType.TOTP) {
        //                    counterLayout.setVisibility(View.GONE);
        //                    periodLayout.setVisibility(View.VISIBLE);
        //
        //                    digitsInput.setText(String.format(Locale.US, "%d", TokenCalculator.TOTP_DEFAULT_DIGITS));
        //                    digitsInput.setEnabled(isNewEntry);
        //                    periodInput.setEnabled(isNewEntry);
        //                    algorithmInput.setEnabled(isNewEntry);
        //                } else if (type == Entry.OTPType.HOTP) {
        //                    counterLayout.setVisibility(View.VISIBLE);
        //                    periodLayout.setVisibility(View.GONE);
        //
        //                    digitsInput.setText(String.format(Locale.US, "%d", TokenCalculator.TOTP_DEFAULT_DIGITS));
        //                    digitsInput.setEnabled(isNewEntry);
        //                    periodInput.setEnabled(isNewEntry);
        //                    algorithmInput.setEnabled(isNewEntry);
        //                }
        //            }
        //
        //            @Override
        //            public void onNothingSelected(AdapterView<?> adapterView) {
        //
        //            }
        //        });
        //
        //        List<String> allTags = adapter.getTags();
        //        HashMap<String, Boolean> tagsHashMap = new HashMap<>();
        //        for(String tag: allTags) {
        //            tagsHashMap.put(tag, false);
        //        }
        //        final TagsAdapter tagsAdapter = new TagsAdapter(callingActivity, tagsHashMap);
        //
        //        final Callable tagsCallable = new Callable() {
        //            @Override
        //            public Object call() throws Exception {
        //                List<String> selectedTags = tagsAdapter.getActiveTags();
        //                StringBuilder stringBuilder = new StringBuilder();
        //                for(int j = 0; j < selectedTags.size(); j++) {
        //                    stringBuilder.append(selectedTags.get(j));
        //                    if(j < selectedTags.size() - 1) {
        //                        stringBuilder.append(", ");
        //                    }
        //                }
        //                tagsInput.setText(stringBuilder.toString());
        //                return null;
        //            }
        //        };
        //
        //        tagsInput.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                TagsDialog.show(callingActivity, tagsAdapter, tagsCallable, tagsCallable);
        //            }
        //        });
        //
        //        final Button expandButton = inputView.findViewById(R.id.dialog_expand_button);
        //
        //        // Dirty fix for the compound drawable to avoid crashes on KitKat
        //        expandButton.setCompoundDrawablesWithIntrinsicBounds(null, null, callingActivity.getResources().getDrawable(R.drawable.ic_arrow_down_accent), null);
        //
        //        final ExpandableLinearLayout expandLayout = inputView.findViewById(R.id.dialog_expand_layout);
        //
        //        expandButton.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                expandLayout.toggle();
        //            }
        //        });
        //
        //        expandLayout.setListener(new ExpandableLayoutListenerAdapter() {
        //            @Override
        //            public void onOpened() {
        //                super.onOpened();
        //                expandButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_accent, 0);
        //            }
        //
        //            @Override
        //            public void onClosed() {
        //                super.onClosed();
        //                expandButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_accent, 0);
        //            }
        //        });
        //
        //        AlertDialog.Builder builder = new AlertDialog.Builder(callingActivity);
        //        builder.setTitle(R.string.dialog_title_manual_entry)
        //                .setView(inputView)
        //                .setPositiveButton(R.string.button_save, new DialogInterface.OnClickListener() {
        //                    @Override
        //                    public void onClick(DialogInterface dialogInterface, int i) {
        //                        Entry.OTPType type = (Entry.OTPType) typeInput.getSelectedItem();
        //                        TokenCalculator.HashAlgorithm algorithm = (TokenCalculator.HashAlgorithm) algorithmInput.getSelectedItem();
        //
        //                        String issuer = issuerInput.getText().toString();
        //                        String label = labelInput.getText().toString();
        //                        //Replace spaces with empty characters
        //                        String secret = secretInput.getText().toString().replaceAll("\\s+","");
        //                        int digits = Integer.parseInt(digitsInput.getText().toString());
        //
        //                       if (type == Entry.OTPType.TOTP || type == Entry.OTPType.STEAM) {
        //                            int period = Integer.parseInt(periodInput.getText().toString());
        //
        //                            if (oldEntry == null) {
        //                                Entry e = new Entry(type, secret, period, digits, issuer, label, algorithm, tagsAdapter.getActiveTags());
        //                                e.updateOTP();
        //                                e.setLastUsed(System.currentTimeMillis());
        //
        //                                adapter.addEntry(e);
        //                            } else {
        //                                oldEntry.setIssuer(issuer);
        //                                oldEntry.setLabel(label);
        //                                oldEntry.setTags(tagsAdapter.getActiveTags());
        //
        //                                adapter.saveAndRefresh(settings.getAutoBackupEncryptedFullEnabled());
        //                            }
        //
        //                            callingActivity.refreshTags();
        //                        } else if (type == Entry.OTPType.HOTP) {
        //                            long counter = Long.parseLong(counterInput.getText().toString());
        //
        //                            if (oldEntry == null) {
        //                                Entry e = new Entry(type, secret, counter, digits, issuer, label, algorithm, tagsAdapter.getActiveTags());
        //                                e.updateOTP();
        //                                e.setLastUsed(System.currentTimeMillis());
        //
        //                                adapter.addEntry(e);
        //                            } else {
        //                                oldEntry.setIssuer(issuer);
        //                                oldEntry.setLabel(label);
        //                                oldEntry.setTags(tagsAdapter.getActiveTags());
        //
        //                                adapter.saveAndRefresh(settings.getAutoBackupEncryptedFullEnabled());
        //                            }
        //                        }
        //                    }
        //                })
        //                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
        //                    @Override
        //                    public void onClick(DialogInterface dialogInterface, int i) {}
        //                });
        //
        //        AlertDialog dialog = builder.create();
        //        dialog.show();
        //
        //        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        //
        //        positiveButton.setEnabled(false);
        //
        //        TextWatcher watcher = new TextWatcher() {
        //            @Override
        //            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //            }
        //
        //            @Override
        //            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //            }
        //
        //            @Override
        //            public void afterTextChanged(Editable editable) {
        //                if (TextUtils.isEmpty(labelInput.getText()) || TextUtils.isEmpty(secretInput.getText())
        //                        || TextUtils.isEmpty(digitsInput.getText()) || Integer.parseInt(digitsInput.getText().toString()) == 0) {
        //                    positiveButton.setEnabled(false);
        //                } else {
        //                    Entry.OTPType type = (Entry.OTPType) typeInput.getSelectedItem();
        //                    if (type == Entry.OTPType.HOTP) {
        //                        if (TextUtils.isEmpty(counterInput.getText())) {
        //                            positiveButton.setEnabled(false);
        //                        } else {
        //                            positiveButton.setEnabled(true);
        //                        }
        //                    } else if (type == Entry.OTPType.TOTP || type == Entry.OTPType.STEAM) {
        //                        if (TextUtils.isEmpty(periodInput.getText()) || Integer.parseInt(periodInput.getText().toString()) == 0) {
        //                            positiveButton.setEnabled(false);
        //                        } else {
        //                            positiveButton.setEnabled(true);
        //                        }
        //                    } else {
        //                        positiveButton.setEnabled(true);
        //                    }
        //                }
        //            }
        //        };
        //
        //        labelInput.addTextChangedListener(watcher);
        //        secretInput.addTextChangedListener(watcher);
        //        periodInput.addTextChangedListener(watcher);
        //        digitsInput.addTextChangedListener(watcher);
        //        counterInput.addTextChangedListener(watcher);
        //
        //        if (!isNewEntry) {
        //            Entry.OTPType oldType = oldEntry.getType();
        //
        //            typeInput.setSelection(typeAdapter.getPosition(oldType));
        //            issuerInput.setText(oldEntry.getIssuer());
        //            labelInput.setText(oldEntry.getLabel());
        //            secretView.setText(oldEntry.getSecretEncoded());
        //            digitsInput.setText(Integer.toString(oldEntry.getDigits()));
        //            algorithmInput.setSelection(algorithmAdapter.getPosition(oldEntry.getAlgorithm()));
        //
        //            if (oldType == Entry.OTPType.TOTP || oldType == Entry.OTPType.STEAM) {
        //                periodInput.setText(Integer.toString(oldEntry.getPeriod()));
        //            } else if (oldType == Entry.OTPType.HOTP) {
        //                counterInput.setText(Long.toString(oldEntry.getCounter()));
        //            }
        //
        //            for(String tag: oldEntry.getTags()) {
        //                tagsAdapter.setTagState(tag, true);
        //            }
        //            try {
        //                tagsCallable.call();
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //
        //            secretInput.setVisibility(View.GONE);
        //            secretView.setVisibility(View.VISIBLE);
        //
        //            // Little hack: match the color and background of the TextView to that of a disabled EditText
        //            secretInput.setEnabled(false);
        //            secretView.setBackground(secretInput.getBackground());
        //            secretView.setTextColor(secretInput.getTextColors().getColorForState(secretInput.getDrawableState(), R.color.colorPrimary));
        //
        //            typeInput.setEnabled(false);
        //            digitsInput.setEnabled(false);
        //            algorithmInput.setEnabled(false);
        //            periodInput.setEnabled(false);
        //            counterInput.setEnabled(false);
        //        }
        //    }
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
