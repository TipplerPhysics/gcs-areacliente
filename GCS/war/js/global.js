$(function(){
	// init all the datepickers which generally are always inside of a form.
	$('form').find('.datepicker').each(function(){
		var $datepicker = $(this);
		if($datepicker.hasClass('datefuture')) {
			$datepicker.datepicker({minDate:0});
		} else if($datepicker.hasClass('datepast')) {
			$datepicker.datepicker({maxDate:0});
		} else {
			$datepicker.datepicker();
		}
		if($datepicker.hasClass('fromTo')){
			var $targetDatepicker = $('#'+$datepicker.data('target-id'));
			$datepicker.on('change.fromTo', function(){
				$targetDatepicker.datepicker('option', 'minDate', $datepicker.datepicker('getDate'));
			});
		}
	});

	// init all the datepickers which generally are always inside of a form.
	$('form').find('.selectpicker').selectpicker();
});