(function() {

    BPF.Uploader = new function(){

        function progressHandlingFunction(e){
            if(e.lengthComputable){
                $('progress').attr({value:e.loaded,max:e.total});
            }
        }

        function beforeSendHandler(request){

            // set headers
            request.setRequestHeader("tags", $("#txtUploadTags").val());

            // set view
            $('#uploadProgress').show();
            $("#uploadFile").hide();
            $("#uploadTags").hide();
            $("#uploadCommit").hide();
        }

        function completeHandler(e){
            //alert("completeHandler")
            BPF.notify("Upload Complete", e);
            $.fancybox.close();
        }

        function errorHandler(e){
            //alert("errorHandler")
            BPF.notify("", "ERROR @ uploader.js.errorHandler()");
        }

        this.upload = function(){

            var formData = new FormData($('form')[0]);

            $.ajax({
                url: 'FileUpload',  //Server script to process data
                type: 'POST',
                xhr: function() {  // Custom XMLHttpRequest
                    var myXhr = $.ajaxSettings.xhr();
                    if(myXhr.upload){ // Check if upload property exists
                        myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
                    }
                    return myXhr;
                },
                //Ajax events
                beforeSend: beforeSendHandler,
                success: completeHandler,
                error: errorHandler,
                // Form data
                data: formData,
                //Options to tell jQuery not to process data or worry about content-type.
                cache: false,
                contentType: false,
                processData: false
            });

        }

    }


}());

