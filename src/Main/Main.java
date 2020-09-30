/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Assistant.ClipBoardAssistant;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


/**
 *
 * @author Matt
 */
public class Main {
        public static void main(String[] args) {
        try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

		GlobalScreen.addNativeKeyListener(new ClipBoardAssistant(sysClip));

        
                
                
                
        
        while (true) {
               
        }
 }
        



   
}
