package mate.academy.controllers;

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

public class CreateManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB.INF/views/manufacturers/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        if (name.isEmpty() || country.isEmpty()) {
            req.setAttribute("message", "Please enter valid data");
            req.getRequestDispatcher("/WEB.INF/views/manufacturers/create.jsp").forward(req, resp);
        } else {
            manufacturerService.create(new Manufacturer(name, country));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}