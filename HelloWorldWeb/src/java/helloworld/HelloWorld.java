package helloworld;

import javax.servlet.http.*;    //--HttpServletRequest, HttpServletResponse
import javax.servlet.*;         //--ServletException
import java.io.*;               //--IOException, PrintWriter


/**
 *=Class HelloWorld
 */
public class HelloWorld {

  //--HTTP Get request/response handler
  public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
      PrintWriter out = res.getWriter();    //--создаем поток вывода
      out.println("<html>");
      out.println("<head>");
      out.println("<title>");
      out.println("Hello World Page");      //--заголовок страницы
      out.println("</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<center>");
      out.println("Hello, world!");         //--выводим сообщение
      out.println("</center>");
      out.println("</body>");
      out.println("</html>");
      
      out.close();                          //--закрываем поток вывода
  } //--doGet();
  
}
