package my.teach.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserConsoleArguments {
    private static final String INSTRUCTION_HOW_TO_START_SERVER = "Для работы сервера необходимо 2 параметра:"
            + " 'Путь к ресурсам' и 'Номер порта', например: D://files/index.html 5555";
    private static final String ERROR_FORMAT_PORT = "Вы ввели строку, а нужно число";
    private String resourcePath;
    private int port;
    private static final Logger LOGGER = Logger.getLogger(ParserConsoleArguments.class.getName());

    public ParserConsoleArguments() {
    }

    public void parse(String[] args) {
        if (args.length == 2) {
            resourcePath = args[0].trim();
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                LOGGER.log(Level.SEVERE, ERROR_FORMAT_PORT, nfe);
            }
        } else {
            LOGGER.log(Level.SEVERE, INSTRUCTION_HOW_TO_START_SERVER);
        }
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public int getPort() {
        return port;
    }
}
