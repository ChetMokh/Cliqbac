package co.thepartyon.cliqbac;

import android.location.Location;
import android.media.Image;

import java.util.ArrayList;

/**
 * Created by asymkowick on 3/6/16.
 */
public class Party {
    int partyId;
    User partyOwner;
    Location partyLocation;
    String partyDescription;
    Image partyPic;

    ArrayList<User> guestList; //Stores all the JOINED users

    //Default Constructor
    public Party() {
        this.partyDescription = "DEFAULT DESCRIPTION - PLEASE ENTER A DESCRIPTION";

    }

    //Put more methods here as needed







    /**********************/
    /** Getters/Setters **/
    /**********************/

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public User getPartyOwner() {
        return partyOwner;
    }

    public void setPartyOwner(User partyOwner) {
        this.partyOwner = partyOwner;
    }

    public Location getPartyLocation() {
        return partyLocation;
    }

    public void setPartyLocation(Location partyLocation) {
        this.partyLocation = partyLocation;
    }

    public String getPartyDescription() {
        return partyDescription;
    }

    public void setPartyDescription(String partyDescription) {
        this.partyDescription = partyDescription;
    }

    public Image getPartyPic() {
        return partyPic;
    }

    public void setPartyPic(Image partyPic) {
        this.partyPic = partyPic;
    }
}
