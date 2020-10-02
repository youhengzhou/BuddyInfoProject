import java.util.*;
public class AddressBook {
    private ArrayList<BuddyInfo> buddyList;
    public AddressBook(){
        buddyList = new ArrayList<>();
    }

    public void addBuddy(BuddyInfo buddy){
        if(buddy != null) {
            this.buddyList.add(buddy);
        }
    }

    public void removeBuddy(int index){
        if(index >= 0 && index < buddyList.size()){
            this.buddyList.remove(index);
        }
    }

    public void removeLastBuddy(BuddyInfo buddy){
        if(buddy != null) {
            this.buddyList.remove(buddy);
        }
    }

    public static void main(String[] args){
        BuddyInfo buddy = new BuddyInfo("Tom", "carleton");
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(buddy);
        addressBook.removeBuddy(0);
    }
}
