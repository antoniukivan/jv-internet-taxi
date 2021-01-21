package mate.academy.controller;

import mate.academy.lib.Injector;
import mate.academy.model.Driver;
import mate.academy.model.Manufacturer;
import mate.academy.service.DriverService;
import mate.academy.service.ManufacturerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllManufacturersController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Manufacturer> allManufacturers = manufacturerService.getAll();
        req.setAttribute("drivers", allManufacturers);
        req.getRequestDispatcher("/WEB.INF/views/manufacturers/all.jsp").forward(req, resp);
    }
}
