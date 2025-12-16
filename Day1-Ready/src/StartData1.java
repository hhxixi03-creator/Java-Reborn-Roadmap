import java.text.SimpleDateFormat;
import java.util.Date;

public class StartData1 {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(new Date());
        System.out.println(formatDate);
    }
}

