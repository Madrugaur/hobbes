package com.bradenlittle.hobbes.async.thread;
import java.util.ArrayList;

/**
 * This class represents a task to be done by the SQL database. It can contain statements or queries and all of the
 * parameters that are needed.
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class SQLTask {
    private final ArrayList<String> statements;
    private final ArrayList<Object[]> arguments;

    /**
     * Constructor, inits local variables
     */
    public SQLTask(){
        statements = new ArrayList<>();
        arguments = new ArrayList<>();
    }

    /**
     * Adds a statement and its arguments to the list of commands to be executed
     * @param command a string containing an SQL command
     * @param args the arguments that will be inserted into this command
     */
    public void addStatement(String command, Object[] args){
        statements.add(command);
        arguments.add(args);
    }

    /**
     * Returns a list of all of the statements that have been added to this task
     * @return a list of statements
     */
    public ArrayList<String> getStatements() {
        return statements;
    }

    /**
     * Returns a list of all of the arguments that have been added to this task
     * @return a list of arguments
     */
    public ArrayList<Object[]> getArguments() {
        return arguments;
    }

    /**
     * Returns the size of this task, i.e. the number of statements to be executed
     * @return the size
     */
    public int size() { return statements.size(); }
}
