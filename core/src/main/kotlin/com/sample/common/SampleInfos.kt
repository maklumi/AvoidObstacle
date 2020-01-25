@file:JvmName("SampleInfos")

package com.sample.common

import com.sample.InputPollingSample

object SampleInfos {

    private val ALL = arrayListOf(
            InputPollingSample.SAMPLE_INFO
    )

    @JvmStatic
    val sampleNames: List<String>
        get() = ALL.map { it.name }

    fun find(name: String): SampleInfo<*>? {
        return ALL.find { it.name == name }
    }

}