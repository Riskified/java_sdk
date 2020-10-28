package com.riskified;

import com.riskified.models.*;

public class AdviseDecideHandler {

    private Response response;

    public Response handleAdviseOnly(Response adviseResponse) {
        String adviseStatus = adviseResponse.getOrder().getStatus();
        String authType = adviseResponse.getOrder().getAuthenticationType().getAuthType();

        if (adviseStatus.equals("declined") && authType.equals("fraud")) {
            response = handleDeclinedAndFraud(adviseResponse);
        } else if (adviseStatus.equals("declined") && authType.equals("out_of_scope")) {
            response = handleDeclineAndOutOfScope(adviseResponse);
        } else if (adviseStatus.equals("captured") && authType.equals("sca")) {
            response = handleCapturedAndSca(adviseResponse);
        }

        return response;
    }

    public Response handleAdviseAndDecide(Response adviseResponse, Response decideResponse) {
        String authType = adviseResponse.getOrder().getAuthenticationType().getAuthType();
        String decideStatus = decideResponse.getOrder().getStatus();

        if (authType.equals("tra") && decideStatus.equals("declined")) {
            response = handleTraAndDeclined(decideResponse);
        } else if (authType.equals("tra") && decideStatus.equals("approved")) {
            response = handleTraAndApprovedOrCaptured(decideResponse, adviseResponse);
        } else if (authType.equals("low_amount") && decideStatus.equals("declined")) {
            response = handleLowAmountAndDeclined(decideResponse);
        } else if (authType.equals("low_amount") && !decideStatus.equals("declined")) {
            response = handleLowAmountAndApprovedOrCaptured(decideResponse);
        } else if (authType.equals("out_of_scope")) {
            response = handleOutOfScope(decideResponse);
        }

        return response;
    }

    public Response handleDeclinedAndFraud(Response adviseResponse) {
        Advice advice = new Advice(true);
        ResOrder adviseResponseOrder = adviseResponse.getOrder();

        adviseResponseOrder.setDescription("Order exhibits data points that are highly correlated with fraudulent activity");
        adviseResponseOrder.setAdvice(advice);
        adviseResponse.setOrder(adviseResponseOrder);
        return adviseResponse;
    }

    public Response handleDeclineAndOutOfScope(Response adviseResponse) {
        Advice advice = new Advice(false);
        ResOrder adviseResponseOrder = adviseResponse.getOrder();

        adviseResponseOrder.setDescription("Order exhibits data points that are highly correlated with fraudulent activity");
        adviseResponseOrder.setAdvice(advice);
        adviseResponse.setOrder(adviseResponseOrder);
        return adviseResponse;
    }

    public Response handleCapturedAndSca(Response adviseResponse) {
        Advice advice = new Advice(true);
        advice.setRecommendation("sca");
        ResOrder adviseResponseOrder = adviseResponse.getOrder();

        adviseResponseOrder.setDescription("Received by Riskified");
        adviseResponseOrder.setAdvice(advice);
        adviseResponse.setOrder(adviseResponseOrder);
        return adviseResponse;
    }

    public Response handleTraAndDeclined(Response decideResponse) {
        Advice advice = new Advice(true);
        ResOrder decideResponseOrder = decideResponse.getOrder();
        decideResponseOrder.setAdvice(advice);
        decideResponse.setOrder(decideResponseOrder);
        return decideResponse;
    }

    public Response handleTraAndApprovedOrCaptured(Response decideResponse, Response adviseResponse) {
        Advice advice = new Advice(true);
        advice.setRecommendation("tra");
        advice.setScore(adviseResponse.getOrder().getScore());
        ResOrder decideResponseOrder = decideResponse.getOrder();
        decideResponseOrder.setAdvice(advice);
        decideResponse.setOrder(decideResponseOrder);
        return decideResponse;
    }

    public Response handleLowAmountAndDeclined(Response decideResponse) {
        Advice advice = new Advice(true);
        ResOrder decideResponseOrder = decideResponse.getOrder();
        decideResponseOrder.setAdvice(advice);
        decideResponse.setOrder(decideResponseOrder);
        return decideResponse;
    }

    public Response handleLowAmountAndApprovedOrCaptured(Response decideResponse) {
        Advice advice = new Advice(true);
        advice.setRecommendation("low_amount");
        ResOrder decideResponseOrder = decideResponse.getOrder();
        decideResponseOrder.setAdvice(advice);
        decideResponse.setOrder(decideResponseOrder);
        return decideResponse;
    }

    public Response handleOutOfScope(Response decideResponse) {
        Advice advice = new Advice(false);
        ResOrder decideResponseOrder = decideResponse.getOrder();

        decideResponseOrder.setAdvice(advice);
        decideResponse.setOrder(decideResponseOrder);
        return decideResponse;
    }
}


