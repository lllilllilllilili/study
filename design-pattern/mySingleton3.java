public class mySingleton3 {

    //클래스 로딩과 동시에 싱글톤 인스턴스를 만드는 방법
    private final static mySingleton3 instance = new mySingleton3();
    private mySingleton3() {}
    public static mySingleton3 getInstance(){
        return instance;
    }
}
