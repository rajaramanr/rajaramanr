
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ActualFunction
 */
public class ActualFunction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActualFunction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String str = (String) request.getSession().getAttribute("flag");
		System.out.println(Thread.currentThread().getName()+ " is accessing" );
		System.out.println(str+" this is the value present now ");
		if("true".equals(str))
		{
			try {
				System.out.println("waiting ");
				request.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		request.getSession().setAttribute("flag", "true");
		System.out.println(request.getSession().getAttribute("flag"));
		System.out.println(Thread.currentThread().getName()+ "Inside actual function" );
		try 
		{
			System.out.println(Thread.currentThread().getName()+" Thread gonna sleep for 10 seconds");
			Thread.sleep(30000);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		request.getSession().setAttribute("flag", "false");
		request.notifyAll();
		RequestDispatcher rd = request.getRequestDispatcher("Success.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
