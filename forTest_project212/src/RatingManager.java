import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
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
        Scanner scan=null;
        try {
            scan = new Scanner(new File(fileName));
            while (scan.hasNextLine()) {

                int userID = scan.nextInt();
                int itemID = scan.nextInt();
                int value = scan.nextInt();
                scan.next();
                Rating rating = new Rating(userID, itemID, value);
                result.addRating(rating);

            }
            scan.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (NoSuchElementException ex) {
           // ex.printStackTrace();
            scan.close();
            return result;
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
             ratingListItem.insert(list);
             sizeItem++;
        } else {
            ratingListItem.findFirst();
            while (!ratingListItem.last()) {

                list = ratingListItem.retrieve();
                if (list.retrieve().getItemId() == rating.getItemId()) {
                    break;
                } else {
                    list = null;
                }
                ratingListItem.findNext();
            }//end while 
            
            if (list == null) {
                list = ratingListItem.retrieve();
                if (list.retrieve().getItemId() != rating.getItemId()) {
                    list = null;
                }
            }

        }
        if (list == null) {
            list = new LinkedList<>();
             ratingListItem.insert(list);
             sizeItem++;
        }
        list.insert(rating);
       
        
    }

    private void addRatingUser(Rating rating) {
        LinkedList<Rating> list = null;
        if (ratingListUser.empty()) {
            list = new LinkedList<>();
             ratingListUser.insert(list);
             sizeUser++;
        } else {
            ratingListUser.findFirst();
            while (!ratingListUser.last()) {

                list = ratingListUser.retrieve();
                if (list.retrieve().getUserId() == rating.getUserId()) {
                    break;
                } else {
                    list = null;
                }
                ratingListUser.findNext();
            }
            if (list == null) {
                list = ratingListUser.retrieve();
                if (list.retrieve().getUserId() != rating.getUserId()) {
                    list = null;
                }
            }

        }
        if (list == null) {
            list = new LinkedList<>();
             ratingListUser.insert(list);
             sizeUser++;
        }
        list.insert(rating);
       
        
    }

    // Return all ratings given by user i. Search should be efficient. 
    public LinkedList<Rating> getUserRatings(int i) {
        LinkedList<Rating> result = null;
        ratingListUser.findFirst();
        for (int j = 0; j < sizeUser; j++) {
            Rating r = ratingListUser.retrieve().retrieve();
            if (r.getUserId() == i) {
                result = ratingListUser.retrieve();
                return result;
            }
            ratingListUser.findNext();

        }
        return null;
    }

    // Return all ratings given to item j. Search should be efficient.
    public LinkedList<Rating> getItemRatings(int i) {
        LinkedList<Rating> result = null;
        ratingListItem.findFirst();
        for (int j = 0; j < sizeItem; j++) {
            Rating r = ratingListItem.retrieve().retrieve();
            if (r.getItemId() == i) {
                result = ratingListItem.retrieve();
                return result;
            }
            ratingListItem.findNext();

        }
        return null;
    }

    // Return the average rating of item j. If j has no ratings, -1 is returned
    public double getAverageItemRating(int i) {
        double avg = 0;
        double sum = 0;
        int count = 0;
        LinkedList<Rating> list = this.getItemRatings(i);
        if(list==null)
            return -1;
        list.findFirst();
        while (!list.last()) {
            Rating r = list.retrieve();

            if (r.getItemId() == i) {
                count++;
                sum += r.getValue();
            }
            list.findNext();
        }
        Rating r = list.retrieve();

        if (r.getItemId() == i) {
            count++;
            sum += r.getValue();
        }

        if (count == 0) {
            return -1;
        }
        avg = sum / count;
        return avg;
    }

    // Return the average rating given by user i. If i has no ratings, -1 is returned
    public double getAverageUserRating(int i) {
        double avg = 0;
        double sum = 0;
        int count = 0;
        LinkedList<Rating> list = this.getUserRatings(i);
        if(list==null)
            return -1;
        list.findFirst();
        while (!list.last()) {
            Rating r = list.retrieve();

            if (r.getUserId() == i) {
                count++;
                sum += r.getValue();
            }
            list.findNext();
        }
        Rating r = list.retrieve();

        if (r.getUserId() == i) {
            count++;
            sum += r.getValue();
        }

        if (count == 0) {
            return -1;
        }
        avg = sum / count;
        return avg;
    }

    // Return the list of all items having the highest average rating (for example if the highest average rating is 4.9, the method should return all items with average rating 4.9)
    public LinkedList<Integer> getHighestRatedItems() {
      //  this.ratingListItem.display();
        LinkedList<Integer> result = new LinkedList<>();
        double[] avg = new double[sizeItem];
        double maxAvg = 0;
        ratingListItem.findFirst();
        for (int i = 0; i < sizeItem; i++) {
            Rating r = ratingListItem.retrieve().retrieve();
            avg[i] = this.getAverageItemRating(r.getItemId());
            if (maxAvg < avg[i]) {
                maxAvg = avg[i];
                result = new LinkedList<>();
                result.insert(r.getItemId());
            } else if (maxAvg == avg[i]) {
                result.insert(r.getItemId());
            }

            ratingListItem.findNext();
        }//
        System.out.println("max avg: "+ maxAvg);
        return result;

    }

}
