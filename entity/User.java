// entity/User.java
package entity;

import java.util.Date;

public class User {
    private int userId;
    private int balance;
    private Date createdAt;
    
    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
        this.createdAt = new Date();
    }
    
    // Getters and Setters
    public int getUserId() { 
        return userId; 
    }
    
    public int getBalance() { 
        return balance; 
    }
    
    public void setBalance(int balance) { 
        this.balance = balance; 
    }
    
    public Date getCreatedAt() { 
        return createdAt; 
    }
    
    public void deductBalance(int amount) {
        this.balance -= amount;
    }
    
    @Override
    public String toString() {
        return "User{userId=" + userId + ", balance=" + balance + "}";
    }
}