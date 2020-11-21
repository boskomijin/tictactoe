package com.tictactoe.game.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tictactoe.game.model.Game;
import com.tictactoe.game.model.Player;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The <code>TokenUtil</code> class is utility class for token handling.
 *
 * @author Bosko Mijin.
 */
@Component
public class TokenUtil implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3301605591108950415L;

    /** The secret. */
    @Value("${jwt.secret}")
    private String secret;

    /** The expiration. */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * The <code>getUsernameFromToken</code> method gets the username from token.
     *
     * @param token - the token.
     * @return String - the username from token.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * The <code>getExpirationDateFromToken</code> method gets the expiration date from token.
     *
     * @param token - the token
     * @return Date - the expiration date from token.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * The <code>getClaimFromToken</code> method gets the claim from token.
     *
     * @param <T> the generic type
     * @param token - the token.
     * @param claimsResolver - the claims resolver.
     * @return the claim from token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gets the all claims from token.
     *
     * @param token the token
     * @return the all claims from token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if is token expired.
     *
     * @param token - the token which has to be checked.
     * @return boolean - is created before last password reset.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token.
     *
     * @param userId - the user id.
     * @return string - generated token.
     */
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userId);
    }

    /**
     * Do generate token.
     *
     * @param claims - the claims.
     * @param subject - the subject.
     * @return string - generated token.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * The <code>validateToken</code> method validates the provided token.
     *
     * @param token - The token which has to be validated.
     * @param game - The game which holds the player.
     * @return boolean - true if token is valid, otherwise false.
     */
    public Boolean validateToken(String token, Game game) {
        String playerIdFromToken = getUsernameFromToken(token);
        Map<Player, Character> filteredPlayers = game.getPlayers().entrySet().stream()
                .filter(entry -> entry.getKey().getId().equals(playerIdFromToken))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        isTokenExpired(token);
        return !filteredPlayers.isEmpty();
    }

    /**
     * Calculate expiration date.
     *
     * @param createdDate - the created date.
     * @return date - calculated expiration date.
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
