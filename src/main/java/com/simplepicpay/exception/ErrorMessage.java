package com.simplepicpay.exception;

import org.antlr.v4.runtime.atn.ErrorInfo;

public class ErrorMessage extends RuntimeException {
    public ErrorMessage(String errorMessage){
        super(errorMessage);
    }
}
