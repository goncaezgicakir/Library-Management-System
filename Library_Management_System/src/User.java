public abstract class User implements UserInterface {

    private String name;
    private String surname;
    private String ID;
    private String password;

    public User(String name, String surname, String ID, String password) {
        this.name = name;
        this.surname = surname;
        this.ID = ID;
        this.password = password;
    }

    @Override
    public void showBook() {
        //book data library sistemde
        //LibrarySystem.showBook();
        //kitaplar basıldı
    }
    @Override
    public void showTable() {
        //table data library sistemde
        //LibrarySystem.showTable();
        //masalar basıldı
    }
    @Override
    public void searchBook(String book_name) {
        //book data library sistemde
        //LibrarySystem.searchBook(book_name);
        //kitap basıldı yoksa err basıldı
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
