package com.skysoul.plugin.rbase.creator.fragment

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.vfs.VirtualFile
import com.skysoul.plugin.rbase.creator.CreatorInfo
import com.skysoul.plugin.rbase.creator.ICreator
import com.skysoul.plugin.rbase.creator.tamplates.FragmentTemplate
import com.skysoul.plugin.rbase.utils.FileUtil
import com.skysoul.plugin.rbase.utils.StringUtil
import java.io.File

class FragmentCreator:ICreator {
    private var mName:String?=null
    private var mFile: VirtualFile?=null
    private lateinit var action: AnActionEvent
    private lateinit var creatorInfo: CreatorInfo


    private fun getContent() = creatorInfo.run {
        dataBindingName = StringUtil.toClassName(layoutName)+"Binding"
        FragmentTemplate.mvvm(packageName, appId,dataBindingName,this@FragmentCreator.mName!!,packageName,modelName,layoutName)
    }

    override fun doCreator(){
        mFile = creatorInfo.file.createChildData(null, "$mName.kt")
        FileUtil.writeFile(mFile!!.path, getContent())
        OpenFileDescriptor(action.project!!,mFile!!).navigate(true)
    }

    override fun init(action: AnActionEvent, creatorInfo: CreatorInfo): String? {
        this.action = action
        this.creatorInfo = creatorInfo
        mName = initFileName()
        if(File(creatorInfo.folder4Opt,"$mName.kt").exists()){
            return "Fragment Cannot be Created ,the name $mName exists"
        }
        creatorInfo.className = mName!!
        return null
    }

    private fun initFileName():String {
        val preName: String? = creatorInfo.preNameInput
        val name: String = creatorInfo.nameInput

        var finalName = name
        if (!name.contains("Fragment", true)) {
            finalName += "Fragment"
        }
        return (preName ?: "") + finalName
    }

    override fun doCancel() {
        if(mFile!=null && mFile!!.exists()) {
            mFile!!.delete(null)
        }
    }
}