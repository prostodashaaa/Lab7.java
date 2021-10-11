package server.collectionUtils;

import server.serverUtils.DataBaseControl;
import sharedClasses.elementsOfCollection.Flat;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс для хранения коллекции.
 */
public class PriorityQueueStorage implements StorageInterface<Flat> {
    /**
     * Путь к исходному файлу.
     */
    private final DataBaseControl dataBaseControl;
    /**
     * Дата создания.
     */
    private final LocalDate creationDate;
    /**
     * Коллекция.
     */
    private Collection<Flat> priorityQueue = Collections.synchronizedCollection(new PriorityQueue<>(10, (c1, c2) -> c2.getArea() - c1.getArea()));

    public PriorityQueueStorage(DataBaseControl dataBaseControl) {
        this.dataBaseControl = dataBaseControl;
        creationDate = LocalDate.now();
    }

    /**
     * Метод, проверяющий элемент коллекции на допустимые знаечения полей.
     *
     * @param flat проверяемый объект.
     */
    public void checkElement(Flat flat) throws NumberFormatException {
        if (flat.getName().equals("") || flat.getCoordinates() == null ||
                flat.getCoordinates().getY() == null || flat.getHeight() == null || flat.getView() == null || flat.getTransport() == null)
            throw new NullPointerException();
        if (flat.getCoordinates().getX() > 900 || flat.getCoordinates().getY() <= -989 || flat.getArea() <= 0 ||
                flat.getNumberOfRooms() > 8 || flat.getNumberOfRooms() <= 0 || flat.getHeight() <= 0 ||
                flat.getHouse().getYear() <= 0 || flat.getHouse().getYear() > 568 || flat.getHouse().getNumberOfFlatsOnFloor() <=0 ||
                flat.getHouse().getNumberOfLifts() <=0)
            throw new NumberFormatException();
    }

    /**
     * Метод, возвращающий дату создания коллекции.
     *
     * @return дата создания коллекции.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, возвращающий коллекцию.
     *
     * @return коллекция.
     */
    public Collection<Flat> getCollection() {
        return priorityQueue;
    }

    public void addToCollection(Flat c, User user) throws SQLException, ParseException, ClassNotFoundException {
        c = dataBaseControl.addToDataBase(c, user);
        priorityQueue.add(c);
    }

    public void clear(User user) throws SQLException {
        dataBaseControl.clear(user);
        final String s = user.getLogin();
        priorityQueue = priorityQueue.stream().
                filter(c -> !c.getOwner().equals(s)).
                collect(Collectors.toCollection(PriorityQueue::new));
    }

    public boolean remove(Flat flat, User user) throws SQLException {
        boolean flag = dataBaseControl.remove(flat, user);
        if (flag) priorityQueue.remove(flat);
        return flag;
    }

    public boolean update(Flat oldFlat, Flat flat, long id, User user) throws SQLException {
        boolean flag = dataBaseControl.update(flat, id, user);
        if (flag) {
            priorityQueue.remove(oldFlat);
            flat.setId(id);
            priorityQueue.add(flat);
        }
        return flag;
    }

    public Flat pollFromQueue() {
        Flat flat = priorityQueue.stream().max(Flat::compareTo).get();
        priorityQueue.remove(flat);
        return flat;
    }

}

