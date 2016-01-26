package ui.io;

public class ShellConsole implements GeneralConsole {
    @Override
    public void writeLine(String format, Object[] args) {
        System.out.println(String.format(format, args));
    }

    @Override
    public void clear() {

    }
}
