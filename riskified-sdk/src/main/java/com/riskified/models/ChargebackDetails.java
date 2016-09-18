package com.riskified.models;

import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class ChargebackDetails {
	private String id;
	private Date chargebackAt;
	private String chargebackCurrency;
	private Double chargebackAmount;
	private String reasonCode;
	private String reasonDescription;
	private String type;
	private String mid;
	private String arn;
	private String creditCardCompany;
	private Date respondBy;
	private Double feeAmount;
	private String feeCurrency;
	private String cardIssuer;
	private String gateway;
	private String cardHolder;
	private String message;
	
	public ChargebackDetails() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getChargebackAt() {
		return chargebackAt;
	}

	public void setChargebackAt(Date chargebackAt) {
		this.chargebackAt = chargebackAt;
	}

	public String getChargebackCurrency() {
		return chargebackCurrency;
	}

	public void setChargebackCurrency(String chargebackCurrency) {
		this.chargebackCurrency = chargebackCurrency;
	}

	public Double getChargebackAmount() {
		return chargebackAmount;
	}

	public void setChargebackAmount(Double chargebackAmount) {
		this.chargebackAmount = chargebackAmount;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getArn() {
		return arn;
	}

	public void setArn(String arn) {
		this.arn = arn;
	}

	public String getCreditCardCompany() {
		return creditCardCompany;
	}

	public void setCreditCardCompany(String creditCardCompany) {
		this.creditCardCompany = creditCardCompany;
	}

	public Date getRespondBy() {
		return respondBy;
	}

	public void setRespondBy(Date respondBy) {
		this.respondBy = respondBy;
	}

	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getFeeCurrency() {
		return feeCurrency;
	}

	public void setFeeCurrency(String feeCurrency) {
		this.feeCurrency = feeCurrency;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
