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
package org.lorislab.jel.rs.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.TypeVariable;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.lorislab.jel.base.exception.ServiceException;
import org.lorislab.jel.rs.client.resources.ClientServiceErrors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class that creates REST proxy object for type safe RS client.
 *
 */
public final class ClientServiceProxyFactory implements InvocationHandler {

    private static final String[] EMPTY = {};

    private static Logger log = LoggerFactory.getLogger(ClientServiceProxyFactory.class);

    private final Class<?> resourceInterface;
    private final WebTarget target;
    private final MultivaluedMap<String, Object> headers;
    private final List<Cookie> cookies;
    private final Form form;

    private static final MultivaluedMap<String, Object> EMPTY_HEADERS = new MultivaluedHashMap<>();
    private static final Form EMPTY_FORM = new Form();
    private static final List<Class> PARAM_ANNOTATION_CLASSES = Arrays.<Class> asList(PathParam.class, QueryParam.class, HeaderParam.class, CookieParam.class, MatrixParam.class, FormParam.class);

    public static <C> C newResource(final Class<C> resourceInterface, final WebTarget target) {
        return newResource(resourceInterface, target, false, EMPTY_HEADERS, Collections.<Cookie> emptyList(), EMPTY_FORM);
    }

    @SuppressWarnings("unchecked")
    public static <C> C newResource(final Class<C> resourceInterface, final WebTarget target, final boolean ignoreResourcePath, final MultivaluedMap<String, Object> headers, final List<Cookie> cookies, final Form form) {
        return (C) Proxy.newProxyInstance(resourceInterface.getClassLoader(), new Class[] { resourceInterface }, new ClientServiceProxyFactory(resourceInterface, ignoreResourcePath ? target : addPathFromAnnotation(resourceInterface, target), headers, cookies, form));
    }

