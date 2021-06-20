package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;


/**
 *=Class GetDataFromDB: Тест передачи параметров из JSP Формы
 * get data from MSSQL Database
 */
public class GetDataFromDBTest1 extends HttpServlet {

  //--HTTP Get request/response handler
  @Override
  public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //--REQUEST PROCESSING--
    String param1 = request.getParameter("param1");
    String param2 = request.getParameter("param2");
    String param3 = request.getParameter("param3");
      
    //--RESPONSE PROCESSING--
    //--устраняем проблему с выводом кириллицы в response из сервлета; 
    response.setContentType("text/html; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");

    //--Формирование HTML-ответа для передачи в тело HTTP response    
    PrintWriter pw = response.getWriter();
    //-тест вывода кириллицы;
    //pw.println("Test | Тест");
    
    pw.println("<!DOCTYPE html>");
    pw.println("<html>");
    
    pw.println("<head>");
    pw.println("<title>Response Page</title>");
    pw.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">");
    pw.println("</head>");
    
    pw.println("<body>");
    pw.println("<h1>Response page | Страница ответа</h1>");
    pw.println("<hr/>");
    
    pw.println("<pre>");
    pw.println("=DEBUG:");
    pw.println(" param1: " + param1);
    pw.println(" param2: " + param2);
    pw.println(" param3: " + param3);
    pw.println("</pre>");
  
    pw.println("</body>");
    
    pw.println("</html>");
  
  } //--doGet();
    
}
