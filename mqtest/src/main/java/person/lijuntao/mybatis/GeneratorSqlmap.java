package person.lijuntao.mybatis;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;  
import java.util.List;  
  
import org.mybatis.generator.api.MyBatisGenerator;  
import org.mybatis.generator.config.Configuration;  
import org.mybatis.generator.config.xml.ConfigurationParser;  
import org.mybatis.generator.internal.DefaultShellCallback;

import tk.mybatis.mapper.common.Mapper;  
  
  
public class GeneratorSqlmap {  
  
  
    public void generator() throws Exception{  
  
  
        List<String> warnings = new ArrayList<String>();  
        boolean overwrite = true;  
        //º”‘ÿ≈‰÷√Œƒº˛  
        //File configFile = new File("generatorConfig.xml");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("generatorManage.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);  
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);  
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,  
                callback, warnings);  
        myBatisGenerator.generate(null);  
  
    }   
    public static void main(String[] args) throws Exception {  
        try {  
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();  
            generatorSqlmap.generator();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
    }  
}  