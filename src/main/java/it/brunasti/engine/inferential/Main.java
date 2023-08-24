package it.brunasti.engine.inferential;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.brunasti.engine.inferential.utils.RuleEngineUtils;

import it.brunasti.engine.inferential.utils.Utils;
import org.apache.commons.cli.*;

import java.io.IOException;

// This Main uses https://commons.apache.org/proper/commons-cli/usage.html
public class Main {

    static String query = "X";

    static boolean debug = false;
    static boolean dump = false;
    static boolean trace = false;


    static String factsFile = "default_facts.json";
    static String rulesFile = "default_rules.json";
    static String knowledgeBaseFile = "knowledge_base.json";
    static boolean readFromKB = true;
    static boolean readJSON = true;

    static String dumpKnowledgeBaseFile = "dump_knowledge_base.json";
    static String traceFile = "trace_execution.txt";



    public static boolean processCommandLine(String[] args) {

        // Reset all the flags, to avoid multiple sequence runs interfering
        debug = false;
        dump = false;
        trace = false;
        readFromKB = true;
        readJSON = true;

        Options options = new Options();

        Option optionDebug = new Option("d", "debug", false, "Execute in debug mode");
        Option optionTrace = new Option("T", "trace", true, "Trace the execution in a file");
        Option optionDump = new Option("D", "dump", true, "Dump the KnowledgeBase at the end of the execution");
        Option optionHelp = new Option("h", "help", false, "Help");
        Option optionFactsFile = new Option("f", "facts", true, "KnowledgeBase facts file");
        Option optionRulesFile = new Option("r", "rules", true, "KnowledgeBase rules file");
        Option optionKnowledgeBaseFile = new Option("k", "knowledge", true, "KnowledgeBase JSON file");
        Option optionNoJSON = new Option("x", "no-json", false, "Read from non JSON format");

        options.addOption(optionHelp);
        options.addOption(optionDump);
        options.addOption(optionTrace);
        options.addOption(optionDebug);
        options.addOption(optionKnowledgeBaseFile);
        options.addOption(optionFactsFile);
        options.addOption(optionRulesFile);
        options.addOption(optionNoJSON);

        HelpFormatter helper = new HelpFormatter();

        try {

            CommandLine cmd;
            CommandLineParser parser = new BasicParser();

            cmd = parser.parse(options, args);

            if (cmd.hasOption("d")) {
                debug = true;
            }
            if (cmd.hasOption("D")) {
                dumpKnowledgeBaseFile = cmd.getOptionValue(optionDump.getLongOpt());
                if (debug) System.out.println(optionDump.getDescription() + " set to [" + dumpKnowledgeBaseFile + "]");
                dump = true;
            }
            if (cmd.hasOption("T")) {
                traceFile = cmd.getOptionValue(optionTrace.getLongOpt());
                if (debug) System.out.println(optionTrace.getDescription() + " set to [" + traceFile + "]");
                trace = true;
            }
            if (debug) {
                Utils.dump("ARGS", args);
                Utils.dump("CMD", cmd.getArgs());
            }
            if (cmd.hasOption("h")) {
                helper.printHelp("java Main <query> <options>", options);
                return false;
            }


            if (cmd.getArgs().length > 0) {
                query = cmd.getArgs()[0];
            }

            if (cmd.hasOption("k")) {
                knowledgeBaseFile = cmd.getOptionValue(optionKnowledgeBaseFile.getLongOpt());
                if (debug)
                    System.out.println(optionKnowledgeBaseFile.getDescription() + " set to [" + knowledgeBaseFile + "]");
                readFromKB = true;
            }
            if (cmd.hasOption("f")) {
                factsFile = cmd.getOptionValue(optionFactsFile.getLongOpt());
                if (debug) System.out.println(optionFactsFile.getDescription() + " set to [" + factsFile + "]");
                readFromKB = false;
            }
            if (cmd.hasOption("r")) {
                rulesFile = cmd.getOptionValue(optionRulesFile.getLongOpt());
                if (debug) System.out.println(optionRulesFile.getDescription() + " set to [" + rulesFile + "]");
                readFromKB = false;
            }
            if (cmd.hasOption("x")) {
                readJSON = false;
            }


        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Usage:", options);
            return false;
        }
        return true;
    }


