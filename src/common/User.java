package common;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String customerId;
    public User(){}
    public User(String name, String customerId) {
        this.name=name;
        this.customerId=customerId;
    }
    @Override
    public String toString() {
        return "common.User{" +
                "name='" + name + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
    //set + get: name
    public void setName(String name) {
        this.name=name;
    }
    public String getName() {
        return this.name;
    }
//    set+get: customerId
    public String getCustomerId() {
        return this.customerId;
    }
    public  void setCustomerId(String customerId) {
            this.customerId=customerId;
    }
    public void displayName(){
        System.out.println(this.name);
    }

}
