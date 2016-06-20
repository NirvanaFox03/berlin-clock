package hotels.com.interview.berlin_clock;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import hotels.com.interview.berlin_clock.Lamps.LampRow;

@RunWith(Parameterized.class)
public class LampsTest {

	private static class Expected {

		private final LocalTime time;
		private final boolean secLamp;
		private final int topHrCnt;
		private final int botHrCnt;
		private final int topMinCnt;
		private final int botMinCnt;

		public Expected(int hour, int minute, int second, boolean secLamp, int topHrCnt, int botHrCnt, int topMinCnt,
				int botMinCnt) {
			super();
			this.time = LocalTime.of(hour, minute, second);
			this.secLamp = secLamp;
			this.topHrCnt = topHrCnt;
			this.botHrCnt = botHrCnt;
			this.topMinCnt = topMinCnt;
			this.botMinCnt = botMinCnt;
		}

	}

	@Parameters
	public static ArrayList<Expected> allInput() {
		ArrayList<Expected> list = new ArrayList<>();
		list.add(new Expected(0, 0, 0, false, 0, 0, 0, 0));
		list.add(new Expected(0, 0, 1, true, 0, 0, 0, 0));
		list.add(new Expected(0, 0, 58, false, 0, 0, 0, 0));
		list.add(new Expected(0, 0, 59, true, 0, 0, 0, 0));
		list.add(new Expected(0, 1, 33, true, 0, 0, 0, 1));
		list.add(new Expected(0, 5, 33, true, 0, 0, 1, 0));
		list.add(new Expected(0, 55, 33, true, 0, 0, 11, 0));
		list.add(new Expected(0, 59, 33, true, 0, 0, 11, 4));
		list.add(new Expected(1, 11, 11, true, 0, 1, 2, 1));
		list.add(new Expected(5, 11, 11, true, 1, 0, 2, 1));
		list.add(new Expected(20, 11, 11, true, 4, 0, 2, 1));
		list.add(new Expected(23, 11, 11, true, 4, 3, 2, 1));

		return list;
	}

	@Parameter
	public Expected expected;

	@Test
	public void test_time() {
		Lamps actual = new Lamps(expected.time);
		assertThat(actual.getSecLamp(), equalTo(expected.secLamp));
		assertThat(actual.getLitLampCnt(LampRow.TOP_HR), equalTo(expected.topHrCnt));
		assertThat(actual.getLitLampCnt(LampRow.BOT_HR), equalTo(expected.botHrCnt));
		assertThat(actual.getLitLampCnt(LampRow.TOP_MIN), equalTo(expected.topMinCnt));
		assertThat(actual.getLitLampCnt(LampRow.BOT_MIN), equalTo(expected.botMinCnt));
	}
}
