package Controller;

import Exceptions.MyExceptions;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Statements.IStatement;
import Repository.IRepository;
import Repository.MyRepoExceptions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Alex on 21.10.2017.
 */
public class Controller {
    private IRepository repo;
    private ExecutorService executor;
    private boolean flag;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }



    public Controller(IRepository repo) {
        this.repo = repo;
        this.flag =true;
    }

    Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer,Integer> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

    }

    public PrgState oneStep(PrgState state) throws MyExceptions{
        MyIStack<IStatement> stack = state.getExeStack();
        if(stack.isEmpty() == true)
            throw new MyExceptions("Stack is empty");
        IStatement stmt = stack.pop();
        return stmt.execute(state);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotComplete())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyControllerException, InterruptedException {
        try {
            prgList.forEach(prg -> repo.logPrgStateExec(prg));
        }catch(MyRepoExceptions e){
            throw new MyControllerException(e.toString());
        }

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future ->
        { try { return future.get();
        }catch(Exception e) {
            throw new MyControllerException(e.toString());}
        }).filter(p -> p!=null).collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg ->repo.logPrgStateExec(prg));
        repo.setStates(prgList);
    }



    public void allSteps(){

//        try {
//            while (true) {
//                oneStep(prg);
//                prg.getHeap().setHeap(new HashMap<Integer,Integer>(conservativeGarbageCollector(prg.getSymTable().values(),prg.getHeap().getHeap())));
//                repo.logPrgStateExec();
//            }
//        }catch(MyExceptions e){
//            System.out.println(e.toString());
//        }
//        catch(MyStmtException e){
//            System.out.println(e.toString());
//        }
//        catch(MyExpException e){
//            System.out.println(e.toString());
//        }
//        finally {
//            try {
//                prg.getFileTable().keySet().stream().forEach(v -> new closeRFile(new ConstExp(v)).execute(prg));
//            }catch(Exception e){
//                System.out.println(e.toString());
//            }
//            System.out.println(prg.toString());
//        }
        executor = Executors.newFixedThreadPool(5);

        List<PrgState> prgList=removeCompletedPrg(repo.getStates());
        while(prgList.size() > 0){
            try {
                oneStepForAllPrg(prgList);
            }catch (MyControllerException e){
                System.out.println(e.toString());
            }
            catch(InterruptedException e){
                System.out.println(e.toString());
            }

            prgList=removeCompletedPrg(repo.getStates());
        }
        executor.shutdownNow();
        repo.setStates(prgList);

    }

    @Override
    public String toString() {
        return repo.toString();
    }
}
