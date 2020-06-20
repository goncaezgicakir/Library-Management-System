import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class LibrarySystem {
    private static ArrayList<Student> students;
    private static ArrayList<Librarian> librarians;
    private static LMSPriorityQueue<Table> tables;

    public LibrarySystem(){
        tables = new LMSPriorityQueue<Table>(10, null);
        initializeTableData(tables);
    }

    static {
        students = new ArrayList<>();
        librarians = new ArrayList<>();
    }

    private void initializeTableData(LMSPriorityQueue<Table> tables){


        tables.offer(new Table("Table1", "available", 10));
        tables.offer(new Table("Table2", "available", 60));
        tables.offer(new Table("Table3", "available", 30));
        tables.offer(new Table("Table4", "available", 50));
        for(int i=4; i<tables.getCapacity()  ; i++){
            String tableID = "Table" + (i+1);
            //Initializing each table with -> TableX, Available, Time(now)
            //puts every object to priority queue
            tables.offer(new Table(tableID, "available", 0));
        }



        for(int i=0; i<tables.getCapacity() ; i++){
            Table t =  tables.poll();
            System.out.println("ID: " +t.getID() + "  Status: " + t.getStatus() + "  Time: " + t.getTime() );

        }
    }

    protected static void acceptRejectTableReservation(Student student, String tableID){
        for(int i=0; i<tables.getTheData().size(); i++){

            Table t= tables.getTheData().get(i);

            //table found and its status is available
            if(t.getID().equals(tableID)){
                if(t.getStatus().equals("available")) {
                    student.setMyTable(tableID); //tableID assigned to student
                    student.setTableCounter(student.getTableCounter() + 1); //table counter incremented
                    t.setStatus("reserved");
                    t.setTime(60); //60 minutes
                    System.out.println(tableID + " is reserved for " + student.getName() + " " + student.getSurname() + " for 1 hour.");
                    return;
                }

                else{
                    System.out.println("Reservation denied by system.\nGiven " +  tableID + " is already occupied.");
                    return;
                }
            }
        }

        //Table ID not valid
        System.out.println("Reservation denied by system.\nGiven " + tableID + " is not exist in library.");

    }


    protected static void approveTableTimeExtend(Student student){

        String tableID = student.getMyTable();

        for(int i=0; i<tables.getTheData().size(); i++){

            Table t= tables.getTheData().get(i);

            //table found
            if(t.getID().equals(tableID)){
                t.setTime(t.getTime() + 60); //time extended for another 60 minutes
                System.out.println(tableID + "'s time extended 1 hour for student " + student.getName() + " " + student.getSurname() + " (Remaining time: " + t.getTime() + ")");
                return;
            }
        }

        System.out.println("Table time extension error.");

    }

    //-MENU---------------------------------------------------------------------------
    public void mainMenu(){
        Scanner sb = new Scanner(System.in);
        System.out.println("1) Sign Up\n2) Log In\n3) Exit\n");
        int type = sb.nextInt();

        switch (type){
            case 1 :
                signUp();
                mainMenu();
                break;
            case 2:
                System.out.println("1) Student\n2) Librarian\n3) Exit\n");
                type = sb.nextInt();
                switch (type){
                    case 1:
                        Student student = null;
                        studentMenu("student",student);
                        break;
                    case 2:
                        Librarian librarian = null;
                        librarianMenu("librarian",librarian);
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Wrong Entry");
                        mainMenu();
                        break;
                }
            case 3:
                break;
            default:
                System.out.println("Wrong Entry");
                mainMenu();
                break;
        }
    }
    private void studentMenu(String type,Student student){
        if(student == null){
            student = (Student) logIn(type);
            if (student == null) {
                System.out.println("Log in process is failed.");
                mainMenu();
                return;
            }
        }
        System.out.println("\nStudent Menu\n");
        System.out.println("1) Edit Profile\n2) Book Option\n3) Table Option\n4) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                profileOption((User) student);
                studentMenu(type,student);
                break;
            case 2:
                studentBookOption(student);
                break;
            case 3:
                studentTableOption(student);
                break;
            case 4:
                break;
            default:
                System.out.println("Wrong Entry\n");
                studentMenu(type,student);

        }
    }
    public void profileOption(User user){
        System.out.println("Profile Information\n");
        System.out.println("Name:      "+user.getName());
        System.out.println("Surname:   "+user.getSurname());
        System.out.println("ID:        "+user.getID());
        System.out.println("Password:  "+user.getPassword());
        System.out.println("\n1) Edit Name\n2) Edit Surname\n3) Edit Password\n4) Exit");

        Scanner sb = new Scanner(System.in);
        String change;
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                System.out.println(" Enter the new name please ");
                change = sb.next();
                user.setName(change);
                profileOption(user);
                break;
            case 2:
                System.out.println(" Enter the new surname please ");
                change = sb.next();
                user.setSurname(change);
                profileOption(user);
                break;
            case 3:
                System.out.println(" Enter the new password please ");
                change = sb.next();
                user.setPassword(change);
                profileOption(user);
                break;
            case 4:
                break;
            default:
                System.out.println("Wrong choice. Please enter a new choice\n");
                profileOption(user);
                break;
        }
    }
    private void studentBookOption(Student user){
        System.out.println("\nBook Option Menu\n");
        System.out.println("1) Search Book\n2) Request Book\n3) Extend Time Book\n4) Check Book Time\n5) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                user.searchBook(null); //CHANGE PARAMETER!!!!!
                studentBookOption(user);
                break;
            case 2:
                user.requestBook(null); //CHANGE PARAMETER !!!!!!
                studentBookOption(user);
                break;
            case 3:
                user.extendTimeBook();
                studentBookOption(user);
                break;
            case 4:
                user.checkBookTime();
                studentBookOption(user);
                break;
            case 5:
                studentMenu("student",user);
                break;
            default:
                System.out.println("Wrong Entry\n");
                studentBookOption(user);
                break;
        }
    }
    private void studentTableOption(Student user){
        System.out.println("\nTable Option Menu\n");
        System.out.println("1) Show Table\n"+"2) Reserve Table\n" +
                "3) Leave Table\n4) Extend Time Table\n5) Check Table Time\n6) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();
        String tableId;


        switch (choice){
            case 1:
                user.showTable();
                studentTableOption(user);
                break;
            case 2:
                System.out.println(" Enter tableID to reserve.");
                tableId = sb.next();
                user.reserveTable(tableId);
                studentTableOption(user);
                break;
            case 3:
                user.leaveTable();
                studentTableOption(user);
                break;
            case 4:
                user.extendTimeTable();
                studentTableOption(user);
                break;
            case 5:
                user.checkTableTime();
                studentTableOption(user);
            case 6:
                studentMenu("student",user);
                break;
            default:
                System.out.println("Wrong Entry");
                studentTableOption(user);
                break;
        }
    }
    private void librarianMenu(String type,Librarian librarian){
        if(librarian == null){
            librarian = (Librarian) logIn(type);
            if (librarian == null) {
                System.out.println("Log in process is failed.");
                mainMenu();
                return;
            }
        }
        System.out.println("\nLibrarian Menu\n");
        System.out.println("1) Open your profile\n2) Book Option\n3) Table Option\n4) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                profileOption((User) librarian);
                librarianMenu("librarian",librarian);
                break;
            case 2:
                librarianBookOption(librarian);
                break;
            case 3:
                librarianTableOption(librarian);
                break;
            case 4:
                break;
            default:
                System.out.println("Wrong Entry");
                librarianMenu(type,librarian);
                break;
        }

    }
    private void librarianBookOption(Librarian user){
        System.out.println("\nBook Option Menu\n");
        System.out.println("1) Search Book\n2) Add Book\n3) Remove Book\n4) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                user.searchBook();
                librarianBookOption(user);
                break;
            case 2:
                user.addBook();
                librarianBookOption(user);
                break;
            case 3:
                user.removeBook();
                librarianBookOption(user);
                break;
            case 4:
                librarianMenu("librarian",user);
                break;
            default:
                System.out.println("Wrong Entry");
                librarianBookOption(user);
                break;
        }
    }
    private void librarianTableOption(Librarian user){
        System.out.println("\nTable Option Menu\n");
        System.out.println("1) Show Table\n"+"2) Cancel Table Reservation\n3) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                user.showTable();
                librarianTableOption(user);
                break;
            case 2:
                user.cancelTableReservation();
                librarianTableOption(user);
            case 3:
                librarianMenu("librarian",user);
                break;
            default:
                System.out.println("Wrong Entry\n");
                break;
        }
    }


    //-----------------------------------------------------------------


    public void signUp(){
        int type;
        Scanner sb = new Scanner(System.in);
        System.out.println("1) Student\n2) Librarian\n3) Exit\n");
        type = sb.nextInt();
        switch (type){
            case 1:
                User newStudent = createUser(type);
                if(newStudent == null){
                    signUp();
                }else{
                    students.add((Student) newStudent);
                }
                mainMenu();
                break;
            case 2:
                User newLibrarian = createUser(type);
                if(newLibrarian == null){
                    signUp();
                }else{
                    librarians.add((Librarian) newLibrarian);
                }
                mainMenu();
                break;
            case 3:
                mainMenu();
                break;
            default:
                System.out.println("Wrong entry");
                signUp();
                break;
        }
    }

    private User createUser(int type){
        String name;
        String surname;
        String ID;
        String password;
        Scanner sb = new Scanner(System.in);
        System.out.println("In case you want to leave menu, please write 'exit'.\n");

        System.out.println(" Enter the name please ");
        name = sb.next();
        if (name.equals("exit")){
            return null;
        }

        System.out.println(" Enter the surname please ");
        surname = sb.next();
        if (surname.equals("exit")){
            return null;
        }

        System.out.println(" Enter the ID please");
        ID = sb.next();
        if (ID.equals("exit")){
            return null;
        }

        System.out.println(" Enter the password please");
        password = sb.next();
        if (password.equals("exit")){
            return null;
        }

        if(type == 1) {
            return (User) new Student(name, surname, ID, password); //EXCHANGED PARAMETERS ID AND PASSWORD
        }else{
            return (User) new Librarian(name, surname, ID, password); //EXCHANGED PARAMETERS ID AND PASSWORD
        }
    }

    public User logIn(String type){
        User temp = IDControl(type,0);
        if (temp == null){
            return null;
        }else{
            boolean password = passwordControl(temp,0);

            if (password){
                return temp;
            }else{
                return null;
            }
        }
    }

    private User IDControl(String type,int i){
        if(i == 4){
            return null;
        }

        Scanner sb = new Scanner(System.in);
        System.out.println(" Entry the ID please ");
        String ID = sb.next();
        int index = 0;

        if (type.equals("student")){

            while (students.size()>index){
                if (students.get(index).getID().equals(ID)){
                    return  students.get(index);
                }
                index++;
            }

        }else{
            while (librarians.size()>index){
                if (librarians.get(index).getID().equals(ID)){
                    return  librarians.get(index);
                }
                index++;
            }
        }
        System.out.println("Wrong ID. Your remaining ID retry: " +(4-i-1));
        return IDControl(type,i+1);
    }

    private boolean passwordControl(User temp,int i){
        Scanner sb = new Scanner(System.in);
        System.out.println(" Enter password please ");
        String password = sb.next();

        if (i == 4){
            return false;
        }else{
            if (temp.getPassword().equals(password)){
                return true;
            }else{
                System.out.println(" Wrong password. Your remaining password retry:  " +(4-i-1));
                return passwordControl(temp,i+1);
            }
        }
    }

}
