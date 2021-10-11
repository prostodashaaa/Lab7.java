package sharedClasses.commands;

import server.serverUtils.IOForClient;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для команды update, которая обновляет значение элемента коллекции по его id.
 */

public class UpdateId extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public UpdateId(User user, CommandsControl commandsControl) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному", 1, true, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        StringBuilder result = new StringBuilder();
        synchronized (priorityQueue.getCollection()) {
            try {
                long id = Long.parseLong(getArgument());
                List<Flat> flats = priorityQueue.getCollection().stream().
                        filter(flat -> flat.getId() == id).
                        collect(Collectors.toList());
                if (flats.size() > 0) {
                    Flat flat = getFlat();
                    if (priorityQueue.update(flats.get(0), flat, id, getUser())) {
                        result.append("Обновление элемента успешно завершено.");
                    }
                } else result.append("Элемент с id ").append(id).append(" не существует.");
            } catch (NumberFormatException e) {
                result.append("Неправильный формат id.");
            } catch (SQLException e) {
                result.append("Возникла ошибка при попытке соединения с БД, объект не обновлен");
            }
            return Serialization.serializeData(result.toString());
        }
    }
}
