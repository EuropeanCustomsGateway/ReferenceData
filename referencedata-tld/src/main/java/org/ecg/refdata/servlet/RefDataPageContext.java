package org.ecg.refdata.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

import com.cc.framework.Globals;

/**
 * Dummy implementation of JSP page context.
 *
 */
public class RefDataPageContext extends PageContext {

    private Servlet servlet;
    private ServletRequest servletRequest;
    private ServletResponse servletResponse;
    private ServletContext servletContext;

    private Map<String, Object> attrs = new HashMap<String, Object>();

    public RefDataPageContext(Servlet servlet, ServletContext servletContext,
            ServletRequest servletRequest, ServletResponse servletResponse) {

        this.servlet = servlet;
        this.servletContext = servletContext;
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;

        attrs.put(Globals.PAINTER_KEY, servletContext
                .getAttribute(Globals.PAINTER_KEY));

    }

    /**
     * @see javax.servlet.jsp.PageContext#getPage()
     */
    @Override
    public Object getPage() {
        return servlet;
    }

    /**
     * @see javax.servlet.jsp.PageContext#getRequest()
     */
    @Override
    public ServletRequest getRequest() {
        return servletRequest;
    }

    /**
     * @see javax.servlet.jsp.PageContext#getResponse()
     */
    @Override
    public ServletResponse getResponse() {
        return servletResponse;
    }

    /**
     * @see javax.servlet.jsp.PageContext#getServletConfig()
     */
    @Override
    public ServletConfig getServletConfig() {
        return servlet.getServletConfig();
    }

    /**
     * @see javax.servlet.jsp.PageContext#getServletContext()
     */
    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @see javax.servlet.jsp.PageContext#getSession()
     */
    @Override
    public HttpSession getSession() {
        return ((HttpServletRequest) servletRequest).getSession(true);
    }

    /**
     * @see javax.servlet.jsp.PageContext#initialize(javax.servlet.Servlet,
     * javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * java.lang.String, boolean, int, boolean)
     */
    @Override
    public void initialize(Servlet servlet, ServletRequest request,
            ServletResponse response, String errorPageURL,
            boolean needsSession, int bufferSize, boolean autoFlush)
            throws IOException, IllegalStateException, IllegalArgumentException {
        throw new RuntimeException("Operation not supported.");
    }

    /**
     * @see javax.servlet.jsp.PageContext#forward(java.lang.String)
     */
    @Override
    public void forward(String arg0) throws ServletException, IOException {
        throw new RuntimeException("Operation not supported.");
    }

    /**
     * @see javax.servlet.jsp.PageContext#include(java.lang.String)
     */
    @Override
    public void include(String arg0) throws ServletException, IOException {
        throw new RuntimeException("Operation not supported.");

    }

    /**
     * @see javax.servlet.jsp.PageContext#include(java.lang.String, boolean)
     */
    @Override
    public void include(String arg0, boolean arg1) throws ServletException,
            IOException {
        throw new RuntimeException("Operation not supported.");
    }

    /**
     * @see
     * javax.servlet.jsp.PageContext#handlePageException(java.lang.Exception)
     */
    @Override
    public void handlePageException(Exception arg0) throws ServletException,
            IOException {
        throw new RuntimeException("Operation not supported.");
    }

    /**
     * @see
     * javax.servlet.jsp.PageContext#handlePageException(java.lang.Throwable)
     */
    @Override
    public void handlePageException(Throwable arg0) throws ServletException,
            IOException {
        throw new RuntimeException("Operation not supported.");
    }

    /**
     * @see javax.servlet.jsp.PageContext#getException()
     */
    @Override
    public Exception getException() {
        return null;
    }

    /**
     * @see javax.servlet.jsp.PageContext#release()
     */
    @Override
    public void release() {
    }

    /**
     * @see javax.servlet.jsp.JspContext#getExpressionEvaluator()
     */
    @Override
    public ExpressionEvaluator getExpressionEvaluator() {
        return null;
    }

    /**
     * @see javax.servlet.jsp.JspContext#getOut()
     */
    @Override
    public JspWriter getOut() {
        return null;
    }

    /**
     * @see javax.servlet.jsp.JspContext#getVariableResolver()
     */
    @Override
    public VariableResolver getVariableResolver() {
        return null;
    }

    /**
     * @see javax.servlet.jsp.JspContext#findAttribute(java.lang.String)
     */
    @Override
    public Object findAttribute(String arg0) {
        return attrs.get(arg0);
    }

    /**
     * @see javax.servlet.jsp.JspContext#getAttribute(java.lang.String)
     */
    @Override
    public Object getAttribute(String arg0) {
        return attrs.get(arg0);
    }

    /**
     * @see javax.servlet.jsp.JspContext#getAttribute(java.lang.String, int)
     */
    @Override
    public Object getAttribute(String arg0, int arg1) {
        return attrs.get(arg0);
    }

    /**
     * @see javax.servlet.jsp.JspContext#getAttributeNamesInScope(int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Enumeration getAttributeNamesInScope(int arg0) {
        return Collections.enumeration(attrs.keySet());
    }

    /**
     * @see javax.servlet.jsp.JspContext#getAttributesScope(java.lang.String)
     */
    @Override
    public int getAttributesScope(String arg0) {
        return PAGE_SCOPE;
    }

    /**
     * @see javax.servlet.jsp.JspContext#removeAttribute(java.lang.String)
     */
    @Override
    public void removeAttribute(String arg0) {
        attrs.remove(arg0);
    }

    /**
     * @see javax.servlet.jsp.JspContext#removeAttribute(java.lang.String, int)
     */
    @Override
    public void removeAttribute(String arg0, int arg1) {
        attrs.remove(arg0);
    }

    /**
     * @see javax.servlet.jsp.JspContext#setAttribute(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setAttribute(String arg0, Object arg1) {
        attrs.put(arg0, arg1);
    }

    /**
     * @see javax.servlet.jsp.JspContext#setAttribute(java.lang.String,
     * java.lang.Object, int)
     */
    @Override
    public void setAttribute(String arg0, Object arg1, int arg2) {
        attrs.put(arg0, arg1);
    }

}
