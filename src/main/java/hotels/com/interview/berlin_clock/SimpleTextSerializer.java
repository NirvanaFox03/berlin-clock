package hotels.com.interview.berlin_clock;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import hotels.com.interview.berlin_clock.Lamps.LampRow;

import static hotels.com.interview.berlin_clock.Lamps.LampRow.*;

/**
 * This class represents a clock serializer that will display the Berlin clock
 * as multiple-line strings:<br>
 * The first line is the blink lamp for the second.<br>
 * The second and third line represent the lamps for the hour.<br>
 * The fourth and last line represent the lamps for the minutes.<br>
 * Each lamp is represented by a short string (e.g. the color, specified in the
 * constructor) and there are three types: the colour for the hour lamps and
 * quarter lamps (i.e. the 3rd, 6th, 9th of the fourth row); the colour for
 * second/other minute lamps; and the colour when the lamp is off.<br>
 * <br>
 * The above representation is from the Wikipedia page:
 * https://en.wikipedia.org/wiki/Mengenlehreuhr
 */
public class SimpleTextSerializer implements ClockSerializer {

	private final String hrAndQuarterLamp;
	private final String minAndSecLamp;
	private final String offLamp;

	public SimpleTextSerializer(String hrAndQuarterLamp, String minAndSecLamp, String offLamp) {
		this.hrAndQuarterLamp = hrAndQuarterLamp;
		this.minAndSecLamp = minAndSecLamp;
		this.offLamp = offLamp;
	}

	@Override
	public String serialize(Lamps lamps) {
		StringBuilder result = new StringBuilder();

		appendLine(result, lamps.getSecLamp() ? minAndSecLamp : offLamp);

		Stream.of(TOP_HR, BOT_HR, TOP_MIN, BOT_MIN)
				.forEach(
						row -> appendLine(result,
								IntStream.range(0, lamps.getLampCnt(row))
										.mapToObj(
												idx -> idx < lamps.getLitLampCnt(row) ? onLampIsOn(row, idx) : offLamp)
										.collect(Collectors.joining())));

		return result.toString();

	}

	private String onLampIsOn(LampRow row, int idx) {
		return (row == BOT_MIN || (row == TOP_MIN && (idx + 1) % 3 != 0)) ? minAndSecLamp : hrAndQuarterLamp;
	}

	private StringBuilder appendLine(StringBuilder sb, String line) {
		sb.append(line + System.lineSeparator());
		return sb;
	}

}
