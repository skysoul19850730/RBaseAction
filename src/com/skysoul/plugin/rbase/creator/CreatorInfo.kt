package com.skysoul.plugin.rbase.creator

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.xml.XmlFile
import com.jetbrains.rd.util.insert
import com.skysoul.plugin.rbase.constants.FConfig
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class CreatorInfo {
    lateinit var actionEvent: AnActionEvent
    lateinit var file: VirtualFile
    var preNameInput: String? = ""
    var nameInput: String = ""
    var mSelect: Int = -1

    var packageName: String = ""
    var appId: String = ""
    var className: String = ""
    var dataBindingName: String = ""
    var modelName: String = ""
    var layoutName: String = ""
    var path4Manifest: String = ""
    var folder4Layout: String = ""
    var folder4Opt: String = ""

    fun init(action: AnActionEvent, preName: String?, name: String, select: Int): String? {
        this.actionEvent = action
        file = actionEvent.getData(PlatformDataKeys.VIRTUAL_FILE)!!
        preNameInput = preName
        nameInput = name
        mSelect = select

        return parseFilePaths()
    }

    private fun parseFilePaths(): String? {
        try {

            if(!file.isDirectory){
                file = file.parent
            }
            folder4Opt = file.path

            val srcMainIndex = folder4Opt.indexOf(FConfig.SRC_MAIN)
            if (srcMainIndex < 0) {
                return "找不到路径：${FConfig.SRC_MAIN}"
            }
            val folder4SrcMain = folder4Opt.substring(0, srcMainIndex + FConfig.SRC_MAIN.length)
            folder4Layout = folder4SrcMain + FConfig.RES_LAYOUT

            val fileSrcMain = file.fileSystem.findFileByPath(folder4SrcMain)
            if (fileSrcMain == null || !fileSrcMain.exists()) {
                return "找不到路径：${FConfig.SRC_MAIN}"
            }
            var fileRes = fileSrcMain.findChild("res")
            if (fileRes == null || !fileRes.exists()) {
                fileRes = fileSrcMain.createChildDirectory(null, "res")
            }
            var fileLayout = fileRes!!.findChild("layout")
            if (fileLayout == null || !fileLayout.exists()) {
                fileLayout = fileRes.createChildDirectory(null, "layout")
            }


            path4Manifest = folder4SrcMain + FConfig.MANIFEST_FILENAME
            val fileManifest = File(path4Manifest)
            if (!fileManifest.exists()) {
                return "Manifest is not exists"
            }
            try {
                val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(File(path4Manifest))
                document.documentElement
                appId = document.documentElement.getAttribute("package")
            } catch (e: Exception) {
                return "cannot get appId from manifest"
            }
            packageName = folder4Opt.substring(folder4Opt.indexOf(FConfig.SRC_MAIN_JAVA) + FConfig.SRC_MAIN_JAVA.length).replace("/", ".")
        } catch (e: Exception) {
            return e.message
        }

        return null
    }


}