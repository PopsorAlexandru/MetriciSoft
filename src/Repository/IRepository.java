package Repository;

import Model.PrgState;

import java.util.List;

/**
 * Created by Alex on 21.10.2017.
 */
public interface IRepository {
    //PrgState getCrtPrg();
    void add(PrgState state);
    void logPrgStateExec(PrgState state) throws MyRepoExceptions;
    void setStates(List<PrgState> states);
    List<PrgState> getStates();
}
