/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Assistant.ApplicationsAssistant;
import Assistant.ClipBoardAssistant;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Matt
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        LogManager.getLogManager().reset();

// Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            
            System.exit(1);
        }
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        ApplicationsAssistant appAssist = new ApplicationsAssistant();
        GlobalScreen.addNativeKeyListener(new ClipBoardAssistant(sysClip, appAssist));
        
        System.out.println("Done");
        
        while (true) {
            appAssist.checkApplications();
            Thread.sleep(20);
        }
    }
    
}
