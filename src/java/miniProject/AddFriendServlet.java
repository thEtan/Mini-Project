/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miniProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francis
 */
public class AddFriendServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String strQueryNum = request.getParameter("qnum");
        String username = (String)request.getSession().getAttribute("username");
        String alias = (String)request.getSession().getAttribute("alias");
        int intQueryNum = Integer.parseInt(strQueryNum);
        String accType = (String)request.getSession().getAttribute("accType");
        String url="";
        
        if(!accType.equals("patient"))
        {
            url="/fancyError.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
            return;
        }
        String patientUsername = request.getParameter("pNum");
        try {
            url = "/friendResults.jsp";
            //Add Friend
            if(intQueryNum == 1){
                //ADD AND RETURN FRIENDS OF PATIENT
                //MiniProjectDBAO.addFriend(username,patientUsername);
                //RETURN PATIENTS WHO ARE NOT FRIENDS
                //ArrayList ret = MiniProjectDBAO.queryPatients(username,alias);
                
                PatientDB.newFriendship(username, patientUsername);
                
                ArrayList<Patient> friends = PatientDB.getFriends(username);
                ArrayList<Patient> ret = PatientDB.findNewFriends(username, alias);

                request.setAttribute("patientList",ret);
                request.setAttribute("friendList",friends);
                
                url = "/friendResults.jsp";
            }
            //Remove Friend
            else if(intQueryNum == 2){
                //REMOVE AND RETURN FRIENDS OF PATIENT
                //ArrayList friends = MiniProjectDBAO.removeFriend(username,patientUsername);
                //RETURN PATIENTS WHO ARE NOT FRIENDS
                //ArrayList ret = MiniProjectDBAO.queryPatients(username,alias);
                
                PatientDB.removeFriend(username,patientUsername);
                
                ArrayList<Patient> friends = PatientDB.getFriends(username);
                ArrayList<Patient> ret = PatientDB.findNewFriends(username, alias);
                
                request.setAttribute("patientList",ret);
                request.setAttribute("friendList",friends);
                
                url = "/friendResults.jsp";
            }
            /* TODO output your page here. You may use following sample code. */
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw new SQLException(e);
            }
            url="/fancyError.jsp";
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddFriendServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddFriendServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
