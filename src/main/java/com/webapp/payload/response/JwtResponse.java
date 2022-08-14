package com.webapp.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String refreshToken;
  private Long id;
  private String fullName;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken , String refreshToken, Long id, String fullName, String username, String email, List<String> roles) {
    this.token = accessToken;
    this.refreshToken = refreshToken;
    this.id = id;
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }
}
