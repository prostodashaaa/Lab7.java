package sharedClasses.commands;

import java.io.Serializable;
import java.util.HashMap;

import sharedClasses.utils.User;

/**
 * Класс для создания объектов комманд и их хранения.
 */

public class CommandsControl implements Serializable {
    /**
     * Поле, использующееся для хранения объектов команд.
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandsControl(User user){
        commands.put("help", new Help(user, this));
        commands.put("show", new Show(user, this));
        commands.put("info", new Info(user, this));
        commands.put("add", new Add(user, this));
        commands.put("update", new UpdateId(user, this));
        commands.put("remove_by_id", new RemoveById(user, this));
        commands.put("clear", new CommandClear(user, this));
        commands.put("execute_script", new ExecuteScript(user, this));
        commands.put("exit", new CommandExit(user, this));
        commands.put("remove_head", new RemoveHead(user, this));
        commands.put("add_if_max", new AddIfMax(user, this));
        commands.put("remove_first", new RemoveFirst(user, this));
        commands.put("count_by_number_of_rooms", new CountByNumberOfRooms(user, this));
        commands.put("count_less_than_house", new CountLessThanHouse(user, this));
        commands.put("filter_greater_than_number_of_rooms", new FilterGreaterThanNumberOfRooms(user, this));
    }

    /**
     * Метод, возвращающий карту команд.
     *
     * @return карту команд.
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
