package Function;

import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

import DataObject.Document;

public class IRFunction 
{
    public Hashtable<String, HashSet<Integer>> WorldIndex(List<Document> doc)
    {
        Hashtable<String, HashSet<Integer>> result = new Hashtable<String, HashSet<Integer>>();

        for (Document document : doc) {
            String[] worlds = document.getContent().split(" ");
        
            for (String world : worlds) 
            {
                if(result.containsKey(world))
                {
                    result.get(world).add(document.getId());                   
                }
                else
                {
                    HashSet<Integer> indexWorld = new HashSet<Integer>();
                    indexWorld.add(document.getId());

                    result.put(world, indexWorld);
                }
            }
        }
        
        return result;
    }

    public HashSet<Integer> FindDocID(String doc, Hashtable<String, HashSet<Integer>> documentIndexs)
    {
        HashSet<Integer> result = new HashSet<Integer>();

        String[] docSplit = doc.trim().toLowerCase().split(" ");

        for(int i = 0; i < docSplit.length; i++)
        {
            if(i == 0)
            {
                result = documentIndexs.get(docSplit[i]);
            }
            else
            {
                result = Intersect(result, documentIndexs.get(docSplit[i]));
            }
        }

        return result;
    }

    public HashSet<Integer> FindDocIDWithSkip(String doc, Hashtable<String, HashSet<Integer>> documentIndexs, int skip)
    {
        HashSet<Integer> result = new HashSet<Integer>();

        String[] docSplit = doc.trim().toLowerCase().split(" ");

        for(int i = 0; i < docSplit.length; i++)
        {
            if(i == 0)
            {
                result = documentIndexs.get(docSplit[i]);
            }
            else
            {
                result = Intersect(result, documentIndexs.get(docSplit[i]), skip);
            }
        }

        return result;
    }

    public HashSet<Integer> FindDocIDWithOptimaze(String doc, Hashtable<String, HashSet<Integer>> documentIndexs)
    {
        HashSet<Integer> result = new HashSet<Integer>();

        String[] docSplit = doc.trim().toLowerCase().split(" ");        

        List<HashSet<Integer>> docFilterIndex = new ArrayList<HashSet<Integer>>();

        for (String world : docSplit) 
        {
            docFilterIndex.add(documentIndexs.get(world));
        }

        Collections.sort(docFilterIndex, new Comparator<HashSet<Integer>>()
        {
            @Override
            public int compare(HashSet<Integer> o1, HashSet<Integer> o2) 
            {
                if(o1 == null)
                    return 1;

                if(o2 == null)
                    return -1;

                return Integer.compare(o1.size(), o2.size());
            }
        });

        if(docFilterIndex.size() > 0)
        {
            result = docFilterIndex.get(0);

            for (HashSet<Integer> docIDHashSet : docFilterIndex) 
            {
                result = Intersect(result, docIDHashSet);
            }
        }
        
        return result;
    }

    public HashSet<Integer> FindDocIDWithOptimaze(String doc, Hashtable<String, HashSet<Integer>> documentIndexs, int skip)
    {
        HashSet<Integer> result = new HashSet<Integer>();

        String[] docSplit = doc.trim().toLowerCase().split(" ");        

        List<HashSet<Integer>> docFilterIndex = new ArrayList<HashSet<Integer>>();

        for (String world : docSplit) 
        {
            docFilterIndex.add(documentIndexs.get(world));
        }

        Collections.sort(docFilterIndex, new Comparator<HashSet<Integer>>()
        {
            @Override
            public int compare(HashSet<Integer> o1, HashSet<Integer> o2) 
            {
                if(o1 == null)
                    return 1;

                if(o2 == null)
                    return -1;

                return Integer.compare(o1.size(), o2.size());
            }
        });

        if(docFilterIndex.size() > 0)
        {
            result = docFilterIndex.get(0);

            for (HashSet<Integer> docIDHashSet : docFilterIndex) 
            {
                result = Intersect(result, docIDHashSet, skip);
            }
        }
        
        return result;
    }

    private HashSet<Integer> Intersect(HashSet<Integer> first, HashSet<Integer> second)
    {
        HashSet<Integer> result = new HashSet<Integer>();
        
        if(first == null)
        {
            if(second == null)
            {
                return result;
            }
            else
            {
                return second;
            }
        }
        else
        {
            if(second == null)
            {
                return first;
            }
        }

        Iterator<Integer> firstIter = first.iterator();
        Iterator<Integer> secondIter = second.iterator();

        int firstValue = -1;
        int secondValue = -1;

        if(firstIter.hasNext() && secondIter.hasNext())
        {
            firstValue = firstIter.next();
            secondValue = secondIter.next();

            while(firstIter.hasNext() && secondIter.hasNext())
            {           
                if(firstValue == secondValue)
                {
                    result.add(firstValue);
                    firstValue = firstIter.next();
                    secondValue = secondIter.next();
                }
                else
                {
                    if(firstValue < secondValue)
                    {
                        firstValue = firstIter.next();
                    }
                    else
                    {
                        secondValue = secondIter.next();
                    }
                }
            }
        }        

        return result;
    }

    private HashSet<Integer> Intersect(HashSet<Integer> first, HashSet<Integer> second, int skip)
    {
        HashSet<Integer> result = new HashSet<Integer>();
        
        Iterator<Integer> firstIter = first.iterator();
        Iterator<Integer> secondIter = second.iterator();

        int firstValue = -1;
        int secondValue = -1;

        if(firstIter.hasNext() && secondIter.hasNext())
        {
            firstValue = firstIter.next();
            secondValue = secondIter.next();

            while(firstIter.hasNext() && secondIter.hasNext())
            {           
                if(firstValue == secondValue)
                {
                    result.add(firstValue);
                    firstValue = firstIter.next();
                    secondValue = secondIter.next();
                }
                else
                {
                    if(firstValue < secondValue)
                    {
                        for(int i=0; i < skip; i++)
                        {
                            firstValue = firstIter.next();
                        }
                    }
                    else
                    {
                        for(int i=0; i < skip; i++)
                        {
                            secondValue = secondIter.next();
                        }                        
                    }
                }
            }
        }        

        return result;
    }
}
