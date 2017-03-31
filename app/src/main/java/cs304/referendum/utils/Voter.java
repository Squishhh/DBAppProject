package cs304.referendum.utils;

/**
 * Created by b0ss on 2017-03-29.
 */

public class Voter {
    private Integer userID;
    private String name;
    private String userName;
    private String email;

    public Voter(Integer userID, String name, String userName, String email) {
        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.email = email;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Voter voter = (Voter) o;

        if (userID != null ? !userID.equals(voter.userID) : voter.userID != null) return false;
        if (name != null ? !name.equals(voter.name) : voter.name != null) return false;
        if (userName != null ? !userName.equals(voter.userName) : voter.userName != null)
            return false;
        return email != null ? email.equals(voter.email) : voter.email == null;

    }

    @Override
    public int hashCode() {
        int result = userID != null ? userID.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
