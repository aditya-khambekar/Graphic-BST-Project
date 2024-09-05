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
            Storage.println(c+" added as root. ");
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
                Storage.println(tba.value() + " added as right child of " + parent.value()+". ");
                return;
            }else{
                Storage.println(tba.value() + " greater than " + parent.value()+". ");
                add((RedBlackNode)parent.right(), tba);
                
            }
        }
        else if(tba.value().compareTo(parent.value())<0){
            if(parent.left()==null){
                parent.setLeft(tba);
                Storage.println(tba.value() + " added as left child of " + parent.value()+". ");
                return;
            }else{
                Storage.println(tba.value() + " less than " + parent.value()+". ");
                add((RedBlackNode)parent.left(), tba);
                
            }
        }else{
            Storage.println(tba.value() + " already in tree. ");
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
        if(((RedBlackNode)grandparent.left())!=null&&((RedBlackNode)grandparent.left()).getColor()==0&&((RedBlackNode)grandparent.right())!=null&&((RedBlackNode)grandparent.right()).getColor()==0){
            Storage.println("Color Swap with "+grandparent.value()+" as parent");
            grandparent.setColor(0);
            ((RedBlackNode)grandparent.left()).setColor(1);
            ((RedBlackNode)grandparent.right()).setColor(1);
        }
    }

    public void forceColorSwap(RedBlackNode grandparent){
        if(true){
            Storage.println("Color Swap with "+grandparent.value()+" as parent");
            grandparent.setColor(grandparent.getColor()-1);
            if((RedBlackNode)grandparent.left()!=null){((RedBlackNode)grandparent.left()).setColor(((RedBlackNode)grandparent.left()).getColor()+1);}
            if((RedBlackNode)grandparent.left()!=null){((RedBlackNode)grandparent.right()).setColor(((RedBlackNode)grandparent.right()).getColor()+1);}
        }
    }

    private RedBlackNode leftLeftRotation(RedBlackNode grandparent){
        RedBlackNode parent = (RedBlackNode)grandparent.left();

        
        grandparent.setLeft((parent.right()));
        parent.setRight(grandparent);
        grandparent.setColor(grandparent.getColor()-1);
        parent.setColor(parent.getColor()+1);
        return parent;

    }

    private RedBlackNode rightRightRotation(RedBlackNode grandparent){
        RedBlackNode parent = (RedBlackNode)grandparent.right();

        
        grandparent.setRight((parent.left()));
        parent.setLeft(grandparent);
        grandparent.setColor(grandparent.getColor()-1);
        parent.setColor(parent.getColor()+1);
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


    private RedBlackNode simpleCaseDeletion(RedBlackNode nodeToRemove){
        Storage.println("Simple Case Deletion of "+nodeToRemove.value());
        RedBlackNode child = (RedBlackNode) nodeToRemove.left();
        if(nodeToRemove.right()!=null){
            child = (RedBlackNode) nodeToRemove.right();
        }

        nodeToRemove.setLeft(null);
        nodeToRemove.setRight(null);
        try {
            child.setColor(1);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return child;

    }

    private RedBlackNode rightRightRestructure(RedBlackNode grandparent){
        Storage.println("RightRight Restructure with "+grandparent.value()+" as parent. ");
        grandparent = rightRightRotation(grandparent);
        forceColorSwap(grandparent);
        if(grandparent.right().right()!=null){
            ((RedBlackNode)grandparent.right().right()).setColor(((RedBlackNode)grandparent.right().right()).getColor()-1);
        }

        return grandparent;
    }


    private RedBlackNode leftLeftRestructure(RedBlackNode grandparent){
        Storage.println("leftLeft Restructure with "+grandparent.value()+" as parent. ");
        grandparent = leftLeftRotation(grandparent);
        forceColorSwap(grandparent);
        if(grandparent.left().left()!=null){
            ((RedBlackNode)grandparent.left().left()).setColor(((RedBlackNode)grandparent.left().left()).getColor()-1);
        }

        return grandparent;
    }

    private RedBlackNode rightLeftRestructure(RedBlackNode grandparent){
        Storage.println("RightLeft Restructure with "+grandparent.value()+" as parent. ");
        grandparent.setRight(leftLeftRotation((RedBlackNode) grandparent.right()));
        return rightRightRestructure(grandparent);
        
    }


    private RedBlackNode leftRightRestructure(RedBlackNode grandparent){
        Storage.println("LeftRight Restructure with "+grandparent.value()+" as parent. ");
        grandparent.setLeft(rightRightRotation((RedBlackNode) grandparent.left()));
        return leftLeftRestructure(grandparent);
    }

    private void recolor(RedBlackNode grandparent){
        Storage.println("Recolor with "+grandparent.value()+" as parent. ");
        if(true){
            grandparent.setColor(grandparent.getColor()+1);
            if((RedBlackNode)grandparent.left()!=null){((RedBlackNode)grandparent.left()).setColor(((RedBlackNode)grandparent.left()).getColor()-1);}
            if((RedBlackNode)grandparent.right()!=null){((RedBlackNode)grandparent.right()).setColor(((RedBlackNode)grandparent.right()).getColor()-1);}
        }
    }

    private RedBlackNode adjustmentLeft(RedBlackNode grandparent){
        Storage.println("adjustment Left with "+grandparent.value()+" as parent. ");
        RedBlackNode sibling = (RedBlackNode)grandparent.right();
        rightRightRotation(grandparent);
        recolor(grandparent);
        return sibling;
    }

    private RedBlackNode adjustmentRight(RedBlackNode grandparent){
        Storage.println("adjustment Right with "+grandparent.value()+" as parent. ");
        RedBlackNode sibling = (RedBlackNode)grandparent.left();
        leftLeftRotation(grandparent);
        recolor(grandparent);
        return sibling;
    }

    private RedBlackNode specialCaseLeft(RedBlackNode grandparent){
        Storage.println("Special Case Left with "+grandparent.value()+" as parent. ");
        RedBlackNode sibling = (RedBlackNode)grandparent.right();
        forceColorSwap(sibling);
        grandparent.setRight(sibling.left());
        sibling.setLeft(grandparent);
        recolor(grandparent);
        return sibling;
    }

    private RedBlackNode specialCaseRight(RedBlackNode grandparent){
        Storage.println("Special Case Right with "+grandparent.value()+" as parent. ");
        RedBlackNode sibling = (RedBlackNode)grandparent.left();
        forceColorSwap(sibling);
        grandparent.setLeft(sibling.right());
        sibling.setRight(grandparent);
        recolor(grandparent);
        return sibling;
    }

    private RedBlackNode parentSearch(RedBlackNode b, Comparable t){
        
        if(b==null){
           
            return null;
        }

        colorSwap(b);

        if(t.compareTo(b.value())<0){
            if(b.left()!=null&&t.equals(b.left().value())){
                //Storage.println("Parent of " +t+" is "+b.value());
                return b;
            }
            else if(b.left()!=null){
              
                if(parentSearch((RedBlackNode)b.left(), t)==null){
                    return parentSearch((RedBlackNode)b.right(), t);
                }
                return parentSearch((RedBlackNode)b.left(), t);
                
            }
            else{
                return null;
            }
        }

        if(t.compareTo(b.value())>0){
            if(b.right()!=null&&t.equals(b.right().value())){
               //Storage.println("Parent of " +t+" is "+b.value());
                return b;
            }
            else if(b.right()!=null){
                
                if(parentSearch((RedBlackNode)b.right(), t)==null){
                    return parentSearch((RedBlackNode)b.left(), t);
                }
                return parentSearch((RedBlackNode)b.right(), t);
            }
            else{
                return null;
            }
        }
        return null;
    }

    public RedBlackNode remove(Comparable target){
        removeFirst(target);
        root = deleteFix(root);
        RedBlackNode removed = (RedBlackNode) naiveRemove(target);
        fixTree();
        root.setColor(1);
        return removed;
    }

    public BinaryNode naiveRemove(Comparable target){
        if(root==null){return null;}

        BinaryNode temp = root;

        if(target.equals(root.value())){
           if(root.left()==null&&root.right()==null){ //root is degree 0
                root = null;
                
                return temp;
           }else if(root.right()==null){//degree 1 and left child
                root = (RedBlackNode) root.left();
                temp.setLeft(null);
                
                return temp;
           }else if(root.left()==null){//degree 1 and right child
                root = (RedBlackNode) root.right();
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
                return naiveRemove(root.right(), target);
           }
           
        }
        
        return naiveRemove(this.root, target);
    }

    public BinaryNode naiveRemove(BinaryNode start, Comparable target){
        if(start==null){return null;}

        BinaryNode nodeToRemove, inorderSuccessor;
        RedBlackNode parent = this.parentSearch((RedBlackNode)start,target);
       

        if(parent==null){return null;}

        Boolean isLeft = target.compareTo(parent.value())<0;//target is parent's left child

        if(isLeft){
            nodeToRemove = parent.left();
            
        }else{
            nodeToRemove = parent.right();
        }

        BinaryNode temp = nodeToRemove;

        if(nodeToRemove.left()==null&&nodeToRemove.right()==null){ //nodeToRemove is degree 0
            if(isLeft){
                parent.setLeft(null);
                
            }else{
                parent.setRight(null);
            }
            
            return nodeToRemove;
       }else if(nodeToRemove.right()==null){//degree 1 and left child
            if(isLeft){
                parent.setLeft(nodeToRemove.left());
            }else{
                parent.setRight(nodeToRemove.left());
            }
            temp.setLeft(null);
            
            return temp;
       }else if(nodeToRemove.left()==null){//degree 1 and right child
            if(isLeft){
                parent.setLeft(nodeToRemove.right());
            }else{
                parent.setRight(nodeToRemove.right());
            }
            temp.setLeft(null);
            
            return temp;
       }else{//degree 2
            BinaryNode successor = successor(nodeToRemove);
            swap(nodeToRemove, successor);
            if(successor.equals(nodeToRemove.right())){
                nodeToRemove.setRight(successor.right());
                successor.setRight(null);
                return successor;
            }
            return naiveRemove(nodeToRemove.right(), target);
       }
       
    }

    public RedBlackNode removeFirst(Comparable target){
        if(root==null){return null;}


        RedBlackNode temp = this.root;
        colorSwap(temp);
        if(target.equals(root.value())){
           if(root.left()==null&&root.right()==null){ //root is degree 0
                root = null;
                return temp;
           }else if(root.right()==null){//degree 1 and left child
                temp = simpleCaseDeletion(temp);
                return temp;
           }else if(root.left()==null){//degree 1 and right child
                temp = simpleCaseDeletion(temp);
                return temp;
           }else{//degree 2
                
                BinaryNode successor = successor(root);
                swap(root, successor);
                if(successor.equals(root.right())){
                    root.setRight(successor.right());
                    successor.setRight(null);
                    
                    return (RedBlackNode) successor;
                }
                return degreeUp((RedBlackNode)root.right(), target);
           }
           
        }
        
        return degreeUp(root, target);
    }

    public RedBlackNode degreeUp(RedBlackNode start, Comparable target){
        if(start==null){return null;}

        RedBlackNode nodeToRemove;
        RedBlackNode parent = parentSearch((RedBlackNode)start,target);

        Boolean isLeft = target.compareTo(parent.value())<0;//target is parent's left child

        if(isLeft){
            nodeToRemove = (RedBlackNode) parent.left();
        }else{
            nodeToRemove = (RedBlackNode) parent.right();
        }

        if(parent==null){return null;}

        if(nodeToRemove.left()!=null&&nodeToRemove.right()!=null){//degree 2
            BinaryNode successor = successor(nodeToRemove);
            swap(nodeToRemove, successor);
            if(successor.equals(nodeToRemove.right())){
                nodeToRemove.setRight(successor.right());
                successor.setRight(null);
                return (RedBlackNode) successor;
            }
            return (RedBlackNode) degreeUp((RedBlackNode) nodeToRemove.right(), target);
       }

       //check if simple case deletion
       Boolean simpleCaseDeletion = false;

       try {
        if(nodeToRemove.getColor()==0){
            simpleCaseDeletion = true;
        }
       } catch (Exception e) {
        // TODO: handle exception
       }

       try {
        if(((RedBlackNode) nodeToRemove.left()).getColor()==0){
            simpleCaseDeletion = true;
        }
       } catch (Exception e) {
        // TODO: handle exception
       }

       try {
        if(((RedBlackNode) nodeToRemove.right()).getColor()==0){
            simpleCaseDeletion = true;
        }
       } catch (Exception e) {
        // TODO: handle exception
       }

       
       if(simpleCaseDeletion){
        if(isLeft){
            parent.setLeft(simpleCaseDeletion(nodeToRemove));
        }else{
            parent.setRight(simpleCaseDeletion(nodeToRemove));
        }
        return nodeToRemove;
       }


        nodeToRemove.setColor(nodeToRemove.getColor()+1);


       return nodeToRemove;
       
    }

    private RedBlackNode deleteFix(RedBlackNode r){
        colorSwap(r);

        if(r.left()!=null){r.setLeft(deleteFix((RedBlackNode) r.left()));}
        if(r.right()!=null){r.setRight(deleteFix((RedBlackNode) r.right()));}


        RedBlackNode right = new RedBlackNode(null);
        right.setColor(1);
        RedBlackNode left = new RedBlackNode(null);
        left.setColor(1);

        if(r.right()!=null){
            right = (RedBlackNode)r.right();
        }
        if(r.left()!=null){
            left = (RedBlackNode)r.left();
        }

        RedBlackNode rightLeft = new RedBlackNode(null); if(right.value()!=null&&r.right().left()!=null){rightLeft=(RedBlackNode)r.right().left();}
        RedBlackNode rightRight = new RedBlackNode(null); if(right.value()!=null&&r.right().right()!=null){rightRight=(RedBlackNode)r.right().right();}
        RedBlackNode leftRight = new RedBlackNode(null); if(left.value()!=null&&r.left().right()!=null){leftRight =(RedBlackNode)r.left().right();}
        RedBlackNode leftLeft = new RedBlackNode(null); if(left.value()!=null&&r.left().left()!=null){leftLeft=(RedBlackNode)r.left().left();}
        if(rightLeft.value()==null){rightLeft.setColor(1);}
        if(rightRight.value()==null){rightRight.setColor(1);}
        if(leftRight.value()==null){leftRight.setColor(1);}
        if(leftLeft.value()==null){leftLeft.setColor(1);}
        
        if((left).getColor()==2||(right).getColor()==2){//there is a BB node somewhere in the 2 children
            if(r.height()==1){//no children subtree w/recolor
                if((right.getColor()==2||left.getColor()==2)){
                    Boolean nullIsLeftChild = left.getColor()==2;
                    if(nullIsLeftChild&&right.getColor()==1){
                        recolor(r);
                        return r;
                    }else if(!nullIsLeftChild&&left.getColor()==1){
                        recolor(r);
                        return r;
                    }
                }
            }
            
            if(r.height()<2){
                return r;
            }

            if(r.getColor()==1){//black parent, can choose restructure, adjustment or recolor
                if(((right.getColor()==2&&left.getColor()==1)||(left.getColor()==2&&right.getColor()==1))){//restructure, black sibling and black parent
                    Boolean nullIsLeftChild = left.getColor()==2;
                    if(nullIsLeftChild&&(rightRight.getColor()==0)){//left child, children of right are reds
                        return rightRightRestructure(r);
                    }else if(nullIsLeftChild&&(rightLeft.getColor()==0)){//left child, children of right are reds
                        return rightLeftRestructure(r);
                    }else if(!nullIsLeftChild&&(leftLeft.getColor()==0)){
                        return leftLeftRestructure(r);
                    }else if(!nullIsLeftChild&&(leftRight.getColor()==0)){
                        return leftRightRestructure(r);
                    }
                }

                if(((right.getColor()==2&&left.getColor()==0)||(left.getColor()==2&&right.getColor()==0))){//adjustment, red sibling and blacl parent;
                    Boolean nullIsLeftChild = left.getColor()==2;
                    if(nullIsLeftChild){
                        return adjustmentLeft(r);
                    }else if(!nullIsLeftChild){
                        return adjustmentRight(r);
                    }
                }

                recolor(r);//if there is a null node and black parent and it doesnt meet restructure or adjustment, then recolor and propagate.
                return r;

            }

            if(r.getColor()==0){
                Boolean nullIsLeftChild = left.getColor()==2;
                if(nullIsLeftChild&&(rightRight.getColor()==0||rightLeft.getColor()==0)){//left child, children of right are reds, use special case deletion
                    return specialCaseLeft(r);
                }else if(!nullIsLeftChild&&(leftRight.getColor()==0||leftLeft.getColor()==0)){
                    return specialCaseRight(r);
                }

                recolor(r);//if red parent and not special case deletion, recolor and propagate
                return r;
            }

        }
        return r;
    }



}
