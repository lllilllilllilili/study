
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

public class reflectionTest {
    public static void main(String args[]) throws Exception {
        Class<mySingleton5> msclass = mySingleton5.class;
        System.out.println("Class name: "+msclass.getName());
        Class clazz2 = Class.forName("mySingleton5");
        Field fld = clazz2.getDeclaredField("instance");
        fld.setAccessible(true);
        fld.set(msclass, "123");
        System.out.println(fld.get(msclass));
    }
}
