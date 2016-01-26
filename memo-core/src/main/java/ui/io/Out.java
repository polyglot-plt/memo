package ui.io;

public class Out {

    private static GeneralConsole console;

    public static void writeLine(String format, Object... args) {
        console.writeLine(format, args);
    }

    public static void clear() {
        console.clear();
    }

    public static void setConsole(GeneralConsole newConsole) {
        console = newConsole;
    }
}
