@file:JvmName("SampleInfos")

package com.sample.common

import com.sample.InputPollingSample
import com.sample.OrthographicCameraSample
import com.sample.SpriteBatchSample
import com.sample.ViewportSample

object SampleInfos {

    private val ALL = arrayListOf(
            InputPollingSample.SAMPLE_INFO,
            OrthographicCameraSample.SAMPLE_INFO,
            ViewportSample.SAMPLE_INFO,
            SpriteBatchSample.SAMPLE_INFO
    )

    @JvmStatic
    val sampleNames: List<String>
        get() = ALL.map { it.name }

    fun find(name: String): SampleInfo<*>? {
        return ALL.find { it.name == name }
    }

}