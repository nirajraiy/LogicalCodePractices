public class Text {

    public static void main(String[] args) {
        int n=5;

        for(int i=n;i>=1;i--) {
            for(int j=1;j<=n;j++) {
                System.out.print((i==j || i<=j)?n-j+1+" ": "  ");
            } System.out.println();
        }
    }
}