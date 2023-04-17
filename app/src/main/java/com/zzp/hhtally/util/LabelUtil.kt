package com.zzp.hhtally.util

import com.zzp.hhtally.data.Label

object LabelUtil {
    var labelList: ArrayList<Label> = arrayListOf()

    fun getLabelNameFormId(labelId: Int) : String{
        for( label in labelList) {
            if (label.labelId == labelId) {
                return label.labelName
            }
        }

        throw Exception("标签id不存在")
    }

    fun getLabelIdFromName(labelName: String): Int {
        for( label in labelList) {
            if (label.labelName == labelName) {
                return label.labelId
            }
        }

        throw Exception("标签名不存在")
    }

    fun isContainLabelName(labelName: String) : Boolean{
        for( label in labelList) {
            if (label.labelName == labelName) {
                return true
            }
        }
        return false
    }
}