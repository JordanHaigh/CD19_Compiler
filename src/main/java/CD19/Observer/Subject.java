package CD19.Observer;


/**
 * Observer Pattern - Subject will notify observers about things happening in the attached class
 *
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public interface Subject
{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(ObservableMessage message);
}