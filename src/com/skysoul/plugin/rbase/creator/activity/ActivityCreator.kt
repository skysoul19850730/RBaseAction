package com.skysoul.plugin.rbase.creator.activity

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.xml.XmlFile
import com.skysoul.plugin.rbase.creator.CreatorInfo
import com.skysoul.plugin.rbase.creator.ICreator
import com.skysoul.plugin.rbase.creator.tamplates.ActivityTemplate
import com.skysoul.plugin.rbase.creator.tamplates.ManifestTemplate
import com.skysoul.plugin.rbase.utils.FileUtil
import com.skysoul.plugin.rbase.utils.StringUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.StringBuilder

class ActivityCreator : ICreator {
    var mName: String? = null
    private var mFile: VirtualFile? = null
    private lateinit var action: AnActionEvent
    private lateinit var creatorInfo: CreatorInfo


    private fun getContent() = creatorInfo.run {
        dataBindingName = StringUtil.toClassName(layoutName) + "Binding"
        ActivityTemplate.mvvm_kt(packageName, appId, dataBindingName, this@ActivityCreator.mName!!, packageName, modelName, layoutName)
    }

    override fun doCreator() {
        mFile = creatorInfo.file.createChildData(null, "$mName.kt")
        FileUtil.writeFile(mFile!!.path, getContent())
        OpenFileDescriptor(action.project!!, mFile!!).navigate(true)

        registerToManifest()
    }

    override fun init(action: AnActionEvent, creatorInfo: CreatorInfo): String? {
        this.action = action
        this.creatorInfo = creatorInfo
        mName = initFileName()
        if (File(creatorInfo.folder4Opt, "$mName.kt").exists()) {
            return "Activity Cannot be Created ,the name $mName exists"
        }
        creatorInfo.className = mName!!
        return null
    }

    private fun initFileName(): String {
        val preName: String? = creatorInfo.preNameInput
        val name: String = creatorInfo.nameInput

        var finalName = name
        if (!name.contains("Act", true) && !name.contains("Activity", true)) {
            finalName += "Activity"
        }
        return (preName ?: "") + finalName
    }

    override fun doCancel() {
        if (mFile != null && mFile!!.exists()) {
            mFile!!.delete(null)
        }
    }

    fun registerToManifest() {
        val fileMani: XmlFile = (PsiManager.getInstance(action.project!!).findFile(creatorInfo.file.fileSystem.findFileByPath(creatorInfo.path4Manifest)!!) as XmlFile)
        WriteCommandAction.runWriteCommandAction(action.project) {
            var appTag = fileMani.rootTag!!.findFirstSubTag("application")
            if (appTag == null) {
                appTag = fileMani.rootTag!!.createChildTag("application", null, null, true)
                fileMani.rootTag!!.addSubTag(appTag, true)
            }
            val addTag = appTag!!.createChildTag("activity", null, null, true)
            addTag.setAttribute("android:name", "${creatorInfo.packageName}.${mName}")
            appTag.addSubTag(addTag, true)
        }
    }
}