package com.skysoul.plugin.rbase.creator

import com.intellij.openapi.actionSystem.AnActionEvent

interface ICreator {
    fun init(action: AnActionEvent, creatorInfo: CreatorInfo): String?
    fun doCreator()
    fun doCancel()
}