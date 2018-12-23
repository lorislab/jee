/*
 * Copyright 2016 Andrej Petras.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lorislab.jee.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.Principal;
import org.lorislab.jee.annotation.LoggerService;
import org.lorislab.jee.Configuration;

/**
 *
 * @author Andrej Petras
 */
public final class InterceptorUtil {

    private static final String TIME_FORMAT = "%.3f";

    private InterceptorUtil() {
    }

    public static String getPrincipalName(Principal principal) {
//        if (principal != null) {
//            return principal.getName();
//        }
        return Configuration.PATTERN_NO_USER;
    }

    public static String getPrincipalName(String principal) {
        if (principal != null) {
            return principal;
        }
        return Configuration.PATTERN_NO_USER;
    }

    public static LoggerService getLoggerServiceAno(Class<?> clazz, Method method) {
        LoggerService result = new LoggerService() {
            @Override
            public boolean log() {
                return true;
            }

            @Override
            public boolean stacktrace() {
                return true;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return LoggerService.class;
            }
        };
        if (method != null && method.isAnnotationPresent(LoggerService.class)) {
            result = method.getAnnotation(LoggerService.class);
        } else if (clazz != null && clazz.isAnnotationPresent(LoggerService.class)) {
            result = clazz.getAnnotation(LoggerService.class);
        }
        return result;
    }

    public static String intervalToString(long startTime, long endTime) {
        return String.format(TIME_FORMAT, (endTime - startTime) / 1000f);
    }
}
