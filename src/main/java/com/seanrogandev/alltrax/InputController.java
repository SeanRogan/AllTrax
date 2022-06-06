package com.seanrogandev.alltrax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class InputController {
    private final Logger logger = LoggerFactory.getLogger(PropertiesManager.class);
    final private Scanner in = new Scanner(System.in);

    public String takeInput() {
        logger.info("Receiving input from console..");
        String input = in.nextLine();
        if(!validateInput(input)) {
            logger.info("input returned is: " + input);
            return input;
        }
        return null;
    }

    private boolean validateInput(String input) {
        //todo figure out what illegal search terms would be,
        // filter them, return true if not one of them
        return false;
    }
}
