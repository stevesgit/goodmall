/**
 * Created By SteveWoo
 */

public class enumTest {
    public static void main(String[] args) {
        System.out.println(Signal.GREEN.getCode());
        String name = Signal.getName(1);
        System.out.println(name);
        System.out.println(Food.Coffee.BLACK_COFFEE);
    }
}

enum Signal {
    RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);
    private String color;
    private int code;

    Signal(String color, int code) {
        this.color = color;
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getName(int code) {
        for (Signal c : Signal.values()) {
            if (c.getCode() == code) {
                return c.color;
            }
        }
        return null;
    }
}
