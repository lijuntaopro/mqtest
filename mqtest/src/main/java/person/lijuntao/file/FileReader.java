package person.lijuntao.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.FileUtils;

public class FileReader {
	public static void main(String[] args) {
		
		String read = null;
		try {
			read = FileUtils.fileRead(new File("C:\\Users\\Administrator\\Desktop\\1.2.3版本呢有改动过的.txt"), "gbk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] strings = read.split("\n");
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		if(strings != null){
			for(String s : strings){
				if(StringUtils.isNotBlank(s) ){
					if( !set.contains(s)){
						set.add(s);
					}else{
						s = "--" + s;
					}
					list.add(s);
				}
			}
		}
		Collections.sort(list);
		System.out.println(StringUtils.join(list, "\n"));
		System.out.println("--------------------Set------------------\n" + StringUtils.join(set, "\n"));
	}
}
