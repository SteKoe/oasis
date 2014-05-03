(function($) {
    $.fn.expandable = function(options) {
        var settings = $.extend({
            color: "#fff",
            backgroundColor: "#aaa"
        }, options);
        
        var css = {
        	backgroundColor: settings.backgroundColor,
        	color: settings.color,
        	padding: "0 5px",
        	borderRadius: "4px",
        	cursor: "pointer"
        };
        
        var expandButton = $(document.createElement('span')).css(css).html("&middot;&middot;&middot;");
 
        return this.append(expandButton);
    };
}(jQuery));