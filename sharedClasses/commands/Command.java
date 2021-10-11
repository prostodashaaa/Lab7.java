package sharedClasses.commands;

import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.io.Serializable;

/**
 * Абстрактный класс для всех команд.
 */

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Дополнительная информация о команде.
     */
    private final String someInformation;
    /**
     * Название команды.
     */
    private final String name;
    private final int amountOfArguments;
    private String argument;
    private Flat flat;
    private final boolean needFlat;
    private final User user;
    private final CommandsControl commandsControl;

    /**
     * Конструктор.
     *
     * @param name            название команды.
     * @param someInformation дополнительная информация о команде.
     */
    public Command(String name, String someInformation, int amountOfArguments, boolean needFlat, User user, CommandsControl commandsControl) {
        this.name = name;
        this.someInformation = someInformation;
        this.amountOfArguments = amountOfArguments;
        this.needFlat = needFlat;
        this.user = user;
        this.commandsControl = commandsControl;
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     * @throws Exception в случае ошибки при выполнении команды.
     */
    public abstract byte[] doCommand(IOInterface ioForClient, StorageInterface<Flat> priorityQueue) throws Exception;

    /**
     * Метод, возвращающий название команды.
     *
     * @return название команды.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, возвращающий информацию о команде.
     *
     * @return информация о команде.
     */
    public String getSomeInformation() {
        return someInformation;
    }

    public User getUser() {
        return user;
    }

    public int getAmountOfArguments() {
        return amountOfArguments;
    }

    public String getArgument() {
        return argument;
    }

    public Flat getFlat() {
        return flat;
    }

    public CommandsControl getCommandsControl() {
        return commandsControl;
    }

    public boolean isNeedFlat() {
        return needFlat;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    /**
     * Метод, возвращающий объект в строковом представлении.
     *
     * @return объект в строковом представлении.
     */
    public String toString() {
        return getName() + " : " + getSomeInformation();
    }
}