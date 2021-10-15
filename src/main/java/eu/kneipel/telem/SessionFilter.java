package eu.kneipel.telem;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Create a HTTP Session if there's none
 */
@WebFilter(value = "/*")
public class SessionFilter implements Filter {

  @Override public void init(FilterConfig filterConfig) {
    //nothing
  }

  @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws ServletException, IOException {
    if (request instanceof HttpServletRequest) {
      ((HttpServletRequest) request).getSession(true);
    }
    chain.doFilter(request, response);

  }

  @Override public void destroy() {
    //nothing
  }
}
