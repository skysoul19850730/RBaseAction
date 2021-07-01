package com.skysoul.plugin.rbase.utils

import com.skysoul.plugin.rbase.creator.tamplates.LayoutTemplate
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.charset.Charset

object FileUtil {

    fun writeFile(fileAbPath: String, content: String) {
        var randomAccessFile: RandomAccessFile? = null
        try {
            randomAccessFile = RandomAccessFile(fileAbPath, "rw")
            if (randomAccessFile?.length() > 2) {
                randomAccessFile?.seek(randomAccessFile.length() - 2)
            }
            randomAccessFile?.write(content.toByteArray(Charset.forName("UTF-8")))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                randomAccessFile?.close()
            } catch (e: Exception) {

            }
        }
    }


    fun createFileAnywayRepeat(folder: String, name: String, suffix: String): String {
        var layoutName = name
        var repeatCount = 0
        while (true) {
            val layoutFile = File(folder, "$layoutName$suffix")
            if (!layoutFile.exists()) {
//                try {
//                    layoutFile.createNewFile()
//                }catch (e: IOException){
//                    e.printStackTrace()
//                    layoutName = ""
//                }
                break
            } else {
                repeatCount++
                layoutName = name + "_$repeatCount"
            }
        }
        return layoutName
    }
}