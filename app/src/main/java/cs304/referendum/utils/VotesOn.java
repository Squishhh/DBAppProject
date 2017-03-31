package cs304.referendum.utils;

/**
 * Created by b0ss on 2017-03-29.
 */

public class VotesOn {
    private Integer userID;
    private Integer refID;
    private String option;

    public VotesOn(Integer userID, Integer refID, String option) {
        this.userID = userID;
        this.refID = refID;
        this.option = option;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getRefID() {
        return refID;
    }

    public void setRefID(Integer refID) {
        this.refID = refID;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotesOn votesOn = (VotesOn) o;

        if (userID != null ? !userID.equals(votesOn.userID) : votesOn.userID != null) return false;
        if (refID != null ? !refID.equals(votesOn.refID) : votesOn.refID != null) return false;
        return option != null ? option.equals(votesOn.option) : votesOn.option == null;

    }

    @Override
    public int hashCode() {
        int result = userID != null ? userID.hashCode() : 0;
        result = 31 * result + (refID != null ? refID.hashCode() : 0);
        result = 31 * result + (option != null ? option.hashCode() : 0);
        return result;
    }
}
