/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assistant;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Matt
 */
public class ClipBoardAssistant implements NativeKeyListener {
    
      static final LinkedList<String> clipTracker = new LinkedList<String>();
      Clipboard sysClip;
      
      
public ClipBoardAssistant (Clipboard sysClip){
    this.sysClip = sysClip;
}

	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

	if (e.getKeyCode() == NativeKeyEvent.VC_C
            && NativeKeyEvent.getModifiersText(e.getModifiers()).equals(
                    "Ctrl")) {
                    try {
                        clipTracker.add(sysClip.getData(DataFlavor.stringFlavor).toString());
                        System.out.println(sysClip.getData(DataFlavor.stringFlavor).toString());
                    } catch (UnsupportedFlavorException ex) {
                        Logger.getLogger(ClipBoardAssistant.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ClipBoardAssistant.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
	}

  }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    



}

