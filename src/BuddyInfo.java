import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.StringTokenizer;
import java.io.Serializable;

public class BuddyInfo implements Serializable {

    private static final long serialVersionUID = 1196656838076753133L;

    private static final String DELIMETER = "#";
    private String name;
    private String school;

    public BuddyInfo(String name, String school){
        this.name = name;
        this.school = school;
    }

    public BuddyInfo(BuddyInfo p){
        this.name = new String(p.getName());
        this.school = new String(p.getSchool());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String toString(){
        return this.name + DELIMETER + this.school;
    }

    public static BuddyInfo create(Element node){
        BuddyInfo info = new BuddyInfo("", "");
        NodeList list;

        list = node.getElementsByTagName("name");
        if(list.getLength() > 0){
            info.setName(list.item(0).getTextContent());
        }

        list = node.getElementsByTagName("school");
        if(list.getLength() > 0){
            info.setSchool(list.item(0).getTextContent());
        }
        return info;
    }

    public static BuddyInfo create(String serial){
        BuddyInfo buddy = null;
        StringTokenizer st = new StringTokenizer(serial, DELIMETER);
        //Scanner scanner = new Scanner(serial);
        //scanner.useDelimiter(DELIMETER);
        buddy = new BuddyInfo(st.nextToken(), st.nextToken());
        return buddy;
    }

    public String toXMLString(){
        return "<student>" + "<name>" + this.name + "</name>" + "<school>" + this.school + "</school>" + "</student>";
    }

    public static void main(String[] args) {
        BuddyInfo buddy = new BuddyInfo("Henry", "Carleton");
        System.out.print("Hello ");
        System.out.println(buddy.getName());
    }
}
