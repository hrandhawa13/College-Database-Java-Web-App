<!DOCTYPE html>
<html>
    <head>
        <title>HarmanRandhawa University</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
    </head>
    <body>
        
        <nav class="navbar navbar-default">
          <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="StudentControllerServlet">Home</a>
            </div>
        
            <!-- Collect the nav links, forms, and other content for toggling -->
          </div><!-- /.container-fluid -->
        </nav>
        <div class= "container">
		    <div class="row" >
		        <h1 style = "text-align: center;">Add New Student</h1>
		        <div style="width:30% ; margin:30px auto;">
		            <form action="StudentControllerServlet" method="GET">
		            	<input type="hidden" name="command" value="ADD" />
		                <div class="form-group">
		                    <input class="form-control" type="text" placeholder="First Name" name="firstName"></input>
		                </div>
		                <div class="form-group">
		                    <input class="form-control" type = "text" placeholder="Last Name" name="lastName"></input>
		                </div>
		                <div class="form-group">
		                    <input class="form-control" type="text" placeholder="Email" name="email"></input>
		                </div>
		                <div class="form-group">
		                    <button class="btn btn-lg btn-primary btn-block">Submit</button>
		                </div>
		            </form>
		            <a href="StudentControllerServlet">Go back </a>
		        </div>
		    </div>
		</div>
		     <p>Trademark HR 2018</p>
    </body>
</html>
        
        