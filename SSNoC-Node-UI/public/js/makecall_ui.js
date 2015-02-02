/**
 * UI LOGIC FOR MAKING A CALL
 */

function init() {

    // Compatibility shim
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
    var userId =  $('#my_id').text();
    var peer = new Peer(userId, {host: '/', port: 9000, path: 'ssnoc'});


    peer.on('open', function(){
        //$('#my_id').text(peer.id);
    });
    // Receiving a call
    peer.on('call', function(call){
        // Answer the call automatically (instead of prompting user) for demo purposes
      //有人CALL 你的时候，应该跳出个提示框，然后你调用如下代码
    	tAnswer = setInterval(function(){
    		if(window.localStream){
    			call.answer(window.localStream);
    			clearInterval(tAnswer);
    		}
    		else{
    			console.log('wait for camera!!');
    		}
    	}, 500);
        
        call.on('stream', function(stream){
            $('#their-video').prop('src', URL.createObjectURL(stream));
        });
        
        call.on('close', function(){
        	alert('The call is over');
        	location.href='/people';
        });
    });
    peer.on('error', function(err){
        alert(err.message);
        // Return to step 2 if error occurs
        processErrors()
    });

    $(function(){
        //$('#make_call').click(function(){
    	//alert($('#ISend').val());
       	if($('#ISend').val() == "true"){
    		task = setInterval(function(){
    			if(window.localStream){
    				// Initiate a call!
    			    var call = peer.call($('#callToId').val(), window.localStream);
    			
    			    // Hang up on an existing call if present
    			    if (window.existingCall) {
    			        window.existingCall.close();
    			    }
    			
    			    // Wait for stream on the call, then set peer video display
    			    call.on('stream', function(stream){
    			        $('#their-video').prop('src', URL.createObjectURL(stream));
    			    });
    			
    			    // UI stuff
    			    window.existingCall = call;
    			    $('#their_id').text(call.peer);
    			    
    			    call.on('close', function(){
    			    	alert('The call is over');
    			    	location.href='/people';
    			    });
    			    
    			    clearInterval(task);
    			}
    			else{
    				console.log('wait for camera ...');
    			}
    		}, 500);
		    
        	
        }//);

        $('#end_call').click(function(){
        	peer.destroy();
            alert('The call is over');
            location.href='/people';
        });

        // Get things started
        initializeUI();
    	
    });

    function initializeUI(){

        // Get audio/video stream
        navigator.getUserMedia({audio: true, video: true}, function(stream){
            // Set your video displays
            $('#my-video').prop('src', URL.createObjectURL(stream));
            window.localStream = stream;
            //step2();
        }, function(){ $('#step1-error').show(); });

    }
    function processErrors(){

    }

    function doSomething(){

    }

}

$(document).on('ready', init);