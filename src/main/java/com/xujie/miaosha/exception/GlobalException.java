package com.xujie.miaosha.exception;

import com.xujie.miaosha.result.CodeMsg;

public class GlobalException extends RuntimeException {

    public static final long serialVersionUID = 1l;

    private CodeMsg codeMsg;

    public GlobalException( CodeMsg codeMsg ) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
