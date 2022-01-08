package reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

public class Test {
    public static void main(String args[]) throws Exception {
        //Class 찾기
        Class clazz = Child.class;
        System.out.println("Class name: "+clazz.getName());

        //Class 이름으로 정보 찾기
        Class clazz2 = Class.forName("reflection.Child");
        System.out.println("Class name: "+clazz2.getName());

        //Constructor로 찾기
        Class clazz3 = Class.forName("reflection.Child");
        Constructor constructor = clazz3.getDeclaredConstructor(String.class);
        System.out.println("Constructor: "+constructor.getName());

        //모든 생성자는 가져오기 (private, public)
        Class clazz4 = Class.forName("reflection.Child");
        Constructor constructors[] = clazz4.getDeclaredConstructors();
        for(Constructor cons : constructors){
            System.out.println("Get constructors in Child: " +cons);
        }

        //public 생성자 리턴
        Class clazz5 = Class.forName("reflection.Child");
        Constructor constructors2[] = clazz5.getConstructors();
        for(Constructor cons : constructors2){
            System.out.println("Get public constructors in Child: " +cons);
        }

        //refliection API로 private method에 접근하기
        Child child = new Child();
        Class clazz6 = Class.forName("reflection.Parent");
        Method method = clazz6.getDeclaredMethod("method1");
        method.setAccessible(true);
        method.invoke(child);

        //reflection API로 private method에 field 접근하기
        Class clazz7 = Class.forName("reflection.Child");
        Field fld2 = clazz7.getDeclaredField("cstr2");
        fld2.setAccessible(true);
        fld2.set(child, "cstr3");
        System.out.println(fld2);
        System.out.println("child.cstr2: "+fld2.get(child));


    }
}
