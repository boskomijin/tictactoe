package com.tictactoe.game.web.exception;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

/**
 * The <code>DefaultExceptionAttributes</code> class is the default implementation of {@link ExceptionAttributes}. This
 * implementation seeks to be similar to the {@link DefaultErrorAttributes} class, but differs in the source of the
 * attribute data. The DefaultErrorAttributes class requires the exception to be thrown from the Controller so that it
 * may gather attribute values from {@link RequestAttributes}. This class uses the {@link Exception},
 * {@link HttpServletRequest}, and {@link HttpStatus} values.
 * <p>
 * Provides a Map of the following attributes when they are available:
 * <ul>
 * <li>timestamp - The time that the exception attributes were processed
 * <li>status - The HTTP status code in the response
 * <li>error - The HTTP status reason text
 * <li>exception - The class name of the Exception
 * <li>message - The Exception message
 * <li>path - The HTTP request servlet path when the exception was thrown
 * </ul>
 * </p>
 *
 * @author Bosko Mijin
 * @see ExceptionAttributes
 */
public class DefaultExceptionAttributes implements ExceptionAttributes {

    /** The timestamp attribute key. */
    public static final String TIMESTAMP = "timestamp";

    /** The status attribute key. */
    public static final String STATUS = "status";

    /** The error attribute key. */
    public static final String ERROR = "error";

    /** The exception attribute key. */
    public static final String EXCEPTION = "exception";

    /** The message attribute key. */
    public static final String MESSAGE = "message";

    /** The path attribute key. */
    public static final String PATH = "path";

    /**
     * Returns a {@link Map} of exception attributes. The Map may be used to display an error page or serialized into a
     * {@link ResponseBody}.
     *
     * @param exception The Exception reported.
     * @param httpRequest The HttpServletRequest in which the Exception occurred.
     * @param httpStatus The HttpStatus value that will be used in the {@link HttpServletResponse}.
     * @return A Map of exception attributes.
     */
    @Override
    public Map<String, Object> getExceptionAttributes(Exception exception, HttpServletRequest httpRequest,
            HttpStatus httpStatus) {

        Map<String, Object> exceptionAttributes = new LinkedHashMap<>();

        exceptionAttributes.put(TIMESTAMP, ZonedDateTime.now());
        addHttpStatus(exceptionAttributes, httpStatus);
        addExceptionDetail(exceptionAttributes, exception);
        addPath(exceptionAttributes, httpRequest);

        return exceptionAttributes;
    }

    /**
     * Adds the status and error attribute values from the {@link HttpStatus} value.
     *
     * @param exceptionAttributes The Map of exception attributes.
     * @param httpStatus The HttpStatus enum value.
     */
    private void addHttpStatus(Map<String, Object> exceptionAttributes, HttpStatus httpStatus) {
        exceptionAttributes.put(STATUS, httpStatus.value());
        exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());
    }

    /**
     * Adds the exception and message attribute values from the {@link Exception}.
     *
     * @param exceptionAttributes The Map of exception attributes.
     * @param exception The Exception object.
     */
    private void addExceptionDetail(Map<String, Object> exceptionAttributes, Exception exception) {
        exceptionAttributes.put(EXCEPTION, exception.getClass().getSimpleName());
        exceptionAttributes.put(MESSAGE, exception.getMessage());
    }

    /**
     * Adds the path attribute value from the {@link HttpServletRequest}.
     *
     * @param exceptionAttributes The Map of exception attributes.
     * @param httpRequest The HttpServletRequest object.
     */
    private void addPath(Map<String, Object> exceptionAttributes, HttpServletRequest httpRequest) {
        exceptionAttributes.put(PATH, httpRequest.getServletPath());
    }

}
