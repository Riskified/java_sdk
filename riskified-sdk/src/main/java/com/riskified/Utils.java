package com.riskified;

public class Utils {

    public static final String DEBUG_ENVIRONMENT = "http://localhost:3000";
    public static final String SANDBOX_ENVIRONMENT = "https://sandbox.riskified.com";
    public static final String PRODUCTION_ENVIRONMENT = "https://wh.riskified.com";
    public static final String PRODUCTION_SYNC_ANALYZE_ENVIRONMENT = "https://wh-sync.riskified.com";
    public static final String DECO_SANDBOX_ENVIRONMENT = "https://sandboxw.decopayments.com";
    public static final String DECO_PRODUCTION_ENVIRONMENT = "https://w.decopayments.com";
    public static final String ACCOUNT_SANDBOX_ENVIRONMENT = "https://api-sandbox.riskified.com";
    public static final String ACCOUNT_PRODUCTION_ENVIRONMENT = "https://api.riskified.com";
    public static final String SCREEN_SANDBOX_ENVIRONMENT = "https://screen-sandbox.riskified.com";
    public static final String CHINA_ACCOUNT_PRODUCTION_ENVIRONMENT = "https://apicdn.riskified.com";

    /*
     * Riskified offers suite of services as well as different env, the getUrlString is used to fetch the appropriate service url & env   
     * For example, We added a new production env called China_Production only for ATO product, therefore we added a new parameter to incorporate it into getUrlString method 
     * while maintaining backward compatibility with Riskified Client
     * 
     */
    public static String getBaseUrlFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, SANDBOX_ENVIRONMENT, PRODUCTION_ENVIRONMENT, PRODUCTION_ENVIRONMENT);
    }
    
    public static String getBaseUrlSyncAnalyzeFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, SANDBOX_ENVIRONMENT, PRODUCTION_SYNC_ANALYZE_ENVIRONMENT,PRODUCTION_SYNC_ANALYZE_ENVIRONMENT);
    }

    public static String getDecoBaseFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, DECO_SANDBOX_ENVIRONMENT, DECO_PRODUCTION_ENVIRONMENT,DECO_PRODUCTION_ENVIRONMENT);
    }

    public static String getAccountBaseFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, ACCOUNT_SANDBOX_ENVIRONMENT, ACCOUNT_PRODUCTION_ENVIRONMENT,CHINA_ACCOUNT_PRODUCTION_ENVIRONMENT);
    }

    public static String getScreenBaseFromEnvironment(Environment environmentType) {
        return getUrlString(environmentType, SCREEN_SANDBOX_ENVIRONMENT, PRODUCTION_ENVIRONMENT,PRODUCTION_ENVIRONMENT);
    }

    private static String getUrlString(Environment environmentType, String sandboxEnvironment, String productionEnvironment, String chinaEnvironment) {
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
                case CHINA_PRODUCTION:
                	url = chinaEnvironment;
                	break;
            }
        }
        return url;
    }
}
