package fabricator.j.tests;

import fabricator.enums.DateFormat;
import fabricator.enums.DateRangeType;
import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CalendarJavaTest extends JavaBaseTest {

	@Test
	public void testDatesRange() {
		final List<String> yearRange = calendar.datesRange()
				.startYear(2001)
				.startMonth(1)
				.startDay(1)
				.stepEvery(1, DateRangeType.YEARS)
				.endYear(2010)
				.endMonth(1)
				.endDay(1)
				.asStringsJavaList();
		assertEquals(9, yearRange.size());
	}

	@DataProvider
	public Object[][] datesRangeDP() {
		return new Object[][]{
				{ 2001, 1, 1, 2010, 1, 1, DateRangeType.YEARS, 1, 9 },
				{ 2001, 1, 1, 2010, 1, 1,DateRangeType.YEARS, 2, 5 },
				{ 2001, 1, 1, 2010, 1, 1, DateRangeType.MONTHS, 1, 108},
				{ 2001, 1, 1, 2010, 1, 1,DateRangeType.MONTHS, 2, 54},
				{ 2001, 1, 1, 2010, 1, 1,DateRangeType.WEEKS, 2, 235},
				{ 2001, 1, 1, 2001, 10, 1,DateRangeType.DAYS, 10, 28},
				{ 2001, 1, 1, 2001, 2, 1,DateRangeType.HOURS, 10, 75},
				{ 2001, 1, 1, 2001, 1, 2,DateRangeType.MINUTES, 1, 1440}
		};
	}

	@Test(dataProvider = "datesRangeDP")
	public void testDatesRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, DateRangeType type, int step, int expectedSize) {
		final List<String> datesRange = calendar.datesRange()
												.startYear(startYear)
												.startDay(startDay)
												.startMonth(startMonth)
												.stepEvery(step, type)
												.format(DateFormat.dd_MM_yyyy_HH_mm)
												.endYear(endYear)
												.endMonth(endMonth)
												.endDay(endDay)
												.asStringsJavaList();
		assertTrue(Math.abs(expectedSize - datesRange.size()) <= 10);
	}

	@Test
	public void testDateRangeAsDates() {
		final List<DateTime> datesRange = calendar.datesRange()
				.startYear(2001)
				.startMonth(1)
				.startDay(1)
				.endDay(1)
				.endMonth(1)
				.endYear(2009)
				.stepEvery(1, DateRangeType.YEARS)
				.asJavaList();
		for (int i = 1; i < datesRange.size()-1; i++) {
			final DateTime expectedDate = new DateTime(new Integer("200" + (i + 1)), 1, 1, 0, 0);
			assertEquals(expectedDate, datesRange.get(i));
		}
	}
}
