package com.example.common;

public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    // 无参构造（必须有，否则反序列化会报错）
    public Result() {}

    // 成功的方法
    public static <T> Result<T> ok(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        return r;
    }

    // 失败的方法
    public static <T> Result<T> fail(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMessage(msg);
        return r;
    }

    // --- 以下是手写的 Getter 和 Setter ---
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}