package sharedClasses.commands;

import sharedClasses.elementsOfCollection.Flat;
import server.serverUtils.IOForClient;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для команды remove_by_id, которая удаляет элемент из коллекции по его id.
 */

public class RemoveById extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveById(User user, CommandsControl commandsControl) {
        super("remove_by_id id", "удалить элемент из коллекции по его id", 1, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        StringBuilder result = new StringBuilder();
        synchronized (priorityQueue.getCollection()) {
            try {
                int id = Integer.parseInt(this.getArgument());
                List<Flat> flats = priorityQueue.getCollection().stream().
                        filter(flat -> flat.getId() == id).
                        collect(Collectors.toList());
                if (flats.size() > 0) {
                    boolean flag = priorityQueue.remove(flats.get(0), getUser());
                    if (flag) result.append("Удаление элемента успешно завершено.");
                    else result.append("Удаление элемента не выполнено!");
                } else result.append("Элемента с id ").append(id).append(" не существует.");
            } catch (NumberFormatException e) {
                result.append("Неправильный формат id.");
            } catch (SQLException e) {
                result.append("Ошибка при попытке удаления значения из БД; удаление не выполнено.");
            }
        }
        return Serialization.serializeData(result.toString());
    }
}
