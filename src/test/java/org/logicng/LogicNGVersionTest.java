package org.logicng;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Unit tests for {@link LogicNGVersion}.
 * @version 2.0.0
 * @since 2.0.0
 */
public class LogicNGVersionTest {

    @Test
    public void testMajor() {
        setVersion("2.0.0");
        assertThat(LogicNGVersion.major()).isEqualTo(2);
        setVersion("2.0.0-SNAPSHOT");
        assertThat(LogicNGVersion.major()).isEqualTo(2);
        setVersion("2.1.3");
        assertThat(LogicNGVersion.major()).isEqualTo(2);
        setVersion("42.7.19");
        assertThat(LogicNGVersion.major()).isEqualTo(42);
        setVersion("0.0.0");
        assertThat(LogicNGVersion.major()).isEqualTo(0);

        setVersion("A.0.1");
        assertThat(LogicNGVersion.major()).isEqualTo(-1);
    }

    @Test
    public void testMinor() {
        setVersion("2.0.0");
        assertThat(LogicNGVersion.minor()).isEqualTo(0);
        setVersion("2.3.0-SNAPSHOT");
        assertThat(LogicNGVersion.minor()).isEqualTo(3);
        setVersion("2.1.3");
        assertThat(LogicNGVersion.minor()).isEqualTo(1);
        setVersion("42.7.19");
        assertThat(LogicNGVersion.minor()).isEqualTo(7);
        setVersion("0.123.0");
        assertThat(LogicNGVersion.minor()).isEqualTo(123);

        setVersion("2.A.1");
        assertThat(LogicNGVersion.minor()).isEqualTo(-1);
    }

    @Test
    public void testPatch() {
        setVersion("2.0.3");
        assertThat(LogicNGVersion.patch()).isEqualTo(3);
        setVersion("2.3.3-SNAPSHOT");
        assertThat(LogicNGVersion.patch()).isEqualTo(3);
        setVersion("2.1.0");
        assertThat(LogicNGVersion.patch()).isEqualTo(0);
        setVersion("42.7.19");
        assertThat(LogicNGVersion.patch()).isEqualTo(19);
        setVersion("0.123.22");
        assertThat(LogicNGVersion.patch()).isEqualTo(22);

        setVersion("2.2.A");
        assertThat(LogicNGVersion.patch()).isEqualTo(-1);
    }

    @Test
    public void testSnapshot() {
        setVersion("2.0.3");
        assertThat(LogicNGVersion.snapshot()).isFalse();
        setVersion("2.0.3-SNAPSHOT");
        assertThat(LogicNGVersion.snapshot()).isTrue();
        setVersion("2.0.3-HUGO");
        assertThat(LogicNGVersion.snapshot()).isFalse();
    }

    private void setVersion(final String version) {
        try {
            final Field implVersion = Package.class.getDeclaredField("implVersion");
            implVersion.setAccessible(true);
            implVersion.set(LogicNGVersion.class.getPackage(), version);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to initialize test.");
        }
    }
}
