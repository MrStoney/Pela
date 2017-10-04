package pela;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Pela extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pela() {

        add(new Taust());
        
        setResizable(false);
        pack();
        
        setTitle("Pela");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame ex = new Pela();
                ex.setVisible(true);                
            }
        });
    }
}