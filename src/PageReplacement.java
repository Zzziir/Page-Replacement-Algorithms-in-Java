import java.util.*;

public class PageReplacement {

    //Fields of PageReplacement Class
    static int numberOfPages, pointer, pageFault;
    static ArrayList<Integer> reference = new ArrayList<Integer>();
    static int[] currentPages;
    static int[] prevPages;
    static boolean isFull;

    //table for visualization of pages
    static String[][] table;

    //Constructor
    public PageReplacement(ArrayList<Integer> reference, int numberOfPages){

        boolean loop = true;
        while(loop) {

            PageReplacement.reference = reference;
            PageReplacement.numberOfPages = numberOfPages;
            pointer = 0;
            pageFault = 0;
            isFull = false;
            currentPages = new int[numberOfPages];
            prevPages = new int[numberOfPages];
            table = new String[reference.size()][numberOfPages];

            Scanner sc = new Scanner(System.in);

            System.out.println("\n[0] FIFO (First-in, First-out)" +
                    "\n[1] OPT (Optimal Algorithm)" +
                    "\n[2] LRU (Least Recently Used)" +
                    "\n[3] MRU (Most Recently Used)" +
                    "\n[4] LFU (Least Frequently Used)" +
                    "\n[5] MFU (Most Frequently Used)\n");

            System.out.print("What Page Replacement Algorithm do you want to simulate?: ");
            int option = sc.nextInt();
            switch (option) {
                case 0:
                    //FIFO
                    System.out.println("\nFIFO (First-in, First-out)\n");
                    FIFO();
                    loop = tryAgain();
                    break;

                case 1:
                    //OPT
                    System.out.println("\nOPT (Optimal Algorithm)\n");
                    Optimal();
                    loop = tryAgain();
                    break;

                case 2:
                    //LRU
                    System.out.println("\nLRU (Least Recently Used)\n");
                    LRU();
                    loop = tryAgain();
                    break;

                case 3:
                    //MRU
                    System.out.println("\nMRU (Most Recently Used)\n");
                    MRU();
                    loop = tryAgain();
                    break;

                case 4:
                    //LFU
                    System.out.println("\nLFU (Least Frequently Used)\n");
                    LFU();
                    loop = tryAgain();
                    break;

                case 5:
                    //MFU
                    System.out.println("\nMFU (Most Frequently Used)\n");
                    MFU();
                    loop = tryAgain();
                    break;

                default:
                    System.out.println("\nInvalid Option. Please choose another. ");
            }
        }
    }

    //==========================================================================================//
    //Method for FIFO Algorithm
    public static void FIFO(){

        /*Initially assigns a value to the currentPages array. Its values are assigned as -1 since the values of processes
          the user will enter are at least 0.
        */
        for(int j = 0; j < numberOfPages; j++){
            currentPages[j] = -1;
        }

        //Reiterates each value from the reference string
        for(int i = 0; i < reference.size(); i++){
            boolean isInsideThePages = false;

            //Copies the elements inside currentPages to prevPages. It will be used for visualization of the pages.
            for (int j = 0; j < currentPages.length; j++) {
                prevPages[j] = currentPages[j];
            }

            /*Checks if the current value from the reference string is occupying the pages. If so, updates the boolean
              isInsideThePages to true.
            */
            for(int j = 0; j < numberOfPages; j++){
                if(currentPages[j] == reference.get(i)){
                    isInsideThePages = true;
                    break;
                }
            }

            /*Changes the first value that went in (pointer) that is currently inside the current pages with the
              current reference string value (i) if the current reference string value (i) is not in the current pages.
            */
            if(!isInsideThePages){
                currentPages[pointer] = reference.get(i);
                pageFault++;
                pointer++;

                /*
                Resets the pointer to 0 if it already has changed the last value in the current pages
                 */
                if(pointer == numberOfPages){
                    pointer = 0;
                }
            }

            //Assigning elements to the table for Visualization of Pages
            for(int j = 0; j < numberOfPages; j++){
                if(Arrays.equals(currentPages, prevPages)){
                        table[i][j] = " ";
                }
                else {
                    if (currentPages[j] == -1) {
                        table[i][j] = " ";
                    } else {
                        table[i][j] = String.valueOf(currentPages[j]);
                    }
                }
            }
        }
        //Method calls the printPages to display the table of pages
        printPages();
    }

