public class testMain {

    public static void main(String args[]) throws Exception {
        multiThread[] mt = new multiThread[3];
        for(int i=0; i<3; i++){
            mt[i] = new multiThread();
            mt[i].start();
        }
    }
}
