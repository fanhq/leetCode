import com.leetCode.problems.GenerateMatrix;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Hachel on 2017/12/22
 */
public class CommonTest {

    @Test
    public void testMatrix(){
        int[][] result = GenerateMatrix.generateMatrix(5);
        System.out.println(result.length);
    }

}
