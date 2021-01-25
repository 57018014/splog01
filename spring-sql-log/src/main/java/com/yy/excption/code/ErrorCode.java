package com.yy.excption.code;


public enum ErrorCode {

    SUCCESS(0, "请求成功"),
    //================== common =================//
    SERVICE_ERROR(1, "服务器内部错误,请重试"),
    PARAMS_ERROR(2, "参数错误"),
    DATA_MISS(3, "请求数据不存在"),
    //================== 登录相关 =================//
    TOKEN_NULL(200, "未登陆,请登陆"),
    TOKEN_INVALID(201, "请重新登陆"), //token失效 or redis中的数据结构有问题
    //================== 直播相关 =================//
    //300
    SUFFIX_NOT_SUPPORT(300, "不支持的文件类型,只支持mp4文件"),
    DELETE_FAIL_NO_AUTHOR(301, "删除失败，只能删除本人的视频"),
    ANCHOR_NO_ENOUGH_VOLUE(302, "空间不足，请联系管理员扩容"),
    INIT_ANCHOR_ERROR(303, "创建主持人失败"),

    //================== 新闻相关 =================//
    //400
    ACTION_ERROR(1,"操作失败"),
    //================== 课程相关 =================//
    //500


    //================== 视频转码相关 =================//
    // 600
    TRANSCODE_ERROR(600, "转码失败"),
    //=================== 交友相关 ===================//
    FRIEND_EXIST(3, "用户已存在"),






    ;
    private int errorCode;

    private String errorMsg;


    ErrorCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}