package com.lc.java.code.demo.multithread;

import lombok.Data;

@Data
public class Result {
    private String code;
    private String msg;
    private int taskResult;

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, int taskResult) {
        this.code = code;
        this.msg = msg;
        this.taskResult = taskResult;
    }
}
