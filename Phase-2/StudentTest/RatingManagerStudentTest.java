import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

public class RatingManagerStudentTest {
	RatingManager manager;

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return true if l1 is a subset of l2.
	 */
	private static boolean subsetRatings(LinkedList<Rating> l1, LinkedList<Rating> l2) {
		if (l1 == l2) {
			return true;
		}
		if (l1 == null) {
			return true;
		}
		if (l2 == null) {
			return false;
		}
		if (l1.empty()) {
			return true;
		}
		if (l2.empty()) {
			return false;
		}
		boolean contained = true;
		l1.findFirst();
		while (!l1.last()) {
			Rating r1 = l1.retrieve();
			boolean found = false;
			l2.findFirst();
			while (!l2.last()) {
				Rating r2 = l2.retrieve();
				if ((r1.getUserId() == r2.getUserId()) && (r1.getItemId() == r2.getItemId())
						&& (r1.getValue() == r2.getValue())) {
					found = true;
					break;
				} else {
					l2.findNext();
				}
			}
			if (!found) {
				Rating r2 = l2.retrieve();
				if ((r1.getUserId() == r2.getUserId()) && (r1.getItemId() == r2.getItemId())
						&& (r1.getValue() == r2.getValue())) {
					found = true;
				}
			}
			if (!found) {
				contained = false;
				break;
			} else {
				l1.findNext();
			}
		}
		if (contained) {
			Rating r1 = l1.retrieve();
			boolean found = false;
			l2.findFirst();
			while (!l2.last()) {
				Rating r2 = l2.retrieve();
				if ((r1.getUserId() == r2.getUserId()) && (r1.getItemId() == r2.getItemId())
						&& (r1.getValue() == r2.getValue())) {
					found = true;
					break;
				} else {
					l2.findNext();
				}
			}
			if (!found) {
				Rating r2 = l2.retrieve();
				if ((r1.getUserId() == r2.getUserId()) && (r1.getItemId() == r2.getItemId())
						&& (r1.getValue() == r2.getValue())) {
					found = true;
				}
			}
			if (!found) {
				contained = false;
			}
		}

		return contained;
	}

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return true if the two lists are equal (an empty list and a null list
	 *         are considered equal).
	 */
	private static boolean equalRatings(LinkedList<Rating> l1, LinkedList<Rating> l2) {
		if (l1 == l2) {
			return true;
		}
		if (((l1 == null) && l2.empty()) || ((l2 == null) && l1.empty())) {
			return true;
		}
		if ((l1 == null) || (l2 == null)) {
			return false;
		}
		if (l1.empty() != l2.empty()) {
			return false;
		}
		if (l1.empty()) {
			return true;
		}
		int n1 = 0;
		l1.findFirst();
		while (!l1.last()) {
			n1++;
			l1.findNext();
		}
		int n2 = 0;
		l2.findFirst();
		while (!l2.last()) {
			n2++;
			l2.findNext();
		}
		if (n1 != n2) {
			return false;
		}
		return subsetRatings(l1, l2) && subsetRatings(l2, l1);
	}

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return true if l1 is a subset of l2.
	 */
	private static boolean subset(LinkedList<Integer> l1, LinkedList<Integer> l2) {
		if (l1 == l2) {
			return true;
		}
		if (l1 == null) {
			return true;
		}
		if (l2 == null) {
			return false;
		}
		if (l1.empty()) {
			return true;
		}
		if (l2.empty()) {
			return false;
		}
		boolean contained = true;
		l1.findFirst();
		while (!l1.last()) {
			int r1 = l1.retrieve();
			boolean found = false;
			l2.findFirst();
			while (!l2.last()) {
				int r2 = l2.retrieve();
				if ((r1 == r2)) {
					found = true;
					break;
				} else {
					l2.findNext();
				}
			}
			if (!found) {
				int r2 = l2.retrieve();
				if (r1 == r2) {
					found = true;
				}
			}
			if (!found) {
				contained = false;
				break;
			} else {
				l1.findNext();
			}
		}
		if (contained) {
			int r1 = l1.retrieve();
			boolean found = false;
			l2.findFirst();
			while (!l2.last()) {
				int r2 = l2.retrieve();
				if (r1 == r2) {
					found = true;
					break;
				} else {
					l2.findNext();
				}
			}
			if (!found) {
				int r2 = l2.retrieve();
				if (r1 == r2) {
					found = true;
				}
			}
			if (!found) {
				contained = false;
			}
		}

		return contained;
	}

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return true if the two lists are equal (an empty list and a null list
	 *         are considered equal).
	 */
	private static boolean equal(LinkedList<Integer> l1, LinkedList<Integer> l2) {
		if (l1 == l2) {
			return true;
		}
		if (((l1 == null) && l2.empty()) || ((l2 == null) && l1.empty())) {
			return true;
		}
		if ((l1 == null) || (l2 == null)) {
			return false;
		}
		if (l1.empty() != l2.empty()) {
			return false;
		}
		if (l1.empty()) {
			return true;
		}
		int n1 = 0;
		l1.findFirst();
		while (!l1.last()) {
			n1++;
			l1.findNext();
		}
		int n2 = 0;
		l2.findFirst();
		while (!l2.last()) {
			n2++;
			l2.findNext();
		}
		if (n1 != n2) {
			return false;
		}
		return subset(l1, l2) && subset(l2, l1);
	}

