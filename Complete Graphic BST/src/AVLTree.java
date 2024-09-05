import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AVLTree extends BinaryTree{
    
    private AVLNode root = null;

    public AVLTree(){
        super();
    }

        public BinaryNode search(Comparable c){
        BinaryNode current = this.root;

        while(current.value()!=c){
            if(c.compareTo(current.value())>0){
                if(current.right()!=null){
                    current = current.right();
                }else{
                    return null;
                }
            }else if(c.compareTo(current.value())<0){
                if(current.left()!=null){
                    current = current.left();
                }else{
                    return null;
                }
            }else{
                return current;
            }
        }
        return current;
    }

    public Boolean containsNode(Comparable c){
        BinaryNode result = search(c);
        if(result==null){
            return false;
        }
        return true;
    }

    public Comparable getLargest(){
        BinaryNode current=root;
        while(current.right!=null){
            current = current.right();
        }
        return current.value();
    }

    public Comparable getSmallest(){
        BinaryNode current=root;
        while(current.left!=null){
            current = current.left();
        }
        return current.value();
    }

    public Integer diameter(){
        return 3+root.left().height() + root.right().height();
    }

    public Boolean isFull(){
        Queue<BinaryNode> q = new LinkedList<BinaryNode>();
        q.add(root);
        while(q.size()>0){
            BinaryNode current = q.remove();
            if(current.degree()==1){
                return false;
            }
            if(current.left()!=null){
                q.add(current.left());}
            if(current.right()!=null){
                q.add(current.right());}
        }
        return true;
    }

    public ArrayList<String> TreeAsList(){
        Queue<BinaryNode> currentLevel = new LinkedList<>();
        Queue<BinaryNode> nextLevel = new LinkedList<>();
        Queue<BinaryNode> temp;
        BinaryNode current;
        Integer levelsToPrint = Math.min(6, root.height()+1);
        //System.out.println(currentLevel.size());
        currentLevel.add(root);
        //System.out.println(currentLevel.size());
        ArrayList<String> treeList = new ArrayList<String>();

        for(int i = 0; i<levelsToPrint; i++){
            //System.out.println("i = "+i);
            //System.out.println(currentLevel);
            treeList.add(stringLevelFromQueue(currentLevel));
            //System.out.println(currentLevel.size());
            while(currentLevel.size()>0){
                current = currentLevel.remove();
                //System.out.println("Removed "+current+" from Queue");
                if(current.left()==null){
                    nextLevel.add(new BinaryNode());
                }else{
                    nextLevel.add(current.left());
                }
                if(current.right()==null){
                    nextLevel.add(new BinaryNode());
                }else{
                    nextLevel.add(current.right());
                }
            }
            while(nextLevel.size()>0){
                currentLevel.add(nextLevel.remove());
            }
            nextLevel = new LinkedList<>();
        }

        return treeList;
    }

    public static String stringLevelFromQueue(Queue<BinaryNode> q){
        String tbp = "";//to be printed
        BinaryNode current;
        //Queue<BinaryNode>q = new LinkedList<>();
        Queue<BinaryNode> storageQueue = new LinkedList<>();


        while(q.size()>1){
            current = q.remove();
            storageQueue.add(current);
            if(current.value()==null){
                tbp = tbp + "--";
            }else{
                tbp = tbp + current.value().toString();
            }
            tbp = tbp + " | ";
        }

        //try {
            current = q.remove();
            storageQueue.add(current);
        if(current.value()==null){
            tbp = tbp + "--";
        }else{
             tbp = tbp + current.value().toString();
        }
        //} catch (Exception e) {
            // TODO: handle exception
        //}
        
        while(storageQueue.size()>0){
            q.add(storageQueue.remove());
        }

        return tbp;

    }

    public Integer width(){
        Queue<BinaryNode> currentLevel = new LinkedList<>();
        Queue<BinaryNode> nextLevel = new LinkedList<>();
        Queue<BinaryNode> temp;
        BinaryNode current;
        Integer width = 0;
        currentLevel.add(root);

        for(int i = 0; i<root.height()+1; i++){
            width = Math.max(currentLevel.size(), width);
            while(currentLevel.size()>0){
                current = currentLevel.remove();
                if(current.left()==null){
                    //nextLevel.add(new BinaryNode());
                }else{
                    nextLevel.add(current.left());
                }
                if(current.right()==null){
                    //nextLevel.add(new BinaryNode());
                }else{
                    nextLevel.add(current.right());
                }
            }
            currentLevel = nextLevel;
            nextLevel = new LinkedList<>();
        }

        return width;
    }

    public Integer width(Integer level){
        Queue<BinaryNode> currentLevel = new LinkedList<>();
        Queue<BinaryNode> nextLevel = new LinkedList<>();
        Queue<BinaryNode> temp;
        BinaryNode current;
        Integer width = 0;
        currentLevel.add(root);

        for(int i = 0; i<root.height()+1; i++){
            if(i==level){
                return currentLevel.size();
            }
            while(currentLevel.size()>0){
                current = currentLevel.remove();
                if(current.left()==null){
                    //nextLevel.add(new BinaryNode());
                }else{
                    nextLevel.add(current.left());
                }
                if(current.right()==null){
                    //nextLevel.add(new BinaryNode());
                }else{
                    nextLevel.add(current.right());
                }
            }
            currentLevel = nextLevel;
            nextLevel = new LinkedList<>();
        }

        return width;
    }

    public Integer numDegree(Integer degree){
        return numDegree(root, degree);
    }

    private Integer numDegree(BinaryNode x, Integer degree){
        Integer numDegree;
        if(x!=null){
            numDegree = 0;
            if(x.degree()==degree){
                numDegree++;
            }
            
            numDegree += numDegree(x.left(), degree);
            
            
            numDegree += numDegree(x.right(), degree);
        }else{
            return 0;
        }
        
        return numDegree;
    }

    public Integer countNodes(){
        return countNodes(root);
    }

    private Integer countNodes(BinaryNode x){
        Integer numNodes = 0;
        if(x!=null){
            numNodes++;
            
            numNodes += countNodes(x.left());
            
            
            numNodes += countNodes(x.right());
        }else{
            return 0;
        }
        
        return numNodes;
    }

    public void add(Comparable c){
        AVLNode x = new AVLNode(c);
        if(this.root==null){
            root = new AVLNode(c);
            Storage.println(c+" added as root. ");
        }else{
            add(root, x);
        }
        
        this.fixTree();
    }

    public AVLNode getRoot(){
        return this.root;
    }

    private void add(AVLNode parent, AVLNode tba){
        if(tba.value().compareTo(parent.value())>0){
            if(parent.right()==null){
                parent.setRight(tba);
                Storage.println(tba.value() + " added as right child of " + parent.value()+". ");
                return;
            }else{
                Storage.println(tba.value() + " greater than " + parent.value()+". ");
                add((AVLNode)parent.right(), tba);
                
            }
        }
        else if(tba.value().compareTo(parent.value())<0){
            if(parent.left()==null){
                parent.setLeft(tba);
                Storage.println(tba.value() + " added as left child of " + parent.value()+". ");
                return;
            }else{
                Storage.println(tba.value() + " less than " + parent.value()+". ");
                add((AVLNode)parent.left(), tba);
                
            }
        }else{
            Storage.println(tba.value() + " already in tree. ");
        }

        
    }

    public String preorder(){
        return preorder(root).trim();
    }

    private String preorder(BinaryNode x){
        String returnString = "";
        if(x!=null){
            returnString +=x.value()+" ";
            
            returnString += preorder(x.left());
            
            
            returnString += preorder(x.right());
        }
        return returnString;
    }

    public String postorder(){
        return postorder(root).trim();
    }

    private String postorder(BinaryNode x){
        String ret = "";
        if(x!=null){
            
            
            ret += postorder(x.left());
            
            
            ret += postorder(x.right());

            ret +=x.value()+" ";
        }
        return ret;
    }

    public String inorder(){
        return inorder(root).trim();
    }

    private String inorder(BinaryNode x){
        String ret = "";
        if(x!=null){
            
            
            ret += inorder(x.left());
            
            ret +=x.value()+" ";
            ret += inorder(x.right());

            
        }
        return ret;
    }

    public String levelorder(){
        String ret = "";
        Queue<BinaryNode> q = new LinkedList<BinaryNode>();
        q.add(root);
        while(q.size()>0){
            BinaryNode current = q.remove();
            ret+=current.value+" ";
            if(current.left()!=null){
                q.add(current.left());}
            if(current.right()!=null){
                q.add(current.right());}
        }
        return ret;
    }

    public String reverseorder(){
        return reverseorder(root).trim();
    }

    private String reverseorder(BinaryNode x){
        String ret = "";
        if(x!=null){
            
            
            ret += reverseorder(x.right());
            
            ret +=x.value()+" ";
            
            ret += reverseorder(x.left());

            
        }
        return ret;
    }

    public void fixTree(){
        root = fix(root);
    }

    private AVLNode fix(AVLNode r){
        if(r==null){
            return null;
        }


        r.setLeft(fix((AVLNode)r.left()));
        r.setRight(fix((AVLNode)r.right()));

        /*System.out.println("Balance Factor of "+r.value().toString()+ " is "+r.balanceFactor());
        if(r.left()!=null){
            System.out.println("Left Child Balance Factor of "+r.left().value().toString()+ " is "+((AVLNode)r.left()).balanceFactor());
        }
        
        if(r.right()!=null){
            System.out.println("Right Child Balance Factor of "+r.right().value().toString()+ " is "+((AVLNode)r.right()).balanceFactor());
        }*/
        

        if(r.left()!=null&&r.left().left()!=null){
            if(r.absBf()>1&&r.balanceFactor()>0&&((AVLNode)r.left()).balanceFactor()>=0){
                //System.out.println("LeftLeft "+r.value().toString());
                return leftLeftRotation(r);
            }
        }


        if(r.right()!=null&&r.right().right()!=null){
            if(r.absBf()>1&&r.balanceFactor()<0&&((AVLNode)r.right()).balanceFactor()<=0){
                //System.out.println("RightRight "+r.value().toString());
                return rightRightRotation(r);
            }
        }


        if(r.left()!=null&&r.left().right()!=null){
            if(r.absBf()>1&&r.balanceFactor()>0&&((AVLNode)r.left()).balanceFactor()<0){
                //System.out.println("LeftRight "+r.value().toString());
                return leftRightRotation(r);
            }
   
        }


        if(r.right()!=null&&r.right().left()!=null){
            if(r.absBf()>1&&r.balanceFactor()<0&&((AVLNode)r.right()).balanceFactor()>0){
                //System.out.println("RightLeft "+r.value().toString());
                return rightLeftRotation(r);
            }
        }

        
        //r.setLeft(fix((AVLNode)r.left()));
        //r.setRight(fix((AVLNode)r.right()));
        
        return r;


    }

    public int getHeight(){
        return getHeight(root);
    }

    private int getHeight(BinaryNode k){
        if(k == null) return -1;
        return 1 + Math.max(getHeight(k.left()),
        getHeight(k.right()));
    }

    private AVLNode leftLeftRotation(AVLNode grandparent){
        AVLNode parent = (AVLNode)grandparent.left();

        Storage.println("LeftLeft Rotation with "+ grandparent.value()+" as Grandparent. ");
        grandparent.setLeft((parent.right()));
        parent.setRight(grandparent);
        //grandparent.setColor(0);
        //parent.setColor(1);
        return parent;


    }


    private AVLNode rightRightRotation(AVLNode grandparent){
        AVLNode parent = (AVLNode)grandparent.right();

        Storage.println("RightRight Rotation with "+ grandparent.value()+" as Grandparent. ");
        grandparent.setRight((parent.left()));
        parent.setLeft(grandparent);
        //grandparent.setColor(0);
        //parent.setColor(1);
        return parent;


    }


    private AVLNode leftRightRotation(AVLNode grandparent){
        AVLNode parent = (AVLNode)grandparent.left();
        AVLNode child = (AVLNode)parent.right();

        Storage.println("LeftRight Rotation with "+ grandparent.value()+" as Grandparent. ");
        parent.setRight(child.left());
        child.setLeft(parent);
        grandparent.setLeft(child);


        return leftLeftRotation(grandparent);


    }


    private AVLNode rightLeftRotation(AVLNode grandparent){
        AVLNode parent = (AVLNode)grandparent.right();
        AVLNode child = (AVLNode)parent.left();

        Storage.println("RightLeft Rotation with "+ grandparent.value()+" as Grandparent. ");
        parent.setLeft(child.right());
        child.setRight(parent);
        grandparent.setRight(child);


        return rightRightRotation(grandparent);


    }

    private AVLNode parentSearch(AVLNode b, Comparable t){
        if(b==null){
            return null;
        }
        if(t.compareTo(b.value())<0){
            if(b.left()!=null&&t.equals(b.left().value())){
                Storage.println("Found parent of " + t + " to be "+b.value()+". ");
                return b;
            }
            else if(b.left()!=null){
                return parentSearch((AVLNode)b.left(), t);
            }
            else{
                Storage.println(t+" not found in tree. ");
                return null;
            }
        }

        if(t.compareTo(b.value())>0){
            if(b.right()!=null&&t.equals(b.right().value())){
                Storage.println("Found parent of " + t + " to be "+b.value()+". ");
                return b;
            }
            else if(b.right()!=null){
                return parentSearch((AVLNode)b.right(), t);
            }
            else{
                Storage.println(t+" not found in tree. ");
                return null;
            }
        }
        Storage.println(t+" not found in tree. ");
        return null;
    }

    public AVLNode search(AVLNode b, Comparable t){
        if(b==null){
            return null;
        }
        if(t.compareTo(b.value())<0){
            return search((AVLNode)b.left(), t);
        }

        if(t.compareTo(b.value())>0){
            return search((AVLNode)b.right(), t);
        }

        if(t.equals(b.value())){
            return b;
        }
        return null;
    }

    private void swap(AVLNode a, AVLNode b){
        Comparable temp = a.value();
        a.setValue(b.value());
        b.setValue(temp);
        Storage.println("Swapped values of "+a.value() + " and "+b.value()+". ");
    }

    private AVLNode successor(AVLNode b){
        AVLNode current = (AVLNode)b.right();
        while(current.left()!=null){
            current = (AVLNode)current.left();
        }
        Storage.println("Inorder successor of "+b.value()+" is "+current.value()+". ");
        return current;
    }

    public AVLNode remove(Comparable target){
        if(root==null){return null;}

        AVLNode temp = root;

        if(target.equals(root.value())){
           if(root.left()==null&&root.right()==null){ //root is degree 0
                root = null;
                Storage.println("Removed "+target+" as degree 0 root. ");
                return temp;
           }else if(root.right()==null){//degree 1 and left child
                root = (AVLNode)root.left();
                temp.setLeft(null);
                Storage.println("Removed "+target+" as degree 1 root with left child. ");
                return temp;
           }else if(root.left()==null){//degree 1 and right child
                root = (AVLNode)root.right();
                temp.setRight(null);
                Storage.println("Removed "+target+" as degree 1 root with right child. ");
                return temp;
           }else{//degree 2
                Storage.println("Removing "+target+" as degree 2 root. ");
                AVLNode successor = successor(root);
                swap(root, successor);
                if(successor.equals(root.right())){
                    root.setRight(successor.right());
                    successor.setRight(null);
                    Storage.println("Inorder successor is right child.");
                    Storage.println("Removed "+target+". ");
                    return successor;
                }
                return remove((AVLNode)root.right(), target);
           }
           
        }
        Storage.println(target+" is not the root. ");
        AVLNode ret = remove(root, target);
        fixTree();
        return ret;
    }

    public AVLNode remove(AVLNode start, Comparable target){
        if(start==null){return null;}

        AVLNode nodeToRemove, inorderSuccessor;
        AVLNode parent = parentSearch(start,target);

        if(parent==null){return null;}

        Boolean isLeft = target.compareTo(parent.value())<0;//target is parent's left child

        if(isLeft){
            nodeToRemove = (AVLNode)parent.left();
        }else{
            nodeToRemove = (AVLNode)parent.right();
        }

        AVLNode temp = nodeToRemove;

        if(nodeToRemove.left()==null&&nodeToRemove.right()==null){ //nodeToRemove is degree 0
            if(isLeft){
                parent.setLeft(null);
            }else{
                parent.setRight(null);
            }
            Storage.println("Removed "+target+" as degree 0 node. ");
            return nodeToRemove;
       }else if(nodeToRemove.right()==null){//degree 1 and left child
            if(isLeft){
                parent.setLeft(nodeToRemove.left());
            }else{
                parent.setRight(nodeToRemove.left());
            }
            temp.setLeft(null);
            Storage.println("Removed "+target+" as degree 1 node with left child. ");
            return temp;
       }else if(nodeToRemove.left()==null){//degree 1 and right child
        if(isLeft){
            parent.setLeft(nodeToRemove.right());
        }else{
            parent.setRight(nodeToRemove.right());
        }
        temp.setLeft(null);
        Storage.println("Removed "+target+" as degree 1 node with right child. ");
        return temp;
       }else{//degree 2
        Storage.println("Removing "+target+" as degree 2 node. ");
            AVLNode successor = successor(nodeToRemove);
            swap(nodeToRemove, successor);
            if(successor.equals(nodeToRemove.right())){
                nodeToRemove.setRight(successor.right());
                successor.setRight(null);
                return successor;
            }
            return remove((AVLNode)nodeToRemove.right(), target);
       }
       
    }


}
