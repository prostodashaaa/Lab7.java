package sharedClasses.elementsOfCollection;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Класс Flat.
 */
public class Flat implements Serializable, Comparable {
    /**
     * Id квартиры. Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически.
     */
    private long id;
    /**
     * Название квартиры. Поле не может быть null, Строка не может быть пустой.
     */
    private String name;
    /**
     * Координты квартиры. Поле не может быть null.
     */
    private Coordinates coordinates;
    /**
     * Размер территории квартиры. Поле не может быть null, Значение этого поля должно генерироваться автоматически.
     */
    private LocalDate creationDate;
    /**
     * Область квартиры. Значение поля должно быть больше 0.
     */
    private int area;
    /**
     * Номер команты квартиры. Максимальное значение поля: 8, Значение поля должно быть больше 0.
     */
    private long numberOfRooms;
    /**
     * Высота квартиры. Значение поля должно быть больше 0.
     */
    private Integer height;
    /**
     * Вид мз квартиры. Поле не может быть null.
     */
    private View view;
    /**
     * Транспорт до квартиры. Поле не может быть null.
     */
    private Transport transport;
    /**
     * Дом, в котором находится квартира. Поле не может быть null.
     */
    private House house;

    private String user;

    /**
     * Метод для get ID
     *
     * @return long id
     */
    public long getId() {
        return id;
    }

    /**
     * Метод, присваивающий значение id.
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Метод для get name
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, присваивающий имя.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод для get coordinates
     *
     * @return Coordinates coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод, присваивающий значение координат.
     *
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Метод для get ID
     *
     * @return long id
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Метод, присваивающий значение высоты.
     *
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Метод для get transport
     *
     * @return Transport transport
     */
    public Transport getTransport() {
        return transport;
    }

    /**
     * Метод, присваивающий значение транспорта.
     *
     * @param transport
     */
    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    /**
     * Метод для get view
     *
     * @return View view
     */
    public View getView() {
        return view;
    }

    /**
     * Метод, присваивающий значение вида.
     *
     * @param view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Метод для get house
     *
     * @return House house
     */
    public House getHouse() {
        return house;
    }

    /**
     * Метод, присваивающий значение дома.
     *
     * @param house
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Метод для get numberOfRooms
     *
     * @return long numberOfRooms
     */
    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Метод, присваивающий значение номера комнаты.
     *
     * @param numberOfRooms
     */
    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Метод для get area
     *
     * @return int area
     */
    public int getArea() {
        return area;
    }

    /**
     * Метод, присваивающий значение размера территории.
     *
     * @param area
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * Метод для get creationDate
     *
     * @return LocalDateTime creationDate
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, присваивающий значение даты создания объекта.
     *
     * @param creationDate
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setOwner(String user) {
        this.user = user;
    }

    public String getOwner() {
        return user;
    }

    /**
     * Метод печати объекта класса Flat в строковом представлении.
     */
    public String toString() {
        return "Flat:" +
                getId() +
                "\nname = " + getName() +
                "\n" + coordinates.toString() +
                "\ncreationDate = " + getCreationDate() +
                "\nheight = " + getHeight() +
                "\ntransport = " + getTransport() +
                "\nview = " + getView() +
                "\nnumberOfRooms = " + getNumberOfRooms() +
                "\narea = " + getArea() +
                "\n" + house.toString() +
                "\n}";
    }

    @Override
    public int compareTo(Object o) {
        Flat flat = (Flat) o;
        return flat.getArea() - getArea();
    }

}
