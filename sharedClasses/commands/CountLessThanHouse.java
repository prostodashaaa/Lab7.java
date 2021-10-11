package sharedClasses.commands;

import server.serverUtils.IOForClient;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для команды count_less_than_house, которая выводит количество элементов,
 * значение поля house которых меньше заданного
 */

public class CountLessThanHouse extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CountLessThanHouse(User user, CommandsControl commandsControl) {
        super("count_less_than_house", "вывести количество элементов, значение поля house которых меньше заданного", 1, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        StringBuilder result = new StringBuilder();
        try {
            if (priorityQueue.getCollection().isEmpty()) {
                result.append("Коллекция пуста; количесвто элементов поля house установить невозможно.");
            }
            else {
                Integer house = Integer.parseInt(getArgument());
                List<Flat> flats = priorityQueue.getCollection().stream().
                        filter(flat -> flat.getHouse().getYear() < house).collect(Collectors.toList());
                int count = flats.size();
                result.append("Количество значений поля house, меньших заданного: ").append(count);
            }
        } catch (NumberFormatException e) {
            result.append("Неправильный формат house.");
        }
        return Serialization.serializeData(result.toString());
    }
}
