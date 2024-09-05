public class BinaryNode {
    public Comparable value;
    public BinaryNode left;
    public BinaryNode right;

    public BinaryNode(){
        value = null;
        left = null;
        right =null;
    }

    public BinaryNode(Comparable c, BinaryNode left, BinaryNode right){
        value = c;
        this.left = left;
        this.right = right;
    }

    public BinaryNode(Comparable c){
        value = c;
        left = null;
        right =null;
    }


    

    public Integer height(){

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

            return 1+Math.max(leftHeight, rightHeight);
        }
    }

    public void setLeft(BinaryNode n){
        left = n;
    }

    public void setRight(BinaryNode n){
        right = n;
    }

    public void setValue(Comparable c){
        value = c;
    }

    public BinaryNode left(){
        return left;
    }

    public BinaryNode right(){
        return right;
    }

    public Comparable value(){
        return value;
    }

        


}
