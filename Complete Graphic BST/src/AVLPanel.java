import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.font.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;
public class AVLPanel extends JPanel implements KeyListener{

    private AVLTree tree = new AVLTree();
    private String readValues = "";
    private Boolean displayedWarning = false;
    private int drawStyle = 0; 
    private int numStyle = 0;

    public AVLPanel(){
        addKeyListener(this);
    }

    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, (20)));
        
        //g.setFont(new Font("Gill Sans MT", Font.BOLD, xFont(35)));
        if(tree.getRoot()!=null){
        drawTree(g);}

        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, (15)));
        g.drawString("Input>> "+readValues+"█", 5, getHeight()-5);
        drawStorage(g);

       
    }

    public void drawStorage(Graphics g){
        int height = getHeight() - 51;

        if(Storage.getStorage().size()!=0){
            g.drawString("<<Use /clearText to clear text>>", 5, getHeight()-23);
        }else{
            g.drawString("<<Use /commands to view commands>>", 5, getHeight()-23);
        }

        for(int i = Storage.getStorage().size()-1; i>=0; i--){
            g.drawString(Storage.getStorage().get(i), 5, height);
            height -= 18;
        }
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
            g.setFont(new Font("Consolas", Font.PLAIN, 25));
        }else if(tree.getRoot().height()+1<6){
            g.setFont(new Font("Consolas", Font.PLAIN, 20));
        }else{
            g.setFont(new Font("Consolas", Font.BOLD, 15));
        }

        int changeXByLeft = 100;
        int changeXByRight = 100;

        if(drawStyle==0){
            int changeByMultiplier = 150;

            int changeByIfOverflow=1;

            for(int i = 1; i<=tree.getRoot().height()+1; i++){
                changeByIfOverflow+=(int)Math.pow(2, i);
            }

            changeByIfOverflow*=150;//one side width of tree

            changeByIfOverflow = (int)(150*(getWidth()+0.0)/(2.5*changeByIfOverflow));

            changeByMultiplier =(int)(2.65*Math.pow(0.3, getWidth()/1800.0 -1)*Math.min(changeByIfOverflow, changeByMultiplier));

            if(n.left()!=null){changeXByLeft = changeByMultiplier*(int)Math.pow(2, n.left().height()+1);}

            if(n.right()!=null){changeXByRight= changeByMultiplier*(int)Math.pow(2, n.right().height()+1);}
        }else{
             changeXByLeft = (int)(425*(getWidth()/1800.0)*Math.pow(2, level*-1));
             changeXByRight = changeXByLeft;
        }

        if(n==tree.getRoot()&&(changeXByLeft<=5||changeXByRight<=5)){
            drawStyle =1;
            repaint();
        }

        g.setColor(new Color(50, 50, 50));

        if(n.left()!=null){
            
            g.drawLine(x(x), y(y), x(x-changeXByLeft), y(y+40));
            drawNode(g, n.left(), x-changeXByLeft, y+40, level+1);
        }
        
        g.setColor(new Color(50, 50, 50));
        if(n.right()!=null){
            
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
        String s = n.value().toString();
        int changeXBy = (int)(19*s.length()/4.0);

        if(tree.getRoot().height()+1<5){
            changeXBy*=1;
        }else if(tree.getRoot().height()+1<6){
            changeXBy*=4.0/5;
        }else{
            changeXBy*=3.0/5;
        }

        g.drawString(s, x(x-changeXBy), y(y+10));
    }

    public void centerString(Graphics g, String s, int xCo, int yCo){
        int x = xCo;
        int y = yCo;
        int changeXBy = (int)(21*s.length()/4.0);

        
        

        g.drawString(s, x(x)-changeXBy, (y+10));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        int asInt = (int)e.getKeyChar()-'0';
        System.out.println(asInt);
        if(asInt==-40){//backspace
            readValues=readValues.substring(0, readValues.length()-1);
    
        }else if(asInt==-26){
            //paste????
        }else if(asInt!=-38){//number
            String s = Character.toString(c);
            readValues += s;
        
        }else{//enter key
            
            try {
                if(readValues.toCharArray()[0]=='/'){
                    evaluateCommand(readValues.substring(1));
                }
    
                Scanner rV = new Scanner(readValues);
                while(rV.hasNextInt()){
                    tree.add(rV.nextInt());
                }
                readValues = "";
                rV.close();
            } catch (Exception exe) {
                Storage.println("There may have been an error with the command "+readValues);
                readValues = "";
            }

            
            
        }
        repaint();
    }

    public void evaluateCommand(String s){
        Scanner sc = new Scanner(s);
        String command = sc.next();

        switch(command){
            case "preOrder":
            //Storage.clear();
            Storage.println("preOrder traversal: "+tree.preorder());
            break;
            case "postOrder":
            //Storage.clear();
            Storage.println("postOrder traversal: "+tree.postorder());
            break;
            case "inOrder":
            //Storage.clear();
            Storage.println("inOrder traversal: "+tree.inorder());
            break;
            case "height":
            //Storage.clear();
            if(sc.hasNextInt()){
                Storage.println("height: "+tree.search(tree.getRoot(), sc.nextInt()).height().toString());
            }else{
                Storage.println("height: "+tree.getRoot().height().toString());
            }
            break;
            case "width":
            //Storage.clear();
            if(sc.hasNextInt()){
                Storage.println("width: "+tree.width(sc.nextInt()));
            }else{
                Storage.println("width: "+tree.width());
            }
            break;
            case "diameter":
            Storage.println("Diameter: "+tree.diameter());
            break;
            case "degreeNum":
            int deg = sc.nextInt();
            Storage.println(tree.numDegree(deg)+" degree "+deg+" nodes. ");
            break;
            case "levelOrder":
            //Storage.clear();
            Storage.println("levelOrder traversal: "+tree.levelorder());
            break;
            case "reverseOrder":
            Storage.println("reverseOrder traversal: "+tree.reverseorder());
            break;
            case "delete":
            //Storage.clear();
            while(sc.hasNextInt()){
                tree.remove(sc.nextInt());
            }
            break;
            case "insert":
            //Storage.clear();
            while(sc.hasNextInt()){
                tree.add(sc.nextInt());
            }
            break;
            case "insertRange":
            //Storage.clear();
            int value1 = sc.nextInt();
            int value2 = sc.nextInt();

            if(value2>value1){
                for(int i = value1; i<=value2; i++){
                    //Storage.delay();
                    tree.add(i);
                    //repaint();
                }
            }else{
                for(int i = value1; i>=value2; i--){
                    //Storage.delay();
                    tree.add(i);
                    //repaint();
                }
            }
            break;
            case "deleteRange":
            //Storage.clear();
            int value1D = sc.nextInt();
            int value2D = sc.nextInt();

            if(value2D>value1D){
                for(int i = value1D; i<=value2D; i++){
                    if(tree.containsNode(i)){
                        tree.remove(i);
                    }  
                }
            }else{
                for(int i = value1D; i>=value2D; i--){
                    if(tree.containsNode(i)){
                        tree.remove(i);
                    }  
                }
            }
            break;
            case "insertRandom":
            int numValues = sc.nextInt();
            int startValue = sc.nextInt();
            int endValue = sc.nextInt();
            endValue = endValue - startValue;

            for(int i = 0; i<numValues; i++){
                tree.add((int)(Math.random()*endValue+1)+startValue);
            }
            break;
            case "deleteMath":
            break;
            case "clearTree":
            Storage.clear();
            tree = new AVLTree();
            break;
            case "copy":
                String myString = "";
                ArrayList storage = Storage.getStorage();
                for(int i = 0; i<storage.size(); i++){
                    myString +=storage.get(i);
                    myString += "\n";
                }

                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                Storage.println("Copied text");
            break;
            case "contains":
                int containsCheck = sc.nextInt();
                if(tree.containsNode(containsCheck)){
                    Storage.println(containsCheck + " found in tree. ");
                }else{
                    Storage.println(containsCheck + " not found in tree. ");
                }
            break;
            case "drawStyle":
            drawStyle = sc.nextInt();
            break;
            case "clearText":
            Storage.clear();
            break;
            case "wipe":
            Storage.clear();
            break;
            case "commands":
            //Storage.clear();
            Storage.println("List of Commands:");
            Storage.println("/commands: brings up this commands list");
            Storage.println("/insert [value] [value] etc. : inserts values seperated by a space");
            Storage.println("/insertRange [value1] [value2]: inserts all integer values between [value1] and [value2], inclusive");
            Storage.println("/insertRandom [numValues] [startValue] [endValue]: inserts [numValues] random integers into the tree between [startValue] and [endValue], inclusive");
            Storage.println("/delete [value] [value] etc. : deletes values seperated by a space");
            Storage.println("/deleteRange [value1] [value2]: deletes all integer values between [value1] and [value2], inclusive");
            Storage.println("/preOrder: prints a preorder traversal of the tree");
            Storage.println("/postOrder: prints a postorder traversal of the tree");
            Storage.println("/inOrder: prints a inorder traversal of the tree");
            Storage.println("/levelOrder: prints a levelorder traversal of the tree");
            Storage.println("/reverseOrder: prints a reverse inorder traversal of the tree");
            Storage.println("/height: height of the tree");
            Storage.println("/height [value]: height of the subtree starting from [value]");
            Storage.println("/width: max width of the tree");
            Storage.println("/width [level]: width of the tree at [level]");
            Storage.println("/diameter: diameter of the tree");
            Storage.println("/degreeNum [degree]: number of nodes with degree [degree]");
            Storage.println("/contains [value]");
            Storage.println("/clearTree: clears the tree");
            Storage.println("/copy: copies the current display text to the clipboard");
            Storage.println("/clearText: clears display text");
            break;
        }
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
