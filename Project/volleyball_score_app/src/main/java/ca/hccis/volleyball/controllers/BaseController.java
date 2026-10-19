package ca.hccis.volleyball.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controls the general pages of the Volleyball Score App.
 * Adapted from the instructor reference BaseController.
 *
 * @author John Aquino
 */
@Controller
public class BaseController {

    /**
     * Send the user to the welcome view.
     * @return the home template
     */
    @RequestMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Send the user to the about view.
     * @return the about template
     */
    @RequestMapping("/about")
    public String about() {
        return "other/about";
    }
}
