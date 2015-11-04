Riskified JAVA SDK
=================
See http://apiref.riskified.com for full API documentation 

See riskified-sample/ for examples on how to use this SDK.

Data validation:
---------------
The SDK includes a validation mechanism to help you catch formatting and data issues quickly.
However, it is possible to control the level of validation, through the SDKs Validation object,
which is the forth parameter in the SDK's RiskifiedClient constructor.
For example, you can tell the Validation engine to ignore missing values, by passing the constructor the
Validations.ignoreMissing

like this:
```
RiskifiedClient client = new RiskifiedClient(domain, authToken, Environment.SANDBOX, Validation.IGNORE_MISSING);
```
Or specify the validation type in the config file 'riskified_sdk.properties'.

Available validation types: 
*	none - disable validations
*	ignoreMissing - validates only the data format
*	all - validates the data format and that required fields are not missing


Prerequisites:
---------------
*	Java JDK-1.5 or higher

To build sdk and samples:
--------------------------
*	Run `mvn install` to build sdk jar and sample.

Client sample
--------------------------
This samples shows how to construct an Order model and invoke each and every one of our endpoints with it
<code sh>
cd riskified-sample

mvn exec:java -Dexec.mainClass="com.riskified.samples.orderClient.Client"
<code>

Json Client sample
--------------------------
This samples shows how to construct an Order model and save it locally as a Json file, this sample is useful for early stages of the integration and for POCs
<code sh>
cd riskified-sample

mvn exec:java -Dexec.mainClass="com.riskified.samples.orderClient.JsonClient"

<code>

Notifications sample
--------------------------
This samples shows how to bring up a notification end point
<code sh>
cd riskified-sample

mvn exec:java -Dexec.mainClass="com.riskified.samples.notificationServer.servlet.SampleServer"
<code>


Maven dependency excerpt:
----------------
```xml
<dependency>
	<groupId>com.riskified</groupId>
	<artifactId>riskified-sdk</artifactId>
	<version>1.0.1.5</version>
</dependency>
```


