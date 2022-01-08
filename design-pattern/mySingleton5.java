public class mySingleton5 {
    //이중 체크 잠금
    //Double check lock

    //volatile는 Java 변수를 Main Memory에 저장, CPU cache 에 r/w 가 아니라 Main Memory에서 읽어 옵니다.
    //Volatile stores Java variables in Main Memory and reads them from Main Memory, not r/w to CPU cache.
    private volatile mySingleton5 instance;

    private mySingleton5(){}
    public mySingleton5 getInstance(){
        if(instance == null){ //1. 싱글톤 클래스 잠근 전근
                              //Single tone class, locked transfer.
            synchronized (mySingleton5.class){
                if(instance == null){ //2. 객체를 생성할 때 한번
                                      //When you create an object,
                    instance = new mySingleton5();
                }
            }
        }
        return instance;
    }
}
