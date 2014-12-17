package main.java.com.riskified;

import java.security.NoSuchAlgorithmException;

public class RiskifedError extends Exception {

  public RiskifedError(Exception ex) {
    super("Error: contact yout admin with this error", ex);
  }

  public RiskifedError(String string) {
    super("Error: contact yout admin with this error: " + string);
  }
}
