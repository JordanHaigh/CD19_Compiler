package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class State.java
 * State Interface used for State Transitions
 * */
public interface State {

    void updateState(StateMachine sm, char c);

}

