package Function;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import DataObject.*;
import Variable.SystemVarible;

public class Data {
    public List<Document> ReadRawFileData(String filePath)
    {
        List<Document> documents = new ArrayList<Document>();
        
        StringBuilder doc = new StringBuilder();
        int docID = 0;

        try
        {
            FileReader fr = new FileReader(SystemVarible.GetBaseApplicationPath() + "\\" + filePath);
            Scanner s = new Scanner(fr);

            while(s.hasNextLine())
            {
                String tempInput = s.nextLine().trim().toLowerCase();          

                try
                {
                    docID = Integer.parseInt(tempInput);
                }
                catch (NumberFormatException e)
                {
                    if(tempInput.length() == 1)
                    {
                        Document d = new Document(docID, doc.toString().trim());
                        documents.add(d);
                        doc = new StringBuilder();
                    }
                    else
                    {
                        doc.append(tempInput);
                        doc.append(" ");
                    }    
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    break;
                }                       
            }

            s.close();
            fr.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        return documents;
    }

    public boolean WriteWorldIndexFile (Hashtable<String, HashSet<Integer>> worldIndex, String filePath)
    {
        try
        {
            FileWriter fw = new FileWriter(SystemVarible.GetBaseApplicationPath() + "\\" + filePath, false);

            Set<String> keyWorld = worldIndex.keySet();
            for (String key : keyWorld) 
            {
                fw.write(key);
                fw.write( " - ");
                
                List<Integer> indexDocList = new ArrayList<Integer>(worldIndex.get(key));
                Collections.sort(indexDocList);

                for (Integer index : indexDocList) 
                {                  
                    fw.write(index.toString());
                    fw.write(" ");
                }

                fw.write(SystemVarible.GetNewLineChar());
            }

            fw.close();

            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Hashtable<String, HashSet<Integer>> ReadWorldIndexFile(String filePath)
    {
        Hashtable<String, HashSet<Integer>> result = new Hashtable<String, HashSet<Integer>>();

        try
        {
            FileReader fr = new FileReader(SystemVarible.GetBaseApplicationPath() + "\\" + filePath);
            Scanner s = new Scanner(fr);

            while(s.hasNextLine())
            {
                String tempInput = s.nextLine().trim().toLowerCase();          

                if(tempInput.contains("-")) // check format data   
                {
                    String[] keyValue = tempInput.split("-");

                    if(keyValue.length > 1)
                    {
                        String[] values = keyValue[1].trim().split(" ");
                        HashSet<Integer> valuesHashSet = new HashSet<Integer>();
                        
                        for (String v : values) 
                        {
                            try
                            {
                                valuesHashSet.add(Integer.parseInt(v.trim()));
                            }
                            catch (Exception e)
                            {
                                System.out.print("Error for Value");
                                System.out.println(e.getMessage());
                                return result;
                            }
                        }

                        result.put(keyValue[0].trim(), valuesHashSet);
                    }
                }                                    
            }

            s.close();
            fr.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public boolean WriteQueryOutput(List<HashSet<Integer>> queryIndex, String filePath)
    {
        try
        {
            FileWriter fw = new FileWriter(SystemVarible.GetBaseApplicationPath() + "\\" + filePath, false);

            for(Integer i = 0; i < queryIndex.size(); i++)
            {
                List<Integer> indexs = new ArrayList<Integer>(queryIndex.get(i));
                Collections.sort(indexs);

                fw.write(i.toString());
                fw.write(" - ");

                for (Integer docIndex : indexs) 
                {
                    fw.write(docIndex.toString());
                    fw.write(" ");
                }

                fw.write(SystemVarible.GetNewLineChar());
            }            

            fw.close();

            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
