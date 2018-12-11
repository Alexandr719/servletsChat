package controllers.fillpagecontrollers;

        import dao.DAOFactory;
        import dao.MessageDAO;
        import entity.Message;
        import entity.User;
        import mapper.EntityMapper;

        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
        import java.util.List;

@WebServlet(name = "MessageDAOController", urlPatterns = "/getmessages")
public class MessageDAOController extends HttpServlet {
    private MessageDAO messageDAO;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityMapper mapper = new EntityMapper();
        //TODO
        List<Message> messages = messageDAO.getLastMessages(100);

        response.setContentType("application/json");
        response.getWriter().println(mapper.objectToJSON(messages));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        messageDAO = dao.getMessageDAO();
    }
}
