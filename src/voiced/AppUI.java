package voiced;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import javax.swing.JTextArea;

/**
 *
 * @author iagu
 */
public class AppUI extends javax.swing.JFrame {

    final private LiveSpeechRecognizer recognizer;
    private boolean stopListeningToCommands;

    /**
     * Creates new form AppUI
     */
    public AppUI() {
        this.stopListeningToCommands = false;
        initComponents();
        recognizer = initializeRecognizer();
        startAppButtonActionPerformed();
    }

    private LiveSpeechRecognizer initializeRecognizer() {

        stopAppButton.setEnabled(false);

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        //configuration.setDictionaryPath("file:///Users/iagu/NetBeansProjects/Voiced/0958.dic");

        configuration.setGrammarPath("file:///Users/iagu/NetBeansProjects/Voiced/");
        configuration.setLanguageModelPath("file:///Users/iagu/NetBeansProjects/Voiced/langmodel.lm");
        configuration.setUseGrammar(true);
        configuration.setGrammarName("hello");

        System.out.println("The sample rate is " + configuration.getSampleRate());

        try {
            return new LiveSpeechRecognizer(configuration);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public JTextArea getYourCommandText() {
        return yourCommandText;
    }

    public void setYourCommandText(String word) {
        this.yourCommandText.setText(word);
        System.out.println("Text area set");
    }

    public JTextArea getComputerResponseText() {
        return computerResponseText;
    }
    
    public void setComputerResponseText(String response) {
        computerResponseText.setText(response);
    }

    public void getWords(final LiveSpeechRecognizer recognizer) {
        if (recognizer != null) {
            new Thread(() -> {
                while (true) {
                    String words = recognizer.getResult().getHypothesis();
                    if (words != null) {
                        
                        handleCommand(words);
                    
                        if (words.endsWith("hold on computer")) {
                            handleCommand("hold on computer");
                            stopApp(recognizer);
                            clearTextFields();
                            break;
                        }
                    }
                    words = null;
                }
            }).start();
        }
    }
    
    private void handleCommand(String words) {
        setCommandTextBox(words);
        String response = executeCommand(words);
        setComputerResponseText(response);
        respondToCommand(response);
    }

    public void startApp(LiveSpeechRecognizer recognizer) {
        stopListeningToCommands = false;
        if (recognizer != null) {
            recognizer.startRecognition(true);
            clearTextFields();
        }
        stopAppButton.setEnabled(true);
        startAppButton.setEnabled(false); 
    }

    public void stopApp(LiveSpeechRecognizer recognizer) {
        stopListeningToCommands = true;
        if (recognizer != null) {
            recognizer.stopRecognition();
            clearTextFields();
        }
        stopAppButton.setEnabled(false);
        startAppButton.setEnabled(true);   
    }

    public String executeCommand(String command) {
        try {
            return DesktopAppManager.executeCommand(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "I did not understand your command.\nPerharps, I drank too much beer last night";
        }
    }

    public void setCommandTextBox(String words) {
        if (words.trim().equals("") || words.equals("<unk>")) {
            setYourCommandText("");
        } else {
            setYourCommandText(words);
        }
    }

    public void respondToCommand(String response) {
        if (response != null) {
            FreeTTS freeTTS = new FreeTTS(response);
            freeTTS.speak();
        }
    }
    
    private void clearTextFields() {
        setYourCommandText(null);
        setComputerResponseText(null);
    }

    private void startAppButtonActionPerformed() {
        // TODO add your handling code here:
        System.out.println("Button pressed");

        startAppButton.setEnabled(false);
        stopAppButton.setEnabled(true);
        startApp(recognizer);
        getWords(recognizer);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        appHeader = new javax.swing.JLabel();
        startAppButton = new javax.swing.JButton();
        stopAppButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        yourCommandText = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        computerResponseText = new javax.swing.JTextArea();
        yourCommandTextAreaLabel = new javax.swing.JLabel();
        computerResponseTextAreaLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        appHeader.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        appHeader.setText("IFEANYI'S MAC COMMANDER");

        startAppButton.setText("Start App");
        startAppButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startAppButtonActionPerformed(evt);
            }
        });

        stopAppButton.setText("Stop App");
        stopAppButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopAppButtonActionPerformed(evt);
            }
        });

        yourCommandText.setColumns(20);
        yourCommandText.setRows(5);
        jScrollPane1.setViewportView(yourCommandText);

        computerResponseText.setColumns(20);
        computerResponseText.setRows(5);
        jScrollPane2.setViewportView(computerResponseText);

        yourCommandTextAreaLabel.setText("Your command");

        computerResponseTextAreaLabel.setText("My Response");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(computerResponseTextAreaLabel)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(yourCommandTextAreaLabel)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(startAppButton)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(stopAppButton)))
                                        .addGap(118, 118, 118))))
                .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(appHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(appHeader)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(startAppButton)
                                .addComponent(stopAppButton))
                        .addGap(7, 7, 7)
                        .addComponent(yourCommandTextAreaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(computerResponseTextAreaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(63, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void startAppButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.out.println("Button pressed");
        new Thread(() -> {
            startAppButton.setEnabled(false);
            stopAppButton.setEnabled(true);
            startApp(recognizer);
            getWords(recognizer);
        }).start();
    }

    private void stopAppButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.out.println("Stop button pressed");
        stopApp(recognizer);
        stopAppButton.setEnabled(false);
        startAppButton.setEnabled(true);
        yourCommandText.setText(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AppUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel appHeader;
    private javax.swing.JTextArea computerResponseText;
    private javax.swing.JLabel computerResponseTextAreaLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton startAppButton;
    private javax.swing.JButton stopAppButton;
    private javax.swing.JTextArea yourCommandText;
    private javax.swing.JLabel yourCommandTextAreaLabel;
    // End of variables declaration                   
}