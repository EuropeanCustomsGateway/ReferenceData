package org.ecg.refdata.test.servlet;

/**
 * FrontController extends ActionServlet
 *
 */
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionServlet;

import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.def.DefPainterFactory;
import com.cc.framework.ui.painter.html.HtmlPainterFactory;
import org.ecg.refdata.ui.painter.RefDataPainterFactory;

public class FrontController extends ActionServlet {

    private static final long serialVersionUID = 1L;

    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

    public static HttpServletRequest getCurrentRequest() {
        return request.get();
    }

    public static void setCurrentRequest(HttpServletRequest currentRequest) {
        request.set(currentRequest);
    }

    public void init() throws ServletException {
        super.init();
        // Register all Painter Factories with the preferred GUI-Design
        // In this case we use the Default-Design.

        PainterFactory.registerApplicationPainter(getServletContext(),
                DefPainterFactory.instance());
        PainterFactory.registerApplicationPainter(getServletContext(),
                HtmlPainterFactory.instance());

        // THIS MUST BE THE LAST ONE
        PainterFactory.registerApplicationPainter(getServletContext(),
                new RefDataPainterFactory());
    }
}
