package com.doublewordplay;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        File file = new File("data/sample.txt");
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String notes = null;
        Date date = null;
        while (input.hasNext()) {
            int breaks = -1;
            String day, month, year = null;
            String nextLine = input.nextLine();
            switch (nextLine) {
                case "\\d{4}": //year - YYYY
                    year = nextLine;
                    break;
                case "[a-zA-Z]{3} \\d{1,2}": //day - MMM dd
                    String[] temp = nextLine.split(" ");
                    month = temp[0];
                    day = temp[1];
                    try {
                        date = new SimpleDateFormat("yyyy-MMM-dd").parse(year + "-" + month + "-" + day);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "\\d{1,2}:\\d{1,2}-\\d{1,2}:\\d{1,2} \\d{1,2}[a-z]{3}\\d{1,2}[m]": //time range + subtotal

                    breaks++;
                    break;
                case "\\n\\d{1,2}[a-z]{3}\\d{1,2}[m]": //total time
                    String total = nextLine;
                    break;
                case "": //weekly total
                    break;
                case "[a-zA-Z]": //notes - string
                    notes = nextLine;
                    break;
                default:
                    //stuff
            }
        }
    }
}

class Day {
    private String notes;
    private Date date;
    private int totalTime;
    private int breaks;
    private int averageBreakLength;

    private Day(final Date date, final int totalTime, final int breaks, final int averageBreakLength) {
        this.date = date;
        this.totalTime = totalTime;
        this.breaks = breaks;
        this.averageBreakLength = averageBreakLength;
    }

    private Day(final Date date, final String notes) {
        this.date = date;
        this.notes = notes;
    }

    private Day(final Date date, final int totalTime, final int breaks, final int averageBreakLength, final String notes) {
        this(date, totalTime, breaks, averageBreakLength);
        this.notes = notes;
    }
}

class Week {
    private List<Day> week = null;

    private Week(Day... days) {
        if (days.length == 7) {
            this.week = new LinkedList<>(Arrays.asList(days));
        }
    }
}