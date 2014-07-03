var isAnimatingOpen=false;
var isAnimatingClose=false;


$('.side-menu').hover(function(){
	if (!isAnimatingOpen){
			isAnimatingOpen = true;
			$(".side-menu").animate({left: "10px"}, 400, function(){
				isAnimatingOpen = false;
			});
		}		
	},function(){
		if (!isAnimatingClose){
				isAnimatingClose = true;
				$(".side-menu").animate({left: "95px"}, 400,function(){
					isAnimatingClose = false;
				});
		}
});