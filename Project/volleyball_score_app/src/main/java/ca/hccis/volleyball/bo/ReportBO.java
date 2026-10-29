package ca.hccis.volleyball.bo;

import ca.hccis.volleyball.entity.ReportInput;
import ca.hccis.volleyball.entity.VolleyballMatch;
import ca.hccis.volleyball.util.CisUtility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Report processing follows the updated reference ReportBO structure.
 * Custom file output is kept here to preserve the reference utilities.
 * @author John Aquino
 * @since 20261026
 */
public class ReportBO {
    public static final String FOLDER_NAME = "C:/cis2232/reports";

    /**
     * Populate a report using JDBC and calculate its match statistics.
     * @param reportInput submitted criteria and resulting rows
     * @throws SQLException when database retrieval fails
     */
    public static void processTeamScoreReport(ReportInput reportInput) throws SQLException {
        reportInput.setVolleyballMatches(
                new VolleyballMatchBO().processSelectAllByNameAndDate(reportInput));
    }

    /**
     * Build the same report details displayed on the results page.
     * @param reportInput criteria and calculated records
     * @param generated report creation time displayed on screen
     * @return complete plain-text report
     */
    public static String buildReportText(ReportInput reportInput, String generated) {
        StringBuilder text = new StringBuilder("Team Match Performance Report\nGenerated: ").append(generated)
                .append("\nTeam: ").append(reportInput.getName()).append("\nDate range: ")
                .append(reportInput.getStartDate()).append(" to ").append(reportInput.getEndDate())
                .append("\nMatches: ").append(reportInput.getVolleyballMatches().size()).append("\n\n");
        if (reportInput.getVolleyballMatches().isEmpty()) {
            text.append("No matches found for the selected team and date range.\n");
        }
        for (VolleyballMatch match : reportInput.getVolleyballMatches()) {
            text.append(match).append('\n');
        }
        return text.toString();
    }

    /**
     * Save a complete report in the configured folder.
     * @param reportText report header, criteria and results
     * @return saved file path
     * @throws IOException when creating or writing the report fails
     */
    public static Path writeReportToFile(String reportText) throws IOException {
        ResourceBundle properties = ResourceBundle.getBundle("application");
        String folder = properties.containsKey("report.output.directory")
                ? properties.getString("report.output.directory") : FOLDER_NAME;
        return writeReportToFile(Paths.get(folder), reportText);
    }

    /**
     * Save without overwriting another report, including simultaneous requests.
     * @param folder destination folder
     * @param reportText complete text
     * @return saved file path
     * @throws IOException when output fails
     */
    public static Path writeReportToFile(Path folder, String reportText) throws IOException {
        Files.createDirectories(folder);
        String baseName = "MatchReport" + CisUtility.getCurrentDate("yyyyMMddHHmmss");
        for (int suffix = 0; ; suffix++) {
            Path output = folder.resolve(baseName + (suffix == 0 ? "" : "_" + suffix) + ".txt");
            try {
                Files.createFile(output);
            } catch (FileAlreadyExistsException e) {
                continue;
            }
            try {
                Files.writeString(output, reportText, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
                return output;
            } catch (IOException e) {
                try { Files.deleteIfExists(output); } catch (IOException cleanup) { e.addSuppressed(cleanup); }
                throw e;
            }
        }
    }

}
