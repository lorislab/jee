package org.lorislab.jee.logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import org.lorislab.jee.cdi.util.JelConfig;

/**
 * The default host name service.
 *
 * @author Andrej_Petras
 */
public final class HostNameService {

    private static final String HOST_NAME;

    /**
     * The default constructor
     */
    static {
        String tmp;
        Properties prop = JelConfig.loadConfig();
        tmp = prop.getProperty("org.lorislab.jel.base.host", null);
        if (tmp == null || tmp.isEmpty()) {
            tmp = prop.getProperty("jboss.server.name", null);
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
