/**
 * Created By SteveWoo
 */
public interface Food {
    enum Coffee implements Food {
        BLACK_COFFEE, DECAF_COFFEE, LATTE, CAPPUCCINO
    }

    enum Dessert implements Food {
        FRUIT, CAKE, GELATO;
        private int a;

        Dessert() {
        }
    }

}
