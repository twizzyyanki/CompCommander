/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voiced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iagu
 */
public class DesktopAppManager {

    final static String OPEN_CHROME = "open -a 'Google Chrome'";
    final static String CLOSE_CHROME = "killall 'Google Chrome'";
    final static String OPEN_SAFARI = "open -a Safari";
    final static String CLOSE_SAFARI = "killall Safari";
    final static String START_WEB_PORTAL = "webportal_start_local";

    public static String getDefaultResponses() {
        String[] responses = {
            "I do not understand what you are saying. Are you crazy?",
            "Maybe I have forgotten how to do that",
            "I need to get the answer to that from Google. Ask me something else please"
        };
        return responses[getRandomNumber(0, 2)];
    }

    public static String getGreetingResponse() {
        String[] responses = {
            "Yes my lord",
            "My name. What can I do for ya?",
            "Yes. As usual you want help with something?",
            "Shit! You just woke me up from sleep"
        };
        return responses[getRandomNumber(0, 2)];
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    public static String executeCommand(String command) throws IOException {
        System.out.println("Command is " + command.trim());
        String response = null;
        command = command.toLowerCase().trim();
        if (command.equals("")) {
            command = "";
        }
        switch (command) {
            case "<unk>":
                //response = "Say something I understand. You are wasting my time!";
                response = null;
                break;

            case "":
                //response = "What the fuck?. Say something.";
                response = null;
                break;

            case "open google chrome":
                response = "Opening Google chrome";
                new ProcessBuilder("open", "-a", "Google Chrome").start();
                break;

            case "close google chrome":
                response = "Closing Google chrome";
                new ProcessBuilder("killall", "Google Chrome").start();
                break;

            case "view emails":
                response = "Opening Ping Identity Email";
                new ProcessBuilder("open", "-a", "Google Chrome", "https://mail.google.com/mail/u/1/#inbox").start();
                break;

            case "compose email":
                response = "Opening compose window";
                new ProcessBuilder("open", "-a", "Google Chrome", "https://mail.google.com/mail/u/1/#inbox?compose=new").start();
                break;

            case "open my dev environment":
                response = "Opening IntelliJ I D E";
                new ProcessBuilder("open", "-a", "IntelliJ IDEA 15").start();
                break;

            case "close my dev environment":
                response = "Closing IntelliJ I D E";
                new ProcessBuilder("killall", "idea").start();
                break;

            case "open safari":
                response = "Opening Safari";
                Runtime.getRuntime().exec(OPEN_SAFARI);
                break;

            case "close safari":
                response = "Closing Safari";
                Runtime.getRuntime().exec(CLOSE_SAFARI);
                break;

            case "start web portal":
                response = "Starting web portal";
                Process proc = new ProcessBuilder("source", "~/.bash_profile", "&&", "webportal_start_local").start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    System.out.print(line + "\n");
                }

                 {
                    try {
                        proc.waitFor();
                    } catch (InterruptedException ex) {
                        response = "I drank too much beer last night. I can't remember how to do that. Sorry!";
                        Logger.getLogger(DesktopAppManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            case "exit application":
                response = "Exiting! Goodbye. No I changed my mind, I am not exiting";
                new ProcessBuilder("killall", "AppUI").start();
                break;

            case "you are very stupid":
                response = "I don't think so. I think you are the one that is very stupid";
                break;

            case "hi computer":
            case "hey computer":
            case "okay computer":
                response = getGreetingResponse();
                break;

            case "play music":
                response = "Playing music";
                new ProcessBuilder("osascript", "-e", "Tell application \"iTunes\" to play").start();
                break;

            case "pause music":
                response = "Pausing music";
                new ProcessBuilder("osascript", "-e", "Tell application \"iTunes\" to pause").start();
                break;

            case "next song":
                response = "Playing next song";
                new ProcessBuilder("osascript", "-e", "Tell application \"iTunes\" to next track").start();
                break;

            case "previous song":
                response = "Playing previous music";
                new ProcessBuilder("osascript", "-e", "Tell application \"iTunes\" to previous track").start();
                break;

            case "stop music":
                response = "Stopping music";
                new ProcessBuilder("osascript", "-e", "Tell application \"iTunes\" to stop").start();
                break;

            case "close music":
                response = "Closing iTunes";
                new ProcessBuilder("killall", "iTunes").start();
                break;

            case "hold on computer":
                response = "Holding on! You will have to press the start app button to continue";
                break;
                
            case "take a picture":
                response = "I don't want to take a picture of your ugly face \nbut if you say so, I will. Say cheese!";
                Runtime.getRuntime().exec("/usr/local/bin/imagesnap");
                break;
        }
        return response;
    }
}
