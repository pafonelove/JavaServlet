/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/*
    Дана реляционная база данных, имеющая единственную таблицу apartments со следующими полями:
        id, целочисленное, первичный ключ
        number, целочисленное
        square, вещественное
    В данной таблице хранится список квартир многоквартирного дома, описанный номером квартиры и её площадью. 
    Необходимо разработать сервлет, который будет принимать параметр square из GET запроса
    и выводить на экран все записи из БД с совпадающим значением площади квартиры (square).
*/

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 *
 * @author pavie
 */
@WebServlet(urlPatterns = {"/Task3Servlet"})
public class Task3Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    // Create Apartments SQL table.
    private void createApartmentsTable (Statement statement) throws SQLException {
        statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Apartments" + 
            "(`id` INTEGER not NULL, " + 
            " number INTEGER, " +  
            " square DECIMAL(5,2), " +
            " PRIMARY KEY ( id ))"
        );
        statement.executeUpdate("INSERT INTO Apartments VALUES (1, 104, 95.23)");
        statement.executeUpdate("INSERT INTO Apartments VALUES (2, 251, 95.23)");
        statement.executeUpdate("INSERT INTO Apartments VALUES (3, 451, 101.01)");
        statement.executeUpdate("INSERT INTO Apartments VALUES (4, 128, 87.39)");
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        // Get request params.
        String square = req.getParameter("square");
        // Get object for display data on page.
        PrintWriter out = resp.getWriter();
        try {
            // Connect to H2 DB.
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
            // Get data from DB.
            Statement statement = connection.createStatement();
            createApartmentsTable(statement);
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM Apartments WHERE square =" + square);
                // Test DB.
                while (rs.next()) {
                    out.println("Apartment " + rs.getString("number") + ": " + rs.getString("square") + " square meters");
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException|ClassNotFoundException sqlex) {
            out.print(sqlex);
        }
        out.close();
    }

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
