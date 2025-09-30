import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class test_allocate_instance {
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
        
        public TestClass() {
            this.value = 100;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Testing allocateInstance intrinsic...");
        
        try {
            // This should trigger our C1 intrinsic logging
            TestClass obj = (TestClass) UNSAFE.allocateInstance(TestClass.class);
            System.out.println("Created object with value: " + obj.getValue());
            System.out.println("Success - allocateInstance worked!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
