package mate.academy.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Car;
import mate.academy.model.Driver;
import mate.academy.service.CarService;
import mate.academy.service.DriverService;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long driverId = Long.valueOf(req.getParameter("driver_id"));
            Long carId = Long.valueOf(req.getParameter("car_id"));
            Car car = carService.get(carId);
            Driver driver = driverService.get(driverId);
            carService.addDriverToCar(driver, car);
        } catch (NumberFormatException | DataProcessingException e) {
            req.setAttribute("message", "Please enter valid id");
            req.getRequestDispatcher("/WEB.INF/views/cars/drivers/add.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}