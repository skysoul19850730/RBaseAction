package com.skysoul.plugin.rbase.creator

import com.intellij.openapi.actionSystem.AnActionEvent
import com.skysoul.plugin.rbase.creator.activity.ActivityCreator
import com.skysoul.plugin.rbase.creator.fragment.FragmentCreator
import com.skysoul.plugin.rbase.creator.layout.LayoutCreator
import com.skysoul.plugin.rbase.creator.viewmodel.ViewModelCreator
import com.skysoul.plugin.rbase.ui.SelectDialog

class CreatorDispatcher {

    fun dispatcher(action: AnActionEvent, preName: String?, name: String, select: Int): String? {
        val creatorInfo = CreatorInfo()
        creatorInfo.init(action, preName, name, select)?.run {
            return this
        }
        val viewModelCreator = ViewModelCreator()
        val layoutCreator = LayoutCreator()

        val mainCreator: ICreator = if (select == SelectDialog.SELECT_ACTIVITY) ActivityCreator() else FragmentCreator()

        val listCreator2: ArrayList<ICreator> = arrayListOf(viewModelCreator, layoutCreator, mainCreator)

        listCreator2.forEach {
            var result = it.init(action, creatorInfo)
            if (result != null) {
                return result
            }
        }

        listCreator2.forEach {
            it.doCreator()
        }
        return null
    }

}