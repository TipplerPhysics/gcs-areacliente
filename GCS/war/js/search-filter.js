var normalize = (function() {
	var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
	  to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
	  mapping = {};

	for(var i = 0, j = from.length; i < j; i++ )
	  mapping[ from.charAt( i ) ] = to.charAt( i );

	return function( str ) {
	  var ret = [];
	  for( var i = 0, j = str.length; i < j; i++ ) {
	      var c = str.charAt( i );
	      if( mapping.hasOwnProperty( str.charAt( i ) ) )
	          ret.push( mapping[ c ] );
	      else
	          ret.push( c );
	  }
	  return ret.join( '' );
	}
})();

$(function() {
	$('.search').on('keyup', function(e) { 
		var $table = $(this).closest('table').find('tbody').first();	
		var $table_results = $table.children();


		var $current_input = $(this);
		$(this).closest('tr').find('.search-th').find('input').each(function(){
			if (!($(this).is($current_input)) && $(this).val().trim().length != 0) {
			    multipleFilter = true;
			}
		});

		for (var e=0; e<$table_results.length; e++){
			var isValid = true;
			var $linea = $($table_results[e]);	
			$(this).closest('tr').find('.search-th').find('input').each(function(){
				if ($(this).val().trim().length != 0) {
					var text = normalize($(this).val().toLowerCase());
					var columna = $(this).attr("class").split("col")[1];
				    var cont = $($linea.children()[columna]).children().html().toLowerCase();
				    if (cont.indexOf(text)==-1){
				    	isValid = false;
				    }
				}
			});

			if (isValid){
				$linea.show().addClass('valid-result');
			}else{
				$linea.hide().removeClass('valid-result');
			}			
		}
		// re-paginate
		$table.paginateMe({
			pagerSelector : '#myPager',
			showPrevNext : true,
			hidePageNumbers : false,
			perPage : 5
		});
	});
});