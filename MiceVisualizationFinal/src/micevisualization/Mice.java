/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package micevisualization;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author parker
 */
public class Mice {
    ArrayList<Mouse> mice;
    
    public Mice() {
        this.mice = new ArrayList<>();
    }
    
    public void print() {
        for (int i = 0; i < mice.size(); ++i) {
            System.out.println("IdRFID: " + mice.get(i).IdRFID + ", IdLabel: " + mice.get(i).IdLabel + ", FirstAppearance: " + mice.get(i).firstAppearance + ", First Grid: " + mice.get(i).firstGrid);
        }
    }
    
    public Boolean add(Mouse m) {
        return mice.add(m);
    }
    
    public Boolean hasMouse(String IdRFID) {
        for (int i = 0; i < mice.size(); ++i) {
            if (mice.get(i).IdRFID.equals(IdRFID)) return true;
        }
        return false;
    }
    
    public Mouse getMouseByIdRFID(String IdRFID) {
        for (int i = 0; i < mice.size(); ++i) {
            if (mice.get(i).IdRFID.equals(IdRFID)) return mice.get(i);
        }
        return null;       
    }
    
    /**
     * 
     * @author: parker
     * 
     * create an observableList of mice IdRFIDs + IdLabel for use in the listView
     * GUI control.
     * 
     * @return the string representation of the mouse objects in mice
     */
    public ObservableList<String> getMouseIdsLabelsObservableList() {
        ObservableList<String> miceIds = FXCollections.observableArrayList();
        for (int i = 0; i < this.mice.size(); ++i) {
            String info = mice.get(i).IdRFID + " (" + mice.get(i).IdLabel + ")";
            miceIds.add(info);
        }
        return miceIds;
    }
    
    /**
     * 
     * @author: parker
     * 
     * search for Mouse objects within Mice by an ObservableList of Strings from 
     * the listView GUI control. Return an ArrayList of the actual Mouse objects that match the 
     * ids within the ObservableList.
     * 
     * @param ids
     * @return 
     */
    public ArrayList<Mouse> getMicebyIdsLabels(ObservableList<String> ids) {
        ArrayList<Mouse> returnMice = new ArrayList<>();
        for (int i = 0; i < ids.size(); ++i) {
            // perform string manipulation to grab only the IdRFID portion of the string:
            int cutoff = ids.get(i).indexOf(' ');
            String rfid = ids.get(i).substring(0, cutoff);
            //System.out.println("getMicebyIdsLables: " + rfid);
            // check if the array of Mouse objects contains a mouse with the IdRFID:
            if (getMouseByIdRFID(rfid) != null)
                returnMice.add(getMouseByIdRFID(rfid));
        }
        if (!returnMice.isEmpty())
            return returnMice;
        else
            return null;
    }
    /**
     * @author Whitney
     * * Function returns ArrayList of existing MiceIDs and their current Grid locations
     * @param thisMouseID
     */
    public ArrayList<Neighbor> getNeighbors(String thisMouseID){
        ArrayList<Neighbor> r = new ArrayList<>();
        if (!mice.isEmpty()){
            for (Mouse m : mice) {
                if(!m.IdRFID.equals(thisMouseID)){
                    r.add(new Neighbor(m.IdRFID, m.IdLabel, m.getColor(), m.currentPosition));
                }
            }
            return r;
        }
        else return r;
    }
    /**
     * 
     * @author: Parker
     * 
     * This function returns a range of MouseLocTime data, within the bounds of the start and stop Dates, from an Array of Mouse objects.
     * 
     * @param selectedMice
     * @param start
     * @param stop
     * @return 
     */
    public static ArrayList<MouseLocTime> getMiceDataRowsFromRange(ArrayList<Mouse> selectedMice, Date start, Date stop) {
        ArrayList<MouseLocTime> locTimeData = new ArrayList<>();
        for (int i = 0; i < selectedMice.size(); ++i) {
            for (int j = 0; j < selectedMice.get(i).locTimeData.size(); ++j) {
                if (selectedMice.get(i).locTimeData.get(j).timestamp.compareTo(stop) <= 0) {
                    if (selectedMice.get(i).locTimeData.get(j).timestamp.compareTo(start) >= 0) {
                        locTimeData.add(selectedMice.get(i).locTimeData.get(j));
                    }
                }
            }
        }
        return locTimeData;
    }
}
