package sharedClasses.commands;

import server.serverUtils.IOForClient;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

/**
 * Класс для команды show, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */

public class Show extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Show(User user, CommandsControl commandsControl) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) {
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста.").append('\n');
        else
            priorityQueue.getCollection().stream().
                    sorted((flat1, flat2) -> flat2.getName().compareTo(flat1.getName())).
                    forEach(flat -> result.append(flat.toString()).append('\n'));
        result.delete(result.length() - 1, result.length());
        return Serialization.serializeData(result.toString());
    }
}

