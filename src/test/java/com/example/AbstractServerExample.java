package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hua on 16/1/27.
 */
public class AbstractServerExample {
    public static final int NOT_STARTED_PORT = -1;

    private static final Map<String, Integer> serverPorts = new ConcurrentHashMap<>();


    public static boolean isServerStarted(Class<? extends AbstractServerExample> serverClass) {
        Integer serverPort = serverPorts.get(serverClass.getName());
        return null != serverPort;
    }

    protected static boolean shouldWaitForShutdown(String[] args) {
        return args.length == 0;
    }

    public static int getServerPort(Class<? extends AbstractServerExample> serverClass) {
        if (isServerStarted(serverClass)) {
            return serverPorts.get(serverClass.getName());
        }

        return NOT_STARTED_PORT;
    }

    protected static void setServerPort(int serverPort) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            final String exampleClassName = stackTrace[2].getClassName();
            serverPorts.put(exampleClassName, serverPort);
        }
    }
}
