package mate.academy.controller.driver;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Driver;
import mate.academy.service.DriverService;

public class CreateDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/drivers/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String licenseNumber = req.getParameter("license_number");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            driverService.create(new Driver(name, licenseNumber, login, password));
        } catch (DataProcessingException e) {
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/drivers/create.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
