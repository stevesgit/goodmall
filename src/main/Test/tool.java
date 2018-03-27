import com.mmall.common.ResponseCode;
import com.sun.tools.internal.jxc.ap.Const;

public class tool {
    private tool() {
    }
    public class toolA extends tool {
    }

    public static void main(String[] args) {
        ResponseCode[] a = ResponseCode.values();
        for (ResponseCode item : ResponseCode.values()){
            System.out.println(item);
        }
    }

}

