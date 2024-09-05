import java.util.LinkedList;
import java.util.Queue;

public class AVLTree extends BinaryTree{
    
    private AVLNode root = null;

    public AVLTree(){
        super();
    }

    public void add(Comparable c){
        AVLNode x = new AVLNode(c);
        if(this.root==null){
            root = new AVLNode(c);
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
                return;
            }else{
                add((AVLNode)parent.right(), tba);
            }
        }

        if(tba.value().compareTo(parent.value())<0){
            if(parent.left()==null){
                parent.setLeft(tba);
                return;
            }else{
                add((AVLNode)parent.left(), tba);
            }
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

    public void fixTree(){
        root = fix(root);
    }

    private AVLNode fix(AVLNode r){
        if(r==null){
            return null;
        }


        r.setLeft(fix((AVLNode)r.left()));
        r.setRight(fix((AVLNode)r.right()));


        if(r.left()!=null&&r.left().left()!=null){
            if(r.absBf()>1&&r.balanceFactor()>0&&((AVLNode)r.left()).balanceFactor()>0){
                return leftLeftRotation(r);
            }
        }


        if(r.right()!=null&&r.right().right()!=null){
            if(r.absBf()>1&&r.balanceFactor()<0&&((AVLNode)r.right()).balanceFactor()<0){
                return rightRightRotation(r);
            }
        }


        if(r.left()!=null&&r.left().right()!=null){
            if(r.absBf()>1&&r.balanceFactor()>0&&((AVLNode)r.left()).balanceFactor()<0){
                return leftRightRotation(r);
            }
   
        }


        if(r.right()!=null&&r.right().left()!=null){
            if(r.absBf()<1&&r.balanceFactor()<0&&((AVLNode)r.right()).balanceFactor()>0){
                return rightLeftRotation(r);
            }
        }

        System.out.println("Fixed "+r.value().toString());
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


        grandparent.setLeft((parent.right()));
        parent.setRight(grandparent);
        //grandparent.setColor(0);
        //parent.setColor(1);
        return parent;


    }


    private AVLNode rightRightRotation(AVLNode grandparent){
        AVLNode parent = (AVLNode)grandparent.right();


        grandparent.setRight((parent.left()));
        parent.setLeft(grandparent);
        //grandparent.setColor(0);
        //parent.setColor(1);
        return parent;


    }


    private AVLNode leftRightRotation(AVLNode grandparent){
        AVLNode parent = (AVLNode)grandparent.left();
        AVLNode child = (AVLNode)parent.right();


        parent.setRight(child.left());
        child.setLeft(parent);
        grandparent.setLeft(child);


        return leftLeftRotation(grandparent);


    }


    private AVLNode rightLeftRotation(AVLNode grandparent){
        AVLNode parent = (AVLNode)grandparent.right();
        AVLNode child = (AVLNode)parent.left();


        parent.setLeft(child.right());
        child.setRight(parent);
        grandparent.setRight(child);


        return rightRightRotation(grandparent);


    }


}
