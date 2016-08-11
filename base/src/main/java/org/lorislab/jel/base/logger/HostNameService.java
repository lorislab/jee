package org.lorislab.jel.base.logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The default host name service.
 *
 * @author Andrej_Petras
 */
public final class HostNameService {

    public static final String PROPERTY_HOSTNAME = "org.lorislab.jel.base.host";

    private static final String HOST_NAME;

    /**
     * The default constructor
     */
    static {
        String tmp;

        tmp = System.getProperty(PROPERTY_HOSTNAME, null);
        if (tmp == null || tmp.isEmpty()) {
            tmp = System.getProperty("jboss.server.name", null);
        }
        if (tmp == null || tmp.isEmpty()) {
            try {
                tmp = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
            }
        }
        HOST_NAME = tmp;
    }

    public static String getHostName() {
        return HOST_NAME;
    }

    private HostNameService() {
    }
    
    
}
