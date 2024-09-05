import javax.swing.JFrame;

public class BinaryTreeWindow extends JFrame{
    public BinaryTreeWindow(int type, String framename){
        super(framename);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1800, 800);
        switch(type){
            case 0:
                add(new BinaryTreePanel());
                break;
            case 1:
                add(new AVLPanel());
                break;
            case 2:
                add(new AVLPanel());
                break;
        }
        setVisible(true);
    }
}
