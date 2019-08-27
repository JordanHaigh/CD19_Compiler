package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class StateMachine.java
 * State Machine Stores current state, previous state. Has functionality to reset and start state changes.
 * */
public class StateMachine {

    private State currentState;
    private State previousState;

    public StateMachine(){
        reset();
    }

    public void updateState(char c){
        this.currentState.updateState(this, c);
    }

    public State getCurrentState() {return currentState;}
    public State getPreviousState() { return previousState; }
    public void setCurrentState(State currentState){
        this.previousState = this.currentState;
        this.currentState = currentState;
    }

    public void reset(){
        this.previousState = currentState;
        this.currentState = new InitState();
    }



}
