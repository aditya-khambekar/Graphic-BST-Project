public  class RedBlackNode extends BinaryNode{
    private int color;

    public RedBlackNode(){
        super();
        color = 0;
    }

    public RedBlackNode(Comparable c){
        super(c);
        color = 0;
    }

    public RedBlackNode(Comparable c, RedBlackNode left, RedBlackNode right){
        super(c, left, right);
        color = 0;
    }

    public void setColor(int color){
        this.color = color;
    }

    public int getColor(){
        return color;
    }
}
