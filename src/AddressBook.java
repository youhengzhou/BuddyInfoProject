import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AddressBook extends DefaultHandler implements Serializable{
    private ArrayList<BuddyInfo> buddyList;
    public AddressBook(){
        buddyList = new ArrayList<>();
    }

    boolean name = false, school = false;
    String newName;
    String newSchool;
    BuddyInfo buddy;

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

    public int size(){
        return buddyList.size();
    }

    public BuddyInfo get(int x){
        return buddyList.get(x);
    }

    public static AddressBook readObject(String file){
        try{
            ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(file));
            AddressBook book = (AddressBook)oStream.readObject();
            oStream.close();
            return book;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void writeObject(String file){
        try{
            ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(file));
            oStream.writeObject(this);
            oStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void load(String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null){
                BuddyInfo buddy = BuddyInfo.create(line);
                if(buddy != null)
                    buddyList.add(buddy);
                line = br.readLine();
            }
            br.close();
        } catch(FileNotFoundException e){
            System.out.println("The given file was not found: " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(String path) throws IOException{
        StringBuffer buff = new StringBuffer("");
        for(int i = 0; i < buddyList.size(); i++){
            buff.append(buddyList.get(i).toString() + "\n");
        }
        BufferedWriter stream = new BufferedWriter(new FileWriter(path));
        stream.write(buff.toString());
        stream.close();
    }

    public void exportToXMLFile(String path) throws IOException{
        StringBuffer buff = new StringBuffer("<book>");
        for(int i = 0; i < buddyList.size(); i++){
            buff.append(buddyList.get(i).toXMLString());
        }
        buff.append("</book>");
        BufferedWriter stream = new BufferedWriter(new FileWriter(path));
        stream.write(buff.toString());
        stream.close();
    }

    public void importFromXMLFile(String path) throws IOException, ParserConfigurationException, SAXException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new FileInputStream(path), this);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String u, String ln, String qName, Attributes a) throws SAXException {
        if (qName.equals("BuddyInfo")) {
        }
        if (qName.equalsIgnoreCase("name")) {
            name = true;
        }
        if (qName.equalsIgnoreCase("school")) {
            school = true;
        }
    }

    @Override
    public void endElement(String url, String localName, String qName) throws SAXException{
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (name) {
            newName = new String(ch, start, length);
        }
        if (school) {
            newSchool = new String(ch, start, length);
        }
        if (name && school) {
            System.out.println(name + " " + school);
            buddy = new BuddyInfo(newName, newSchool);
            buddyList.add(buddy);
            name = false;
        }
    }

    /*public void importFromXMLFile(String path)throws FileNotFoundException, SAXException, IOException, ParserConfigurationException{
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(path));
        NodeList nodeList = doc.getElementsByTagName("student");
        for(int i = 0; i < nodeList.getLength(); i++){
            buddyList.add(BuddyInfo.create((Element)nodeList.item(i)));
        }
    }*/

    public static void main(String[] args){
        BuddyInfo buddy = new BuddyInfo("Tom", "Carleton");
        BuddyInfo buddy2 = new BuddyInfo("Jim", "Carleton");

        BuddyInfo buddy3 = buddy.create("Pam#Carleton");

        AddressBook addressBook = new AddressBook();
        AddressBook addressBook2 = new AddressBook();
        AddressBook addressBook3 = new AddressBook();
        addressBook.addBuddy(buddy);
        addressBook.addBuddy(buddy2);
        addressBook.addBuddy(buddy3);
        addressBook.removeBuddy(0);

        try {
            addressBook.save("addressText.txt");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        addressBook.writeObject("addressSerialized.txt");

        try {
            addressBook.exportToXMLFile("address.xml");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            addressBook2.importFromXMLFile("address.xml");
        } catch (SAXException | IOException | ParserConfigurationException e1) {
            e1.printStackTrace();
        }

        try {
            addressBook2.exportToXMLFile("address2.xml");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
