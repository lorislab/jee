/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lorislab.jel.rs.client.cdi;

import org.lorislab.jel.rs.client.ClientConfig;

/**
 *
 * @author Andrej Petras
 */
public interface RestClientConfigService {
    
    public ClientConfig getClientConfig(Class clazz, String value);
}
