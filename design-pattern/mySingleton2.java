public class mySingleton2 {

    //synchronized 방법
    private static mySingleton2 instance;

    private mySingleton2() {}

    public static synchronized mySingleton2 getInstance() {
        if(instance == null) instance = new mySingleton2();
        return instance;
    }
}
