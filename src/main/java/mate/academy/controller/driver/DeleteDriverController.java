package mate.academy.controller.driver;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lib.Injector;
import mate.academy.service.DriverService;

public class DeleteDriverController extends HttpServlet {
    public static final String DRIVER_ID = "id";
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long driverId = Long.valueOf(req.getParameter(DRIVER_ID));
        driverService.delete(driverId);
        resp.sendRedirect(req.getContextPath() + "/drivers");
    }
}
