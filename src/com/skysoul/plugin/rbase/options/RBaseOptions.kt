package com.skysoul.plugin.rbase.options
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "RBaseOptions", storages = [Storage("options.xml")])
class RBaseOptions : PersistentStateComponent<RBaseOptions> {

    var activity:CustomClass?=null
    var fragment:CustomClass?=null
    var viewModel:CustomClass?=null


    companion object INSTANCE{
        val instanse = ServiceManager.getService(RBaseOptions::class.java)
    }

    override fun getState(): RBaseOptions? {
        return this
    }

    override fun loadState(p0: RBaseOptions) {
        XmlSerializerUtil.copyBean(p0,this)
    }
}