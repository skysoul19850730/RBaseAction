package com.skysoul.plugin.rbase.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.skysoul.plugin.rbase.creator.CreatorDispatcher
import com.skysoul.plugin.rbase.ui.SelectDialog

class RBaseAction : AnAction() {

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = (e.project != null)
    }

    override fun actionPerformed(e: AnActionEvent) {
        showSelectDialog(e)
    }

    private fun showSelectDialog(e: AnActionEvent) {
        SelectDialog.show { dialog, preName, name, select ->
           val result = doCreate(e,preName,name,select)
            if(result == null){
                dialog.dispose()
            }else{
                dialog.showError(result)
            }
        }
    }

    private fun doCreate(e: AnActionEvent, perName: String?, name: String, select: Int): String? {
        return CreatorDispatcher().dispatcher(e, perName, name, select)
    }

}