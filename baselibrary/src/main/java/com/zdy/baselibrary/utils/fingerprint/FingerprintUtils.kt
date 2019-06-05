package com.zdy.baselibrary.utils.fingerprint

/*
 * Copyright 2017 Idealnaya rabota LLC
 * Licensed under Multy.io license.
 * See LICENSE for details
 */

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

object FingerprintUtils {

    enum class mSensorState {
        NOT_SUPPORTED,
        NOT_BLOCKED,
        NO_FINGERPRINTS,
        READY
    }

    fun checkFingerprintCompatibility(context: Context): Boolean {
        return FingerprintManagerCompat.from(context).isHardwareDetected
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkSensorState(context: Context): mSensorState {
        if (checkFingerprintCompatibility(context)) {

            val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (!keyguardManager.isKeyguardSecure) {
                return FingerprintUtils.mSensorState.NOT_BLOCKED
            }

            return if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
                FingerprintUtils.mSensorState.NO_FINGERPRINTS
            } else FingerprintUtils.mSensorState.READY

        } else {
            return FingerprintUtils.mSensorState.NOT_SUPPORTED
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun isSensorStateAt(state: mSensorState, context: Context): Boolean {
        return checkSensorState(context) == state
    }
}
