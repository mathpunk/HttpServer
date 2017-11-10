package HttpServer.core.utility;

import HttpServer.core.utility.logger.StringDefinedInterval;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StringDefinedIntervalTest {

    private Function maybe;

    @Before
    public void setup() {
        // is "nullable" a word? how about,
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
}
