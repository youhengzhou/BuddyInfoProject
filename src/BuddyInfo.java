public class BuddyInfo {
    private String name;
    private String school;
    public BuddyInfo(String name, String school){
        this.name = name;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        BuddyInfo buddy = new BuddyInfo("Henry", "Carleton");
        System.out.print("Hello ");
        System.out.println(buddy.getName());
    }
}
