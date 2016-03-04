<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>Slackbot Config</title>
	
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/bootstrap-switch.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css" rel="stylesheet">	
    <link href="css/datepicker.css" rel="stylesheet">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/bot.css" rel="stylesheet">
    
    <style>
    
    
    </style>
    
   
</head>
<body role="document">

<div class="container">

<div id="mainPanel" class="container-fluid">

	<div class="header clearfix">
		
		<nav class="navbar navbar-default">
		  <div class="container-fluid">
		    <div class="navbar-header">
				<h3 class="text-muted">Project Bot Configuration</h3>
				
		    </div>
		    <div class="collapse navbar-collapse">
		    	<p id="configIcon" class="navbar-text navbar-right"><span class="glyphicon glyphicon-cog"></span></p>
		    </div> 		    
		  </div>
		</nav>		
	</div>
      
<div id="pickerPanel" class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">General Preferences</h3>
	</div>
	<div id="prefsPanelBody" class="panel-body">
		<div class="row">
			<div class="col-md-4"><label class="control-label" for="date">Team to Work With</label></div>
			<div class="col-md-4">
				<div class="dropdown">
			  	<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    	Select Team
			    	<span class="caret"></span>
			  	</button>
			  		<ul id="teamList" class="dropdown-menu" aria-labelledby="dropdownMenu1">
			    		<li><a href="#">Loading....</a></li>
			    		
			  		</ul>
				</div>
			</div>
			<div class="col-md-4"><button class="btn btn-default" id="btnRefreshTeams"><span class="glyphicon glyphicon-refresh"></span></button></div>
		</div>			
	
		<div class="row">
			<div class="col-md-4"><label class="control-label">Current Status</label></div>
			<div class="col-md-4">Unknown</div>
			<div class="col-md-4">_</div>
		</div>

	</div>
</div>

<div id="configPanel" class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title"><span id="teamName"></span> Configuration</h3>
	</div>
	<div id="configPanelBody" class="panel-body">
	
		<div class="row">
			<div class="col-md-4"><label class="control-label requiredField" for="txtApiKey">Verification Token<span class="asteriskField">*</span></label></div>
			<div class="col-md-4">
				<input class="form-control" id="txtApiKey" name="txtApiKey" type="text"/>
				<span class="help-block" id="hint_txtApiKey">Obtain from ...slack.com/apps/manage/custom-integrations</span></div>
			<div class="col-md-4">
				<button class="btn btn-primary " id="btnSave" name="submit">Save Key</button>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4"><label class="control-label requiredField" for="txtWebhook">Incoming Webhook URL<span class="asteriskField">*</span></label></div>
			<div class="col-md-4">
				<input class="form-control" id="txtWebhook" name="txtWebhook" type="text"/>
				<span class="help-block" id="hint_txtWebhook">Obtain from ...slack.com/apps/manage/custom-integrations</span></div>
			<div class="col-md-4">
				<button class="btn btn-primary " id="btnSaveWebhook" name="submit">Save URL</button>
			</div>
		</div>

		
		<div class="row">
			<div class="col-md-4"><label class="control-label" for="date">Launch Date</label></div>
			<div class="col-md-4"><input class="form-control" id="launchDate" name="txtLaunchDate" type="text"/></div>
			<div class="col-md-4"><button class="btn btn-primary " id="btnSaveDate" name="submit">Save Date</button></div>
		</div>

		<div class="row">
			<div class="col-md-4"><label class="control-label" for="date">Delete Team</label></div>
			<div class="col-md-4"><button class="btn btn-danger " id="btnDeleteTeam" name="submit">Delete</button></div>
			<div class="col-md-4">&nbsp;</div>
		</div>

	</div>	
</div>
      

            
           

<div id="phasesPanel" class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Project Phases/Sprints</h3>
	</div>
	<div class="panel-body">
	<table id="phases" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Phase Name</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tfoot>
            <tr>
                <th><input class="form-control" id="newName" name="newName" type="text"/></th>
                <th><input class="form-control" id="newStart" name="newStart" type="text"/></th>
                <th>
                	<input class="span2 form-control" id="newEnd" name="newEnd" type="text"/>
                </th>
                <th>
                	<button class="btn btn-primary " id="btnAdd" name="submit" type="submit">Add</button>
                	<button class="btn btn-default" id="btnRefreshPhases"><span class="glyphicon glyphicon-refresh"></span></button>
                </th>
            </tr>
        </tfoot>        
    </table>
	</div>    
</div>

<div id="responsesPanel" class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Bot Question/Response</h3>
	</div>
	<div class="panel-body">
		<table id="responses" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Question</th>
                <th>Answer</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tfoot>
            <tr>
                <th><input class="form-control" id="newQuestion" name="newQuestion" type="text"/></th>
                <th><input class="form-control" id="newAnswer" name="newAnswer" type="text"/></th>
                <th>
                	<button class="btn btn-primary " id="btnAddQuestion" name="submit" type="submit">Add</button>
                	<button class="btn btn-default" id="btnRefreshQuestionss"><span class="glyphicon glyphicon-refresh"></span> Refresh</button>
                </th>
            </tr>
        </tfoot>        
		</table>        
	
	</div>

