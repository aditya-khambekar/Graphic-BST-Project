public class RedBlackTree extends BinaryTree{
    private RedBlackNode root= null;

    public RedBlackTree(){
        super();
    }

    public RedBlackNode getRoot(){
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
                return 1+ outsideHeight(b.left(), true);
            }else{
                return 1+ outsideHeight(b.right(), false);
            }
        }
    }

    public void add(Comparable c){
        RedBlackNode x = new RedBlackNode(c);
        if(root==null){
            root = x;
        }else{
            add(root, x);
        }

        this.fixTree();
    }

    private void add(BinaryNode parent, BinaryNode tba){
        if(parent.left()!=null&&parent.right()!=null){
            colorSwap((RedBlackNode)parent);
        }
        

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



    public void fixTree(){
        root = fix(root);

        if(root.getColor()==0){
            root.setColor(1);
        }
        
    }

    private RedBlackNode fix(RedBlackNode r){

        if(r==null){
            return null;
        }

        r.setLeft(fix((RedBlackNode)r.left()));
        r.setRight(fix((RedBlackNode)r.right()));

        if(r.left()!=null&&r.left().left()!=null){
            if(((RedBlackNode)r.left()).getColor()==0&&((RedBlackNode)r.left().left()).getColor()==0){
                return leftLeftRotation(r);
            }
        }

        if(r.right()!=null&&r.right().right()!=null){
            if(((RedBlackNode)r.right()).getColor()==0&&((RedBlackNode)r.right().right()).getColor()==0){
                return rightRightRotation(r);
            }
        }

        if(r.left()!=null&&r.left().right()!=null){
            if(((RedBlackNode)r.left()).getColor()==0&&((RedBlackNode)r.left().right()).getColor()==0){
                return leftRightRotation(r);
            }
    
        }

        if(r.right()!=null&&r.right().left()!=null){
            if(((RedBlackNode)r.right()).getColor()==0&&((RedBlackNode)r.right().left()).getColor()==0){
                return rightLeftRotation(r);
            }
        }

        return r;
    }

    public void colorSwap(RedBlackNode grandparent){
        if(((RedBlackNode)grandparent.left()).getColor()==0&&((RedBlackNode)grandparent.right()).getColor()==0){
            grandparent.setColor(0);
            ((RedBlackNode)grandparent.left()).setColor(1);
            ((RedBlackNode)grandparent.right()).setColor(1);
        }
    }

    private RedBlackNode leftLeftRotation(RedBlackNode grandparent){
        RedBlackNode parent = (RedBlackNode)grandparent.left();

        grandparent.setLeft((parent.right()));
        parent.setRight(grandparent);
        grandparent.setColor(0);
        parent.setColor(1);
        return parent;

    }

    private RedBlackNode rightRightRotation(RedBlackNode grandparent){
        RedBlackNode parent = (RedBlackNode)grandparent.right();

        grandparent.setRight((parent.left()));
        parent.setLeft(grandparent);
        grandparent.setColor(0);
        parent.setColor(1);
        return parent;

    }

    private RedBlackNode leftRightRotation(RedBlackNode grandparent){
        RedBlackNode parent = (RedBlackNode)grandparent.left();
        RedBlackNode child = (RedBlackNode)parent.right();

        parent.setRight(child.left());
        child.setLeft(parent);
        grandparent.setLeft(child);

        return leftLeftRotation(grandparent);

    }

    private RedBlackNode rightLeftRotation(RedBlackNode grandparent){
        RedBlackNode parent = (RedBlackNode)grandparent.right();
        RedBlackNode child = (RedBlackNode)parent.left();

        parent.setLeft(child.right());
        child.setRight(parent);
        grandparent.setRight(child);

        return rightRightRotation(grandparent);

    }
}
