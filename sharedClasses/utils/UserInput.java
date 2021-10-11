package sharedClasses.utils;

import sharedClasses.elementsOfCollection.*;
import sharedClasses.utils.IOInterface;

import java.time.LocalDate;


public class UserInput {

    IOInterface ioInterface;

    public UserInput(IOInterface ioInterface) {
        this.ioInterface = ioInterface;
    }

    /**
     * Метод, считывающий значение поля name.
     *
     * @return значение поля name.
     */
    private String readName() {
        boolean flag = false;
        String name = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                name = readField("Введите название квартиры:");
                if (name.equals("")) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            name = readField("Введите название квартиры:");
            if (name.equals("")) throw new NumberFormatException();
        }
        return name;
    }

    /**
     * Метод, считывающий значение одного поля.
     *
     * @param message сообщение пользователю.
     * @return значение поля.
     */
    public String readField(String message) {
        String s;
        if (ioInterface.getPrintMessages()) System.out.println(message);
        s = ioInterface.getScanner().nextLine();
        return s;
    }

    /**
     * Метод, считывающий значение поля area.
     *
     * @return значение поля area.
     */
    private int readArea() throws NumberFormatException {
        boolean flag = false;
        int area = 1;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    area = Integer.parseInt(readField("Введите размер территории (в квадратных метрах), оно должно быть больше 0:"));
                    if (area <= 0) {
                        ioInterface.output("Значение меньше допустимого, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            area = Integer.parseInt(readField("Введите размер территории (в квадратных метрах), оно должно быть больше 0:"));
            if (area <= 0) throw new NumberFormatException();
        }
        return area;
    }

    /**
     * Метод, считывающий значение поля numberOfRooms.
     *
     * @return значение поля numberOfRooms.
     */
    private long readNumberOfRooms() throws NumberFormatException {
        boolean flag = false;
        long numberOfRooms = 1;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    numberOfRooms = Long.parseLong(readField("Введите номер комнаты, максимальное значение 8:"));
                    if (numberOfRooms <= 0 || numberOfRooms > 8) {
                        ioInterface.output("Значение не соответсвует допустимому, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            numberOfRooms = Long.parseLong(readField("Введите номер комнаты, максимальное значение 8:"));
            if (numberOfRooms <= 0 || numberOfRooms > 8) throw new NumberFormatException();
        }
        return numberOfRooms;
    }

    /**
     * Метод, считывающий значение поля height.
     *
     * @return значение поля height.
     */
    private Integer readHeight() throws NumberFormatException {
        boolean flag = false;
        Integer height = 1;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    height = Integer.parseInt(readField("Введите высоту, она должна быть больше 0:"));
                    if (height <= 0) {
                        ioInterface.output("Значение меньше допустимого, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            height = Integer.parseInt(readField("Введите высоту, она должна быть больше 0:"));
            if (height <= 0) throw new NumberFormatException();
        }
        return height;
    }

    /**
     * Метод, считывающий значение поля view.
     *
     * @return значение поля view.
     */
    private View readView() throws NumberFormatException {
        boolean flag = false;
        View view = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    view = View.valueOf(readField("Введите вид (YARD, NORMAL, GOOD, TERRIBLE):"));
                } catch (IllegalArgumentException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else view = View.valueOf(readField("Введите тип климата (YARD, NORMAL, GOOD, TERRIBLE):"));
        return view;
    }


    /**
     * Метод, считывающий значение поля transport.
     *
     * @return значение поля transport.
     */
    private Transport readTransport() throws NumberFormatException {
        boolean flag = false;
        Transport transport = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    transport = Transport.valueOf(readField("Введите тип транспорта (FEW, NONE, LITTLE, NORMAL, ENOUGH):"));
                } catch (IllegalArgumentException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else transport = Transport.valueOf(readField("Введите тип транспорта (FEW, NONE, LITTLE, NORMAL, ENOUGH):"));
        return transport;
    }

    /**
     * Метод, считывающий значение поля house.
     *
     * @return значение поля house.
     */
    private House readHouse() throws NumberFormatException {
        boolean flag = false;
        String name = null;
        Integer year = null;
        long numberOfFlatsOnFloor = 1;
        Integer numberOfLifts = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    name = readField("Введите название дома:");
                    if (name.equals("")) {
                        ioInterface.output("Значение не соответсвует допустимому, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }

            flag = false;
            while (!flag) {
                flag = true;
                try {
                    year = Integer.parseInt(readField("Введите возраст дома, максимальное значение 568:"));
                    if (year <= 0 || year > 568) {
                        ioInterface.output("Неверный формат данных, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }

            flag = false;
            while (!flag) {
                flag = true;
                try {
                    numberOfFlatsOnFloor = Long.parseLong(readField("Введите количество квартир на этаже, оно должно быть больше 0:"));
                    if (numberOfFlatsOnFloor <= 0) {
                        ioInterface.output("Неверный формат данных, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }

            flag = false;
            while (!flag) {
                flag = true;
                try {
                    numberOfLifts = Integer.parseInt(readField("Введите количество лифтов, оно должно быть больше 0:"));
                    if (numberOfFlatsOnFloor <= 0) {
                        ioInterface.output("Неверный формат данных, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            name = readField("Введите название дома:");
            year = Integer.parseInt(readField("Введите возраст дома, максимальное значение 568:"));
            numberOfFlatsOnFloor = Long.parseLong(readField("Введите количество квартир на этаже, оно должно быть больше 0:"));
            numberOfLifts = Integer.parseInt(readField("Введите количество лифтов, оно должно быть больше 0:"));
            if (year <= 0 || year > 568 || numberOfFlatsOnFloor <= 0 || numberOfLifts <=0) throw new NumberFormatException();
        }
        return new House(name, year, numberOfFlatsOnFloor, numberOfLifts);
    }

    /**
     * Метод, считывающий и создающий объект класса Flat.
     *
     * @return новый объект класса Flat.
     */
    public Flat readFlat() throws NumberFormatException {
        Flat flat = new Flat();
        flat.setName(readName());
        flat.setCoordinates(readCoordinates());
        flat.setCreationDate(LocalDate.now());
        flat.setArea(readArea());
        flat.setNumberOfRooms(readNumberOfRooms());
        flat.setHeight(readHeight());
        flat.setView(readView());
        flat.setTransport(readTransport());
        flat.setHouse(readHouse());
        return flat;
    }

    /**
     * Метод, считывающий значение поля coordinates.
     *
     * @return значение поля coordinates.
     */
    public Coordinates readCoordinates() throws NumberFormatException {
        boolean flag = false;
        float x = 1;
        Float y = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    x = Float.parseFloat(readField("Введите координату х, максимальное значение 900:"));
                    if (x > 900) {
                        ioInterface.output("Значение больше допустимого, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }

            flag = false;
            while (!flag) {
                flag = true;
                try {
                    y = Float.parseFloat(readField("Введите координату у:"));
                    if (y.equals("")) {
                        ioInterface.output("Неверный формат данных, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            x = Float.parseFloat(readField("Введите координату х, максимальное значение 900:"));
            y = Float.parseFloat(readField("Введите координату у:"));
            if (x > 900) throw new NumberFormatException();
        }
        return new Coordinates(x, y);
    }
}
