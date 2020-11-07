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
        String description = "Order exhibits data points that are highly correlated with fraudulent activity";
        addAdviceToAdviseResponse(adviseResponse, advice, description);
        return adviseResponse;
    }

    public Response handleDeclineAndOutOfScope(Response adviseResponse) {
        Advice advice = new Advice(false);
        String description = "Order exhibits data points that are highly correlated with fraudulent activity";
        addAdviceToAdviseResponse(adviseResponse, advice, description);
        return adviseResponse;
    }

    public Response handleCapturedAndSca(Response adviseResponse) {
        Advice advice = new Advice(true);
        advice.setRecommendation("sca");
        String description = "Received by Riskified";
        addAdviceToAdviseResponse(adviseResponse, advice, description);
        return adviseResponse;
    }

    public Response handleTraAndDeclined(Response decideResponse) {
        Advice advice = new Advice(true);
        addAdviceToDecideResponse(decideResponse, advice);
        return decideResponse;
    }

    public Response handleTraAndApprovedOrCaptured(Response decideResponse, Response adviseResponse) {
        Advice advice = new Advice(true);
        advice.setRecommendation("tra");
        addAdviceToDecideResponse(decideResponse, advice);
        return decideResponse;
    }

    public Response handleLowAmountAndDeclined(Response decideResponse) {
        Advice advice = new Advice(true);
        addAdviceToDecideResponse(decideResponse, advice);
        return decideResponse;
    }

    public Response handleLowAmountAndApprovedOrCaptured(Response decideResponse) {
        Advice advice = new Advice(true);
        advice.setRecommendation("low_amount");
        addAdviceToDecideResponse(decideResponse, advice);
        return decideResponse;
    }

    public Response handleOutOfScope(Response decideResponse) {
        Advice advice = new Advice(false);
        addAdviceToDecideResponse(decideResponse, advice);
        return decideResponse;
    }

    private Response addAdviceToAdviseResponse(Response response, Advice advice, String description) {
        ResOrder responseOrder = response.getOrder();
        responseOrder.setDescription(description);
        responseOrder.setAdvice(advice);
        response.setOrder(responseOrder);
        return response;
    }

    private Response addAdviceToDecideResponse(Response response, Advice advice) {
        ResOrder responseOrder = response.getOrder();
        responseOrder.setAdvice(advice);
        response.setOrder(responseOrder);
        return response;
    }
}


