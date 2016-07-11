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

@WebServlet("/CreaYRecuperaAtributosContexto")
public class CreaYRecuperaAtributosContexto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		ServletContext contexto = request.getSession().getServletContext();
		String servidor = contexto.getServerInfo();
		// String salida = devuelveListadoAtributosContexto(contexto);
		String salida = devuelveNombresAtributosContexto(contexto);
	
				
		out.println("<h3>Servlet: CreaYRecuperaAtributosContexto</h3>");
		out.println("<h6>(ejecutado en " + servidor + ")</h6>");
		out.println("<h2>Atributos del Contexto en GET</h2>");		
		out.println(salida);
		out.println(" <br />");
		contexto.setAttribute("#_NOMBRE_#", "Jonás");
		out.println("Aquí se crea automáticamente un atributo de contexto. #_NOMBRE_# = Jonás" + " <br /><br />");
		out.println("<fieldset>");
		out.println("<legend>Creación de un atributo de contexto (en doPost del mismo servlet)</legend>");
		out.println("<form action=\"CreaYRecuperaAtributosContexto\" method=\"post\">");
		out.println("  Nombre del atributo <input type=\"text\" name=\"nombreAtributo\"> <br />");
		out.println("  Valor del atributo <input type=\"text\" name=\"valorAtributo\">");
		out.println("  <input type=\"submit\" value=\"Enviar al mismo Servlet\">");
		out.println("</form>");
		out.println("</fieldset>");
		out.println("<fieldset>");
		out.println("<legend>Invocación de otro servlet</legend>");
		out.println("<form action=\"RecuperaAtributosContexto\" method=\"post\">");
		out.println("  <input type=\"submit\" value=\"Enviar a otro Servlet\">");
		out.println("</form>");
		out.println("</fieldset>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ServletContext contexto = request.getSession().getServletContext();
		String servidor = contexto.getServerInfo();
		
		out.println("<h3>Servlet: CreaYRecuperaAtributosContexto</h3>");
		out.println("<h5>(ejecutado en " + servidor + ")</h5>");
		out.println("<h2>Atributos del Contexto en POST</h2>");		
		
		String nombreAtributo = request.getParameter("nombreAtributo");
		String valorAtributo = request.getParameter("valorAtributo");
		if (!nombreAtributo.isEmpty()) {
			contexto.setAttribute(nombreAtributo, valorAtributo);
			out.println("Se ha creado un nuevo atributo de Contexto: <b>" + nombreAtributo + "</b> = " + valorAtributo + " <br />");
		}
		
		String salida = devuelveListadoAtributosContexto(contexto);

		out.println(salida);
	}
	
	private String devuelveNombresAtributosContexto(ServletContext contexto) {
		String salida = "";
		Enumeration<String> e = contexto.getAttributeNames();
		int cuentaAtributos = 0;
		while (e.hasMoreElements()) {
			cuentaAtributos++;
			String nombreAtributo = (String) e.nextElement();
			salida += "<b>" + nombreAtributo + "</b> = [";
			try {
				Object valorAtributo = contexto.getAttribute(nombreAtributo);
				salida += valorAtributo.getClass().toString().substring(6) + "] <br />";
			} catch (Exception ex) {
				salida += "Excepción de cast de una clase: " + ex.getMessage() + "] <br />";
			}
		}
		salida += "<h4>Se han recuperado " + cuentaAtributos + " atributos</h4>";
		return salida;
	}
	
	private String devuelveListadoAtributosContexto(ServletContext contexto) {
		String salida = "";
		Enumeration<String> e = contexto.getAttributeNames();
		int cuentaAtributos = 0;
		
		// get all attributes names (nested or not) in Servlet Context
		// and iterate if it's a map or list
		while (e.hasMoreElements()) {
			cuentaAtributos++;
		    String nombreAtributo = (String) e.nextElement();
		    Object valorAtributo = contexto.getAttribute(nombreAtributo);

		    if (valorAtributo instanceof Map) {  // si el atributo es un Map
		        for (Map.Entry<?, Object> entrada : ((Map<?, Object>)valorAtributo).entrySet()) {
		            String claveEntrada = entrada.getKey().toString();
		            Object valorEntrada = entrada.getValue().toString();
		        	salida += claveEntrada + " = " + valorEntrada + " <br />";
		        }
		    } else if (valorAtributo instanceof List) {
		        for (Object elemento : (List<Object>)valorAtributo) {
		        	salida += elemento + " <br />";
		        }
		    } else if (valorAtributo instanceof String) {   
		    	salida += "<b>" + nombreAtributo + "</b> = " + valorAtributo + " <br />";
		    } else {  // otros tipos de atributo
		    	salida += "<b>" + nombreAtributo + "</b> = [" + valorAtributo.getClass().toString().substring(6) + "] <br />";
		    }
		}
		salida += "<h4>Se han recuperado " + cuentaAtributos + " atributos</h4>";
		return salida;
	}

}
