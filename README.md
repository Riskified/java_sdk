Riskified JAVA SDK
=================

version: 5.0.0
------------------

See http://apiref.riskified.com for full API documentation

see riskified-sample/ for examples on how to use this SDK.

Data validation:

---------------
The SDK includes a validation mechanism to help you catch formatting and data issues quickly.
However, it is possible to control the level of validation, through the SDKs Validation object,
which is the forth parameter in the SDK's RiskifiedClient constructor.
For example, you can tell the Validation engine to ignore missing values, by passing the constructor the
Validations.ignoreMissing

like this:

```text
RiskifiedClient client = new RiskifiedClient(domain, authToken, Environment.SANDBOX, Validation.IGNORE_MISSING);
```

Or specify the validation type in the config file 'riskified_sdk.properties'.

Available validation types:

* none - disable validations
* ignoreMissing - validates only the data format
* all - validates the data format and that required fields are not missing

PSD2: Advise endpoint old response testing:

-------------------------------------------
Riskified changed the /advice endpoint response. To enable the old response for testing purposes, follow steps below:

1. Open riskified_sdk.properties file
2. Set "enable_old_advise_response" to "true"

Riskified SDK will return the old advise response.

Prerequisites:

---------------

* Java JDK-8 or higher

Running the samples:

--------------------------

* **Build the SDK** - Run `mvn package` which will build both the SDK and the samples
* **Configure your properties** (authKey and shopUrl) - in the riskified-sdk/src/main/resources/riskified_sdk.properties file.
 Your Riskified's authorization token (authKey) can be found in the [advanced settings section](https://sandbox.riskified.com/main/settings/advanced) of your Riskified sandbox environment.

## Simple order creation sample

This samples shows how to construct an Order model and invoke the basic api/create endpoint

```shell
cd riskified-sample
mvn exec:java -Dexec.mainClass="com.riskified.samples.orderClient.SimpleClient"
```

Client sample
===============

This samples shows how to construct an Order model and invoke each and every one of our endpoints with it

```shell
cd riskified-sample
mvn exec:java -Dexec.mainClass="com.riskified.samples.orderClient.Client"
```

## Json Client sample

This samples shows how to construct an Order model and save it locally as a Json file, this sample is useful for early stages of the integration and for POCs

```shell
cd riskified-sample
mvn exec:java -Dexec.mainClass="com.riskified.samples.orderClient.JsonClient"
```

## Notifications sample

This samples shows how to bring up a notification end point

```sh
cd riskified-sample
mvn exec:java -Dexec.mainClass="com.riskified.samples.notificationServer.servlet.SampleServer"
```

Then you can test the endpoint by running a curl command from another terminal

```sh
curl -H "Content-Type: application/json" -H  "X-RISKIFIED-HMAC-SHA256: 071ef80d5790011d2f111479b75eed15e907432a4523defb4e627c6725d3b6b3" -X POST -d '{"order":{"id":"123","status":"approved","old_status":"submitted","description":"Approved by Riskified"}}' http://localhost:8080
```

## Maven dependency excerpt

```xml
<dependency>
    <groupId>com.riskified</groupId>
    <artifactId>riskified-sdk</artifactId>
    <version>v5.0.0</version>
</dependency>
```

