package it.brunasti.engine.inferential.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.io.*;

@Builder
@AllArgsConstructor
public class ExecutionTracer {

    final static public String ROW_SEPARATOR = "----------------------------------";

    private static ExecutionTracer executeTracer = new ExecutionTracer();

    @Setter
    private boolean debug = false;
    @Setter
    private boolean doTrace = false;
    @Setter
    private String title = "---- TRACE ----";
    @Setter
    private String fileName = "trace.txt";
    private PrintWriter writer;


    public ExecutionTracer() {
        setExecuteTracer(fileName, title, doTrace);
    }

    public ExecutionTracer(String fileName, String title, boolean doTrace, boolean debug) {
        this.debug = debug;
        setExecuteTracer(fileName, title, doTrace);
    }

    private void setExecuteTracer(String fileName, String title, boolean doTrace) {
        if (debug) System.out.println("setExecuteTracer ("+doTrace+"): "+fileName);
        this.fileName = fileName;
        this.title = title;
        this.doTrace = doTrace;

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(title);
            printWriter.println(ROW_SEPARATOR);
            printWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void trace(String string) {
        trace(new String[]{string} );
    }

    public void trace(String[] strings) {
        if (debug) System.out.println("TRACING ("+doTrace+"): "+strings.toString());
        if (doTrace) {
            try {
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(
                        fileName,
                        true /* append = true */));

                for (String string : strings) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
