package dareharu.triggerreactor.util;

public final class AnsiColorUtils {

    private AnsiColorUtils() {}

    public static String BLACK_FOREGROUND = "\u001B[0;30m឵឵";
    public static String RED_FOREGROUND = "\u001B[0;31m឵឵";
    public static String GREEN_FOREGROUND = "\u001B[0;32m឵឵";
    public static String YELLOW_FOREGROUND = "\u001B[0;33m឵឵";
    public static String BLUE_FOREGROUND = "\u001B[0;34m឵឵";
    public static String MAGENTA_FOREGROUND = "\u001B[0;35m឵឵";
    public static String CYAN_FOREGROUND = "\u001B[0;36m឵឵";
    public static String WHITE_FOREGROUND = "\u001B[0;37m឵឵";
    public static String DEFAULT_FOREGROUND = "\u001B[0;39m឵឵";
    public static String RESET_FOREGROUND = "\u001B[0;0m឵឵";

    public static String bukkitColorToAnsiColor(final String s) {
        return s
            .replaceAll("[§&]0", BLACK_FOREGROUND)
            .replaceAll("[§&]1", BLUE_FOREGROUND)
            .replaceAll("[§&]2", GREEN_FOREGROUND)
            .replaceAll("[§&]3", CYAN_FOREGROUND)
            .replaceAll("[§&]4", RED_FOREGROUND)
            .replaceAll("[§&]5", MAGENTA_FOREGROUND)
            .replaceAll("[§&]6", YELLOW_FOREGROUND)
            .replaceAll("[§&]7", DEFAULT_FOREGROUND)
            .replaceAll("[§&]8", DEFAULT_FOREGROUND)
            .replaceAll("[§&]9", CYAN_FOREGROUND)
            .replaceAll("(?i)[§&]a", GREEN_FOREGROUND)
            .replaceAll("(?i)[§&]b", CYAN_FOREGROUND)
            .replaceAll("(?i)[§&]c", RED_FOREGROUND)
            .replaceAll("(?i)[§&]d", MAGENTA_FOREGROUND)
            .replaceAll("(?i)[§&]e", YELLOW_FOREGROUND)
            .replaceAll("(?i)[§&]f", WHITE_FOREGROUND)
            .replaceAll("(?i)[§&]r", RESET_FOREGROUND)
            .replaceAll("(?i)[§&][k-o]", "") + RESET_FOREGROUND;
    }

    public static void main(final String[] args) {
        System.out.println(bukkitColorToAnsiColor("§eHello"));
    }

}
