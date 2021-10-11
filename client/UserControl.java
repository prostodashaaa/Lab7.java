package client;

import sharedClasses.utils.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserControl {

    public User logIn(InputAndOutput inputAndOutput, boolean newUser) throws NoSuchAlgorithmException {
        return new User(readLogin(inputAndOutput, newUser), readPassword(inputAndOutput, newUser));
    }

    public String readPassword(InputAndOutput inputAndOutput, boolean newUser) throws NoSuchAlgorithmException {
        String password;
        if (newUser) inputAndOutput.output("Придумайте пароль:");
        else inputAndOutput.output("Введите пароль:");
        password = inputAndOutput.inputLine();
        while (password.equals("")) {
            inputAndOutput.output("Недопустимый пароль, повторите ввод:");
            password = inputAndOutput.inputLine();
        }
        password = getEncodedPassword(password);
        return password;
    }

    public String getTypeOfUser(boolean newUser) {
        String type;
        if (newUser) type = "newUser";
        else type = "oldUser";
        return type;
    }

    private String getEncodedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        return getEncodedString(messageDigest.digest(("*&^mVLCf(#" + password).getBytes(StandardCharsets.UTF_8)));
    }

    private String getEncodedString(byte[] bytes) {
        StringBuilder password = new StringBuilder();
        for (byte byte1 : bytes) {
            String s = Integer.toHexString(byte1);
            try {
                password.append(s.substring(s.length() - 2));
            } catch (IndexOutOfBoundsException e) {
                password.append("0").append(s);
            }
        }
        return password.toString();
    }

    public String readLogin(InputAndOutput inputAndOutput, boolean newUser) {
        String login;
        if (newUser) inputAndOutput.output("Придумайте логин:");
        else inputAndOutput.output("Введите логин:");
        login = inputAndOutput.inputLine();
        while (login.equals("")) {
            inputAndOutput.output("Недопустимый логин, повторите ввод:");
            login = inputAndOutput.inputLine();
        }
        return login;
    }
}
