package ca.hccis.volleyball.controllers;

import ca.hccis.volleyball.bo.ReportBO;
import ca.hccis.volleyball.entity.ReportInput;
import ca.hccis.volleyball.util.CisUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.sql.SQLException;
import java.util.MissingResourceException;

/**
 * Administers reports following the instructor ReportController pattern.
 * @author John Aquino
 * @since 20261022
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /** @return the report menu */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() {
        return "report/list";
    }

    /**
     * Display report criteria.
     * @param model view attributes
     * @return input view
     */
    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public String reportTeam(Model model) {
        model.addAttribute("reportInput", new ReportInput());
        return "report/reportTeam";
    }

    /**
     * Run the JDBC report, display results and save the text output.
     * @param model view attributes
     * @param report input form
     * @return input/results view
     */
    @RequestMapping(value = "/team/submit", method = RequestMethod.POST)
    public String reportTeamSubmit(Model model, @ModelAttribute("reportInput") ReportInput report) {
        try {
            ReportBO.processTeamScoreReport(report);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "report/reportTeam";
        } catch (SQLException | MissingResourceException e) {
            logger.error("Unable to retrieve match report", e);
            model.addAttribute("error", "Unable to load matches. Check that MySQL is running, the database is imported, and connection settings are correct.");
            return "report/reportTeam";
        }
        String generated = CisUtility.getCurrentDate("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("generated", generated);
        model.addAttribute("reportReady", true);
        try {
            model.addAttribute("savedFile", ReportBO.writeReportToFile(ReportBO.buildReportText(report, generated)).toString());
        } catch (IOException | SecurityException e) {
            logger.error("Unable to save match report", e);
            model.addAttribute("fileError", "The report is shown below, but its text file could not be saved. Check the report folder and write permissions.");
        }
        return "report/reportTeam";
    }
}