    //==========================================================================================//
    //Method for Optimal Algorithm
    public static void Optimal(){

        /*Initially assigns a value to the currentPages array. Its values are assigned as -1 since the values of processes
          the user will enter are at least 0.
        */
        for(int j = 0; j < numberOfPages; j++){
            currentPages[j] = -1;
        }

        //Reiterates each value from the reference string
        for(int i = 0; i < reference.size(); i++){
            boolean isInsideThePages = false;

            //Copies the elements inside currentPages to prevPages. It will be used for visualization of the pages.
            for (int j = 0; j < currentPages.length; j++) {
                prevPages[j] = currentPages[j];
            }

            /*Checks if the current value from the reference string is occupying the pages. If so, updates the boolean
              isInsideThePages to true.
            */
            for(int j = 0; j < numberOfPages; j++){
                if(currentPages[j] == reference.get(i)){
                    isInsideThePages = true;
                    break;
                }
            }

            /*
            Executes this conditional statement if the current reference string value (i) is currently not inside the
            current pages
             */
            if(!isInsideThePages){

                /*Executes when the currentPages array is full
                */
                if(isFull){
                    int[] index = new int[numberOfPages];
                    boolean[] index_flag = new boolean[numberOfPages];

                    //Determines the last index of each page from the currentPages that occurred in the reference string
                    for(int j = i + 1; j < reference.size(); j++){
                        for(int k = 0; k < numberOfPages; k++){
                            if((reference.get(j) == currentPages[k]) && (!index_flag[k])){
                                index[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int max = index[0];
                    pointer = 0;

                    //Initially assigns a value to max if it is 0
                    if(max == 0){
                        max = 200;
                    }
                    //Determines the index of the max value in the index array. The max value indicates the page to be replaced.
                    for(int j = 0; j < numberOfPages; j++){
                        if(index[j] == 0){
                            index[j] = 200;
                        }
                        if(index[j] > max){
                            max = index[j];
                            pointer = j;
                        }
                    }
                }
                //Sets the pointer index of currentPages with the current reference string value
                currentPages[pointer] = reference.get(i);
                pageFault++;
                if(!isFull){
                    pointer++;

                    /*Checks if the current pages is already full based from the pointer variable as it reaches the total number
                    of pages set by the user
                    */
                    if(pointer == numberOfPages){
                        pointer = 0;
                        isFull = true;
                    }
                }
            }

            //Assigning elements to the table for Visualization of Pages
            for(int j = 0; j < numberOfPages; j++){

                if(Arrays.equals(currentPages, prevPages)){
                    table[i][j] = " ";
                }
                else {
                    if (currentPages[j] == -1) {
                        table[i][j] = " ";
                    } else {
                        table[i][j] = String.valueOf(currentPages[j]);
                    }
                }
            }
        }
        //Method calls the printPages to display the table of pages
        printPages();
    }

    //==========================================================================================//
    //Method for Least Recently Used Algorithm
    public static void LRU(){

        //storage for the recent reference string values
        ArrayList<Integer> recent = new ArrayList<Integer>();

        /*Initially assigns a value to the currentPages array. Its values are assigned as -1 since the values of processes
          the user will enter are at least 0.
        */
        for(int j = 0; j < numberOfPages; j++){
            currentPages[j] = -1;
        }

        //Reiterates each value from the reference string
        for(int i = 0; i < reference.size(); i++){

            //Puts the current reference string value to the top of the stack
            if(recent.contains(reference.get(i))){
                recent.remove(recent.indexOf(reference.get(i)));
            }
            recent.add(reference.get(i));
            boolean isInsideThePages = false;

            //Copies the elements of currentPages to prevPages array
            for (int j = 0; j < currentPages.length; j++) {
                prevPages[j] = currentPages[j];
            }

            /*Checks if the current value from the reference string is occupying the pages. If so, updates the boolean
              isInsideThePages to true.
            */
            for(int j = 0; j < numberOfPages; j++){
                if(currentPages[j] == reference.get(i)){
                    isInsideThePages = true;
                    break;
                }
            }

            /*
            Executes this conditional statement if the current reference string value (i) is currently not inside the
            current pages
             */
            if(!isInsideThePages){

                /*Determines the index of the value that is the least recently used if the current pages are full which
                  will set the value of the pointer
                 */
                if(isFull){
                    int leastRecentIndex = reference.size();
                    for(int j = 0; j < numberOfPages; j++){
                        if(recent.contains(currentPages[j])){
                            int temp = recent.indexOf(currentPages[j]);
                            if(temp < leastRecentIndex){
                                leastRecentIndex = temp;
                                pointer = j;
                            }
                        }
                    }
                }

                /*
                Updates the page with the most recent value with the current reference string value (i). Also adds the
                number of page fault
                 */
                currentPages[pointer] = reference.get(i);
                pageFault++;
                pointer++;

                /*
                Checks if the current pages is already full based from the pointer variable as it reaches the total number
                of pages set by the user
                 */
                if(pointer == numberOfPages){
                    pointer = 0;
                    isFull = true;
                }
            }
            //Assigning elements to the table for Visualization of Pages
            for(int j = 0; j < numberOfPages; j++){

                if(Arrays.equals(currentPages, prevPages)){
                    table[i][j] = " ";
                }
                else {
                    if (currentPages[j] == -1) {
                        table[i][j] = " ";
                    } else {
                        table[i][j] = String.valueOf(currentPages[j]);
                    }
                }
            }
        }
        //Method calls the printPages to display the table of pages
        printPages();
    }

    //==========================================================================================//
    //Method for Most Recently Used Algorithm
    public static void MRU(){

        //storage for the recent reference string values
        ArrayList<Integer> recent = new ArrayList<Integer>();

        /*Initially assigns a value to the currentPages array. Its values are assigned as -1 since the values of processes
          the user will enter are at least 0.
        */
        for(int j = 0; j < numberOfPages; j++){
            currentPages[j] = -1;
        }

        //Reiterates each value from the reference string
        for(int i = 0; i < reference.size(); i++){
            if(recent.contains(reference.get(i))){
                recent.remove(recent.indexOf(reference.get(i)));
            }
            recent.add(reference.get(i));
            boolean isInsideThePages = false;

            //Copies the elements of currentPages to prevPages array
            for (int j = 0; j < currentPages.length; j++) {
                prevPages[j] = currentPages[j];
            }

            /*Checks if the current value from the reference string is occupying the pages. If so, updates the boolean
              isInsideThePages to true.
            */
            for(int j = 0; j < numberOfPages; j++){
                if(currentPages[j] == reference.get(i)){
                    isInsideThePages = true;
                    break;
                }
            }
            /*
            Executes this conditional statement if the current reference string value (i) is currently not inside the
            current pages
             */
            if(!isInsideThePages){

                /*Determines the index of the value that is the most recently used if the current pages are full which
                  will set the value of the pointer
                 */
                if(isFull){
                    int mostRecentIndex = 0;
                    for(int j = 0; j < numberOfPages; j++){
                        if(recent.contains(currentPages[j])){
                            int temp = recent.indexOf(currentPages[j]);
                            if(temp > mostRecentIndex){
                                mostRecentIndex = temp;
                                pointer = j;
                            }
                        }
                    }
                }
                /*
                Updates the page with the most recent value with the current reference string value (i). Also adds the
                number of page fault
                 */
                currentPages[pointer] = reference.get(i);
                pageFault++;
                pointer++;

                /*
                Checks if the current pages is already full based from the pointer variable as it reaches the total number
                of pages set by the user
                 */
                if(pointer == numberOfPages){
                    pointer = 0;
                    isFull = true;
                }
            }

            //Assigns elements to the table for Visualization of Pages
            for(int j = 0; j < numberOfPages; j++){

                if(Arrays.equals(currentPages, prevPages)){
                    table[i][j] = " ";
                }
                else {
                    if (currentPages[j] == -1) {
                        table[i][j] = " ";
                    } else {
                        table[i][j] = String.valueOf(currentPages[j]);
                    }
                }
            }
        }
        //Method calls the printPages to display the table of pages
        printPages();
    }

    //==========================================================================================//
    //Method for Least Frequently Used Algorithm
    public static void LFU(){

        //storage for frequencies of the values inside the current pages
        ArrayList<Integer> frequencyCurrentPages = new ArrayList<Integer>();

        /*storage for the indexes with the lowest frequency (in case of there are two or more pages with the same frequencies)
          for FIFO purposes
        */
        ArrayList<Integer> indexesWithLowestFrequency = new ArrayList<Integer>();

        //storage of distances in case of FIFO purposes
        ArrayList<Integer> distance = new ArrayList<Integer>();

        /*Initially assigns a value to the currentPages array. Its values are assigned as -1 since the values of processes
          the user will enter are at least 0.
        */
        for(int j = 0; j < numberOfPages; j++){
            currentPages[j] = -1;
        }

        //Reiterates each value from the reference string
        for(int i = 0; i < reference.size(); i++){

            boolean isInsideThePages = false;

            //Resets each storage for each reference string value
            frequencyCurrentPages.clear();
            distance.clear();
            indexesWithLowestFrequency.clear();

            //Copies the elements of currentPages to prevPages array
            for (int j = 0; j < currentPages.length; j++) {
                prevPages[j] = currentPages[j];
            }

            /*Checks if the current value from the reference string is occupying the pages. If so, updates the boolean
              isInsideThePages to true.
            */
            for(int j = 0; j < numberOfPages; j++){
                if(currentPages[j] == reference.get(i)){
                    isInsideThePages = true;
                    break;
                }
            }

            /*
            Executes this conditional statement if the current reference string value (i) is currently not inside the
            current pages
             */
            if(!isInsideThePages){

                /*Executes when the currentPages array is full
                 */
                if(isFull){

                    /*Determines the number of frequency for each reference string value that is currently inside
                      the currentPages array. Each frequency will be added to the frequencyCurrentPages arraylist
                    */
                    for(int j = 0; j < currentPages.length; j++){
                        int frequency = 0;
                        for(int k = 0; k < i; k++){
                            if(currentPages[j] == reference.get(k)){
                                frequency++;
                            }
                        }
                        frequencyCurrentPages.add(frequency);
                    }

                    //Determines if all number of frequencies is the same. Do FIFO if so.
                    if(isAllTheSame(frequencyCurrentPages)){

                        /*Determines the distance of each reference string value from when it was added. Distances
                          will be added to the distance arraylist.
                        */
                        for(int j = 0; j < currentPages.length; j++) {
                            int dist = 0;
                            for (int d = i; d >= 0; d--) {
                                if(currentPages[j] == reference.get(d)){
                                    break;
                                }
                                else{
                                    dist++;
                                }
                            }
                            distance.add(dist);
                        }
                        pointer = distance.indexOf(Collections.max(distance));
                    }

                    //If there are variety of frequencies
                    else{
                        /*Determines the distance of each reference string value from when it was added. Distances
                          will be added to the distance arraylist.
                        */
                        for(int j = 0; j < currentPages.length; j++) {
                            int dist = 0;
                            for (int d = i; d >= 0; d--) {
                                if(currentPages[j] == reference.get(d)){
                                    break;
                                }
                                else{
                                    dist++;
                                }
                            }
                            distance.add(dist);
                        }

                        boolean hasSameFrequency = false;
                        ArrayList<Integer> sorted = new ArrayList<Integer>(frequencyCurrentPages);
                        Collections.sort(sorted);

                        //Determines if there are same frequencies within the frequencyCurrentPages array
                        for(int m = 1; m < frequencyCurrentPages.size(); m++){
                            if(Collections.min(frequencyCurrentPages) == sorted.get(m)){
                                hasSameFrequency = true;
                                break;
                            }
                        }

                        //do FIFO if there are values with the same frequency
                        int temp = Collections.max(distance);
                        if(hasSameFrequency){
                            for(int k = 0; k < frequencyCurrentPages.size(); k++) {

                                //Gets the indexes of the values with the lowest frequency
                                if(frequencyCurrentPages.get(k) == Collections.min(frequencyCurrentPages)){
                                    indexesWithLowestFrequency.add(k);
                                }
                            }
                            //Initially assigns the pointer to the first index with the lowest frequency
                            pointer = indexesWithLowestFrequency.get(0);
                            for (int p = 0; p < distance.size(); p++) {
                                for (int d = 0; d < indexesWithLowestFrequency.size(); d++){
                                    //Determines the index of the value that went inside the pages first
                                    if (p == indexesWithLowestFrequency.get(d)) {
                                        if(temp <= distance.get(p)){
                                            temp = distance.get(p);
                                            pointer = p;
                                        }
                                    }
                                }
                            }
                        }
                        //Assigns the index of the value with the lowest frequency to pointer
                        else {
                            pointer = frequencyCurrentPages.indexOf(Collections.min(frequencyCurrentPages));
                        }
                    }
                }
                //Sets the pointer index of currentPages with the current reference string value
                currentPages[pointer] = reference.get(i);
                pageFault++;
                pointer++;

                /*
                Checks if the current pages is already full based from the pointer variable as it reaches the total number
                of pages set by the user
                 */
                if(pointer == numberOfPages){
                    pointer = 0;
                    isFull = true;
                }
            }

            //Assigns elements to the table for Visualization of Pages
            for(int j = 0; j < numberOfPages; j++){

                if(Arrays.equals(currentPages, prevPages)){
                    table[i][j] = " ";
                }
                else {
                    if (currentPages[j] == -1) {
                        table[i][j] = " ";
                    } else {
                        table[i][j] = String.valueOf(currentPages[j]);
                    }
                }
            }
        }
        //Method calls the printPages to display the table of pages
        printPages();
    }

    //==========================================================================================//
    //Method for Most Frequently Used Algorithm
    public static void MFU(){

        //storage for frequencies of the values inside the current pages
        ArrayList<Integer> frequencyCurrentPages = new ArrayList<Integer>();

        //storage of distances in case of FIFO purposes
        ArrayList<Integer> distance = new ArrayList<Integer>();

        /*storage for the indexes with the highest frequency (in case of there are two or more pages with the same frequencies)
          for FIFO purposes
        */
        ArrayList<Integer> indexesWithHighestFrequency = new ArrayList<Integer>();

        /*Initially assigns a value to the currentPages array. Its values are assigned as -1 since the values of processes
          the user will enter are at least 0.
        */
        for(int j = 0; j < numberOfPages; j++){
            currentPages[j] = -1;
        }

        //Reiterates each value from the reference string
        for(int i = 0; i < reference.size(); i++){
            boolean isInsideThePages = false;

            //Resets each storage for each reference string value
            frequencyCurrentPages.clear();
            distance.clear();
            indexesWithHighestFrequency.clear();

            //Copies the elements of currentPages to prevPages array
            for (int j = 0; j < currentPages.length; j++) {
                prevPages[j] = currentPages[j];
            }

            /*Checks if the current value from the reference string is occupying the pages. If so, updates the boolean
              isInsideThePages to true.
            */
            for(int j = 0; j < numberOfPages; j++){
                if(currentPages[j] == reference.get(i)){
                    isInsideThePages = true;
                    break;
                }
            }

            /*
            Executes this conditional statement if the current reference string value (i) is currently not inside the
            current pages
             */
            if(!isInsideThePages){

                /*Executes when the currentPages array is full
                 */
                if(isFull){
                    for(int j = 0; j < currentPages.length; j++){
                        int frequency = 0;
                        for(int k = 0; k < i; k++){
                            if(currentPages[j] == reference.get(k)){
                                frequency++;
                            }
                        }
                        frequencyCurrentPages.add(frequency);
                    }

                    //Determines if all number of frequencies is the same. Do FIFO if so.
                    if(isAllTheSame(frequencyCurrentPages)){

                        /*Determines the distance of each reference string value from when it was added. Distances
                          will be added to the distance arraylist.
                        */
                        for(int j = 0; j < currentPages.length; j++) {
                            int dist = 0;
                            for (int d = 0; d < reference.size(); d++) {
                                if(currentPages[j] == reference.get(d)){
                                    dist = reference.indexOf(reference.get(i)) - d;
                                    break;
                                }
                            }
                            distance.add(dist);
                        }
                        pointer = distance.indexOf(Collections.max(distance));
                    }

                    //If there are variety of frequencies
                    else{
                        /*Determines the distance of each reference string value from when it was added. Distances
                          will be added to the distance arraylist.
                        */
                        for(int j = 0; j < currentPages.length; j++) {
                            int dist = 0;
                            for (int d = 0; d < reference.size(); d++) {
                                if(currentPages[j] == reference.get(d)){
                                    dist = reference.indexOf(reference.get(i)) - d;
                                    break;
                                }
                            }
                            distance.add(dist);
                        }

                        boolean hasSameFrequency = false;
                        ArrayList<Integer> sorted = new ArrayList<Integer>(frequencyCurrentPages);
                        Collections.sort(sorted, Collections.reverseOrder());

                        //Determines if there are same frequencies within the frequencyCurrentPages array
                        for(int m = 1; m < frequencyCurrentPages.size(); m++){
                            if(Collections.max(frequencyCurrentPages).equals(sorted.get(m))){
                                hasSameFrequency = true;
                                break;
                            }
                        }

                        //do FIFO if there are values with the same frequency
                        int temp = Collections.max(distance);
                        if(hasSameFrequency){
                            for(int k = 0; k < frequencyCurrentPages.size(); k++) {

                                //Gets the indexes of the values with the highest frequency
                                if(Objects.equals(frequencyCurrentPages.get(k), Collections.max(frequencyCurrentPages))){
                                    indexesWithHighestFrequency.add(k);
                                }
                            }
                            //Initially assigns the pointer to the first index with the highest frequency
                            pointer = indexesWithHighestFrequency.get(0);
                            for (int p = 0; p < distance.size(); p++) {
                                for (int d = 0; d < indexesWithHighestFrequency.size(); d++){
                                    //Determines the index of the value that went inside the pages first
                                    if (p == indexesWithHighestFrequency.get(d)) {
                                        if(temp > distance.get(p)){
                                            temp = distance.get(p);
                                            pointer = p;
                                        }
                                    }
                                }
                            }
                        }
                        //selects the index of the value with the highest frequency
                        else {
                            pointer = frequencyCurrentPages.indexOf(Collections.max(frequencyCurrentPages));
                        }
                    }
                }
                //Sets the pointer index of currentPages with the current reference string value
                currentPages[pointer] = reference.get(i);
                pageFault++;
                pointer++;

                /*Checks if the current pages is already full based from the pointer variable as it reaches the total number
                  of pages set by the user
                 */
                if(pointer == numberOfPages){
                    pointer = 0;
                    isFull = true;
                }
            }

            //Assigns elements to the table for Visualization of Pages
            for(int j = 0; j < numberOfPages; j++){

                if(Arrays.equals(currentPages, prevPages)){
                    table[i][j] = " ";
                }
                else {
                    if (currentPages[j] == -1) {
                        table[i][j] = " ";
                    } else {
                        table[i][j] = String.valueOf(currentPages[j]);
                    }
                }
            }
        }
        //Method calls the printPages to display the table of pages
        printPages();
    }

    //======================================== Non-Page Replacement Methods ========================================//
    //Method to display the table of pages
    public static void printPages(){

        for(int i = 0; i < reference.size(); i++){
            System.out.print(reference.get(i) + " |\t");
        }
        System.out.println();
        for (int i = 0; i < reference.size(); i++) {
            System.out.print("----");
        }
        System.out.println();

        for(int i = 0; i < numberOfPages; i++){
            for(int j = 0; j < reference.size(); j++){
                System.out.printf(table[j][i] + "\t");
            }
            System.out.println();
        }
        System.out.println("\nThe number of Faults: " + pageFault);
    }

    //Method for choosing another algorithm to simulate
    public static boolean tryAgain(){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.print("Do you want to try another algorithm? [Y] / [N]: ");
        String tryAgainOption = sc.next();
        return !tryAgainOption.equals("N") && !tryAgainOption.equals("n");
    }

    //Method to identify if all elements of an arraylist is the same
    public static boolean isAllTheSame(ArrayList<Integer> array){
        Integer first = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            if(!array.get(i).equals(first))
                return false;
        }
        return true;
    }
}
