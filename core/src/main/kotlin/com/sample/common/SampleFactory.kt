package com.sample.common

import com.badlogic.gdx.utils.reflect.ClassReflection

object SampleFactory {

    @JvmStatic
    fun newSample(name: String): SampleBase? {
        val info = SampleInfos.find(name)
        return ClassReflection.newInstance(info?.clazz)
    }

}