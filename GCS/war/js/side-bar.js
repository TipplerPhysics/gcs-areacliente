var isAnimatingOpen=false;
var isAnimatingClose=false;

$('#sidebar').hover(
	function() {
		$(this).addClass('hovering');
	}, function() {
		$(this).removeClass('hovering');
	}
);