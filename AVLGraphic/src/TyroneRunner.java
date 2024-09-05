import java.util.Scanner;
public class TyroneRunner {
    public static void main(String[]args){
        /*BinaryTree b = new BinaryTree();
        Scanner kb = new Scanner (System.in);
        while(kb.hasNext()){
            b.add(kb.nextInt());
            System.out.println();
            System.out.println("Preorder: "+b.preorder());
            System.out.println("Inorder: "+b.inorder());
            System.out.println("Postorder: "+b.postorder());
            System.out.println();
        }*/

        for (int i = 0; i < 20; i++){
            System.out.print((int)(Math.random()  * 201) + " ");
        }
    }
}
