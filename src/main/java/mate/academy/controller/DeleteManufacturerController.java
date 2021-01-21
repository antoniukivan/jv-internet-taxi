package mate.academy.controller;

import mate.academy.lib.Injector;
import mate.academy.service.DriverService;
import mate.academy.service.ManufacturerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long manufacturerId = Long.valueOf(req.getParameter("id"));
        manufacturerService.delete(manufacturerId);
        resp.sendRedirect(req.getContextPath() + "/manufacturers/all");
    }
}
