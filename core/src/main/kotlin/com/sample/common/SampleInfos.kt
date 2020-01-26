@file:JvmName("SampleInfos")

package com.sample.common

import com.sample.*

object SampleInfos {

    private val ALL = arrayListOf(
            InputPollingSample.SAMPLE_INFO,
            OrthographicCameraSample.SAMPLE_INFO,
            ViewportSample.SAMPLE_INFO,
            SpriteBatchSample.SAMPLE_INFO,
            ShapeRendererSample.SAMPLE_INFO
    )

    @JvmStatic
    val sampleNames: List<String>
        get() = ALL.map { it.name }

    fun find(name: String): SampleInfo<out SampleBase>? {
        return ALL.find { it.name == name }
    }

}