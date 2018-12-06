package entity;


import dao.DAOFactory;
import dao.PropertyWorker;
import dao.UserDAO;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class user
 */
//TODO javadoc
@Data
@ToString
@Log4j2
public class User {

    private final static Logger logger = LogManager.getLogger(User.class);

    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;


}

