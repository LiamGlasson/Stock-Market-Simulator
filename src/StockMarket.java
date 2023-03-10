import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class StockMarket extends JPanel {
    private static MainWindow window;

    public StockMarket() {
        JFrame frame = new JFrame("Stock Market Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window = new MainWindow();
        frame.getContentPane().add(window);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StockMarket();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Thread thread = new Thread(() -> window.tick());
                thread.start();
            }
        }, 0, 1000);
    }
}