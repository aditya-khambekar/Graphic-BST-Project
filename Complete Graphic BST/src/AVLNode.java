public class AVLNode extends BinaryNode{
    
    private Integer balanceFactor = 0;

    public AVLNode(){
        super();
    }

    public AVLNode(Comparable c, BinaryNode left, BinaryNode right){
        super(c, left, right);
    }

    public AVLNode(Comparable c){
        super(c);
    }

    public Integer balanceFactor(){//change to BalanceFactor to fix;
        int leftH = -1;
        int rightH = -1;

        if(this.left()!=null){
            leftH = ((AVLNode)this.left()).height();
        }

        if(this.right()!=null){
            rightH = ((AVLNode)this.right()).height();
        }

        balanceFactor = leftH-rightH;
        return leftH-rightH;
    }

    public Integer height(){
        System.out.println("Height Method Called");
        if(this.left() == null&&this.right()==null){
            return 0;
        }else{
            int leftHeight=-1;
            int rightHeight=-1;

            if(this.left()!=null){
                leftHeight = this.left().height();
            }

            if(this.right()!=null){
                rightHeight = this.right().height();
            }
            balanceFactor = leftHeight - rightHeight;
            return 1+Math.max(leftHeight, rightHeight);
        }
    }

    public Integer RbalanceFactor(){
        System.out.println("Balance Factor called of "+value());
        return balanceFactor();
    }

    public Integer appendBalanceFactor(Boolean isIncrement){
        if(isIncrement){
            return ++balanceFactor;
        }else{
            return --balanceFactor;
        }
    }
        
    public Integer absBf(){
        return Math.abs(balanceFactor());
    }

    

    
}
