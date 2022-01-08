public class multiThread extends Thread {
    multiThread(){

    }

    public void run(){
        try{
//            mySingleton ms = mySingleton.getInstance();
//            mySingleton@563af05b
//            mySingleton@6cc1bfc7
//            mySingleton@563af05b

            //mySingleton2 ms = mySingleton2.getInstance();

//            mySingleton2@563af05b
//            mySingleton2@563af05b
//            mySingleton2@563af05b

            mySingleton4 ms = mySingleton4.getInstance();
            System.out.println(ms.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
