package mate.academy.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        req.getRequestDispatcher("/WEB.INF/views/drivers/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String licenseNumber = req.getParameter("licenseNumber");
        if (name.isEmpty() || licenseNumber.isEmpty()) {
            req.setAttribute("message", "Please enter valid data");
            req.getRequestDispatcher("/WEB.INF/views/drivers/create.jsp").forward(req, resp);
        } else {
            driverService.create(new Driver(name, licenseNumber));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
