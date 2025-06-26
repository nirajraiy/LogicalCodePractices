public class Text {
    static int fun(int num, int f) {
        if (f == 1 ? num % 2 == 1 : num % 2 == 0) {
            return num;
        } else {
            return num + 1;
        }

    }

    public static void main(String[] args) {
        int n = 5;
        int ev = 0, od = -1, f = 0;

        for (int i = n; i >= 1; i--) {

            for (int j = 1; j <= n; j++) {
                if (i == j || i <= j) {
                    if ((n - i + 1) % 2 == 0) {
                        f = 0;
                        ev=ev+2;
                    } else {
                        f = 1;
                        od=od+2;
                    }
                    System.out.print((n - i + 1) % 2 == 0 ? (fun(ev, f) < 10)?fun(ev, f)+" " : fun(ev, f)+" "  : fun(od, f) + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}