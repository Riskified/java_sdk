package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum PaymentType {

    @SerializedName("card")
    CARD,
    @SerializedName("apple_pay")
    APPLE_PAY,
    @SerializedName("google_pay")
    GOOGLE_PAY,
    @SerializedName("samsung_pay")
    SAMSUNG_PAY,
    @SerializedName("paypal")
    PAYPAL,
    @SerializedName("shop_pay")
    SHOP_PAY,
    @SerializedName("amazon_pay")
    AMAZON_PAY,
    @SerializedName("bnpl")
    BNPL,
    @SerializedName("bank_transfer")
    BANK_TRANSFER,
    @SerializedName("ach")
    ACH,
    @SerializedName("crypto")
    CRYPTO,
    @SerializedName("gift_card")
    GIFT_CARD,
    @SerializedName("store_credit")
    STORE_CREDIT,
    @SerializedName("cash_on_delivery")
    CASH_ON_DELIVERY,
    @SerializedName("invoice")
    INVOICE,
    @SerializedName("reward_points")
    REWARD_POINTS,
    @SerializedName("mobile_carrier")
    MOBILE_CARRIER,
    @SerializedName("cashe")
    CASHE,
    @SerializedName("check")
    CHECK,
    @SerializedName("boleto")
    BOLETO,
    @SerializedName("wechat_pay")
    WECHAT_PAY,
    @SerializedName("alipay")
    ALIPAY,
    @SerializedName("other")
    OTHER,

}
