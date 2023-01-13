package org.noahsark.protostuff;

/**
 * 结果对象
 *
 * @author zhangxt
 * @date 2022/12/13 15:01
 **/
public class Result {

    private int code;
    private String message;
    private Object data;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
