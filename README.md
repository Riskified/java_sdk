Riskified JAVA SDK
=================

version: v3.0.0
------------------

# 📚Documentation

For complete API documentation, visit [Riskified API Reference](https://apiref.riskified.com)


# 🚀Getting Started
    
**Installation**

Add this dependency to your project's pom.xml

```xml
    <dependency>
        <groupId>com.riskified</groupId>
        <artifactId>riskified-sdk</artifactId>
        <version>v3.0.0</version>
    </dependency>
```

You can also import the dependecy through your IDE:
* **IntelliJ IDEA**: Go to File → Project Structure → Libraries → + → From Maven, then search for "com.riskified:riskified-sdk:v3.0.0"
* **Eclipse**: Right-click on your project → Maven → Add Dependency, then search for the artifact

For Gradle projects, add to your build.gradle file:
```java
implementation 'com.riskified:riskified-sdk:v3.0.0' 
```

# 🛠️Configuration
```java
RiskifiedClient client = new RiskifiedClient(<shopUrl>, <authToken>, Environment.SANDBOX);
```
**Data Validation**
The SDK includes a validation mechanism to help you catch formatting and data issues quickly. You can control the validation level through the `Validation` object when creating a `RiskifiedClient`

```java
// Initialize with specific validation level
RiskifiedClient client = new RiskifiedClient(<shopUrl>, <authToken>, Environment.SANDBOX, Validation.IGNORE_MISSING);
```

| Type              | Description                               |
|-------------------|-------------------------------------------|
| `NONE`            | Disable validations completely            |
| `IGNORE_MISSING`  | Validate only data format                 |
| `ALL`             | Validate data format and required fields  |

## 🧪 Examples

For complete examples on how to use this SDK, refer to the `riskified-sample/` directory in the repository.

### Building the SDK and Samples
**Build the SDK** 
```bash 
bash
mvn package
```
which will build both the SDK and the samples
# Running the samples:


* **Configure your properties** (authKey and shopUrl) - in the 
riskified-sdk/src/main/resources/riskified_sdk.properties file.
 Your Riskified's authorization token (authKey) can be found in the [advanced settings section](https://sandbox.riskified.com/main/settings/advanced) of your Riskified sandbox environment.
 
## Simple order creation sample

This samples shows how to construct an Order model and invoke the basic api/create endpoint

```shell
cd riskified-sample
mvn exec:java -Dexec.mainClass="com.riskified.samples.orderClient.SimpleClient"
```

## Client sample


This sample shows how to construct an Order model and invoke each and every one of our endpoints with it

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

```bash
curl -H "Content-Type: application/json" \ -H "X-RISKIFIED-HMAC-SHA256:071ef80d5790011d2f111479b75eed15e907432a4523defb4e627c6725d3b6b3" \ -X POST \ -d '{"order":{"id":"123","status":"approved","old_status":"submitted","description":"Approved by Riskified"}}' \ http://localhost:8080
```
