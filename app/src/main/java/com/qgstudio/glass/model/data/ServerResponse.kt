package com.qgstudio.glass.model.data

data class ServerResponse<T>(val state: Int, val info: String, val data: T?) {

    enum class StateCodeEnum(val code: Int, val info: String) {
        OK(200, "操作正常"),
        PARAMS_NULL(501, "参数为空"),
        PHONE_NOT_EXIST(51, "电话不存在"),
        VERIFICATION_CODE_ERROR(52, "验证码错误"),
        NAME_TOO_LONG(53, "联系⼈名称太⻓"),
        PHONE_TOO_LONG(54, "电话太⻓"),
        CONTACT_NOT_EXIST(55, "联系⼈不存在"),
        NEED_ALARM(56, "需要报警"),
        NOT_NEED_ALARM(57, "不需要报警"),
        UNKNOWN(-1, "未知状态");

        fun getStateCodeEnum(state: Int): StateCodeEnum {
            return when (state) {
                200 -> {
                    StateCodeEnum.OK
                }
                501 -> {
                    StateCodeEnum.PARAMS_NULL
                }
                51 -> {
                    StateCodeEnum.PHONE_NOT_EXIST
                }
                52 -> {
                    StateCodeEnum.VERIFICATION_CODE_ERROR
                }
                53 -> {
                    StateCodeEnum.NAME_TOO_LONG
                }
                54 -> {
                    StateCodeEnum.PHONE_TOO_LONG
                }
                55 -> {
                    StateCodeEnum.CONTACT_NOT_EXIST
                }
                56 -> {
                    StateCodeEnum.NEED_ALARM
                }
                57 -> {
                    StateCodeEnum.NOT_NEED_ALARM
                }
                else -> {
                    StateCodeEnum.UNKNOWN
                }
            }
        }
    }

    fun getStateCodeEnum(): StateCodeEnum {
        return when (state) {
            200 -> {
                StateCodeEnum.OK
            }
            501 -> {
                StateCodeEnum.PARAMS_NULL
            }
            51 -> {
                StateCodeEnum.PHONE_NOT_EXIST
            }
            52 -> {
                StateCodeEnum.VERIFICATION_CODE_ERROR
            }
            53 -> {
                StateCodeEnum.NAME_TOO_LONG
            }
            54 -> {
                StateCodeEnum.PHONE_TOO_LONG
            }
            55 -> {
                StateCodeEnum.CONTACT_NOT_EXIST
            }
            56 -> {
                StateCodeEnum.NEED_ALARM
            }
            57 -> {
                StateCodeEnum.NOT_NEED_ALARM
            }
            else -> {
                StateCodeEnum.UNKNOWN
            }
        }
    }

}