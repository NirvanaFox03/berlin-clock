package hotels.com.interview.berlin_clock;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the arguments passed via the command line.
 */
public class Options implements Closeable {

	private static final Pattern OPTION_FORMAT = Pattern.compile("(?<name>.+)=(?<value>.+)");

	private DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_TIME;
	private InputStream input = System.in;
	private PrintStream output = System.out;

	public Options(String[] options) throws FileNotFoundException {
		for (String option : options) {
			Matcher matcher = OPTION_FORMAT.matcher(option);
			checkArgument(matcher.matches(), "invalid option: " + option);

			String name = matcher.group("name");
			String value = matcher.group("value");
			switch (name) {
			case "input":
				input = new FileInputStream(value);
				break;
			case "output":
				output = new PrintStream(value);
				break;
			case "time_formatter":
				try {
					timeFormatter = DateTimeFormatter.ofPattern(value);
				} catch (Exception e) {
					/*
					 * the original exception message from Java is not clear
					 * enough for revealing the cause directly
					 */
					throw new IllegalArgumentException("invalid pattern for the time formatter: " + value, e);
				}
				break;
			default:
				throw new IllegalArgumentException("invalid option: " + option);
			}
		}
	}

	public InputStream getInput() {
		return input;
	}

	public PrintStream getOutput() {
		return output;
	}

	public DateTimeFormatter getTimeFormatter() {
		return timeFormatter;
	}

	@Override
	public void close() throws IOException {
		input.close();
		output.close();
	}

}
