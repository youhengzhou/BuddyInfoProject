import java.util.*;
public class AddressBook {
    private List<BuddyInfo> buddyList;
    public AddressBook(){
        buddyList = new ArrayList<>();
    }

    public void addBuddy(BuddyInfo buddy){
        this.buddyList.add(buddy);
    }

    public void removeBuddy(BuddyInfo buddy){
        this.buddyList.remove(buddy);
    }

    public static void main(String[] args){
        BuddyInfo buddy = new BuddyInfo("Tom", "carleton");
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(buddy);
        addressBook.removeBuddy(buddy);
    }
}
