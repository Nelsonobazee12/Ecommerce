package com.nelson.ecommerce_app.Exception;

public class TokenParsingFailedException extends Throwable {
    public TokenParsingFailedException(String failedParsingTheToken) {
        super(failedParsingTheToken);
    }
}
