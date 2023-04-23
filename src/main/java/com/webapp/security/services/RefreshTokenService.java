package com.webapp.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.exception.TokenRefreshException;
import com.webapp.models.RefreshToken;
import com.webapp.repositories.RefreshTokenRepo;
import com.webapp.repositories.UserRepo;

@Service
public class RefreshTokenService {
  @Value("${stuju.app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;
  @Autowired
  private RefreshTokenRepo refreshTokenRepo;
  @Autowired
  private UserRepo userRepo;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepo.findByToken(token);
  }

  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUser(userRepo.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken = refreshTokenRepo.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepo.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepo.deleteByUser(userRepo.findById(userId).get());
  }
}
