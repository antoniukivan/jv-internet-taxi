package mate.academy.controller;

import mate.academy.lib.Injector;
import mate.academy.service.CarService;
import mate.academy.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final CarService carService
            = (CarService) injector.getInstance(CarService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long carId = Long.valueOf(req.getParameter("id"));
        carService.delete(carId);
        resp.sendRedirect(req.getContextPath() + "/cars/all");
    }
}
