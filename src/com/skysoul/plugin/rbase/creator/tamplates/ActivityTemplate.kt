package com.skysoul.plugin.rbase.creator.tamplates

import com.skysoul.plugin.rbase.options.RBaseOptions

object ActivityTemplate {
    fun mvvm_kt(packageName: String, appId: String, dataBindingName: String, className: String, modelPackage: String, modelName: String, layoutName: String): String {

        var import = "com.radiance.androidbase.applibcore.activity.mvvm.RBaseMvvmActivity"
        var actName = "RBaseMvvmActivity"

        RBaseOptions.instanse.activity?.run {
            import = "${this.classPackage}.${this.className}"
            actName = this.className
        }


        return "package ${packageName}\n" +
                "\n"+
                "import $import\n" +
                "import android.os.Bundle\n" +
                "import android.view.View\n" +
                "\n" +
                "import $appId.R\n" +
                "import $appId.databinding.$dataBindingName\n" +
                (if (packageName == modelPackage) ""
                else "import ${modelPackage}.$modelName\n") +
                "\n" +

                "class $className :$actName<$dataBindingName,$modelName>(){\n" +
                "\n" +
                "    override fun getViewLayoutId():Int{\n" +
                "        return R.layout.$layoutName\n" +
                "    }\n" +
                "\n" +
                "    override fun init(savedInstanceState:Bundle?){\n" +
                "        mBinding.viewModel = mViewModel\n" +
                "        mBinding.clickEvent = View.OnClickListener {\n" +
                "            onClick(it)\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    private fun onClick(v:View){\n" +
                "        when(v.id){\n" +
                "        \n" +
                "        }\n" +
                "    }\n" +
                "}"
    }
}