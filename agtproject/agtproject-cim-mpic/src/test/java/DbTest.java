import cn.agtsci.AgtProjectMpicApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgtProjectMpicApplication.class})
public class DbTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoad() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from cities");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String cityName = resultSet.getString("name");
            System.out.println(cityName);
        }
    }
}
