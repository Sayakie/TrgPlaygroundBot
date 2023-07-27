package dareharu.triggerreactor.util;

public final class Ints {

    private Ints() {}

    public static int tryParse(final String s) {
        return tryParse(s, 10, 0);
    }

    public static int tryParse(final String s, final int radix) {
        return tryParse(s, radix, 0);
    }

    public static int tryParse(final String s, final int radix, final int defaultValue) {
        try {
            return Integer.parseInt(s, radix);
        } catch (final Throwable ignored) {
            return defaultValue;
        }
    }

}
