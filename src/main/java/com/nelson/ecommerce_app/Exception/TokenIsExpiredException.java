package com.nelson.ecommerce_app.Exception;

public class TokenIsExpiredException extends Throwable {
    public TokenIsExpiredException(String tokenHasExpired) {
        super(tokenHasExpired);
    }
}
