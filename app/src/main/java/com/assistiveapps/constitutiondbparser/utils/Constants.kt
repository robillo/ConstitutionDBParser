package com.assistiveapps.constitutiondbparser.utils

class Constants {
    companion object {
        const val EXTRA_TAG_NAME = "EXTRA_TAG_NAME"
        const val EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_TYPE"

        const val DEFAULT_VALUE_INT = 0
        const val DEFAULT_VALUE_FLOAT = 0f
        const val DEFAULT_VALUE_LONG = 0L
        const val DEFAULT_VALUE_STRING = ""

        const val CATEGORY_PARTS = "PARTS"
        const val CATEGORY_AMENDMENTS = "AMENDMENTS"
        const val CATEGORY_SCHEDULES = "SCHEDULES"
        const val CATEGORY_PREAMBLE = "PREAMBLE"
        const val CATEGORY_ALL = "ALL"

        const val CHARSET_UTF_8 = "UTF-8"
        const val JSON_READ_ELEMENTS = "read_elements"

        const val PREAMBLE_INDEX = 0

        const val SCHEDULES_START_INDEX = 1
        const val SCHEDULES_END_INDEX = 12

        const val AMENDMENTS_START_INDEX = 13
        const val AMENDMENTS_END_INDEX = 113

        const val PARTS_START_INDEX = 121
        const val PARTS_END_INDEX = 599

        const val ALL_START_INDEX = 0
        const val ALL_END_INDEX = 599

        const val MAIN_DB_PATH = "main_db.json"
    }
}