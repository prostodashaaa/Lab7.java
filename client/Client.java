package client;

import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.Serialization;
import sharedClasses.commands.Command;
import sharedClasses.commands.CommandsControl;
import sharedClasses.utils.User;
import sharedClasses.utils.UserInput;
import sharedClasses.utils.WrapperForObjects;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private Selector selector;
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private final Serialization serialization;
    private CommandsControl commandsControl;
    private final UserInput userInput;
    private final InputAndOutput inputAndOutput;
    private final Scanner scanner;
    private final String host = "localhost";
    private User user;

    public Client() {
        scanner = new Scanner(System.in);
        inputAndOutput = new InputAndOutput(scanner, true);
        userInput = new UserInput(inputAndOutput);
        serialization = new Serialization();
    }

    public InputAndOutput getInputAndOutput() {
        return inputAndOutput;
    }

    private void initialize() throws IOException {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
    }

    public static void main(String[] args) {
        Client client = new Client();
        boolean flag = true;
        boolean flag1 = true;
        int port = Integer.parseInt(args[0]);
        while (flag1) {
            try {
                if (args != null) port = Integer.parseInt(args[0]);
                else System.exit(1);
                if (port < 0 || port > 65535) {
                    client.inputAndOutput.output("Неверный формат порта, повторите ввод:");
                    continue;
                }
                flag1 = false;
            } catch (IllegalArgumentException e) {
                client.inputAndOutput.output("Неверный формат порта, повторите ввод:");
            }
        }
        while (flag) {
            try {
                client.initialize();
                client.connect(client.host, port);
                flag = false;
                client.user = client.connectToDB();
                client.commandsControl = new CommandsControl(client.user);
                client.inputAndOutput.output("Введите команду:");
                client.run();
            } catch (IOException | NoSuchAlgorithmException e) {
                client.getInputAndOutput().output("Соединение не установлено.");
                flag = client.getInputAndOutput().readAnswer("Повторить попытку? (yes/no)");
            }
        }
    }

    private void connect(String host, int port) throws IOException {
        socketAddress = new InetSocketAddress(host, port);
        datagramChannel.connect(socketAddress);
    }

    private void sendCommand(String[] s) throws IOException {
        Command currentCommand;
        if (s.length > 0 && commandsControl.getCommands().containsKey(s[0])) {
            currentCommand = commandsControl.getCommands().get(s[0]);
            if (currentCommand.getAmountOfArguments() > 0) {
                currentCommand.setArgument(s[1]);
            }
            if (currentCommand.isNeedFlat()) {
                Flat flat = userInput.readFlat();
                flat.setOwner(user.getLogin());
                currentCommand.setFlat(flat);
            }
            WrapperForObjects object = new WrapperForObjects(currentCommand, "Command");
            byte[] ser = Serialization.serializeData(object);
            if (ser != null) {
                ByteBuffer buffer = ByteBuffer.wrap(ser);
                datagramChannel.send(buffer, socketAddress);
            }
        } else {
            inputAndOutput.output("Данной команды не существет, повторите ввод");
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
        }
    }

    private boolean outputAnswer() throws IOException {
        boolean flag = true;
        byte[] bytes = new byte[100000];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        socketAddress = datagramChannel.receive(buffer);
        bytes = buffer.array();
        String outputForUser = (String) serialization.deserializeData(bytes);
        inputAndOutput.output(outputForUser);
        if (outputForUser != null && (outputForUser.equals("Пользователь не найден.") ||
                outputForUser.equals("Новый пользователь не добавлен, попробуйте придумать другой логин.") ||
                outputForUser.equals("Пользователь найден, неверный пароль!"))) {
            flag = false;
        }
        if (outputForUser != null && outputForUser.equals("Работа приложения завершается.")) {
            System.exit(1);
        }
        if (outputForUser != null && outputForUser.equals("Возникла ошибка при сохранении коллекции.")) {
            if (inputAndOutput.readAnswer("Хотите выйти без сохранения коллекции?")) System.exit(1);
            else inputAndOutput.output("Выход не выполнен.");
        } else {
            if (outputForUser == null) {
                inputAndOutput.output("Ошибка сериализации команды; команда не выполнена.");
                datagramChannel.register(selector, SelectionKey.OP_WRITE);
            }
        }
        return flag;
    }

    private User connectToDB() throws NoSuchAlgorithmException, IOException {
        boolean flag = false;
        boolean newUser = !inputAndOutput.readAnswer("Вы зарегестрированы? (введите yes/no)");
        UserControl userControl = new UserControl();
        WrapperForObjects user = new WrapperForObjects(userControl.logIn(inputAndOutput, newUser), userControl.getTypeOfUser(newUser));
        datagramChannel.register(selector, SelectionKey.OP_WRITE);
        while (!flag) {
            if (selector.select() == 0) {
                System.exit(0);
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = (SelectionKey) keyIterator.next();
                if (key.isWritable()) {
                    datagramChannel.register(selector, SelectionKey.OP_READ);
                    try {
                        byte[] ser = Serialization.serializeData(user);
                        if (ser != null) {
                            ByteBuffer buffer = ByteBuffer.wrap(ser);
                            datagramChannel.send(buffer, socketAddress);
                        }
                    } catch (Exception e) {
                        inputAndOutput.output("Произошла непредвиденная ошибка.");
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
                if (key.isReadable()) {
                    datagramChannel.register(selector, SelectionKey.OP_WRITE);
                    flag = outputAnswer();
                    if (!flag) {
                        flag = inputAndOutput.readAnswer("Хотите повторить попытку подключения?");
                        if (!flag) System.exit(0);
                        else {
                            flag = false;
                            newUser = !inputAndOutput.readAnswer("Вы зарегестрированы? (введите yes/no)");
                            user = new WrapperForObjects(userControl.logIn(inputAndOutput, newUser), userControl.getTypeOfUser(newUser));
                        }
                    }
                }
                keyIterator.remove();
            }
        }
        return (User) user.getObject();
    }

    private void run() throws IOException {
        datagramChannel.register(selector, SelectionKey.OP_WRITE);
        while (true) {
            if (selector.select() == 0) {
                System.exit(0);
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = (SelectionKey) keyIterator.next();
                if (key.isReadable()) {
                    datagramChannel.register(selector, SelectionKey.OP_WRITE);
                    outputAnswer();
                }
                if (key.isWritable()) {
                    datagramChannel.register(selector, SelectionKey.OP_READ);
                    String[] s = null;
                    if (scanner.hasNextLine()) s = scanner.nextLine().split(" ");
                    else System.exit(1);
                    try {
                        sendCommand(s);
                    } catch (IndexOutOfBoundsException e) {
                        inputAndOutput.output("Введены не все аргументы команды.");
                        datagramChannel.register(selector, SelectionKey.OP_WRITE);
                    } catch (Exception e) {
                        inputAndOutput.output("Произошла непредвиденная ошибка.");
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
                keyIterator.remove();
            }
        }
    }
}
