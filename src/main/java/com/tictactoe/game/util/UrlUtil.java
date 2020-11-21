package com.tictactoe.game.util;

import javax.servlet.http.HttpServletRequest;

/**
 * The <code>UrlUtil</code> class contains all the functionalities related to the url handling.
 *
 * @author Bosko Mijin
 */
public final class UrlUtil {

    /**
     * The <code>UrlUtil</code> no-args private constructor - prevents instantiation.
     */
    private UrlUtil() {

    }

    /**
     * The <code>prepareBaseUrl</code> method prepares the base url according to request.
     *
     * @param request - The request from which url has to be prepared.
     * @return baseUrl - The prepared based url.
     */
    public static String prepareBaseUrl(HttpServletRequest request) {
        return String.format("%s://%s:%s%s", request.getScheme(), request.getServerName(), request.getServerPort(),
                request.getContextPath());
    }
}
