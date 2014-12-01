jQuery(document).ready(function () {
    $("[data-toggle='tooltip']").tooltip();
    $('.dropdown-toggle').dropdown();
});

var OASIS = {
    form: {
        showErrors: function(form, errors) {
            this.hideErrors(form);

            for(var name in errors) {
                var fieldErrors = errors[name];

                var field = form.find('[name=' + name + ']');
                var formGroup = field.closest('.form-group')
                formGroup.addClass('has-error');

                for(var i in fieldErrors) {
                    var errorMessage = $(document.createElement('span'))
                        .addClass('help-block help-error-message')
                        .text(fieldErrors[i]);

                    formGroup.append(errorMessage);
                }
            }
        },
        hideErrors: function(form) {
            form.find('.has-error').removeClass('.has-error');
            form.find('.help-error-message').remove();
        }
    }
};