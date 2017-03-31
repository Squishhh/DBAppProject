package cs304.referendum.utils;

/**
 * Created by b0ss on 2017-03-29.
 */

public class VoterRef {
    private String UserName;
    private String RefDescription;
    private Integer RefYear;

    public VoterRef(String UserName, String RefDescription, Integer RefYear) {
        this.UserName = UserName;
        this.RefDescription = RefDescription;
        this.RefYear = RefYear;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getRefYear() {
        return RefYear;
    }

    public void setRefYear(Integer refYear) {
        RefYear = refYear;
    }

    public String getRefDescription() {

        return RefDescription;
    }

    public void setRefDescription(String refDescription) {
        RefDescription = refDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoterRef voterRef = (VoterRef) o;

        if (UserName != null ? !UserName.equals(voterRef.UserName) : voterRef.UserName != null)
            return false;
        if (RefDescription != null ? !RefDescription.equals(voterRef.RefDescription) : voterRef.RefDescription != null)
            return false;
        return RefYear != null ? RefYear.equals(voterRef.RefYear) : voterRef.RefYear == null;

    }

    @Override
    public int hashCode() {
        int result = UserName != null ? UserName.hashCode() : 0;
        result = 31 * result + (RefDescription != null ? RefDescription.hashCode() : 0);
        result = 31 * result + (RefYear != null ? RefYear.hashCode() : 0);
        return result;
    }
}

