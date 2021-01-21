package mate.academy.controllers;

import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.service.CarService;
import mate.academy.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long driverId = Long.valueOf(req.getParameter("id"));
        driverService.delete(driverId);
        resp.sendRedirect(req.getContextPath() + "/drivers/all");
    }
}