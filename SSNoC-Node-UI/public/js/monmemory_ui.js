/**
 * UI LOGIC FOR MON MOMERY PAGE
 */

  function init() {


    var responseTxt;
    var tableContent;

    $("#spinner").hide();
	  
	$('#btn_start').click(function() {
          $("#spinner").show();
            $.ajax({
              url:  '/startmonmemory',
              type: 'POST',
              dataType: 'json'
              //data: {name: name}
            }).done(function(data){
                responseTxt = data;
            });

	  });

    $('#btn_stop').click(function() {
        $("#spinner").hide();
        $.ajax({
            url:  '/stopmonmemory',
            type: 'POST',
            dataType: 'json'
            //data: {name: name}
        }).done(function(data){
            responseTxt = data;
        });
    });

    $('#btn_view').click(function() {
        $("#spinner").hide();
        $.ajax({
            url:  '/memorycrumb1',
            type: 'GET',
            dataType: 'json'
            //data: {name: name}
        }).done(function(data){
            tableContent = data;
            if(tableContent != null && tableContent != undefined ){

                var oldTable = $('#resultTable');
                if(oldTable){
                   $('#resultTable').remove();
                }

                var table = $("<table id='resultTable'></table>").addClass("table");
                table.append("<thead><tr><td>Timestamp</td><td>Used Memory</td><td>Free Memory</td><td>Used Pst Memory</td><td>Free Pst Memory</td></tr></thead>");

                tableContent.forEach(function(memoryObj) {
                   var row = $('<tr></tr>');
                   row.append($('<td></td>').text(memoryObj.createAt));
                   row.append($('<td></td>').text(memoryObj.usedVolatile));
                   row.append($('<td></td>').text(memoryObj.remainingVolatile));
                   row.append($('<td></td>').text(memoryObj.usedPersistent));
                   row.append($('<td></td>').text(memoryObj.remainingPersistent));
                   table.append(row);
                });
                $('#resultContainer').append(table);

            }


        });

    });

	}

	$(document).on('ready', init);  