    private ClientServiceProxyFactory(final Class<?> resourceInterface, final WebTarget target, final MultivaluedMap<String, Object> headers, final List<Cookie> cookies, final Form form) {
        this.resourceInterface = resourceInterface;
        this.target = target;
        this.headers = headers;
        this.cookies = cookies;
        this.form = form;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (args == null && method.getName().equals("toString")) {
            return toString();
        }

        // response type
        final Class<?> responseType = method.getReturnType();

        // determine method name
        String httpMethod = getHttpMethodName(method);
        if (httpMethod == null) {
            for (final Annotation ann : method.getAnnotations()) {
                httpMethod = getHttpMethodName(ann.annotationType());
                if (httpMethod != null) {
                    break;
                }
            }
        }

        // create a new UriBuilder appending the @Path attached to the method
        WebTarget newTarget = addPathFromAnnotation(method, target);

        if (httpMethod == null) {
            if (newTarget == target) {
                // no path annotation on the method -> fail
                throw new UnsupportedOperationException("Not a resource method.");
            } else if (!responseType.isInterface()) {
                // the method is a subresource locator, but returns class,
                // not interface - can't help here
                throw new UnsupportedOperationException("Return type not an interface");
            }
        }

        // process method params (build maps of (Path|Form|Cookie|Matrix|Header..)Params
        // and extract entity type
        final MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>(this.headers);
        final LinkedList<Cookie> cookies = new LinkedList<>(this.cookies);
        final Form form = new Form();
        form.asMap().putAll(this.form.asMap());
        final Annotation[][] paramAnns = method.getParameterAnnotations();
        Object entity = null;
        Type entityType = null;
        for (int i = 0; i < paramAnns.length; i++) {
            final Map<Class<?>, Annotation> anns = new HashMap<>();
            for (final Annotation ann : paramAnns[i]) {
                anns.put(ann.annotationType(), ann);
            }
            Annotation ann;
            Object value = args[i];
            if (!hasAnyParamAnnotation(anns)) {
                entityType = method.getGenericParameterTypes()[i];
                entity = value;
            } else {
                if (value == null && (ann = anns.get(DefaultValue.class)) != null) {
                    value = ((DefaultValue) ann).value();
                }

                if (value != null) {
                    if ((ann = anns.get(PathParam.class)) != null) {
                        newTarget = newTarget.resolveTemplate(((PathParam) ann).value(), value);
                    } else if ((ann = anns.get((QueryParam.class))) != null) {
                        if (value instanceof Collection) {
                            newTarget = newTarget.queryParam(((QueryParam) ann).value(), convert((Collection<?>) value));
                        } else {
                            newTarget = newTarget.queryParam(((QueryParam) ann).value(), value);
                        }
                    } else if ((ann = anns.get((HeaderParam.class))) != null) {
                        if (value instanceof Collection) {
                            headers.addAll(((HeaderParam) ann).value(), convert((Collection<?>) value));
                        } else {
                            headers.addAll(((HeaderParam) ann).value(), value);
                        }

                    } else if ((ann = anns.get((CookieParam.class))) != null) {
                        final String name = ((CookieParam) ann).value();
                        Cookie c;
                        if (value instanceof Collection) {
                            for (final Object v : ((Collection) value)) {
                                if (!(v instanceof Cookie)) {
                                    c = new Cookie(name, v.toString());
                                } else {
                                    c = (Cookie) v;
                                    if (!name.equals(((Cookie) v).getName())) {
                                        // is this the right thing to do? or should I fail? or ignore the difference?
                                        c = new Cookie(name, c.getValue(), c.getPath(), c.getDomain(), c.getVersion());
                                    }
                                }
                                cookies.add(c);
                            }
                        } else if (!(value instanceof Cookie)) {
                            cookies.add(new Cookie(name, value.toString()));
                        } else {
                            c = (Cookie) value;
                            if (!name.equals(((Cookie) value).getName())) {
                                // is this the right thing to do? or should I fail? or ignore the difference?
                                cookies.add(new Cookie(name, c.getValue(), c.getPath(), c.getDomain(), c.getVersion()));
                            }
                        }
                    } else if ((ann = anns.get((MatrixParam.class))) != null) {
                        if (value instanceof Collection) {
                            newTarget = newTarget.matrixParam(((MatrixParam) ann).value(), convert((Collection) value));
                        } else {
                            newTarget = newTarget.matrixParam(((MatrixParam) ann).value(), value);
                        }
                    } else if ((ann = anns.get((FormParam.class))) != null) {
                        if (value instanceof Collection) {
                            for (final Object v : ((Collection) value)) {
                                form.param(((FormParam) ann).value(), v.toString());
                            }
                        } else {
                            form.param(((FormParam) ann).value(), value.toString());
                        }
                    }
                }
            }
        }

        if (httpMethod == null) {
            return ClientServiceProxyFactory.newResource(responseType, newTarget, true, headers, cookies, form);
        }

        // accepted media types
        Produces produces = method.getAnnotation(Produces.class);
        if (produces == null) {
            produces = resourceInterface.getAnnotation(Produces.class);
        }
        final String[] accepts = (produces == null) ? EMPTY : produces.value();

        // determine content type
        String contentType = null;
        if (entity != null) {
            final List<Object> contentTypeEntries = headers.get(HttpHeaders.CONTENT_TYPE);
            if ((contentTypeEntries != null) && (!contentTypeEntries.isEmpty())) {
                contentType = contentTypeEntries.get(0).toString();
            } else {
                Consumes consumes = method.getAnnotation(Consumes.class);
                if (consumes == null) {
                    consumes = resourceInterface.getAnnotation(Consumes.class);
                }
                if (consumes != null && consumes.value().length > 0) {
                    contentType = consumes.value()[0];
                }
            }
        }

        Invocation.Builder builder = newTarget.request().headers(headers) // this resets all headers so do this first
                .accept(accepts); // if @Produces is defined, propagate values into Accept header; empty array is NO-OP

        for (final Cookie c : cookies) {
            builder = builder.cookie(c);
        }

        final Object result;

        if (entity == null && !form.asMap().isEmpty()) {
            entity = form;
            contentType = MediaType.APPLICATION_FORM_URLENCODED;
        } else {
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM;
            }
            if (!form.asMap().isEmpty()) {
                if (entity instanceof Form) {
                    ((Form) entity).asMap().putAll(form.asMap());
                } else {
                    // TODO: should at least log some warning here
                }
            }
        }

        Type type = method.getGenericReturnType();
        Type actualReturnType =findActualReturnType(type);
        
