import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class TestFrame extends JFrame {

    private JPanel contentPane;
    private final Timer timer;
    private TimerTask[] tasks;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TestFrame frame = new TestFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TestFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        final JLabel lblCountdown = new JLabel();
        contentPane.add(lblCountdown, BorderLayout.NORTH);
        JButton btnStart = new JButton("Start");
        contentPane.add(btnStart, BorderLayout.SOUTH);

        timer = new Timer();
        tasks = new TimerTask[4];

        setContentPane(contentPane);

        for (int i = 0; i < 4; i++) {
            final int count = i;
            tasks[i] = new TimerTask() {
                public void run() {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            lblCountdown.setText(count + "");
                        }
                    });
                }
            };
        }

        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < 4; i++) {
                    timer.schedule(tasks[4 - i - 1], (1000 * i), (1000 * (i + 1)));
                }
                // add another timer.schedule(TimerTask)
                // to execute that "move to game screen" task
                TimerTask taskGotoGame = new TimerTask() {
                    public void run() {
                        timer.cancel();
                        JOptionPane.showMessageDialog(null, "Go to game", "Will now", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                };
                // and schedule it to happen after ROUGHLY 3 seconds
                timer.schedule(taskGotoGame, 3000);
            }
        });

    }

}