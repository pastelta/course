package ru.practice;

public class OperationAttemptException extends Exception{
    public OperationAttemptException(){
        super();
    }
    public OperationAttemptException(String message){
        super(message);
    }
}
