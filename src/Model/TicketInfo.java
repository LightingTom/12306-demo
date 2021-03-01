package Model;

public class TicketInfo {
    public String trainID;
    public String depart_station;
    public String arrive_station;
    public String depart_time;
    public String arrive_time;
    public int rest;
    public int status = 0;
    public String seatType;
    public int ticketID = 0;
    public double price;


    public TicketInfo(){};

    public TicketInfo(String trainID, String depart_station,
                      String arrive_station, String depart_time, String arrive_time,
                      int rest,int ticketID,String seat_type,double price) {
        this.trainID = trainID;
        this.depart_station = depart_station;
        this.arrive_station = arrive_station;
        this.depart_time = depart_time;
        this.arrive_time = arrive_time;
        this.rest = rest;
        this.seatType = seat_type;
        this.ticketID = ticketID;
        this.price = price;
    }

    @Override
    public String toString() {
        String res = String.format("TrainID:%s\n\r" +
                "Depart_station:%s   Arrive_station:%s\n\r" +
                "Depart_time:%s      Arrive_time:%s\n\r" +
                "Seat_type:%s        Rest ticket: %d",trainID,depart_station,arrive_station,depart_time,arrive_time,seatType,rest);
        return res;
    }
}
