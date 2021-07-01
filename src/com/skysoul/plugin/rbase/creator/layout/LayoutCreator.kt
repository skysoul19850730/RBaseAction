package com.skysoul.plugin.rbase.creator.layout

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.skysoul.plugin.rbase.constants.FConfig
import com.skysoul.plugin.rbase.creator.CreatorInfo
import com.skysoul.plugin.rbase.creator.ICreator
import com.skysoul.plugin.rbase.creator.tamplates.LayoutTemplate
import com.skysoul.plugin.rbase.creator.tamplates.ViewModelTemplate
import com.skysoul.plugin.rbase.ui.SelectDialog
import com.skysoul.plugin.rbase.utils.FileUtil
import com.skysoul.plugin.rbase.utils.StringUtil
import java.io.File

class LayoutCreator : ICreator {
    private var mName: String? = null
    private var mFile: VirtualFile? = null
    private lateinit var action: AnActionEvent
    private lateinit var creatorInfo: CreatorInfo


    private fun getContent() = LayoutTemplate.getMvvmContent(creatorInfo.packageName + "." + creatorInfo.modelName)

    override fun doCreator() {
        val folderLayout = LocalFileSystem.getInstance().findFileByPath(creatorInfo.folder4Layout)
        mFile = folderLayout!!.createChildData(null, "$mName.xml")
        FileUtil.writeFile(mFile!!.path, getContent())
        OpenFileDescriptor(action.project!!, mFile!!).navigate(true)
    }

    override fun init(action: AnActionEvent, creatorInfo: CreatorInfo): String? {
        this.action = action
        this.creatorInfo = creatorInfo
        mName = initFileName()
        if (File(creatorInfo.folder4Layout, "$mName.xml").exists()) {
            return "layout file Cannot be Created ,the name $mName exists"
        }
        creatorInfo.layoutName = mName!!
        return null
    }

    private fun initFileName(): String {
        val preName: String? = creatorInfo.preNameInput
        val name: String = creatorInfo.nameInput

        var flag = "act"
        if (creatorInfo.mSelect == SelectDialog.SELECT_FRAGMENT) {
            flag = "frag"
        }
        return (if (preName.isNullOrEmpty()) "" else preName.toLowerCase() + "_") + "${flag}_" + StringUtil.toResName(name)
    }

    override fun doCancel() {
        if (mFile != null && mFile!!.exists()) {
            mFile!!.delete(null)
        }
    }
}