package hotels.com.interview.berlin_clock;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OptionsTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void use_default_values() throws IOException {
		try (Options sut = new Options(new String[] {})) {
			assertThat(sut.getTimeFormatter(), equalTo(DateTimeFormatter.ISO_TIME));
			assertThat(sut.getInput(), equalTo(System.in));
			assertThat(sut.getOutput(), equalTo(System.out));
		}
	}

	@Test
	public void specify_time_formatter() throws IOException {
		try (Options sut = new Options(new String[] { "time_formatter=HH mm ss" })) {
			assertThat(sut.getInput(), equalTo(System.in));
			assertThat(sut.getOutput(), equalTo(System.out));

			assertThat(LocalTime.parse("11 22 33", sut.getTimeFormatter()), equalTo(LocalTime.of(11, 22, 33)));
		}
	}

	@Test
	public void specify_invalid_time_formatter() throws IOException {
		String formatter = "invalid";
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(formatter);

		try (Options sut = new Options(new String[] { "time_formatter=" + formatter })) {
		}
	}

	@Test
	public void specify_file_as_input() throws IOException {
		String input = "test input";
		String filename = "input_test.txt";
		Files.write(Paths.get(filename), input.getBytes());

		try (Options sut = new Options(new String[] { "input=" + filename });
				Scanner scanner = new Scanner(sut.getInput())) {
			assertThat(sut.getTimeFormatter(), equalTo(DateTimeFormatter.ISO_TIME));
			assertThat(sut.getOutput(), equalTo(System.out));

			assertThat(scanner.nextLine(), equalTo(input));
		} finally {
			Files.deleteIfExists(Paths.get(filename));
		}
	}

	@Test
	public void specify_invalid_file_as_input() throws IOException {
		String filename = "input_test.txt";
		exception.expect(FileNotFoundException.class);
		exception.expectMessage(filename);

		try (Options sut = new Options(new String[] { "input=" + filename })) {
		}
	}

	@Test
	public void specify_file_as_output() throws IOException {
		String output = "test output";
		String filename = "output_test.txt";

		try (Options sut = new Options(new String[] { "output=" + filename })) {
			assertThat(sut.getTimeFormatter(), equalTo(DateTimeFormatter.ISO_TIME));
			assertThat(sut.getInput(), equalTo(System.in));

			sut.getOutput().print(output);
			assertThat(new String(Files.readAllBytes(Paths.get(filename))), equalTo(output));
		} finally {
			Files.deleteIfExists(Paths.get(filename));
		}
	}

	@Test
	public void specify_invalid_file_as_output() throws IOException {
		String filename = ":\\?%*><";
		exception.expect(FileNotFoundException.class);
		exception.expectMessage(filename);

		try (Options sut = new Options(new String[] { "output=" + filename })) {
		}
	}

}
