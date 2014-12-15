package main.java.com.riskified;

import java.security.NoSuchAlgorithmException;

public class RiskifedError extends Exception {

  public RiskifedError(Exception ex) {
    super(ex);
  }

  public RiskifedError(NoSuchAlgorithmException ex) {
    super(ex);
  }
}
