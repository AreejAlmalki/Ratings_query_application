
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RatingManager {

    private int sizeItem;
    private int sizeUser;
    private LinkedList<LinkedList<Rating>> ratingListItem;
    private LinkedList<LinkedList<Rating>> ratingListUser;
    // Constructor

    public RatingManager() {
        sizeItem = 0;
        sizeUser = 0;
        ratingListItem = new LinkedList<>();
        ratingListUser = new LinkedList<>();
    }

    // Read ratings from a file and create a RatingManager object that stores these ratings
    public static RatingManager read(String fileName) {
        RatingManager result = new RatingManager();
        Scanner scan;
        try {
            scan = new Scanner(new File(fileName));
            while (scan.hasNextLine()) {
                scan.next();
                int userID = scan.nextInt();
                int value = scan.nextInt();
                int itemID = scan.nextInt();
                Rating rating = new Rating(userID, itemID, value);
                result.addRating(rating);

            }
            scan.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    // Add a rating
    public void addRating(Rating rating) {
        addRatingItem(rating);
        addRatingUser(rating);

    }

    private void addRatingItem(Rating rating) {
        LinkedList<Rating> list = null;
        if (ratingListItem.empty()) {
            list = new LinkedList<>();
        } else {
            ratingListItem.findFirst();
            while (!ratingListItem.last()) {

                list = ratingListItem.retrieve();
                if (list.retrieve().getitemId() == rating.getitemId()) {
                    break;
                } else {
                    list = null;
                }
                ratingListItem.findNext();
            }
            if (list == null) {
                list = ratingListItem.retrieve();
                if (list.retrieve().getitemId() != rating.getitemId()) {
                    list = null;
                }
            }

        }
        if (list == null) {
            list = new LinkedList<>();
        }
        list.insert(rating);
        ratingListItem.insert(list);
        sizeItem++;
    }

    private void addRatingUser(Rating rating) {
        LinkedList<Rating> list = null;
        if (ratingListUser.empty()) {
            list = new LinkedList<>();
        } else {
            ratingListUser.findFirst();
            while (!ratingListUser.last()) {

                list = ratingListUser.retrieve();
                if (list.retrieve().getuserId() == rating.getuserId()) {
                    break;
                } else {
                    list = null;
                }
                ratingListUser.findNext();
            }
            if (list == null) {
                list = ratingListUser.retrieve();
                if (list.retrieve().getuserId() != rating.getuserId()) {
                    list = null;
                }
            }

        }
        if (list == null) {
            list = new LinkedList<>();
        }
        list.insert(rating);
        ratingListUser.insert(list);
        sizeUser++;
    }

    // Return all ratings given by user i. Search should be efficient. 
    public LinkedList<Rating> getUserRatings(int i) {
        LinkedList<Rating> result = null;
        ratingListUser.findFirst();
        for (int j = 0; j < sizeUser; j++) {
            Rating r = ratingListUser.retrieve().retrieve();
            if (r.getuserId() == i) {
                result = ratingListUser.retrieve();
                return result;
            }
            ratingListUser.findNext();

        }
        return null;
    }

    // Return all ratings given to item j. Search should be efficient.
    public LinkedList<Rating> getItemRatings(int i) {
        LinkedList<Rating> result ;
        ratingListItem.findFirst();
        for (int j = 0; j < sizeItem; j++) {
            Rating r = ratingListItem.retrieve().retrieve();
            if (r.getitemId() == i) {
                result = ratingListItem.retrieve();
                return result;
            }
            ratingListItem.findNext();

        }
        return null;
    }

    // Return the average rating of item j. If j has no ratings, -1 is returned
    public double getAverageItemRating(int i) {
        double avg;
        double sum = 0;
        int count = 0;
        LinkedList<Rating> list = this.getItemRatings(i);
        list.findFirst();
        while (!list.last()) {
            Rating r = list.retrieve();

            if (r.getitemId() == i) {
                count++;
                sum += r.getvalue();
            }
            list.findNext();
        }
        Rating r = list.retrieve();

        if (r.getitemId() == i) {
            count++;
            sum += r.getvalue();
        }

        if (count == 0) {
            return -1;
        }
        avg = sum / count;
        return avg;
    }

    // Return the average rating given by user i. If i has no ratings, -1 is returned
    public double getAverageUserRating(int i) {
        double avg;
        double sum = 0;
        int count = 0;
        LinkedList<Rating> list = this.getUserRatings(i);
        list.findFirst();
        while (!list.last()) {
            Rating r = list.retrieve();

            if (r.getitemId() == i) {
                count++;
                sum += r.getvalue();
            }
            list.findNext();
        }
        Rating r = list.retrieve();

        if (r.getitemId() == i) {
            count++;
            sum += r.getvalue();
        }

        if (count == 0) {
            return -1;
        }
        avg = sum / count;
        return avg;
    }

    // Return the list of all items having the highest average rating (for example if the highest average rating is 4.9, the method should return all items with average rating 4.9)
    public LinkedList<Integer> getHighestRatedItems() {
        LinkedList<Integer> result = new LinkedList<>();
        ratingListItem.findFirst();
       double [] avg=new double[sizeItem];
       double maxAvg=0;
        for(int i=0;i<sizeItem;i++){
           Rating r=ratingListItem.retrieve().retrieve();
           avg[i]=this.getAverageItemRating(r.getitemId());
           if(maxAvg < avg[i]){
               maxAvg=avg[i];
               result=new LinkedList<>();
               result.insert(r.getitemId());
           }
           else
               if(maxAvg == avg[i])
                   result.insert(r.getitemId());
               
            ratingListItem.findNext();
        }//
        return result;

    }

}
