package HttpServer.core.utility;

import HttpServer.core.utility.logger.StringDefinedInterval;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StringDefinedIntervalTest {

    private Function maybe;

    @Before
    public void setup() {
        // i don't know what the word "nullable" means, what I'm trying to say is,
        maybe = (value) -> java.util.Optional.ofNullable(value);
    }

    @Test
    public void itHandlesUnboundedBelow() {
        String definition = "-10";
        StringDefinedInterval interval = new StringDefinedInterval(definition);
        assertNull(interval.lower);
        assertEquals(java.util.Optional.ofNullable(10), java.util.Optional.ofNullable(interval.upper));
    }

    @Test
    public void itHandlesUnboundedAbove() {
        String definition = "10-";
        StringDefinedInterval interval = new StringDefinedInterval(definition);
        assertNull(interval.upper);
        assertEquals(maybe.apply(10), maybe.apply(interval.lower));
    }

    @Test
    public void itHandlesTwoEndpoints() {
        String definition = "5-10";
        StringDefinedInterval interval = new StringDefinedInterval(definition);
        assertEquals(maybe.apply(5), maybe.apply(interval.lower));
        assertEquals(maybe.apply(10), maybe.apply(interval.upper));
    }

    @Test
    public void itHandlesUnboundAboveAndBelow() {
        StringDefinedInterval interval = new StringDefinedInterval(null);
        assertNotNull(interval);
        assertNull(interval.upper);
        assertNull(interval.lower);
    }

    @Test
    public void itCanComputeLengthWhenBounded() {
        // The client of this class includes the endpoints in length computations,
        // so we shall as well.
        String definition = "0-4";
        StringDefinedInterval interval = new StringDefinedInterval(definition);
        assertEquals(maybe.apply(5), maybe.apply(interval.length()));
    }

    @Test
    public void itComputesLengthFromEndWhenOnlyBoundedAbove() {
        String definition = "-6";
        StringDefinedInterval interval = new StringDefinedInterval(definition);
        assertEquals(maybe.apply(6), maybe.apply(interval.length()));
    }
}
