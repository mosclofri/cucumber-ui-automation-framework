package com.support.framework.support;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.support.framework.support.Property.DEVICE_NAME;

public class CmdLine {

    public static String executeShellReturnStringResult(String command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        org.apache.commons.exec.CommandLine commandline = org.apache.commons.exec.CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        try {
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            return outputStream.toString("UTF-8").substring(0, outputStream.toString().length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAndroidSDKVersion() {
        return executeShellReturnStringResult("adb -s " + DEVICE_NAME.toString() + " shell getprop ro.build.version.sdk");
    }

}
