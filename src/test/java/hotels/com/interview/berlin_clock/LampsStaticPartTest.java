package hotels.com.interview.berlin_clock;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.LocalTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import hotels.com.interview.berlin_clock.Lamps.LampRow;

public class LampsStaticPartTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void test_lamp_counts() {
		Lamps sut = new Lamps(LocalTime.now());
		assertThat(sut.getLampCnt(LampRow.TOP_HR), equalTo(4));
		assertThat(sut.getLampCnt(LampRow.BOT_HR), equalTo(4));
		assertThat(sut.getLampCnt(LampRow.TOP_MIN), equalTo(11));
		assertThat(sut.getLampCnt(LampRow.BOT_MIN), equalTo(4));
	}

	@Test
	public void construct_with_empty_time() {
		exception.expect(NullPointerException.class);
		exception.expectMessage("time must be specified");

		new Lamps(null);
	}

}
