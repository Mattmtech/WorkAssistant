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
import java.awt.datatransfer.StringSelection;
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

    LinkedList<String> clipTracker;
    Clipboard sysClip;
    int ref;
    ApplicationsAssistant appAssist;

    public ClipBoardAssistant(Clipboard sysClip, ApplicationsAssistant appAssist) {
        this.sysClip = sysClip;
        this.ref = -1;
        this.clipTracker = new LinkedList<String>();
        this.appAssist = appAssist;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClipBoardAssistant.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_C
                && NativeKeyEvent.getModifiersText(e.getModifiers()).equals(
                        "Ctrl")) {
            try {
                clipTracker.add(sysClip.getData(DataFlavor.stringFlavor).toString());
                ref++;
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(ClipBoardAssistant.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClipBoardAssistant.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_V
                && NativeKeyEvent.getModifiersText(e.getModifiers()).equals(
                        "Ctrl") && clipTracker.size() > 0) {
            try {
                sysClip.setContents(new StringSelection(clipTracker.get(ref)), new StringSelection(clipTracker.get(ref)));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_UP
                && NativeKeyEvent.getModifiersText(e.getModifiers()).equals(
                        "Ctrl") && clipTracker.size() > 0 && ref > -1) {
            if (ref > 0) {
                ref = ref - 1;
            }
            sysClip.setContents(new StringSelection(clipTracker.get(ref)), new StringSelection(clipTracker.get(ref)));
        } else if (e.getKeyCode() == NativeKeyEvent.VC_DOWN
                && NativeKeyEvent.getModifiersText(e.getModifiers()).equals(
                        "Ctrl") && clipTracker.size() - 1 > ref) {
            if (ref < clipTracker.size() - 1) {
                ref++;
            }
            sysClip.setContents(new StringSelection(clipTracker.get(ref)), new StringSelection(clipTracker.get(ref)));
        } else if (e.getKeyCode() == NativeKeyEvent.VC_SPACE
                && NativeKeyEvent.getModifiersText(e.getModifiers()).equals(
                        "Ctrl")) {
            try {
                appAssist.undoAppClose();
            } catch (IOException ex) {
                Logger.getLogger(ClipBoardAssistant.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {

    }

}
