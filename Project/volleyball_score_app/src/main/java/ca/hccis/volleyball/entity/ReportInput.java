package ca.hccis.volleyball.entity;

import java.util.ArrayList;

/**
 * Form criteria and results, following the reference ReportSquash pattern.
 * Dates stay as strings so invalid submissions can be returned to the form.
 * @author John Aquino
 * @since 20261022
 */
public class ReportInput {
    private String name = "";
    private String startDate = "";
    private String endDate = "";
    private ArrayList<VolleyballMatch> volleyballMatches = new ArrayList<>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name == null ? "" : name.trim(); }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate == null ? "" : startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate == null ? "" : endDate; }
    public ArrayList<VolleyballMatch> getVolleyballMatches() { return volleyballMatches; }
    public void setVolleyballMatches(ArrayList<VolleyballMatch> matches) { volleyballMatches = matches; }
}
