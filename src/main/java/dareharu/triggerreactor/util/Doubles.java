package dareharu.triggerreactor.util;

public final class Doubles {

    private Doubles() {}

    public static double tryParse(final String s) {
        return tryParse(s, 0);
    }

    public static double tryParse(final String s, final double defaultValue) {
        try {
            return Double.parseDouble(s);
        } catch (final Throwable ignored) {
            return defaultValue;
        }
    }

}
