public class stupid {
    private String str = "abcXabcXabc";
    private char c = 'X';

    public stupid(){

    }

    public String getClean(){
        while(str.indexOf(c)!=-1){
            str = str.substring(0, c) + str.substring(c+1);
        }

        return str;
    }

    public static void main (String[]args){
        stupid s = new stupid();

        System.out.println(s.getClean());
    }
}
