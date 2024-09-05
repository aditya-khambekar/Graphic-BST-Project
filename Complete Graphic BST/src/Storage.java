import java.util.ArrayList;

public class Storage {
    public static ArrayList<String> storage = new ArrayList<String>();

    public static void println(String s){
        storage.add(s);
    }

    public static ArrayList<String> getStorage(){
        return storage;
    }

    public static void clear(){
        storage = new ArrayList<String>();
    }

    public static void delay(){
        for(int i = 0; i<10000; i++){
            System.out.println("Delay");
        }
    }
}

