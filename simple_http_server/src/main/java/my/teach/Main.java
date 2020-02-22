package my.teach;

import my.teach.parser.ParserConsoleArguments;
import my.teach.property.PropertyDistributor;
import my.teach.server.Server;

public class Main {
    public static void main(String[] args) {
        PropertyDistributor properties = new PropertyDistributor();
        ParserConsoleArguments parserConsoleArguments = new ParserConsoleArguments();
        parserConsoleArguments.parse(args);
        new Server(parserConsoleArguments.getPort(), properties.getBufferSize(),
                parserConsoleArguments.getResourcePath()).start();
    }
}
