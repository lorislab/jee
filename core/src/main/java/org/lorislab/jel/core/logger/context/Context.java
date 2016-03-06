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
package org.lorislab.jel.core.logger.context;

import java.util.List;

/**
 *
 * @author Andrej Petras
 */
public class Context {
        
  /**
     * The request id.
     */
    private String id;

    /**
     * The principal.
     */
    private String principal;

    /**
     * The client.
     */
    private String client;

    /**
     * The host.
     */
    private String host;

    /**
     * The current principal.
     */
    private String currentPrincipal;

    /**
     * The service class.
     */
    private String clazz;

    /**
     * The service method.
     */
    private String method;

    /**
     * The list of method parameters.
     */
    private List<String> parameters;

    /**
     * The result value.
     */
    private String result;

    /**
     * The execution time.
     */
    private double time;

    /**
     * The error message.
     */
    private String error;

    /**
     * The error code.
     */
    private String code;

    /**
     * The start time.
     */
    private long startTime;

    /**
     * The remote flag.
     */
    private boolean remote;

    /**
     * Gets the code.
     *
     * @return the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param code the code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the remote flag.
     *
     * @param remote the remote flag.
     */
    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    /**
     * Gets the remote flag.
     *
     * @return the remote flag.
     */
    public boolean isRemote() {
        return remote;
    }

    /**
     * Gets the client.
     *
     * @return the client.
     */
    public String getClient() {
        return client;
    }

    /**
     * Sets the client.
     *
     * @param client the client.
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Gets the start time.
     *
     * @return the start time.
     */
    public long getStartTime() {
        return startTime;
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
     * Gets the error message.
     *
     * @return the error message.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error message.
     *
     * @param error the error message.
     */
    public void setError(String error) {
        this.error = error;
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
     * Sets the execution time.
     *
     * @param time the execution time.
     */
    public void setTime(double time) {
        this.time = time;
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
     * Gets the host id.
     *
     * @return the host id.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host id.
     *
     * @param host the host id to set.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets the service class name.
     *
     * @return the service class name.
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the service class name.
     *
     * @param clazz the class name to set.
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
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
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Sets the list of method parameters.
     *
     * @param parameters the list of method parameters.
     */
    public void setParameters(List<String> parameters) {
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

    /**
     * Gets the current principal.
     *
     * @return the current principal.
     */
    public String getCurrentPrincipal() {
        return currentPrincipal;
    }

    /**
     * Sets the current principal.
     *
     * @param currentPrincipal the current principal to set.
     */
    public void setCurrentPrincipal(String currentPrincipal) {
        this.currentPrincipal = currentPrincipal;
    }


    
}
