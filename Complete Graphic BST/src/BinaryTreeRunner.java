import javax.swing.JOptionPane;

public class BinaryTreeRunner {
    public static void main(String[]Args){
        int type = JOptionPane.showOptionDialog(null, "What Type Of Tree Do You Want To Run?", "Graphic Tree Lab", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Binary", "AVL"}, "Binary");
        
        String[] options = new String[]{"Binary", "AVL", "AVL"};

        BinaryTreeWindow window = new BinaryTreeWindow(type,"Graphic Tree Lab - "+options[type] + " Tree");
    }
}
