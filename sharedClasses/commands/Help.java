package sharedClasses.commands;

import server.serverUtils.IOForClient;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

/**
 * Класс для команды help, которая выводит справку по доступным коммандам.
 */

public class Help extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Help(User user, CommandsControl commandsControl) {
        super("help", "вывести справку по доступным командам", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        StringBuilder result = new StringBuilder("Доступные команды:" + '\n');
        for (Command command : getCommandsControl().getCommands().values()) {
            result.append(command.toString()).append('\n');
        }
        result.delete(result.length() - 1, result.length());
        return Serialization.serializeData(result.toString());
    }
}
