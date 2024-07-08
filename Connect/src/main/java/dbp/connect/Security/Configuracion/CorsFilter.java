//package dbp.connect.Security.Configuracion;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.GenericFilterBean;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//public class CorsFilter extends GenericFilterBean implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        chain.doFilter(request, response);
//    }
//}
