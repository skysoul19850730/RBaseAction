package com.skysoul.plugin.rbase.creator.viewmodel

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.vfs.VirtualFile
import com.skysoul.plugin.rbase.constants.FConfig
import com.skysoul.plugin.rbase.creator.CreatorInfo
import com.skysoul.plugin.rbase.creator.ICreator
import com.skysoul.plugin.rbase.creator.tamplates.ViewModelTemplate
import com.skysoul.plugin.rbase.utils.FileUtil
import java.io.File

class ViewModelCreator : ICreator {
    private var mName: String? = null
    private var mFile: VirtualFile? = null
    private lateinit var action: AnActionEvent
    private lateinit var creatorInfo: CreatorInfo


    private fun getContent(packageName: String, className: String) = ViewModelTemplate.getMvvmVm(packageName, className)

    override fun doCreator() {
        mFile = creatorInfo.file.createChildData(null, "$mName.kt")
        FileUtil.writeFile(mFile!!.path, getContent(creatorInfo.packageName, mName!!))
        OpenFileDescriptor(action.project!!, mFile!!).navigate(true)
    }

    override fun init(action: AnActionEvent, creatorInfo: CreatorInfo): String? {
        this.action = action
        this.creatorInfo = creatorInfo
        mName = initFileName()
        if (File(creatorInfo.folder4Opt, "$mName.kt").exists()) {
            return "ViewModel Cannot be Created ,the name $mName exists"
        }
        creatorInfo.modelName = mName!!
        return null
    }

    private fun initFileName(): String {
        val preName: String? = creatorInfo.preNameInput
        val name: String = creatorInfo.nameInput

        var finalName = name
        if (!name.contains(FConfig.SUFFER_VIEWMODEL, true)) {
            finalName += FConfig.SUFFER_VIEWMODEL
        }
        return (preName ?: "") + finalName
    }

    override fun doCancel() {
        if (mFile != null && mFile!!.exists()) {
            mFile!!.delete(null)
        }
    }
}