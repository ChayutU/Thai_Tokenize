package com.company;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.Vector;

/**
 * Created by Chayut on 10-Oct-16.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        LongLexTo tokenizer = new LongLexTo(new File("lexitron.txt"));
        File unknownFile = new File("unknown.txt");
        if (unknownFile.exists())
            tokenizer.addDict(unknownFile);
        Vector typeList;
        String text = "", inFileName, outFileName;
        String[] line;
        char ch;
        int begin, end, type;

        File inFile, outFile;
        FileReader fr;
        CSVReader br;
        FileWriter fw;

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n*******************************");
        ;
        System.out.println("*******************************");
        do {
            //Get input file name
            do {
                System.out.print("\n >>> Enter input file  ('q' to quit): ");
                inFileName = (streamReader.readLine()).trim();
                if (inFileName.equals("q"))
                    System.exit(1);
                inFile = new File(System.getProperty("user.dir") + "//" + inFileName);
            } while (!inFile.exists());

            //Get output file name

            System.out.print(" >>> Enter output file (.html only): ");
            outFileName = (streamReader.readLine()).trim();
            outFile = new File(System.getProperty("user.dir") + "//" + outFileName);

            fr = new FileReader(inFile);
            br = new CSVReader(fr);
            fw = new FileWriter(outFile);

            fw.write(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");

            while ((line = br.readNext()) != null) {
//                line=line.trim();

                tokenizer.lineInstance(line[2]);
                begin = tokenizer.first();
                fw.write(line[0] + ",");
                fw.write(line[1] + ",");
                line[2] = line[2].replaceAll("[!#%&-+.^:,?\\//]", " ");
                if(line[2].length()>0) {
                    while (tokenizer.hasNext()) {
                        end = tokenizer.next();
                        fw.write(line[2].substring(begin, end) + "|");
                        begin = end;
                    }
                }
                fw.write(",");
                fw.write(line[3] + ",");
                fw.write(line[4] + ",");
                fw.write(line[5].substring(0, line[5].length()-6));
                fw.write("\r\n");

            } //while all line
            fr.close();
            fw.close();
            System.out.println("\n *** Status: View result: " + outFileName);
        } while (true);
    } //main
}
