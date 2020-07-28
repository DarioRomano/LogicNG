package org.logicng;

/**
 * A class which exposes the LogicNG library version as specified in the POM.
 * This works only if the compiled library as jar is included in your project.
 * @version 2.0.0
 * @since 2.0.0
 */
public final class LogicNGVersion {

    /**
     * Private constructor.
     */
    private LogicNGVersion() {
        // Intentionally left empty.
    }

    /**
     * Returns the version string from the POM.
     * @return the version string from the POM
     */
    public static String version() {
        final Package mainPackage = LogicNGVersion.class.getPackage();
        return mainPackage.getImplementationVersion();
    }

    /**
     * Returns the major version or -1 if the version could not be parsed.
     * @return the major version
     */
    public static int major() {
        return major(version());
    }

    private static int major(final String version) {
        try {
            return Integer.parseInt(version.split("\\.")[0]);
        } catch (final ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Returns the minor version or -1 if the version could not be parsed.
     * @return the minor version
     */
    public static int minor() {
        return minor(version());
    }

    private static int minor(final String version) {
        try {
            return Integer.parseInt(version.split("\\.")[1]);
        } catch (final ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Returns the patch version or -1 if the version could not be parsed.
     * @return the patch version
     */
    public static int patch() {
        return patch(version());
    }

    private static int patch(final String version) {
        try {
            return Integer.parseInt(version.split("\\.")[2].split("-")[0]);
        } catch (final ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Returns whether this is a snapshot version or not.
     * @return true if it is a snapshot version, false otherwise
     */
    public static boolean snapshot() {
        return snapshot(version());
    }

    private static boolean snapshot(final String version) {
        return version.contains("-SNAPSHOT");
    }
}
