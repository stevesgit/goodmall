import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

/**
 * Created By SteveWoo
 */
public class SmallTest implements Serializable {
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
}
