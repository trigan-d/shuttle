package com.pepel.games.shuttle.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
	public static final String COOKIE_PARAM_MAXAGE = "maxAge";
	public static final String COOKIE_PARAM_PATH = "path";

	public static String getCookieValue(ServletRequest request, String name) {
		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static Map<String, Object> cookiePropertiesWithMaxAge(int maxAge) {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(COOKIE_PARAM_MAXAGE, (Object) maxAge);
		props.put(COOKIE_PARAM_PATH, "/");
		return props;
	}

	public static void removeCookie(ExternalContext ctx, String name) {
		ctx.addResponseCookie(name, null, cookiePropertiesWithMaxAge(0));
	}
}
