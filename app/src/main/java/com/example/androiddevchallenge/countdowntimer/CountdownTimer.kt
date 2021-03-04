/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.countdowntimer

import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow

class CountdownTimer {
    val remainingSeconds: MutableStateFlow<Int> = MutableStateFlow(10)
    private var _seconds: Int = 100

    private var countDownTimer: CountDownTimer? = null

    fun setTimer(seconds: Int) {
        _seconds = seconds
    }

    fun reset() {
        remainingSeconds.value = _seconds
    }

    val running = MutableStateFlow(false)

    fun startStop() {
        if (countDownTimer == null) {
            countDownTimer =
                object : CountDownTimer((remainingSeconds.value * 1000).toLong(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        remainingSeconds.value = (millisUntilFinished / 1000).toInt()
                    }

                    override fun onFinish() {
                        running.value = false
                    }
                }.start()
            running.value = true
        } else {
            countDownTimer?.cancel()
            countDownTimer = null
            running.value = false
        }
    }
}