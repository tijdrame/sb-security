package com.boa.api.sbsecurity.security;

public interface SecurityUtils {
    public static final String name = "";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SECRET_KEY = "TVlTVVBFUlNFQ1JFVDU2NyMwcWUvcWR1aVpJVQ==";
    public static final Integer TOKEN_LIFETIME_DURATION = 60 * 60 * 24 * 1000; //24 h en ms
    public static final Integer ACCESS_TOKEN_LIFETIME_DURATION = 60 * 60 * 24 * 30 * 1000; //1 mois
    

}
