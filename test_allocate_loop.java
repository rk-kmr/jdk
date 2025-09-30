import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class test_allocate_loop {
    private static final Unsafe UNSAFE;
    
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    static class TestClass {
        private int value = 42;
    }
    
    public static void main(String[] args) {
        System.out.println("Testing allocateInstance intrinsic in loop...");
        
        // Force compilation by calling many times
        for (int i = 0; i < 10000; i++) {
            try {
                TestClass obj = (TestClass) UNSAFE.allocateInstance(TestClass.class);
                if (i % 1000 == 0) {
                    System.out.println("Iteration " + i + ", value: " + obj.value);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
        System.out.println("Done!");
    }
}
