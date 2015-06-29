package os.proj.p5.pagers;

import java.util.NoSuchElementException;

public class PagerFactory {
	public static AbstractPager createPagerWithScheme(PagingScheme scheme) {
		AbstractPager newPager = null;
		switch (scheme) {
			case FIRST_IN_FIRST_OUT:
				newPager = new FirstInFirstOutPager();
				break;
			case LEAST_FREQUENTLY_USED:
				newPager = new LeastFrequentlyUsedPager();
				break;
			case LEAST_RECENTLY_USED:
				newPager = new LeastRecentlyUsedPager();
				break;
			case OPTIMAL:
				newPager = new OptimalPager();
				break;
			default:
				System.err.println("IT'S A PROBLEM YO!");
				throw new NoSuchElementException();
		}
		return newPager;
	}
}
