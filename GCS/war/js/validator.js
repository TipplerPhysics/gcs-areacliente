$(function() {
	$.extend($.validator.messages, {
		required: "Este campo es obligatorio.",
		remote: "Por favor, rellena este campo.",
		email: "Por favor, escribe una direcci&oacuten de correo v&aacutelida.",
		url: "Por favor, escribe una URL válida.",
		date: "Por favor, escribe una fecha válida.",
		dateISO: "Por favor, escribe una fecha (ISO) válida.",
		number: "Por favor, escribe un n&uacutemero v&aacutelido.",
		digits: "Por favor, escribe s&oacutelo dígitos.",
		creditcard: "Por favor, escribe un número de tarjeta válido.",
		equalTo: "Por favor, escribe el mismo valor de nuevo.",
		extension: "Por favor, escribe un valor con una extensión aceptada.",
		maxlength: $.validator.format("Por favor, no escribas más de {0} caracteres."),
		minlength: $.validator.format("Por favor, no escribas menos de {0} caracteres."),
		rangelength: $.validator.format("Por favor, escribe un valor entre {0} y {1} caracteres."),
		range: $.validator.format("Por favor, escribe un valor entre {0} y {1}."),
		max: $.validator.format("Por favor, escribe un valor menor o igual a {0}."),
		min: $.validator.format("Por favor, escribe un valor mayor o igual a {0}."),
		nifES: "Por favor, escribe un NIF válido.",
		nieES: "Por favor, escribe un NIE válido.",
		cifES: "Por favor, escribe un CIF válido."
	});

	// One to rule the ... selects
	$.validator.addMethod("selected", function(value, element){
		var valid = false;

		if(value != 'default') {
			valid = true;
		}else{
			var select = $(element).parent();
			var bootstrapSel = $(select).find('button.selecpicker');
			bootstrapSel.addClass('error');	
			
			
		}
		return valid;
	}, "Por favor, selecciona un valor.");

	$.validator.addMethod('require-one', function(value, element) {
		var valid = false;
		var selector = 'input[name=' + $(element).attr('name') + ']';

		$(element).closest('form').find(selector).each(function() {
			if($(this).is(":checked")) {
				valid = true;
			}
		});
		return valid;
	},'Por favor, selecciona una opci&oacute;n.');


	// Setup form validation on the #register-form element
	$('form').validate({
	    submitHandler: function(form) {
	        form.submit();
	    },
	    errorPlacement: function(error, $element) {
			// overwritable when using the tag data-error-show-style = tooltip
			if(($element.is(':checkbox') || $element.is(':radio')) && $element.parent().hasClass('radio-container')) {
				var $target = $element.closest('.radio-container-holder');
				var $container = $target.find('#error-messages');
				if($container.length == 0){
					$container = $('<div id="error-messages" class="block-error server-errors"><ul></ul></div>');
					$element.closest('.radio-container-holder').prepend($container);
				}

				// Create error element and append it to error container
				var $errorelement = $('<li>');
				$errorelement.append(error);
				$container.find('ul').append($errorelement);

			} else {
				var $target = $element.closest('.form-container');
				var $container = $target.find('#error-messages');

				if($container.length == 0){
					$container = $('<div id="error-messages" class="block-error server-errors detail"><ul></ul></div>');
					$target.prepend($container);
				}

				// Create error element and append it to error container
				var $errorelement = $('<li>');
				$errorelement.append(error);
				$container.find('ul').append($errorelement);
			}
		}
	});
});