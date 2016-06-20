package hotels.com.interview.berlin_clock;

/**
 * This interface represents the serializer for the Berlin-clock.<br>
 * The logic of the lamp statuses and the actual representation of the clock is
 * decoupled so we can have different representations of the clock (like
 * different colours or different string formats, which is a quite likely
 * potential requirement)
 */
public interface ClockSerializer {

	public String serialize(Lamps lamps);
}
