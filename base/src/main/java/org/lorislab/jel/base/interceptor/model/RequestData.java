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
package org.lorislab.jel.base.interceptor.model;

import java.io.Serializable;
import java.util.Stack;

/**
 *
 * @author Andrej Petras
 */
public class RequestData implements Serializable {

    private static final long serialVersionUID = -2411833536548526699L;
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
    private String clientHost;

    /**
     * The current principal.
     */
    private String clientPrincipal;

    /**
     * The result value.
     */
    private String result;

    /**
     * The execution time.
     */
    private long executionTime;

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

    private Stack<String> trace = new Stack<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public String getClientPrincipal() {
        return clientPrincipal;
    }

    public void setClientPrincipal(String clientPrincipal) {
        this.clientPrincipal = clientPrincipal;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Stack<String> getTrace() {
        return trace;
    }

    public void setTrace(Stack<String> trace) {
        this.trace = trace;
    }

    public boolean isTrace() {
        return !trace.isEmpty();
    }

    public String peekTrace() {
        return trace.peek();
    }

    public String popTrace() {
        return trace.pop();
    }

    public void addTrace(String item) {
        trace.push(item);
    }
}
