
(function() {

    $(document).ready(function() {

        setHoverHandlers();
        setClickHandlers();
        setSearchTableHoverHandler();

    });

    function setHoverHandlers(){

        setHeaderLinkHoverHandler("#btnUpload");
        setHeaderLinkHoverHandler("#btnDownload");

        setIconHoverHandler("#githubIcon", "githubOver", "github")
        setIconHoverHandler("#facebookIcon", "facebookOver", "facebook")
        setIconHoverHandler("#twitterIcon", "twitterOver", "twitter")

    }

    function setClickHandlers(){

        var $parent = $("#mainContainer");

        $parent.on("click", "#btnSearch", function() {
            BPF.searchClick();
        });

        $parent.on("focus", "#txtSearch", function() {
            $(this).removeClass().addClass("black");
            $(this).val("");
        });

        $parent.on("keypress", "#txtSearch", function(e) {
            if(e.which == 13) {
                $("#btnSearch").click();
            }
        });

        $parent.on("click", ".fileName", function(e) {
            BPF.downloadClick({
                "fileId": $(this).attr("fileId")
            });
        });

        $parent.on("click", ".fileDownload", function(e) {
            BPF.downloadClick({
                "fileId": $(this).attr("fileId")
            });
        });

        $parent.on("click", ".fileTag", function(e) {
            var query = $(this).html().split("#")[1];
            BPF.searchClick(query);
        });

        $("body").on("click", "#btnUploadCommit", function(e) {
            BPF.Uploader.upload();
        });

        $parent.on("click", "#btnDownload", function(e) {
            BPF.redirect("search.html");
        });

        $parent.on("click", "#btnUpload", function(e) {

            BPF.startWait();

            BPF.template('templates/uploadFile.html', "tmplUploadFile", {}, function(html){
                BPF.fancybox(html);
                BPF.endWait();
            });

        });

        $("body").on("focus", "#txtUploadTags", function() {
            $(this).removeClass().addClass("black");
            $(this).val("");
        });

    }

    function setIconHoverHandler($id, hoverClass, defaultClass){

        var $parent = $("#mainContainer");

        $parent.on("mouseover", $id, function() {
            $(this).removeClass(defaultClass).addClass(hoverClass);
        });

        $parent.on("mouseleave", $id, function() {
            $(this).removeClass(hoverClass).addClass(defaultClass);
        });

    }

    function setSearchTableHoverHandler(){

        var $parent = $("#mainContainer");
        var hoverClass = "whiteBg";
        var defaultClass = "lightBlueBg";

        $parent.on("mouseover", ".tableRow", function() {
            $(this).removeClass(defaultClass).addClass(hoverClass);
        });

        $parent.on("mouseleave", ".tableRow", function() {
            $(this).removeClass(hoverClass).addClass(defaultClass);
        });

    }

    function setHeaderLinkHoverHandler($id){

        var $parent = $("#mainContainer");

        $parent.on("mouseover", $id, function() {
            $(this).removeClass("white").addClass("green");
        });

        $parent.on("mouseleave", $id, function() {
            $(this).removeClass("green").addClass("white");
        });

    }

}());

(function() {

    String.prototype.format = function() {
        var s = this,
            i = arguments.length;

        while (i--) {
            s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
        }
        return s;
    };

}());

