package sharedClasses.commands;

import server.serverUtils.IOForClient;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.NoSuchElementException;

/**
 * Класс для команды add, которая добавляет новый элемент в коллекцию.
 */

public class Add extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Add(User user, CommandsControl commandsControl) {
        super("add", "добавить новый элемент в коллекцию", 0, true, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        StringBuilder result = new StringBuilder();
        try {
            Flat flat = this.getFlat();
            synchronized (priorityQueue.getCollection()) {
                priorityQueue.addToCollection(flat, getUser());
            }
           result.append("В коллекцию добавлен новый элемент.");
        } catch (IllegalStateException e) {
            result.append("Ошибка: в коллекции слишком много элементов; объект коллекции не создан.");
        } catch (NoSuchElementException e) {
            result.append("В скрипте не указаны значения для создания элемента коллекции. Команда add не выполнена.");
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            result.append("Возникла ошибка при попытке соединения с БД, новый объект не добавлен.");
            e.printStackTrace();
        }
        return Serialization.serializeData(result.toString());
    }
}
