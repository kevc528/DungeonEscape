import java.awt.*;
import java.awt.Font;
import javax.swing.*;

// This class is responsible for running the game/GUI
public class Game implements Runnable{
	
	public final static JPanel status_panel = new JPanel();
	
	// the launching of the GUI
    public void run() {

        final JFrame frame = new JFrame("KEY COLLECTOR");
        frame.setLocation(300, 300);

        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);
        
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        JLabel top = new JLabel("Welcome to Dungeon Escape");
        top.setFont(new Font("Courier New", Font.BOLD, 16));
        control_panel.add(top);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
