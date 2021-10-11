package sharedClasses.elementsOfCollection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    /**
     * Координата Х. Максимальное значение поля: 900.
     */
    private float x;
    /**
     * Координата Y. Поле может быть null.
     */
    private Float y;

    /**
     * Конструктор для создания Coordinates
     */
    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод для get X
     * @return float X
     */
    public float getX() {
        return x;
    }

    /**
     * Метод для get Y
     * @return Float Y
     */
    public Float getY() {
        return y;
    }


    /**
     * Метод печати объекта класса Coordinates в строковом представлении.
     */
    public String toString() {
        return "\nCoordinates:" +
                "\nx = " + x +
                "\ny = " + y;

    }
}

