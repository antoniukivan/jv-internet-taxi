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
import mate.academy.model.Manufacturer;
import mate.academy.service.CarService;
import mate.academy.service.DriverService;
import mate.academy.service.ManufacturerService;

public class CreateCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final CarService carService
            = (CarService) injector.getInstance(CarService.class);
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB.INF/views/cars/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String model = req.getParameter("model");
        String stringManufacturerId = req.getParameter("manufacturerId");
        if (model.isEmpty() || stringManufacturerId.isEmpty()) {
            req.setAttribute("message", "Please enter valid data");
            req.getRequestDispatcher("/WEB.INF/views/cars/create.jsp").forward(req, resp);
        } else {
            try {
                Long manufacturerId = Long.valueOf(stringManufacturerId);
                Manufacturer manufacturer = manufacturerService.get(manufacturerId);
                carService.create(new Car(model, manufacturer));
            } catch (NumberFormatException | DataProcessingException e) {
                req.setAttribute("message", "Please enter valid manufacturer id");
                req.getRequestDispatcher("/WEB.INF/views/cars/create.jsp").forward(req, resp);
            }
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
