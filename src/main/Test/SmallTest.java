import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

/**
 * Created By SteveWoo
 */
public class SmallTest implements Serializable {
    private Integer number;

    /**
     * Guava 测试
     */
    @Test
    public void test1() {
        String str = "a,b,c";
        List<String> result = Arrays.asList(str.split(","));
        System.out.println(result);
        Map<String, Map<Long, List<String>>> map = Maps.newHashMap();

    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("c");
        Set<String> set = new HashSet<>();
        set.add("d");
        set.add("b");
        set.add("u");
        String str = Joiner.on(",").join(set);
        System.out.println(str);
    }

    /**
     * 自动装箱拆箱注意点
     */
    @Test
    public void test3() {
        long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

    //值比较 Integer 在-128 ~ 127内是相等的 jvm只会创建一个Integer对象 a和b引用同一份对象
    //所以这里是true
    @Test
    public void test4() {
        Integer a = 1;
        Integer b = 1;
        System.out.println(a == b);
    }

    @Test
    public void test5() {
        //NullPointerException on unboxing
        //拆箱遇到空指针错误 因为这个原本为null
        System.out.println(number);
        if (number < 3) {
            System.out.println("success");
        }
    }
}
