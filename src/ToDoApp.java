import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoApp {
    public static void main(String[] args) {

        ArrayList<String> taskArr = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        String file = "c:/temp/to_do.txt";
        String logFile = "c:/temp/to_do_log.txt";
        int userImput = 0;
        boolean logChanges = false;

        readFileToArr(file, taskArr);

        while(true) {

            addTaskCheckBox(taskArr);
            printArr(taskArr);
            System.out.println("[1] = Change task status.");
            System.out.println("[2] = Change tasks.");
            System.out.println("[3] = Add new task.");
            System.out.println("[4] = Remove tasks.");
            System.out.println("[5] = Save tasks.");
            System.out.println("[E] = Close program.");
            System.out.println(" ");

            userImput = getUserImputInt(in);

            if (userImput == -1) {
                System.out.println("Program closed.");
                break;
            }
            else 

            switch (userImput) {
                case 1: {
                    printArr(taskArr);
                    changeTaskStatus(taskArr);
                    break;
                }
                case 2: {
                    printArr(taskArr);
                    changeTask(taskArr);
                    break;
                }
                case 3: {
                    addNewTask(taskArr);
                    break;
                }
                case 4: {
                    removeTask(taskArr);
                    break;
                }
                case 5: {
                    writeArrToFile(file, logFile, taskArr, logChanges);
                    break;
                }
            }

        }
        in.close();

    }//main ends

    public static void printArr (ArrayList<String> arrayList) {
        System.out.println("To-Do lista: \n");

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println("Tehtävä "+(i+1)+":| "+arrayList.get(i));
        }
        for (int i = 0; i < 11; i++){
            System.out.print("*");
        }
        for (int i = 0; i < 61; i++) {
            System.out.print("-");
        }
        System.out.println(" ");
        
    }// Print ends

    public static int getUserImputInt (Scanner userImput) {
        while(true) {
            String test = userImput.nextLine();
            if (test.toLowerCase().equals("e")) {
                return -1;
            }
            try {        
                int testInt = Integer.parseInt(test);
                if (testInt == 0 ) {
                    System.out.println("0 is invalid number!\n");
                }
                else {
                return testInt;
                }
            } catch (Exception e) {
                System.out.println("Input numbers only!\n");
            }
        }
    }//getUserImputInt ends

    public static int indexBoundsCheck(int userInput,int maxIndex) {
        Scanner in = new Scanner(System.in);
        int test = userInput;
        int indexBorder = maxIndex;

        while (true) {
            if (test > indexBorder) {
                System.out.println("Task number invalid. Please give number between 1-"+(indexBorder)+".");
                test = getUserImputInt(in);
            } else {
                break;
            }
        }
        return test -1;
    }

    public static ArrayList<String> readFileToArr (String file, ArrayList<String> arr) {
         try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                arr.add(line);
            }
            reader.close();
        }catch (IOException e) {
            System.out.println("Error");
        }
        return arr;
    }//readFileToArr ends


    public static void  writeArrToFile (String file,String logFile, ArrayList<String> arr, boolean log) {
        try {
            FileWriter writer = new FileWriter(file);
            FileWriter logWriter = new FileWriter (logFile,true);
            for (int i = 0; i < arr.size(); i++ ) {
            String writeToFile = arr.get(i);
            writer.write(writeToFile+"\n");
                if (log == true) {
                    logWriter.write(writeToFile+"\n");
                }
            }
            writer.close();
            logWriter.close();

        }catch(IOException e) {
            System.out.println("Error when writing to file");
        }
        System.out.println("Tasklist saved.");
    }// writeArrToFile ends

    public static void changeTask(ArrayList<String> arr) {

        Scanner in = new Scanner(System.in);
        int userImput;
        String taskChange = "";
        int taskIndex;

        while (true) {
            System.out.println("Choose task index you want to change. Press e to get back in menu.");
            userImput = getUserImputInt(in);
            if (userImput == -1) {
                System.out.println("\nReturned to menu.\n");
                break;
            }
            taskIndex = indexBoundsCheck(userImput, arr.size());


            System.out.println("Insert new task and press enter.");
            taskChange = in.nextLine();
            arr.set((taskIndex),taskChange);
 
            printArr(arr);
        }
    }//changeTask ends

    public static void changeTaskStatus (ArrayList<String> arr) {

        Scanner in = new Scanner(System.in);
        int userImput;
        int taskIndex;
        String originalTask;
        
        while (true) {
            System.out.println("Choose task which status you want to update. Press e to get back in menu.");
            userImput = getUserImputInt(in);
            if (userImput == -1) {
                break;
            }
            taskIndex = indexBoundsCheck(userImput, arr.size());
            originalTask = arr.get(taskIndex).substring(0, arr.get(taskIndex).length()-4);

            if (arr.get(taskIndex).contains(" [ ]")) {
                arr.set(taskIndex, originalTask+" [x]");
            }
            else {
                arr.set(taskIndex, originalTask+" [ ]");
            }
            printArr(arr);
        } 
    }//changeTaskStatus ends

    public static ArrayList<String> addNewTask(ArrayList<String> arr) {

        Scanner in = new Scanner(System.in);
        while (true){
            System.out.println("Add task. Press e to exit");
            String userImput = in.nextLine();
            if (userImput.toLowerCase().equals("e")) {
                break;
            }
            arr.add(userImput);
            addTaskCheckBox(arr);
            printArr(arr);
        }
        return(arr);
    }//AddNewTasa ends

    public static void addTaskCheckBox(ArrayList<String> arr) {

        for (int i = 0; i < arr.size(); i++) {

            if (!arr.get(i).contains("[ ]") && !arr.get(i).contains(" [x]")) {
                arr.set(i,arr.get(i)+" [ ]");
            }
        }
    }//addTaskCheckBox ends

    public static ArrayList<String> removeTask(ArrayList<String> arr) {
        Scanner in = new Scanner(System.in);
        int userImput;
        int taskIndex;
        while (true) {
            System.out.println("Input task number you want to remove. Press e to return to menu.");
            userImput = getUserImputInt(in);
            if (userImput == -1) {
                break;
            }
            else {
                taskIndex = indexBoundsCheck(userImput, arr.size());
                arr.remove(taskIndex);
                printArr(arr);
            }
        } 
        return arr;
    }//removeTask ends

}//Class ends
