import java.util.Random;
import jdk.internal.misc.Unsafe;


public class UnsafeTest {
    public static void main(String[] args) {
        try {
            Unsafe.getUnsafe().allocateInstance(Random.class);
        } catch (InstantiationException e) {
            System.out.println(e);
        }
    }
}
