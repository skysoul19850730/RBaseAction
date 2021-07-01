package com.skysoul.plugin.rbase.creator.tamplates

object FragmentTemplate {
    fun mvvm(packageName: String, appId: String, dataBindingName: String, className: String, modelPackage: String, modelName: String, layoutName: String): String {
        return "package ${packageName}\n" +
                "\n" +
                "import android.os.Bundle\n" +
                "import android.view.View\n" +
                "import com.radiance.androidbase.applibcore.fragments.mvvm.RBaseMvvmFragment\n" +
                "\n" +
                "import ${appId}.R\n" +
                "import ${appId}.databinding.$dataBindingName\n" +
                (if (packageName == modelPackage) ""
                else "import ${modelPackage}.$modelName\n") +
                "\n" +

                "class ${className} :RBaseMvvmFragment<$dataBindingName,$modelName>(){\n" +
                "\n" +
                "    override fun getViewLayoutId():Int{\n" +
                "        return R.layout.$layoutName\n" +
                "    }\n" +
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