</div> <!-- Responses Panel -->

<footer class="footer"><p>&copy; 2016 DigitasLBi Chicago (update 02-25-2016 15:03)</p></footer>

</div> <!-- Main Panel -->

<div id="newTeamModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">What is your Slack team name?</h4>
      </div>
      <div class="modal-body">
        <div class="control-group">
        <label class="control-label" for="dlgAddDeviceFolder_name">From https://TEAM_NAME.slack.com </label>
        <div class="controls">
          <input type="text" id="newTeamName" placeholder="TEAM_NAME" autocomplete="off">
        </div>
      </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button id="btnSaveTeam" type="button" class="btn btn-primary" data-dismiss="modal">Create Team</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="prefsModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Preferences (don't change)</h4>
      </div>
      <div class="modal-body">
		<div class="row">
			<div class="col-md-4"><label class="control-label" for="date">Password</label></div>
			<div class="col-md-4"><input class="form-control" id="txtPassword" name="txtPassword" type="text"/></div>
			<div class="col-md-4"><button class="btn btn-primary " id="btnSavePassword" name="submit">Save Password</button></div>
		</div>
		<div class="row">
			<div class="col-md-4"><label class="control-label">Require a Password?</label></div>
			<div class="col-md-4">
				<input type="checkbox" name="requirePassword">
			</div>
			<div class="col-md-4">Not working...</div>
		</div>	
		<div class="row">
			<div class="col-md-4"><label class="control-label">Connect to Slack</label></div>
			<div class="col-md-4">
				<input type="checkbox" name="slackLogin">
			</div>
			<div class="col-md-4">Send/Process Messages</div>
		</div>	
      </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</div> <!--  Container  -->

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>    
    <script src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>    
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/bootstrap-switch.js"></script>
    <script src="js/jquery.easy-overlay.js"></script>
    
    
    <script>
    
    $(document).ready(function() {
    	
    	$("#mainPanel").easyOverlay("start");
    	
    	$("[name='requirePassword']").bootstrapSwitch();
    	$("[name='slackLogin']").bootstrapSwitch();

    	loadData();
        loadTeams();
        
    	$('#newStart').datepicker({
			format: 'mm-dd-yyyy'
		});
        
    	$('#newEnd').datepicker({
			format: 'mm-dd-yyyy'
		});

    	$('#launchDate').datepicker({
			format: 'mm-dd-yyyy'
		});
    	
    	$("#mainPanel").easyOverlay("stop");
    	
    	$('#configPanel').css({ opacity: 0.4 });    	
    	$('#phasesPanel').css({ opacity: 0.4 });
    	$('#responsesPanel').css({ opacity: 0.4 });

    } );

    $('input[name="requirePassword"]').on('switchChange.bootstrapSwitch', function(event, state) {
    		console.log("Password req change: " + state.toString());
		post('/bot/passwordrequired', state.toString() );
	});

    $('input[name="slackLogin"]').on('switchChange.bootstrapSwitch', function(event, state) {
		console.log("Changed Slack Login  " + state.toString());
		post('/bot/slacklogin', state.toString() );
	});

    $('#btnSave').click(function() { 
    	saveData();	
    });

    $('#btnSaveTeam').click(function() {
    	var team = $('#newTeamName').val();
    	console.log("Saving team " + team);
    	post('/bot/team', team);
    	pauseAndReloadTeams();    	
    });

    $('#btnRefreshPhases').click(function() { 
    	pauseAndReloadTable();	
    });

    $('#btnRefreshTeams').click(function() { 
    	pauseAndReloadTeams();	
    });

    
    $('#btnSavePassword').click(function() { 
    	savePassword();	
    });

    $('#btnSaveDate').click(function() { 
    	saveLaunchDate();	
    });

    $('#btnSaveWebhook').click(function() { 
    	saveWebhook();	
    });

    $('#btnAdd').click(function() { 
    	savePhase();    	
    });

    $('#configIcon').click(function() { 
    	$('#prefsModal').modal('show');    	
    });
    

    $(document).on('click','.btn-danger',function(){
    	var button = $(this);
    	console.log("Delete details " + button);
    	alert("Check console");
        var id = $(this).attr('id'); // $(this) refers to button that was clicked
      //  deletePhase(id);
    });
    
    function loadPhases(team)
    {
    	var phaseurl = "/bot/phases/" + team;
    	
        $('#phases').DataTable( {
            "paging":   false,
            "ordering": true,
            "info":     false,
            "order": [[ 3, "desc" ]],
			ajax: {
                url: phaseurl,
                dataSrc: ""
            }
        } );
    	
    }
    
    function loadTeamPrefs(team)
    {

    	var boturl = "/bot/teamprefs/" + team;
    	console.log("Load prefs from " + boturl);
    	
    	$.ajax({url: boturl,
        	    dataType: "text",
				success: function(data) 
				{					
					var json = $.parseJSON(data);
					console.log("Response: " + json);
					$("#txtApiKey").val(json.token);			
					$("#launchDate").val(json.launchDate);
					$("#txtWebhook").val(json.webhook);

			}
    	});
    	
    }
    
    function loadData()
    {
    	var boturl = "/bot/preferences";
    	
    	$.ajax({url: boturl,
        	    dataType: "text",
				success: function(data) 
				{
					var json = $.parseJSON(data);
					$("#txtPassword").val(json.password);			
					$("[name='requirePassword']").bootstrapSwitch('state', json.passwordRequired, true);
					$("[name='slackLogin']").bootstrapSwitch('state', json.slackLogin, true);

			}
    	});

    }
    
    function loadTeams()
    {
    	$("#prefsPanelBody").easyOverlay("start");

    	// right way http://stackoverflow.com/questions/22317904/dynamically-creating-bootstrap-dropdown-from-jquery-ajax-doesnt-work
    	console.log("Loading Teams");
    	$.get( "/bot/teams-html", function(data) {
    		console.log("loadTeams() received " + data);
    		$('#teamList').html(data);
    		$('#teamList').dropdown();
    	});
    	
    	$("#teamList").on("click", "li", function(event){
            console.log("You clicked the drop down " + event.target.innerText );
            var team = event.target.innerText;
            if (team == "Create new team...")
			{
            	console.log("Open the create modal");
            	$('#newTeamModal').modal('show');	
            }
            else
            {
            	loadPhases(team);
            	loadTeamPrefs(team);
            	$('#teamName').html(team);
            	
            	$('#phasesPanel').css({ opacity: 1.0 });
            	$('#responsesPanel').css({ opacity: 1.0 });
            	$('#configPanel').css({ opacity: 1.0 });

            }
        });
    	$("#prefsPanelBody").easyOverlay("stop");

    }
    
    function savePhase()
    {
    	var name = $("#newName").val();
    	var start = $("#newStart").val();
    	var end = $("#newEnd").val();
    	var req = $("#newReq").attr('checked') ;
    	var team = $('#teamName').html();
    	var url = "/bot/phase/" + team;
    	
    	var postdata = name + "," + start + "," + end + "," + req;
    	
    	post(url, postdata);
    	$("#newName").val("");
    	$("#newStart").val("");
    	$("#newEnd").val("");

    	pauseAndReloadTable();
    }
    
    function pauseAndReloadTable()
    {
    	$("#phasesPanel").easyOverlay("start");
    	
    	setTimeout(function() {
    		console.log("Reloading");
		}, 2000);
    	
    	$('#phases').DataTable().ajax.reload();
    	$("#phasesPanel").easyOverlay("stop");
    }
    function pauseAndReloadTeams()
    {
    	
    	setTimeout(function() {
    		console.log("Reloading Teams");
		}, 2000);
    	loadTeams();
    	
    }

    
    function post(posturl, postdata)
    {
    	console.log("POST to " + posturl + ": " + postdata);
    	
    	var saveData = $.ajax({
  	      type: 'POST',
  	      url: 	posturl,
  	      data: postdata,
  	      dataType: "text",
  	      success: function(resultData) {  }
  		});
  		saveData.error(function() { alert("Something went wrong"); });     	
    }

    
    function saveData()
    {
    	$("#configPanelBody").easyOverlay("start");
    	var teamname = $("#teamName").html();
    	var postdata = $("#txtApiKey").val();    	
    	post('/bot/token/' + teamname, postdata);
    	$("#configPanelBody").easyOverlay("stop");
    }

    function saveLaunchDate()
    {
    	$("#configPanelBody").easyOverlay("start");
    	var teamname = $("#teamName").html();
    	var postdata = $("#txtLaunchDate").val();    	
    	post('/bot/launchdate/' + teamname, postdata);
    	$("#configPanelBody").easyOverlay("stop");
    }

    function saveWebhook()
    {
    	$("#configPanelBody").easyOverlay("start");
    	var teamname = $("#teamName").html();
    	var postdata = $("#txtWebhook").val();    	
    	post('/bot/webhook/' + teamname, postdata);
    	$("#configPanelBody").easyOverlay("stop");
    }

    function savePassword()
    {
    	var postdata = $("#txtPassword").val();
    	post('/bot/password', postdata);
    }

    
    function deletePhase(id)
    {
    	post('/bot/deletephase', id);
    	pauseAndReloadTable();
    }
    </script>
</body>
</html>