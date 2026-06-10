package model;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List; 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Finance {
    public String username;
    public String cardNum;
    public String bank;
    public BigDecimal balance;
    public List<String> history;
    private final Path path = Paths.get("transaction_history.txt"); 

    public Finance(){
    }

    public Finance(String username, String cardNum, String bank, String balance){
        this.username = username;
        this.cardNum = cardNum;
        this.bank = bank;
        this.balance = new BigDecimal(balance);
        this.history = retrieveHistory();
    }

    public List<String> toStorageBlock(){
        List<String> block = new ArrayList<>();
        block.add("username: " + username);
        block.add("cardNum: " + cardNum);
        block.add("bank: " + bank);
        block.add("balance: " + balance.toString());
        block.add("------------");
        return block;
    }


    private List<String> retrieveHistory(){
        List<String> returned = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        if(Files.exists(path)){
            try {
                if(Files.exists(path))
                    lines = Files.readAllLines(path);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read users file", e);
            }
        }
        
        return returned;

    }    


    private List<String> readLines(boolean isUsers) {
        if(isUsers){
            try {
                if (!Files.exists(storagePath)) {
                    return new ArrayList<>();
                }
                return Files.readAllLines(storagePath);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read users file", e);
            }
        }
        else{
            try {
                if (!Files.exists(financePath)) {
                    return new ArrayList<>();
                }
                return Files.readAllLines(financePath);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read finance file", e);
            }
        }
    }

    public void RegisterCard(){
        
    }    

    public void Deposit(){

    }

    public void Withdraw(){

    }

    public void viewBalance(){

    }

    public void viewTransactionHistory(){

    }



}
