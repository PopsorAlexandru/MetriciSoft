package Model;

import Exceptions.MyExceptions;
import Model.ADT.*;
import Model.Statements.IStatement;

import java.io.BufferedReader;

/**
 * Created by Alex on 20.10.2017.
 */
public class PrgState {
    public MyIStack<IStatement> exeStack;
    public MyIDictionary<String, Integer> symTable;
    public MyIList<Integer> out;
    public MyIFileTable<Integer, Pair<String,BufferedReader>> fileTable;
    public MyIHeap<Integer,Integer> heap;
    public IStatement originalProgram;

    public int id;

    public PrgState(MyIStack<IStatement> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out,MyIFileTable<Integer, Pair<String,BufferedReader>> fileTable, MyIHeap<Integer,Integer> heap, IStatement originalProgram,int id) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram;
        this.id = id;
        this.exeStack.push(this.originalProgram);
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Integer> getOut() {
        return out;
    }

    public void setOut(MyIList<Integer> out) {
        this.out = out;
    }

    public MyIFileTable<Integer, Pair<String, BufferedReader>> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIFileTable<Integer, Pair<String, BufferedReader>> fileTable) {
        this.fileTable = fileTable;
    }

    public MyIHeap<Integer, Integer> getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap<Integer, Integer> heap) {
        this.heap = heap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public boolean isNotComplete()
    {
        return !(exeStack.isEmpty());
    }

    public PrgState oneStep() throws MyExceptions {
        if(exeStack.isEmpty())
            throw new MyExceptions("Stack is empty");

        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);

    }

    @Override
    public String toString() {
        return "ID: " + id + "\nEXESTACK:\n" + exeStack.toString() + "SYMTABLE_" + id + "\n" + symTable.toString()  + "OUT:\n" + out.toString() + "FILETABLE:" + fileTable.toString() + "HEAP:" + heap.toString() + "\n\n";
    }
}
