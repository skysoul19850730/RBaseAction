package com.skysoul.plugin.rbase.creator.tamplates

import com.skysoul.plugin.rbase.options.RBaseOptions

object ViewModelTemplate {
    fun getMvvmVm(packageName:String, className:String):String {
        var import = "com.radiance.androidbase.applibcore.RBaseViewModel"
        var actName = "RBaseViewModel"

        RBaseOptions.instanse.viewModel?.run {
            import = "${this.classPackage}.${this.className}"
            actName = this.className
        }
        return "package ${packageName}\n" +
                "\n" +
                "import android.app.Application\n" +
                "import $import\n" +
                "\n" +
                "class ${className}(app:Application) :$actName(app){\n" +
                "\n" +
                "    override fun init(){\n" +
                "    \n" +
                "    }\n" +
                "}"
    }
}