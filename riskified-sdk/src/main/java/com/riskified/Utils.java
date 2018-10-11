package com.riskified;

public class Utils {

    public static final String DEBUG_ENVIRONMENT = "http://localhost:3000";
    public static final String SANDBOX_ENVIRONMENT = "https://sandbox.riskified.com";
    public static final String PRODUCTION_ENVIRONMENT = "https://wh.riskified.com";
    public static final String PRODUCTION_SYNC_DECIDE_ENVIRONMENT = "https://wh-sync.riskified.com";
    public static final String DECO_SANDBOX_ENVIRONMENT = "https://sandboxw.decopayments.com";
    public static final String DECO_PRODUCTION_ENVIRONMENT = "https://w.decopayments.com";
    public static final String ACCOUNT_SANDBOX_ENVIRONMENT = "https://api-sandbox.riskified.com";
    public static final String ACCOUNT_PRODUCTION_ENVIRONMENT = "https://api.riskified.com";

    public static String getBaseUrlFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, SANDBOX_ENVIRONMENT, PRODUCTION_ENVIRONMENT);
    }
    
    public static String getBaseUrlSyncDecideFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, SANDBOX_ENVIRONMENT, PRODUCTION_SYNC_DECIDE_ENVIRONMENT);
    }

    public static String getDecoBaseFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, DECO_SANDBOX_ENVIRONMENT, DECO_PRODUCTION_ENVIRONMENT);
    }

    public static String getAccountBaseFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, ACCOUNT_SANDBOX_ENVIRONMENT, ACCOUNT_PRODUCTION_ENVIRONMENT);
    }

    private static String getUrlString(Environment environmentType, String sandboxEnvironment, String productionEnvironment) {
        String url = null;
        if (environmentType != null) {
            switch (environmentType) {
                case DEBUG:
                    url = DEBUG_ENVIRONMENT;
                    break;
                case SANDBOX:
                    url = sandboxEnvironment;
                    break;
                case PRODUCTION:
                    url = productionEnvironment;
                    break;
            }
        }
        return url;
    }
}
