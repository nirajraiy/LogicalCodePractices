public class Text {

    public static void main(String[] args) {
        int n=9;
        int count=0;

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {

                if(count==n)
                    count=0;
                count++;
                System.out.print(count+" ");
            }
            if(count==n)
                count=0;
            count++;
            System.out.println();
        }
    }
}