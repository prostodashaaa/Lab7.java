package sharedClasses.elementsOfCollection;

import java.io.Serializable;

/**
 * Класс House.
 */
public class House implements Serializable {
    /**
     * Название дома. Поле может быть null.
     */
    private String name;
    /**
     * Возраст дома. Максимальное значение поля: 568, Значение поля должно быть больше 0.
     */
    private Integer year;
    /**
     * Количество квартир на этаже. Значение поля должно быть больше 0.
     */
    private long numberOfFlatsOnFloor;
    /**
     * Количество лифтов. Значение поля должно быть больше 0.
     */
    private Integer numberOfLifts;

    /**
     * Конструктор.
     */
    public House(String name, Integer year, long numberOfFlatsOnFloor, Integer numberOfLifts){
        this.name = name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
        this.numberOfLifts = numberOfLifts;
    }

    /**
     * Метод печати объекта класса Coordinates в строковом представлении.
     */
    public String toString() {
        return "\nHouse:" +
                "\nnameHouse = " + name +
                "\nyear = " + year +
                "\nnumberOfFlatsOnFloor = " + numberOfFlatsOnFloor +
                "\nnumberOfLifts = " + numberOfLifts;
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
     * Метод, присваивающий значение имени.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод для get year
     *
     * @return Integer year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Метод, присваивающий значение года.
     *
     * @param year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Метод для get numberOfFlatsOnFloor
     *
     * @return long numberOfFlatsOnFloor
     */
    public long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    /**
     * Метод, присваивающий значение количества квартир на этаже.
     *
     * @param numberOfFlatsOnFloor
     */
    public void setNumberOfFlatsOnFloor(long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    /**
     * Метод для get numberOfLifts
     *
     * @return Integer numberOfLifts
     */
    public Integer getNumberOfLifts() {
        return numberOfLifts;
    }

    /**
     * Метод, присваивающий значение количества лифтов.
     *
     * @param numberOfLifts
     */
    public void setNumberOfLifts(Integer numberOfLifts) {
        this.numberOfLifts = numberOfLifts;
    }
}


