package ku.cs.shop.models;

import ku.cs.shop.services.filterer.ConditionFilterer;

import java.util.ArrayList;

public class ReportList {
    private ArrayList<Reporter> reporters;

    public ReportList() {
        reporters = new ArrayList<>();
    }

    public void addReport(Reporter reporter) {
        reporters.add(reporter);
    }

    public ArrayList<Reporter> search(ConditionFilterer<Reporter> filterer) {
        ArrayList<Reporter> filtered = new ArrayList<>();

        for (Reporter reporter : reporters) {
            if (filterer.match(reporter)) {
                filtered.add(reporter);
            }
        }

        return filtered;
    }

    public String toCSV() {
        String result = "";
        for (Reporter reporter : reporters) {
            result += reporter.toCSV() + "\n";
        }
        return result;
    }

    public ArrayList<Reporter> getReportInfo() {
        return reporters;
    }
}
