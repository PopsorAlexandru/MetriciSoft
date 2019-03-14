package Model.Statements;

import Exceptions.MyExceptions;
import Model.ADT.MyDictionary;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.PrgState;

/**
 * Created by Alex on 11.12.2017.
 */
public class fork implements IStatement {
    private IStatement stmt;

    public fork(IStatement stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyExceptions {
        MyIStack<IStatement> exeStack = new MyStack<IStatement>();
        MyIDictionary<String, Integer> symTable= new MyDictionary<String, Integer>();
        for(String key: state.getSymTable().keySet())
            symTable.put(key,state.getSymTable().get(key));
        PrgState newState = new PrgState(exeStack,symTable,state.getOut(),state.getFileTable(),state.getHeap(),stmt,state.getId()*10);
        return newState;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}
