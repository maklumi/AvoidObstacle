package com.sample.common

class SampleInfo<T : SampleBase>(val clazz: Class<T>) {

    val name: String = clazz.simpleName

}


