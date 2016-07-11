package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InformacionContexto")
public class InformacionContexto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h2>Información del Contexto</h2>");
		
		ServletContext contexto = request.getSession().getServletContext();
		
		String servidor = contexto.getServerInfo();
		out.println("<b>contexto.getServerInfo()</b> = " + servidor + " <br />");
		String nombresAtributos = devuelveNombresAtributosContexto(contexto);
		out.println("<b>contexto.getAttributeNames()</b> = " + nombresAtributos);
		String contentPath = contexto.getContextPath();
		out.println("<b>contexto.getContentPath()</b> = " + contentPath + " <br />");
		String servletContextName = contexto.getServletContextName();
		out.println("<b>contexto.getServletContextName()</b> = " + servletContextName + " <br />");
		String virtualServerName = contexto.getVirtualServerName();
		out.println("<b>contexto.getVirtualServerName()</b> = " + virtualServerName + " <br />");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	private String devuelveNombresAtributosContexto(ServletContext contexto) {
		String salida = "";
		Enumeration<String> e = contexto.getAttributeNames();
		int cuentaAtributos = 0;
		while (e.hasMoreElements()) {
			cuentaAtributos++;
			String nombreAtributo = (String) e.nextElement();
			salida += nombreAtributo + " = [";
			try {
				Object valorAtributo = contexto.getAttribute(nombreAtributo);
				salida += valorAtributo.getClass().toString().substring(6) + "] <br /> \n";
			} catch (Exception ex) {
				salida += "Excepción de cast de una clase: " + ex.getMessage() + "] <br /> \n";
			}
		}
		return salida;
	}
	

}
