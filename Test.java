import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {
    public static void main(String[] args) throws IOException {
        Clothing c = new Clothing("Blue Diamond Pattern", "Sari", "Mumbai" , 55.3, 67.3, 3484);
        JFrame frame = new JFrame();





        
        frame.add(c);
        frame.setSize(215, 150);
        frame.setTitle("Inventory Manager");
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
