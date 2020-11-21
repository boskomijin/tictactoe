package com.tictactoe.game.web.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * The <code>ExceptionAdvisor</code> class declares custom exception handlers which handles custom exceptions defined in
 * this application and globally handles all the exceptions defined.
 *
 * @author Bosko Mijin.
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {

    /**
     * The <code>handleAnotherPlayersTurnException</code> method handles the exception where the player is tries to
     * place a mark but it's not his turn and the exception {@link AnotherPlayersTurnException} is raised. Creates a
     * response with the {@link DefaultExceptionAttributes} in the response body as JSON and a HTTP status code of 409 -
     * conflict.
     *
     * @param anotherPlayersTurnException - AnotherPlayersTurnException which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         400 - bad request.
     */
    @ExceptionHandler(AnotherPlayersTurnException.class)
    public ResponseEntity<Map<String, Object>> handleAnotherPlayersTurnException(
            AnotherPlayersTurnException anotherPlayersTurnException, HttpServletRequest request) {
        log.error("--> handleAnotherPlayersTurnException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(anotherPlayersTurnException,
                request, HttpStatus.CONFLICT);
        log.error("<-- handleAnotherPlayersTurnException");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * The <code>handleGameOverException</code> method handles the exception where the player is already joined and the
     * exception {@link GameOverException} is raised. Creates a response with the {@link DefaultExceptionAttributes} in
     * the response body as JSON and a HTTP status code of 400 - bad request.
     *
     * @param gameOverException - GameOverException which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         400 - bad request.
     */
    @ExceptionHandler(GameOverException.class)
    public ResponseEntity<Map<String, Object>> handleGameOverException(GameOverException gameOverException,
            HttpServletRequest request) {
        log.error("--> handleGameOverException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(gameOverException, request,
                HttpStatus.CONFLICT);
        log.error("<-- handleGameOverException");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * The <code>handleInvalidPositionException</code> method handles the exception where the player is sending the bad
     * input for position and the exception {@link InvalidPositionException} is raised. Creates a response with the
     * {@link DefaultExceptionAttributes} in the response body as JSON and a HTTP status code of 400 - bad request.
     *
     * @param invalidPositionException - InvalidPositionException which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         400 - bad request.
     */
    @ExceptionHandler(InvalidPositionException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPositionException(
            InvalidPositionException invalidPositionException, HttpServletRequest request) {
        log.error("--> handleInvalidPositionException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(invalidPositionException, request,
                HttpStatus.CONFLICT);
        log.error("<-- handleInvalidPositionException");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * The <code>handleAlreadyJoinedException</code> method handles the exception where the player is already joined and
     * the exception {@link AlreadyJoinedException} is raised. Creates a response with the
     * {@link DefaultExceptionAttributes} in the response body as JSON and a HTTP status code of 409 - conflict.
     *
     * @param alreadyJoinedException - AlreadyJoinedException which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         409 - conflict.
     */
    @ExceptionHandler(AlreadyJoinedException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyJoinedException(
            AlreadyJoinedException alreadyJoinedException, HttpServletRequest request) {
        log.error("--> handleAlreadyJoinedException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(alreadyJoinedException, request,
                HttpStatus.CONFLICT);
        log.error("<-- handleAlreadyJoinedException");
        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }

    /**
     * The <code>handleMissingGameException</code> method handles the exception where the player is trying to join on
     * the missing game and the exception {@link MissingGameException} is raised. Creates a response with the
     * {@link DefaultExceptionAttributes} in the response body as JSON and a HTTP status code of 404 - not found.
     *
     * @param missingGameException - MissingGameException which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         404 - not found.
     */
    @ExceptionHandler(MissingGameException.class)
    public ResponseEntity<Map<String, Object>> handleMissingGameException(MissingGameException missingGameException,
            HttpServletRequest request) {
        log.error("--> handleMissingGameException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(missingGameException, request,
                HttpStatus.NOT_FOUND);
        log.error("<-- handleMissingGameException");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    /**
     * The <code>handleMissingPlayerException</code> method handles the exception where the player is trying to join on
     * the game but missing player which is created the game and the exception {@link MissingPlayerException} is raised.
     * Creates a response with the {@link DefaultExceptionAttributes} in the response body as JSON and a HTTP status
     * code of 404 - not found.
     *
     * @param missingPlayerException - MissingPlayerException which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         404 - not found.
     */
    @ExceptionHandler(MissingPlayerException.class)
    public ResponseEntity<Map<String, Object>> handleMissingPlayerException(
            MissingPlayerException missingPlayerException, HttpServletRequest request) {
        log.error("--> handleMissingPlayerException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(missingPlayerException, request,
                HttpStatus.NOT_FOUND);
        log.error("<-- handleMissingPlayerException");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    /**
     * The <code>handleAuthenticationException</code> method handles the exception where the player is not authenticated
     * and the exception {@link AuthenticationException} is raised. Creates a response with the
     * {@link DefaultExceptionAttributes} in the response body as JSON and a HTTP status code of 401 - unauthorized.
     *
     * @param authenticationException - AuthenticationException which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         401 - unauthorized.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(
            AuthenticationException authenticationException, HttpServletRequest request) {
        log.error("--> handleAuthenticationException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(authenticationException, request,
                HttpStatus.UNAUTHORIZED);
        log.error("<-- handleAuthenticationException");
        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }

    /**
     * The <code>handleGeneralException</code> method handles all the exceptions which are not covered separately.
     *
     * @param exception - The exception which has to be handled.
     * @param request - The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity - An entity which containing the Exception Attributes in the body and a HTTP status code
     *         500 - internal server error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception exception, HttpServletRequest request) {
        log.error("--> handleGeneralException");
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request,
                HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("<-- handleGeneralException");
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
