package com.skysoul.plugin.rbase.creator.tamplates

object ViewModelTemplate {
    fun getMvvmVm(packageName:String, className:String):String {

        return "package ${packageName}\n" +
                "\n" +
                "import android.app.Application\n" +
                "import com.radiance.androidbase.applibcore.RBaseViewModel\n" +
                "\n" +
                "class ${className}(app:Application) :RBaseViewModel(app){\n" +
                "\n" +
                "    override fun init(){\n" +
                "    \n" +
                "    }\n" +
                "}"
    }
}