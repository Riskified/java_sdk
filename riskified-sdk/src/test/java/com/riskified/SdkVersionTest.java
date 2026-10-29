package com.riskified;

import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static org.junit.Assert.assertEquals;

public class SdkVersionTest {

    @Test
    public void versionMatchesPom() throws Exception {
        assertEquals(pomVersion(), SdkVersion.VALUE);
    }

    @Test
    public void userAgentReportsSdkVersion() throws Exception {
        RiskifiedClient client = new RiskifiedClient("shop.example.com", "auth-key", Environment.SANDBOX);
        HttpPost request = client.createPostRequest("https://sandbox.riskified.com/api/submit");
        assertEquals("riskified_java_sdk/" + pomVersion(), request.getFirstHeader("User-Agent").getValue());
    }

    // The project's own <version>, a direct child of <project> (not the parent's or a dependency's).
    private static String pomVersion() throws Exception {
        Element project = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new File("pom.xml")).getDocumentElement();
        for (Node child = project.getFirstChild(); child != null; child = child.getNextSibling()) {
            if ("version".equals(child.getNodeName())) {
                return child.getTextContent().trim();
            }
        }
        throw new IllegalStateException("No <version> in pom.xml");
    }
}
