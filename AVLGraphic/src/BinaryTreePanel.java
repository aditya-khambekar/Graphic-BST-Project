import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
public class BinaryTreePanel extends JPanel implements KeyListener{

    private BinaryTree tree = new AVLTree();
    private String readValues = "";
    private Boolean displayedWarning = false;

    public BinaryTreePanel(){
        addKeyListener(this);
    }

    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Gill Sans MT", Font.PLAIN, (20)));
        centerString(g,"Input: "+readValues, 900, 40);
        g.setFont(new Font("Gill Sans MT", Font.BOLD, xFont(35)));
        if(tree.getRoot()!=null){
        drawTree(g);}

        
       
    }

    public void addNotify(){
        super.addNotify();
        requestFocus();
    }

    public void drawTree(Graphics g){
        drawNode(g, tree.getRoot(), 900, 155, 0);
    }

    public void drawNode(Graphics g, BinaryNode n, int x, int y, int level){

        /*if(level>=6){
            if(displayedWarning=false){
                JOptionPane.showConfirmDialog(null, "Cannot display all values due to high level", "Cannot Display value", JOptionPane.PLAIN_MESSAGE);
                displayedWarning=true;
                return;
            }else{
                return;
            }

        }*/

        if(tree.getRoot().height()+1<5){
            g.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        }else if(tree.getRoot().height()+1<6){
            g.setFont(new Font("Gill Sans MT", Font.PLAIN, 27));
        }else{
            g.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
        }
        int changeByMultiplier = 150;

        int changeByIfOverflow=1;

        for(int i = 1; i<=tree.getRoot().height()+1; i++){
            changeByIfOverflow+=(int)Math.pow(2, i);
        }

        changeByIfOverflow*=150;//one side width of tree

        changeByIfOverflow = (int)(150*(getWidth()+0.0)/(2.5*changeByIfOverflow));

        changeByMultiplier =(int)(2.65*Math.pow(0.3, getWidth()/1800.0 -1)*Math.min(changeByIfOverflow, changeByMultiplier));


        g.setColor(new Color(50, 50, 50));

        if(n.left()!=null){
            int changeXByLeft = changeByMultiplier*(int)Math.pow(2, n.left().height()+1);
            g.drawLine(x(x), y(y), x(x-changeXByLeft), y(y+40));
            drawNode(g, n.left(), x-changeXByLeft, y+40, level+1);
        }
        
        g.setColor(new Color(50, 50, 50));
        if(n.right()!=null){
            int changeXByRight= changeByMultiplier*(int)Math.pow(2, n.right().height()+1);
            g.drawLine(x(x), y(y), x(x+changeXByRight), y(y+40));
            drawNode(g, n.right(), x+changeXByRight, y+40, level+1);
        }

        

        

        centerCircle(g, x, y, ((AVLNode)n).balanceFactor());
        //g.setColor(Color.BLACK);
        centerNumber(g, n, x, y);
    }

    public void centerCircle(Graphics g, int x, int y, int color){
        if(color == 0){
            g.setColor(new Color(200, 200, 200));
        }else if(color == -1){
            g.setColor(new Color(182, 208, 226));
            //g.setColor(new Color(200, 200, 200));
        }else{
            g.setColor(new Color(232, 255, 245));
        }

        if(tree.getRoot().height()+1<5){
            g.fillOval(x(x-20), y(y-25), 50, 50);
        }else if(tree.getRoot().height()+1<6){
            g.fillOval(x(x-16), y(y-20), 40, 40);
        }else{
            g.fillOval(x(x-12), y(y-15), 30, 30);
        }

        if(color == 0){
            g.setColor(Color.WHITE);
        }else if(color == -1){
            g.setColor(Color.BLUE);
        }else{
            g.setColor(Color.GREEN);
        }

        if(tree.getRoot().height()+1<5){
            g.drawOval(x(x-20), y(y-25), 50, 50);
        }else if(tree.getRoot().height()+1<6){
            g.drawOval(x(x-16), y(y-20), 40, 40);
        }else{
            g.drawOval(x(x-12), y(y-15), 30, 30);
        }


        g.setColor(Color.BLACK);//end
    }

    public void centerNumber(Graphics g, BinaryNode n, int xCo, int yCo){
        int x = xCo;
        int y = yCo;

        
        
        String s = n.value().toString()+" ("+((AVLNode)n).balanceFactor()+")";
        int changeXBy = (int)(8*s.length()/4.0);

        if(tree.getRoot().height()+1<5){
            g.setFont(new Font("Gill Sans MT", Font.PLAIN, (int)(100.0/s.length())));
        }else if(tree.getRoot().height()+1<6){
            g.setFont(new Font("Gill Sans MT", Font.PLAIN, (int)(80.0/s.length())));
        }else{
            g.setFont(new Font("Gill Sans MT", Font.BOLD, (int)(60.0/s.length())));
        }

        if(tree.getRoot().height()+1<5){
            changeXBy*=1;
        }else if(tree.getRoot().height()+1<6){
            changeXBy*=4.0/5;
        }else{
            changeXBy*=3.0/5;
        }

        g.drawString(s, x(x-changeXBy), y(y+5));
    }

    public void centerString(Graphics g, String s, int xCo, int yCo){
        int x = xCo;
        int y = yCo;
        int changeXBy = (int)(16*s.length()/4.0);

        
        

        g.drawString(s, x(x)-changeXBy, (y+10));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        int asInt = (int)e.getKeyChar()-'0';
        System.out.println(asInt);
        if(asInt==-40){
            readValues=readValues.substring(0, readValues.length()-1);
    
        }else if(asInt!=-38){
            String s = Character.toString(c);
            readValues += s;
        
        }else{
            Scanner rV = new Scanner(readValues);
            while(rV.hasNextInt()){
                tree.add(rV.nextInt());
                //System.out.println(tree.preorder());
            }
            readValues = "";
            rV.close();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        return;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }
    
    public int xFont(int x){
		//double dubx = (double)x;
	return (int)(((double)getWidth()/1800.0)*x);
	}

    public int x(int x){
		//double dubx = (double)x;
	return (int)(((double)getWidth()/1800.0)*x);
	}

	public int y(int y){
		//double duby = (double)y;
	//return (int)(((double)getHeight()/300.0)*y);
    return y;
	}
}