	/**
	 * Prints a rating.
	 * 
	 * @param r
	 */
	private static void print(Rating r) {
		System.out.println(r.getUserId() + "\t" + r.getItemId() + "\t" + r.getValue());
	}

	/**
	 * Prints a list of ratings.
	 * 
	 * @param l
	 */
	private static void printRatingsList(LinkedList<Rating> l) {
		System.out.println("----------------------------");
		if ((l == null) || l.empty()) {
			return;
		}
		l.findFirst();
		while (!l.last()) {
			print(l.retrieve());
			l.findNext();
		}
		print(l.retrieve());
		System.out.println("----------------------------");
	}

	/**
	 * Prints a list of integers.
	 * 
	 * @param l
	 */
	private static void printIntegersList(LinkedList<Integer> l) {
		System.out.println("----------------------------");
		if ((l == null) || l.empty()) {
			return;
		}
		l.findFirst();
		while (!l.last()) {
			System.out.println(l.retrieve());
			l.findNext();
		}
		System.out.println(l.retrieve());
		System.out.println("----------------------------");
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRead1() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testRead2() {
		try {
			manager = RatingManager.read("ratings-sample8.txt");
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testAddRating1() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
			{
				LinkedList<Rating> actual = manager.getUserRatings(20);
				LinkedList<Rating> expected = new LinkedList<Rating>();
				expected.insert(new Rating(20, 30, 4));
				expected.insert(new Rating(20, 40, 3));
				assertTrue("The method getUserRatings does not give the correct results",
						equalRatings(expected, actual));
			}
			manager.addRating(new Rating(20, 10, 5));
			{
				LinkedList<Rating> actual = manager.getUserRatings(20);
				LinkedList<Rating> expected = new LinkedList<Rating>();
				expected.insert(new Rating(20, 30, 4));
				expected.insert(new Rating(20, 40, 3));
				expected.insert(new Rating(20, 10, 5));
				assertTrue("The method addRating does not work properly", equalRatings(expected, actual));
			}
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testAddRating2() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
			manager.addRating(new Rating(10, 70, 5));
			{
				LinkedList<Rating> actual = manager.getItemRatings(70);
				LinkedList<Rating> expected = new LinkedList<Rating>();
				expected.insert(new Rating(35, 70, 2));
				expected.insert(new Rating(40, 70, 5));
				expected.insert(new Rating(5, 70, 2));
				expected.insert(new Rating(30, 70, 1));
				expected.insert(new Rating(10, 70, 5));
				assertTrue("The method addRating does not work properly", equalRatings(expected, actual));
			}
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetUserRatings1() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
			LinkedList<Rating> actual = manager.getUserRatings(2);
			LinkedList<Rating> expected = new LinkedList<Rating>();
			assertTrue(
					"The method getUserRatings does not give the correct results which is empty list for not found user ID! ",
					equalRatings(expected, actual));
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetUserRatings2() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
			LinkedList<Rating> actual = manager.getUserRatings(30);
			LinkedList<Rating> expected = new LinkedList<Rating>();
			expected.insert(new Rating(30, 40, 2));
			expected.insert(new Rating(30, 50, 2));
			expected.insert(new Rating(30, 70, 1));
			assertTrue("The method getUserRatings does not give the correct results", equalRatings(expected, actual));
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetItemRatings1() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
			LinkedList<Rating> actual = manager.getItemRatings(5);
			LinkedList<Rating> expected = new LinkedList<Rating>();

			assertTrue(
					"The method getItemRatings does not give the correct results for items that don't exist! it should give an empty list",
					equalRatings(expected, actual));
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetItemRatings2() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
			LinkedList<Rating> actual = manager.getItemRatings(40);
			LinkedList<Rating> expected = new LinkedList<Rating>();
			expected.insert(new Rating(15, 40, 1));
			expected.insert(new Rating(25, 40, 2));
			expected.insert(new Rating(30, 40, 2));
			expected.insert(new Rating(5, 40, 2));
			expected.insert(new Rating(20, 40, 3));
			assertTrue("The method getItemRatings does not give the correct results", equalRatings(expected, actual));
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetAverageItemRating1() {
		try {
			manager = RatingManager.read("ratings-sample3.txt");
			double actual = manager.getAverageItemRating(711);
			double expected = (4.0 + 2.0) / 2;
			assertEquals("The method getItemRatings does not give the correct results", expected, actual, 1.0e-8);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetAverageItemRating2() {
		try {
			manager = RatingManager.read("ratings-sample3.txt");
			double actual = manager.getAverageItemRating(1);
			double expected = -1;
			assertEquals("The method getItemRatings does not give the correct results", expected, actual, 1.0e-8);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetAverageUserRating1() {
		try {
			manager = RatingManager.read("ratings-sample3.txt");
			double actual = manager.getAverageUserRating(1934);
			double expected = (1.0 + 3.0 + 3.0) / 3;
			assertEquals("The method getUserRatings does not give the correct results", expected, actual, 1.0e-8);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetAverageUserRating2() {
		try {
			manager = RatingManager.read("ratings-sample3.txt");
			double actual = manager.getAverageUserRating(51);
			double expected = (4.0) / 1;
			assertEquals("The method getUserRatings does not give the correct results", expected, actual, 1.0e-8);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetAverageUserRating3() {
		try {
			manager = RatingManager.read("ratings-sample3.txt");
			double actual = manager.getAverageUserRating(20);
			double expected = -1;
			assertEquals("The method getUserRatings does not give the correct results", expected, actual, 1.0e-8);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetHighestRatedItems1() {
		try {
			manager = RatingManager.read("ratings-sample7.txt");
			LinkedList<Integer> actual = manager.getHighestRatedItems();
			LinkedList<Integer> expected = new LinkedList<Integer>();
			expected.insert(10);
			expected.insert(20);
			expected.insert(30);
			assertTrue("The method getHighestRatedItems does not give the correct results", equal(expected, actual));
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetHighestRatedItems2() {
		try {
			manager = RatingManager.read("ratings-sample8.txt");
			LinkedList<Integer> actual = manager.getHighestRatedItems();
			LinkedList<Integer> expected = new LinkedList<Integer>();
			expected.insert(10);
			assertTrue("The method getHighestRatedItems does not give the correct results", equal(expected, actual));
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}

	@Test
	public void testGetHighestRatedItems3() {
		try {
			manager = RatingManager.read("ratings-sample9.txt");
			LinkedList<Integer> actual = manager.getHighestRatedItems();
			LinkedList<Integer> expected = new LinkedList<Integer>();
			expected.insert(10);
			expected.insert(20);
			expected.insert(30);
			expected.insert(40);
			expected.insert(50);
			expected.insert(60);
			assertTrue("The method getHighestRatedItems does not give the correct results", equal(expected, actual));
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			fail("Your code threw an exception: " + errors);
		}
	}
}
