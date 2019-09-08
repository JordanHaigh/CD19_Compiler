package CD19.Observer;

public interface Subject
{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(ObservableMessage message);
}