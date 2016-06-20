package hotels.com.interview.berlin_clock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the entry point of the whole program.
 */
public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);
	private static final String SEPARATOR = StringUtils.repeat('-', 33);

	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(App.class.getClassLoader().getResourceAsStream("MANUAL.txt")))) {
			System.out.println(reader.lines().collect(Collectors.joining(System.lineSeparator())));
		} catch (Throwable e) {
			logger.error("an error occurred when reading MANUAL file: " + e.getMessage(), e);
		}

		try (Options options = new Options(args); Scanner input = new Scanner(options.getInput())) {
			PrintStream output = options.getOutput();
			DateTimeFormatter timeFormatter = options.getTimeFormatter();

			while (input.hasNextLine()) {
				String time = input.nextLine();
				String serialized = new SimpleTextSerializer("R", "Y", "N")
						.serialize(new Lamps(LocalTime.parse(time, timeFormatter)));
				output.println(serialized + SEPARATOR);
			}
		} catch (Throwable e) {
			logger.error("an error occurred when presenting the times: " + e.getMessage(), e);
		}
	}

}
