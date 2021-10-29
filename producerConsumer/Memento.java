package producerConsumer;

public class Memento {
    private SaveState state;

    public Memento(SaveState state) {
        this.state = state;
    }

    public SaveState getState() {
        return state;
    }
}
