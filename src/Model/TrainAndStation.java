package Model;

public class TrainAndStation {
    String train1;
    String train2;
    String transfer;
    int distance;

    public TrainAndStation(String train1, String train2, int distance,String transfer) {
        this.train1 = train1;
        this.train2 = train2;
        this.distance = distance;
        this.transfer = transfer;
    }

    public String getTrain1() {
        return train1;
    }

    public void setTrain1(String train1) {
        this.train1 = train1;
    }

    public String getTrain2() {
        return train2;
    }

    public void setTrain2(String train2) {
        this.train2 = train2;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return train1+"次 ->"+train2+"次\n"+ "换乘站： "+transfer+"      距离："+distance;
    }
}
