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
        String userImput = "";
        boolean logChanges = false;

        ReadFileToArr(file, taskArr);

        while(true) {

            AddTaskCheckBox(taskArr);
            PrintArr(taskArr);
            System.out.println("[1] = Change task status.");
            System.out.println("[2] = Change task.");
            System.out.println("[3] = Add new task.");
            System.out.println("[4] = Save tasks.");
            System.out.println("[E] = Close program.");

            userImput = in.nextLine();
            System.out.println(" ");
            CheckUserImputInt(userImput);

            switch (userImput) {
                case "1": {
                    PrintArr(taskArr);
                    ChangeTaskStatus(taskArr);
                    break;
                }
                case "2": {
                    PrintArr(taskArr);
                    ChangeTask(taskArr);
                    break;
                }
                case "3": {
                    AddNewTask(taskArr);
                    break;
                }
                case "4": {
                    WriteArrToFile(file, logFile, taskArr, logChanges);
                    break;
                }
            }
            if (userImput.toLowerCase().equals("e")) {
                System.out.println("Program closed.");
                break;
            }

        }
        in.close();

    }//main ends

    public static void PrintArr (ArrayList<String> arrayList) {
        System.out.println("To-Do lista: \n");

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println("Tehtävä "+(i+1)+": "+arrayList.get(i));
        }
    }// Print ends

    public static void CheckUserImputInt (String userImput) {
        while(true) {
            try {
                if (userImput.toLowerCase().equals("e")) {
                    break;
                }               
                int testi = Integer.parseInt(userImput);
                if (testi == 0 ) {
                    System.out.println("0 is invalid number!\n");
                    break;
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Input numbers only!\n");
                break;
            }
        }
    }

    public static ArrayList<String> ReadFileToArr (String file, ArrayList<String> arr) {
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
    }//ReadFileToArr ends


    public static void  WriteArrToFile (String file,String logFile, ArrayList<String> arr, boolean log) {
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
    }// WriteArrToFile ends

    public static void ChangeTask(ArrayList<String> arr) {

        Scanner in = new Scanner(System.in);
        String userImput = "";
        String taskChange = "";
        String taskIndex = "";
        int taskIndexToInt;

        while (true) {
            System.out.println("Choose task index you want to change. Press e to get back in menu.");
            userImput = in.nextLine();
            CheckUserImputInt(userImput);

            if (userImput.toLowerCase().equals("e")) {
                System.out.println("Returned to menu\n");
                break;
            }

            taskIndex = userImput;
            taskIndexToInt = Integer.parseInt(taskIndex);

            System.out.println("Insert new task and press enter.");
            userImput = in.nextLine();
            taskChange = userImput;

            arr.set((taskIndexToInt - 1),taskChange);
        }
    }//ChangeTask ends

    public static void ChangeTaskStatus (ArrayList<String> arr) {

        Scanner in = new Scanner(System.in);
        String taskIndex = "";
        int taskIndexToInt;
        String originalTask;
        
        while (true) {
            System.out.println("Choose task which status you want to update. Press e to get back in menu.");

            taskIndex = in.nextLine();
            CheckUserImputInt(taskIndex);

            if (taskIndex.toLowerCase().equals("e")) {
                break;
            }

            taskIndexToInt = Integer.parseInt(taskIndex) -1;
            originalTask = arr.get(taskIndexToInt).substring(0, arr.get(taskIndexToInt).length()-4);

            if (arr.get(taskIndexToInt).contains(" [ ]")) {
                arr.set(taskIndexToInt, originalTask+" [x]");
            }
            else {
                arr.set(taskIndexToInt, originalTask+" [ ]");
            }
            PrintArr(arr);
        } 
    }//ChangeTaskStatus ends

    public static void AddNewTask(ArrayList<String> arr) {

        Scanner in = new Scanner(System.in);
        while (true){
            System.out.println("Add task. Press e to exit");
            String userImput = in.nextLine();
            if (userImput.toLowerCase().equals("e")) {
                break;
            }
            arr.add(userImput);
        }
    }//AddNewTask ends

    public static void AddTaskCheckBox(ArrayList<String> arr) {

        for (int i = 0; i < arr.size(); i++) {

            if (!arr.get(i).contains("[ ]") && !arr.get(i).contains(" [x]")) {
                arr.set(i,arr.get(i)+" [ ]");
            }
        }
    }//AddTaskCheckBox ends


}//Class ends
