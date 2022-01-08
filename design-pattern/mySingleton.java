public class mySingleton {

    //가장 일반적인 방법
    private static mySingleton instance;

    private mySingleton(){}

    public static mySingleton getInstance(){
        if(instance == null) instance = new mySingleton();

        return  instance;
    }
}
