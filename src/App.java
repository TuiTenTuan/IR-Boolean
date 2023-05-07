//Nhóm 030
//N18DCCN200 - Võ Minh Tuấn
//N19DCCN030 - NGuyễn Thanh Dũng
//N19DCCN032 - Võ Đông Duy
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import DataObject.Document;
import Function.*;
import Variable.SystemVarible;

public class App {
    private static String pathData = "Data\\doc-text";
    private static String pathQuery = "Data\\query-text";
    private static String pathStorage = "Output\\storage-text.txt";
    private static String pathOutput = "Output\\output-text.txt";
    private static String pathAss = "Data\\rlv-ass";

    public static void main(String[] args)
    {
        //read raw data
        Data data = new Data();
        List<Document> doc = data.ReadRawFileData(pathData);
        System.out.println(doc.size());           

        //Make WorldIndex
        IRFunction ir = new IRFunction();
        Hashtable<String, HashSet<Integer>> worldBaseIndex = ir.WorldIndex(doc);
        System.out.println(worldBaseIndex.size());

        //storage worldIndex
        if(data.WriteWorldIndexFile(worldBaseIndex, pathStorage))
        {
            System.out.println("Storage World Index Success at: " + SystemVarible.GetBaseApplicationPath() + "\\" + pathStorage);
        }
        else
        {
            System.out.println("Storage World Index fail!");
        }

        List<Document> dataQuery = data.ReadRawFileData(pathQuery);
        System.out.println(dataQuery.size());

        //Find world
        List<HashSet<Integer>> queryIndex = new ArrayList<HashSet<Integer>>();
        for (Document document : dataQuery) 
        {
            queryIndex.add(ir.FindDocIDWithOptimaze(document.getContent(), worldBaseIndex));
        }

        if(data.WriteQueryOutput(queryIndex, pathOutput))
        {
            System.out.println("Storage Query Find Document Index Success at: " + SystemVarible.GetBaseApplicationPath() + "\\" + pathStorage);
        }
        else
        {
            System.out.println("Storage Query Find Document Index fail!");
        }
    }    
}
