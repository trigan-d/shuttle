package com.pepel.games.shuttle.web.security.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.jboss.seam.security.annotations.SecurityBindingType;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SecurityBindingType
@Target({ TYPE, METHOD, FIELD })
@Retention(RUNTIME)
public @interface NotLoggedIn {
}
