package Model.ADT;

import Exceptions.MyExceptions;

/**
 * Created by Alex on 20.10.2017.
 */
public interface MyIStack<T> {
    boolean isEmpty();
    void push(T v);
    T pop() throws MyExceptions;
}
