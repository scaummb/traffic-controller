package com.bryant.traffic.response;

public enum ResponseCode {
    REDIS_COMMON_EXCEPTION("603", "Redis统一异常!")
    ;



    private final String code;
    private final String msg;

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getFormatMsg(String... params) {
        return String.format(this.getMsg(), params);
    }

    public String toString() {
        return "ResponseCode(code=" + this.getCode() + ", msg=" + this.getMsg() + ")";
    }

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