    public static RuleEngine loadEngine(boolean readFromKB, String knowledgeBaseFile, String factsFile, String rulesFile, boolean readJSON) {
        RuleEngine ruleEngine;

        if (readFromKB) {
            if (debug) System.out.println("Load KB [" + knowledgeBaseFile + "]");
            try {
                ruleEngine = RuleEngineUtils.readFromJsonFile(knowledgeBaseFile);
            } catch (JsonProcessingException ioe) {
                System.err.println("Problem parsing KnowledgeBase file [" + knowledgeBaseFile + "]");
                ioe.printStackTrace();
                return null;
            } catch (IOException ioe) {
                System.err.println("Problem reading KnowledgeBase file [" + knowledgeBaseFile + "]");
                ioe.printStackTrace();
                return null;
            }
        } else {
            if (debug)
                System.out.println("Load KB facts [" + factsFile + "] and rules [" + rulesFile + "]");
            // TODO : provide the reading of facts and rules separately
            ruleEngine = new RuleEngine();

            try {
                if (readJSON) {
                    RuleEngineUtils.readFactsFromJsonFile(ruleEngine, factsFile);
                } else {
                    RuleEngineUtils.readFactsFromNonJsonFile(ruleEngine, factsFile);
                }
            } catch (JsonProcessingException ioe) {
                System.err.println("Problem parsing Facts file [" + factsFile + "]");
                ioe.printStackTrace();
                return null;
            } catch (IOException ioe) {
                System.err.println("Problem reading Facts file [" + factsFile + "]");
                ioe.printStackTrace();
                return null;
            }

            try {
                if (readJSON) {
                    RuleEngineUtils.readRulesFromJsonFile(ruleEngine, rulesFile);
                } else {
                    RuleEngineUtils.readRulesFromNonJsonFile(ruleEngine, rulesFile);
                }
            } catch (JsonProcessingException ioe) {
                System.err.println("Problem parsing Rules file [" + rulesFile + "]");
                ioe.printStackTrace();
                return null;
            } catch (IOException ioe) {
                System.err.println("Problem reading Rules file [" + rulesFile + "]");
                ioe.printStackTrace();
                return null;
            }

//            System.err.println("NOT YET IMPLEMENTED - Feature 01");
//            return null;
        }

        return  ruleEngine;
    }



    // TODO : find a way to return the output of the elaboration in a "computer oriented" way
    // TODO : decompose into simpler basic functions
    public static void execute(RuleEngine ruleEngine, boolean debug, String dumpKnowledgeBaseFile, boolean trace) {
        // setup the execution env
        ruleEngine.setDebug(debug);
        ruleEngine.setTrace(trace);

        if (debug) System.out.println(ruleEngine);

        // Evaluate question
        if (debug) System.out.println("Start querying : "+query);
        if (debug) System.out.println("  - : "+trace+" "+traceFile);
        double res = ruleEngine.evaluateQuery(query, trace, traceFile);

        if (debug) System.out.println("==== END ====");
        if (debug) System.out.println(ruleEngine);

        System.out.println("Result : " +query+ " = " + res);
        if (dump) {
            try {
                RuleEngineUtils.writeToJsonFile(ruleEngine, dumpKnowledgeBaseFile);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    // TODO : find a way to return the output of the elaboration in a "computer oriented" way
    // TODO : decompose into simpler basic functions
    public static void main(String[] args) {
        boolean correctCLI = processCommandLine(args);

        if (!correctCLI) return;

        RuleEngine ruleEngine = loadEngine(readFromKB, knowledgeBaseFile, factsFile, rulesFile, readJSON);

        if (ruleEngine == null) return;

        execute(ruleEngine, debug, dumpKnowledgeBaseFile, trace);
    }
}