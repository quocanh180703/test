package com.example.nhom3_tt_.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${app.jwt.secret}")
  private String SECRET;

  @Value("${app.jwt.access-token-expiration-in-ms}")
  private long expiration;

  public <T> T extractClaim(
      String token, Function<Claims, T> claimsResolver, HttpServletResponse response)
      throws IOException {
    final Claims claims = extractAllClaims(token, response);
    if (claims != null) {
      return claimsResolver.apply(claims);
    }
    return null;
  }

  public String extractUsername(String token, HttpServletResponse response) throws IOException {
    return extractClaim(token, Claims::getSubject, response);
  }

  private Claims extractAllClaims(String token, HttpServletResponse response) throws IOException {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getSignInKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired!");
      return null;
    } catch (MalformedJwtException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Invalid!");
      return null;
    }
  }

  public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    extraClaims.put(
        "role", userDetails.getAuthorities() != null ? userDetails.getAuthorities() : "USER");
    return Jwts.builder()
        .addClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public String generateAccessToken(UserDetails userDetails) {
    return generateAccessToken(new HashMap<>(), userDetails);
  }

  public boolean isTokenValid(String token, UserDetails userDetails, HttpServletResponse response)
      throws IOException {
    final String username = extractUsername(token, response);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, response);
  }

  public boolean isTokenExpired(String token, HttpServletResponse response) throws IOException {
    return extractExpiration(token, response).before(new Date());
  }

  private Date extractExpiration(String token, HttpServletResponse response) throws IOException {
    return extractClaim(token, Claims::getExpiration, response);
  }

  private Key getSignInKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }
}
