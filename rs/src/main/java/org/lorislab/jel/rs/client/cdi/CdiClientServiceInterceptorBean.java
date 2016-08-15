/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lorislab.jel.rs.client.cdi;

import javax.enterprise.context.ApplicationScoped;
import org.lorislab.jel.base.interceptor.CdiServiceInterceptor;
import org.lorislab.jel.base.interceptor.annotation.LoggerService;

/**
 *
 * @author Andrej Petras
 */
@ApplicationScoped
@LoggerService(log = false)
public class CdiClientServiceInterceptorBean extends CdiServiceInterceptor {
    
    private static final long serialVersionUID = 6549277374117582127L;
    
    
}
