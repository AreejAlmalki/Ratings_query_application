
import java.util.*;
import java.io.*;

public class test {

    static Scanner read = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        
        RatingManager r = new RatingManager();
        

        
       r = RatingManager.read("ratings.txt");
        System.out.println("read done");
        LinkedList<Integer> list =r.getHighestRatedItems();
        
        r.addRating(new Rating(10,66,5));
        
        LinkedList<Rating> actual = r.getUserRatings(20);
         System.out.println(actual);
        
        list.findFirst();
        while(! list.last()){
            System.out.println(list.retrieve());
            list.findNext();
        }
         System.out.println(list.retrieve());
        
        double d = r.getAverageUserRating(19);
        if(d==-1)
            System.out.println("cannot find item");
        System.out.println(d);
        
    }
}
