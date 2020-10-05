/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assistant;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Matt
 */
public class ApplicationsAssistant {

    LinkedList<String> applicationTracker;
    Stack<String> previousApplications;

    public ApplicationsAssistant() throws InterruptedException, IOException {
        this.applicationTracker = BuildList();
        this.previousApplications = new Stack<String>();
    }

    public void checkApplications() throws InterruptedException, IOException {
        LinkedList<String> tempList = applicationTracker;
        LinkedList<String> newApps = new LinkedList<String>();
        Process process = new ProcessBuilder("powershell", "\"gps| ? {$_.mainwindowtitle.length -ne 0} | Format-Table -HideTableHeaders  Path").start();
        new Thread(() -> {
            Scanner sc = new Scanner(process.getInputStream());
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                newApps.add(line);
                if (tempList.contains(line)) {
                    tempList.remove(line);
                }
            }
        }).start();
        process.waitFor();
        applicationTracker = newApps;
        if (tempList.size() > 0) {
            for (String item : tempList) {
                previousApplications.add(item);
            }
        }
    }

    public LinkedList<String> BuildList() throws InterruptedException, IOException {
        LinkedList<String> Applications = new LinkedList<String>();
        Process process = new ProcessBuilder("powershell", "\"gps| ? {$_.mainwindowtitle.length -ne 0} | Format-Table -HideTableHeaders  Path").start();
        new Thread(() -> {
            Scanner sc = new Scanner(process.getInputStream());
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
                Applications.add(line);
            }
        }).start();
        process.waitFor();
        return Applications;
    }

    public void undoAppClose() throws IOException {
        if (previousApplications.empty()) {
            return;
        } else {
            String exe = previousApplications.pop();
            if(null == exe || exe.length() <= 0){
                return;
            }
            else {
                int endIndex = exe.lastIndexOf("\\");
                if (endIndex != -1) {
                    String dir = exe.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
                    Runtime.getRuntime().exec(exe, null, new File(dir));
                }
            }
            
        }
    }
}
