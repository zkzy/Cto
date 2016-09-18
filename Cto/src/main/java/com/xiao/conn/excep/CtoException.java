package com.xiao.conn.excep;

import java.io.PrintStream;
import java.io.PrintWriter;

public class CtoException extends RuntimeException {

    public CtoException() {
        super();
    }

    public CtoException(String message) {
        super(message);
    }
}
