/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lorislab.jel.rs.client.annotation;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import org.lorislab.jel.base.interceptor.annotation.LoggerService;

/**
 *
 * @author Andrej Petras
 */
@Qualifier
@Retention(RUNTIME)
@Target({FIELD})
@LoggerService
public @interface RestClient {
    
    String value() default "";
}
