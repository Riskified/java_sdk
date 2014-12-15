package main.java.com.riskified;

import java.security.NoSuchAlgorithmException;

public class RiskifedError extends Exception {

  public RiskifedError(Exception e){
    super(e);
  }

  public RiskifedError(NoSuchAlgorithmException e) {
    super(e);
  }
}
