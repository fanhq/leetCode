import com.leetCode.util.excel.ExcelLogs;
import com.leetCode.util.excel.ExcelUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Hachel on 2017/12/22
 */
public class CommonTest {

    @Test
    public void excelTest() throws Exception{
        File file = new File("D:\\work file\\云南虚拟机信息.xlsx");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ExcelLogs logs = new ExcelLogs();
        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs, 0);

        for (Map m : importExcel) {
            System.out.println(m.get("虚拟机名称"));
        }
    }

}
