package ar.com.rollpaper.pricing.ui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import java.awt.Toolkit;

public class SplashScreen extends JWindow {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean isRegistered;
    private static JProgressBar progressBar = new JProgressBar();
    private static SplashScreen execute;
    private static int count;
    private static Timer timer1;

    public SplashScreen() {

        Container container = getContentPane();
        container.setLayout(null);
        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.EtchedBorder());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 10, 528, 308);
        panel.setLayout(null);
        container.add(panel);
       
        WebImage webImage3 = new WebImage (Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource("/images/rpLogo.PNG")) );
        webImage3.setDisplayType(DisplayType.fitComponent);
        webImage3.setImage(Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource("/images/rpLogo.PNG")));
        webImage3.setBounds(0, 0, 525, 312);
        panel.add(webImage3);
      
        progressBar.setMaximum(50);
        progressBar.setBounds(10, 329, 528, 38);
        container.add(progressBar);
        loadProgressBar();
        setSize(548, 378);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadProgressBar() {
        ActionListener al = new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                count++;

                progressBar.setValue(count);

                System.out.println(count);

                if (count == 300) {

                    createFrame();

                    execute.setVisible(false);//swapped this around with timer1.stop()

                    timer1.stop();
                }

            }

            private void createFrame() throws HeadlessException {
                JFrame frame = new JFrame();
                frame.setSize(300, 300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        };
        timer1 = new Timer(10, al);
        timer1.start();
    }

    public static void main(String[] args) {
        execute = new SplashScreen();
    }
};