package com.skysoul.plugin.rbase.creator.tamplates

object LayoutTemplate {
    fun getMvvmContent(viewModelAbPath:String):String
    {
      return  "<layout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    xmlns:app=\"http://schemas.android.com/apk/res-auto\">\n" +
                "\n" +
                "    <data>\n" +
                "\n" +
                "        <variable\n" +
                "            name=\"viewModel\"\n" +
                "            type=\"$viewModelAbPath\" />\n" +
                "        <variable\n" +
                "            name=\"clickEvent\"\n" +
                "            type=\"android.view.View.OnClickListener\" />\n" +
                "    </data>\n" +
                "\n" +
                "\n" +
                "    <androidx.constraintlayout.widget.ConstraintLayout\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"match_parent\"\n" +
                "        >\n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "    </androidx.constraintlayout.widget.ConstraintLayout>\n" +
                "\n" +
                "</layout>"
    }

    fun getMvvmItem(itemAbPath:String):String
    {

        return "<layout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    xmlns:app=\"http://schemas.android.com/apk/res-auto\">\n" +
                "\n" +
                "    <data>\n" +
                "\n" +
                "        <variable\n" +
                "            name=\"item\"\n" +
                "            type=\"$itemAbPath\" />\n" +
                "        <variable name=\"clickEvent\" type=\"com.radiance.androidbase.applibcore.adapter.simpleadapter2.SimpleClickListener4Adapter\" />\n" +
                "\n" +
                "    </data>\n" +
                "\n" +
                "\n" +
                "    <androidx.constraintlayout.widget.ConstraintLayout\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        >\n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "    </androidx.constraintlayout.widget.ConstraintLayout>\n" +
                "\n" +
                "</layout>"
    }
}