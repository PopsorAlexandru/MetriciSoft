package Repository;

import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 21.10.2017.
 */
public class Repository implements IRepository {
    //private PrgState[] states;
    private List<PrgState> states;
    private String logFilePath;
    private int currentState;

//    public Repository() {
//        states = new PrgState[10];
//        this.currentState =0;
//    }

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
        states = new ArrayList<PrgState>();
        this.currentState =0;

    }

    public List<PrgState> getStates() {
        return states;
    }

    public void setStates(List<PrgState> states) {
        this.states = states;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void add(PrgState state){
        this.states.add(state);
        currentState++;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyRepoExceptions {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        }catch(IOException e){
            throw new MyRepoExceptions("Log file can not be opened");
        }
        logFile.append(state.toString());
        logFile.close();

    }

//    @Override
//    public PrgState getCrtPrg() {
//        return states.get(currentState);
//
//    }

    @Override
    public String toString() {
        String str = "";

        for(PrgState state : states)
        {
            str = str + state.toString();
        }
        str = str + "\n\n";
        return str;
    }
}
