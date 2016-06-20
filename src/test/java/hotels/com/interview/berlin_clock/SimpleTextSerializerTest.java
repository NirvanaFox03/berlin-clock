package hotels.com.interview.berlin_clock;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringJoiner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SimpleTextSerializerTest {

	private static class Expected {

		public final LocalTime time;
		public final String serlialized;

		public Expected(int hour, int minute, int second, String secRow, String topHrRow, String botHrRow,
				String topMinRow, String botMinRow) {
			time = LocalTime.of(hour, minute, second);
			serlialized = new StringJoiner(System.lineSeparator(), "", System.lineSeparator()).add(secRow).add(topHrRow)
					.add(botHrRow).add(topMinRow).add(botMinRow).toString();
		}

	}

	@Parameters
	public static ArrayList<Expected> allInput() {
		ArrayList<Expected> list = new ArrayList<>();
		list.add(new Expected(0, 0, 0, "N", "NNNN", "NNNN", "NNNNNNNNNNN", "NNNN"));
		list.add(new Expected(23, 59, 59, "Y", "RRRR", "RRRN", "YYRYYRYYRYY", "YYYY"));
		list.add(new Expected(11, 33, 30, "N", "RRNN", "RNNN", "YYRYYRNNNNN", "YYYN"));

		return list;
	}

	@Parameter
	public Expected expected;

	@Test
	public void test_time() {
		assertThat(new SimpleTextSerializer("R", "Y", "N").serialize(new Lamps(expected.time)),
				equalTo(expected.serlialized));
	}
}
