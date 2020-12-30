/*
 * Copyright (C) 2019 The AospExtended Project
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

package com.android.settings.deviceinfo.firmwareversion;

import java.io.IOException;
import android.content.Context;
import androidx.annotation.VisibleForTesting;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.annotation.VisibleForTesting;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import com.android.settings.utils.ColtSpecUtils;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;
import com.android.settingslib.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.BasePreferenceController;

import android.widget.TextView;
public class ColtVersionPreferenceController extends BasePreferenceController {

    @VisibleForTesting

    private static final String KEY_COLT_VERSION_PROP = "ro.colt.release.version";

    @VisibleForTesting
    TextView mColtVersionText;
    @VisibleForTesting
    TextView mColtVersionFlavourText;
    @VisibleForTesting
    TextView mDeviceNameText;
    @VisibleForTesting
    TextView mCpuText;
    @VisibleForTesting
    TextView mScreenResText;
    @VisibleForTesting
    TextView mBatteryText;
    @VisibleForTesting
    TextView mRamText;
    @VisibleForTesting
    TextView mMaintainerText;

    private PreferenceFragmentCompat mHost;
    private LayoutPreference mColtVersionLayoutPref;
    private Context mContext;

    public ColtVersionPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        mContext = context;
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    public void setFragment(PreferenceFragmentCompat fragment) {
        mHost = fragment;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mColtVersionLayoutPref = screen.findPreference(getPreferenceKey());
        mColtVersionText = mColtVersionLayoutPref.findViewById(R.id.colt_version);
        mColtVersionFlavourText = mColtVersionLayoutPref.findViewById(R.id.colt_version_flavour);
        mDeviceNameText = mColtVersionLayoutPref.findViewById(R.id.device_name_text);
        mCpuText = mColtVersionLayoutPref.findViewById(R.id.device_cpu_text);
        mScreenResText = mColtVersionLayoutPref.findViewById(R.id.device_screen_res_text);
        mBatteryText = mColtVersionLayoutPref.findViewById(R.id.device_battery_text);
        mRamText = mColtVersionLayoutPref.findViewById(R.id.device_ram_text);
        mMaintainerText = mColtVersionLayoutPref.findViewById(R.id.device_maintainer_text);

        UpdateColtVersionPreference();
    }

    private void UpdateColtVersionPreference() {
        // We split the different specs into different voids to make the code more organized.
        updateColtVersionText();
        updateDeviceNameText();
        updateCpuText();
        updateScreenResText();
        updateBatteryText();
        updateRamText();
        updateMaintainerText();

    }

    public CharSequence updateColtVersionText() {
      return SystemProperties.get(KEY_COLT_VERSION_PROP,
                mContext.getString(R.string.unknown));

    }

    private void updateDeviceNameText() {
        mDeviceNameText.setText(ColtSpecUtils.getDeviceName());
    }

    private void updateBatteryText() {
        mBatteryText.setText(ColtSpecUtils.getBatteryCapacity(mContext) + " mAh");
    }

    private void updateCpuText() {
        mCpuText.setText(ColtSpecUtils.getProcessorModel());
    }

    private void updateScreenResText() {
        mScreenResText.setText(ColtSpecUtils.getScreenRes(mContext));
    }

    private void updateRamText() {
        mRamText.setText(String.valueOf(ColtSpecUtils.getTotalRAM())+ " GB");
    }
    private void updateMaintainerText() {
        mMaintainerText.setText(String.valueOf(ColtSpecUtils.getMaintainerName()));
    }

}
