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
RiskifiedClient client = new RiskifiedClient(domain, authToken, Environment.sandbox, Validation.ignoreMissing);
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


Maven dependency excerpt:
----------------
```xml
<dependency>
	<groupId>com.riskified</groupId>
	<artifactId>riskified-sdk</artifactId>
	<version>1.0.0.9</version>
</dependency>
```


