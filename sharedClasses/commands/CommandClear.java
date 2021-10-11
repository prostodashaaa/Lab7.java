package sharedClasses.commands;

import server.serverUtils.IOForClient;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.sql.SQLException;

/**
 * Класс для команды clear, которая очищает коллекцию.
 */

public class CommandClear extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandClear(User user, CommandsControl commandsControl) {
        super("clear", "очистить коллекцию", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        String s = "Элементы пользователя успешно удалены.";
        synchronized (priorityQueue.getCollection()) {
            try {
                priorityQueue.clear(getUser());
            } catch (SQLException e) {
                s = "Элементы пользователя не удалены, так как возникла проблема с подключением к БД.";
            }
        }
        return Serialization.serializeData(s);
    }
}
