package com.android.app.channel.common;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final String errorMsg;
    private final int errorCode;

    public CommonException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public static CommonException createDefaultException() {
        return new CommonException(-1, "未知错误");
    }

    public static CommonException createCommonException(String errorMsg) {
        return new CommonException(-1, errorMsg);
    }

}
