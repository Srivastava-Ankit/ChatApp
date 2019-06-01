package com.assignment.chatapp.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by ravindra on 14,May,2018
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
