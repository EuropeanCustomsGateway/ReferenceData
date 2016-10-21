/**
 *
 */
package org.ecg.refdata.test.action;

/**
 *
 */
import java.util.Locale;
import java.util.logging.Logger;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.def.DefPainterFactory;
import com.cc.framework.ui.painter.def2.Def2PainterFactory;

public class TestAction extends FWAction {

    private static Logger logger = Logger.getLogger(TestAction.class.getName());

    public void doExecute(ActionContext ctx) {

        /**
         * only for test purposes for painter changes
         */
        String painter = ctx.request().getParameter("painter");
        logger.info("Painter param: " + painter);
        if ("def2".equals(painter)) {
            logger.info("Switching to painter Def2");
            // if painter param is set to def2 use def2 painter
            PainterFactory.unregisterApplicationPainter(ctx.request()
                    .getSession().getServletContext(), DefPainterFactory
                    .instance());
            PainterFactory.registerApplicationPainter(ctx.request()
                    .getSession().getServletContext(), Def2PainterFactory
                    .instance());
        } else {
            logger.info("Switching to default painter Def");
            PainterFactory.unregisterApplicationPainter(ctx.request()
                    .getSession().getServletContext(), Def2PainterFactory
                    .instance());
            PainterFactory.registerApplicationPainter(ctx.request()
                    .getSession().getServletContext(), DefPainterFactory
                    .instance());
        }

        String language = ctx.request().getParameter("language");
        logger.info("Language: " + language);
        if (language != null) {
            logger.info("Switching to language: " + language);
            Locale locale = new Locale(language, "");
            //Locale locale = new Locale("", language);
            logger.info("Switching to locale from: "
                    + ctx.session().getAttribute(
                            org.apache.struts.Globals.LOCALE_KEY) + ", to: "
                    + locale);
            ctx.session().setAttribute(org.apache.struts.Globals.LOCALE_KEY,
                    locale);
        }

        logger.info("Current locale are:" + ctx.session().getAttribute(org.apache.struts.Globals.LOCALE_KEY));

        SimpleListControl control = new SimpleListControl();
        control.setDataModel(new ListDataModel() {
            public int size() {
                return 5;
            }

            public Object getElementAt(int arg0) {
                return new TestItem("value1_" + arg0, "value2_" + arg0);
            }

            public String getUniqueKey(int arg0) {
                return "" + arg0;
            }
        });
        ctx.session().setAttribute("testList", control);

        ctx.forwardToInput();
    }
}
