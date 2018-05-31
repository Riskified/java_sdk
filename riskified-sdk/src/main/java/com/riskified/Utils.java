package com.riskified;

public class Utils {

    public static final String DEBUG_ENVIRONMENT = "http://localhost:3000";
    public static final String SANDBOX_ENVIRONMENT = "https://sandbox.riskified.com";
    public static final String PRODUCTION_ENVIRONMENT = "https://wh.riskified.com";
    public static final String PRODUCTION_SYNC_ANALYZE_ENVIRONMENT = "https://wh-sync.riskified.com";
    public static final String DECO_SANDBOX_ENVIRONMENT = "https://sandboxw.decopayments.com";
    public static final String DECO_PRODUCTION_ENVIRONMENT = "https://w.decopayments.com";

    public static String getBaseUrlFromEnvironment(Environment environmentType) {
        String url = getUrlString(environmentType, SANDBOX_ENVIRONMENT, PRODUCTION_ENVIRONMENT);
        return url;
    }
    
    public static String getBaseUrlSyncAnalyzeFromEnvironment(Environment environmentType) {
        String url = getUrlString(environmentType, SANDBOX_ENVIRONMENT, PRODUCTION_SYNC_ANALYZE_ENVIRONMENT);
        return url;
    }

    public static String getDecoBaseFromEnvironment(Environment environmentType) {
        String url = getUrlString(environmentType, DECO_SANDBOX_ENVIRONMENT, DECO_PRODUCTION_ENVIRONMENT);
        return url;
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
