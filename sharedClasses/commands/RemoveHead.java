package sharedClasses.commands;

import server.serverUtils.IOForClient;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.sql.SQLException;

/**
 * Класс для команды remove_head, которая выводит и удаляет первый элемент из коллекции.
 */

public class RemoveHead extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveHead(User user, CommandsControl commandsControl) {
        super("remove_head", "вывести первый элемент коллекции и удалить его", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        StringBuilder result = new StringBuilder();
        synchronized (priorityQueue.getCollection()){
            try{
                if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста.");
                else {
                    Flat flat = priorityQueue.pollFromQueue();
                    boolean flag = priorityQueue.remove(flat, getUser());
                    if (flag) result.append("Удаление элемента успешно завершено.");
                    else {
                        result.append("Удаление элемента не выполнено!");
                        priorityQueue.getCollection().add(flat);
                    }
                }
            } catch (SQLException e) {
                result.append("Ошибка при попытке удаления значения из БД; удаление не выполнено.");
            }
        }
        return Serialization.serializeData(result.toString());
    }
}
