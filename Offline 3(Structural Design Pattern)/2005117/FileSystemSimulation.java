import java.util.*;
import java.text.SimpleDateFormat;

interface Component {
    String getName();

    String getType();

    int getSize();

    String getDirectory();

    int getComponentCount();

    String getCreationTime();

    void displayDetails();
}

abstract class FileComponent implements Component{
    String name;
    String type;
    int size;
    String directory;
    int componentCount;
    String creationTime;

    public FileComponent(String name, String type, int size, String directory, int componentCount, String creationTime) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.directory = directory;
        this.componentCount = componentCount;
        this.creationTime = creationTime;
    }

    public void displayDetails() {
        System.out.println("Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Size: " + getSize() + " kB");
        System.out.println("Directory: \"" + getDirectory() + "\"");
        System.out.println("Component Count: " + getComponentCount());
        System.out.println("Creation Time: " + getCreationTime());
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public String getDirectory() {
        return directory;
    }

    public int getComponentCount() {
        return componentCount;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setSize(int size) {
        this.size = size;
    }
}


class File extends FileComponent {
    public File(String name, int size, String directory,String creationTime) {
        super(name, "File", size, directory, 0, creationTime);
    }
}
class Folder extends FileComponent {
    List<FileComponent> contents;
    Folder parentComponent;

    public Folder(String name, String type,String directory, String creationTime) {
        super(name, type, 0, directory, 0, creationTime);
        contents = new ArrayList<>();
    }

    public void addComponent(FileComponent component) {
        contents.add(component);
        this.setSize(component.getSize());
        componentCount++;
    }

    public void removeComponent(FileComponent component) {
        contents.remove(component);
        this.setSize((-1)*component.getSize());
        componentCount--;
    }

    public List<FileComponent> getContents() {
        return contents;
    }

    public Folder getParentComponent() {
        return parentComponent;
    }

    public void setParentComponent(Folder parentComponent) {
        this.parentComponent = parentComponent;
    }
    @Override
    public void setSize(int size){
        this.size = this.getSize()+size;
        if (this.getParentComponent() != null) {
            this.getParentComponent().setSize(size);
        }
    }
}



class Drive extends Folder {
    public Drive(String name,String creationTime) {
        super(name, "Drive",name + ":\\",creationTime);
    }
}

public class FileSystemSimulation {
    static Folder root;
    static Folder currentDirectory;

    public static void main(String[] args) {
        root = new Folder("Root", "Folder","",getTime());
        currentDirectory = root;

        Scanner scanner = new Scanner(System.in);
        String command;
        boolean running = true;

        while(running) {
            System.out.print("> ");
            command = scanner.nextLine();
            String[] splitCommand = command.split(" ");
            String action = splitCommand[0];

            switch (action) {
                case "mkdrive":
                    if(splitCommand.length==2){
                        createDrive(splitCommand[1]);
                    } else{
                        System.out.println("Invalid command!");
                    }
                    break;
                case "cd":
                    if(splitCommand.length==2){
                        if (splitCommand[1].endsWith(":\\")) {
                            splitCommand[1] = splitCommand[1].substring(0, splitCommand[1].length() - 2);
                        }
                        changeDirectory(splitCommand[1]);
                    } else{
                        System.out.println("Invalid command!");
                    }
                    break;
                case "ls":
                    if(splitCommand.length==2){
                        listDetails(splitCommand[1]);
                    } else{
                        System.out.println("Invalid command!");
                    }
                    break;
                case "list":
                    if(splitCommand.length==1){
                        listContents();
                    } else{
                        System.out.println("Invalid command!");
                    }
                    break;
                case "delete":
                    if(splitCommand.length>=2 && splitCommand.length<=3){
                        if (splitCommand.length > 2 && splitCommand[1].equals("-r")) {
                            recursiveDelete(currentDirectory,splitCommand[2]);
                        } else {
                            delete(splitCommand[1]);
                        }
                    } else{
                        System.out.println("Invalid command!");
                    }
                    break;
                case "mkdir":
                    if(splitCommand.length==2){
                        makeDirectory(splitCommand[1]);
                    } else{
                        System.out.println("Invalid command!");
                    }
                    break;
                case "touch":
                    if(splitCommand.length==3){
                        createFile(splitCommand[1], Integer.parseInt(splitCommand[2]));
                    } else{
                        System.out.println("Invalid command!");
                    }
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }

    static void createDrive(String name) {
        if(currentDirectory!=root){
            System.out.println("Error: Drive can not be created here!");
            return;
        }
        Drive drive = new Drive(name,getTime());
        drive.setParentComponent(root);
        root.addComponent(drive);
        System.out.println("Drive " + name + " created.");
    }

    static void changeDirectory(String name) {
        if (name.equals("~")) {
            currentDirectory = root;
            System.out.println("Directory changed, now in root folder.");
            return;
        }

        List<FileComponent> contents = currentDirectory.getContents();
        boolean found = false;

        for (FileComponent component : contents) {
            if (component.getName().equals(name))  {
                if(component.getType().equals("File")){
                    System.out.println("Error : Given name is a file!");
                    return;
                }
                currentDirectory = (Folder) component;
                System.out.println("Directory changed,now in "+currentDirectory.getDirectory());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Folder or drive not found.");
        }
    }

    static void listDetails(String name) {
        List<FileComponent> contents = currentDirectory.getContents();
        boolean found = false;

        for (FileComponent component : contents) {
            if (component.getName().equals(name)) {
                component.displayDetails();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("File, folder, or drive not found.");
        }
    }

    static void listContents() {
        List<FileComponent> contents = currentDirectory.getContents();
        for (FileComponent component : contents) {
            System.out.println(component.getName()+"    "+component.getSize()+"kB   "+component.getCreationTime());
        }
    }

    static void delete(String name) {
        List<FileComponent> contents = currentDirectory.getContents();
        boolean found = false;

        for (FileComponent component : contents) {
            if (component.getName().equals(name)) {
                if (component.getType().equals("File")) {
                    currentDirectory.removeComponent(component);
                    System.out.println("File " + name + " deleted.");
                } else if (component.getType().equals("Folder") && component.getComponentCount() == 0) {
                    currentDirectory.removeComponent(component);
                    System.out.println("Folder " + name + " deleted.");
                }else if (component.getType().equals("Drive") && component.getComponentCount() == 0) {
                    currentDirectory.removeComponent(component);
                    System.out.println("Drive " + name + " deleted.");
                } else {
                    System.out.println("Failed! " + name + " is not empty.");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("File or folder or drive not found.");
        }
    }

    static void recursiveDelete(Folder inDirectory,String name) {
        List<FileComponent> contents = inDirectory.getContents();
        boolean found = false;

        for (FileComponent component : contents) {
            if (component.getName().equals(name)) {
                if (component.getType().equals("File")) {
                    inDirectory.removeComponent(component);
                    System.out.println("File " + name + " deleted.");
                } else if (component.getType().equals("Folder")) {
                    Folder folder = (Folder) component;
                    if (folder.getComponentCount() == 0) {
                        inDirectory.removeComponent(component);
                        System.out.println("Folder " + name + " deleted.");
                    } else {
                        List<FileComponent> copiedContents = new ArrayList<>(folder.getContents());
                        for (FileComponent child : copiedContents) {
                            recursiveDelete(folder,child.getName());
                        }
                        inDirectory.removeComponent(component);
                        System.out.println("Folder " + name + " and its contents recursively deleted.");
                    }
                }
                else if (component.getType().equals("Drive")) {
                    Folder drive = (Folder) component;
                    if (drive.getComponentCount() == 0) {
                        inDirectory.removeComponent(component);
                        System.out.println("Drive " + name + " deleted.");
                    } else {
                        List<FileComponent> copiedContents = new ArrayList<>(drive.getContents());
                        for (FileComponent child :copiedContents ) {
                            recursiveDelete(drive,child.getName());
                        }
                        inDirectory.removeComponent(component);
                        System.out.println("Drive " + name + " and its contents recursively deleted.");
                    }
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("File or folder not found.");
        }
    }

    static void makeDirectory(String name) {
        if(currentDirectory==root){
            System.out.println("Folder creation under root is not allowed!");
            return;
        }
        Folder folder = new Folder(name, "Folder",currentDirectory.directory + name + "\\",getTime());
        folder.setParentComponent(currentDirectory);
        currentDirectory.addComponent(folder);
        System.out.println("Folder " + name + " created.");
    }

    static void createFile(String fileName, int size) {
            if(currentDirectory==root){
                System.out.println("File creation under root is not allowed!");
                return;
            }
            File file = new File(fileName, size, currentDirectory.directory,getTime());
            currentDirectory.addComponent(file);
            System.out.println("File " + fileName + " created.");
    }

    static String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
