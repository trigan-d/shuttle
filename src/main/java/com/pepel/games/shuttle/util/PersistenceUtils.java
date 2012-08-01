package com.pepel.games.shuttle.util;

import javax.ejb.Stateless;

import org.hibernate.exception.ConstraintViolationException;

@Stateless
public class PersistenceUtils {
	public static boolean isConstraintViolation(Throwable t) {
		Throwable cause = t;
		while (cause != null) {
			if (cause instanceof ConstraintViolationException) {
				return true;
			} else {
				cause = cause.getCause();
			}
		}
		return false;
	}
}
