$(function(){
	// init all the datepickers which generally are always inside of a form.
	$('form').find('.datepicker').each(function(){
		if($(this).hasClass('datefuture')) {
			$(this).datepicker({minDate:0});
		} else if($(this).hasClass('datepast')) {
			$(this).datepicker({maxDate:0});
		} else {
			$(this).datepicker();
		}
	});

	// init all the datepickers which generally are always inside of a form.
	$('form').find('.selectpicker').selectpicker();
});