public class mySingleton4 {

    //정적블록을 사용하는 방법
    //*정적블록 = 클래스 로딩할 때 자동으로 실행
    private static mySingleton4 instance = null;

    static {
        instance = new mySingleton4();
    }

    private mySingleton4(){}

    public static mySingleton4 getInstance(){
        return instance;
    }
}
