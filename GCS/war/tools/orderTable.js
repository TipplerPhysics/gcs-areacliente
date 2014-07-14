$(function() {

	$('.search').on('keyup', function(e) { 
		var $this = $(this);
		var text = $this.val().toLowerCase();		
		var columna = $this.attr("class").split("col")[1];		
		var $table_results = $('.table_results').children();		
		for (var e=0; e<=$table_results.length; e++){
			var $linea = $($table_results[e]);			
			var cont = $($($linea.children()[columna]).children()).html().toLowerCase();			
			if (cont.indexOf(text)==-1){
				$linea.hide();
			}else{
				$linea.show();
			}			
		}
		
	});
});