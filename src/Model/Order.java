package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
    private final StringProperty trainID;
    private final StringProperty dep;
    private final StringProperty arr;
    private final StringProperty d_time;
    private final StringProperty a_time;
    private final StringProperty o_time;
    private final StringProperty state;
    private final StringProperty ticket_price;
    private int orderID;

    public Order(String UID, String dep, String arr, String d_time, String a_time, String o_time, String state, String ticket_price) {
        this.trainID = new SimpleStringProperty(UID);
        this.dep = new SimpleStringProperty(dep);
        this.arr = new SimpleStringProperty(arr);
        this.d_time = new SimpleStringProperty(d_time);
        this.a_time = new SimpleStringProperty(a_time);
        this.o_time = new SimpleStringProperty(o_time);
        this.state = new SimpleStringProperty(state);
        this.ticket_price = new SimpleStringProperty(String.format("%.2f",Double.parseDouble(ticket_price)));
    }

    public StringProperty getUID() {
        return trainID;
    }

    public void setUID(String UID) {
        this.trainID.set(UID);
    }

    public StringProperty getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep.set(dep);
    }

    public StringProperty getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr.set(arr);
    }

    public StringProperty getD_time() {
        return d_time;
    }

    public void setD_time(String d_time) {
        this.d_time.set(d_time);
    }

    public StringProperty getA_time() {
        return a_time;
    }

    public void setA_time(String a_time) {
        this.a_time.set(a_time);
    }

    public StringProperty getO_time() {
        return o_time;
    }

    public void setO_time(String o_time) {
        this.o_time.set(o_time);
    }

    public StringProperty getState() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public StringProperty getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(double ticket_price) {
        this.ticket_price.set(ticket_price+"");
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