        try {       
            final GenericType responseGenericType = new GenericType(actualReturnType);  
            if (entity != null) {
                if (entityType instanceof ParameterizedType) {
                    entity = new GenericEntity(entity, entityType);
                }
                result = builder.method(httpMethod, Entity.entity(entity, contentType), responseGenericType);
            } else {
                result = builder.method(httpMethod, responseGenericType);
            }
            return result;
        } catch (WebApplicationException wae) {
            if (isServiceExceptionDeclared(method)) {
                log.info("catched WebApplicationException, morphing to ServiceException");
                throw new ServiceException(ClientServiceErrors.ERROR_CLIENT_UNDEFINED, wae);
            }
            throw wae;
        } catch (ResponseProcessingException rpe) {
            if (isServiceExceptionDeclared(method)) {
                log.info("catched ResponseProcessingException, morphing to ServiceException");
                throw new ServiceException(ClientServiceErrors.ERROR_CLIENT_UNDEFINED, rpe);
            }
            throw rpe;
        } catch (ProcessingException pe) {
            if (isServiceExceptionDeclared(method)) {
                log.info("catched ProcessingException, morphing to ServiceException");
                throw new ServiceException(ClientServiceErrors.ERROR_CLIENT_UNDEFINED, pe);
            }
            throw pe;
        } catch (Exception e) {
            if (isServiceExceptionDeclared(method)) {
                log.info("catched Exception, morphing to ServiceException");
                throw new ServiceException(ClientServiceErrors.ERROR_CLIENT_UNDEFINED, e);
            }
            throw e;
        }

    }
    
    private Type findActualReturnType(Type type){
        Type tempArgumentType=null;
        Type tempRawType=null;
        Type tempOwnerType=null;
        if (type instanceof TypeVariable) {
            // Is type generic T?
            Type[] genericInterfaces = resourceInterface.getGenericInterfaces();
            if (genericInterfaces != null && genericInterfaces.length > 0) {
                Type interf = genericInterfaces[0];
                if (interf instanceof ParameterizedType) {
                    // Find class that is used as T
                    type = ((ParameterizedType) interf).getActualTypeArguments()[0];
                    return type;
                }
            }
        }else if (type instanceof ParameterizedType) {
            // Is type a class that uses generic like List<T>?
            Type[] genericInterfaces = resourceInterface.getGenericInterfaces();
            if (genericInterfaces != null && genericInterfaces.length > 0) {
                Type interf = genericInterfaces[0];
                if (interf instanceof ParameterizedType) {
                    // Assign old raw and owner types and add actual class used for T
                    tempArgumentType = ((ParameterizedType) interf).getActualTypeArguments()[0];
                    tempRawType = ((ParameterizedType) type).getRawType();
                    tempOwnerType = ((ParameterizedType) type).getOwnerType();
                }
            }
        }else {
            // If type doesn't use generics, we can return it unmodified
            return type;
        }
        final Type rawType = tempRawType;
        final Type argumentType=tempArgumentType;
        final Type ownerType = tempOwnerType;
        // Generics are compiled at runtime, so we need to create a new anonymous class instead
        ParameterizedType paremeterizedResponseType=new ParameterizedType() {
            public Type getRawType() {
                return rawType;
            }
            
            public Type getOwnerType() {
                return ownerType;
            }
            
            public Type[] getActualTypeArguments() {
                return new Type[]{argumentType};
            }
        };
        return paremeterizedResponseType;
        
    }

    private boolean isServiceExceptionDeclared(Method method) {
        for (Class<?> exc : method.getExceptionTypes()) {
            if (ServiceException.class.isAssignableFrom(exc)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAnyParamAnnotation(final Map<Class<?>, Annotation> anns) {
        for (final Class<?> paramAnnotationClass : PARAM_ANNOTATION_CLASSES) {
            if (anns.containsKey(paramAnnotationClass)) {
                return true;
            }
        }
        return false;
    }

    private Object[] convert(final Collection<?> value) {
        return value.toArray();
    }

    private static WebTarget addPathFromAnnotation(final AnnotatedElement ae, WebTarget target) {
        final Path p = ae.getAnnotation(Path.class);
        if (p != null) {
            target = target.path(p.value());
        }
        return target;
    }

    @Override
    public String toString() {
        return target.toString();
    }

    private static String getHttpMethodName(final AnnotatedElement ae) {
        final HttpMethod a = ae.getAnnotation(HttpMethod.class);
        return a == null ? null : a.value();
    }
}
