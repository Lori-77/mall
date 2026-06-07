package com.example.mall.common;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功（没有数据）
    public static Result<Void> success() {
        return new Result<>(200, "操作成功", null);
    }

    // 成功（有数据)
    public static<T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 失败
    public static<T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    // getter
    public int getCode() { return code; }
    public String getMsg() { return msg; }
    public T getData() { return data; }
}
