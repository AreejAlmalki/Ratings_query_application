
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RatingManager {

    private int sizeItem;
    private int sizeUser;
    private BST<BST<Rating>> ratingItem;
    private BST<BST<Rating>> ratingUser;
// Constructor

    public RatingManager() {
        sizeItem = 0;
        sizeUser = 0;
        ratingItem = new BST<>();
        ratingUser = new BST<>();
    }
    // Read ratings from a file and create a RatingManager object that stores these ratings. The ratings must be inserted in their order of appearance in the file.

    public static RatingManager read(String fileName) {

        RatingManager result = new RatingManager();
        Scanner scan = null;
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
        } catch (NoSuchElementException ex) {
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
        // LinkedList<Rating> list = null;
        BST<Rating> result = null;

        if (ratingItem.empty()) {
            result = new BST<>();
            result.insert(rating.getUserId(), rating);
            ratingItem.insert(rating.getItemId(), result);
            sizeItem++;
        } else if (ratingItem.findKey(rating.getItemId())) {

            result = ratingItem.retrieve();
            result.insert(rating.getUserId(), rating);
        } else {
            result = new BST<>();
            result.insert(rating.getUserId(), rating);
            ratingItem.insert(rating.getItemId(), result);
            sizeItem++;
        }

    }

    private void addRatingUser(Rating rating) {
        BST<Rating> result = null;

        if (ratingUser.empty()) {
            result = new BST<>();
            result.insert(rating.getItemId(), rating);
            ratingUser.insert(rating.getUserId(), result);
            sizeUser++;
        } else if (ratingUser.findKey(rating.getUserId())) {

            result = ratingUser.retrieve();
            result.insert(rating.getItemId(), rating);
        } else {
            result = new BST<>();
            result.insert(rating.getItemId(), rating);
            ratingUser.insert(rating.getUserId(), result);
            sizeUser++;
        }

    }

// Return all ratings given by user i. 
    public LinkedList<Rating> getUserRatings(int i) {
        LinkedList<Rating> result = null;
        BST<Rating> Items = null;
        if (ratingUser.findKey(i)) {
            Items = ratingUser.retrieve();
            result = Items.getAllData();
        }

        return result;

    }

// Return all ratings given to item j
    public LinkedList<Rating> getItemRatings(int j) {
        LinkedList<Rating> result = null;
        BST<Rating> users = null;
        if (ratingItem.findKey(j)) {
            users = ratingItem.retrieve();
            result = users.getAllData();

        }

        return result;
    }

    // Return the list of highest rated items
    public LinkedList<Integer> getHighestRatedItems() {
        LinkedList<Integer> result = new LinkedList<>();
        double[] avg = new double[sizeUser];
        double maxAvg = 0;
        Rating r;
        int i;
        LinkedList<BST<Rating>> ratingListItem = ratingItem.getAllData();
        ratingListItem.findFirst();
        for (i = 0; !ratingListItem.last(); i++) {
            r = ratingListItem.retrieve().retrieve();
            avg[i] = this.getAverageItemRating(r.getItemId());
            if (maxAvg < avg[i]) {
                maxAvg = avg[i];
                result = new LinkedList<>();
                result.insert(r.getItemId());
            } else if (maxAvg == avg[i]) {
                result.insert(r.getItemId());
            }
            ratingListItem.findNext();
        }//end for 
        r = ratingListItem.retrieve().retrieve();
        avg[i] = this.getAverageItemRating(r.getItemId());
        if (maxAvg < avg[i]) {
            maxAvg = avg[i];
            result = new LinkedList<>();
            result.insert(r.getItemId());
        } else if (maxAvg == avg[i]) {
            result.insert(r.getItemId());
        }
        return result;

    }

    // Return the average rating of item j. If i has no ratings, -1 is returned
    public double getAverageItemRating(int j) {
        LinkedList<Rating> result = this.getItemRatings(j);
        int count = 0;
        double avg = 0;
        double sum = 0;
        if (result != null) {
            result.findFirst();
            while (!result.last()) {
                sum += result.retrieve().getValue();
                count++;
                result.findNext();
            }
            sum += result.retrieve().getValue();
            count++;
            avg = sum / count;
            return avg;
        }

        return -1;
    }

    // Return the average rating given by user i. If i has no ratings, -1 is returned
    public double getAverageUserRating(int i) {
        LinkedList<Rating> result = this.getUserRatings(i);
        int count = 0;
        double avg = 0;
        double sum = 0;
        if (result != null) {
            result.findFirst();
            while (!result.last()) {
                sum += result.retrieve().getValue();
                count++;
                result.findNext();
            }
            sum += result.retrieve().getValue();
            count++;
            avg = sum / count;
            return avg;
        }

        return -1;
    }
    //***************************************************************************

    // Return the rating of user i for item j. If there is no rating, -1 is returned.
    public int getRating(int i, int j) {
        BST<Rating> result = null;
        if (ratingUser.findKey(i)) {
            result = ratingUser.retrieve();
            if (result.findKey(j)) {
                return result.retrieve().getValue();
            }
        }
        return -1;
    }

    // Return the number of keys to compare with in order to find the rating of user i for item j.
    public int nbComp(int i, int j) {
        BST<Rating> result = null;
        int count = 0;
        count = ratingUser.nbComp(i);
        if (ratingUser.findKey(i)) {
            result = ratingUser.retrieve();
            count = count + result.nbComp(j);
        }
        return count;
    }

    // Compute the distance between the two users ui and uj   
    //. If ui and uj have no common item in their ratings, then Double.POSITIVE_INFINITY is returned.
    public double getDist(int ui, int uj) {
        int countI = 0, countJ = 0, countIJ = 0;
        BST<Rating> BST_UI = null;
        BST<Rating> BST_UJ = null;

        LinkedList<Rating> uiListItems = new LinkedList<>();
        LinkedList<Rating> ujListItems = new LinkedList<>();

        if (ratingUser.findKey(ui)) {
            BST_UI = ratingUser.retrieve();
            uiListItems = BST_UI.getAllData();
            if (ratingUser.findKey(uj)) {
                BST_UJ = ratingUser.retrieve();
                ujListItems = BST_UJ.getAllData();

                if (uiListItems.empty() || ujListItems.empty()) {
                    return Double.POSITIVE_INFINITY;
                }

                LinkedList<Integer> Uij_ListItem = new LinkedList<>();
                uiListItems.findFirst();
                while (!uiListItems.last()) {
                    ujListItems.findFirst();
                    while (!ujListItems.last()) {
                        if (uiListItems.retrieve().getItemId() == ujListItems.retrieve().getItemId()) {
                            Uij_ListItem.insert(ujListItems.retrieve().getItemId());
                            countIJ++;
                        }
                        ujListItems.findNext();
                    }
                    if (uiListItems.retrieve().getItemId() == ujListItems.retrieve().getItemId()) {
                        Uij_ListItem.insert(ujListItems.retrieve().getItemId());
                        countIJ++;

                    }
                    uiListItems.findNext();
                }
                ujListItems.findFirst();
                while (!ujListItems.last()) {
                    if (uiListItems.retrieve().getItemId() == ujListItems.retrieve().getItemId()) {
                        Uij_ListItem.insert(ujListItems.retrieve().getItemId());
                        countIJ++;
                    }
                    ujListItems.findNext();
                }
                if (uiListItems.retrieve().getItemId() == ujListItems.retrieve().getItemId()) {
                    Uij_ListItem.insert(ujListItems.retrieve().getItemId());
                    countIJ++;

                }

                if (countIJ != 0) {
                    Uij_ListItem.findFirst();
                    double distance = 0;
                    double d = 0;
                    int Item;
                    for (int i = 0; i < countIJ; i++) {
                        Item = Uij_ListItem.retrieve();
                        BST_UI.findKey(Item);
                        BST_UJ.findKey(Item);
                        d += Math.pow((BST_UI.retrieve().getValue() - BST_UJ.retrieve().getValue()), 2);
                        //    distance += Math.sqrt(Math.pow((BST_UI.retrieve().getValue() - BST_UJ.retrieve().getValue()), 2)) / countIJ;
                        Uij_ListItem.findNext();
                    }
                    distance = Math.sqrt(d) / countIJ;

                    return distance;
                }
            }
        }

        return Double.POSITIVE_INFINITY;
    }

    // Return a list of at most k nearest neighbors to user i from a list of users. User i and users at infinite distance should not be included (the number of users returned can therefore be less than k).
    public LinkedList<Integer> kNNUsers(int i, LinkedList<Integer> users, int k) {
        if (users == null || users.empty() || k == 0 || k < 0 || ratingUser == null || !ratingUser.findKey(i)) {
            return null;
        }
        int Qsize = 0;
        LinkedList l = users;

        l.findFirst();
        if (!l.empty()) {
            while (!l.last()) {
                Qsize++;
                l.findNext();
            }
            Qsize++;
        }

        LinkedList<Integer> nearest = new LinkedList<>();
        PQ<Integer> neighbors = new PQ<>(Qsize);
        double dist, in = Double.POSITIVE_INFINITY;
        users.findFirst();
        while (!users.last()) {
            dist = getDist(i, users.retrieve());
            if (dist != in) {
                neighbors.enqueue(dist, users.retrieve());
            }
            users.findNext();

        }
        dist = getDist(i, users.retrieve());
        if (dist != in) {
            neighbors.enqueue(dist, users.retrieve());
        }
        if (neighbors.length() == 0) {
            return null;
        }
        if (neighbors.length() > k) {
            while (neighbors.length() != k) {
                neighbors.serve();
            }
        }
        while (neighbors.length() != 0) {
            nearest.insert(neighbors.serve().data);
        }

        return nearest;

        // return null;
    }

// Return the average rating given to item j by a list of users. If the list users is empty or non of the users it contains rated item j, then the global average rating of item j (as computed by getAverageItemRating(j)) is returned.
    public double getAverageRating(int j, LinkedList<Integer> users) {
        LinkedList<Rating> result = this.getItemRatings(j);
        if (users == null || result == null) {
            return getAverageItemRating(j);
        }

        double avg = 0;
        int count = 0;
        boolean flag = false;
        if (!result.empty() && !users.empty()) {
            users.findFirst();
            while (!users.last()) {
                result.findFirst();
                while (!result.last()) {
                    if (result.retrieve().getUserId() == users.retrieve()) {
                        avg += result.retrieve().getValue();
                        count++;
                        flag = true;
                    }

                    result.findNext();
                }

                if (result.retrieve().getUserId() == users.retrieve()) {
                    avg += result.retrieve().getValue();
                    count++;
                    flag = true;
                }
                if (flag == false) {
                    return getAverageItemRating(j);
                }
                users.findNext();
                flag = false;

            }
            result.findFirst();
            while (!result.last()) {
                if (result.retrieve().getUserId() == users.retrieve()) {
                    avg += result.retrieve().getValue();
                    count++;
                    flag = true;

                }
                result.findNext();
            }
            if (result.retrieve().getUserId() == users.retrieve()) {
                avg += result.retrieve().getValue();
                count++;
                flag = true;

            }
            if (flag == false) {
                return getAverageItemRating(j);
            }
            if (count != 0) {
                return avg / count;
            }
            return getAverageItemRating(j);
        }
        return getAverageItemRating(j);
    }

    // Return an estimation of the rating given by user i for item j using k nearest neighbor users.
    public double getEstimatedRating(int i, int j, int k) {
        int r = getRating(i, j);

        if (r != -1) {
            return r;
        }
        LinkedList<Rating> ratings = getItemRatings(j);
        LinkedList<Integer> users = new LinkedList<Integer>();
        if ((ratings
                != null) && !ratings.empty()) {
            ratings.findFirst();
            while (!ratings.last()) {
                users.insert(ratings.retrieve().getUserId());
                ratings.findNext();
            }
            users.insert(ratings.retrieve().getUserId());
        }
        LinkedList<Integer> knn = kNNUsers(i, users, k);

        return getAverageRating(j, knn);
    }

}
