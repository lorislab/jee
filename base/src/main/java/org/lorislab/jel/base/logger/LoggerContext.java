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
package org.lorislab.jel.base.logger;

/**
 *
 * @author Andrej Petras
 */
public class LoggerContext {

    private static final String TIME_FORMAT = "%.3f";

    /**
     * The request id.
     */
    private String id;

    /**
     * The principal.
     */
    private String principal;

    /**
     * The service method.
     */
    private String method;

    /**
     * The list of method parameters.
     */
    private String parameters;

    /**
     * The result value.
     */
    private String result;

    /**
     * The execution time.
     */
    private double time;

    /**
     * The start time.
     */
    private long startTime;

    public LoggerContext(String id, String principal, String method, String parameters) {
        this.id = id;
        this.principal = principal;
        this.method = method;
        this.parameters = parameters;
    }

    /**
     * Sets the start time.
     *
     * @param startTime the start time.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the execution time.
     *
     * @return the execution time.
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets the end time.
     *
     * @param endTime the execution time.
     */
    public void setEndTime(long endTime) {
        this.time = interval(startTime, endTime);
    }

    /**
     * Gets the request id.
     *
     * @return the request id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the request id.
     *
     * @param id the request id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the service method.
     *
     * @return the name of the service method.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the name of the service method.
     *
     * @param method the name of the service method to set.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the list of method parameters.
     *
     * @return the list of method parameters.
     */
    public String getParameters() {
        return parameters;
    }

    /**
     * Sets the list of method parameters.
     *
     * @param parameters the list of method parameters.
     */
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets the result.
     *
     * @return the result.
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the method result.
     *
     * @param result the result of the method to set.
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Gets the principal.
     *
     * @return the principal.
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * Sets the principal.
     *
     * @param principal the principal to set.
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Object[] getStartParams() {
        return new Object[]{this.principal, this.method, parameters};
    }

    public Object[] getSuccessParams() {
        return new Object[]{this.principal, this.method, parameters, this.result, intervalString(time)};
    }

    public Object[] getFailedParams() {
        return new Object[]{this.principal, this.method, parameters, this.result, intervalString(time)};
    }

    public static String intervalString(double time) {
        return String.format(TIME_FORMAT, time);
    }
    
    public static String intervalString(long startTime, long endTime) {
        return String.format(TIME_FORMAT, interval(startTime, endTime));
    }
    
    public static double interval(long startTime, long endTime) {
        return (endTime - startTime) / 1000f;
    }
}
