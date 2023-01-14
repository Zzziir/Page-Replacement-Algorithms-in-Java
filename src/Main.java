import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of pages: ");
        int numberOfPages = sc.nextInt();
        ArrayList<Integer> reference = new ArrayList<Integer>();

        System.out.println("Enter the Reference String: ");
        sc.nextLine();
        String referenceString = sc.nextLine();
        int length = referenceString.replace(" ", "").length();

        for(int i = 0; i < length; i++){
            reference.add(Integer.valueOf(String.valueOf(referenceString.replace(" ", "").charAt(i))));
        }
        PageReplacement p = new PageReplacement(reference, numberOfPages);
    }
}
