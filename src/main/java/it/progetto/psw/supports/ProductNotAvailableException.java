package it.progetto.psw.supports;

public class ProductNotAvailableException extends Exception{
    public ProductNotAvailableException(String message){
        super(message);
    }
    public ProductNotAvailableException(){}
}
