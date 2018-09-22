package depends.util;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {

    private ArrayList<String> fileNameList = new ArrayList<String>();

    public FileUtil(String dirName) {
        recursiveFindFile(dirName);
    }

    public void recursiveFindFile(String dirName)
    {
        File dir = new File(dirName);
        if (!dir.exists()) return;
        if(dir.isFile()) {
            this.fileNameList.add(dir.getAbsolutePath());
            return;
        }
        for (File eachFile : dir.listFiles())
        {
            recursiveFindFile(eachFile.getAbsolutePath());
        }
    }


    public ArrayList<String> getFileNameList(String suffix)
    {
        ArrayList<String> goFileNameList = new ArrayList<String>();
        for(String fileName : this.fileNameList)
        {
            if(fileName.endsWith(suffix))
            {
                goFileNameList.add(fileName);
            }
        }
        return goFileNameList;
    }
}
