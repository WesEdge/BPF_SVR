(function() {

    BPF.SearchEngine = new function(){

        function beforeSendHandler(request){

        }

        function completeHandler(e){
            //alert(e);
        }

        function errorHandler(data, msg){
            //alert("errorHandler")
            //console.log(msg)
            BPF.notify("", "ERROR @ searchEngine.js.errorHandler() " + msg);
        }

        this.search = function(args){

            $.ajax({
                url: 'Search',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                data: {query: args.query},
                beforeSend: beforeSendHandler,
                success: function(e){
                    args.data = e;
                    completeHandler(e);
                    if (args.callback != null){
                        args.callback(args);
                    }
                },
                error: errorHandler
            });

        }

    }


}());

