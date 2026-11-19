package com.riskified.models;

/**
 * Documented values for {@code BaseOrder.source} (the platform the order came from).
 *
 * @see BaseOrder#setSource(String)
 */
public final class OrderSource {

    public static final String DESKTOP_WEB = "desktop_web";
    public static final String MOBILE_WEB = "mobile_web";
    public static final String MOBILE_APP = "mobile_app";
    public static final String MOBILE_APP_ANDROID = "mobile_app_android";
    public static final String MOBILE_APP_IOS = "mobile_app_ios";
    public static final String WEB = "web";
    public static final String CHAT = "chat";
    public static final String THIRD_PARTY = "third_party";
    public static final String PHONE = "phone";
    public static final String IN_STORE = "in_store";
    public static final String SHOPIFY_DRAFT_ORDER = "shopify_draft_order";
    public static final String UNKNOWN = "unknown";
    public static final String SUBSCRIPTION = "subscription";
    public static final String AI_AGENT = "ai_agent";

    private OrderSource() {
    }
}
