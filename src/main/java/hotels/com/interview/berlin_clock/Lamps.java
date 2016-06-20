package hotels.com.interview.berlin_clock;

import java.time.LocalTime;
import java.util.EnumMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represents the statuses of the lamps in a Berlin clock.<br>
 * The row of seconds is presented by a single boolean.<br>
 * Then the following rows has its corresponding enum type (see {@link LampRow})
 * and an integer indicates how many lamps are lit of that row (the lit lamps
 * always start from the beginning and are consecutive)
 */
public class Lamps {

	public enum LampRow {
		TOP_HR, BOT_HR, TOP_MIN, BOT_MIN
	}

	/*
	 * the hour/minute intervals are unlikely to change, however, this
	 * implementation make it very easy to change if the unlikely requirement
	 * raises, without adding any extra complexities or performance penalties
	 */
	public static final int HR_INTERVAL = 5;
	public static final int MIN_INTERVAL = 5;
	public static final EnumMap<LampRow, Integer> lampCounts = genLampCounts();

	private static EnumMap<LampRow, Integer> genLampCounts() {
		EnumMap<LampRow, Integer> counts = new EnumMap<>(LampRow.class);
		counts.put(LampRow.BOT_HR, HR_INTERVAL - 1);
		counts.put(LampRow.BOT_MIN, MIN_INTERVAL - 1);
		counts.put(LampRow.TOP_HR, 23 / HR_INTERVAL);
		counts.put(LampRow.TOP_MIN, 59 / MIN_INTERVAL);

		return counts;
	}

	private final EnumMap<LampRow, Integer> statuses = new EnumMap<>(LampRow.class);
	private final boolean secLamp;

	public Lamps(LocalTime time) {
		checkNotNull(time, "time must be specified");

		secLamp = time.getSecond() % 2 == 1;
		statuses.put(LampRow.TOP_HR, time.getHour() / HR_INTERVAL);
		statuses.put(LampRow.BOT_HR, time.getHour() % HR_INTERVAL);
		statuses.put(LampRow.TOP_MIN, time.getMinute() / MIN_INTERVAL);
		statuses.put(LampRow.BOT_MIN, time.getMinute() % MIN_INTERVAL);
	}

	public int getLampCnt(LampRow row) {
		return lampCounts.get(row);
	}

	public int getLitLampCnt(LampRow row) {
		return statuses.get(row);
	}

	public boolean getSecLamp() {
		return secLamp;
	}

}
