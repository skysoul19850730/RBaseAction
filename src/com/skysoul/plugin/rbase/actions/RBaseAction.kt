package com.skysoul.plugin.rbase.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.PsiManager
import com.skysoul.plugin.rbase.creator.CreatorDispatcher
import com.skysoul.plugin.rbase.ui.SelectDialog

class RBaseAction : AnAction() {

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = (e.project != null && checkFile(e))
    }
    private fun checkFile(e: AnActionEvent):Boolean{
        val file = e.getData(PlatformDataKeys.VIRTUAL_FILE)!!
        var psiFile = PsiManager.getInstance(e.project!!).findFile(file)
        if(psiFile == null){
            val psiDir = PsiManager.getInstance(e.project!!).findDirectory(file) ?: return false
            return JavaDirectoryService.getInstance().getPackage(psiDir)?.name != null
        }else{
//            if(psiFile !is PsiJavaFile)return false
            val psiDir = psiFile.parent?:return false
            //psiFile即使放到src包名外（虽然不能创建，但是可以复制一个现成的java文件到外面),通过psiFile.packageName还是可以获取到值
            //但使用JavaDirectoryService.getInstance().getPackage(psiDir)来获取其所在文件夹来判断packageName是可以的
            return JavaDirectoryService.getInstance().getPackage(psiDir)?.name != null
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        showSelectDialog(e)
    }

    private fun showSelectDialog(e: AnActionEvent) {
        SelectDialog.show(object :SelectDialog.SelectDialogListener{
            override fun onClickOk(dialog: SelectDialog?, preName: String?, name: String?, select: Int) {
                val result = doCreate(e,preName,name!!,select)
                if(result == null){
                    dialog!!.dispose()
                }else{
                    dialog!!.showError(result)
                }
            }

            override fun getActionEvent(): AnActionEvent {
                return e
            }

        })
    }

    private fun doCreate(e: AnActionEvent, perName: String?, name: String, select: Int): String? {
        return CreatorDispatcher().dispatcher(e, perName, name, select)
    }

}