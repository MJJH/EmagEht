package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SplashScreen extends JWindow {

    private static JProgressBar progressBar = new JProgressBar();
    private static SplashScreen splashScreen;
    private static int count = 1, TIMER_PAUSE = 25,PROGBAR_MAX=100;
    private static Timer progressBarTimer;
    private int counttill;
    
    ActionListener al = new ActionListener() {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            progressBar.setValue(count);
            if(counttill == count)
            {
                progressBarTimer.stop();
                
            }
            
            if (PROGBAR_MAX == count) {
                splashScreen.dispose();
                progressBarTimer.stop();
            }
            count++;

        }
    };

    public void SplashScreen() {
        createSplash();
    }

    private void createSplash() {
        Container container = getContentPane();
        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.EtchedBorder());
        container.add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("Loading TheGame:");
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        panel.add(label);

        progressBar.setMaximum(PROGBAR_MAX);
        container.add(progressBar, BorderLayout.SOUTH);

        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startProgressBar();
    }

    private void startProgressBar() {
        progressBarTimer = new Timer(TIMER_PAUSE, al);
        
    }

   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                splashScreen = new SplashScreen();
            }
        });
    }
    
    public void countTill(int count)
    {
        this.counttill = count;
        progressBarTimer.start();
    }
    
    public int returnCount()
    {
       return count;
    }

   

    public void giveSplash(SplashScreen splash) {
        splashScreen = splash;
    }
}