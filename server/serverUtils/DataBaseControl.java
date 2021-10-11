package server.serverUtils;

import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.elementsOfCollection.*;
import sharedClasses.utils.User;


import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;

public class DataBaseControl {
    private final String URL;
    private final String USER;
    private final String PASS;
    private Connection connection;
    private Statement stat;

    public DataBaseControl(String[] args) {
        URL = "jdbc:postgresql://pg:5432/studs";
        USER = args[0];
        PASS = args[1];
    }

    public void setConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASS);
    }

    public void createUsersTable() throws SQLException {
        stat = connection.createStatement();
        String creation = "CREATE TABLE IF NOT EXISTS users (password VARCHAR(255), login VARCHAR(255)PRIMARY KEY);";
        stat.executeUpdate(creation);
        stat.close();
    }

    public Flat addToDataBase(Flat flat, User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO flat (name, x, y, creationdate," +
                " area, numberOfRooms, height, view, transport, houseName, year, numberOfFlatsOnFloor, numberOfLifts, owner)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, flat.getName());
        statement.setFloat(2, flat.getCoordinates().getX());
        statement.setFloat(3, flat.getCoordinates().getY());
        statement.setString(4, flat.getCreationDate().toString());
        statement.setInt(5, flat.getArea());
        statement.setLong(6, flat.getNumberOfRooms());
        if (flat.getHeight() != null) statement.setInt(7, flat.getHeight());
        else statement.setNull(7, Types.INTEGER);
        statement.setString(8, flat.getView().toString());
        statement.setString(9, flat.getTransport().toString());
        statement.setString(10, flat.getHouse().getName());
        statement.setInt(11, flat.getHouse().getYear());
        statement.setLong(12, flat.getHouse().getNumberOfFlatsOnFloor());
        statement.setInt(13, flat.getHouse().getNumberOfLifts());
        statement.setString(14, user.getLogin());
        int count = statement.executeUpdate();
        if (count > 0) {
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                flat.setId(resultSet.getLong("id"));
            }
        }
        statement.close();
        return flat;
    }

    public void createSequence() throws SQLException {
        stat = connection.createStatement();
        String creation = "CREATE SEQUENCE IF NOT EXISTS idgeneration START 1;";
        stat.executeUpdate(creation);
        stat.close();
    }

    public void createTableFlats() throws SQLException {
        stat = connection.createStatement();
        String creation = "CREATE TABLE IF NOT EXISTS flat (\n" +
                "id integer PRIMARY KEY CHECK(id > 0) DEFAULT nextval('idgeneration'),\n" +
                "name VARCHAR(255) NOT NULL,\n" +
                "x float CHECK(x < 900 AND x > 0),\n" +
                "y FLOAT NOT NULL,\n" +
                "creationDate VARCHAR(255) NOT NULL,\n" +
                "area int NOT NULL CHECK(area > 0),\n" +
                "numberOfRooms BIGINT CHECK(numberOfRooms > 0 AND numberOfRooms < 8),\n" +
                "height INTEGER NOT NULL CHECK(height > 0),\n" +
                "view VARCHAR(255),\n" +
                "transport VARCHAR(255),\n" +
                "houseName VARCHAR(255) NOT NULL,\n" +
                "year INTEGER CHECK(year < 568 AND year > 0),\n" +
                "numberOfFlatsOnFloor BIGINT CHECK(numberOfFlatsOnFloor > 0),\n" +
                "numberOfLifts INTEGER CHECK(numberOfLifts > 0),\n" +
                "owner VARCHAR(255));";
        stat.executeUpdate(creation);
        stat.close();
    }

    public void takeAllFromDB(PriorityQueueStorage priorityQueue) throws SQLException, ClassNotFoundException, ParseException {
        Class.forName("org.postgresql.Driver");
        setConnection();
        createSequence();
        createUsersTable();
        createTableFlats();
        stat = connection.createStatement();
        ResultSet resultSet = stat.executeQuery("SELECT * FROM flat");
        while (resultSet.next()) {
            Flat flat = new Flat();
            flat.setId(resultSet.getInt("id"));
            flat.setName(resultSet.getString("name"));
            flat.setCoordinates(new Coordinates(resultSet.getFloat("x"), resultSet.getFloat("y")));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            flat.setCreationDate(Instant.ofEpochMilli(formatter.parse(resultSet.getString("creationDate")).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            flat.setArea(resultSet.getInt("area"));
            flat.setNumberOfRooms(resultSet.getLong("numberOfRooms"));
            if (resultSet.getString("height") != null)
                flat.setHeight(resultSet.getInt("height"));
            else flat.setHeight(null);
            flat.setView(View.valueOf(resultSet.getString("view")));
            flat.setTransport(Transport.valueOf(resultSet.getString("transport")));
            flat.setHouse(new House(resultSet.getString("houseName"), resultSet.getInt("year"),
                    resultSet.getLong("numberOfFlatsOnFloor"), resultSet.getInt("numberOfLifts")));
            flat.setOwner(resultSet.getString("owner"));
            priorityQueue.checkElement(flat);
            priorityQueue.getCollection().add(flat);
        }
        stat.close();
    }

    public String addUser(User user) {
        String result = null;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?,?);");
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getLogin());
            int count = statement.executeUpdate();
            if (count > 0) result = "Новый пользователь добавлен.";
            else result = "Новый пользователь не добавлен, попробуйте придумать другой логин.";
        } finally {
            return result;
        }
        }


    public String checkUser(User user) throws SQLException {
        String result = "Пользователь не найден.";
        PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE login = ?");
        statement.setString(1, user.getLogin());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = "Пользователь найден";
            if (resultSet.getString(1).equals(user.getPassword())) result += ", вход выполнен.";
            else result += ", неверный пароль!";
        }
        return result;
    }

    public void clear(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM flat WHERE owner = ?");
        statement.setString(1, user.getLogin());
        statement.executeUpdate();
    }

    public boolean remove(Flat flat, User user) throws SQLException {
        boolean flag = false;
        PreparedStatement statement = connection.prepareStatement("DELETE FROM flat WHERE id = ? AND owner = ?");
        statement.setLong(1, flat.getId());
        statement.setString(2, user.getLogin());
        int count = statement.executeUpdate();
        if (count > 0) flag = true;
        return flag;
    }

    public boolean update(Flat flat, long id, User user) throws SQLException {
        boolean flag = false;
        PreparedStatement statement = connection.prepareStatement("UPDATE flat SET name = ?, x = ?," +
                "y = ?, creationDate = ?, area = ?, numberOfRooms = ?, height = ?," +
                "view = ?, transport = ?, houseName = ?, year = ?, numberOfFlatsOnFloor = ?, numberOfLifts = ? WHERE id = ? AND owner = ?");
        statement.setString(1, flat.getName());
        statement.setFloat(2, flat.getCoordinates().getX());
        statement.setFloat(3, flat.getCoordinates().getY());
        statement.setString(4, flat.getCreationDate().toString());
        statement.setInt(5, flat.getArea());
        statement.setLong(6, flat.getNumberOfRooms());
        if (flat.getHeight() != null) statement.setInt(7, flat.getHeight());
        else statement.setNull(7, Types.INTEGER);
        statement.setString(8, flat.getView().toString());
        statement.setString(9, flat.getTransport().toString());
        statement.setString(10, flat.getHouse().getName());
        statement.setInt(11, flat.getHouse().getYear());
        statement.setLong(12, flat.getHouse().getNumberOfFlatsOnFloor());
        statement.setInt(13, flat.getHouse().getNumberOfLifts());
        statement.setLong(14, id);
        statement.setString(15, user.getLogin());
        int count = statement.executeUpdate();
        if (count > 0) flag = true;
        return flag;
    }
}
