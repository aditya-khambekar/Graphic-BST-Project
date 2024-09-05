public class AVLNode extends BinaryNode{
    
    public AVLNode(){
        super();
    }

    public AVLNode(Comparable c, BinaryNode left, BinaryNode right){
        super(c, left, right);
    }

    public AVLNode(Comparable c){
        super(c);
    }

    public Integer balanceFactor(){
        int leftH = -1;
        int rightH = -1;

        if(this.left()!=null){
            leftH = ((AVLNode)this.left()).height();
        }

        if(this.right()!=null){
            rightH = ((AVLNode)this.right()).height();
        }

        return leftH-rightH;
    }

    public Integer absBf(){
        return Math.abs(balanceFactor());
    }

    

    
}
