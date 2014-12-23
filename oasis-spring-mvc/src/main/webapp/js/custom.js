jQuery(document).ready(function () {
    $("[data-toggle='tooltip']").tooltip();

    $('.dropdown-toggle').dropdown();
});

var OASIS = {};

OASIS.ajax = function(opts) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var defOpts = {
        beforeSend: function( xhr ) {
            xhr.setRequestHeader(header, token);
        }
    }
    return $.ajax($.extend(defOpts, opts));
}

// Form Helper
OASIS.form = {};
OASIS.form.showErrors = function(form, errors) {
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
};
OASIS.form.hideErrors = function(form) {
    form.find('.has-error').removeClass('.has-error');
    form.find('.help-error-message').remove();
};
OASIS.form.sendJSON = function(url, type, data) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var promise = $.ajax({
        url: url,
        type: type,
        data : JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function( xhr ) {
            xhr.setRequestHeader(header, token);
        }
    });

    return promise;
};
OASIS.form.delete = function(link, url, cb) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var id = link.data('id');

    if(id !== undefined) {

        if(/\/$/g.test(url) !== true) {
            url += '/';
        }

        var promise = $.ajax({
            url: url + id,
            type: 'DELETE',
            beforeSend: function( xhr ) {
                xhr.setRequestHeader(header, token);
            }
        });
        promise.success(function() {
            window.location.reload();
        });
    } else {
        return null;
    }
};
// Evaluation Form
OASIS.form.evaluation = {};
OASIS.form.evaluation.toggleDontKnow = function() {
    $('form.userChoices').on('click', 'input', function(){
        var $el = $(this);
        var $measureDiv = $el.closest('.measure');

        if($el.hasClass('dontknow') !== true) {
            $measureDiv.find('input.dontknow').prop('checked', false);
        } else if($el.hasClass('dontknow') === true && $el.prop('checked') === true) {
            var $inputs = $measureDiv.find('input');
            $inputs.each(function(){
                $(this).prop('checked',false);
            });
            $el.prop('checked', true);
        }
    });
}

// Sortable Helper
OASIS.sortable = {};
OASIS.sortable.table = function(cb) {
    var posOnStart = -1;
    var posOnEnd = -1;

    $('.table-sortable').sortable({
        items: 'tbody > tr',
        handle: '.handle',
        start: function(event, ui) {
            var items = $(this).find('tbody > tr');
            posOnStart = items.index(ui.item);
        },
        stop: function(event, ui) {
            var items = $(this).find('tbody > tr');
            posOnEnd = items.index(ui.item);

            if(typeof cb === 'function') {
                cb(ui.item, posOnEnd-posOnStart);
            }
        }
    });
}