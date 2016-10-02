function deleteUserAjaxCall(dltbtn, deleteUrl) {
    $.ajax({
        url: deleteUrl,
        type: "DELETE",
             
          beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
          },
             
          success: function(responseData) {
            if (responseData.status == 'SUCCESS') {
                var rowToDelete = $(dltbtn).closest("tr");
                rowToDelete.remove();
                $("#processMessageDiv").attr('class', 'alert alert-success alert-dismissible');
            } else {
            	$("#processMessageDiv").attr('class', 'alert alert-error alert-dismissible');
            }
            $("#processMessageDiv").css("display", "block");
            $("#processMessageText").html(responseData.message);
          }
    });
}

function collectFormData(fields) {
	var data = {};
	for (var i = 0; i < fields.length; i++) {
		var $item = $(fields[i]);
		data[$item.attr('name')] = $item.val();
	}
	return data;
}

$(document).ready(function() {
	var $form = $('#add-user-form');
	if($form != null) {
		$('#myModal').modal('show');
	}
	$form.bind('submit', function(e) {
		// Ajax validation
		var $inputs = $form.find('input');
		var data = collectFormData($inputs);
		
		$.post('userFormValidation.json', data, function(response) {
			$form.find('.control-group').removeClass('error');
			$form.find('.help-inline').empty();
			$form.find('.alert').remove();
			
			if (response.status == 'FAIL') {
				for (var i = 0; i < response.errorMessageList.length; i++) {
					var item = response.errorMessageList[i];
					var $controlGroup = $('#' + item.fieldName);
					$controlGroup.addClass('error');
					$controlGroup.find('.help-inline').html(item.message);
				}
			} else {
				$form.unbind('submit');
				$form.submit();
			}
		}, 'json');
		
		e.preventDefault();
		return false;
	});
});