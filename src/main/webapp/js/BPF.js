(function() {

    BPF = new function(){

        this.notify = function(header, text){

            $.jGrowl.defaults.closer = false;

            $.jGrowl(text, {
                header: header
                , life: 8000
                , theme:  'iphone'
            });

        }

        this.partialPage = function(path, name, callback){
            this.template(path, name, {}, callback);
        }

        this.template = function(path, name, data, callback){

            $.get(path, function(template) {
                $("body").append(template);
                ich.refresh();
                var html = ich[name](data);
                if (callback){
                    callback(html);
                }
            });

        }

        this.getQuerystringVal = function(name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");

            // TODO: this regex removes the entire search item if it includes #

            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
            return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        }

        this.searchClick = function(query){

            query = (query == null) ? $("#txtSearch").val() : query;

            this.redirect({
                "target":"search.html",
                "params": [BPF.kvp("query", query)]
            });

        }

        this.redirect = function(args){

            if ($.type(args) === "string"){
                args = {"target":args};
            }

            var href = args.target;

            if (!$.isArray(args.params)){
                args.params = [];
            }

            $.each(args.params, function( index, kvp ) {
                var pre = index<1 ? "?" : "&";
                href += "{0}{1}={2}".format(pre, kvp.key, kvp.val);
            });

            location.href = href;
        }

        this.kvp = function(key, value){
            return {
               "key": key,
                "val": value
            };
        }

        this.searchDebug = function(args){

            //TODO: ajax get search servlet here, call args.callback with response

            //TEMP: force demo object until search servlet is available
            if (!args || !args.callback){
                return;
            }

            args.callback({"query":args.query,
            "data":[
                {"id":"12344", "name": "The.True.Story.of.Wrestlemania.x264-WD", "size": "1.5 G", "type":"video/mp4", "date": "12/12/2013", "tags":["wrestlemania 3", "big john stud", "wrestling", "wwf"]},
                {"id":"23456", "name": "Greys.Anatomy.S01E12.720p.HDTV.X264-DIMENSION", "size": "123 M", "type":"video/x-msvideo", "date": "12/14/2013", "tags":["grays anatomy", "se01", "ep12"]},
                {"id":"34567", "name": "Seether Discography", "size": "250 M", "type":"audio/mp3", "date": "2/16/2014", "tags":["seether", "discography"]},
                {"id":"45673", "name": "Call of Duty Black Ops 2.iso", "size": "54.3 M", "type":"application/octet-stream", "date": "11/23/2013", "tags":["call of duty","black ops", "video game", "ps4"]},
                {"id":"54321", "name": "Aqua Teen Hunger Force (Seasons 1-9)", "size": "17.45 G", "type":"video/x-msvideo", "date": "1/1/2014", "tags":["aqua teen hunger force", "adult swim", "check it out", "steve brule", "for your health"]}
            ]});

        }

        this.downloadClick = function(args){
            if (args && args.fileId){
                BPF.notify("", "todo: download file id " + args.fileId);
            }

        }

        this.fancybox = function(html){
            $.fancybox.open({
                //maxWidth : 800,
                //maxHeight : 600,
                fitToView : false,
                width : "auto",
                height : "auto",
                autoSize : true,
                closeClick : false,
                openEffect : 'elastic',
                closeEffect : 'elastic',
                content: html
            })
        }

        this.startWait = function($element, count){
            $element = $element || $("#spinny");
            BPF.Spinny.show($element, count);
        }

        this.endWait = function(callback){
            BPF.Spinny.kill(callback);
        }

        this.chooseFile = function(){
            this.fileChooser = new FileChooser();
        }

    }


}());


