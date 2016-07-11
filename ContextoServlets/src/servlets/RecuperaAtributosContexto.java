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

@WebServlet("/RecuperaAtributosContexto")
public class RecuperaAtributosContexto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		ServletContext contexto = request.getSession().getServletContext();
		String servidor = contexto.getServerInfo();
		out.println("<h3>Servlet: RecuperaAtributosContexto</h3>");
		out.println("<h5>(ejecutado en " + servidor + ")</h5>");
		out.println("<h2>Atributos del Contexto en POST</h2>");
		
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
		    Object valorAtributo = contexto.getAttribute(nombreAtributo);
		    salida += "<b>" + nombreAtributo + "</b> = [" + valorAtributo.getClass().toString().substring(6) + "] <br />";
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
		        for (Map.Entry<String, Object> entrada : ((Map<String, Object>)valorAtributo).entrySet()) {
		            String claveEntrada = entrada.getKey();
		            Object valorEntrada = entrada.getValue();
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
