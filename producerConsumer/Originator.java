package producerConsumer;

public class Originator {
    private SaveState state;

    public void setState(SaveState state){
        this.state = state;
    }

    public SaveState getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
