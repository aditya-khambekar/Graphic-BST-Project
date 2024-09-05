import java.util.LinkedList;
import java.util.Queue;
public class BinaryTree {
    private BinaryNode root;

    public BinaryTree(){
        root = null;
    }

    public BinaryNode getRoot(){
        return root;
    }

    public Integer outsideHeight(){
        return Math.max(outsideHeight(root.left(), true), outsideHeight(root.right(), false));
    }

    public Integer outsideHeight(BinaryNode b){
        return Math.max(outsideHeight(b.left(), true), outsideHeight(b.right(), false));
    }

    private Integer outsideHeight(BinaryNode b, Boolean goingLeft){
        if(b==null){
            return 0;
        }else{
            if(goingLeft){
                return 1 + outsideHeight(b.left(), true);
            }else{
                return 1 + outsideHeight(b.right(), false);
            }
        }
    }

    

    public void add(Comparable c){
        BinaryNode x = new BinaryNode(c);
        if(root==null){
            root = x;
        }else{
            add(root, x);
        }
    }

    private void add(BinaryNode parent, BinaryNode tba){
        if(tba.value().compareTo(parent.value())>0){
            if(parent.right()==null){
                parent.setRight(tba);
                return;
            }else{
                add(parent.right(), tba);
            }
        }

        if(tba.value().compareTo(parent.value())<0){
            if(parent.left()==null){
                parent.setLeft(tba);
                return;
            }else{
                add(parent.left(), tba);
            }
        }

    }

    //Traversals

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

    //Remove helper methods

    private BinaryNode parentSearch(BinaryNode b, Comparable t){
        if(b==null){
            return null;
        }
        if(t.compareTo(b.value())<0){
            if(b.left()!=null&&t.equals(b.left().value())){
                return b;
            }
            else if(b.left()!=null){
                return parentSearch(b.left(), t);
            }
            else{
                return null;
            }
        }

        if(t.compareTo(b.value())>0){
            if(b.right()!=null&&t.equals(b.right().value())){
                return b;
            }
            else if(b.right()!=null){
                return parentSearch(b.right(), t);
            }
            else{
                return null;
            }
        }
        return null;
    }

    private void swap(BinaryNode a, BinaryNode b){
        Comparable temp = a.value();
        a.setValue(b.value());
        b.setValue(temp);
    }

    private BinaryNode successor(BinaryNode b){
        BinaryNode current = b.right();
        while(b.left()!=null){
            current = current.left();
        }

        return current;
    }

    public BinaryNode remove(Comparable target){
        if(root==null){return null;}

        BinaryNode temp = root;

        if(target.equals(root.value())){
           if(root.left()==null&&root.right()==null){ //root is degree 0
                root = null;
                return temp;
           }else if(root.right()==null){//degree 1 and left child
                root = root.left();
                temp.setLeft(null);
                return temp;
           }else if(root.left()==null){//degree 1 and right child
                root = root.right();
                temp.setRight(null);
                return temp;
           }else{//degree 2
                BinaryNode successor = successor(root);
                swap(root, successor);
                if(successor.equals(root.right())){
                    root.setRight(successor.right());
                    successor.setRight(null);
                    return successor;
                }
                return remove(root.right(), target);
           }
           
        }
        return remove(root, target);
    }

    public BinaryNode remove(BinaryNode start, Comparable target){//not finished
        if(start==null){return null;}

        BinaryNode nodeToRemove, inorderSuccessor;
        BinaryNode parent = parentSearch(start,target);

        if(parent==null){return null;}

        Boolean isLeft = target.compareTo(parent.value())<0;
        if(isLeft&&parent.left!=null){
            nodeToRemove = parent.left();
        }else if(parent.right!=null){
            nodeToRemove = parent.right();
        }else{
            return null;
        }
         return null;

    }
}
