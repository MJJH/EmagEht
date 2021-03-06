package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SplashScreen extends JWindow {

    private JProgressBar progressBar = new JProgressBar();
    private int count = 1, TIMER_PAUSE = 1, PROGBAR_MAX = 100;
    private Timer progressBarTimer;
    private int counttill;

    ActionListener al = new ActionListener() {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            progressBar.setValue(count);
            if (counttill == count)
            {
                progressBarTimer.stop();

            }

            if (PROGBAR_MAX == count)
            {
                dispose();
                progressBarTimer.stop();
            }
            count++;

        }
    };

    public void SplashScreen()
    {
        createSplash();
    }

    private void createSplash()
    {
        Container container = getContentPane();
        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.EtchedBorder());
        container.add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("Hier komt straks de tutorial!");
        label.setFont(new Font("Verdana", Font.BOLD, 11));
        panel.add(label);

        progressBar.setMaximum(PROGBAR_MAX);
        container.add(progressBar, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startProgressBar();
    }

    private void startProgressBar()
    {
        progressBarTimer = new Timer(TIMER_PAUSE, al);

    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run()
            {
                new SplashScreen();
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
}
